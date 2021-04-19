<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>


            <v-card
                class="mx-auto text-center"
                color="green"
                dark
                max-width="600"
            >
              <v-card-text>
                <v-sheet color="rgba(0, 0, 0, .12)">
                  <v-sparkline
                      :value="value"
                      color="rgba(255, 255, 255, .7)"
                      height="100"
                      padding="24"
                      stroke-linecap="round"
                      smooth
                  >
                    <template v-slot:label="item">
                      {{ item.value }}
                    </template>
                  </v-sparkline>
                </v-sheet>
              </v-card-text>

              <v-card-text>

              </v-card-text>

              <v-divider></v-divider>


            </v-card>

        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  data: () => ({
    value: [
      10.4,
      11.4,
      12.4,
      13.4,
      14.4,
      15.4,
      16.4,
    ],
  }),
}
</script>

<style scoped>

</style>