<template>
  <div>
    <h1 class="subheading">TestTable</h1>
    <v-card class="mx-5 my-5">
      <v-card-title>
        Data
        <v-spacer></v-spacer>
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
          :items="albums"
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
                  color="red"
                  v-bind="attrs"
                  v-on="on"
                  @click="deleteAlbum(item.id)"
              >
                mdi-delete
              </v-icon>
            </template>
            <span>Delete</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>

<script>
export default {
  methods: {
    deleteAlbum(id) {
      this.$http.delete(`https://jsonplaceholder.typicode.com/albums/${id}`)
          .then((result) => {
            console.log(JSON.stringify(result));
          });
    },
  },
  mounted() {
    this.$http.get('https://jsonplaceholder.typicode.com/albums/')
        .then((result) => {
          this.albums = result.data;
        });
  },
  data() {
    return {
      albums: [],
      search: '',
      headers: [
        {
          text: 'User ID',
          align: 'start',
          sortable: false,
          value: 'userId',
          width: '100',
        },
        {
          text: 'ID',
          value: 'id',
          width: '100',
        },
        {
          text: 'Title',
          value: 'title',
        },
        {
          value: 'actions',
          sortable: false,
          align: 'end',
        },
      ],
    };
  },
};
</script>
