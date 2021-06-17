<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Offene Bestellungen</h1>
          <v-list>
            <v-list-item class="mb-12" v-for="item in eingegangeneBestellungen" v-bind:key="item.id">

                <v-col style="background-color: lightsteelblue" >
                  <v-card-title>Bestellung {{ item.id }} - {{ item.name }}</v-card-title>
                  <v-card-text>{{ item.products }}</v-card-text>
                  <v-card-text>{{ item.count }}x</v-card-text>
                  <div  class="text-right">{{ item.price }} €</div>
                </v-col>

              <v-slider
                  :value="item.currentState"
                  :tick-labels="bestellstati"
                  :max="3"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
                  @change="changeBestellungStatus(item.id, item.status)"


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

    <v-dialog persistent
              transition="dialog-top-transition"
              :retain-focus="false"
              v-model="dialog"
              max-width="290"
    >
      <v-card>
        <v-toolbar
            color="grey"
            dark
        >
          <div class="mx-auto">
            <h2>Bestellung stornieren</h2>
          </div>
        </v-toolbar>
        <v-card-actions>
          <v-container>
            <v-row class="mt-2">
              <v-col align="center" justify="center">
                <v-btn color="green" depressed tile width="200px"
                       @click="acceptStornierung()">Bestätigen
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-card-actions>
        <v-card-actions>
          <v-btn color="red"
                 text
                 @click="closeDialog()"
          >Schließen
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-main>
</template>

<script>
import axios from "axios";


export default {
  name: "RestaurantBestellungen",
  created() {
    this.loadBestellungen();
  },
  methods: {
    closeDialog() {
      window.location.reload();
      this.dialog = false;
    },
    acceptStornierung() {
      this.dialog = false;
      this.accepted = true;
      this.changeBestellungStatus(this.bestellungStatus.bestellID, this.bestellungStatus.zustand);
      this.bestellungStatus = {};
    },
    async loadBestellungen() {

      const ResponseBestellungen = await axios.get("Bestellung/getRestaurantBestellungen/" + this.$store.getters.getLoginData.auth.username);


      let anzahl = ResponseBestellungen.data.length.toString();

      let item;
      for (let i = 0; i < anzahl; i++) { // outer loop
        let statusNummer = 0;

        if ((ResponseBestellungen.data[i][2]) === 'bezahlt') {
          statusNummer = 1;
        }
        if ((ResponseBestellungen.data[i][2]) === 'bearbeitung') {
          statusNummer = 2;
        }
        if ((ResponseBestellungen.data[i][2]) === 'abholbereit') {
          statusNummer = 3;
        }

        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0]);

        item = {
          id: (ResponseBestellungen.data[i][0]),
          name: (ResponseBestellungen.data[i][1]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          currentState: statusNummer
        }

        this.eingegangeneBestellungen.push(item)

      }

    },
    async changeBestellungStatus(bestellID, zustand) {
      if (zustand === 'stornieren') {
        if (!this.accepted) {
          this.dialog = true;
          this.bestellungStatus = {bestellID: bestellID, zustand: zustand};
          return;
        }
        zustand = 'storniert';
      }
      if (zustand === 'Bereit') {
        zustand = 'bezahlt';
      }
      if (zustand === 'In Zubereitung') {
        zustand = 'bearbeitung';
      }
      if (zustand === 'Abholbereit') {
        zustand = 'abholbereit';
      }

      let bestellung = {
        status: zustand,
        bestell_ID: bestellID
      }

      await axios.put("/Bestellung/updateBestellungStatus", bestellung);

      this.accepted = false;

      if (zustand === 'storniert') {
        window.location.reload();
      }
    }
  },
  data() {
    return {
      bestellstati: ['stornieren', 'Bereit', 'In Zubereitung', 'Abholbereit'],
      bestellstatifarben: ['red', 'yellow', 'green'],
      eingegangeneBestellungen: [],
      dialog: false,
      accepted: false,
      bestellungStatus: {}
    }
  },
}
</script>

<style scoped>

</style>