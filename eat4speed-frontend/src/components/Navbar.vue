<template>
  <v-card class="overflow-hidden">
    <v-app-bar
        flat
    >

      {{ displayUser }}
      <v-btn v-if="isUserLoggedIn" class="ml-4" color="primary" depressed tile @click="logoutUser()">Abmelden</v-btn>

      <v-spacer></v-spacer>

      <v-text-field
          v-model="searchString"
          prepend-inner-icon="mdi-magnify"
          hide-details
          :label="btnType === 0 ? 'Suche nach Gericht' : 'Suche nach Umgebung'"
          required
          single-line
      >
        <template v-slot:append>
          <v-btn
              color="primary"
              depressed
              tile
              @click="setStoreSearchString">
            Los
          </v-btn>
          <v-btn class="ml-1 white--text" :disabled="!valid" width="200px" depressed tile
                 @click="gerichtFarbe" @mousedown="setDestinationToGerichte"
                 :color="btnType === 0 ? 'primary' : 'blue-grey'">Gericht
          </v-btn>
          <v-btn class="ml-1 white--text" ref="UmgebungButton" :disabled="!valid" width="200px" depressed tile
                 @click="umbegungFarbe" @mousedown="setDestinationToRestaurants"
                 :color="btnType === 1 ? 'primary' : 'blue-grey'">Umgebung
          </v-btn>
        </template>
      </v-text-field>


      <v-spacer></v-spacer>

      <v-menu
          bottom
          left
          offset-y
          tile
          :close-on-content-click="false"
      >
        <template v-slot:activator="{ on, attrs}">
          <v-btn
              v-bind="attrs"
              v-on="on"
              class="ml-3"
              color="primary"
              tile
          >
            <v-icon> mdi-account </v-icon>
            Konto
          </v-btn>
        </template>
        <v-list
            max-width="400"
        >
          <v-list-item>
            <v-btn
                :to="{name: 'Favorites'}"
                text
                tile
                width="200"
            >
              <v-content
                  class="text-left"
              >
                <v-icon>mdi-heart</v-icon>
                Favoriten
              </v-content>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn
                text
                tile
                width="200"
            >
              <v-content
                  class="text-left"
              >
                <v-icon>mdi-map-clock</v-icon>
                Lieferansicht
              </v-content>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn
                text
                tile
                width="200"
            >
              <v-content
                  class="text-left"
              >
                <v-icon>mdi-history</v-icon>
                Bestellhistorie
              </v-content>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn
                :to="{name: 'Einstellungen'}"
                text
                tile
                width="200"
            >
              <v-content
                  class="text-left"
              >
                <v-icon>mdi-account-cog-outline</v-icon>
                Einstellungen
              </v-content>
            </v-btn>
          </v-list-item>
        </v-list>
      </v-menu>

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
      user: this.$cookies.get('emailAdresse'),
      valid: true,
      searchString: "",
      searchDestination: "Gerichte",
      btnType: 0
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
  beforeRouteLeave(to, from, next) {
    this.setStoreSearchString();
    next();
  },
  methods: {
    logoutUser() {
      this.$store.dispatch('deleteLoginDate');
      this.user = undefined;
      if (this.$router.name !== 'Startseite') {
        this.$router.push({ name: 'Startseite' }).catch(()=>{ });
      }
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString", this.searchString);
      if (this.searchDestination === "Gerichte") {
        const searchOptions = {
          gericht_ID: -1,
          kundennummer: this.loggedInKunde_ID,
          gerichtName: this.searchString,
          kategorien: [],
          excludedAllergene: [],
          maxMindestbestellwert: 0,
          maxEntfernung: 0,
          minBewertung: 0,
          useName: true,
          useKategorien: false,
          useAllergene: false,
          useMindestbestellwert: false,
          useEntfernung: false,
          useBewertung: false
        }
        this.$store.commit("changeSearchOptions", searchOptions);
        this.$store.commit("changeSearchType", "Gerichte");
        this.$router.push({name: 'Kunde'});
      } else {
        this.$store.commit("changeSearchType", "Restaurants");
        const searchOptionsRestaurant = {
          kundennummer: this.loggedInKunde_ID,
          restaurantName: this.searchString,
          maxMindestbestellwert: 0,
          maxEntfernung: 0,
          minBewertung: 0,
          useName: true,
          useMindestbestellwert: false,
          useEntfernung: false,
          useBewertung: false
        }
        this.$store.commit("changeSearchOptionsRestaurant", searchOptionsRestaurant)
        this.$router.push({path: '/kundeRestaurants'});
      }
    },
    setDestinationToGerichte() {
      this.searchDestination = "Gerichte";
    },
    setDestinationToRestaurants() {
      this.searchDestination = "Restaurants";
    },
    gerichtFarbe() {
      this.btnType = 0;
    },
    umbegungFarbe() {
      this.btnType = 1;
    }
  }
}
</script>