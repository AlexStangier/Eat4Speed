<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md3 sm6 xs12>
          <v-card>
            <v-tabs v-model="tab" background-color="red" dark grow icons-and-text show-arrows>
              <v-tabs-slider color="red"></v-tabs-slider>
              <v-tab v-for="i in tabs" :key="i">
                <v-icon large>{{ i.icon }}</v-icon>
                <div class="caption py-1">{{ i.name }}</div>
              </v-tab>
              <v-tab-item>
                <v-card class="px-4">
                  <v-card-text>
                    <v-form ref="loginForm" v-model="valid" lazy-validation>
                      <v-row>
                        <v-col cols="12">
                          <v-text-field v-model="loginEmail" :rules="loginEmailRules" label="E-Mail"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-text-field
                              v-model="loginPassword"
                              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                              :rules="[rules.required, rules.min]"
                              :type="show1 ? 'text' : 'password'"
                              counter
                              hint="Mindestens 8 Zeichen"
                              label="Passwort"
                              name="input-10-1"
                              @click:append="show1 = !show1"
                          ></v-text-field>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col class="text-right">
                          <v-btn color="red" dark rounded @click="login">Login</v-btn>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-tab-item>
              <v-tab-item>
                <v-card class="px-4">
                  <v-card-text>
                    <v-form ref="registerForm" v-model="valid" lazy-validation>
                      <v-row>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="salutation" :rules="[rules.required]" label="Anrede"
                                        maxlength="20" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="firstName" :rules="[rules.required]" label="Vorname"
                                        maxlength="20" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="lastName" :rules="[rules.required]" label="Nachname" maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="username" :rules="[rules.required]" label="Benutzername"
                                        maxlength="20" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="8" sm="8">
                          <v-text-field v-model="street" :rules="[rules.required]" label="Straße" maxlength="40"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="4" sm="4">
                          <v-text-field v-model="houseNumber" :rules="[rules.required]" label="Hausnummer"
                                        maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="8" sm="8">
                          <v-text-field v-model="place" :rules="[rules.required]" label="Ort" maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="4" sm="4">
                          <v-text-field v-model="postCode" :rules="[rules.required]" label="Postleitzahl"
                                        maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="8" sm="8">
                          <v-text-field v-model="email" :rules="emailRules" label="E-Mail" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="4" sm="4">
                          <v-text-field v-model="phoneNumber" :rules="[rules.required]" label="Telefonnummer"
                                        maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="paypal" :rules="emailRules" label="Paypal Email"
                                        maxlength="20" required></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                        :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'Passwort'"
                                        counter hint="Mindestens 8 Zeichen" label="Passwort" name="input-10-1"
                                        @click:append="show1 = !show1"></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-text-field v-model="verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                        :rules="[rules.required, passwordMatch]"
                                        :type="show1 ? 'text' : 'Passwort'" block
                                        counter label="Passwort bestätigen" name="input-10-1"
                                        @click:append="show1 = !show1"></v-text-field>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col class="text-right">
                          <v-btn color="red" dark rounded @click="validate">Register</v-btn>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-tab-item>
            </v-tabs>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import router from "@/router";
import Popup from '@/components/Snackbar.vue';

export default {
  name: "LoginCustomer",
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  components: {
    popup: Popup,
  },
  methods: {

    async login() {
      this.$http.post('/Login/user', {
        emailAdresse: this.loginEmail,
        passwort: btoa(this.loginPassword)
      })
          .then((response) => {
            if (response.status === 200) {
              this.$store.commit('saveLoginData', {
                emailAdresse: response.data.emailAdresse,
                passwort: response.data.passwort
              });
              router.push({ name: "Start"})
            }
          }, (error) => {
            if (error.message === 'Request failed with status code 404') {
              this.openSnackbar('Benutzername oder Passwort falsch');
            } else {
              this.openSnackbar(error);
            }
          });
      },
    async validate() {

      console.log("test");

      var benutzer = {
        vorname: this.firstName,
        nachname: this.lastName,
        benutzername: this.username,
        emailAdresse: this.email,
        passwort: this.password,
        telefonnummer: this.phoneNumber,
        rolle: "Customer",
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
      console.log(responseAdressen.data.adress_ID);

      this.adress_ID = responseAdressen.data.adress_ID;

      let createdKunde;
      var kunde = {
        benutzer_ID: this.benutzer_ID,
        anrede: this.salutation,
        name: this.lastName,
        vorname: this.firstName,
        anschrift: this.adress_ID
      };

      axios.post("/Kunde", kunde)
          .then(function (response) {
            createdKunde.output = response.data;
          }.bind(this))

      if (this.$refs.loginForm.validate()) {
        // submit form to server/

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
      salutation: "",
      adress_ID: "",
      benutzer_ID: "",
      firstName: "",
      lastName: "",
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
      username: "",
      paypal: "",
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