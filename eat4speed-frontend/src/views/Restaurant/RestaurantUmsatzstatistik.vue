<template>
  <v-main>

    <v-app-bar color="blue"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Umsatzstatistik</v-toolbar-title>
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



          <v-row>
            <v-col
                cols="12"
                sm="6"
            >
              <v-date-picker
                  v-model="dates"
                  range
                  @change="iterateDate"
              ></v-date-picker>
            </v-col>
            <v-col
                cols="12"
                sm="6"
            >
              <v-text-field
                  v-model="dateRangeText"
                  label="Date range"
                  prepend-icon="mdi-calendar"
                  readonly
              ></v-text-field>
              model: {{ dates }}
            </v-col>
          </v-row>








          <line-chart :data="iteratedDates"></line-chart>






        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import moment from 'moment';
import Vue from 'vue';
import Chartkick from 'vue-chartkick';
import Chart from 'chart.js';

Vue.use(Chartkick.use(Chart));
export default {

  data () {
    return{
      drawer: false,
      group: null,
      switch1: true,

      value:[ ],
      dates : ['2021-09-10', '2021-09-20'],
      iteratedDates :{}
    };
  },
  methods:{
    async iterateDate() {
      this.iteratedDates={};
      this.value=[];
      var date0;
      var date1;
      if (this.dates[0] > this.dates[1]) {
        date0 = new Date(this.dates[1]);
        date1 = new Date(this.dates[0]);
      } else {
        date1 = new Date(this.dates[1]);
        date0 = new Date(this.dates[0]);
      }


      //var datesList = [];
      for (var d = date0; d <= date1; d.setDate(d.getDate() + 1)) {
        const date = moment(d, "YYYYMMDD").format("YYYY-MM-DD");
        // this.iteratedDates.push(date.toString());
        this.iteratedDates[date] = Math.random()+1;
        // this.iteratedDates.push(date.toString(),Math.random());
        //this.value.push(Math.random());
      }
      console.log(this.iteratedDates);

    }
  },
  computed: {
    dateRangeText () {
      return this.dates.join(' ~ ')
    },

  },
}
</script>

<style scoped>
.v-sheet--offset {
  top: -24px;
  position: relative;
}
</style>