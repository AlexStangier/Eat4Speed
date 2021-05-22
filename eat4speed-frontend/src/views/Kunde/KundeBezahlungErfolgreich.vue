<template>
  <v-main>
    <v-card fill-height fluid>
      <h1 class="subheading text-center">Bestellung erfolgreich</h1>
      <v-divider></v-divider>
      <v-layout justify-center>
        <v-flex md3 sm6 xs12>
          <v-card-text v-if="carts.length > 0">

            <v-list :key="version">

              <v-list-item v-for="(item, idx) in carts" :key="idx">
                <v-list-item-avatar>
                  <img :src="item.thumbnail">
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                  <v-list-item-subtitle>Anzahl: {{ item.quantity }}</v-list-item-subtitle>
                  <v-list-item-subtitle>{{ calculateItemPrice(item.quantity, item.price) }} &euro;
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>


            </v-list>

            <v-divider></v-divider>

            <h2 class="pt-2">Endpreis: {{ calculateCartPrice() }} &euro;</h2>
          </v-card-text>
        </v-flex>
      </v-layout>
    </v-card>
  </v-main>
</template>

<script>
export default {
  name: "KundeBezahlungErfolgreich",
  props: {
    cart: {
      type: Array
    }
  },
  methods: {
    calculateItemPrice(price, amount) {
      return price * amount;
    },
    calculateCartPrice() {
      let cartPrice = 0;
      this.carts.forEach(value => {
        cartPrice = cartPrice + this.calculateItemPrice(value.quantity, value.price);
      });
      return cartPrice;
    },
  },
  created() {
    this.carts = this.cart;
  },
  data() {
    return {
      carts: []
    };
  },
};
</script>
