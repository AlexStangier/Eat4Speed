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
              :disabled="item.hideButton"
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
import Vue from "vue";

export default {
  name: "FahrerFahrtenplan",
  components: {
    DirectionsRenderer
  },
  methods: {
    async abholungBestätigen(id) {
      Vue.set(id, 'hideButton', true);
      if(id.beschreibung === 'Abholung'){
        await this.$http.get('/route/confirm/' + id.beschreibung + '?auftraege=' + id.auftrags_id + '&data=' + id.restaurantname + '&email=' + this.$store.getters.getLoginData.auth.username);
      }
      else{
        await this.$http.get('/route/confirm/' + id.beschreibung + '?auftraege=' + id.auftrags_id + '&email='+ this.$store.getters.getLoginData.auth.username);
      }

      id.hidebutton=true;
    },
    getTermin() {
      return '14.06.2021';
    },
    getStationen() {
      return this.data.length;
    },
    convertToKM(meters) {
      return meters / 1000;
    },

  },
  async mounted() {
      await this.$http.get('/route/calculate/' + this.$store.getters.getLoginData.auth.username).then(response => this.data = response.data);

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
          text: 'Auftrags_ID',
          value: 'auftrags_id',
          sortable: false
        },
        {
          text: 'Beschreibung',
          value: 'beschreibung',
          sortable: false
        },
        {
          text: 'Kundenanschrift',
          value: 'kunde',
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
          sortable: false,
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
