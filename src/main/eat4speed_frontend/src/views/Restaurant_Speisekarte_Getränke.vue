<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>
            <div class="text-h3 mb-10"> Restaurantname</div>
            <v-col class="d-flex justify-space-between mb-6">

              <v-btn
                  color="red"
                  dark
                  align="right"
                  to="/restaurant-speisekarte-gerichte"
                  class="mt-5"
              >
                Gerichte
              </v-btn>
              <v-card-title class="text-h4"> Getränke</v-card-title>
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
                v-model="artDialogDr"
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
                      v-model="value"
                      :items="kategorien"
                      chips
                      label="Kategorien"
                      multiple
                      outlined
                      block
                  ></v-select>


                  <v-spacer class="ma-2"></v-spacer>
                  <v-col>
                    <v-row>

                      <v-btn
                          color="red"
                          dark
                          class="justify-center"
                      >
                        Fertig
                      </v-btn>
                      <v-spacer class="mr-2"></v-spacer>

                      <v-btn
                          @click="artDialogDr = false"
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

  },
  data: () => ({
    artDialogDr: false,
    names: ['Cola', 'Fanta', 'Eistee'],
    descriptions: ['Braun', 'Gelb', 'Abbildung kann abweichen!'],
    prices: ['3,99 €', '3,99 €', '4,99 €'],
    imgs: ['https://www.fivestartrading-holland.eu/images/product_images/original_images/coco_cola_dosen_pfandfrei.png',
      'https://images-na.ssl-images-amazon.com/images/I/71y12uT-UuL._SL1500_.jpg',
      'https://ais.kochbar.de/kbrezept/445563_590001/1200x1200/selbstgemachter-eistee-rezept.jpg'],

    kategorien: ['Softdrink', 'alkoholisch', 'heiss', 'kalt'],
    value: ['Softdrink', 'alkoholisch', 'heiss', 'kalt'],

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
    items() {
      let i = 0
      return Array.from({length: 3}, () => {
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