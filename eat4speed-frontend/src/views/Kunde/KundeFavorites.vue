<template>
  <v-main>
    <v-container>
      <v-card class="mx-auto">
        <v-card-title>
          <v-col>
            <v-row class="text-h4">
              Favoriten
            </v-row>
            <v-row>
              <v-btn
                  @click="setDisplayGerichteToTrue"
                  :color="btnType === 0 ? 'primary' : 'blue-grey'"
                  class="mt-2 white--text"
                  width="150"
                  tile
              >
                Gerichte
              </v-btn>
              <v-btn
                  @click="setDisplayGerichteToFalse"
                  :color="btnType === 1 ? 'primary' : 'blue-grey'"
                  class="mt-2 ml-2 white--text"
                  width="150"
                  tile
              >
                Restaurants
              </v-btn>
            </v-row>
          </v-col>
        </v-card-title>
        <v-divider></v-divider>
        <v-virtual-scroll
            :items="items"
            :item-height="230"
            max-height="650"
            :key="favoritenKey"
        >
          <template v-slot:default="{ item }" v-resize>
            <v-card
                flat
                tile
            >
              <v-container>
                <v-row>
                  <v-col
                      cols="3"
                  >
                    <v-card
                        flat
                        tile
                        class="center"
                    >
                      <v-img alt="Bild von Essen" min-height="200" max-height="200" max-width="400" :src="item.img"></v-img>
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
                            v-if="a === 1 && displayGerichte===true"
                            class="text-h5 text-decoration-underline"
                            flat
                        >
                          {{ item.name }}
                        </v-card>
                        <v-card
                            v-if="a === 1 && displayGerichte===true"
                            class="text-subtitle-1"
                            flat
                        >
                          {{ item.description }}
                        </v-card>
                        <v-card
                            v-if="a === 1 && displayGerichte===false"
                            class="text-h5 text-decoration-underline"
                            flat
                        >
                          {{item.restaurant}}
                        </v-card>
                        <v-card
                            v-if="a === 2 && displayGerichte===true"
                            class="text-subtitle-1"
                            flat
                        >
                          {{item.restaurant}}
                        </v-card>
                      </v-col>
                      <v-col class="text-right">
                        <v-card
                            v-if="a === 1"
                            flat
                        >
                          <v-btn
                              small="true"
                              icon
                              @mouseenter="selectItem(item)"
                              @click="deleteFromFavorites"
                          >
                            <v-icon>mdi-heart-broken</v-icon>
                          </v-btn>
                        </v-card>
                        <v-card
                            v-if="a === 2 && displayGerichte===true"
                            class="text-subtitle-1"
                            flat
                        >
                          Preis: {{ item.price + ' €'}}
                        </v-card>
                        <v-card
                            v-if="a === 2"
                            flat
                            class="text-subtitle-1"
                        >
                          Anzahl Bestellungen: {{item.anzahlBestellungen}}
                        </v-card>
                        <v-card
                            v-if="a === 3"
                            flat
                            class="text-right"
                        >
                          <v-dialog
                              max-width="50%"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-btn
                                  v-bind="attrs"
                                  v-on="on"
                                  small
                                  color="primary"
                                  class="ml-1"
                                  tile
                                  @mouseenter="fillAllergene(item)"
                              >
                                Allergene
                              </v-btn>
                            </template>
                            <template v-slot:default="dialog">
                              <v-card>
                                <v-container>
                                  <v-row
                                      class="pa-2"
                                  >
                                    <v-select
                                        readonly
                                        :items="allergeneGericht"
                                        v-model="allergeneGericht"
                                        chips
                                        label="Allergene"
                                        multiple
                                        :key="allergeneKey"
                                    >

                                    </v-select>
                                  </v-row>
                                  <v-row
                                      class="pa-2"
                                      justify="end"
                                  >
                                    <v-btn
                                        class="ml-1 justify-end"
                                        @click="dialog.value = false"
                                        color="error"
                                        tile
                                    >
                                      Schließen
                                    </v-btn>
                                  </v-row>
                                </v-container>
                              </v-card>
                            </template>
                          </v-dialog>
                          <v-btn
                              v-if="displayGerichte===true"
                              color="primary"
                              tile
                              class="ml-1"
                              small="true"
                              bottom="bottom"
                              :to="{name: 'Gericht'}"
                              @mouseover="selectGericht(item)"
                          >
                            Details
                          </v-btn>
                          <v-btn
                              color="primary"
                              tile
                              class="ml-1"
                              small="true"
                              bottom="bottom"
                              @mouseenter="selectItem(item)"
                              @click="setStoreRestaurant_ID"
                          >
                            Zur Speisekarte
                          </v-btn>
                          <v-menu
                              v-if="displayGerichte===true"
                              bottom
                              left
                              offset-y
                              :close-on-content-click="false"
                          >
                            <template v-slot:activator="{ on, attrs}">
                              <v-btn
                                  color="primary"
                                  tile
                                  class="ml-1"
                                  v-bind="attrs"
                                  v-on="on"
                                  small="true"
                                  :disabled="item.available !== 'verfügbar'"
                                  bottom="bottom"
                                  @mouseover="selectItem(item)"
                                  @click="gerichtAnzahl=0"
                              >
                                Bestellen
                              </v-btn>
                            </template>
                            <v-list
                                max-width="200"
                                min-width="250"
                                class="text-center"
                            >
                              <v-list-item>
                                <v-text-field label="Anzahl" v-model="gerichtAnzahl" type="number" :rules="countMinMaxRule"></v-text-field>
                              </v-list-item>
                              <v-btn
                                  color="primary"
                                  tile
                                  @click="addToCart()"
                                  small="small"
                              >
                                Zum Warenkorb hinzufügen
                              </v-btn>
                            </v-list>
                          </v-menu>
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
        <v-card
            v-if="amountGerichte === -1"
            tile
        >
          <v-row justify="center">
            <v-progress-circular
                indeterminate
                color="primary"
                :size="70"
                :width="7"
            ></v-progress-circular>
          </v-row>
        </v-card>
        <v-card
            v-if="amountGerichte === 0"
            tile
            class="text-center text-h5"
        >
          Keine Favoriten gefunden
        </v-card>
      </v-card>
    </v-container>

  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Favorites",
  async mounted() {
    await this.getLoggedInKunde();
    this.loadGerichte();
  },
  beforeRouteLeave(to, from, next) {
    this.$store.commit("changeSearchType","Favoriten");
    this.setStoreGericht_ID();
    next();
  },
  methods: {
    async getLoggedInKunde()
    {
      const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$cookies.get('emailAdresse'))
      this.loggedInKunde_ID = response.data[0];
    },
    setDisplayGerichteToTrue() {
      this.btnType = 0
      this.displayGerichte = true;
      this.loadGerichte();
    },
    setDisplayGerichteToFalse() {
      this.btnType = 1;
      this.displayGerichte = false;
      this.loadGerichte();
    },
    setStoreRestaurant_ID() {
      this.$store.commit("changeSelectedRestaurant_ID",this.selectedItem.restaurant_ID);
      this.$router.push({name: "KundeAuswahlseiteRestaurant"});
    },
    async loadGerichte() {

      if(this.displayGerichte === true)
      {
        const ResponseGerichte = await axios.get("Gericht/getGerichtDataByKundennummer_Favoriten/"+this.loggedInKunde_ID);

        console.log(ResponseGerichte);

        //console.log("Verarbeite ResponseGerichte")

        for (let i = 0; i < ResponseGerichte.data.length; i++) {
          let gerichtData = ResponseGerichte.data[i];

          this.gericht_IDs[i] = gerichtData[0];
          this.names[i] = gerichtData[1];
          this.descriptions[i] = gerichtData[2];
          this.prices[i] = gerichtData[3];

          if(gerichtData[4] === 0)
          {
            this.availabilities[i] = "nicht verfügbar";
          }
          else
          {
            this.availabilities[i] = "verfügbar";
          }
          this.restaurant_IDs[i] = gerichtData[8];
          this.restaurantnamen[i] = gerichtData[9];
          this.minimums[i] = gerichtData[7];
          this.anzahlBestellungen[i] = gerichtData[6];
          this.hinzufuegedaten[i] = gerichtData[7];
          let ResponseAmount = await axios.get("Bestellung/getAllOrdersFromCustomerByDishId/"+this.loggedInKunde_ID+"/"+gerichtData[0]);
          console.log(ResponseAmount);

          if(ResponseAmount.data!==0)
          {
            this.anzahlBestellungen[i] = ResponseAmount.data;
          }
        }

        //console.log("Suche nach Bildern");

        for (let i = 0; i < ResponseGerichte.data.length; i++)
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

        //console.log("Verarbeitung abgeschlossen")
        console.log(this.imgs);
        this.amountGerichte = 0;
        this.amountGerichte = ResponseGerichte.data.length;
      }
      else {
        const ResponseRestaurants = await axios.get("Restaurant/getRestaurantDataByKundennummer_Favoriten/"+this.loggedInKunde_ID);

        for (let i = 0; i < ResponseRestaurants.data.length; i++) {
          let restaurantData = ResponseRestaurants.data[i];

          this.restaurant_IDs[i] = restaurantData[0];
          this.restaurantnamen[i] = restaurantData[1];
          this.minimums[i] = restaurantData[3];
          this.anzahlBestellungen[i] = restaurantData[11];
          this.hinzufuegedaten[i] = restaurantData[12];

          let ResponseAmount = await axios.get("Bestellung/getAllOrdersFromRestaurantId/"+this.loggedInKunde_ID+"/"+restaurantData[0]);
          console.log(ResponseAmount);

          if(ResponseAmount.data!==0)
          {
            this.anzahlBestellungen[i] = ResponseAmount.data;
          }

        }

        for (let i = 0; i < ResponseRestaurants.data.length; i++)
        {
          const config = { responseType:"arraybuffer" };
          const responsePicture = await axios.get("/RestaurantBilder/getBild/"+this.restaurant_IDs[i],config);

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

        this.amountGerichte = 0;
        this.amountGerichte = ResponseRestaurants.data.length;
      }

      this.favoritenKey++;
    },
    selectItem(item) {
      console.log("Gericht selected "+item.id);
      this.selectedItem = item;
    },
    selectGericht(item) {
      console.log("Gericht selected "+item.id);
      this.selectedGericht_ID = item.id;
      this.setStoreGericht_ID()
    },
    setStoreGericht_ID() {
      this.$store.commit("changeGericht_ID",this.selectedGericht_ID);
      console.log("changed gericht_ID to "+this.$store.getters.gericht_ID);
    },
    async deleteFromFavorites(){
      if(this.displayGerichte===true)
      {
        await axios.delete("Favoritenliste_Gerichte/remove/"+this.loggedInKunde_ID+"/"+this.selectedItem.id);
      }
      else
      {
        await axios.delete("Favoritenliste_Restaurants/remove/"+this.loggedInKunde_ID+"/"+this.selectedItem.restaurant_ID)
      }

      this.loadGerichte();
    },
    addToCart() {

      console.log("Selected: "+ this.selectedItem.id+", "+this.selectedItem.name);
      let cartGericht = {
        gericht_ID: this.selectedItem.id,
        name: this.selectedItem.name,
        thumbnail: this.selectedItem.img,
        quantity: this.gerichtAnzahl,
        price: this.selectedItem.price,
        restaurant_ID: this.selectedItem.restaurant_ID,
        num: Math.random() * (999999 - 1) + 1
      }

      this.$store.commit("addToCartGerichte", cartGericht);
      console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    },
  },
  data: () => ({
    displayGerichte: true,
    loggedInKunde_ID: 0,
    amountGerichte: -1,
    selectedGericht_ID: "",
    selectedItem: "",
    version: 0,
    kategorieVersion: 0,
    gericht_IDs: [],
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen:[],
    distances: [],
    minimums: [],
    availabilities: [],
    allergeneGericht: [],
    anzahlBestellungen: [],
    kategorien: [],
    selectedKategorien: [],
    favoritenlisteGerichte_IDs: [],
    hinzufuegedaten: [],
    hinzufuegedatumAssigned: [],
    isFavorite: [],
    gerichtAnzahl: 1,
    countMinMaxRule:[
      v => (v && v >= 1) || "Bestellungen müssen über 1 sein",
      v => (v && v < 50) || "Bestellungen über 50 Stück geht nicht",
    ],
    btnType: 0,
    allergeneKey: 0,
    favoritenKey: 0,
  }),
  computed: {
    items(){
      let i = 0
      return Array.from({ length: this.amountGerichte}, () => {
        const cid = this.gericht_IDs[i]
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        const crestaurant = this.restaurantnamen[i]
        const crestaurant_ID = this.restaurant_IDs[i]
        const chinzufuegedatum = this.hinzufuegedaten[i]
        const cavailable = this.availabilities[i]
        const cminimum = this.minimums[i]
        const canzahlBestellungen = this.anzahlBestellungen[i]
        i++;

        return {
          id: cid,
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          restaurant: crestaurant,
          restaurant_ID: crestaurant_ID,
          hinzufuegedatum: chinzufuegedatum,
          available: cavailable,
          minimum: cminimum,
          anzahlBestellungen: canzahlBestellungen
        }
      })
    }
  }
}
</script>

<style scoped>

</style>