<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <v-list>
            <v-row class = "mr-5">
            <h1>LOL</h1>
              <v-spacer/>
            <h1>LOL</h1>
              <v-spacer/>
            <h1>LOL</h1>
            </v-row>
            <v-list-item>
              <v-btn
                  class="mr-10"
                  color="red"
                  dark
                  v-bind="attrs"
                  v-on="on"
                  @click="currentlyAdding = true"
              >
                Bestellung
              </v-btn>
              <v-slider
                  v-model="fruits"
                  :tick-labels="ticksLabels"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="10"
              ></v-slider>
            </v-list-item>
            <v-list-item>
              <v-btn
                  class="mr-10"
                  color="red"
                  dark
                  v-bind="attrs"
                  v-on="on"
                  @click="currentlyAdding = true"
              >
                Bestellung
              </v-btn>
              <v-slider
                  v-model="fruits"
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="10"
                  thumb-label
                  thumb-size="100"
              >
                <template v-slot:thumb-label="{ value }">
                  {{ bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>

          </v-list>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "RestaurantBestellungen",
  data () {
    return {
      bestellstati: ['Bereit', 'In Zubereitung', 'Abholbereit'],
      bestellungen: [
        {
          name: 'Bestellung 1',
          offen: true,
          inZubereitung: true,
          abholBereit: false,
        },
        {
          name: 'Bestellung 2',
          offen:  true,
          inZubereitung: false,
          abholBereit: false,
        },

      ],
    }
  },
}
</script>

<style scoped>

</style>