<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md3 sm6 xs12>
            <v-card>
              <v-tabs
                  v-model="tab"
                  background-color="red"
                  dark
                  grow
                  icons-and-text
                  show-arrows
              >
                <v-tabs-slider color="red"></v-tabs-slider>
                <v-tab v-for="(tab, idx) in tabs" :key="idx">
                  <v-icon large>{{ tab.icon }}</v-icon>
                  <div class="caption py-1">{{ tab.name }}</div>
                </v-tab>
                <v-tab-item>
                  <v-card class="px-4">
                    <v-card-text>
                      <v-form
                          ref="loginForm"
                          v-model="loginValid"
                          lazy-validation
                      >
                        <v-row>
                          <v-col cols="12">
                            <v-text-field
                                v-model="loginEmail"
                                :rules="loginEmailRules"
                                label="E-Mail"
                                required
                            ></v-text-field>
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
                          <!-- <v-spacer></v-spacer> -->
                          <v-col class="text-right">
                            <v-btn
                                :disabled="!validateLogin"
                                color="red"
                                dark
                                rounded
                                @click="validateLogin"
                            >Login
                            </v-btn
                            >
                          </v-col>
                        </v-row>
                      </v-form>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
                <v-tab-item>
                  <v-card class="px-4">
                    <v-card-text>
                      <v-form
                          ref="registrationForm"
                          v-model="registrationValid"
                          lazy-validation
                      >
                        <v-row>
                          <v-col cols="12" md="12" sm="12">
                            <v-text-field v-model="salutation" :rules="[rules.required]" label="Anrede"
                                          maxlength="20" required></v-text-field>
                          </v-col>
                          <v-col cols="12" md="12" sm="12">
                            <v-text-field v-model="username" :rules="[rules.required]" label="Benutzername"
                                          maxlength="20" required></v-text-field>
                          </v-col>
                          <v-col cols="12" md="6" sm="6">
                            <v-text-field
                                v-model="firstName"
                                :rules="[rules.required]"
                                label="Vorname"
                                maxlength="20"
                                required
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" md="6" sm="6">
                            <v-text-field
                                v-model="lastName"
                                :rules="[rules.required]"
                                label="Nachname"
                                maxlength="20"
                                required
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" md="8" sm="8">
                            <v-text-field
                                v-model="email"
                                :rules="emailRules"
                                label="E-Mail"
                                required
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" md="4" sm="4">
                            <v-text-field
                                v-model="phoneNumber"
                                :rules="[rules.required]"
                                label="Telefonnummer"
                                maxlength="20"
                                required
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12">
                            <v-menu
                                :close-on-content-click="false"
                                transition="scale-transition"
                                offset-y
                                min-width="auto"
                            >
                              <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="date"
                                    label="Geburtsdatum"
                                    append-icon="mdi-calendar"
                                    readonly
                                    :rules="[rules.required]"
                                    v-bind="attrs"
                                    v-on="on"
                                ></v-text-field>
                              </template>
                              <v-date-picker
                                  v-model="date"
                                  no-title
                                  scrollable
                                  :max="new Date().toISOString().substr(0, 10)"
                                  min="1950-01-01"
                              ></v-date-picker>
                            </v-menu>
                          </v-col>
                          <v-col cols="12">
                            <v-text-field
                                v-model="driverLicense"
                                label="Führerschein"
                                :rules="[rules.required]"
                                required
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12">
                            <v-text-field
                                v-model="password"
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
                          <v-col cols="12">
                            <v-text-field
                                v-model="verify"
                                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                :rules="[rules.required, passwordMatch]"
                                :type="show1 ? 'text' : 'password'"
                                block
                                counter
                                label="Passwort bestätigen"
                                name="input-10-1"
                                @click:append="show1 = !show1"
                            ></v-text-field>
                          </v-col>
                          <v-spacer></v-spacer>
                          <v-col>
                            <v-btn
                                :disabled="!validateRegistration"
                                color="red"
                                dark
                                rounded
                                @click="validateRegistration"
                            >Weiter zur Fahrzeug-Auswahl
                            </v-btn
                            >
                          </v-col>
                        </v-row>
                      </v-form>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
                <v-tab-item>
                  <v-card class="px-4">
                    <v-card-text>
                      <v-form
                          ref="verificationForm"
                          v-model="verificationValid"
                          lazy-validation
                      >
                        <v-row>
                          <v-col cols="12">
                            <v-combobox
                                label="Fahrzeug"
                                :items="vehicles"
                                v-model="vehicle"
                                :rules="[rules.required]"
                                required
                            ></v-combobox>
                          </v-col>
                          <v-col>
                            <v-btn color="blue" dark rounded @click="linkPaypal"
                            >Paypal Konto verknüpfen
                            </v-btn
                            >
                          </v-col>
                          <v-spacer></v-spacer>
                          <v-col cols="12">
                            <v-checkbox
                                label="AGB gelesen und akzeptiert"
                                v-model="agbAccepted"
                            ></v-checkbox>
                          </v-col>
                          <v-col class="text-left">
                            <v-btn
                                color="red"
                                dark
                                rounded
                                @click="validateVerification"
                            >Fahrerkonto erstellen
                            </v-btn
                            >
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
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginDriver",
  computed: {
    passwordMatch() {
      return () =>
          this.password === this.verify || "Passwort muss übereinstimmen.";
    },
  },
  methods: {
    async validateLogin() {


      //if (this.$refs.loginForm.validate()) {

      //}
    },

    async validateRegistration() {

      var benutzer = {
        vorname: this.firstName,
        nachname: this.lastName,
        benutzername: this.username,
        emailAdresse: this.email,
        passwort: this.password,
        telefonnummer: this.phoneNumber,
        rolle: 3,
        paypal_Account: "dummy"
      };

      const responseBenutzer = await axios.post("/Benutzer", benutzer);

      this.benutzer_ID = responseBenutzer.data.benutzer_ID;

      var fahrer = {
        benutzer_Id: this.benutzer_ID,
        anrede: this.salutation,
        geburtsdatum: this.date,
        fuehrerschein: this.driverLicense
      };

      const responseFahrer = await axios.post("/Fahrer", fahrer)

      this.fahrer_ID = responseFahrer.data.fahrernummer;
      if (this.$refs.registrationForm.validate()) {
        this.tab = 2;
      }
    },

    async validateVerification() {

      var fahrzeug = {
        fahrzeugtyp: this.vehicle
      };

      const repsonseFahrzeug = await axios.post("/Fahrzeug", fahrzeug);

      this.fahrzeug_ID = repsonseFahrzeug.data.fahrzeug_Id;

      var createdFahrzeug = {
        fahrzeug_Id: this.fahrzeug_ID,
        fahrzeugtyp: this.vehicle
      };

      await axios.put("/Fahrer/updateFahrzeugId/"+this.fahrer_ID, createdFahrzeug);

      if (this.$refs.verificationForm.validate()) {
        // submit form to server/API here...
      }
    }
    ,
    linkPaypal() {
      // TODO:
    }
    ,
    reset() {
      this.$refs.form.reset();
    }
    ,
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data() {
    return {
      tab: 0,
      tabs: [
        {name: "Login", icon: "mdi-account"},
        {name: "Registrieren", icon: "mdi-account-outline"},
        {name: "Verifikation", icon: "mdi-license"}, // TODO: Hide tab head
      ],
      // Verification Tab
      // TODO: retrieve valid vehicles from API
      vehicles: ["PKW", "Fahrrad", "Motorrad"],
      vehicle: "",
      fahrzeug_ID: "",
      fahrer_ID: "",
      driverLicense: "",
      licensePlate: "",
      agbAccepted: false,
      verificationValid: false,
      // Registration Tab
      registrationValid: true,
      benutzer_ID: "",
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      date: "",
      salutation: "",
      password: "",
      verify: "",
      // Login Tab
      loginValid: false,
      loginPassword: "",
      loginEmail: "",
      loginEmailRules: [
        (v) => !!v || "Required",
        (v) => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein",
      ],
      emailRules: [
        (v) => !!v || "Required",
        (v) => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein",
      ],
      show1: false,
      rules: {
        required: (value) => !!value || "Required.",
        min: (v) => (v && v.length >= 8) || "Mindestens 8 Zeichen",
      },
    };
  }
  ,
}
;
</script>