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
                        @change="reloadFahrer"
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
import axios from "axios";

export default {
  name: "AdminVerification",
  methods: {
    async reloadFahrer(){
      const ResponseAllFahrer = await axios.get("/Fahrer/ALL");

      console.log(ResponseAllFahrer);
      console.log(ResponseAllFahrer.data);
      console.log(ResponseAllFahrer.data[0])

      var test = ResponseAllFahrer.data[0];

      console.log(test[0]);

      this.allFahrer = ResponseAllFahrer;

      var i;
      var toDisplay = "["
      for(i=0;i<ResponseAllFahrer.data.length;i++)
      {
        let entry = ResponseAllFahrer.data[i];

        toDisplay += "{'fahrernummer':";
        toDisplay += entry[0]+"}";

        if(i<ResponseAllFahrer.data.length-1)
        {
          toDisplay += ",";
        }

      }

      toDisplay += "]";

      console.log(toDisplay);

      this.data = toDisplay;
    },
    deleteBewerbung(id) {
      id;
    },
    verifyBewerbung(id) {
      id;
    }
  },
  mounted() {

    this.data = this.allFahrer;
  },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      allFahrer: "",
      fahrerSelection: "",
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
          text: 'Fahrernummer',
          value: 'fahrernummer'
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
