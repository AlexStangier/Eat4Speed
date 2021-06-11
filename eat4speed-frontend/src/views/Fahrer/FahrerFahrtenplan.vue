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
    async getBenachrichtigung()
    {
      const responseBenachrichtigung = await axios.get("Benachrichtigung_Fahrer/getAllBenachrichtigungFahrerUngelesen/"+this.fahrernummer);
      if(responseBenachrichtigung.data.length>0)
      {
        this.benachrichtigungs_ID = responseBenachrichtigung.data[0][0];

        let index = this.auftrags_IDs.length;
        this.auftrags_IDs[index] = responseBenachrichtigung.data[0][1];

        await axios.put("Benachrichtigung_Fahrer/markAsGelesen/"+this.benachrichtigungs_ID);
      }
    },
    async setAuftragFahrernummer()
    {
      const response = await axios.get("Auftrag/getAuftragFahrernummerByAuftrags_ID/"+this.auftrags_IDs[this.auftrags_IDs_index])
      if(response.data[0]===null)
      {
        await axios.put("Auftrag/updateAuftragFahrernummer/"+this.auftrags_IDs[this.auftrags_IDs_index]+"/"+this.fahrernummer);
      }
      else
      {
        alert("Auftrag bereits verteilt.");
      }
    },
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
  async mounted() {
      await this.$http.get('/route').then(response => this.data = response.data)

      //todo this fahrernummer = aktuelle Fahrernummer
    },
  data() {
    return {
      data: [],
      fahrernummer: 0,
      auftrags_IDs: [],
      auftrags_IDs_index: 0,
      benachrichtigungs_ID: 0,
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
