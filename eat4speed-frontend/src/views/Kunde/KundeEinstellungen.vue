<template>
  <v-main>
    <v-container>
      <h1>Einstellungen</h1>
      <v-container>
        <v-form
            ref="form"
            v-model="valid"
            lazy-validation
        >
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="firstname"
                  :rules="[rules.required, rules.lettersAndSpacesOnly]"
                  label="Vorname"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="lastname"
                  :rules="[rules.required, rules.lettersAndSpacesOnly]"
                  label="Nachname"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="street"
                  :rules="[rules.required]"
                  label="Strasse"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="houseNumber"
                  :rules="[rules.required]"
                  label="Hausnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="place"
                  :rules="[rules.required]"
                  label="Ort"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="zip"
                  :rules="[rules.required]"
                  label="Postleitzahl"
                  required
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="email"
                  :rules="emailRules"
                  label="E-mail"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="phone"
                  :rules="[rules.required]"
                  label="Telefonnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>

          <div class="text-right">
            <v-dialog
                v-model="dialog"
                width="25%"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="green"
                    tile
                    v-bind="attrs"
                    v-on="on"
                    :disabled="!valid"
                >
                  Ändern
                </v-btn>
              </template>

              <v-card>
                <v-card-title>
                  Einstellungen ändern
                </v-card-title>

                <v-card-text>
                  Sind Sie sicher, dass Sie die Einstellungen ändern wollen?
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="green"
                      text
                      @click="validate"


                  >
                    Ja
                  </v-btn>
                  <v-btn
                      color="red"
                      text
                      @click="closeDialog"

                  >
                    Nein
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-btn
                color="error"
                tile
                class="ml-2"
                @click="deleteKunde"
            >
              Konto löschen
            </v-btn>
          </div>


        </v-form>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "CustomerSettings",
  mounted() {
    this.loadEinstellungen();
  },

  methods: {
    async loadEinstellungen() {

      const ResponseEinstellungen = await axios.get("Benutzer/getBenutzerKundeEinstellungenByLogin/" + this.$cookies.get('emailAdresse'));
      let EinstellungenData = ResponseEinstellungen.data[0];

      this.firstname = EinstellungenData[0];
      this.lastname = EinstellungenData[1];
      this.email = EinstellungenData[2];
      this.phone = EinstellungenData[3];
      this.street = EinstellungenData[4];
      this.place = EinstellungenData[5];
      this.zip = EinstellungenData[6];
      this.houseNumber = EinstellungenData[7];

      // For Update
      this.benutzer_ID = EinstellungenData[8];
      this.adress_ID = EinstellungenData[9];
      this.kundennummer = EinstellungenData[10];
    },
    async validate() {
      // if (this.$refs.form.validate()) {
        this.snackbar = true

        var response = await axios.get("https://api.geoapify.com/v1/geocode/search?text=" + this.houseNumber + "%20" + this.street + "%2C%20" + this.place + "%20" + this.postCode + "%2C%20Germany&apiKey=e15f70e37a39423cbe921dc88a1ded04");

        this.lng = response.data.features[0].geometry.coordinates[0];
        this.lat = response.data.features[0].geometry.coordinates[1];

        if (this.lng > 7.510900 && this.lng < 9.212988 && this.lat > 47.533674 && this.lat < 48.720036) {

          await axios.delete("EntfernungKundeRestaurant/deleteEntfernungByKundennummer/"+this.kundennummer);

          var responseRestaurantsLngLat = await axios.get("Adressen/getAllRestaurantLngLat");

          if (responseRestaurantsLngLat.data.length > 0) {
            for (let i = 0; i < responseRestaurantsLngLat.data.length; i++) {
              let resData = responseRestaurantsLngLat.data[i];

              this.restaurant_IDs[i] = resData[0];
              this.restaurantLngs[i] = resData[1];
              this.restaurantLats[i] = resData[2];

              let entry = [];
              entry[0] = resData[1];
              entry[1] = resData[2];

              this.targets[i] = entry;
            }

            this.entry[0] = this.lng;
            this.entry[1] = this.lat;

            this.sources[0] = this.entry;

            let config = {
              headers: {
                'Content-Type': 'application/json',
              }
            }

            var data = {
              mode: "drive",
              sources: this.sources,
              targets: this.targets
            }

            var responseEntfernungen = await axios.post("https://api.geoapify.com/v1/routematrix?apiKey=e15f70e37a39423cbe921dc88a1ded04", data, config);

            for (let i = 0; i < responseEntfernungen.data.sources_to_targets[0].length; i++) {
              this.distances[i] = responseEntfernungen.data.sources_to_targets[0][i].distance / 1000
            }
          }

          for (let i = 0; i < this.distances.length; i++) {
            var entfernung = {
              kundennummer: this.kundennummer,
              restaurant_ID: this.restaurant_IDs[i],
              entfernung: this.distances[i]
            };

            await axios.post("/EntfernungKundeRestaurant", entfernung);
          }

          let benutzer = {
            vorname: this.firstname,
            nachname: this.lastname,
            emailAdresse: this.email,
            telefonnummer: this.phone,
            benutzer_ID: this.benutzer_ID
          }

          let adresse = {
            strasse: this.street,
            ort: this.place,
            postleitzahl: this.zip,
            hausnummer: this.houseNumber,
            adress_ID: this.adress_ID,
            lng: this.lng,
            lat: this.lat
          }

          let kunde = {
            name: this.lastname,
            vorname: this.firstname,
            kundennummer: this.kundennummer
          }

          const responseBenutzerKundeToAlter = await axios.put("/Benutzer/updateBenutzerRestaurant", benutzer);
          const responseAdresseToAlter = await axios.put("/Adressen/updateAdresse", adresse);
          const responseKundeToAlter = await axios.put("/Kunde/updateKundeEinstellungen", kunde);
        } else {
          this.openSnackbar("Bitte gültige Adresse eingeben!")
        }
      // }
      this.closeDialog();
    },
    resetValidation() {
      this.$refs.form.resetValidation()
    },
    deleteKunde()
    {
      axios.put("Benutzer/deleteBenutzerByEmail/"+this.email);
    },
    closeDialog: function () {
      this.dialog = false;
    },
    openSnackbar(message) {
      this.popupData.display = true;
      this.popupData.message = message;
    }
  },
  data: () => ({
    valid: true,
    select: null,
    anrede: [
      '-',
      'Frau',
      'Herr',
    ],
    firstname: '',
    lastname: '',
    benutzer_ID: "",
    adress_ID: "",
    kundennummer: "",
    street: '',
    houseNumber: '',
    lng: 0,
    lat: 0,
    entry: [],
    sources: [],
    targets: [],
    restaurant_IDs: [],
    restaurantLngs: [],
    restaurantLats: [],
    distances: [],
    popupData: {
      display: false,
      message: '',
    },
    houseNumberRules: [
      v => !!v || 'Hausnummer wird benötigt',
      v => /.number/.test(v) || 'Nummer muss eine Zahl sein'
    ],
    show1: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 8) || "Mindestens 8 Zeichen",
      lettersAndSpacesOnly: (v) => /^[a-zA-ZöäüÖÄÜß ]+$/.test(v) || "Nur Buchstaben und Leerzeichen sind erlaubt",
    },
    place: '',
    zip: '',
    email: '',
    emailRules: [
      v => !!v || 'E-mail wird benötigt',
      v => /.+@.+/.test(v) || 'E-mail muss korrekt sein'
    ],
    phone: '',
    dialog: false
  })
}
</script>

<style scoped>

</style>