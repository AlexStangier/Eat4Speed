<template>
  <v-main>
    <v-card class="mx-5 my-5">
      <v-data-table
          :headers="headers"
          :items="data"
          :items-per-page="10"
          :single-select="false"
          class="elevation-1 pa-6"
          item-key="name"
          multi-sort
      >
        <template v-slot:top>
          <v-container fluid>
            <v-col cols="6">
              <v-row>
                Liefertermin: {{ getTermin() }}
              </v-row>
              <v-row>
                restliche Stationen: {{ getStationen() }}
              </v-row>
            </v-col>
          </v-container>
        </template>

        <template v-slot:item.map="{ item }">
          <div class="pa-2">
            <GmapMap
                :center="{ lat: 47.98, lng: 7.89 }"
                :options="{
                zoomControl: true,
                mapTypeControl: false,
                scaleControl: false,
                streetViewControl: false,
                rotateControl: false,
                fullscreenControl: true,
                disableDefaultUi: false
                }"
              :zoom="7"
            >
              <DirectionsRenderer :destination="item.end" :origin="item.start" travelMode="DRIVING"/>
            </GmapMap>
          </div>
        </template>

        <template v-slot:item.actions="{ item }">
          <v-btn
              color="primary"
              depressed
              tile
              @click="abholungBestätigen(item)"
          >Bestätigen
          </v-btn>
        </template>
      </v-data-table>
    </v-card>
  </v-main>
</template>

<script>
import DirectionsRenderer from "@/utils/DirectionsRenderer";
import axios from "axios";

export default {
  name: "FahrerFahrtenplan",
  components: {
    DirectionsRenderer
  },
  methods: {
    async get_user(){
      {
        //const ResponseEinstellungen = await axios.get("Benutzer/getBenutzerKundeEinstellungenByLogin/" + this.$store.getters.getLoginData.auth.username);
        //let EinstellungenData = ResponseEinstellungen.data[0];

        console.log(this.$store.getters.getLoginData.auth.username);
      }
    },
    abholungBestätigen(id) {
      id;
    },
    getTermin() {
      return '31.05.2021';
    },
    getStationen() {
      return 7;
    },
    convertToKM(meters) {
      return meters / 1000;
    },
  },
  async mounted() {
      await this.$http.get('/route/' + this.$store.getters.getLoginData.auth.username).then(response => this.data = response.data);
      await this.get_user();
    },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      headers: [
        {
          text: 'Karte',
          align: 'start',
          sortable: false,
          value: 'map'
        },
        {
          text: 'Station',
          value: 'station',
          sortable: false
        },
        {
          text: 'Beschreibung',
          value: 'beschreibung',
          sortable: false
        },
        {
          text: 'Restaurantname',
          value: 'restaurantname',
          sortable: false
        },
        {
          text: 'Entfernung',
          value: 'entfernung',
          sortable: false
        },
        {
          value: 'actions',
          sortable: false,
          align: 'end'
        },
        {
          value: 'count_stations',
          sortable: false
        },
      ],
    };
  },
};
</script>

<style>
.vue-map-container {
  height: 300px;
  width: 300px;
}
</style>
