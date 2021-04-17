<template>
  <v-menu offset-y>
    <template v-slot:activator="{on}">
      <v-btn v-on="on" icon>
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
            <v-list-item-title class="headline font-weight-bold">Cart</v-list-item-title>
          </v-list-item-content>

        </v-list-item>
      </v-list>

      <v-divider></v-divider>
      <v-card-text v-if="carts.length > 0">

        <v-list>

          <v-list-item v-for="(item, idx) in carts" :key="idx">
            <v-list-item-avatar>
              <img :src="item.thumbnail">
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ item.quantity }} item{{ item.quantity > 1 ? 's' : '' }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-action>
              <v-btn icon small>
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>


        </v-list>

        <v-card-actions>
          <v-btn block color="primary" rounded>
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
  data() {
    return {
      carts: [
        {
          name: 'test',
          thumbnail: 'https://foodbrother.com/wp-content/uploads/2020/04/burger-food-brother.jpg',
          quantity: 1,
        },
        {
          name: 'test2',
          thumbnail: 'https://image.brigitte.de/11225976/t/Lx/v3/w960/r1.5/-/ramen-teaser.jpg',
          quantity: 2,
        }
      ],
    };
  },
}
</script>