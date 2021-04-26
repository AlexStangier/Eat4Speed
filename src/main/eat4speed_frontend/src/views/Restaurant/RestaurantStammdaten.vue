<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h3 align="Left">Stammdaten</h3>
          <v-form ref="registerForm" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="12" md="12" sm="12">
                <v-text-field v-model="firstName" label="Vorname"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="12" sm="12">
                <v-text-field v-model="lastName" label="Nachname"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="street" label="Straße"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="houseNumber" :rules="[rules.required]" label="Hausnummer" maxlength="20"
                              required></v-text-field>
              </v-col>
              <v-col cols="12" md="8" sm="8">
                <v-text-field v-model="place" label="Ort"
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
</template>


<script>
import axios from "axios";

export default {
  name: "RestaurantStammdaten",
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  methods: {


    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },


    async saveInfo(){

      this.restaurantID = 22;
      this.benutzer_ID = 22;
      this.adress_ID = 22;

      let adresse = {
        strasse:this.street,
        ort:this.place,
        postleitzahl:this.postCode,
        hausnummer:this.houseNumber
      }

      let benutzer = {
        vorname:this.firstName,
        nachname:this.lastName,
        emailAdresse:this.email,
        telefonnummer:this.phoneNumber
      }

      const responseAdresse = await axios.post("/Adressen", adresse);

      const responseBenutzer = await axios.post("/Benutzer", benutzer);
      this.adress_ID = responseAdresse.data.adress_ID;
      this.benutzer_ID = responseBenutzer.data.benutzer_ID;

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