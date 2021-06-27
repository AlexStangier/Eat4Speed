<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md3 sm6 xs12>
          <v-card>
            <v-toolbar color="primary" dark>
              <v-toolbar-title>Administrations Login</v-toolbar-title>
            </v-toolbar>
            <v-card class="pa-4">
              <v-form ref="loginForm">
                <v-row>
                  <v-col cols="12">
                    <v-text-field v-model="loginEmail" label="eMail" required></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field v-model="loginPassword" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                  :type="show1 ? 'text' : 'password'"
                                  label="Passwort" name="input-10-1"
                                  @click:append="show1 = !show1"></v-text-field>
                  </v-col>
                  <v-spacer></v-spacer>
                  <v-col class="text-right">
                    <v-btn color="primary" depressed tile @click="login">Login</v-btn>
                  </v-col>
                </v-row>
              </v-form>
            </v-card>
          </v-card>
        </v-flex>
      </v-layout>
      <popup :popupData="popupData" ></popup>
    </v-container>
  </v-main>
</template>

<script>
import Popup from '@/components/Snackbar.vue';
import { eventBus } from '@/event/event';

export default {
  name: "AdminAnmeldung",
  components: {
    popup: Popup,
  },
  methods: {
    login() {
      this.$http.post('/Login/admin', {
        emailAdresse: this.loginEmail,
        passwort: btoa(this.loginPassword)
      }, this.$store.getters.getLoginData)
      .then((response) => {
        if (response.status === 200) {
          const payload = {
            emailAdresse: response.data.emailAdresse,
            passwort: response.data.passwort
          }
          this.$store.commit('saveLoginData', payload);
          eventBus.$emit('setLogin', payload.emailAdresse);
          this.$router.push({ name: "AdminVerifizierungFahrer"})
        }
      }, (error) => {
        if (error.message === 'Request failed with status code 404') {
          this.openSnackbar('Benutzername oder Passwort falsch');
        } else {
          this.openSnackbar(error);
        }
      });
    },
    openSnackbar(message) {
      this.popupData.display = true;
      this.popupData.message = message;
    },
  },
  data() {
    return {
      show1: false,
      loginPassword: "",
      loginEmail: "",
      popupData: {
        display: false,
        message: '',
      }
    }
  }
};
</script>