<template>
  <v-main>
    <v-card class="mx-5 my-5">
    <v-app-bar color="primary"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Stammdaten</v-toolbar-title>
    </v-app-bar>
    <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            active-class="deep-purple--text text--accent-4"
        >
          <router-link  to="/restaurant/controlpanel">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>ControlPanel</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/speisekarteGerichte"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-silverware</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/bestellungen"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-view-headline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Offene Bestellungen</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link to="/restaurant/schichtplan">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-watch</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Schichtplan</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link  to="/restaurant/stammdaten"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Stammdaten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/umsatzstatistik">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-margin</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Umsatzstatistik</v-list-item-title>
            </v-list-item>
          </router-link>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h3 align="Left">Stammdaten</h3>
          <v-form ref="registerForm" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="firstName" label="Vorname"
                              maxlength="50" :rules="[rules.required, rules.lettersAndSpacesOnly]"></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="lastName" label="Nachname"
                              maxlength="50" :rules="[rules.required, rules.lettersAndSpacesOnly]"></v-text-field>
              </v-col>
              <v-col cols="12" md="12" sm="12">
                <v-text-field v-model="restaurant_name" label="Restaurant Name"
                              maxlength="50" :rules="[rules.required, rules.lettersAndSpacesOnly]"></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="radius" label="Radius"
                              maxlength="50" :rules="[rules.required]"></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="mindestbestellwert" label="Mindestbestellwert"
                              maxlength="50" :rules="[rules.required]"></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="street" label="Straße"
                              maxlength="50" :rules="[rules.required]"></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="houseNumber" :rules="[rules.required]" label="Hausnummer" maxlength="20"
                              required></v-text-field>
              </v-col>
              <v-col cols="12" md="8" sm="8">
                <v-text-field v-model="place" label="Ort"
                              maxlength="50" :rules="[rules.required]"></v-text-field>
              </v-col>
              <v-col cols="12" md="4" sm="4">
                <v-text-field v-model="postCode" :rules="[rules.required]" label="Postleitzahl" maxlength="20"
                              required></v-text-field>
              </v-col>
              <v-col cols="12" md="8" sm="8">
                <v-text-field v-model="email" :rules="emailRules" label="E-Mail" required></v-text-field>
              </v-col>
              <v-col cols="12" md="4" sm="4">
                <v-text-field v-model="phoneNumber" :rules="[rules.required]" label="Telefonnummer" maxlength="20"
                              required></v-text-field>
              </v-col>
              <label>
                Bild auswählen
                <input type="file" ref="file" id="fileChange" accept="image/*"
                       v-on:change="selectedPicture()"/>
              </label>
              <v-spacer></v-spacer>
              <v-col class="text-right">
                <v-btn @click="validate(); artDialog = false"
                       color="red"
                       dark
                       :disabled="!valid">Speichern
                </v-btn>
              </v-col>
              <v-col>
                <v-btn
                    color="error"
                    tile
                    class="ml-2"
                    @click="deleteRestaurant"
                >
                  Konto löschen
                </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-flex>
      </v-layout>
    </v-container>
    </v-card>
  </v-main>
</template>


<script>
import axios from "axios";

