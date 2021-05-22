<template>
  <v-card class="overflow-hidden">
    <v-app-bar
        flat
    >

      {{ displayUser }}
      <v-btn v-if="isUserLoggedIn" class="ml-4" color="blue" dark rounded width="150" @click="logoutUser()">Abmelden</v-btn>

      <v-spacer></v-spacer>

      <Cart></Cart>

    </v-app-bar>
  </v-card>
</template>

<script>
import { eventBus } from '@/event/event';
import Cart from '@/components/Cart.vue';

export default {
  components: {
    Cart,
  },
  created() {
    eventBus.$on('setLogin', (token) => this.user = token);
  },
  data() {
    return {
      user: this.$cookies.get('emailAdresse')
    }
  },
  computed: {
    isUserLoggedIn() {
      return this.user !== undefined;
    },
    displayUser() {
      if (this.isUserLoggedIn) {
        const email = this.user;
        return 'Angemeldet als: ' + email;
      }
      return 'Du bist nicht angemeldet';
    },
  },
  methods: {
    logoutUser() {
      this.$store.dispatch('deleteLoginDate');
      this.user = undefined;
      if (this.$router.name !== 'Startseite') {
        this.$router.push({ name: 'Startseite' }).catch(()=>{ });
      }
    },
  }
}
</script>