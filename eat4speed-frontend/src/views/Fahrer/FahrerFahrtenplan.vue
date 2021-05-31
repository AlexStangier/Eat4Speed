<template>
  <v-main>
    <h1 class="subheading">Fahrer Fahrtenplan</h1>
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
              color="blue"
              dark
              rounded
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

export default {
  name: "FahrerFahrtenplan",
  components: {
    DirectionsRenderer
  },
  methods: {
    abholungBestätigen(id) {
      id;
    },
    getTermin() {
      return '12.12.2021';
    },
    getStationen() {
      return 2;
    },
    convertToKM(meters) {
      return meters / 1000;
    },
  },
  mounted() {
    this.data = [
      {
        station: "1",
        beschreibung: "Kurzbeschreibung 1",
        restaurantname: "Restaurant 1",
        entfernung: "1 km",
        start: {lat: 36.85, lng: -87.65},
        end: {lat: 45.85, lng: -87.65}
      },
      {
        station: "2",
        beschreibung: "Kurzbeschreibung 1",
        entfernung: "2 km",
        start: {lat: 38.85, lng: -87.65},
        end: {lat: 45.85, lng: -87.65}
      }
    ]
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
