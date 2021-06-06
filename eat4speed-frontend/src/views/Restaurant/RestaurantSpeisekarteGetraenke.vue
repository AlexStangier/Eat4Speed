<template>
  <v-main>

    <v-app-bar color="blue"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Speisekarte getränke</v-toolbar-title>
    </v-app-bar>
    <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            active-class="deep-purple--text text--accent-4"
        >
          <router-link  to="/restaurant/controlpanel">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>ControlPanel</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/speisekarteGerichte"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-silverware</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/bestellungen"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-view-headline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Offene Bestellungen</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link to="/restaurant/schichtplan">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-watch</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Schichtplan</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link  to="/restaurant/stammdaten"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Stammdaten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/umsatzstatistik">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-margin</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Umsatzstatistik</v-list-item-title>
            </v-list-item>
          </router-link>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <div class="text-h3 mb-10"> Restaurantname </div>
          <v-col  class="d-flex justify-space-between mb-6">
            <v-btn
                color="red"
                dark
                align="right"
                to="/restaurant-speisekarte-gerichte"
                class="mt-5"
            >
              Gerichte
            </v-btn>
            <v-card-title class="text-h4"> Getränke </v-card-title>
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
                  <v-list-item-content>{{ item.price}}</v-list-item-content>
                  <v-btn small="true" bottom="bottom">Bearbeiten</v-btn>
                </v-list-item-group>
              </v-list-item>
              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
          <!------------  Artikel hinzufügen + ------------->
          <v-dialog
              v-model="dialog"
              width="500"
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
                    v-model="firstname"
                    :rules="nameRules"
                    :counter="10"
                    label="Artikelname"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="firstname"
                    :rules="nameRules"
                    :counter="10"
                    label="Artikelbeschreibung"
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
                <v-dialog
                    v-model="dialog"
                    max-width="200"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="red"
                        dark
                        v-bind="attrs"
                        v-on="on"
                    >
                      Allergene auswählen
                    </v-btn>
                  </template>
                  <v-card>
                    <v-col>
                      <v-btn ref="allergie1" color="red" dark rounded elevation="15"
                             @click="allergie1Farbe">
                        allergie 1
                      </v-btn >
                      <v-spacer class="ma-2"></v-spacer>
                      <v-btn ref="allergie2" color="red" dark rounded elevation="15"
                             @click="allergie2Farbe">
                        allergie 2
                      </v-btn>
                      <v-spacer class="ma-2"></v-spacer>
                      <v-btn color="red" dark rounded elevation="15">
                        allergie 3
                      </v-btn>
                      <v-spacer class="ma-2"></v-spacer>
                      <v-btn color="red" dark rounded elevation="15">
                        allergie 4
                      </v-btn>
                      <v-spacer class="ma-2"></v-spacer>
                      <v-btn color="red" dark rounded elevation="15">
                        allergie 5
                      </v-btn>
                    </v-col>
                  </v-card>
                </v-dialog>
                <v-spacer class="ma-2"></v-spacer>
                <v-btn
                    color="red"
                    dark
                >
                  Fertig
                </v-btn>
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
export default {
  name: "RestaurantSpeisekarteGetraenke",
  methods: {
    openLogin(){
      this.$refs.Anmeldung.class="px-4 d-flex"
    },
    allergie1Farbe(){
      if (this.allergyButton1.selected===1) {
        this.$refs.allergie1.color = "red"
        this.allergyButton1.selected=0
      }
      if (this.allergyButton1.selected===0) {
        this.$refs.allergie1.color = "blue"
        this.allergyButton1.selected=1
      }
    },
    allergie2Farbe(){
      if (this.allergyButton2.selected===1) {
        this.$refs.allergie2.color = "red"
        this.allergyButton2.selected=0
      }
      if (this.allergyButton2.selected===0) {
        this.$refs.allergie2.color = "blue"
        this.allergyButton2.selected=1
      }
    }
  },
  data: () => ({
    drawer: false,
    group: null,
    switch1: true,
    names: ['Cola','Fanta','Eistee'],
    descriptions: ['Braun','Gelb','Abbildung kann abweichen!'],
    prices: ['3,99 €', '3,99 €','4,99 €'],
    imgs: ['https://www.fivestartrading-holland.eu/images/product_images/original_images/coco_cola_dosen_pfandfrei.png',
      'https://images-na.ssl-images-amazon.com/images/I/71y12uT-UuL._SL1500_.jpg',
      'https://ais.kochbar.de/kbrezept/445563_590001/1200x1200/selbstgemachter-eistee-rezept.jpg'],

    allergyButton1: {
      selected: 0
    },
    allergyButton2: {
      selected: 0
    }
  }),
  mounted() {
    this.allergie1Farbe();
    this.allergie2Farbe();

  },
  computed: {
    items(){
      let i = 0
      return Array.from({ length: 3}, () => {
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