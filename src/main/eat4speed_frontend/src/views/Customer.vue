<template>
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
                  <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                  <v-list-item-content >Entfernung: {{item.distance}} km</v-list-item-content>
                </v-list-item-group>
              </v-list-item-content>

              <v-list-item-content></v-list-item-content>

              <v-list-item-group align="left">
                <v-list-item-content>{{ item.price}}</v-list-item-content>
                <v-list-item-content>Mindestbestellwert<v-spacer></v-spacer>{{item.minimum}} €</v-list-item-content>
                <v-btn small="true" bottom="bottom">Bestellen</v-btn>
              </v-list-item-group>


            </v-list-item>
            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>

      </v-card>
    </v-container>
  </v-container>
</template>

<script>
export default {
  name: "customer",
  data: () => ({
    names: ['Burger','Pizza','Sushi','McNuggets'],
    descriptions: ['Es ist ein Burger','Krosse Krabe Pizza','Fischig','Mit Szechuan Sauce'],
    prices: ['5,50 €', '100 €','4,99 €', '3,99 €'],
    imgs: ['https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg','https://n-cdn.serienjunkies.de/review/97124-pizza-delivery.jpg','https://as.com/deporteyvida/imagenes/2018/09/28/portada/1538126553_039389_1538126831_noticia_normal.jpg','https://wrcb.images.worldnow.com/images/19836084_G.jpeg'],
    restaurants:['Bobs Burgers','Krosse Krabbe','AsiaWok','MCDonalds'],
    distances: ['10','29','7','4'],
    minimums: ['5','10','15','-']
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
        i++;

        return {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          restaurant: crestaurant,
          distance: cdistance,
          minimum: cminimum
        }
      })
    }
  }
}
</script>

<style scoped>

</style>