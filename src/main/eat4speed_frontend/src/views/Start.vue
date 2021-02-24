<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md3 sm6 xs12>
                  <v-card class="px-4">
                    <v-card-text>
                      <v-form ref="loginForm" v-model="valid" lazy-validation>
                        <v-row>
                          <v-col cols="30">
                            <v-text-field  label="Suche..."
                                           required
                                           v-model="search"
                                           append-icon="mdi-magnify"
                                           single-line
                                           hide-details
                            >
                            </v-text-field>
                          </v-col>
                          <v-btn :disabled="!valid" color="red" dark rounded elevation="15"
                                 @click="validate">Los</v-btn>
                        </v-row>
                        <v-row>
                          <v-btn :disabled="!valid" color="red" dark rounded elevation="15"
                                 @click="validate">Gericht</v-btn>
                          <v-btn :disabled="!valid" color="red" dark rounded elevation="15"
                                 @click="validate">Umgebung</v-btn>
                        </v-row>
                      </v-form>
                    </v-card-text>
                  </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "LoginCustomer",
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Passwort muss übereinstimmen.";
    }
  },
  methods: {
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