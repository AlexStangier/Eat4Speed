<template>
  <v-main>
    <v-container>
      <v-container>
        <v-row no-gutters>
          <v-col sm="2">
            <v-select
                v-model="selectedRating"
                label="Bewertung"
                :items="selectRating"
                clearable="true"
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
                v-model="selectedArea"
                label="Entfernung"
                :items="selectArea"
                clearable="true"
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
                <v-subheader>Mindestbestellwert</v-subheader>
                <v-list-item>
                  <v-slider
                      v-model="filterCosts"
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
                  <v-container fluid>
                    <v-select
                        v-model="filterOptions"
                        :items="Options"
                        label="Filteroptionen"
                        multiple
                    >
                      <template v-slot:selection="{ item, index }">
                        <v-chip v-if="index < 3">
                          <span>{{ item }}</span>
                        </v-chip>
                        <v-chip v-if="index === 3">
                            <span
                                class="grey--text caption"
                            >
                              (+{{ filterOptions.length - 3 }} weitere)
                            </span>
                        </v-chip>
                      </template>
                    </v-select>
                  </v-container>
                </v-list-item>
                <v-list-item>
                  <v-btn color="error">Filter löschen</v-btn>
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
    console.log(this.searchString);

    this.loadRestaurants();
  },
  methods: {
    selectRestaurant(item) {
      console.log("Restaurant selected "+item.restaurantid);
      this.selectedRestaurant = item.restaurantid;
    },
    getStoreSeachString() {
      this.searchString = this.$store.getters.searchString;
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
    },
    setStoreRestaurant_ID() {
      this.$store.commit("changeSelectedRestaurant_ID",this.selectedRestaurant);
      this.$router.push({name: "KundeAuswahlseiteRestaurant"});
    },
    async loadRestaurants() {
      const ResponseRestaurants = await axios.get("Restaurant/getRestaurantDataByRestaurantName/" + this.searchString);

      console.log(ResponseRestaurants);

      for (let i = 0; i < ResponseRestaurants.data.length; i++) {
        let restaurantData = ResponseRestaurants.data[i];

        this.restaurant_IDs[i] = restaurantData[0];
        this.restaurantnamen[i] = restaurantData[1];
        this.descriptions[i] = restaurantData[2];
        this.minimums[i] = restaurantData[3];
        this.bestellradius[i] = restaurantData[4];
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
  },
  data: () => ({
    searchString: "",
    amountRestaurants: 4,
    selectedRestaurant: "",
    version: 0,
    descriptions: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen:[],
    distances: [],
    minimums: [],
    bestellradius: []
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
        i++;

        return {
          description: cdescription,
          img: cimg,
          restaurantid: crestaurantid,
          restaurant: crestaurantname,
          mindestbestellwert: cminimum,
          cbestellradius: cbestellradius
        }
      })
    }
  },
  watch:{
    '$store.state.searchString': function() {
      this.getStoreSeachString();
      this.loadRestaurants();
    }
  }
}
</script>

<style scoped>

</style>