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
                    <v-btn small bottom>Bearbeiten</v-btn>
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
                      :counter="10"
                      label="Artikelname"
                      required
                  ></v-text-field>
                  <v-file-input
                      label="Bild auswählen">

                  </v-file-input>
                  <v-text-field label="Preis in €" type="number" append-icon="currency-eur">
                  </v-text-field>

                  <v-textarea label="Beschreiben Sie das Gericht">
                  </v-textarea>

                  <v-checkbox label="Artikel verfügbar?">
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
export default {
  name: "restaurant",
  methods: {
    openLogin() {
      this.$refs.Anmeldung.class = "px-4 d-flex"
    },
    btnSelect() {
      arguments[0].selected ? arguments[0].selected = 0 : arguments[0].selected = 1;
    },
    print(){
      console.log(this.$refs.KategorieSelect.values().next())
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

    kategorie: {
      vegan: {
        selected: 0
      },
      vegetarisch: {
        selected: 0
      },
      pizza: {
        selected: 0
      },
      burger: {
        selected: 0
      },
      sushi: {
        selected: 0
      },
      dessert: {
        selected: 0
      },
      fruehstueck: {
        selected: 0
      },
      fleischGericht: {
        selected: 0
      },
      nudelGericht: {
        selected: 0
      },
      heiss: {
        selected: 0
      },
      kalt: {
        selected: 0
      },
      asiatisch: {
        selected: 0
      },
      alkoholischGetraenk: {
        selected: 0
      },
      softDrink: {
        selected: 0
      }
    },

    allergene: {
      ei: {
        selected: 0
      },
      erdNuss: {
        selected: 0
      },
      fisch: {
        selected: 0
      },
      gluten: {
        selected: 0
      },
      krustenTiere: {
        selected: 0
      },
      lupinen: {
        selected: 0
      },
      kuhMilch: {
        selected: 0
      },
      schalenFruechte: {
        selected: 0
      },
      schwefel: {
        selected: 0
      },
      sellerie: {
        selected: 0
      },
      senf: {
        selected: 0
      },
      sesam: {
        selected: 0
      },
      soja: {
        selected: 0
      },
      weichTiere: {
        selected: 0
      }
    },

  }),

  computed: {

    colorEi() {
      if (this.allergene.ei.selected === 1) {
        return 'blue'
      } else return 'red'

    },
    colorEr() {
      if (this.allergene.erdNuss.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorFi() {
      if (this.allergene.fisch.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSel() {
      if (this.allergene.sellerie.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorKuh() {
      if (this.allergene.kuhMilch.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorGlu() {
      if (this.allergene.gluten.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorKru() {
      if (this.allergene.krustenTiere.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorLup() {
      if (this.allergene.lupinen.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSfr() {
      if (this.allergene.schalenFruechte.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorS() {
      if (this.allergene.schwefel.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSenf() {
      if (this.allergene.senf.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSes() {
      if (this.allergene.sesam.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSoy() {
      if (this.allergene.soja.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorWeich() {
      if (this.allergene.weichTiere.selected === 1) {
        return 'blue'
      } else return 'red'
    },

    colorVegan() {
      if (this.kategorie.vegan.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorVeget() {
      if (this.kategorie.vegetarisch.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorPizza() {
      if (this.kategorie.pizza.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorBurger() {
      if (this.kategorie.burger.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorSushi() {
      if (this.kategorie.sushi.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorDessert() {
      if (this.kategorie.dessert.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorFrueh() {
      if (this.kategorie.fruehstueck.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorFleisch() {
      if (this.kategorie.fleischGericht.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorNudel() {
      if (this.kategorie.nudelGericht.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorHeiss() {
      if (this.kategorie.heiss.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorKalt() {
      if (this.kategorie.kalt.selected === 1) {
        return 'blue'
      } else return 'red'
    },
    colorAsia() {
      if (this.kategorie.asiatisch.selected === 1) {
        return 'blue'
      } else return 'red'
    },


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