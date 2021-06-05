<template>
  <v-menu offset-y>
    <template v-slot:activator="{on}">
      <v-btn v-on="on" @click="loadGerichteFromStore" icon>
        <v-icon>mdi-cart</v-icon>
        <!-- <v-badge v-if="totalItem > 0" :content="totalItem" inline>
        </v-badge>
        -->
      </v-btn>
    </template>
    <v-card width="400">
      <v-list>
        <v-list-item>

          <v-list-item-content>
            <v-list-item-title class="headline font-weight-bold">Warenkorb</v-list-item-title>
          </v-list-item-content>

        </v-list-item>
      </v-list>

      <v-divider></v-divider>
      <v-card-text v-if="carts.length > 0">

        <v-list :key="version">

          <v-list-item v-for="(item, idx) in carts" :key="idx">
            <v-list-item-avatar>
              <img :src="item.thumbnail">
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.name }}</v-list-item-title>
              <v-list-item-subtitle>Anzahl: {{ item.quantity }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{ calculateItemPrice(item.quantity, item.price) }} &euro;</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-action>
              <v-btn icon @mouseover="selectGericht(item)" @click="deleteGerichtFromStore" small>
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>


        </v-list>

        <h3>Steuer: {{ ((calculateCartPrice() * 0.93) * 0.07).toFixed(2) }} &euro; (7% Mwst.)</h3>
        <h3 class="pb-1">Lieferkosten: 2 &euro;</h3>
        <v-divider></v-divider>
        <h2 class="pt-2">Endpreis: {{ calculateCartPrice() }} &euro;</h2>

        <v-card-actions>
          <v-btn block color="primary" depressed tile @click="paypalRequest()">
            Checkout
          </v-btn>
        </v-card-actions>
      </v-card-text>

      <v-card-text v-else>
        <p>No item</p>
      </v-card-text>

    </v-card>

  </v-menu>

</template>

<script>
export default {
  name: "Cart",
  mounted() {
    this.loadGerichteFromStore();
  },
  methods: {
    roundToTwo(num) {
      return (Math.round(num + "e+2")  + "e-2");
    },
    selectGericht(item) {
      this.selectedGericht = item;
    },
    loadGerichteFromStore() {
      this.carts = this.$store.getters.getCartGerichte;
      this.version++;
    },
    deleteGerichtFromStore() {
      this.$store.commit("removeFromCartGerichte", this.selectedGericht);
      this.loadGerichteFromStore();
      this.version++;
    },
    calculateItemPrice(price, amount) {
      return price * amount;
    },
    calculateCartPrice() {
      let cartPrice = 0;
      this.carts.forEach(value => {
        cartPrice = cartPrice + this.calculateItemPrice(value.quantity, value.price);
      });
      return this.roundToTwo((cartPrice + 2) * 1.07).toFixed(2);
    },
    async getCustomerId() {
      const response = await this.$http.post("/Benutzer/getIdByEmail", { email: this.$store.getters.getLoginData.auth.username });
      return response.data;
    },
    async paypalRequest() {
      const items = [];
      this.$store.getters.getCartGerichte.forEach(item => {
        items.push(item.gericht_ID);
      });

      const customerId = await this.getCustomerId();

      this.$http.post('/Bestellung/add', {
        items: items,
        customerId: customerId
      }).then((response) => {
        if (response.status === 201) {

          this.$http.post('/Bestellung/pay', {
            jobId: response.data.auftrags_ID
          }).then((response) => {
            console.log(JSON.stringify(response.data))
            if (response.status === 200) {
              this.$store.commit("deleteCartGerichte");
              this.version++;
              this.$router.push({name: "BezahlungErfolgreich", params: { cart: this.carts, payment: response.data }});
            }
          });
        }
      });
    }
  },
  data() {
    return {
      carts: [],
      version: 0,
      selectedGericht: ""
    };
  },
}
</script>