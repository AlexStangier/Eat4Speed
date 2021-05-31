<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Offene Bestellungen</h1>
          <v-list>
            <v-list-item class="mb-12" v-for="item in shoppingItems" v-bind:key="item.name">
                <v-card class="mr-10" color="red" dark>
                  <v-col >
                    <v-card-title>Bestellung {{ item.id }} - {{ item.name }}</v-card-title>
                    <v-card-text>- {{ item.artikel }}</v-card-text>
                    {{ item.price }} €
                  </v-col>
                </v-card>
              <v-slider
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
                <template v-slot:thumb-label="{ value }">
                  {{ bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>

          </v-list>
        </v-flex>
        <div>
          <ul>

          </ul>
        </div>
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
      console.log(anzahl); // Anzahl der Ausgaben

      for(let i = 0; i < anzahl; i++) { // outer loop
        this.shoppingItems[i].id = (ResponseBestellungen.data[i][0]);
        this.shoppingItems[i].name = (ResponseBestellungen.data[i][1]);
        this.shoppingItems[i].price = parseFloat(ResponseBestellungen.data[i][3]).toFixed(2);
        this.shoppingItems[i].artikel = (ResponseBestellungen.data[i][4]);

      }
      let BestellungenData = ResponseBestellungen.data[0];
      let BestellungenData1 = ResponseBestellungen.data[1];

      console.log(BestellungenData[1]);
      console.log(BestellungenData1);


    }
  },
  data () {
    return {
      bestellstati: ['Bereit', 'In Zubereitung', 'Abholbereit'],
      bestellstatifarben: ['red', 'yellow', 'green'],
      bestellungen: [
        {
          name: 'Bestellung 1',
          offen: false,
          inZubereitung: true,
          abholBereit: false,
        },
        {
          name: 'Bestellung 2',
          offen:  true,
          inZubereitung: false,
          abholBereit: false,
        },

      ],
      shoppingItems: [
        { id: '', name: '', price: '', artikel: '' },
        { id: '', name: '', price: '', artikel: ''  },
        { id: '', name: '', price: '', artikel: ''  }
      ]
    }
  },
}
</script>

<style scoped>

</style>