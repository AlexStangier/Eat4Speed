<template>
  <v-app>
    <v-main>
      <h1 class="subheading">Verifizierung Fahrer</h1>
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
              <v-row>

                <v-col cols="6">
                  <v-row class="pa-6">
                    <v-select
                        v-model="select"
                        :items="items"
                        item-text="text"
                        item-value="value"
                        label="Select"
                        persistent-hint
                        return-object
                        single-line
                    ></v-select>
                  </v-row>
                </v-col>

                <v-col cols="6">
                  <v-row class="pa-6">
                    <v-text-field
                        v-model="search"
                        append-icon="mdi-magnify"
                        hide-details
                        label="Suche"
                        single-line
                    ></v-text-field>
                  </v-row>
                </v-col>

              </v-row>
            </v-container>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-dialog
                v-model="acceptDialog"
                :retain-focus="false"
                max-width="290"
                persistent
            >
              <template v-slot:activator="{ on, attrs }">
                <v-icon
                    v-bind="attrs"
                    v-on="on"
                    class="mr-2"
                    color="grey"
                >
                  mdi-check-bold
                </v-icon>
              </template>
              <v-card>
                <v-card-actions>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-btn color="green" dark rounded width="200"
                               @click="acceptDialog = false; verifyBewerbung(item);">Verifizieren
                        </v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="red" dark rounded width="200" @click="acceptDialog = false">Abbrechen</v-btn>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog
                v-model="deleteDialog"
                :retain-focus="false"
                max-width="290"
                persistent
            >
              <template v-slot:activator="{ on, attrs }">
                <v-icon
                    v-bind="attrs"
                    v-on="on"
                    color="red"
                >
                  mdi-delete
                </v-icon>
              </template>
              <v-card>
                <v-card-actions>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung(item);">Unseriös</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung(item);">Spam</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung(item);">Unvollständig</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung(item);">Keine Angabe</v-btn>
                      </v-col>
                      <v-col cols="12"></v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung(item);">Abbrechen</v-btn>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </template>
        </v-data-table>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "AdminVerification",
  methods: {
    deleteBewerbung(id) {
      id;
    },
    verifyBewerbung(id) {
      id;
    }
  },
  mounted() {
    this.data = [
      {
        anrede: 'Herr',
        vorname: "Daniel",
        nachname: "asd",
        fahrzeugart: "Auto"
      },
      {
        anrede: 'Herr',
        vorname: "Daniel2",
        nachname: "asd2",
        fahrzeugart: "Fahrrad"
      }
    ]
  },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      search: '',
      select: {text: 'Alle Bewerbungen', value: 1},
      items: [
        {text: 'Alle Bewerbungen', value: 1},
        {text: 'Offene Bewerbungen', value: 2},
        {text: 'Geschlossene Bewerbungen', value: 3},
      ],
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
