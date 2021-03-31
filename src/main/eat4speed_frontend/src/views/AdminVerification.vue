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
                    @click="setCurrentRowItem(item)"
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
                               @click="acceptDialog = false; verifyBewerbung();">Verifizieren
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
                    @click="setCurrentRowItem(item)"
                >
                  mdi-delete
                </v-icon>
              </template>
              <v-card>
                <v-card-actions>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung();">Unseriös</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung();">Spam</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung();">Unvollständig</v-btn>
                      </v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false; deleteBewerbung();">Keine Angabe</v-btn>
                      </v-col>
                      <v-col cols="12"></v-col>
                      <v-col cols="12">
                        <v-btn color="blue" dark rounded width="200" @click="deleteDialog = false;">Abbrechen</v-btn>
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

      console.log(this.select);
      console.log(this.select.value)

      if(this.select.value === 1) {
        const ResponseAllFahrer = await axios.get("/Fahrer/ALL");

        console.log(ResponseAllFahrer);
        console.log(ResponseAllFahrer.data);
        console.log(ResponseAllFahrer.data[0])

        var test = ResponseAllFahrer.data[0];

        console.log(test[0]);

        this.allFahrer = ResponseAllFahrer;

        var arrayAllFahrer = [];

        let it;
        for (it = 0; it < ResponseAllFahrer.data.length; it++) {
          let fahrer = ResponseAllFahrer.data[it];

          let entry = {
            fahrernummer: fahrer[0],
            vorname: fahrer[1],
            nachname: fahrer[2],
            fahrzeugart: fahrer[3],
            verifiziert: fahrer[4]
          };
          arrayAllFahrer[it] = entry;

        }
        /*
      this.data = [
        {
          fahrernummer: test[0],
          vorname: test[1],
          nachname: test[2],
          fahrzeugart: test[3],
          verifiziert:test[4]
        }
      ]
      */

        this.data = arrayAllFahrer;

        console.log(arrayAllFahrer);
      }

      if(this.select.value === 2) {
        const ResponseNotVerifiedFahrer = await axios.get("/Fahrer/NOT_VERIFIED");

        console.log(ResponseNotVerifiedFahrer);
        console.log(ResponseNotVerifiedFahrer.data);
        console.log(ResponseNotVerifiedFahrer.data[0])

        this.allFahrer = ResponseNotVerifiedFahrer;

        var arrayNotVerifiedFahrer = [];

        let it;
        for (it = 0; it < ResponseNotVerifiedFahrer.data.length; it++) {
          let fahrer = ResponseNotVerifiedFahrer.data[it];

          let entry = {
            fahrernummer: fahrer[0],
            vorname: fahrer[1],
            nachname: fahrer[2],
            fahrzeugart: fahrer[3],
            verifiziert: fahrer[4]
          };
          arrayNotVerifiedFahrer[it] = entry;

        }
        /*
      this.data = [
        {
          fahrernummer: test[0],
          vorname: test[1],
          nachname: test[2],
          fahrzeugart: test[3],
          verifiziert:test[4]
        }
      ]
      */

        this.data = arrayNotVerifiedFahrer;

        console.log(arrayNotVerifiedFahrer);
      }

      if(this.select.value === 3) {
        const ResponseVerifiedFahrer = await axios.get("/Fahrer/VERIFIED");

        console.log(ResponseVerifiedFahrer);
        console.log(ResponseVerifiedFahrer.data);
        console.log(ResponseVerifiedFahrer.data[0])

        this.allFahrer = ResponseVerifiedFahrer;

        var arrayVerifiedFahrer = [];

        let it;
        for (it = 0; it < ResponseVerifiedFahrer.data.length; it++) {
          let fahrer = ResponseVerifiedFahrer.data[it];

          let entry = {
            fahrernummer: fahrer[0],
            vorname: fahrer[1],
            nachname: fahrer[2],
            fahrzeugart: fahrer[3],
            verifiziert: fahrer[4]
          };
          arrayVerifiedFahrer[it] = entry;

        }
        /*
      this.data = [
        {
          fahrernummer: test[0],
          vorname: test[1],
          nachname: test[2],
          fahrzeugart: test[3],
          verifiziert:test[4]
        }
      ]
      */

        this.data = arrayVerifiedFahrer;

        console.log(arrayVerifiedFahrer);
      }

    },
    setCurrentRowItem(item)
    {
      this.currentRowItem = item;
      console.log(item);
      console.log(this.currentRowItem);
    },
    async deleteBewerbung() {

      const ResponseDeleteFahrer = await axios.delete("Fahrer/"+this.currentRowItem.fahrernummer);

      this.reloadFahrer();
    },
    async verifyBewerbung() {

      const ResponseVerifyFahrer = await axios.put("Fahrer/updateVerifiziert/"+this.currentRowItem.fahrernummer);

      console.log(this.currentRowItem);
      console.log(this.currentRowItem.fahrernummer);

      this.reloadFahrer();
    }
  },
  mounted() {
    this.data = [
      {
        anrede: '',
      }
    ]
    this.reloadFahrer();
  },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      allFahrer: "",
      currentRowItem: "",
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
          text: 'Verifiziert',
          value: 'verifiziert'
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
