<template>
  <v-main>
    <v-container>
      <v-container>
        <v-row no-gutters class="align-center">
          <v-col
              v-for="k in 4"
              :key="k"
              cols="3"
          >
            <v-card
                v-if="k === 1"
                flat
            >
              <v-select
                  v-model="selectedBewertung"
                  label="Bewertung"
                  :items="selectRating"
                  clearable
                  @change="applyBewertungFilterAndSearch"
              >
                <template v-slot:selection="data">
                  {{ data.item }} {{ "Sterne" }}
                </template>
                <template v-slot:item="data">
                  {{ data.item }} {{ "Sterne" }}
                </template>
              </v-select>
            </v-card>
            <v-card
                v-if="k === 2"
                flat
            >
              <v-select
                  class="ml-10"
                  v-model="selectedEntfernung"
                  label="Entfernung"
                  :items="selectArea"
                  :disabled="!isUserLoggedInBoolean"
                  clearable
                  @change="applyDistanceFilterAndSearch"
              >
                <template v-slot:selection="data">
                  {{ data.item }} {{ "km" }}
                </template>
                <template v-slot:item="data">
                  {{ data.item }} {{ "km" }}
                </template>
              </v-select>
            </v-card>
            <v-card
                v-if="k === 4"
                flat
                class="text-right"
            >
              <v-btn
                  disabled
                  color="primary"
                  tile
              >
                Fliter
              </v-btn>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <v-card class="mx-auto">
          <v-card-title> Gerichte</v-card-title>
          <v-divider></v-divider>
          <v-card
              v-if="amountRestaurants === 0"
              tile
              class="text-center text-h5"
          >
            Es wurden keine Restaurants gefunden
          </v-card>
          <v-virtual-scroll
              :items="items"
              :item-height="250"
              max-height="650"
          >
            <template v-slot:default="{ item }" v-resize>

              <v-card
                  flat
                  tile
                  outlined
              >
                <v-container>
                  <v-row>
                    <v-col
                        cols="3"
                    >
                      <v-card
                          flat
                          tile
                          outlined
                      >
                        <v-img alt="Bild von Essen" min-height="230" max-height="230" max-width="400"
                               position="center center" :src="item.img"></v-img>
                      </v-card>
                    </v-col>
                    <v-col>
                      <v-row
                          v-for="a in 3"
                          :key="a"
                      >
                        <v-col
                            cols="4"
                        >
                          <v-card
                              v-if="a === 1"
                              flat
                              class="text-h5 text-decoration-underline"
                          >
                            {{ item.restaurant }}
                          </v-card>
                          <v-card
                              v-if="a === 1"
                              flat
                              class="subtitle-1"
                          >
                            {{ item.description }}
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="subtitle-1"
                          >
                            Mindestbestellwert: {{ item.mindestbestellwert + ' €' }}
                          </v-card>
                          <v-crad
                              v-if="a === 2"
                              flat
                              class="subtitle-1"
                          >
                            Bestellradius: {{ item.bestellradius + ' km' }}
                          </v-crad>
                        </v-col>
                        <v-col>
                          <v-card
                              v-if="a === 1"
                              flat
                              class="text-right"
                          >
                            <div v-if="item.isFav === true">
                              <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn
                                      @mouseenter="selectRestaurant(item)"  small right
                                      @mousedown="deleteFromFavorites"
                                      @mouseup="()=>{this.amountRestaurants=0;version++}"
                                      v-bind="attrs"
                                      v-on="on"
                                      icon
                                  >
                                    <v-icon>mdi-heart</v-icon>
                                  </v-btn>
                                </template>
                                <span>Hinzugefügt am {{ item.hinzufuegedatum }}</span>
                              </v-tooltip>
                            </div>
                            <div v-else>
                              <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn
                                      @mouseenter="selectRestaurant(item)"  small right
                                      @mousedown="addToFavorites"
                                      @mouseup="()=>{this.amountRestaurants=0;version++}"
                                      v-bind="attrs"
                                      v-on="on"
                                      icon
                                  >
                                    <v-icon>mdi-heart-outline</v-icon>
                                  </v-btn>
                                </template>
                                <span>Zu Favoriten hinzufügen</span>
                              </v-tooltip>
                            </div>
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="text-right"
                          >
                            <v-rating readonly length="5" half-icon="$ratingHalf" half-increments dense small :value="item.rating"></v-rating>
                          </v-card>
                          <v-card
                              v-if="a === 3"
                              flat
                              class="text-right"
                          >
                            <v-btn
                                color="primary"
                                tile
                                small
                                bottom="bottom"
                                @mouseenter="selectRestaurant(item)"
                                @click="setStoreSearchOptions"
                            >
                              Zur Speisekarte
                            </v-btn>
                          </v-card>
                        </v-col>
                      </v-row>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card>
              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
        </v-card>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "KundeRestaurants",
  async mounted() {
    this.searchString = this.$store.getters.searchString;
    this.searchOptions = this.$store.getters.searchOptionsRestaurant;

    console.log(this.searchString);
    await this.checkLoggedInUser();
    await this.getLoggedInKunde();
    await this.getAllEntfernungenAndBewertungen();
    this.loadRestaurants();
  },
  methods: {
    async checkLoggedInUser() {
      if (this.$cookies.get('emailAdresse') !== undefined) {
        this.isUserLoggedInBoolean = true;
      }
    },
    async getLoggedInKunde() {
      if (this.isUserLoggedInBoolean) {
        const response = await axios.get("Benutzer/getKundennummerByBenutzername/" + this.$cookies.get('emailAdresse'))
        this.loggedInKunde_ID = response.data[0];
      }
    },
    async getAllEntfernungenAndBewertungen() {
      if (this.isUserLoggedInBoolean) {
        const responseEntfernungen = await axios.get("EntfernungKundeRestaurant/getEntfernungByKundennummer/" + this.loggedInKunde_ID);
        for (let i = 0; i < responseEntfernungen.data.length; i++) {
          this.distanceRestaurant_IDs[i] = responseEntfernungen.data[i][0];
          this.distancesUnassigned[i] = responseEntfernungen.data[i][1];
        }
      }
      const ResponseBewertungen = await axios.get("Bewertung/getAverageBewertungAndCountBewertungAllRestaurants");
      for(let i = 0; i<ResponseBewertungen.data.length;i++)
      {
        this.bewertungAvgUnassigned[i] = ResponseBewertungen.data[i][0];
        this.bewertungRestaurants[i] = ResponseBewertungen.data[i][2];
      }
    },
    selectRestaurant(item) {
      console.log("Restaurant selected " + item.restaurantid);
      this.selectedRestaurant = item.restaurantid;
    },
    getStoreSeachString() {
      this.searchString = this.$store.getters.searchString;
    },
    getStoreSearchOptionsRestaurant() {
      this.searchOptions = this.$store.getters.searchOptionsRestaurant;
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString", this.searchString);
      console.log("changed searchString to " + this.$store.getters.searchString);
    },
    setStoreSearchOptions() {
      this.$store.commit("changeSearchOptionsRestaurant", this.searchOptions);
      this.setStoreRestaurant_ID();
    },
    setStoreRestaurant_ID() {
      this.$store.commit("changeSelectedRestaurant_ID", this.selectedRestaurant);
      this.$router.push({name: "KundeAuswahlseiteRestaurant"});
    },
    async loadRestaurants() {

      if(this.isUserLoggedInBoolean)
      {
        this.favoritenlisteRestaurants_IDs = [];
        this.hinzufuegedaten = [];

        const ResponseFavoriten = await axios.get("Restaurant/getRestaurantDataByKundennummer_Favoriten/" + this.loggedInKunde_ID);

        console.log(ResponseFavoriten);
        for (let i = 0; i < ResponseFavoriten.data.length; i++) {
          let favData = ResponseFavoriten.data[i];
          this.favoritenlisteRestaurants_IDs[i] = favData[0];
          this.hinzufuegedaten[i] = favData[12];
        }
      }

      const ResponseRestaurants = await axios.post("Restaurant/searchRestaurants", this.searchOptions);

      console.log(ResponseRestaurants);

      for (let i = 0; i < ResponseRestaurants.data.length; i++) {
        let restaurantData = ResponseRestaurants.data[i];

        this.restaurant_IDs[i] = restaurantData[0];
        this.restaurantnamen[i] = restaurantData[1];
        this.descriptions[i] = restaurantData[2];
        this.minimums[i] = restaurantData[3];
        this.bestellradius[i] = restaurantData[4];
        this.lng[i] = restaurantData[10];
        this.lat[i] = restaurantData[11];

        if (this.favoritenlisteRestaurants_IDs.includes(restaurantData[0])) {
          this.isFavorite[i] = true;
          let index = this.favoritenlisteRestaurants_IDs.indexOf(restaurantData[0]);
          this.hinzufuegedatumAssigned[i] = this.hinzufuegedaten[index];
        } else {
          this.isFavorite[i] = false;
          this.hinzufuegedatumAssigned[i] = null;
        }
        if(this.isUserLoggedInBoolean)
        {
          let index = this.distanceRestaurant_IDs.indexOf(restaurantData[0]);
          this.distances[i] = this.distancesUnassigned[index];
        }
        if(this.bewertungRestaurants.includes(restaurantData[0]))
        {
          let index = this.bewertungRestaurants.indexOf(restaurantData[0]);
          this.ratings[i] = this.bewertungAvgUnassigned[index];
        }
        else
        {
          this.ratings[i] = 0;
        }

      }
      for (let i = 0; i < ResponseRestaurants.data.length; i++) {
        let ResponseBewertung = await axios.get("Bewertung/getAverageBewertungAndCountBewertungByRestaurant_ID/" + this.restaurant_IDs[i]);
        this.ratings[i] = ResponseBewertung.data[0][0];
      }

      for (let i = 0; i < ResponseRestaurants.data.length; i++) {
        const config = {responseType: "arraybuffer"};
        const responsePicture = await axios.get("/RestaurantBilder/getBild/" + this.restaurant_IDs[i], config);

        console.log(responsePicture);

        if (responsePicture.status !== 204) {
          console.log("received Picture")
          console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], {type: responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          console.log(imageURL);

          this.imgs[i] = imageURL;
        } else {
          this.imgs[i] = "";
        }

      }
      this.amountRestaurants = 0;
      this.amountRestaurants = ResponseRestaurants.data.length;
      this.version++;
    },
    async applyBewertungFilterAndSearch() {

      if (this.selectedBewertung !== null) {
        this.bewertungOptionActive = true;
      } else {
        this.bewertungOptionActive = false;
        this.selectedBewertung = 0;
      }
      this.nameOptionActive = true;

      const searchOptions = {
        kundennummer: this.loggedInKunde_ID,
        restaurantName: this.searchString,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadRestaurants();
    },
    async applyDistanceFilterAndSearch() {

      if (this.selectedEntfernung !== null) {
        this.entfernungOptionActive = true;
      } else {
        this.entfernungOptionActive = false;
        this.selectedEntfernung = 0;
      }
      this.nameOptionActive = true;

      const searchOptions = {
        kundennummer: this.loggedInKunde_ID,
        restaurantName: this.searchString,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadRestaurants();
    },
    async applyFiltersAndSearch() {
      const searchOptions = {
        kundennummer: this.loggedInKunde_ID,
        restaurantName: this.searchString,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadRestaurants();
    },
    async addToFavorites() {


      if (!this.isUserLoggedInBoolean) {
        alert("Sie müssen sich einloggen, um Favoriten hinzufügen zu können!")
        return;
      }

      if (this.selectedItem.bestellradius < this.selectedItem.distance) {
        alert("Sie befinden sich außerhalb des Bestellradius")
        return;
      }

      var today = new Date();
      const restaurantFavorite = {
        restaurant_ID: this.selectedRestaurant,
        kundennummer: this.loggedInKunde_ID,
        hinzufuegedatum: today,
        anzahl_Bestellungen: 0
      }

      await axios.post("Favoritenliste_Restaurants", restaurantFavorite);
      this.loadRestaurants();
    },
    async deleteFromFavorites() {
      await axios.delete("Favoritenliste_Restaurants/remove/" + this.loggedInKunde_ID + "/" + this.selectedRestaurant);
      this.loadRestaurants();
    },

  },
  data: () => ({
    searchOptions: {},
    searchString: "",
    loggedInKunde_ID: 0,
    amountRestaurants: 4,
    selectedRestaurant: "",
    isUserLoggedInBoolean: false,
    version: 0,
    descriptions: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen: [],
    distances: [],
    minimums: [],
    lng: [],
    lat: [],
    ratings: [],
    distanceRestaurant_IDs: [],
    distancesUnassigned: [],
    favoritenlisteRestaurants_IDs: [],
    hinzufuegedaten: [],
    hinzufuegedatumAssigned: [],
    isFavorite: [],
    bewertungAvgUnassigned:[],
    bewertungRestaurants: [],
    selectRating: [5, 4, 3, 2, 1],
    selectArea: [5, 10, 20, 30, 40],
    selectedMindestbestellwert: 0,
    selectedBewertung: 0,
    selectedEntfernung: 0,
    bestellradius: [],
    nameOptionActive: true,
    mindestbestellwertOptionActive: false,
    bewertungOptionActive: false,
    entfernungOptionActive: false
  }),
  computed: {
    items() {
      let i = 0
      return Array.from({length: this.amountRestaurants}, () => {
        const cdescription = this.descriptions[i]
        const cimg = this.imgs[i]
        const crestaurantid = this.restaurant_IDs[i]
        const crestaurantname = this.restaurantnamen[i]
        const cminimum = this.minimums[i]
        const cbestellradius = this.bestellradius[i]
        const clng = this.lng[i]
        const clat = this.lat[i]
        const cisFav = this.isFavorite[i]
        const chinzufuegedatum = this.hinzufuegedatumAssigned[i]
        const crating = this.ratings[i]
        const cdistance = this.distances[i]
        i++;

        return {
          description: cdescription,
          img: cimg,
          restaurantid: crestaurantid,
          restaurant: crestaurantname,
          mindestbestellwert: cminimum,
          bestellradius: cbestellradius,
          lng: clng,
          lat: clat,
          isFav: cisFav,
          hinzufuegedatum: chinzufuegedatum,
          rating: crating,
          distance: cdistance
        }
      })
    }
  },
  watch: {
    '$store.state.searchOptionsRestaurant': function () {
      this.getStoreSearchOptionsRestaurant();
      this.loadRestaurants();
    }
  }
}
</script>

<style scoped>

</style>