<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout justify-center>
          <v-flex md6 sm6 xs12>

            <v-img
                class="justify-center"
                lazy-src="https://i.imgur.com/onX9Kvo.png"
                src="https://i.imgur.com/onX9Kvo.png"
            ></v-img>
                        <v-row>

                          <v-col>

                            <v-text-field  label="Suche nach Gericht"
                                           ref="Suchfeld"
                                           required
                                           v-model="search"
                                           append-icon="mdi-magnify"
                                            single-line
                                           hide-details
                            >
                            </v-text-field>
                          </v-col>
                          <v-btn :disabled="!valid" to="/Customer" color="red" dark rounded elevation="15" align="bottom"
                                 @click="validate">Los</v-btn>
                        </v-row>
                        <v-row
                            style="height: 200px;"
                        >
                          <v-col>
                            <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200"
                                 @click="gerichtFarbe">Gericht</v-btn>
                            <v-btn :disabled="!valid" ref="UmgebungButton" color="red" dark rounded elevation="15" width="200"
                                 @click="umbegungFarbe">Umgebung</v-btn>
                          </v-col>

                        </v-row>
            <!--Anmelde Dialog-->
            <v-dialog
                v-model="dialog"
                width="500"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="red"
                    dark
                    v-bind="attrs"
                    v-on="on"
                >
                  Anmelden
                </v-btn>
              </template>

              <v-card class="pa-10">
                <v-card-title class="d-flex justify-center">
                  Anmeldung
                </v-card-title>
                <v-spacer class="pb-10"></v-spacer>
                <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200" to="/login-customer"
                       @click="gerichtFarbe">Kunde</v-btn>
                <v-spacer class="pb-10"></v-spacer>
                <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200" to="/login-restaurant"
                       @click="gerichtFarbe">Restaurant</v-btn>
                <v-spacer class="pb-10"></v-spacer>
                <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200" to="/login-driver"
                       @click="gerichtFarbe">Fahrer</v-btn>
              </v-card>
            </v-dialog>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'Start',

  methods: {
    openLogin(){
      this.$refs.Anmeldung.class="px-4 d-flex"
    },
    gerichtFarbe(){
      this.$refs.GerichtButton.color = "blue"
      this.$refs.UmgebungButton.color = "red"
      this.$refs.Suchfeld.label = "Suche nach Gericht"
    },
    umbegungFarbe(){
      this.$refs.UmgebungButton.color = "blue"
      this.$refs.GerichtButton.color = "red"
      this.$refs.Suchfeld.label = "Suche nach Umgebung"
    },
    validate() {
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

      valid: true,
      salutation: "",
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