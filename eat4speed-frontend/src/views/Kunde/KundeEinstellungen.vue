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
              :rules="[v => !!v || 'Vorname wird benötigt']"
              label="Vorname"
              required
          ></v-text-field>
            </v-col>
            <v-col>
          <v-text-field
              v-model="lastname"
              :rules="[v => !!v || 'Nachname wird benötigt']"
              label="Nachname"
              required
          ></v-text-field>
            </v-col>
          </v-row>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="street"
                  :rules="[v => !!v || 'Strasse wird benötigt']"
                  label="Strasse"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="houseNumber"
                  :rules="[v => !!v || 'Hausnummer wird benötigt']"
                  label="Hausnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="town"
                  :rules="[v => !!v || 'Ort wird benötigt']"
                  label="Ort"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="zip"
                  :rules="[v => !!v || 'Postleitzahl wird benötigt']"
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
                  :rules="[v => !!v || 'Telefonnummer wird benötigt']"
                  label="Telefonnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>


          <v-btn
              :disabled="!valid"
              @click="validate"
          >
            Ändern
          </v-btn>

          <v-btn
              @click="reset"
          >
            Reset Form
          </v-btn>

          <v-btn
              color="error"
          >
            Konto löschen
          </v-btn>
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

      const ResponseEinstellungen = await axios.get("Benutzer/getBenutzerKundeEinstellungenByLogin/" + this.$store.getters.getLoginData.auth.username);
      let EinstellungenData = ResponseEinstellungen.data[0];

       console.log(ResponseEinstellungen);

      this.firstname = EinstellungenData[0];
      this.lastname = EinstellungenData[1];
      this.email = EinstellungenData[2];
      this.phone = EinstellungenData[3];
      this.street = EinstellungenData[4];
      this.town = EinstellungenData[5];
      this.zip = EinstellungenData[6];
      this.houseNumber = EinstellungenData[7];


      // For Update
      this.benutzer_ID = EinstellungenData[8];
      this.adress_ID = EinstellungenData[9];
      this.kundennummer = EinstellungenData[10];

      // console.log(this.benutzer_ID);
      // console.log(this.restaurant_ID);
      // console.log(this.adress_ID);
    },
    async validate () {
      if (this.$refs.form.validate()) {
        this.snackbar = true

        let benutzer = {
          vorname: this.firstname,
          nachname: this.lastname,
          emailAdresse: this.email,
          telefonnummer: this.phone,
          benutzer_ID: this.benutzer_ID
        }

        let adresse = {
          strasse: this.street,
          ort: this.town,
          postleitzahl: this.zip,
          hausnummer: this.houseNumber,
          adress_ID: this.adress_ID
        }

        let kunde = {
          name: this.lastname,
          vorname: this.firstname,
          kundennummer: this.kundennummer
        }

        const responseBenutzerKundeToAlter = await axios.put("/Benutzer/updateBenutzerRestaurant", benutzer);
        const responseAdresseToAlter = await axios.put("/Adressen/updateAdresse", adresse);
        const responseKundeToAlter = await axios.put("/Kunde/updateKundeEinstellungen", kunde);

        console.log(responseBenutzerKundeToAlter);
        console.log(responseAdresseToAlter);
        console.log(responseKundeToAlter);
      }
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
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
    houseNumberRules: [
      v => !!v || 'Hausnummer wird benötigt',
      v => /.number/.test(v) || 'Nummer muss eine Zahl sein'
    ],
    town: '',
    zip: '',
    email: '',
    emailRules: [
      v => !!v || 'E-mail wird benötigt',
      v => /.+@.+/.test(v) || 'E-mail muss korrekt sein'
    ],
    phone: ''
  })

}
</script>

<style scoped>

</style>