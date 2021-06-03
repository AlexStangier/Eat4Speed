<template>
  <v-main>
    <v-row class="pa-2">
      <v-col cols="auto">
        <v-dialog
            transition="dialog-top-transition"
            :retain-focus="false"
            max-width="290"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                class="ml-2"
                color="primary"
                v-bind="attrs"
                v-on="on"
            >Anmelden</v-btn>
          </template>
          <template v-slot:default="dialog">
            <v-card>
              <v-toolbar
                  color="primary"
                  dark
              >
                <div class="mx-auto">
                  <h2>Anmeldung</h2>
                </div>
              </v-toolbar>
              <v-card-actions>
                <v-container>
                  <v-row class="mt-2">
                    <v-col align="center" justify="center">
                      <v-btn ref="GerichtButton" :disabled="!valid" :to="{ name: 'KundeAnmeldung'}" width="200px" color="primary" depressed tile
                             @click="gerichtFarbe">Kunde
                      </v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn ref="GerichtButton" :disabled="!valid" :to="{ name: 'RestaurantAnmeldung'}" width="200px" color="primary" depressed tile
                             @click="gerichtFarbe">Restaurant
                      </v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn ref="GerichtButton" :disabled="!valid" :to="{ name: 'FahrerAnmeldung'}" width="200px" color="primary" depressed tile
                             @click="gerichtFarbe">Fahrer
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-actions>
              <v-card-actions>
                <v-btn
                    text
                    @click="dialog.value = false"
                >Schließen</v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>
      </v-col>
    </v-row>

    <div class="d-flex flex-column justify-space-between align-center">
      <v-img
          class="justify-center"
          max-height="50%"
          max-width="50%"
          src="@/assets/logo.png"
      ></v-img>
    </div>
    <video-background
        :src="require(`@/assets/background.mp4`)"
        overlay="linear-gradient(45deg,#2a4ae430,#fb949e6b)"
        :playbackRate=0.8
        style="height: 600px; max-height: 100%; width: 100%;"
    >
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md3 sm6 xs12>
            <v-card>
              <v-card class="px-4 pb-4">
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
                  </template>
                </v-text-field>

                <v-row>
                  <v-col align="center">
                    <v-btn class="ma-1 white--text" ref="GerichtButton" :disabled="!valid" width="200px" depressed tile
                           @click="gerichtFarbe" @mousedown="setDestinationToGerichte"
                           :color="btnType === 0 ? 'primary' : 'blue-grey'">Gericht
                    </v-btn>
                    <v-btn class="ma-1 white--text" ref="UmgebungButton" :disabled="!valid" width="200px" depressed tile
                           @click="umbegungFarbe" @mousedown="setDestinationToRestaurants"
                           :color="btnType === 1 ? 'primary' : 'blue-grey'">Umgebung
                    </v-btn>
                  </v-col>
                </v-row>

              </v-card>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </video-background>
  </v-main>
</template>

<script>
export default {
  name: 'Startseite',
  mounted() {
    this.searchDestination = "Gerichte";
    this.$store.commit("changeCartGerichte", []);
  },
  computed: {
    isUserLoggedIn() {
      return this.user !== undefined;
    },
  },
  methods: {
    setStoreSearchString() {
      this.$store.commit("changeSearchString", this.searchString);
      console.log("changed searchString to " + this.$store.getters.searchString);
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
        this.$store.commit("changeSearchType", "Restaurants");
        console.log("To Restaurants");
        this.$router.push({path: '/kundeRestaurants'});
      }
    },
    setDestinationToGerichte() {
      this.searchDestination = "Gerichte";
    },
    setDestinationToRestaurants() {
      console.log("Changed Destination to Restaurants");
      this.searchDestination = "Restaurants";
    },
    gerichtFarbe() {
      this.btnType = 0;
    },
    umbegungFarbe() {
      this.btnType = 1;
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data() {
    return {
      valid: true,
      searchString: "",
      searchDestination: "Gerichte",
      btnType: 0,
      user: this.$cookies.get('emailAdresse'),
    }
  }
};
</script>