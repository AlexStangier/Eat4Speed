<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>

              <v-card-title> Gerichte </v-card-title>
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
                  Artikel beartbeiten
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
  </v-app>
</template>

<script>
export default {
  name: "customer",
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
    names: ['Burger','Pizza','Sushi','McNuggets'],
    descriptions: ['Es ist ein Burger','Krosse Krabe Pizza','Fischig','Mit Szechuan Sauce'],
    prices: ['5,50 €', '100 €','4,99 €', '3,99 €'],
    imgs: ['https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg','https://n-cdn.serienjunkies.de/review/97124-pizza-delivery.jpg','https://as.com/deporteyvida/imagenes/2018/09/28/portada/1538126553_039389_1538126831_noticia_normal.jpg','https://wrcb.images.worldnow.com/images/19836084_G.jpeg'],
    restaurants:['Bobs Burgers','Krosse Krabbe','AsiaWok','MCDonalds'],

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
      return Array.from({ length: 4}, () => {
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