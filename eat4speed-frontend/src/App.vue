<template>
  <v-app>
    <Navbar/>
    <router-view/>
  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
  name: 'App',
  metaInfo: {
    title: 'Eat 4 Speed',
    titleTemplate: '%s'
  },
  components: {
    Navbar
  }
};
</script>
