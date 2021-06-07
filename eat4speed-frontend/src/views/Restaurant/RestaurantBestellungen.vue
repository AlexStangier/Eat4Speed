<template>
  <v-main>

    <v-app-bar color="blue"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Bestellungen</v-toolbar-title>
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
            <v-list-item-title>ControlPanel</v-list-item-title>
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
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Offene Bestellungen (verschwinden wenn Kunde sie erhält)</h1>
          <v-list>

            <v-list-item class="mb-12">
              <v-card class="mr-10" color="red" dark>
              <v-col >
                <v-card-title>Bestellung 1 (Name des kunden?)</v-card-title>
                <v-card-text>- Pizza Mozarella</v-card-text>
                <v-card-text>- Cola groß</v-card-text>

              </v-col>
              </v-card>
              <v-slider
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
                <template v-slot:thumb-label="{ value }">
                  {{ bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>
            <v-list-item class="mb-12">
              <v-card class="mr-10" color="red" dark>
                <v-col>
                  <v-card-title>Bestellung 2 (Name des kunden?)</v-card-title>
                  <v-card-text>- Calzone</v-card-text>
                  <v-card-text>- Cola groß</v-card-text>

                </v-col>
              </v-card>
              <v-slider
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
                <template v-slot:thumb-label="{ value }">
                  {{ bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>

          </v-list>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "RestaurantBestellungen",
  data () {
    return {
      drawer: false,
      group: null,
      switch1: true,
      bestellstati: ['Bereit', 'In Zubereitung', 'Abholbereit'],
      bestellstatifarben: ['red', 'yellow', 'green'],
      bestellungen: [
        {
          name: 'Bestellung 1',
          offen: true,
          inZubereitung: true,
          abholBereit: false,
        },
        {
          name: 'Bestellung 2',
          offen:  true,
          inZubereitung: false,
          abholBereit: false,
        },

      ],
    }
  },
}
</script>

<style scoped>

</style>