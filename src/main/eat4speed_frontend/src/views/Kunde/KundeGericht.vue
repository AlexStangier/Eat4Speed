<template><v-main>
  <v-container>
    <v-row>
      <v-col cols="1"
      >
        <v-btn small @click="returnToPreviousView">Zurück</v-btn>
      </v-col>
    </v-row>
    <v-container>
      <v-container>
        <v-row>
          <v-col
              v-for="a in 2"
              :key="a"
          >
            <v-img v-if="a === 1" :src="gerichtBild" alt="Bild von Essen" max-height="500" max-width="500"></v-img>
            <v-content v-if="a === 2" align="left"> <!--Information-->
              <v-row
                  v-for="b in 10"
                  :key="b"
                  align="center"
              >
                <v-col
                    v-for="c in 3"
                    :key="c"
                >
                  <v-content v-if="b === 1 & c === 1">
                    <h1> {{ gerichtName }} </h1>
                  </v-content>
                  <v-content v-if="b === 2 & c === 1" class="low">
                    {{ restaurantName }}
                  </v-content>
                  <v-content v-if="b === 10 & c === 1" class="text-center">
                    <v-text-field label="Anzahl" v-model="gerichtAnzahl" type="number" :rules="countMinMaxRule"></v-text-field>
                  </v-content>
                  <v-content v-if="b === 10 & c === 2" class="text-center">
                    Preis
                    {{ gerichtPreis + '&euro;' }}
                  </v-content>
                  <v-content v-if="b === 10 & c === 3">
                    <v-btn small @click="addToCart">
                      Zum Warenkorb hinzufügen
                      <v-icon>mdi-cart</v-icon>
                    </v-btn>
                  </v-content>
                </v-col>
              </v-row>
            </v-content>
          </v-col>
        </v-row>
        <br>
        <v-textarea outlined readonly no-resize rows="8" v-model="gerichtBeschreibung"></v-textarea>
        </v-container>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Gericht",
  mounted(){
    this.gericht_ID = this.$store.getters.gericht_ID;
    this.loadGericht();
  },
  methods: {
    async loadGericht() {
      const ResponseGerichte = await axios.get("Gericht/getGerichtDataByGericht_ID/" + this.gericht_ID);

      console.log(ResponseGerichte);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.gerichtName = gerichtData[1];
        this.gerichtBeschreibung = gerichtData[2];
        this.gerichtPreis = parseFloat(gerichtData[3]);
        this.gerichtVerfuegbar = gerichtData[4];
        this.restaurant_ID = gerichtData[5];
        this.restaurantName = gerichtData[6];
        this.restaurantMindestbestellwert = gerichtData[7];
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_ID,config);

        console.log(responsePicture);

        if(responsePicture.status !== 204)
        {
          console.log("received Picture")
          console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          console.log(imageURL);

          this.gerichtBild = imageURL;
        }
        else
        {
          this.gerichtBild = "";
        }

      }
      this.version++;

    },
    addToCart() {

      let cartGericht = {
        gericht_ID: this.gericht_ID,
        name: this.gerichtName,
        thumbnail: this.gerichtBild,
        quantity: this.gerichtAnzahl,
        price: this.gerichtPreis
      }

      this.$store.commit("addToCartGerichte", cartGericht);
      console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    },
    returnToPreviousView() {
      if(this.$store.getters.searchType==="Gerichte")
      {
        this.$router.push({name: "Kunde"});
      }
      else if(this.$store.getters.searchType==="Restaurants")
      {
        this.$router.push({name: "KundeAuswahlseiteRestaurant"});
      }
      else if(this.$store.getters.searchType==="Favoriten")
      {
        this.$router.push({name: "Favorites"})
      }
    }
  },
  data: () => ({
    gerichtName: "",
    gerichtBeschreibung: "",
    gerichtBild: "",
    gerichtPreis: 0.0,
    gerichtVerfuegbar: "",
    gerichtAnzahl: 1,
    cartGerichte: "",
    restaurant_ID: "",
    restaurantName: "",
    restaurantMindestbestellwert: "",
    gericht_ID: "",
    version: 0,
    countMinMaxRule:[
        v => (v && v >= 1) || "Bestellungen müssen über 1 sein",
        v => (v && v < 50) || "Bestellungen über 50 Stück geht nicht",
    ],

  }),
}
</script>

<style scoped>

</style>