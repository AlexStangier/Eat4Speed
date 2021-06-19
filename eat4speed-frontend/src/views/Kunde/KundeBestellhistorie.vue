<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-start justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Bestellhistorie</h1>
          <div>
            <v-tabs>
              <v-tab active title="Offen">
                <p>Offen</p>
              </v-tab>
              <v-tab-item>
                <v-list>
                  <v-list-item v-for="itemOffen in offeneBestellungen" v-bind:key="itemOffen.id" class="mb-12">
                    <v-col cols="8" style="background-color: lightsteelblue">
                      <v-card-title>#{{ itemOffen.id }} Bestelldatum: {{ itemOffen.date }}</v-card-title>
                      <v-card-text>{{ itemOffen.products }}</v-card-text>
                      <v-card-text>{{ itemOffen.count }}x</v-card-text>
                      <div class="text-right">{{ itemOffen.price }} €</div>
                    </v-col>
                    <div>
                      <v-btn
                          class="ml-1"
                          color="red"
                          tile
                          @click="saveValues(itemOffen.id, itemOffen.zahl_number + number)"
                      >
                        Stornieren
                      </v-btn>
                      Zeit übrig: {{ toZero(itemOffen.zahl_number + number) }}s
                    </div>
                  </v-list-item>
                </v-list>
              </v-tab-item>

              <v-tab title="Abgeschlossen">
                <p>Abgeschlossen</p>
              </v-tab>
              <v-tab-item>
                <v-list>
                  <v-list-item v-for="itemAbgeschlossen in abgeschlosseneBestellungen" v-bind:key="itemAbgeschlossen.id" class="mb-12">
                    <v-col cols="8" style="background-color: lightsteelblue">
                      <v-card-title>#{{ itemAbgeschlossen.id }} Bestelldatum: {{ itemAbgeschlossen.date }}</v-card-title>
                      <v-card-text>{{ itemAbgeschlossen.products }}</v-card-text>
                      <v-card-text>{{ itemAbgeschlossen.count }}x</v-card-text>
                      <div class="text-right">{{ itemAbgeschlossen.price }} €</div>
                    </v-col>
                    <div>
                      <v-btn
                          class="ml-1"
                          color="red"
                          tile
                      >
                        nochmal Bestellen
                      </v-btn>
                       geht noch nicht
                    </div>
                  </v-list-item>
                </v-list>
              </v-tab-item>
              <v-tab title="Storniert">
                <p>Storniert</p>
              </v-tab>
              <v-tab-item>
                <v-list>
                  <v-list-item v-for="itemStorniert in stornierteBestellungen" v-bind:key="itemStorniert.id"
                               class="mb-12">

                    <v-col cols="8" style="background-color: lightsteelblue">
                      <v-card-title>#{{ itemStorniert.id }} Bestelldatum: {{ itemStorniert.date }}</v-card-title>
                      <v-card-text>{{ itemStorniert.products }}</v-card-text>
                      <v-card-text>{{ itemStorniert.count }}x</v-card-text>
                      <div class="text-right">{{ itemStorniert.price }} €</div>
                    </v-col>

                  </v-list-item>
                </v-list>
              </v-tab-item>
            </v-tabs>
          </div>
        </v-flex>
      </v-layout>
    </v-container>

    <v-dialog v-model="dialog"
              :retain-focus="false"
              max-width="290"
              persistent
              transition="dialog-top-transition"
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
  name: "KundeBestellhistorie",
  created() {
    this.loadBestellungenOffen();
    this.loadBestellungenStorniert();
    this.loadBestellungenAbgeschlossen();
    this.countDownTimer();
  },
  methods: {
    closeDialog() {
      window.location.reload();
      this.dialog = false;
    },
    acceptStornierung() {
      this.dialog = false;
      this.accepted = true;
      this.changeBestellungStatus(this.stornierteBestellID);
      this.bestellungStatus = {};
    },
    async loadBestellungenOffen() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'bezahlt/' + this.$store.getters.getLoginData.auth.username);

      let anzahl = ResponseBestellungen.data.length.toString();

      let itemOffen;
      for (let i = 0; i < anzahl; i++) { // outer loop

        let sekunden = 300 - ResponseBestellungen.data[i][4];
        console.log(sekunden)
        if (sekunden <= 0) {
          sekunden = -300;
        }

        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0]);

        itemOffen = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          zahl_number: sekunden
        }

        this.offeneBestellungen.push(itemOffen)

      }

    },
    async loadBestellungenStorniert() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'storniert/' + this.$store.getters.getLoginData.auth.username);

      let anzahl = ResponseBestellungen.data.length.toString();

      console.log(anzahl)
      let itemStorniert;
      for (let i = 0; i < anzahl; i++) { // outer loop


        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0]);

        itemStorniert = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          zahl_number: (ResponseBestellungen.data[i][4])
        }

        this.stornierteBestellungen.push(itemStorniert)

      }

      console.log(ResponseBestellungen)
    },
    async loadBestellungenAbgeschlossen() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'abgeliefert/' + this.$store.getters.getLoginData.auth.username);

      let anzahl = ResponseBestellungen.data.length.toString();

      console.log(anzahl)
      let itemAbgeschlossen;
      for (let i = 0; i < anzahl; i++) { // outer loop


        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0]);

        itemAbgeschlossen = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          zahl_number: (ResponseBestellungen.data[i][4])
        }

        this.abgeschlosseneBestellungen.push(itemAbgeschlossen)

      }

      console.log(ResponseBestellungen)
    },
    countDownTimer() {
      if (this.number > 0) {
        setTimeout(() => {
          this.number -= 1
          this.countDownTimer(this.number)
        }, 1000)
      }
    },
    saveValues(bestellID, timeLeft) {
      timeLeft = timeLeft - 300;
      timeLeft = Math.max(0, timeLeft)
      console.log(bestellID)
      if (timeLeft > 0) {
        this.stornierteBestellID = bestellID;
        if (!this.accepted) {
          this.dialog = true;
          return;
        }
      }
    },

    async changeBestellungStatus(bestellID) {
      let bestellung = {
        bestell_ID: bestellID,
        status: 'storniert'
      }
      try {
        await axios.put("/Bestellung/updateBestellungStatus", bestellung);
      } catch (error) {
        console.log(error.response)
      }

      this.accepted = false;
      window.location.reload();

    },
    toZero(inputNumber) {
      inputNumber = inputNumber - 300;
      inputNumber = Math.max(0, inputNumber);
      return inputNumber
    }
  },
  data() {
    return {
      offeneBestellungen: [],
      stornierteBestellungen: [],
      abgeschlosseneBestellungen: [],
      number: 300,
      dialog: false,
      accepted: false,
      stornierteBestellID: 0
    }
  },


}
</script>

<style scoped>

</style>