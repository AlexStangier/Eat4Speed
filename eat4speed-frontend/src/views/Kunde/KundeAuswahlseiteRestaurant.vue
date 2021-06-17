PASTEBIN
API
TOOLS
FAQ
paste
LOGIN SIGN UP
SHARE
TWEET
Guest User
Untitled
A GUEST
JUN 13TH, 2021
3
24 HOURS
Not a member of Pastebin yet? Sign Up, it unlocks many cool features!
19.54 KB

<template>
  <v-main>
    <v-container
        :key="version2"
    >
      <v-card
          class="mx-auto"
          tile
      >
        <v-row no-gutters>
          <v-col
              cols="4"
          >
            <v-container>
              <v-img alt="Bild von Restaurant" max-width="600" :src="this.img"></v-img>
            </v-container>
          </v-col>
          <v-col>
            <v-container
                fluid
            >
              <v-row
                  no-gutters
                  v-for="b in 5"
                  :key="b"
              >
                <v-col
                    v-for="c in 2"
                    :key="c"
                    class="pa-2"
                >
                  <v-card
                      v-if="b === 1 && c === 1"
                      class="text-sm-h4"
                      flat
                  >
                    {{restaurantName}}
                  </v-card>

                  <v-card
                      v-if="b === 1 && c === 2"
                      class="text-right"
                      flat
                  >
                    <div v-if="restaurantIsFav === true">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                         class="html-editor-align-right" small="true" right
                          @mousedown="deleteFromFavorites"
                          v-bind="attrs"
                          v-on="on"
                      >
                        <v-icon>mdi-heart</v-icon>
                      </v-btn>
                    </template>
                    <span>Aus Favoriten entfernen</span>
                  </v-tooltip>
                </div>
                <div v-else>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                          small="true" right
                          @mousedown="addToFavorites"
                          v-bind="attrs"
                          v-on="on"
                      >
                        <v-icon>mdi-heart-outline</v-icon>
                      </v-btn>
                    </template>
                    <span>Zu Favoriten hinzufügen</span>
                  </v-tooltip>
                </div>
                  </v-card>

                  <v-card
                      v-if="b === 2 && c === 1"
                      flat
                  >
                    <v-row
                        no-gutters
                    >
                      <v-rating
                          readonly
                          length="5"
                          half-icon="$ratingHalf"
                          half-increments
                          hover="true"
                          dense
                          small
                          :value="restaurantRating"
                      ></v-rating>
                      <span
                          class="ml-1"
                      >
                     {{restaurantRating}}
                   </span>
                    </v-row>
                  </v-card>

                  <v-card
                      v-if="b === 2 && c === 2"
                      class="text-right"
                      flat
                  >
                    <!--ToDO-->
                    <v-dialog
                        max-width="50%"
                    >
                      <template v-slot:activator="{ on, attrs}">
                        <v-btn
                            v-bind="attrs"
                            v-on="on"
                            color="primary"
                            tile
                        >
                          Bewertungen ({{ restaurantBewertungCount }})
                        </v-btn>
                      </template>
                      <template v-slot:default="dialog">
                        <v-card>
                          <v-toolbar class="text-h4">Bewertungen</v-toolbar>

                          <v-data-table
                              :headers="headers"
                              :items="test123"
                              :items-per-page="5"
                              class="elevation-1"
                          ></v-data-table>

                          <v-divider></v-divider>
                          <v-card-actions>
                            <v-rating
                                x-large
                                v-model="userRating"
                                hover
                            >
                            </v-rating>
                          </v-card-actions>
                          <v-card-actions>
                            <v-textarea label="Kommentar" no-resize="true" clearable="true" rows="1" v-model="userComment"></v-textarea>
                          </v-card-actions>
                          <v-card-actions class="justify-end">
                            <v-btn
                                @click="addBewertung"
                                @mouseup="dialog.value = false"
                                color="primary"
                                tile
                            >
                              Bewerten
                            </v-btn>
                            <v-btn
                                @click="dialog.value = false"
                                color="error"
                                tile
                                class="ml-2"
                            >
                              Schließen
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      </template>
                    </v-dialog>
                  </v-card>


                  <!--Beschreibung und Addresse-->
                  <v-card
                      v-if="b === 3 && c === 1"
                      flat
                  >
                    {{ restaurantDescription }}
                  </v-card>

                  <v-card
                      v-if="b === 4 && c === 1"
                      flat
                  >
                    {{ restaurantAddress}}
                  </v-card>
                  <span
                      v-if="b === 5 && c === 1"
                  >
                 Telefon: {{ restaurantPhoneNumber}}
               </span>
                  <span
                      v-if="b === 5 && c === 2"
                      class="text-right"
                  >
                 <v-card flat>
                   Mindestbestellwert: {{ restaurantMindestbestellwert +' €'}}
                 </v-card>
               </span>
                </v-col>
              </v-row>
            </v-container>
          </v-col>
        </v-row>
      </v-card>
      <v-card
          class="mx-auto"
          tile
      >
        <v-card-title>
          <v-col>
            <v-row class="text-h4">
              Speisekarte
            </v-row>
            <v-row>
              <v-btn
                  depressed
                  :color="btnType === 0 ? 'primary' : 'blue-grey'"
                  class="mt-2 white--text"
                  width="150"
                  tile
                  @click="changeDisplayGerichte"
              >
                Speisen
              </v-btn>
              <v-btn
                  depressed
                  :color="btnType === 1 ? 'primary' : 'blue-grey'"
                  tile
                  class="mt-2 ml-2 white--text"
                  width="150"
                  @click="changeDisplayGetraenke"
              >
                Getränke
              </v-btn>
            </v-row>
          </v-col>
        </v-card-title>
        <v-divider></v-divider>

        <v-virtual-scroll
            :items="items"
            :item-height="200"
            max-height="500"
            :key="version"
        >
          <template v-slot:default="{ item }">

            <v-card
                flat
                tile
            >
              <v-container class="pa-1">
                <v-row>
                  <v-col
                      cols="4"
                  >
                    <v-card
                        flat
                        tile
                        class="center"
                    >
                      <v-img alt="Bild von Essen" min-height="180" max-height="180" max-width="400" :src="item.img"></v-img>
                    </v-card>
                  </v-col>
                  <v-col>
                    <v-row
                        v-for="d in 4"
                        :key="d"
                    >
                      <v-col>
                        <v-card
                            v-if="d === 1"
                            class="text-h5 text-decoration-underline"
                            flat
                        >
                          {{ item.name }}
                        </v-card>
                        <v-card
                            v-if="d === 2"
                            class="text-subtitle-1"
                            flat
                        >
                          {{ item.description }}
                        </v-card>
                      </v-col>
                      <v-col>
                        <v-card
                            v-if="d === 2"
                            class="text-right"
                            flat
                        >
                          {{'Stückpreis: ' + item.price + ' €' }}
                        </v-card>
                        <v-card
                            v-if="d === 4"
                            class="text-right"
                            flat
                        >
                          <v-btn
                              small="true"
                              bottom="bottom"
                              color="primary"
                              tile
                              :to="{name: 'Gericht'}"
                              @mouseover="selectGericht(item)"
                          >
                            Details
                          </v-btn>

                          <v-menu
                              bottom
                              offset-y
                              :close-on-content-click="false"
                          >
                            <template v-slot:activator="{ on, attrs}">
                              <v-btn
                                  v-bind="attrs"
                                  v-on="on"
                                  small="true"
                                  bottom="bottom"
                                  class="ml-1"
                                  color="primary"
                                  tile
                                  @mouseover="selectItem(item)"
                                  @click="gerichtAnzahl=1"
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
                                <v-text-field label="Anzahl" v-model="gerichtAnzahl" type="number" :rules="countMinMaxRule"></v-text-field> <!--TODO-->
                              </v-list-item>
                              <v-btn
                                  @click="addToCart()"
                                  small="small"
                                  color="primary"
                                  tile
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

      </v-card>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "KundeAuswahlseiteRestaurant",
  created() {
    this.selectedRestaurant_ID = this.$store.getters.selectedRestaurant_ID;
    this.getLoggedInKunde()
    this.loadRestaurant();
    this.displayGetraenke = false;
    this.loadGerichte();
    this.loadBewertungen();
  },
  methods: {
    async getLoggedInKunde()
    {
      const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$cookies.get('emailAdresse'));
      this.currentKunde_ID = response.data[0];
    },
    async loadRestaurant() {

      this.restaurantIsFav = false;
      const ResponseFavoriten = await axios.get("Restaurant/getRestaurantDataByKundennummer_Favoriten/"+this.currentKunde_ID);
      for(let i = 0; i < ResponseFavoriten.data.length; i++)
      {
        let favData = ResponseFavoriten.data[i];
        if(this.selectedRestaurant_ID === favData[0])
        {
          this.restaurantIsFav = true;
        }
      }

      const ResponseRestaurant = await axios.get("Restaurant/getAllRestaurantDataByRestaurant_ID/"+this.selectedRestaurant_ID);
      let restaurantData = ResponseRestaurant.data[0];
      this.restaurantName = restaurantData[1];
      this.restaurantDescription = restaurantData[2];
      this.restaurantMindestbestellwert = restaurantData[3];
      this.restaurantBestellradius = restaurantData[4];
      this.restaurantAddress=restaurantData[5]+" "+restaurantData[6]+" "+ restaurantData[7]+" "+restaurantData[8];
      this.restaurantPhoneNumber=restaurantData[9]

      const ResponseBewertung = await axios.get("Bewertung/getAverageBewertungAndCountBewertungByRestaurant_ID/"+this.selectedRestaurant_ID);
      if(ResponseBewertung.data.length>0)
      {
        this.restaurantRating = ResponseBewertung.data[0][0];
        this.restaurantBewertungCount = ResponseBewertung.data[0][1];
      }

      const config = { responseType:"arraybuffer" };
      const responsePicture = await axios.get("/RestaurantBilder/getBild/"+this.selectedRestaurant_ID,config);

      console.log(responsePicture);

      if(responsePicture.status !== 204)
      {
        console.log("received Picture")
        console.log(responsePicture.data);

        let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

        let imageURL = URL.createObjectURL(pictureBlob);
        console.log(imageURL);

        this.img = imageURL;
      }
      else
      {
        this.img = "";
      }

      this.version2++;
    },
    async loadGerichte() {

      let gerichtPath;

      if(this.displayGetraenke === false)
      {
        gerichtPath = "Gericht/getAllGerichtDataRestaurantSpeisekarte/";
      }
      else{
        gerichtPath = "Gericht/getAllGetraenkDataRestaurantSpeisekarte/";
      }

      const ResponseGerichte = await axios.get(gerichtPath + this.selectedRestaurant_ID);

      console.log(ResponseGerichte);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];
        this.gerichtIDs[i] = gerichtData[0];
        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gerichtIDs[i],config);

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
      console.log(this.imgs);
      this.amountGerichte = 0;
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
    async loadBewertungen() {

      const responseBewertungen = await axios.get("Bewertung/getBewertungDataByRestaurant_ID/"+this.selectedRestaurant_ID);

      for (let i = 0; i < responseBewertungen.data.length; i++) {
        let bewertungData = responseBewertungen.data[i];
        this.reviewRating[i] = bewertungData[3];
        this.reviewComment[i] = bewertungData[4];
        this.reviewUsername[i] = bewertungData[6];

        this.test123.push({ reviewRating: bewertungData[3], reviewComment: bewertungData[4], reviewUsername: bewertungData[6] });
      }

      const responseBewertung = await axios.get("Bewertung/getBewertungDataByKundennummerAndRestaurant_ID/"+this.currentKunde_ID+"/"+this.selectedRestaurant_ID);

      if(responseBewertung.data.length>0)
      {
        this.userRating = responseBewertung.data[0][3];
        this.userComment = responseBewertung.data[0][4];
      }

      console.log(this.reviewUsername);
      console.log(this.reviewComment);
      console.log(this.reviewRating);

      this.amountReviews = 0;
      this.amountReviews = responseBewertungen.data.length;
      this.versionreview++;
    },
    async addBewertung(){

      var today = new Date();

      var bewertung = {
        kundennummer: this.currentKunde_ID,
        restaurant_ID: this.selectedRestaurant_ID,
        sterne: this.userRating,
        text: this.userComment,
        datum: today
      }

      await axios.put("Bewertung",bewertung);

      this.loadBewertungen();
    },
    changeDisplayGetraenke() {
      if(this.displayGetraenke===false)
      {
        this.btnType = 1;
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

    },
    changeDisplayGerichte() {
      if(this.displayGetraenke===true)
      {
        this.btnType = 0;
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

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
    selectItem(item) {
      this.selectedItem = item;
    },
    async addToFavorites() {
      var today = new Date();
      const restaurantFavorite = {
        restaurant_ID: this.selectedRestaurant_ID,
        kundennummer: this.currentKunde_ID,
        hinzufuegedatum: today,
        //TODO get anzahl_Bestellungen from Database
        anzahl_Bestellungen: 0
      }

      await axios.post("Favoritenliste_Restaurants", restaurantFavorite);
      this.loadRestaurant();
    },
    async deleteFromFavorites(){
      await axios.delete("Favoritenliste_Restaurants/remove/"+this.currentKunde_ID+"/"+this.selectedRestaurant_ID);
      this.loadRestaurant();
    },
    addToCart() {

      console.log("Selected: "+ this.selectedItem.id+", "+this.selectedItem.name);
      let cartGericht = {
        gericht_ID: this.selectedItem.id,
        name: this.selectedItem.name,
        thumbnail: this.selectedItem.img,
        quantity: this.gerichtAnzahl,
        price: this.selectedItem.price
      }

      this.$store.commit("addToCartGerichte", cartGericht);
      console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    },
  },
  data: () => ({
    selectedRestaurant_ID:"",
    currentKunde_ID:"",
    selectedGericht_ID:"",
    selectedItem: "",
    gerichtAnzahl: 0,
    restaurantBewertungCount:0,
    displayGetraenke:"",
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    img: "",
    restaurantIsFav: false,
    restaurants: [],
    gerichtIDs: [],
    minimums: [],
    ratings: [],
    amountGerichte:4,
    amountReviews:4,
    version:0,
    version2:0,
    versionreview:0,
    restaurantName:"",
    restaurantDescription:"",
    restaurantAddress:"",
    restaurantRating:0,
    restaurantPhoneNumber:"",
    restaurantMindestbestellwert:"",
    restaurantBestellradius:"",
    bewertung_ID:"",

    userRating:0,
    userComment:"",
    reviewUsername: [],
    reviewRating: [],
    reviewComment: [],
    test123: [],
    headers: [
      {
        text: 'Kommentare',
        align: 'start',
        sortable: false,
        value: 'reviewComment',
      },
    ],
    countMinMaxRule:[
      v => (v && v >= 1) || "Bestellungen müssen über 1 sein",
      v => (v && v < 50) || "Bestellungen über 50 Stück geht nicht",
    ],
    btnType: 0,
  }),



  computed: {
    items(){
      let i = 0
      return Array.from({ length: this.amountGerichte}, () => {
        const cid = this.gerichtIDs[i]
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        const crating = this.ratings[i]
        i++;

        return {
          id: cid,
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          rating: crating
        }
      })
    },
  }
}
</script>

<style scoped>

</style>