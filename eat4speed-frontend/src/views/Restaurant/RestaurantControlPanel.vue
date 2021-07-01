<template>
  <v-main>
    <v-card class="mx-5 my-5">
    <v-app-bar color="primary"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Startseite</v-toolbar-title>
    </v-app-bar>
    <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            active-class="deep-purple--text text--accent-4"
        >
          <router-link  to="/restaurant/controlpanel">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Startseite</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/speisekarteGerichte"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-silverware</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/bestellungen"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-view-headline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Offene Bestellungen</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link to="/restaurant/schichtplan">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-watch</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Schichtplan</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link  to="/restaurant/stammdaten"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Stammdaten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/umsatzstatistik">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-margin</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Umsatzstatistik</v-list-item-title>
          </v-list-item>
          </router-link>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-container fill-height fluid>

      <v-layout  justify-center>

        <v-flex md12 sm6 xs12>




          <div  class="d-flex flex-column justify-space-between align-center pa-4" b="100px">
            <v-img
                class="justify-center"
                max-height="50%"
                max-width="50%"
                src="@/assets/logo.png"
            ></v-img>

          </div>


          <v-container  fluid  class="pa-2   d-flex flex-wrap flex-column flex-row justify-space-between align-center "  width="20000px">
          <v-row width="2000"  >

              <v-col cols="12" md="4" sm="4" class="flex-grow-1" width="200px" height="200px">
                <div class="pa-2 " width="200px" height="200px"><router-link width="200px" height="200px"  to="/restaurant/speisekarteGerichte">
                <v-card color="primary" width="200px" height="200px">

                  <v-card-text class="white--text" align="center">
                  Speisekarte bearbeiten
                  </v-card-text>
                 <div align="center">
                  <v-icon color="white" align="center" size="100px" class="justify-center">mdi-silverware</v-icon>
                 </div>
                </v-card>
                </router-link></div>
              </v-col>


              <v-col cols="12" md="4" sm="4" width="200px" height="200px">
                <div class="pa-2"><router-link  to="/restaurant/bestellungen">
                  <v-card color="primary" width="200px" height="200px">

                    <v-card-text class="white--text" align="center">
                  Offene Bestellungen
                    </v-card-text>
                    <div align="center">
                      <v-icon color="white" align="center" size="100px" class="justify-center">mdi-view-headline</v-icon>
                    </div>
                  </v-card>
                </router-link></div>
              </v-col>


              <v-col cols="12" md="4" sm="4" width="200px" height="200px">
                <div class="pa-2"><router-link  to="/restaurant/schichtplan">
                  <v-card color="primary" width="200px" height="200px">

                    <v-card-text class="white--text" align="center">
                      Schichtplan
                    </v-card-text>
                    <div align="center">
                      <v-icon color="white" align="center" size="100px" class="justify-center">mdi-watch</v-icon>
                    </div>
                  </v-card>
                </router-link>
                </div>
              </v-col>
          </v-row>
          <v-row >

              <v-col cols="12" md="6" sm="6">
                <div class="pa-2">
                <router-link  to="/restaurant/stammdaten">
                  <v-card color="primary" width="200px" height="200px">

                    <v-card-text class="white--text" align="center">
                  Stammdaten
                    </v-card-text>
                      <div align="center">
                        <v-icon color="white" align="center" size="100px" class="justify-center">mdi-account</v-icon>
                      </div>
                  </v-card>
                </router-link>
                </div>
              </v-col>
              <v-col cols="12" md="6" sm="6">
                <div class="pa-2">
                <router-link  to="/restaurant/umsatzstatistik">
                  <v-card color="primary" width="200px" height="200px">

                    <v-card-text class="white--text" align="center">
                  Umsatzstatistik
                    </v-card-text>
                    <div align="center">
                      <v-icon color="white" align="center" size="100px" class="justify-center">mdi-margin</v-icon>
                    </div>
                  </v-card>
                </router-link>
                </div>
              </v-col>

          </v-row>
          </v-container>


        </v-flex>
      </v-layout>
    </v-container>
    </v-card>
  </v-main>
</template>




<script>
import axios from "axios";

export default {
  async mounted() {
    let response;
    try {
      response = await axios.get("Benutzer/getRestaurant_IDByBenutzername/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
    }
    catch (e)
    {
      if(e.response.status === 403 || e.response.status === 401)
      {
        window.location.reload();
      }
    }
    this.id = response.data[0];
  },
  data: () => ({
    drawer: false,
    group: null,
    id: 0,
    switch1: true,
    selectedItem: 1,
    items: [
      { text: 'Real-Time', icon: 'mdi-clock' },
      { text: 'Audience', icon: 'mdi-account' },
      { text: 'Conversions', icon: 'mdi-flag' },
    ],
  }),
}
</script>
<style>
div a {
  text-decoration: none;
}
</style>