<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <v-card
              class="mx-auto overflow-hidden"
              height="800"
          >
            <v-app-bar
                color="red"
                dark
            >
              <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
              <v-toolbar-title>ControlPanel</v-toolbar-title>
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
                  <v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-home</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>ControlPanel</v-list-item-title>
                  </v-list-item>
                  <router-link  to="/restaurant/speisekarteGerichte"><v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-silverware</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Speise Karte bearbeiten</v-list-item-title>
                  </v-list-item>
                  </router-link>
                  <router-link  to="/restaurant-bestellungen"><v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-view-headline</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Offene Bestellungen</v-list-item-title>
                  </v-list-item>
                  </router-link>
                  <router-link to="/schichtplan">
                    <v-list-item>
                      <v-list-item-icon>
                        <v-icon>mdi-watch</v-icon>
                      </v-list-item-icon>
                      <v-list-item-title>Schichtplan</v-list-item-title>
                    </v-list-item>
                  </router-link>
                  <router-link  to="/restaurant-stammdaten"><v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-account</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Stammdaten</v-list-item-title>
                  </v-list-item>
                  </router-link>
                  <v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-margin</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Umsatzstatistik</v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-icon>
                      <v-icon>mdi-exclamation </v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>Neue Bestellungen</v-list-item-title>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-navigation-drawer>
            <v-sheet class="pa-5">
              <v-switch
                  v-model="switch1"
                  inset
                  :label="`Restaurant: ${switch1.toString()}`"
              ></v-switch>
            </v-sheet>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "RestaurantControlPanel",
  data: () => ({
    drawer: false,
    group: null,
    switch1: true,
  }),
}
</script>