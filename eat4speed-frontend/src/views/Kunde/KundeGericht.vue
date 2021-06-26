<template>
  <v-main>
    <v-container>
      <v-row>
        <v-col cols="1"
        >
          <v-btn
              color="primary"
              tile
              small
              @click="returnToPreviousView"
          >
            Zurück
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-card
          class="pa-2"
          tile
      >
        <v-row>
          <v-col
              cols="5"
          >
            <v-img :src="gerichtBild" alt="Bild von Essen" max-height="500" max-width="500"></v-img>
          </v-col>
          <v-col>
            <v-content align="left">
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
                  <v-content v-if="b === 10 & c === 2" class="text-right">
                    Preis:
                    {{ (gerichtPreis * gerichtAnzahl) + ' &euro;' }}
                    <v-dialog
                        max-width="50%"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            v-bind="attrs"
                            v-on="on"
                            small
                            color="primary"
                            @mouseenter="fillAllergene()"
                            tile
                            class="ml-1"
                        >
                          Allergene
                        </v-btn>
                      </template>
                      <template v-slot:default="dialog">
                        <v-card>
                          <v-container>
                            <v-select
                                readonly
                                disabled
                                :items="allergeneGericht"
                                v-model="allergeneGericht"
                                chips
                                label="Allergene"
                                multiple
                            >

                            </v-select>
                            <v-btn
                                class="ml-1 justify-end"
                                @click="dialog.value = false"
                                color="error"
                                tile
                            >
                              Schließen
                            </v-btn>
                          </v-container>
                        </v-card>
                      </template>
                    </v-dialog>
                  </v-content>
                  <v-content v-if="b === 10 & c === 3">
                    <v-btn
                        :disabled="gerichtAnzahl < 1 || gerichtAnzahl > 50 || gerichtVerfuegbar === 0"
                        small
                        @click="addToCart"
                        color="primary"
                        tile
                    >
                      <v-icon>mdi-cart</v-icon>
                      Zum Warenkorb hinzufügen
                    </v-btn>
                  </v-content>
                </v-col>
              </v-row>
            </v-content>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-card
                flat
                outlined
                tile
                min-height="150"
            >
              {{ gerichtBeschreibung }}
            </v-card>
          </v-col>
        </v-row>
      </v-card>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Gericht",
  async mounted(){
    this.gericht_ID = this.$store.getters.gericht_ID;
    await this.getLoggedInKunde();
    this.loadGericht();
  },
  methods: {
    async checkLoggedInUser() {
      if (this.$cookies.get('emailAdresse') !== undefined) {
        this.isUserLoggedInBoolean = true;
      }
    },
    async getLoggedInKunde()
    {
      if(this.isUserLoggedInBoolean)
      {
        const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$cookies.get('emailAdresse'));
        this.loggedInKunde_ID = response.data[0];
      }
    },
    async loadGericht() {
      const ResponseGerichte = await axios.get("Gericht/getGerichtDataByGericht_ID/" + this.gericht_ID);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.gerichtName = gerichtData[1];
        this.gerichtBeschreibung = gerichtData[2];
        this.gerichtPreis = parseFloat(gerichtData[3]);
        this.gerichtVerfuegbar = gerichtData[4];
        this.restaurant_ID = gerichtData[5];
        this.restaurantName = gerichtData[6];
        this.restaurantMindestbestellwert = gerichtData[7];
        this.restaurantBestellradius = gerichtData[8];

        if(this.isUserLoggedInBoolean)
        {
          const ResponseEntfernung = await axios.get("/EntfernungKundeRestaurant/getEntfernungByKundennummerRestaurant_ID/"+this.loggedInKunde_ID+"/"+this.restaurant_ID);
          if(ResponseEntfernung.data.length>0)
          {
            this.entfernung = ResponseEntfernung.data[0];
          }
        }
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_ID,config);

        if(responsePicture.status !== 204)
        {
          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);

          this.gerichtBild = imageURL;
        }
        else
        {
          this.gerichtBild = "";
        }
      }
      this.version++;

    },
    async fillAllergene()
    {
      this.allergeneGericht = [];
      const responseAllergene = await axios.get("Gericht_Allergene/getGericht_AllergeneByGericht_ID/"+this.gericht_ID);
      for(let i = 0; i<responseAllergene.data.length; i++)
      {
        this.allergeneGericht[i] = responseAllergene.data[i];
      }
    },
    addToCart() {
      if(this.isUserLoggedInBoolean)
      {
        if(this.restaurantBestellradius<this.entfernung)
        {
          alert("Sie befinden sich außerhalb des Bestellradius")
          return;
        }
      }

      let cartGericht = {
        gericht_ID: this.gericht_ID,
        name: this.gerichtName,
        thumbnail: this.gerichtBild,
        quantity: this.gerichtAnzahl,
        price: this.gerichtPreis
      }

      this.$store.commit("addToCartGerichte", cartGericht);
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
    loggedInKunde_ID: "",
    isUserLoggedInBoolean: false,
    gerichtBeschreibung: "",
    allergeneGericht: [],
    gerichtBild: "",
    gerichtPreis: 1.0,
    gerichtVerfuegbar: "",
    gerichtAnzahl: 1,
    cartGerichte: "",
    entfernung: "",
    restaurant_ID: "",
    restaurantName: "",
    restaurantMindestbestellwert: "",
    restaurantBestellradius: "",
    gericht_ID: "",
    version: 0,
    countMinMaxRule:[
        v => (v && v >= 1) || "Bestellungen müssen größer als 1 sein",
        v => (v && v <= 50) || "Bestellungen über 50 Stück geht nicht",
    ],
  }),
}
</script>

<style scoped>

</style>