<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md3 sm6 xs12>
          <v-card>
            <v-tabs
                v-model="tab"
                background-color="primary"
                dark
                grow
                icons-and-text
                show-arrows
            >
              <v-tabs-slider color="blue-grey"></v-tabs-slider>
              <v-tab v-for="(tab, idx) in tabs" :key="idx">
                <v-icon large>{{ tab.icon }}</v-icon>
                <div class="caption py-1">{{ tab.name }}</div>
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
                          <v-btn color="primary" depressed tile @click="login">Login</v-btn>
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
                        v-model="valid"
                    >
                      <v-row>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="salutation" :rules="[rules.required, rules.lettersAndSpacesOnly]" label="Anrede"
                                        maxlength="50" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="username" :rules="[rules.required]" label="Benutzername"
                                        maxlength="50" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field
                              v-model="firstName"
                              :rules="[rules.required, rules.lettersAndSpacesOnly]"
                              label="Vorname"
                              maxlength="50"
                              required
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field
                              v-model="lastName"
                              :rules="[rules.required, rules.lettersAndSpacesOnly]"
                              label="Nachname"
                              maxlength="50"
                              required
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" md="8" sm="8">
                          <v-text-field
                              v-model="email"
                              :rules="emailRules"
                              label="E-Mail"
                              maxlength="50"
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
                              maxlength="50"
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
                              maxlength="50"
                              @click:append="show1 = !show1"
                              required
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
                              maxlength="50"
                              label="Passwort bestätigen"
                              name="input-10-1"
                              @click:append="show1 = !show1"
                              required
                          ></v-text-field>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col>
                          <v-btn
                              :disabled="!validateRegistration"
                              color="primary"
                              depressed
                              tile
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
                        <v-col cols="12">
                          <v-checkbox
                              label="AGB gelesen und akzeptiert"
                              v-model="agbAccepted"
                              :rules="[rules.required]"
                          ></v-checkbox>
                        </v-col>
                        <v-col cols="12" class="mt-n8">
                          <v-dialog
                              v-model="dialog"
                              scrollable
                              max-width="500px"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-span
                                  color="primary"
                                  dark
                                  v-bind="attrs"
                                  v-on="on"
                              >
                                Zu den Allgemeinen Geschäftsbedingungen
                              </v-span>
                            </template>
                            <v-card>
                              <v-card-title>Allgemeine Geschäftsbedingungen</v-card-title>
                              <v-divider></v-divider>
                              <v-card-text style="height: 400px;">
                                Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.

                                Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.

                                Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.

                                Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer
                              </v-card-text>
                              <v-divider></v-divider>
                              <v-card-actions>
                                <v-btn
                                    color="blue darken-1"
                                    text
                                    @click="dialog = false"
                                >
                                  Schließen
                                </v-btn>
                              </v-card-actions>
                            </v-card>
                          </v-dialog>
                        </v-col>
                        <v-col class="text-left">
                          <v-btn
                              color="primary"
                              depressed
                              tile
                              :disabled="!valid"
                              @click="validateVerification"
                          >Fahrerkonto erstellen
                          </v-btn>
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
      <popup :popupData="popupData"></popup>
    </v-container>
  </v-main>
</template>

<script>
import Popup from '@/components/Snackbar.vue';
import axios from "axios";
import {eventBus} from '@/event/event';

export default {
  name: "FahrerAnmeldung",
  components: {
    popup: Popup,
  },
  computed: {
    passwordMatch() {
      return () =>
          this.password === this.verify || "Passwort muss übereinstimmen.";
    },
  },
  methods: {
    openSnackbar(message) {
      this.popupData.display = true;
      this.popupData.message = message;
    },
    async validateLogin() {


      //if (this.$refs.loginForm.validate()) {

      //}
    },
    async login() {
      console.log(this.loginEmail)
      const responseGeloescht = await axios.get("Benutzer/checkIfBenutzerIsGeloescht/"+this.loginEmail);
      if(responseGeloescht.data[0]===1)
      {
        this.openSnackbar("Dieses Konto wurde gelöscht.");
        return;
      }
      const responseBlacklist = await axios.get("Benutzer/checkIfBenutzerIsBlacklist/"+this.loginEmail);
      if(responseBlacklist.data.length>0)
      {
        this.openSnackbar("Dieses Konto befindet sich auf der Blacklist wegen "+responseBlacklist.data[0]);
        return;
      }
      this.$http.post('/Login/driver', {
        emailAdresse: this.loginEmail,
        passwort: btoa(this.loginPassword)
      })
          .then((response) => {
            if (response.status === 200) {
              const payload = {
                emailAdresse: response.data.emailAdresse,
                passwort: response.data.passwort
              }
              this.$store.commit('saveLoginData', payload);
              eventBus.$emit('setLogin', payload.emailAdresse);
              this.$router.push({name: "FahrerSchichtplan"});
            }
          }, (error) => {
            if (error.message === 'Request failed with status code 404') {
              this.openSnackbar('Benutzername oder Passwort falsch');
            } else {
              this.openSnackbar(error);
            }
          });
    },
    async validateRegistration() {

      var benutzer = {
        vorname: this.firstName,
        nachname: this.lastName,
        benutzername: this.username,
        emailAdresse: this.email,
        passwort: this.password,
        telefonnummer: this.phoneNumber,
        rolle: "fahrer",
        paypal_Account: "dummy",
        geloescht: 0
      };

      const responseBenutzer = await axios.post("/Benutzer/register", benutzer);

      this.benutzer_ID = responseBenutzer.data.benutzer_ID;

      var fahrer = {
        benutzer_ID: this.benutzer_ID,
        anrede: this.salutation,
        geburtsdatum: this.date,
        fuehrerschein: this.driverLicense,
        ist_in_Pause: 0,
        verifiziert: 0
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

      this.fahrzeug_ID = repsonseFahrzeug.data.fahrzeug_ID;

      var createdFahrzeug = {
        fahrzeug_ID: this.fahrzeug_ID,
        fahrzeugtyp: this.vehicle
      };

      await axios.put("/Fahrer/updateFahrzeugId/" + this.fahrer_ID, createdFahrzeug);

      this.$router.push({name: "FahrerSchichtplan"});

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
      valid: false,
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
      capacity: "",
      verify: "",
      // Login Tab
      loginValid: false,
      loginPassword: "",
      loginEmail: "",
      popupData: {
        display: false,
        message: '',
      },
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
        lettersAndSpacesOnly: (v) => /^[a-zA-ZöäüÖÄÜß ]+$/.test(v) || "Nur Buchstaben und Leerzeichen sind erlaubt",
      },
      dialog: false
    };
  }
  ,
}
;
</script>