<template>
  <div>
    <h1 class="subheading">Verifizierung Fahrer</h1>
    <v-card class="mx-5 my-5">
      <v-card-title>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
          :headers="headers"
          :items="data"
          multi-sort
          :single-select="false"
          item-key="name"
          :search="search"
          :items-per-page="10"
          class="elevation-1"
      >
        <template v-slot:item.actions="{ item }">
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                  color="grey"
                  class="mr-2"
                  v-bind="attrs"
                  v-on="on"
                  @click="verifyBewerbung(item)"
              >
                mdi-check-bold
              </v-icon>
            </template>
            <span>Akzeptieren</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                  color="red"
                  v-bind="attrs"
                  v-on="on"
                  @click="deleteBewerbung(item)"
              >
                mdi-delete
              </v-icon>
            </template>
            <span>Löschen</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "AdminVerification",
  methods: {
    deleteBewerbung(id) {
      return id;
    },
    verifyBewerbung(id) {
      return id;
    }
  },
  mounted() {
    this.data = [
      {
        anrede: 'Herr',
        vorname: "Daniel",
        nachname: "asd",
        fahrzeugart: "Auto"
      }
    ]
  },
  data() {
    return {
      data: [],
      search: '',
      headers: [
        {
          text: 'Anrede',
          align: 'start',
          sortable: false,
          value: 'anrede',
          width: '100'
        },
        {
          text: 'Vorname',
          value: 'vorname'
        },
        {
          text: 'Nachname',
          value: 'nachname'
        },
        {
          text: 'Fahrzeugart',
          value: 'fahrzeugart'
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
