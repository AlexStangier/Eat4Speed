
<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <v-card
              class="mx-auto overflow-hidden"
              height="800"
          >



            <v-col align="center" justify="center">
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/speisekarteGerichte">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Spese Karte bearbeiten
                  </v-btn>
                </router-link>
              </v-row>
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/bestellungen">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Offene Bestellungen
                  </v-btn>
                </router-link>
              </v-row>
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/schichtplan">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Schichtplan
                  </v-btn>
                </router-link>
              </v-row>
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/stammdaten">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Stammdaten
                  </v-btn>
                </router-link>
              </v-row>
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/umsatzstatistik">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Umsatzstatistik
                  </v-btn>
                </router-link>
              </v-row>
              <v-row align="center" justify="center">
                <router-link  to="/restaurant/umsatzstatistik">
                  <v-btn class="mt-2"   width="300px" color="blue"
                  >Neue Bestellungen
                  </v-btn>
                </router-link>
              </v-row>

            </v-col>
          </v-card>

        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>




<script>
export default {
  data: () => ({
    selectedItem: 1,
    items: [
      { text: 'Real-Time', icon: 'mdi-clock' },
      { text: 'Audience', icon: 'mdi-account' },
      { text: 'Conversions', icon: 'mdi-flag' },
    ],
  }),
}
</script>