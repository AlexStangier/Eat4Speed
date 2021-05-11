<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h3 align="Left">Stammdaten</h3>
          <v-form ref="registerForm" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="firstName" label="Vorname"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="lastName" label="Nachname"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="12" sm="12">
                <v-text-field v-model="restaurant_name" label="Restaurant Name"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="radius" label="Radius"
                              maxlength="50" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <v-text-field v-model="mindestbestellwert" label="Mindestbestellwert"
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
                <v-btn @click="test(); artDialog = false"
                       color="red"
                       dark
                       class="justify-center">Speichern
                </v-btn>
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

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$store.getters.getLoginData.auth.username);
      let StammdatenData = ResponseStammdaten.data[0];

      // console.log(ResponseStammdaten);

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

      // console.log(this.benutzer_ID);
      // console.log(this.restaurant_ID);
      // console.log(this.adress_ID);
    },
    async test() {
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
        adress_ID: this.adress_ID
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

      // console.log(responseBenutzerRestaurantToAlter);
      // console.log(responseAdresseToAlter);
      // console.log(responseRestaurantToAlter);

    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },

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