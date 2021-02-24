<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>
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
                            :align="align"
                            style="height: 300px;"
                        >
                          <v-col>
                            <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200"
                                 @click="gerichtFarbe">Gericht</v-btn>
                            <v-btn :disabled="!valid" ref="UmgebungButton" color="red" dark rounded elevation="15" width="200"
                                 @click="umbegungFarbe">Umgebung</v-btn>
                          </v-col>

                        </v-row>
            <v-btn :disabled="!valid" color="red" dark rounded elevation="15" to="/login-customer">Anmeldung</v-btn>

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