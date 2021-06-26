<template>
  <v-main>
    <v-app-bar color="primary"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Offene Bestellungen</v-toolbar-title>
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
              <v-list-item-title>Startseite</v-list-item-title>
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
        <v-flex md10 sm10 xs12>
          <h1 class="mb-5">Offene Bestellungen</h1>
          <v-list>
            <v-list-item class="mb-12" v-for="item in eingegangeneBestellungen" v-bind:key="item.id">

              <v-col style="background-color: lightsteelblue" >
                <div  class="text-left">Bestellt: {{ item.date }} // Termin: [{{item.customerDate}}]</div>
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

      const ResponseBestellungen = await axios.get("Bestellung/getRestaurantBestellungen/" + this.$cookies.get('emailAdresse'));


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

        let wunschdatum = ResponseBestellungen.data[i][5];
        if(wunschdatum === null){
          wunschdatum = '-'
        }

        item = {
          id: (ResponseBestellungen.data[i][0]),
          name: (ResponseBestellungen.data[i][1]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][4]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          customerDate: (wunschdatum),
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

      await axios.put("/Bestellung/updateBestellungStatusRestaurantUndKundeDontTouchThis/" + bestellID + "/" + zustand);

      const nochOffeneAuftraege = await axios.get("/Bestellung/getAnzahlFertigerAuftraege/" + bestellID);

      if(nochOffeneAuftraege.data[0][0] === 0){
        await axios.put("/Auftrag/setToErledigt/" + nochOffeneAuftraege.data[0][1]);
        await axios.put("/Auftrag/updateAuftragFahrernummer/" + nochOffeneAuftraege.data[0][1] + '/9999');
      }

      this.accepted = false;

      if (zustand === 'storniert') {
        window.location.reload();
      }
    }
  },
  data() {
    return {
      drawer: false,
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