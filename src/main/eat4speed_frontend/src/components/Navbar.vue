<template>
  <v-card class="overflow-hidden">
    <v-app-bar
        flat
    >
      <v-spacer></v-spacer>

      <Cart></Cart>

    </v-app-bar>
  </v-card>
</template>

<script>
import Cart from '@/components/Cart.vue';
export default {
  components: {
    Cart,
  },
}
</script>