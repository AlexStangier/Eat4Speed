<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md3 sm6 xs12>
          <v-card>
            <v-tabs v-model="tab" background-color="primary" dark grow icons-and-text show-arrows>
              <v-tabs-slider color="blue-grey"></v-tabs-slider>
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
                    <v-form ref="registerForm" v-model="valid">
                      <v-row>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="restaurant_name" :rules="[rules.required, rules.lettersAndSpacesOnly]" label="Restaurant-Name"
                                        maxlength="50" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="12" sm="12">
                          <v-text-field v-model="descriptionShort" :rules="[rules.required]" label="Kurzbeschreibung"
                                        maxlength="200" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="firstName" :rules="[rules.required, rules.lettersAndSpacesOnly]" label="Vorname"
                                        maxlength="20" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="lastName" :rules="[rules.required, rules.lettersAndSpacesOnly]" label="Nachname" maxlength="20"
                                        required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="mindestBestellwert" number :rules="[rules.required]"
                                        label="Mindestbestellwert" required></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="bestellradius" number :rules="[rules.required]" label="Bestellradius"
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
                          <v-text-field v-model="postCode" :rules="[rules.required]" label="Postleitzahl" maxlength="20"
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
                                        @click:append="show1 = !show1" required></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-text-field v-model="verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                        :rules="[rules.required, passwordMatch]"
                                        :type="show1 ? 'text' : 'Passwort'" block
                                        counter label="Passwort bestätigen" name="input-10-1"
                                        @click:append="show1 = !show1" required></v-text-field>
                        </v-col>
                        <label>
                          Bild auswählen
                          <input type="file" ref="file" id="fileChange" accept="image/*"
                                 v-on:change="selectedPicture()"/>
                        </label>
                        <v-col cols="12">
                          <v-checkbox
                              label="AGB gelesen und akzeptiert"
                              v-model="agbAccepted"
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
                        <v-spacer></v-spacer>
                        <v-col class="text-right">
                          <v-btn :disabled="!valid"
                                 color="primary"
                                 depressed tile
                                 @click="validate">Registrieren
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
  name: "RestaurantAnmeldung",
  components: {
    popup: Popup,
  },
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  methods: {
    async login() {
      this.$http.post('/Login/restaurant', {
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
              this.$router.push({name: "RestaurantControlPanel"});
            }
          }, (error) => {
            if (error.message === 'Request failed with status code 404') {
              this.openSnackbar('Benutzername oder Passwort falsch');
            } else {
              this.openSnackbar(error);
            }
          });
    },
    selectedPicture() {
      this.restaurantBild = this.$refs.file.files[0];
      console.log(this.restaurantBild);
    },
    async validate() {
      // if (this.$refs.loginForm.validate()) {
      var response = await axios.get("https://api.geoapify.com/v1/geocode/search?text=" + this.houseNumber + "%20" + this.street + "%2C%20" + this.place + "%20" + this.postCode + "%2C%20Germany&apiKey=e15f70e37a39423cbe921dc88a1ded04");

      this.lng = response.data.features[0].geometry.coordinates[0];
      this.lat = response.data.features[0].geometry.coordinates[1];

      if (this.lng > 7.510900 && this.lng < 9.212988 && this.lat > 47.533674 && this.lat < 48.720036) {

        var benutzer = {
          vorname: this.firstName,
          nachname: this.lastName,
          benutzername: this.username,
          emailAdresse: this.email,
          passwort: this.password,
          telefonnummer: this.phoneNumber,
          rolle: "restaurant",
          paypal_Account: this.paypal,
          geloescht: 0
        };

        const responseBenutzer = await axios.post("/Benutzer", benutzer);

        this.benutzer_ID = responseBenutzer.data.benutzer_ID;


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

          console.log(responseEntfernungen.data.sources_to_targets[0][0].distance)
          console.log(responseEntfernungen.data.sources_to_targets[0][0].distance / 1000)

          for (let i = 0; i < responseEntfernungen.data.sources_to_targets[0].length; i++) {
            this.distances[i] = responseEntfernungen.data.sources_to_targets[0][i].distance / 1000
          }
          console.log(this.distances);
        }

        var adressen = {
          strasse: this.street,
          hausnummer: this.houseNumber,
          ort: this.place,
          postleitzahl: this.postCode,
          lng: this.lng,
          lat: this.lat
        };

        const responseAdressen = await axios.post("/Adressen", adressen);

        console.log(responseAdressen);
        console.log(responseAdressen.data);
        console.log(responseAdressen.data.adress_ID);

        this.adress_ID = responseAdressen.data.adress_ID;

        var restaurant = {
          benutzer_ID: this.benutzer_ID,
          name_des_Restaurants: this.restaurant_name,
          allgemeine_Beschreibung: this.descriptionShort,
          anschrift: this.adress_ID,
          verifiziert: 0,
          mindestbestellwert: this.mindestBestellwert,
          bestellradius: this.bestellradius
        };

        var responseRestaurant = await axios.post("/Restaurant", restaurant);

        this.restaurant_ID = responseRestaurant.data.restaurant_ID;

        for (let i = 0; i < this.distances.length; i++) {
          var entfernung = {
            kundennummer: this.kunden_IDs[i],
            restaurant_ID: this.restaurant_ID,
            entfernung: this.distances[i]
          };

          console.log(entfernung);

          await axios.post("/EntfernungKundeRestaurant", entfernung);

        }
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
            this.$router.push({name: "RestaurantControlPanel"});
          })
              .catch(function () {
                console.log('Picture upload error');
              });

          console.log(responsePictureUpload);
        }
      } else {
        this.openSnackbar("Bitte gültige Adresse eingeben!")
      }


      // submit form to server/API here...
      // }
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
  },
  data() {
    return {
      tab: 0,
      tabs: [
        {name: "Login", icon: "mdi-account"},
        {name: "Registrieren", icon: "mdi-account-outline"}
      ],
      valid: true,
      agbAccepted: false,
      dialog: false,
      paypal: "",
      restaurantBild: "",
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
      mindestBestellwert: "",
      bestellradius: "",
      verify: "",
      lng: "",
      lat: "",
      restaurant_ID: "",
      entry: [],
      sources: [],
      targets: [],
      kunden_IDs: [],
      kundenLngs: [],
      kundenLats: [],
      distances: [],
      loginPassword: "",
      loginEmail: "",
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