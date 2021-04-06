<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <div class="text-h3 mb-10"> Restaurantname</div>
          <v-col class="d-flex justify-space-between mb-6">
            <v-card-title class="text-h4"> Gerichte</v-card-title>
            <v-btn
                color="red"
                dark
                align="right"
                to="/restaurant-speisekarte-getränke"
                class="mt-5"
            >
              Getränke
            </v-btn>
          </v-col>
          <v-divider></v-divider>
          <v-virtual-scroll
              :items="items"
              :item-height="200"
              max-height="500"
          >
            <template v-slot:default="{ item }">
              <v-list-item>
                <v-list-item-content>
                  <v-img alt="Bild von Essen" max-height="300" max-width="300" :src="item.img"></v-img>
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
                  <v-btn small="true" bottom="bottom">Bearbeiten</v-btn>
                </v-list-item-group>
              </v-list-item>
              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
          <!------------  Artikel hinzufügen + ------------->
          <v-dialog
              v-model="artDialog"
              width="500"
              persistent
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="red"
                  dark
                  v-bind="attrs"
                  v-on="on"
              >
                Artikel hinzufügen
              </v-btn>
            </template>
            <v-card>
              <v-col>
                <v-text-field
                    v-model="gerichtName"
                    :counter="20"
                    label="Artikelname"
                    required
                ></v-text-field>
                <v-textarea
                    v-model="gerichtBeschreibung"
                    :counter="100"
                    label="Artikelbeschreibung"
                    required
                ></v-textarea>
                <v-file-input
                    v-model="gerichtBild"
                    label="Bild auswählen">
                </v-file-input>
                <v-text-field label="Preis in €" v-model="gerichtPreis" type="number" append-icon="currency-eur">
                </v-text-field>
                <v-checkbox label="Artikel verfügbar?" v-model="gerichtVerfuegbar">
                </v-checkbox>
                <v-select
                    ref="KategorieSelect"
                    v-model="selectedKategorien"
                    :items="kategorien"
                    chips
                    label="Kategorien"
                    multiple
                    outlined
                    block
                    @click="loadKategorien"
                ></v-select>
                <v-spacer class="ma-2"></v-spacer>
                <v-select
                    v-model="selectedAllergene"
                    :items="allergen"
                    chips
                    label="Allergene"
                    multiple
                    outlined
                    block
                    @click="loadAllergene"
                ></v-select>
                <v-spacer class="ma-2"></v-spacer>
                <v-col>
                  <v-row>
                    <v-btn
                        @click="addGericht(); artDialog = false"
                        color="red"
                        dark
                        class="justify-center"
                    >
                      Fertig
                    </v-btn>
                    <v-spacer class="mr-2"></v-spacer>
                    <v-btn
                        @click="artDialog = false; test();"
                        color="red"
                        dark
                        justify
                    >
                      Abbruch
                    </v-btn>
                  </v-row>
                </v-col>
              </v-col>
            </v-card>
          </v-dialog>
          <!------------  Artikel hinzufügen - ------------->
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "restaurant",
  mounted() {
    this.loadGerichte()
  },
  methods: {

    async loadGerichte() {
      this.restaurantID = 22;
      const ResponseGerichte = await axios.get("Gericht/getAllGerichtDataRestaurantSpeisekarte/" + this.restaurantID);

      console.log(ResponseGerichte);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];
      }
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
    openLogin() {
      this.$refs.Anmeldung.class = "px-4 d-flex"
    },
    computeItems() {

      for (let i = 0; i < this.names.length; i++) {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]

        let entry = {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg
        }
        this.computedItems[i] = entry;
      }
      console.log(this.computedItems);
      console.log(this.items);

    },
    async loadKategorien() {
      const ResponseAllKategorien = await axios.get("/Kategorie");

      console.log(ResponseAllKategorien);
      let arrayKategorien = [];
      let it;
      for (it = 0; it < ResponseAllKategorien.data.length; it++) {
        let kategorie = ResponseAllKategorien.data[it];

        arrayKategorien[it] = kategorie;

      }
      console.log(arrayKategorien);
      this.kategorien = arrayKategorien;
    },
    async test() {
      console.log(this.value);
      console.log(this.valueA);
    },
    async loadAllergene() {
      const ResponseAllAllegergene = await axios.get("/Allergene");

      console.log(ResponseAllAllegergene);
      let arrayAllergene = [];
      let it;
      for (it = 0; it < ResponseAllAllegergene.data.length; it++) {
        let allergen = ResponseAllAllegergene.data[it];

        arrayAllergene[it] = allergen;

      }
      console.log(arrayAllergene);
      this.allergen = arrayAllergene;

    },
    async addGericht() {

      this.restaurantID = 22;

      if (this.gerichtVerfuegbar === true) {
        this.gerichtVerfuegbar = 1;
      } else {
        this.gerichtVerfuegbar = 0;
      }

      var gericht = {
        beschreibung: this.gerichtBeschreibung,
        name: this.gerichtName,
        restaurant_id: this.restaurantID,
        verfuegbar: this.gerichtVerfuegbar,
        preis: this.gerichtPreis
      }

      const responseGericht = await axios.post("/Gericht", gericht);

      this.gericht_ID = responseGericht.data.gerichtId;

      for (let i = 0; i < this.selectedKategorien.length; i++) {
        let gericht_Kategorie = {
          gericht_ID: this.gericht_ID,
          name: this.selectedKategorien[i]
        }
        await axios.post("/Gericht_Kategorie", gericht_Kategorie);

      }
      for (let i = 0; i < this.selectedAllergene.length; i++) {
        let gericht_Allergene = {
          gericht_id: this.gericht_ID,
          allergen: this.selectedAllergene[i]
        }
        await axios.post("/Gericht_Allergene", gericht_Allergene);

      }
      this.loadGerichte();

    }
  },
  data: () => ({
    artDialog: false,
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurants: [],
    kategorien: [],
    selectedKategorien: [],
    allergen: [],
    selectedAllergene: [],

    gerichtName: "",
    gerichtBeschreibung: "",
    gerichtBild: "",
    gerichtPreis: "",
    gerichtVerfuegbar: "",
    restaurantID: "",
    gericht_ID: "",
    computedItems: [],
    version: 0,
    amountGerichte : ""
  }),

  computed: {
    items() {
      let i = 0
      this.version++;
      return Array.from({length: this.amountGerichte}, () => {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        i++;
        console.log(this.version);

        return {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
        }
      })
    }
  }
}
</script>

<style scoped>

</style>