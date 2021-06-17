<template>
  <v-main>
    <v-container>
      <v-card
          class="mx-auto"
          tile
      >
        <v-row no-gutters>
          <v-col
              v-for="a in 3"
              :key="a"
          >
            <v-card
                v-if="a === 1"
                class="text-sm-h4 pa-5"
                flat
            >
              Bestellhistorie
            </v-card>
            <v-card
                v-if="a === 2"
                class="ml-1 pa-2"
                flat
            >
              <v-menu
                  v-model="dateMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  max-width="290px"
                  min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="computedDateFormatted"
                      label="Bestellt am"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                    v-model="date"
                    no-title
                    @input="dateMenu = false"
                    locale="de-DE"
                    :first-day-of-week="1"
                ></v-date-picker>
              </v-menu>
            </v-card>
            <v-card
                v-if="a === 3"
                class="ml-1 pa-2"
                flat
            >
              <v-switch
                  label="Abgeschlossene Bestellungen"
                  v-model="abgeschlosseneBestellungen"
              ></v-switch>
            </v-card>
          </v-col>
        </v-row>
        <v-card-title>
          <v-row>
            <v-col>
              <v-btn
                  depressed
                  :color="btnType === 0 ? 'primary' : 'blue-grey'"
                  class="mt-2 white--text"
                  width="150"
                  tile
                  @click="changeDisplayGerichte"
              >
                Speisen
              </v-btn>
              <v-btn
                  depressed
                  :color="btnType === 1 ? 'primary' : 'blue-grey'"
                  tile
                  class="mt-2 ml-2 white--text"
                  width="150"
                  @click="changeDisplayRestaurants"
              >
                Restaurants
              </v-btn>
            </v-col>
          </v-row>
        </v-card-title>
        <v-divider></v-divider>
        <v-virtual-scroll
            :items="items"
            :item-height="200"
            max-height="500"
        >
          <template v-slot:default="{ item }">
            <v-card
                flat
                tile
            >
              <v-container>
                <v-row>
                  <v-col
                      cols="4"
                  >
                    <v-card
                        flat
                        tile
                        class="center"
                    >
                      <v-img alt="Bild von Essen" min-height="180" max-height="180" max-width="400" :src="item.img"></v-img>
                    </v-card>
                  </v-col>
                  <v-col>
                    <v-row
                        v-for="d in 4"
                        :key="d"
                    >
                      <v-col>
                        <v-card
                            v-if="d === 1"
                            class="text-h5 text-decoration-underline"
                            flat
                        >
                          {{ item.name }}
                        </v-card>
                        <v-card
                            v-if="d === 1"
                            class="text-subtitle-1"
                            flat
                        >
                          {{ item.description }}
                        </v-card>
                        <v-card
                            v-if="d === 2"
                            class="text-subtitle-1"
                            flat
                        >
                          {{ item.restaurant }}
                        </v-card>
                      </v-col>
                      <v-col>
                        <v-card
                            v-if="d === 2"
                            class="text-right"
                            flat
                        >
                          {{'Zuletzt bestellt am: ' + item.date }}
                        </v-card>
                        <v-card
                            v-if="d === 2 && displayGerichte"
                            class="text-right"
                            flat
                        >
                          {{'Stückpreis: ' + item.price + ' €' }}
                        </v-card>
                        <v-card
                            v-if="d === 4 && displayGerichte"
                            class="text-right"
                            flat
                        >
                          <v-btn
                              small="true"
                              bottom="bottom"
                              color="primary"
                              tile
                              :to="{name: 'Gericht'}"
                              @mouseover="selectGericht(item)"
                          >
                            Zum Gericht
                          </v-btn>

                          <v-menu
                              bottom
                              left
                              offset-y
                              :close-on-content-click="false"
                          >
                            <template v-slot:activator="{ on, attrs}">
                              <v-btn
                                  v-bind="attrs"
                                  v-on="on"
                                  small="true"
                                  bottom="bottom"
                                  class="ml-1"
                                  color="primary"
                                  tile
                                  @mouseover="selectItem(item)"
                                  @click="gerichtAnzahl=1"
                              >
                                Nochmal bestellen
                              </v-btn>
                            </template>
                            <v-list
                                max-width="200"
                                min-width="250"
                                class="text-center"
                            >
                              <v-list-item>
                                <v-text-field label="Anzahl" v-model="gerichtAnzahl" type="number" :rules="countMinMaxRule"></v-text-field> <!--TODO-->
                              </v-list-item>
                              <v-btn
                                  @click="addToCart()"
                                  small="small"
                                  color="primary"
                                  tile
                              >
                                Zum Warenkorb hinzufügen
                              </v-btn>
                            </v-list>
                          </v-menu>
                        </v-card>
                        <v-card
                            v-if="d === 2 && !displayGerichte"
                            class="text-right"
                            flat
                        >
                          <v-btn
                              small="true"
                              bottom="bottom"
                              color="primary"
                              tile
                              :to="{name: 'KundeAuswahlseiteRestaurant'}"
                              @mouseover="selectGericht(item)"
                          >
                            Zum Restaurant
                          </v-btn>
                        </v-card>
                      </v-col>
                    </v-row>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
            <v-divider></v-divider>

          </template>
        </v-virtual-scroll>
      </v-card>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "KundeBestellhistorie",
  mounted() {
    this.displayGerichte = false;
  },
  methods: {
    changeDisplayRestaurants()
    {
      if(this.displayGerichte===false)
      {
        this.btnType = 1;
        this.displayGerichte = !this.displayGerichte;
      }

    },
    changeDisplayGerichte(){
      if(this.displayGerichte===true)
      {
        this.btnType = 0;
        this.displayGerichte = !this.displayGerichte;
      }

    },
    formatDate (date) {
      if (!date) return null

      const [year, month, day] = date.split('-')
      return `${day}.${month}.${year}`
    },
    parseDate (date) {
      if (!date) return null

      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    },

  },
  data: vm => ({
    date: new Date().toISOString().substr(0, 10),
    dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
    dateMenu: false,
    displayGerichte:"",
    btnType: 0,
    abgeschlosseneBestellungen: false,
  }),

  computed: {
    computedDateFormatted () {
      return this.formatDate(this.date)
    },
  },
}
</script>

<style scoped>

</style>