<template>
  <v-app>
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
                      v-model="value"
                      :items="kategorien"
                      chips
                      label="Kategorien"
                      multiple
                      outlined
                      block
                  ></v-select>
                  <v-spacer class="ma-2"></v-spacer>
                  <v-select
                      v-model="valueA"
                      :items="allergen"
                      chips
                      label="Allergene"
                      multiple
                      outlined
                      block
                  ></v-select>

                  <v-spacer class="ma-2"></v-spacer>
                  <v-col>
                    <v-row>
                      <v-btn
                          @click="print"
                          color="red"
                          dark
                          class="justify-center"
                      >
                        Fertig
                      </v-btn>
                      <v-spacer class="mr-2"></v-spacer>
                      <v-btn
                          @click="artDialog = false"
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
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "restaurant",
  methods: {
    openLogin() {
      this.$refs.Anmeldung.class = "px-4 d-flex"
    },
    async addGericht(){

      this.restaurantID = 22;

      var gericht = {
        abbildung:this.gerichtBild,
        beschreibung:this.gerichtBeschreibung,
        name:this.gerichtName,
        restaurant_id:this.restaurantID,
        verfuegbar:this.gerichtVerfuegbar,
        preis:this.gerichtPreis
      }

      const responseGericht = await axios.post("/Gericht", gericht);

      this.gericht_ID = responseGericht.data.gerichtID;

    }
  },
  data: () => ({
    artDialog: false,
    names: ['Burger', 'Pizza', 'Sushi', 'McNuggets'],
    descriptions: ['Es ist ein Burger', 'Krosse Krabe Pizza', 'Fischig', 'Mit Szechuan Sauce'],
    prices: ['5,50 €', '100 €', '4,99 €', '3,99 €'],
    imgs: ['https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg', 'https://n-cdn.serienjunkies.de/review/97124-pizza-delivery.jpg', 'https://as.com/deporteyvida/imagenes/2018/09/28/portada/1538126553_039389_1538126831_noticia_normal.jpg', 'https://wrcb.images.worldnow.com/images/19836084_G.jpeg'],
    restaurants: ['Bobs Burgers', 'Krosse Krabbe', 'AsiaWok', 'MCDonalds'],
    kategorien: ['Vegan', 'Vegetarisch', 'Pizza', 'Burger', 'Sushi', 'Dessert', 'Frühstück', 'Fleischgericht', 'Nudelgericht', 'heiß',
      'kalt', 'asiatisch'],
    value: [],
    allergen: ['Ei', 'Erdnüsse', 'Fisch', 'Gluten', 'Krustentiere', 'Lupinen', 'Kuhmilch', 'Schalenfrüchte', 'Schwefeldioxid',
      'Sellerie', 'Senf', 'Sesam', 'Soja', 'Weichtiere'],
    valueA: [],

    gerichtName: "",
    gerichtBeschreibung: "",
    gerichtBild: "",
    gerichtPreis: "",
    gerichtVerfuegbar: "",
    restaurantID:"",
    gericht_ID:""
  }),

  computed: {
    items() {
      let i = 0
      return Array.from({length: 4}, () => {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        i++;

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