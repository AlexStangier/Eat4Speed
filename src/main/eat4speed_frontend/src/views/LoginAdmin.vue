<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md3 sm6 xs12>
            <v-card>
              <v-toolbar color="red" dark>
                <v-toolbar-title>Administrations Login</v-toolbar-title>
              </v-toolbar>
              <v-card class="px-4">
                <v-form ref="loginForm">
                  <v-row>
                    <v-col cols="12">
                      <v-text-field label="eMail" required></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="loginPassword" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :type="show1 ? 'text' : 'password'"
                                    label="Passwort" name="input-10-1"
                                    @click:append="show1 = !show1"></v-text-field>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="text-right">
                      <v-btn color="red" dark rounded @click="login">Login</v-btn>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "LoginAdmin",
  methods: {
    login() {
      // submit form to server/API here...
    },
  },
  data() {
    return {
      show1: false,
      loginPassword: "",
      loginEmail: ""
    }
  }
};
</script>