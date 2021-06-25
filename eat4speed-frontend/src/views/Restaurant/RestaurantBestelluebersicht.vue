<template>
  <v-main>

    <v-app-bar color="primary"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Bestellübersicht</v-toolbar-title>
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
            <v-list-item-title>Startseite</v-list-item-title>
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
                  <v-btn small="true" bottom="bottom">Fertig</v-btn>
                </v-list-item-group>
              </v-list-item>
              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "RestaurantBestelluebersicht",
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