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
                      <div class="text-right">#{{ itemOffen.id }} Bestelldatum: {{ itemOffen.date }}</div>
                      <v-card-title>{{ itemOffen.nameRestaurant }}</v-card-title>
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

              <v-tab active title="Offen">
                <p>Aktiv</p>
              </v-tab>
              <v-tab-item>
                <v-list>
                  <v-list-item v-for="itemAktiv in aktiveBestellungen" v-bind:key="itemAktiv.id" class="mb-12">
                    <v-col cols="8" style="background-color: lightsteelblue">
                      <div class="text-right">#{{ itemAktiv.id }} Bestelldatum: {{ itemAktiv.date }}</div>
                      <v-card-title>{{ itemAktiv.nameRestaurant }}</v-card-title>
                      <v-card-text>{{ itemAktiv.products }}</v-card-text>
                      <v-card-text>{{ itemAktiv.count }}x</v-card-text>
                      <div class="text-right">{{ itemAktiv.price }} €</div>
                    </v-col>
                    <div>
                      <v-col>
                        Status: {{ itemAktiv.state }}
                      </v-col>
                      <v-col>
                        geplante Ankunft: {{ itemAktiv.ankunft_est }}
                      </v-col>
                    </div>

                  </v-list-item>
                </v-list>
              </v-tab-item>

              <v-tab title="Abgeschlossen">
                <p>Abgeschlossen</p>
              </v-tab>
              <v-tab-item>
                <v-list>
                  <v-list-item v-for="itemAbgeschlossen in abgeschlosseneBestellungen" v-bind:key="itemAbgeschlossen.id"
                               class="mb-12">
                    <v-col cols="8" style="background-color: lightsteelblue">
                      <div class="text-right">#{{ itemAbgeschlossen.id }} Bestelldatum: {{
                          itemAbgeschlossen.date
                        }}
                      </div>
                      <v-card-title>{{ itemAbgeschlossen.nameRestaurant }}</v-card-title>
                      <v-card-text>{{ itemAbgeschlossen.products }}</v-card-text>
                      <v-card-text>{{ itemAbgeschlossen.count }}x</v-card-text>
                      <div class="text-right">{{ itemAbgeschlossen.price }} €</div>
                    </v-col>
                    <div>
                      <v-col>
                        <v-btn
                            class="ml-1"
                            color="green"
                            tile
                            @click="addToCart(itemAbgeschlossen.id)"
                        >
                          nochmal Bestellen
                        </v-btn>
                      </v-col>
                      <v-col>
                        Erhalten: {{ itemAbgeschlossen.ankunft_true }}
                      </v-col>
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

                    <v-col cols="8" style="background-color: lightcoral">
                      <div class="text-right">#{{ itemStorniert.id }} Bestelldatum: {{ itemStorniert.date }}</div>
                      <v-card-title>{{ itemStorniert.nameRestaurant }}</v-card-title>
                      <v-card-text>{{ itemStorniert.products }}</v-card-text>
                      <v-card-text>{{ itemStorniert.count }}x</v-card-text>
                      <div class="text-right">{{ itemStorniert.price }} € wurden erstattet</div>
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
    this.loadBestellungenAktive();
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

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'bezahlt/' + this.$store.getters.getLoginData.auth.username, this.$store.getters.getLoginData);

      let anzahl = ResponseBestellungen.data.length.toString();

      let itemOffen;
      for (let i = 0; i < anzahl; i++) { // outer loop

        let sekunden = 300 - ResponseBestellungen.data[i][4];
        if (sekunden <= 0) {
          sekunden = -300;
        }

        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0], this.$store.getters.getLoginData);

        itemOffen = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          nameRestaurant: (ResponseProdukte.data[0][2]),
          zahl_number: sekunden
        }

        this.offeneBestellungen.push(itemOffen)

      }

    },

    async loadBestellungenAktive() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungenAktiv/" + this.$store.getters.getLoginData.auth.username, this.$store.getters.getLoginData);

      let anzahl = ResponseBestellungen.data.length.toString();

      let itemAktiv;
      for (let i = 0; i < anzahl; i++) { // outer loop


        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0], this.$store.getters.getLoginData);

        itemAktiv = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          state: (ResponseBestellungen.data[i][2]),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          nameRestaurant: (ResponseProdukte.data[0][2]),
          zahl_number: (ResponseBestellungen.data[i][4]),
          ankunft_est: (ResponseBestellungen.data[i][5])
        }

        this.aktiveBestellungen.push(itemAktiv)

      }

    },

    async loadBestellungenStorniert() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'storniert/' + this.$store.getters.getLoginData.auth.username, this.$store.getters.getLoginData);

      let anzahl = ResponseBestellungen.data.length.toString();

      let itemStorniert;
      for (let i = 0; i < anzahl; i++) { // outer loop


        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0], this.$store.getters.getLoginData);

        itemStorniert = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          nameRestaurant: (ResponseProdukte.data[0][2]),
          zahl_number: (ResponseBestellungen.data[i][4])
        }

        this.stornierteBestellungen.push(itemStorniert)

      }

    },
    async loadBestellungenAbgeschlossen() {

      const ResponseBestellungen = await axios.get("Bestellung/getKundeBestellungen/" + 'abgeliefert/' + this.$store.getters.getLoginData.auth.username, this.$store.getters.getLoginData);

      let anzahl = ResponseBestellungen.data.length.toString();

      let itemAbgeschlossen;
      for (let i = 0; i < anzahl; i++) { // outer loop


        const ResponseProdukte = await axios.get("Bestellung/getProduktUndAnzahl/" + ResponseBestellungen.data[i][0], this.$store.getters.getLoginData);

        itemAbgeschlossen = {
          id: (ResponseBestellungen.data[i][0]),
          price: parseFloat(ResponseBestellungen.data[i][3]).toFixed(2).toString(),
          date: (ResponseBestellungen.data[i][1]),
          products: (ResponseProdukte.data[0][0]),
          count: (ResponseProdukte.data[0][1]),
          nameRestaurant: (ResponseProdukte.data[0][2]),
          zahl_number: (ResponseBestellungen.data[i][4]),
          ankunft_true: (ResponseBestellungen.data[i][5])
        }

        this.abgeschlosseneBestellungen.push(itemAbgeschlossen)

      }

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
      if (timeLeft > 0) {
        this.stornierteBestellID = bestellID;
        if (!this.accepted) {
          this.dialog = true;
          return;
        }
      }
    },

    async changeBestellungStatus(bestellID) {

      try {
        await axios.put("/Bestellung/updateBestellungStatusRestaurantUndKundeDontTouchThis/" + bestellID + '/storniert', this.$store.getters.getLoginData);
      } catch (error) {
        console.log(error.response)
      }

      const nochOffeneAuftraege = await axios.get("/Bestellung/getAnzahlFertigerAuftraege/" + bestellID, this.$store.getters.getLoginData);

      if(nochOffeneAuftraege.data[0][0] === 0){
        await axios.put("/Auftrag/setToErledigt/" + nochOffeneAuftraege.data[0][1], this.$store.getters.getLoginData);
        await axios.put("/Auftrag/updateAuftragFahrernummer/" + nochOffeneAuftraege.data[0][1] + '/9999', this.$store.getters.getLoginData);
      }

      this.accepted = false;
      window.location.reload();

    },
    toZero(inputNumber) {
      inputNumber = inputNumber - 300;
      inputNumber = Math.max(0, inputNumber);
      return inputNumber
    },

    async addToCart(id) {

      let idArray = await axios.get("/Bestellung/getGerichtIds/" + id, this.$store.getters.getLoginData);

      let replace = idArray.data[0].replaceAll('[', '').replaceAll(']', '')
      let idsArray = replace.split(', ')

      let map = idsArray.reduce((acc, e) => acc.set(e, (acc.get(e) || 0) + 1), new Map());
      let entries = [...map.keys()]
      let anzahl = [...map.values()]


      let i
      for (i = 0; i < entries.length; i++) {

        const config = {responseType: "arraybuffer"};
        const responsePicture = await axios.get("/GerichtBilder/getBild/" + entries[i], config);

        let imageURL;

        if (responsePicture.status !== 204) {

          let pictureBlob = new Blob([responsePicture.data], {type: responsePicture.headers["content-type"]})

          imageURL = URL.createObjectURL(pictureBlob);

        } else {
          imageURL = "";
        }

        let responseGericht = await axios.get("/Gericht/getGerichtDataByGericht_ID/" + entries[i], this.$store.getters.getLoginData);

        let cartGericht = {
          gericht_ID: (responseGericht.data[0][0]),
          name: (responseGericht.data[0][1]),
          thumbnail: imageURL,
          quantity: (anzahl[i]),
          price: (responseGericht.data[0][3])
        }

        this.$store.commit("addToCartGerichte", cartGericht);
      }


    }
  },
  data() {
    return {
      offeneBestellungen: [],
      aktiveBestellungen: [],
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