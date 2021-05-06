<template>
  <v-main>
    <v-container :key="version2">
      <v-card class="mx-auto">
        <v-card-title>
          <v-img alt="Bild von Restaurant" max-height="35%" max-width="35%" src="https://www.onpsx.de/uploads/mediapool/dvdreviews/sponge1-03.jpg"></v-img>

          <v-list-item-content>
            <v-item-group>
              <v-list-item-action>
                <h1>{{restaurantName}}</h1>
              </v-list-item-action>
              <v-list-item-action>
                <v-btn small="true" right="right">
                  <v-icon>mdi-heart</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-item-group>

            <v-list-item-action></v-list-item-action>

            <v-item-group>
              <v-list-item-action>
                Bewertung(12)
              </v-list-item-action>
              <v-list-item-action>
                <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small :value="restaurantRating"></v-rating>
              </v-list-item-action>
              <v-list-item-action>
                {{restaurantRating}}
              </v-list-item-action>
            </v-item-group>

            <v-list-item-action></v-list-item-action>

            <v-list-item-group>
              <v-list-item-action>
                {{ restaurantDescription }}
              </v-list-item-action>
            </v-list-item-group>

            <v-list-item-group>
              <v-list-item-action>
                {{ restaurantAddress}}
              </v-list-item-action>

            </v-list-item-group>
            <v-list-item-group>
              <v-list-item-action>
                Telefon: {{ restaurantPhoneNumber}}
              </v-list-item-action>
            </v-list-item-group>

            <v-list-item-group>
              <v-list-item-action>
                Mindestbestellwert: {{ restaurantMindestbestellwert}} €
              </v-list-item-action>
              <v-list-item-action>
                Bestellradius: {{ restaurantBestellradius}} km
              </v-list-item-action>
            </v-list-item-group>

          </v-list-item-content>

        </v-card-title>
        <v-divider></v-divider>

        <v-card-title>
          <v-col>
            <v-row class="text-h4">
              Speisekarte
            </v-row>
            <v-row>
              <v-list-item-action>
                <v-btn
                    color="red"
                    dark
                    align="right"
                    class="mt-5"
                    @click="changeDisplayGerichte"
                >
                  Gerichte
                </v-btn>
              </v-list-item-action>
              <v-list-item-action>
                <v-btn
                    color="red"
                    dark
                    align="right"
                    class="mt-5"
                    @click="changeDisplayGetraenke"
                >
                  Getränke
                </v-btn>
              </v-list-item-action>
            </v-row>
          </v-col>
        </v-card-title>
        <v-divider></v-divider>
        <v-virtual-scroll
            :items="items"
            :item-height="300"
            max-height="500"
            :key="version"
        >
          <template v-slot:default="{ item }">
            <v-list-item>
              <v-list-item-content>
                <v-img alt="Bild von Essen" max-height="250" max-width="250" :src="item.img"></v-img>
              </v-list-item-content>
              <v-list-item-content>
                <v-list-item-group align="left">
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                  <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                  <br>
                  <br>
                </v-list-item-group>
              </v-list-item-content>
              <v-list-item-content></v-list-item-content>
              <v-list-item-group align="left">
                <v-list-item-content>{{ item.price }}</v-list-item-content>
                <v-btn small="true" bottom="bottom" @mouseover="selectGericht(item)" :to="{name: 'Gericht'}">Bestellen</v-btn>
              </v-list-item-group>
            </v-list-item>
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
  mounted() {
    this.selectedRestaurant_ID = this.$store.getters.selectedRestaurant_ID;
    this.loadRestaurant();
    this.displayGetraenke = false;
    this.loadGerichte();
  },
  methods: {
    async loadRestaurant() {
      const ResponseRestaurant = await axios.get("Restaurant/getAllRestaurantDataByRestaurant_ID/"+this.selectedRestaurant_ID);
      let restaurantData = ResponseRestaurant.data[0];
      this.restaurantName = restaurantData[1];
      this.restaurantDescription = restaurantData[2];
      this.restaurantMindestbestellwert = restaurantData[3];
      this.restaurantBestellradius = restaurantData[4];
      this.restaurantAddress=restaurantData[5]+" "+restaurantData[6]+" "+ restaurantData[7]+" "+restaurantData[8];
      this.restaurantPhoneNumber=restaurantData[9]
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
    changeDisplayGetraenke() {
      if(this.displayGetraenke===false)
      {
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

    },
    changeDisplayGerichte() {
      if(this.displayGetraenke===true)
      {
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
  },
  data: () => ({
    selectedRestaurant_ID:"",
    selectedGericht_ID:"",
    displayGetraenke:"",
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurants: [],
    gerichtIDs: [],
    minimums: [],
    ratings: [],
    amountGerichte:4,
    version:0,
    version2:0,
    restaurantName:"",
    restaurantDescription:"",
    restaurantAddress:"",
    restaurantRating:"",
    restaurantPhoneNumber:"",
    restaurantMindestbestellwert:"",
    restaurantBestellradius:""
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
    }
  }
}
</script>

<style scoped>

</style>