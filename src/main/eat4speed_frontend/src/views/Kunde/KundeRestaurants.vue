<template>
  <v-main>
    <v-container>
      <v-container>
        <v-row no-gutters>
          <v-col sm="2">
            <v-select
                v-model="selectedBewertung"
                label="Bewertung"
                :items="selectRating"
                clearable="true"
                @change="applyBewertungFilterAndSearch"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"Sterne"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"Sterne"}}
              </template>
            </v-select>
          </v-col>
          <v-col sm="2" offset-sm="1">
            <v-select
                v-model="selectedEntfernung"
                label="Entfernung"
                :items="selectArea"
                clearable="true"
                @change="applyDistanceFilterAndSearch"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"km"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"km"}}
              </template>
            </v-select>
          </v-col>
          <v-col offset-sm="1">
            <v-btn>Auf der Karte anzeigen</v-btn>
          </v-col>
          <v-col order-sm="12" offset-sm="1">
            <v-menu
                bottom
                offset-y
                :close-on-content-click="false"
            >
              <template v-slot:activator="{ on, attrs}">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                >
                  Filter
                </v-btn>
              </template>
              <v-list
                  max-width="400"
                  min-width="400"

              >
                <v-checkbox label="Suche benutzen" v-model="nameOptionActive">
                </v-checkbox>
                <v-checkbox label="Mindestbestellwert benutzen" v-model="mindestbestellwertOptionActive">
                </v-checkbox>
                <v-subheader>Mindestbestellwert</v-subheader>
                <v-list-item>
                  <v-slider
                      v-model="selectedMindestbestellwert"
                      min="5"
                      max="100"
                      step="5"
                      thumb-label
                      prepend-icon="mdi-cash"
                      append-icon="mdi-cash-multiple"
                  >
                    <template v-slot:thumb-label="{ value }">
                      {{value}} {{"€"}}
                    </template>
                  </v-slider>
                </v-list-item>
                <v-list-item>
                  <v-btn color="error">Filter löschen</v-btn>
                  <v-btn @click="applyFiltersAndSearch" color="blue">Filter anwenden</v-btn>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>
        </v-row>

      </v-container>
      <v-container>
        <v-card class="mx-auto">
          <v-card-title> Gerichte </v-card-title>
          <v-divider></v-divider>
          <v-virtual-scroll
              :items="items"
              :item-height="300"
              max-height="600"
          >
            <template v-slot:default="{ item }" v-resize>
              <v-list-item v-resize :key="version">
                <v-list-item-content>
                  <v-img alt="Bild von Essen" max-height="300" max-width="300" position="center center" :src="item.img"></v-img>
                </v-list-item-content>
                <v-list-item-content>
                  <v-list-item-group class="text-left">
                    <v-list-item-title>{{ item.restaurant }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                    <v-list-item-content>Mindestbestellwert: {{item.mindestbestellwert}} €</v-list-item-content>
                    <v-list-item-content>Bestellradius: {{item.bestellradius}}</v-list-item-content>
                  </v-list-item-group>
                </v-list-item-content>
                <v-list-item-content></v-list-item-content>
                <v-list-item-group class="text-right">
                  <v-btn small="true" right>
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>
                  <br>
                  <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small="true" :value="item.rating"></v-rating>
                  <br>
                  <v-btn small="true" bottom="bottom" @mouseenter="selectRestaurant(item)" @click="setStoreRestaurant_ID">Zur Speisekarte</v-btn>
                </v-list-item-group>
              </v-list-item>
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
  mounted() {
    this.searchString = this.$store.getters.searchString;
    this.searchOptions = this.$store.getters.searchOptionsRestaurant;

    console.log(this.searchString);
    //TODO change later!
    this.loggedInKunde_ID = 6;

    this.loadRestaurants();
  },
  beforeRouteLeave(to, from, next) {
    this.setStoreRestaurant_ID();
    this.setStoreSearchOptions();
    next();
  },
  methods: {
    selectRestaurant(item) {
      console.log("Restaurant selected "+item.restaurantid);
      this.selectedRestaurant = item.restaurantid;
    },
    getStoreSeachString() {
      this.searchString = this.$store.getters.searchString;
    },
    getStoreSearchOptionsRestaurant() {
      this.searchOptions = this.$store.getters.searchOptionsRestaurant;
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
    },
    setStoreSearchOptions(){
      this.$store.commit("changeSearchOptionsRestaurant", this.searchOptions);
    },
    setStoreRestaurant_ID() {
      this.$store.commit("changeSelectedRestaurant_ID",this.selectedRestaurant);
      this.$router.push({name: "KundeAuswahlseiteRestaurant"});
    },
    async loadRestaurants() {
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
      }
      //TODO
      /*for (let i = 0; i < ResponseRestaurants.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_IDs[i],config);

        console.log(responsePicture);

        if(responsePicture.status !== 204)
        {
          console.log("received Picture")
          console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          console.log(imageURL);

          this.imgs[i] = imageURL;
        }
        else
        {
          this.imgs[i] = "";
        }

      }
      console.log(this.imgs);*/
      this.amountRestaurants = 0;
      this.amountRestaurants = ResponseRestaurants.data.length;
      this.version++;
    },
    async applyBewertungFilterAndSearch() {

      if(this.selectedBewertung>0)
      {
        this.bewertungOptionActive = true;
      }
      else
      {
        this.bewertungOptionActive = false;
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

      if(this.selectedEntfernung>=5)
      {
        this.entfernungOptionActive = true;
      }
      else
      {
        this.entfernungOptionActive = false;
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
  },
  data: () => ({
    searchOptions: {},
    searchString: "",
    loggedInKunde_ID: 0,
    amountRestaurants: 4,
    selectedRestaurant: "",
    version: 0,
    descriptions: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen:[],
    distances: [],
    minimums: [],
    lng: [],
    lat: [],
    selectRating: [5,4,3,2,1],
    selectArea: [5,10,20,30,40],
    selectedMindestbestellwert: 0,
    selectedBewertung: 0,
    selectedEntfernung: 0,
    bestellradius: [],
    nameOptionActive: false,
    mindestbestellwertOptionActive: false,
    bewertungOptionActive: false,
    entfernungOptionActive: false
  }),
  computed: {
    items(){
      let i = 0
      return Array.from({ length: this.amountRestaurants}, () => {
        const cdescription = this.descriptions[i]
        const cimg = this.imgs[i]
        const crestaurantid = this.restaurant_IDs[i]
        const crestaurantname = this.restaurantnamen[i]
        const cminimum = this.minimums[i]
        const cbestellradius = this.bestellradius[i]
        const clng = this.lng[i]
        const clat = this.lat[i]
        i++;

        return {
          description: cdescription,
          img: cimg,
          restaurantid: crestaurantid,
          restaurant: crestaurantname,
          mindestbestellwert: cminimum,
          bestellradius: cbestellradius,
          lng: clng,
          lat: clat
        }
      })
    }
  },
  watch:{
    '$store.state.searchOptionsRestaurant': function() {
      this.getStoreSearchOptionsRestaurant();
      this.loadRestaurants();
    }
  }
}
</script>

<style scoped>

</style>