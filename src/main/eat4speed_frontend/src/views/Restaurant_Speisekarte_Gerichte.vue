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
            <v-btn>Artikel hinzufügen</v-btn>
            <v-form v-model="myForm" hidden></v-form>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
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
  }),
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