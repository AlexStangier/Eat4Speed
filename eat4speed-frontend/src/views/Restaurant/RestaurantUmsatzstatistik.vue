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
              <v-menu
                  v-model="menu2"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="dates"
                      label="Datum"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
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
import axios from "axios";

Vue.use(Chartkick.use(Chart));
export default {
  mounted() {
    this.restaurantID = 5;
    this.start= 0;
    this.end= 2922463867000;
    this.info={
      restaurantId: 5,
      start: 0,
      end: 1922463867000
    },
        this.loadZeiten()
  },

  data () {
    return{
      drawer: false,
      group: null,
      switch1: true,
      menu2: false,
      info:{
        restaurantId: 5,
        start: 0,
        end: 1922463867000
      },
      value:[ ],
      dates : ['2021-06-05', '2021-06-09'],
      iteratedDates :{}
    };
  },
  methods:{
    async loadZeiten() {

      this.info.end=moment().unix()*1000;
      //console.log(this.info.end);

      const ResponseUmsatz = await axios.post("Bestellung/getStatistic/", this.info);
     // console.log("1");
      //let myData=ResponseUmsatz.data.data;
      //console.log(ResponseUmsatz.data.data.length);
      var o=0;
      while(o<10) {
        //var dummy4 = moment.unix(ResponseUmsatz.data.data[o].timestamp / 1000).format("MM.DD.YYYY");
       // console.log(dummy4);
       // console.log(ResponseUmsatz.data.data[o].value);
        //console.log("dummy4");
        o++;
      }
      //console.log(myData);
     // console.log("2");
      var i=0;
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



      var date = moment(date0, "MMDDYYYY").format("MM.DD.YYYY");
      while (i<ResponseUmsatz.data.data.length)
      {
        var dummy=moment.unix(ResponseUmsatz.data.data[i].timestamp/1000).format("MM.DD.YYYY");
        //console.log(dummy);

        if(dummy>=date)
          break;

        i++;
      }
      var j=0;
      date = moment(date1, "MMDDYYYY").format("MM.DD.YYYY");
      while (j<ResponseUmsatz.data.data.length)
      {
        var dummy2=moment.unix(ResponseUmsatz.data.data[j].timestamp/1000).format("MM.DD.YYYY");

        if(dummy2>=date)
          break;
        j++;
        //console.log(j);
      }

      for(var k=i;k<=j;k++)
      {
        if(k>=ResponseUmsatz.data.data.length)
          break;
        var dummy3=moment.unix(ResponseUmsatz.data.data[k].timestamp/1000).format("MM.DD.YYYY");
        var v=0;
        while(k<ResponseUmsatz.data.data.length && dummy3===moment.unix(ResponseUmsatz.data.data[k].timestamp/1000).format("MM.DD.YYYY"))
        {
          v=v+ResponseUmsatz.data.data[k].value;
          k++;
         // console.log(k);
        }
        k--;
        if(dummy3<=date)
          this.iteratedDates[dummy3] = v.toFixed(2)+"€";
      }
      for (var d = date0; d <= date1; d.setDate(d.getDate() + 1)){
        const date4 = moment(d, "MMDDYYYY").format("MM.DD.YYYY");
       // console.log(d);
       // console.log(this.iteratedDates[d]);
        if(this.iteratedDates[date4]===undefined)
          this.iteratedDates[date4]=0;
      }



    },



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
      // console.log(this.iteratedDates);

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