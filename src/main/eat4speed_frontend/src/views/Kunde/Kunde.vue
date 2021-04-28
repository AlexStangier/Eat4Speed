<template>
  <v-main>
    <v-container>
      <v-container>
        <v-row no-gutters>
          <v-col sm="2">
            <v-select
                v-model="selectedRating"
                label="Bewertung"
                :items="selectRating"
                clearable="true"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"Sterne"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"Sterne"}}
              </template>
            </v-select>
          </v-col>
          <v-col sm="2" offset-sm="1">
            <v-select
                v-model="selectedArea"
                label="Entfernung"
                :items="selectArea"
                clearable="true"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"km"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"km"}}
              </template>
            </v-select>
          </v-col>
          <v-col offset-sm="1">
            <v-btn>Auf der Karte anzeigen</v-btn>
          </v-col>
          <v-col order-sm="12" offset-sm="1">
            <v-menu
                bottom
                offset-y
                :close-on-content-click="false"
            >
              <template v-slot:activator="{ on, attrs}">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                >
                  Filter
                </v-btn>
              </template>
              <v-list
                  max-width="400"
                  min-width="400"

              >
                <v-subheader>Mindestbestellwert</v-subheader>
                <v-list-item>
                  <v-slider
                      v-model="filterCosts"
                      min="5"
                      max="100"
                      step="5"
                      thumb-label
                      prepend-icon="mdi-cash"
                      append-icon="mdi-cash-multiple"
                  >
                    <template v-slot:thumb-label="{ value }">
                      {{value}} {{"€"}}
                    </template>
                  </v-slider>
                </v-list-item>
                <v-list-item>
                  <v-container fluid>
                    <v-select
                        v-model="filterOptions"
                        :items="Options"
                        label="Filteroptionen"
                        multiple
                    >
                      <template v-slot:selection="{ item, index }">
                        <v-chip v-if="index < 3">
                          <span>{{ item }}</span>
                        </v-chip>
                        <v-chip v-if="index === 3">
                            <span
                                class="grey--text caption"
                            >
                              (+{{ filterOptions.length - 3 }} weitere)
                            </span>
                        </v-chip>
                      </template>
                    </v-select>
                  </v-container>
                </v-list-item>
                <v-list-item>
                  <v-btn color="error">Filter löschen</v-btn>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>
        </v-row>
      </v-container>
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
                  <v-list-item-group class="text-left">
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                    <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                    <v-list-item-content>Entfernung: {{item.distance}} km</v-list-item-content>
                  </v-list-item-group>
                </v-list-item-content>
                <v-list-item-content></v-list-item-content>
                <v-list-item-group class="text-right">
                  <v-btn small="true" right>
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>
                  <br>
                  <v-list-item-content>
                    Preis: {{ item.price}}
                    <br>
                    Mindestbestellwert: {{item.minimum}} €
                  </v-list-item-content>
                  <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small="true" :value="item.rating"></v-rating>
                  <br>
                  <v-btn small="true" bottom="bottom" to="gericht">Bestellen</v-btn>
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
  name: "Kunde",
  data: () => ({
    names: ['Burger','Pizza','Sushi','McNuggets'],
    descriptions: ['Es ist ein Burger.','Krosse Krabe Pizza.','Räucherlachs.','Mit Szechuan Sauce.'],
    prices: ['5,50 €', '10 €','4,99 €', '3,99 €'],
    imgs: ['https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg','https://n-cdn.serienjunkies.de/review/97124-pizza-delivery.jpg','https://as.com/deporteyvida/imagenes/2018/09/28/portada/1538126553_039389_1538126831_noticia_normal.jpg','https://wrcb.images.worldnow.com/images/19836084_G.jpeg'],
    restaurants:['Bobs Burgers','Krosse Krabbe','AsiaWok','MCDonalds'],
    distances: ['10','29','7','4'],
    minimums: ['5','10','15','-'],
    ratings: [4.5, 5, 3.5, 4],

    selectRating: [5 , 4, 3, 2 , 1],
    selectedRating: null,
    selectArea: [5, 10, 20, 30, 40],
    selectedArea: null,
    filterCosts: '5',
    Options: ['Vegetarisch', 'Vegan', 'Glutenfrei', 'Laktosefrei', 'ohne Erdnüsse', '1', '2', '3'],
    filterOptions: null,
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