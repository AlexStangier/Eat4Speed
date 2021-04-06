<template>
  <v-main>
    <v-container>
      <v-container>
        <v-container>
          <v-text-field placeholder="Suche..." autofocus clearable></v-text-field>
          <v-btn>Suchen</v-btn>
        </v-container>
        <v-btn>Gericht</v-btn>
        <v-btn>Umgebung</v-btn>
      </v-container>
      <v-divider></v-divider>
      <v-container>
        <v-card class="mx-auto">
          <v-card-title> Gerichte </v-card-title>
          <v-divider></v-divider>
          <v-virtual-scroll
              :items="items"
              :item-height="200"
              max-height="600"
          >
            <template v-slot:default="{ item }" v-resize>
              <v-list-item v-resize>
                <v-list-item-content>
                  <v-img alt="Bild von Essen" max-height="300" max-width="300" position="center center" :src="item.img"></v-img>
                </v-list-item-content>
                <v-list-item-content>
                  <v-list-item-group align="left">
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                    <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                    <v-list-item-content>Entfernung: {{item.distance}} km</v-list-item-content>
                  </v-list-item-group>
                </v-list-item-content>
                <v-list-item-content></v-list-item-content>
                <v-list-item-group align="center">
                  <v-list-item-content>
                    Preis: {{ item.price}}
                    <br>
                    Mindestbestellwert: {{item.minimum}} €
                  </v-list-item-content>
                  <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small :value="item.rating"></v-rating>
                  <br>
                  <v-btn small="true" bottom="bottom" to="/dish">Bestellen</v-btn>
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
export default {
  name: "customer",
  data: () => ({
    names: ['Burger','Pizza','Sushi','McNuggets'],
    descriptions: ['Es ist ein Burger.','Krosse Krabe Pizza.','Räucherlachs.','Mit Szechuan Sauce.'],
    prices: ['5,50 €', '10 €','4,99 €', '3,99 €'],
    imgs: ['https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg','https://n-cdn.serienjunkies.de/review/97124-pizza-delivery.jpg','https://as.com/deporteyvida/imagenes/2018/09/28/portada/1538126553_039389_1538126831_noticia_normal.jpg','https://wrcb.images.worldnow.com/images/19836084_G.jpeg'],
    restaurants:['Bobs Burgers','Krosse Krabbe','AsiaWok','MCDonalds'],
    distances: ['10','29','7','4'],
    minimums: ['5','10','15','-'],
    ratings: ['4.5', '5', '3.5', '4']
  }),
  computed: {
    items(){
      let i = 0
      return Array.from({ length: 4}, () => {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        const crestaurant = this.restaurants[i]
        const cdistance = this.distances[i]
        const cminimum = this.minimums[i]
        const crating = this.ratings[i]
        i++;

        return {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          restaurant: crestaurant,
          distance: cdistance,
          minimum: cminimum,
          rating: crating
        }
      })
    }
  }
}
</script>

<style scoped>

</style>