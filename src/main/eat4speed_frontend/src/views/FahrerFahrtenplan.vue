<template>
  <v-app>
    <v-main>
      <h1 class="subheading">Fahrer Fahrtenplan</h1>
      <v-card class="mx-5 my-5">
        <v-data-table
            :headers="headers"
            :items="data"
            :items-per-page="10"
            :search="search"
            :single-select="false"
            class="elevation-1 pa-6"
            item-key="name"
            multi-sort
        >
          <template v-slot:top>
            <v-container fluid>
              <v-col cols="6">
                <v-row>
                  Liefertermin: {{ getTermin() }}
                </v-row>
                <v-row>
                  restliche Stationen: {{ getStationen() }}
                </v-row>
              </v-col>
            </v-container>
          </template>

          <template v-slot:item.map="{ }">
            <GmapMap
                :center="{lat:10, lng:10}"
                :zoom="7"
                map-type-id="terrain"
                style="width: 250px; height: 200px"
            >
              <GmapMarker
                  :key="index"
                  v-for="(m, index) in markers"
                  :position="m.position"
                  :clickable="true"
                  :draggable="true"
                  @click="center=m.position"
              />
            </GmapMap>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-btn
                v-bind="attrs"
                v-on="on"
                color="blue"
                dark
                rounded
                @click="abholungBestätigen(item)"
            >Bestätigen</v-btn>
          </template>
        </v-data-table>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "FahrerFahrtenplan",
  methods: {
    abholungBestätigen(id) {
      id;
    },
    getTermin() {
      return '12.12.2021';
    },
    getStationen() {
      return 2;
    },
    convertToKM(meters) {
      return meters / 1000;
    }
  },
  mounted() {
    this.data = [
      {
        station: "1",
        beschreibung: "Kurzbeschreibung 1",
        restaurantname: "Restaurant 1",
        entfernung: "1 km"
      },
      {
        station: "2",
        beschreibung: "Kurzbeschreibung 1",
        entfernung: "2 km"
      }
    ]
  },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      headers: [
        {
          text: 'Karte',
          align: 'start',
          sortable: false,
          value: 'map'
        },
        {
          text: 'Station',
          value: 'station',
          sortable: false
        },
        {
          text: 'Beschreibung',
          value: 'beschreibung',
          sortable: false
        },
        {
          text: 'Restaurantname',
          value: 'restaurantname',
          sortable: false
        },
        {
          text: 'Entfernung',
          value: 'entfernung',
          sortable: false
        },
        {
          value: 'actions',
          sortable: false,
          align: 'end'
        },
      ],
    };
  },
};
</script>
