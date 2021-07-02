<template>
  <v-main>
    <v-card class="mx-5 my-5">
      <v-app-bar color="primary" dark>
        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
        <v-toolbar-title>Umsatzstatistik</v-toolbar-title>
      </v-app-bar>
      <v-navigation-drawer
          v-model="drawer"
          absolute
          temporary
      >
        <v-list
            dense
            nav
        >
          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <router-link to="/restaurant/controlpanel">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-home</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Startseite</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/speisekarteGerichte">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-silverware</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/bestellungen">
              <v-list-item>
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
            <router-link to="/restaurant/stammdaten">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-account</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Stammdaten</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/umsatzstatistik">
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


            <v-row>
              <v-col
                  cols="12"
                  sm="6"
              >
                <v-menu
                    v-model="menu2"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    min-width="auto"
                    offset-y
                    transition="scale-transition"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="dateRangeText"
                        v-bind="attrs"
                        v-on="on"
                        label="Datum"
                        prepend-icon="mdi-calendar"
                        readonly
                    ></v-text-field>
                  </template>
                  <v-date-picker
                      v-model="dates"
                      range
                      @change="loadZeiten"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
              >


              </v-col>
            </v-row>
            <area-chart :data="iteratedDates" decimal="," :round="2" :zeros="true" download="umsatzstatistik"></area-chart>
          </v-flex>
        </v-layout>
      </v-container>
      <v-layout class="pb-5" justify-center>
        <v-flex md6 sm12 xs12>
          <v-data-table
              :key="componentKey"
              :headers="headers"
              :items="iteratedDatesTable"
              :sort-by="['time']"
              :sort-desc="[false]"
              class="elevation-1"
              dense
              item-key="name"
          ></v-data-table>
        </v-flex>
      </v-layout>

    </v-card>
  </v-main>
</template>

<script>
import moment from 'moment';
import Vue from 'vue';
import Chartkick from 'vue-chartkick';
import Chart from 'chart.js';
import axios from "axios";

Vue.use(Chartkick.use(Chart));
export default {
  async mounted() {
    await this.getLoggedInRestaurant();
    await this.loadZeiten();
  },
  data() {
    return {
      drawer: false,
      group: null,
      switch1: true,
      menu2: false,
      info: {
        restaurantId: 5,
        start: 0,
        end: 1922463867000
      },
      value: [],
      dates: [moment().subtract(7, 'days').format("YYYY-MM-DD"), moment().format("YYYY-MM-DD")],
      iteratedDates: {},
      iteratedDatesTable: [],
      componentKey: 0,
      headers: [
        {
          text: 'Datum',
          align: 'start',
          value: 'time',
        },
        {text: 'Einnahmen', value: 'value'}
      ],
    };
  },
  methods: {
    forceRerender() {
      this.componentKey += 1;
    },
    async getLoggedInRestaurant() {
      const response = await axios.get("Benutzer/getRestaurant_IDByBenutzername/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      this.info.restaurantId = response.data[0];
    },
    async loadZeiten() {
      this.info.start = moment(this.dates[0]).unix() * 1000;
      this.info.end = moment(this.dates[1]).add(1, 'days').unix() * 1000;

      const ResponseUmsatz = await axios.post("Bestellung/getStatistic/", this.info, this.$store.getters.getLoginData);
      let formattedData = {};
      let formattedDataTable = [];
      let found = false;

      ResponseUmsatz.data.data.forEach(data => {
        const date = moment(data.timestamp).format("YYYY-MM-DD");

        Object.entries(formattedData).forEach(([key]) => {
          if (key === date) {
            formattedData[key] = this.roundToTwo(parseFloat(data.value) + parseFloat(formattedData[key]));
            found = true;
          }
        });

        if (!found) {
          formattedData[date] = parseFloat(data.value).toFixed(2);
        }

        formattedDataTable.push({
          time: moment(data.timestamp).format("LL"),
          value: parseFloat(data.value).toFixed(2)
        });

        found = false;
      });

      this.iteratedDates = formattedData;
      this.iteratedDatesTable = formattedDataTable;
    },
    roundToTwo(num) {
      return (Math.round(num + "e+2")  + "e-2");
    },
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ');
    },
  },
}
</script>
