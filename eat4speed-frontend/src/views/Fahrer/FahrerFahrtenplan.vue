<template>
  <v-main>
    <v-card class="mx-5 my-5">
      <v-row v-if="show">Unfall gemeldet</v-row>
      <v-data-table v-if="!show"
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
            <v-btn :to="{name: 'FahrerSchichtplan'}" class="mb-5">
              <v-icon>
                mdi-arrow-left
              </v-icon>
              Schichtplan
            </v-btn>

            <v-col cols="6">
              <v-row>
                Datum: {{ getTermin() }}
              </v-row>
              <v-row>
                restliche Stationen: {{ getStationen() }}
              </v-row>
            </v-col>
          </v-container>
          <v-container fluid>
            <v-btn
                class="white--text"
                color="red"
                @click="unfall_melden(); show= !show"
            >
              Unfall melden
            </v-btn>
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
              :disabled="item.hideButton || (data.indexOf(item) !== currStation)"
              color="primary"
              depressed
              tile
              @click="abholungBestätigen(item)"
          >Bestätigen
          </v-btn>
        </template>
      </v-data-table>
    </v-card>

    <v-dialog
        transition="dialog-top-transition"
        :retain-focus="false"
        v-model="dialog"
        max-width="50%"
    >
      <v-card>
        <v-toolbar
            color="primary"
            dark
        >
          <div class="mx-auto">
            <h2>Fahrt bestätigen</h2>
          </div>
        </v-toolbar>
        <v-card-actions>
          <v-container>
            <row>
              <v-col>
                <v-data-table
                    :headers="headersAuftrag"
                    :items="auftrags_IDs"
                    :items-per-page="3"
                    :single-select="false"
                    class="elevation-1 pa-6"
                    item-key="name"
                >
                  <template v-slot:item.actions="{ item }">
                    <v-btn
                        color="primary"
                        depressed
                        tile
                        @click="setAuftragFahrernummer(item.id)"
                    >Bestätigen
                    </v-btn>
                  </template>
                </v-data-table>
              </v-col>
            </row>
          </v-container>
        </v-card-actions>
        <v-card-actions>
          <v-btn
              text
              @click="dialog = false"
          >Schließen
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-main>
</template>

<script>
import DirectionsRenderer from "@/utils/DirectionsRenderer";
import axios from "axios";
import Vue from "vue";

export default {
  name: "FahrerFahrtenplan",
  components: {
    DirectionsRenderer
  },
  methods: {
    async pollData() {
      this.polling = setInterval(() => {
        this.getBenachrichtigung();
        this.checkAuftraegeforFahrernummer();
      }, 3000);
    },
    async getBenachrichtigung() {
      const responseBenachrichtigung = await axios.get("Benachrichtigung_Fahrer/getAllBenachrichtigungFahrerUngelesen/" + this.fahrernummer, this.$store.getters.getLoginData);

      for (let i = 0; i < responseBenachrichtigung.data.length; i++) {
        let benachrichtigungs_ID = responseBenachrichtigung.data[i][0];

        this.auftrags_IDs.push({id: responseBenachrichtigung.data[i][1]});

        await axios.put("Benachrichtigung_Fahrer/markAsGelesen/" + benachrichtigungs_ID, this.$store.getters.getLoginData);
      }

      if (responseBenachrichtigung.data.length > 0) {
        this.dialog = true;
      }

    },
    async setAuftragFahrernummer(id) {
      const response = await axios.get("Auftrag/getAuftragFahrernummerByAuftrags_ID/" + id, this.$store.getters.getLoginData)

      if (response.data[0] !== 9999 && response.data[0] !== null) {
        alert("Auftrag bereits verteilt.");
      } else {
        await axios.put("Auftrag/updateAuftragFahrernummer/" + id + "/" + this.fahrernummer, this.$store.getters.getLoginData);
        this.active_auftrags_IDs++;
        await axios.put("Fahrer/updateFahrer_anzahl_aktueller_Auftraege/" + this.fahrernummer + "/" + this.active_auftrags_IDs, this.$store.getters.getLoginData);
        //this.auftrags_IDs.splice(index,1);
        this.currStation = 0;
        await this.$http.get('/route/calculate/' + this.$cookies.get('emailAdresse')).then(response => this.data = response.data, this.$store.getters.getLoginData);
      }
    },
    async checkAuftraegeforFahrernummer() {
      if (this.auftrags_IDs.length === 0) {
        this.dialog = false;
        return;
      }

      for (let i = 0; i < this.auftrags_IDs.length; i++) {
        let response = await axios.get("Auftrag/getAuftragFahrernummerByAuftrags_ID/" + this.auftrags_IDs[i].id, this.$store.getters.getLoginData)
        if (response.data[0] !== 9999 && response.data[0] !== null) {
          this.auftrags_IDs.splice(i, 1);
        }
      }
    },
    async abholungBestätigen(id) {
      Vue.set(id, 'hideButton', true);
      let abgechlossen = 0;
      if (id.beschreibung === 'Abholung') {
        await this.$http.put('/route/confirm/' + id.beschreibung + '?auftraege=' + id.auftrags_id + '&data=' + id.restaurantname + '&email=' + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      } else {
        await this.$http.put('/route/confirm/' + id.beschreibung + '?auftraege=' + id.auftrags_id + '&email=' + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData).then(response => abgechlossen=response.data);
      }
      if(this.active_auftrags_IDs>0){
        this.active_auftrags_IDs -= abgechlossen;
      }
      this.currStation++;
      id.hidebutton = true;
    },
    async unfall_melden() {
      let auftrags_ids = this.data[0].auftrags_id;
      for(let i = 1; i < this.data.length; i++){

          auftrags_ids += ", " + this.data[i].auftrags_id;

      }
      // console.log(this.active_auftrags_IDs);
      await this.$http.put('/route/accident/?auftraege=' + auftrags_ids, this.$store.getters.getLoginData);

    },
    getTermin() {
      const current = new Date();
      const date = `${current.getDate()}.${current.getMonth()+1}.${current.getFullYear()}`;
      return date;
    },
    getStationen() {
      return this.data.length;
    },
    convertToKM(meters) {
      return meters / 1000;
    },
    async getLoggedInFahrer()
    {
      const response = await axios.get("Benutzer/getFahrernummerByBenutzername/"+this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData)
      this.fahrernummer = response.data[0];
    },
  },
  async mounted() {
    await this.$http.get('/route/calculate/' + this.$cookies.get('emailAdresse')).then(response => {
      this.data = response.data === "" ? [] : response.data;
    }, this.$store.getters.getLoginData);
    await this.getLoggedInFahrer();
    this.pollData();

  },
  beforeDestroy() {
    clearInterval(this.polling);
  },
  data() {
    return {
      data: [],
      fahrernummer: 0,
      show:false,
      auftrags_IDs: [],
      active_auftrags_IDs: 0,
      auftrags_IDs_index: 0,
      benachrichtigungs_ID: 0,
      polling: null,
      currStation: 0,
      acceptDialog: false,
      deleteDialog: false,
      dialog: false,
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
          text: 'Bestellt auf:',
          value: 'zeit',
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
          sortable: false
        },
        {
          value: 'actions',
          sortable: false,
          align: 'end'
        },
      ],
      headersAuftrag: [
        {
          text: 'Auftrags ID',
          align: 'start',
          sortable: false,
          value: 'id'
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
