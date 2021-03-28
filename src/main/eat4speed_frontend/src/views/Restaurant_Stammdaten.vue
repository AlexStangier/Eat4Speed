<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>

              <h3 align="Left">Name</h3>


            <v-form ref="registerForm" v-model="valid" lazy-validation>
              <v-row>
                <v-col cols="12" md="12" sm="12">
                  <v-text-field label="Vorname"
                                maxlength="50" required></v-text-field>
                </v-col>
                <v-col cols="12" md="12" sm="12">
                  <v-text-field label="Nachname"
                                maxlength="50" required></v-text-field>
                </v-col>
                <v-col cols="12" md="6" sm="6">
                  <v-text-field label="Straße"
                                maxlength="50" required></v-text-field>
                </v-col>
                <v-col cols="12" md="6" sm="6">
                  <v-text-field v-model="houseNumber" :rules="[rules.required]" label="Hausnummer" maxlength="20"
                                required></v-text-field>
                </v-col>

                <v-col cols="12" md="8" sm="8">
                  <v-text-field label="Ort"
                                maxlength="50" required></v-text-field>
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

                <v-spacer></v-spacer>
                <v-col class="text-right">
                  <v-btn>Speichern</v-btn>
                </v-col>
              </v-row>
            </v-form>

          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>


<script>
import axios from "axios";

export default {
  name: "Restaurant_Stammdaten",
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  methods: {
    async validate() {
      var benutzer = {
        vorname: this.firstName,
        nachname: this.lastName,
        benutzername: this.username,
        emailAdresse: this.email,
        passwort: this.password,
        telefonnummer: this.phoneNumber,
        rolle: 2,
        paypal_Account: this.paypal
      };

      const responseBenutzer = await axios.post("/Benutzer", benutzer);

      this.benutzer_ID = responseBenutzer.data.benutzer_ID;

      var adressen = {
        strasse: this.street,
        hausnummer: this.houseNumber,
        ort: this.place,
        postleitzahl: this.postCode
      };

      const responseAdressen = await axios.post("/Adressen", adressen);

      console.log(responseAdressen);
      console.log(responseAdressen.data);
      console.log(responseAdressen.data.adress_Id);

      this.adress_ID = responseAdressen.data.adress_Id;

      let createdRestaurant;
      var restaurant = {
        benutzer_Id: this.benutzer_ID,
        name_Des_Restaurants: this.restaurant_name,
        allgemeine_Beschreibung: this.descriptionShort,
        anschrift: this.adress_ID,
        verifiziert: 0
      };

      axios.post("/Restaurant", restaurant)
          .then(function (response) {
            createdRestaurant.output = response.data;
          }.bind(this))

      if (this.$refs.loginForm.validate()) {
        // submit form to server/API here...
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data() {
    return {
      tab: 0,
      tabs: [
        {name: "Login", icon: "mdi-account"},
        {name: "Registrieren", icon: "mdi-account-outline"}
      ],
      valid: true,
      paypal: "",
      restaurant_name: "",
      descriptionShort: "",
      firstName: "",
      lastName: "",
      username: "",
      benutzer_ID: "",
      adress_ID: "",
      street: "",
      houseNumber: "",
      place: "",
      postCode: "",
      email: "",
      phoneNumber: "",
      password: "",
      verify: "",
      loginPassword: "",
      loginEmail: "",
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
        min: v => (v && v.length >= 8) || "Mindestens 8 Zeichen"
      }
    }
  }
};
</script>

<style scoped>

</style>