<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Offene Bestellungen</h1>
          <v-list>
            <v-list-item class="mb-12" v-for="item in eingegangeneBestellungen" v-bind:key="item.id">
              <v-card class="mr-10" color="red" dark>
                <v-col >
                  <v-card-title>Bestellung {{ item.id }} - {{ item.name }}</v-card-title>
                  <v-card-text>- {{ item.artikel }}</v-card-text>
                  {{ item.price }} €
                </v-col>
              </v-card>
              <v-slider
                  :value="statusNummer"
                  :tick-labels="bestellstati"
                  :max="3"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
                  @change="changeAuftragStatus(item.id, item.status)"


              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
                <template v-slot:thumb-label="{ value }">
                  {{ item.status = bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>

          </v-list>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";


export default {
  name: "RestaurantBestellungen",
  mounted() {
    this.loadBestellungen();
  },
  methods: {
    async loadBestellungen() {

      const ResponseBestellungen = await axios.get("Benutzer/getRestaurantBestellungen/" + this.$store.getters.getLoginData.auth.username);

      let anzahl = ResponseBestellungen.data.length.toString();

      let item;
      for(let i = 0; i < anzahl; i++) { // outer loop
        if((ResponseBestellungen.data[i][2]) == 'bezahlt'){
          this.statusNummer = 1;
        }
        if((ResponseBestellungen.data[i][2]) == 'bearbeitung'){
          this.statusNummer = 2;
        }
        if((ResponseBestellungen.data[i][2]) == 'abholbereit'){
          this.statusNummer = 3;
        }

        item = {id: (ResponseBestellungen.data[i][0]), name: (ResponseBestellungen.data[i][1]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          artikel: (ResponseBestellungen.data[i][4]), status: this.statusNummer}

        this.eingegangeneBestellungen.push( item )

      }

    },
    async changeAuftragStatus(bestellID, zustand){

      if(zustand == 'stornieren'){
        zustand = 'storniert';
      }
      if(zustand == 'Bereit'){
        zustand = 'bezahlt';
      }
      if(zustand == 'In Zubereitung'){
        zustand = 'bearbeitung';
      }
      if(zustand == 'Abholbereit'){
        zustand = 'abholbereit';
      }

      let auftrag = {
        status: zustand,
        auftrags_ID: bestellID
      }

      await axios.put("/Auftrag/updateAuftragStatusRestaurant", auftrag);

      if(zustand == 'storniert'){
        window.location.reload();
      }

    }
  },
  data () {
    return {
      bestellstati: ['stornieren', 'Bereit', 'In Zubereitung', 'Abholbereit'],
      bestellstatifarben: ['red', 'yellow', 'green'],
      eingegangeneBestellungen: [],
      statusNummer: '',
    }
  },
}
</script>

<style scoped>

</style>