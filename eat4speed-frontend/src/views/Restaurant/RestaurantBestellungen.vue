<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <h1 class="mb-5">Offene Bestellungen (verschwinden wenn Kunde sie erhält)</h1>
          <v-list>

            <v-list-item class="mb-12">
              <v-card class="mr-10" color="red" dark>
              <v-col >
                <v-card-title>Bestellung 1 (Name des kunden?)</v-card-title>
                <v-card-text>- Pizza Mozarella</v-card-text>
                <v-card-text>- Cola groß</v-card-text>

              </v-col>
              </v-card>
              <v-slider
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
                <template v-slot:thumb-label="{ value }">
                  {{ bestellstati[value] }}
                </template>
              </v-slider>
            </v-list-item>
            <v-list-item class="mb-12">
              <v-card class="mr-10" color="red" dark>
                <v-col>
                  <v-card-title>Bestellung 2 (Name des kunden?)</v-card-title>
                  <v-card-text>- Calzone</v-card-text>
                  <v-card-text>- Cola groß</v-card-text>

                </v-col>
              </v-card>
              <v-slider
                  :tick-labels="bestellstati"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="0"
                  thumb-size="100"
              >
                <template v-slot:thumb-color="{ value }">
                  {{ bestellstatifarben[value] }}
                </template>
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
      bestellstatifarben: ['red', 'yellow', 'green'],
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