export default {
  name: "RestaurantStammdaten",
  mounted() {
    this.loadStammdaten();
  },
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  methods: {
    async loadStammdaten() {

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$cookies.get('emailAdresse'));
      let StammdatenData = ResponseStammdaten.data[0];

      this.firstName = StammdatenData[0];
      this.lastName = StammdatenData[1];
      this.email = StammdatenData[2];
      this.phoneNumber = StammdatenData[3];
      this.restaurant_name = StammdatenData[4];
      this.radius = StammdatenData[5];
      this.mindestbestellwert = StammdatenData[6];
      this.street = StammdatenData[7];
      this.place = StammdatenData[8];
      this.postCode = StammdatenData[9];
      this.houseNumber = StammdatenData[10];

      // For Update
      this.benutzer_ID = StammdatenData[11];
      this.restaurant_ID = StammdatenData[12];
      this.adress_ID = StammdatenData[13];
    },
    async validate() {
      //if (this.$refs.form.validate()) {

        var response = await axios.get("https://api.geoapify.com/v1/geocode/search?text=" + this.houseNumber + "%20" + this.street + "%2C%20" + this.place + "%20" + this.postCode + "%2C%20Germany&apiKey=e15f70e37a39423cbe921dc88a1ded04");

        this.lng = response.data.features[0].geometry.coordinates[0];
        this.lat = response.data.features[0].geometry.coordinates[1];

        if (this.lng > 7.510900 && this.lng < 9.212988 && this.lat > 47.533674 && this.lat < 48.720036) {

          await axios.delete("EntfernungKundeRestaurant/deleteEntfernungByRestaurant_ID/"+this.restaurant_ID);

          var responseKundenLngLat = await axios.get("Adressen/getAllKundeLngLat");

          if (responseKundenLngLat.data.length > 0) {
            for (let i = 0; i < responseKundenLngLat.data.length; i++) {
              let resData = responseKundenLngLat.data[i];

              this.kunden_IDs[i] = resData[0];
              this.kundenLngs[i] = resData[1];
              this.kundenLats[i] = resData[2];

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
              kundennummer: this.kunden_IDs[i],
              restaurant_ID: this.restaurant_ID,
              entfernung: this.distances[i]
            };

            await axios.post("/EntfernungKundeRestaurant", entfernung);
          }

          let benutzer = {
            vorname: this.firstName,
            nachname: this.lastName,
            emailAdresse: this.email,
            telefonnummer: this.phoneNumber,
            benutzer_ID: this.benutzer_ID
          }

          let adresse = {
            strasse: this.street,
            ort: this.place,
            postleitzahl: this.postCode,
            hausnummer: this.houseNumber,
            adress_ID: this.adress_ID,
            lng: this.lng,
            lat: this.lat
          }

          let restaurant = {
            name_des_Restaurants: this.restaurant_name,
            bestellradius: this.radius,
            mindestbestellwert: this.mindestbestellwert,
            restaurant_ID: this.restaurant_ID
          }

          const responseBenutzerRestaurantToAlter = await axios.put("/Benutzer/updateBenutzerRestaurant", benutzer);
          const responseAdresseToAlter = await axios.put("/Adressen/updateAdresse", adresse);
          const responseRestaurantToAlter = await axios.put("/Restaurant/updateRestaurantStammdaten", restaurant);

          if (this.restaurantBild !== null) {
            const picturedata = new FormData();
            picturedata.append("file", this.restaurantBild);
            picturedata.append("fileName", "Bild" + this.restaurant_ID);

            const options = {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            };

            const responsePictureUpload = await axios.post('/RestaurantBilder/upload',
                picturedata, options
            ).then(function () {
              console.log('Picture successfully uploaded');
            })
                .catch(function () {
                  console.log('Picture upload error');
                });
          }

        } else {
          this.openSnackbar("Bitte gültige Adresse eingeben!")
        }
      //}

    },
    selectedPicture() {
      this.restaurantBild = this.$refs.file.files[0];
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    openSnackbar(message) {
      this.popupData.display = true;
      this.popupData.message = message;
    },
    deleteRestaurant()
    {
      axios.put("Benutzer/deleteBenutzerByEmail/"+this.email);
      axios.put("Gericht/deleteGerichtByRestaurant_ID/"+this.restaurant_ID);
    }
  },
  data() {
    return {
      drawer: false,
      group: null,
      switch1: true,
      tab: 0,
      tabs: [
        {name: "Login", icon: "mdi-account"},
        {name: "Registrieren", icon: "mdi-account-outline"}
      ],
      valid: false,
      paypal: "",
      restaurantBild: "",
      restaurant_name: "",
      descriptionShort: "",
      firstName: "",
      lastName: "",
      username: "",
      benutzer_ID: "",
      adress_ID: "",
      restaurant_ID: "",
      street: "",
      radius: "",
      mindestbestellwert: "",
      houseNumber: "",
      place: "",
      postCode: "",
      email: "",
      phoneNumber: "",
      password: "",
      verify: "",
      loginPassword: "",
      loginEmail: "",
      lng: "",
      lat: "",
      entry: [],
      sources: [],
      targets: [],
      kunden_IDs: [],
      kundenLngs: [],
      kundenLats: [],
      distances: [],
      popupData: {
        display: false,
        message: '',
      },
      loginEmailRules: [
        v => !!v || "Required",
        v => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein"
      ],
      emailRules: [
        v => !!v || "Required",
        v => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein"
      ],
      show1: false,
      rules: {
        required: value => !!value || "Required.",
        min: v => (v && v.length >= 8) || "Mindestens 8 Zeichen",
        lettersAndSpacesOnly: (v) => /^[a-zA-ZöäüÖÄÜß ]+$/.test(v) || "Nur Buchstaben und Leerzeichen sind erlaubt",
      }
    }
  }
};
</script>

<style scoped>

</style>