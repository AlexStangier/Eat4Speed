<template>
  <v-app>
    <v-main>
      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                <tr >
                  <th class="text-center">
                    Bestellung
                  </th>
                  <th class="text-center">
                    Offen
                  </th>
                  <th class="text-center">
                    in Zubereitung
                  </th>
                  <th class="text-center">
                    Abholbereit
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="item in bestellungen"
                    :key="item.name"
                >
                  <td> <v-btn>{{ item.name }}</v-btn></td>

                  <td v-if="item.offen" bgcolor="green"></td>
                  <td v-else-if="!item.offen" bgcolor="red"></td>
                  <td v-if="item.inZubereitung" bgcolor="green"></td>
                  <td v-else-if="!item.inZubereitung" bgcolor="red"></td>
                  <td v-if="item.abholBereit" bgcolor="green"></td>
                  <td v-else-if="!item.abholBereit" bgcolor="red"></td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "Restaurant_Bestellungen",
  data () {
    return {
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