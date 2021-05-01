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
        <v-container>
          <v-text-field placeholder="Suche..." autofocus clearable
            v-model="searchString"
          ></v-text-field>
          <v-btn
            @click="loadGerichte"
          >Suchen</v-btn>
        </v-container>
        <v-btn>Gericht</v-btn>
        <v-btn>Umgebung</v-btn>
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
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                    <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                    <v-list-item-content>Entfernung: {{item.distance}} km</v-list-item-content>
                    <v-list-item-content>Verfügbar: {{item.available}}</v-list-item-content>
                  </v-list-item-group>
                </v-list-item-content>
                <v-list-item-content></v-list-item-content>
                <v-list-item-group class="text-right">
                  <v-btn small="true" right>
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>
                  <br>
                  <v-list-item-content>
                    Preis: {{ item.price}}
                    <br>
                    Mindestbestellwert: {{item.minimum}} €
                  </v-list-item-content>
                  <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small="true" :value="item.rating"></v-rating>
                  <br>
                  <v-btn small="true" bottom="bottom" @mouseover="selectGericht(item)" :to="{name: 'dish'}">Bestellen</v-btn>
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
  name: "Kunde",
  mounted() {
    this.searchString = this.$store.getters.searchString;
    console.log(this.searchString);

    this.loadGerichte();
  },
  beforeRouteLeave(to, from, next) {
    this.setStoreGericht_ID();
    next();
  },
  methods: {
    selectGericht(item) {
      console.log("Gericht selected "+item.id);
      this.selectedGericht_ID = item.id;
      this.setStoreGericht_ID()
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
    },
    setStoreGericht_ID() {
      this.$store.commit("changeGericht_ID",this.selectedGericht_ID);
      console.log("changed gericht_ID to "+this.$store.getters.gericht_ID);
    },
    async loadGerichte() {
      this.setStoreSearchString();
      const ResponseGerichte = await axios.get("Gericht/getGerichtDataByGerichtName/" + this.searchString);

      console.log(ResponseGerichte);

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
        this.restaurant_IDs[i] = gerichtData[5];
        this.restaurantnamen[i] = gerichtData[6];
        this.minimums[i] = gerichtData[7];
      }

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
      console.log(this.imgs);
      this.amountGerichte = 0;
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
  },
  data: () => ({
    searchString: "",
    amountGerichte: 4,
    selectedGericht_ID: "",
    version: 0,
    gericht_IDs: [],
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen:[],
    distances: [],
    minimums: [],
    availabilities: []
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
        const crestaurantid = this.restaurant_IDs[i]
        const crestaurantname = this.restaurantnamen[i]
        const cdistance = this.distances[i]
        const cminimum = this.minimums[i]
        const cavailable = this.availabilities[i]
        i++;

        return {
          id: cid,
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          restaurantid: crestaurantid,
          restaurant: crestaurantname,
          distance: cdistance,
          minimum: cminimum,
          available: cavailable
        }
      })
    }
  }
}
</script>

<style scoped>

</style>