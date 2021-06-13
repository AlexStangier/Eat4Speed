<template>
  <v-main>
    <v-container>

      <h1>Favorites</h1>
      <v-btn @click="setDisplayGerichteToTrue">Gerichte</v-btn>
      <v-btn @click="setDisplayGerichteToFalse">Restaurants</v-btn>
      <v-container>
        <v-virtual-scroll
            :items="items"
            :item-height="310"
            max-height="600"
        >
          <template v-slot:default="{ item }" v-resize>
            <v-list-item v-resize>
              <v-list-item-content>
                <v-img alt="Bild von Essen" max-height="300" max-width="300" position="center center" :src="item.img"></v-img>
              </v-list-item-content>
              <v-list-item-content>
                <v-list-item-group class="text-left">
                  <div v-if="displayGerichte===true">
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                    <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                  </div>
                  <div v-else>
                    <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                  </div>
                </v-list-item-group>
              </v-list-item-content>
              <v-list-item-content>
                <v-list-item-group class="text-right">
                  <v-list-item-content>Anzahl Bestellungen: {{item.anzahlBestellungen}}</v-list-item-content>
                </v-list-item-group>
              </v-list-item-content>
              <v-list-item-group class="text-right">
                <v-list-item-content>
                  <div v-if="displayGerichte===true">
                    Preis: {{ item.price}}
                  </div>
                  <div v-else>
                  </div>
                </v-list-item-content>
                <div v-if="displayGerichte===true">
                  <v-btn small="true" bottom="bottom" :to="{name: 'Gericht'}" @mouseover="selectGericht(item)">Details</v-btn>
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
                      <v-btn @click="addToCart()" small="small">Zum Warenkorb hinzufügen</v-btn>
                    </v-list>
                  </v-menu>
                  <v-btn small="true" bottom="bottom" @mouseenter="selectItem(item)" @click="setStoreRestaurant_ID">Zur Speisekarte</v-btn>
                </div>
                <div v-else>
                  <v-btn small="true" bottom="bottom" @mouseenter="selectItem(item)" @click="setStoreRestaurant_ID">Zur Speisekarte</v-btn>
                </div>
                <v-btn @mouseenter="selectItem(item)" @click="deleteFromFavorites">Aus Favoriten entfernen</v-btn>
              </v-list-item-group>
            </v-list-item>
            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Favorites",
  mounted() {
    //TODO change later to actual value
    this.getLoggedInKunde();
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
      const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$store.getters.getLoginData.auth.username)
      this.loggedInKunde_ID = response.data[0];
    },
    setDisplayGerichteToTrue() {
      this.displayGerichte = true;
      this.loadGerichte();
    },
    setDisplayGerichteToFalse() {
      this.displayGerichte = false;
      this.loadGerichte()
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
        }

        this.amountGerichte = 0;
        this.amountGerichte = ResponseRestaurants.data.length;
      }

      this.version++;
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
        price: this.selectedItem.price
      }

      this.$store.commit("addToCartGerichte", cartGericht);
      console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    }
  },
  data: () => ({
    displayGerichte: true,
    loggedInKunde_ID: 0,
    amountGerichte: 4,
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