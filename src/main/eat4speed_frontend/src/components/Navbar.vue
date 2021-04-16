<template>
  <v-card class="overflow-hidden">
    <v-app-bar
        flat
    >
      {{ displayUser }}

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
  computed: {
    displayUser() {
      const email = this.$auth.auth.username;
      if (this.$auth.auth.username !== '') {
        return 'Angemeldet als: ' + email;
      }
      return '';
    }
  }
}
</script>