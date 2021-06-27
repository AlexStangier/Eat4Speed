<template>
  <v-main>
    <h1 class="subheading">Verifizierung Fahrer</h1>
    <v-card>
      <v-row class="ma-2">
        <v-col>
          <v-btn :to="{ name: 'AdminVerifizierungRestaurants'}" width="275px" color="primary" depressed tile>Zur Restaurant Verifizierung</v-btn>
        </v-col>
      </v-row>
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
                  v-if="item.verifiziert === 0"
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
                    <v-col align="center" justify="center">
                      <v-btn class="white--text" color="green" depressed tile width="200"
                             @click="acceptDialog = false; verifyBewerbung();">Verifizieren
                      </v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn class="white--text" color="red" depressed tile width="200" @click="acceptDialog = false">Abbrechen</v-btn>
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
                    <v-col align="center" justify="center">
                      <v-btn color="primary" depressed tile width="200" @mousedown="deleteReason='Unseriös'" @click="deleteDialog = false; deleteBewerbung();">Unseriös</v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn color="primary" depressed tile width="200" @mousedown="deleteReason='Spam'" @click="deleteDialog = false; deleteBewerbung();">Spam</v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn color="primary" depressed tile width="200" @mousedown="deleteReason='Unvollständig'" @click="deleteDialog = false; deleteBewerbung();">Unvollständig</v-btn>
                    </v-col>
                    <v-col align="center" justify="center">
                      <v-btn color="primary" depressed tile width="200" @mousedown="deleteReason='Keine Angabe'" @click="deleteDialog = false; deleteBewerbung();">Keine Angabe</v-btn>
                    </v-col>
                    <v-col cols="12"></v-col>
                    <v-col align="center" justify="center">
                      <v-btn color="primary" depressed tile width="200" @click="deleteDialog = false;">Abbrechen</v-btn>
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
</template>

<script>
import axios from "axios";

export default {
  name: "AdminVerifizierungFahrer",
  methods: {
    async reloadFahrer(){
      if (this.select.value === 1) {
        const ResponseAllFahrer = await axios.get("/Fahrer/ALL", this.$store.getters.getLoginData);

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
            verifiziert: fahrer[4],
            anrede: fahrer[5],
            email: fahrer[6]
          };
          arrayAllFahrer[it] = entry;
        }
        this.data = arrayAllFahrer;
      }

      if (this.select.value === 2) {
        const ResponseNotVerifiedFahrer = await axios.get("/Fahrer/NOT_VERIFIED", this.$store.getters.getLoginData);

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
            verifiziert: fahrer[4],
            anrede: fahrer[5],
            email: fahrer[6]
          };
          arrayNotVerifiedFahrer[it] = entry;

        }
        this.data = arrayNotVerifiedFahrer;
      }

      if(this.select.value === 3) {
        const ResponseVerifiedFahrer = await axios.get("/Fahrer/VERIFIED", this.$store.getters.getLoginData);

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
            verifiziert: fahrer[4],
            anrede: fahrer[5],
            email: fahrer[6]
          };
          arrayVerifiedFahrer[it] = entry;
        }

        this.data = arrayVerifiedFahrer;
      }
    },
    setCurrentRowItem(item)
    {
      this.currentRowItem = item;
    },
    async deleteBewerbung() {
      const deleteBe = {
        emailAdresse: this.currentRowItem.email,
        loeschbegruendung: this.deleteReason
      }
      await axios.post("Blacklist",deleteBe, this.$store.getters.getLoginData);

      await axios.put("Benutzer/deleteBenutzerByEmail/"+this.currentRowItem.email, this.$store.getters.getLoginData);
      this.reloadFahrer();
    },
    async verifyBewerbung() {
      await this.$http.put("Fahrer/updateVerifiziert/"+this.currentRowItem.fahrernummer, this.$store.getters.getLoginData);
      this.reloadFahrer();
    }
  },
  mounted() {
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
      deleteReason: "",
      search: '',
      select: {text: 'Alle Bewerbungen', value: 1},
      items: [
        {text: 'Alle Bewerbungen', value: 1},
        {text: 'Nicht verifizierte Fahrer', value: 2},
        {text: 'Verifizierte Fahrer', value: 3},
      ],
      headers: [
        {
          text: "Email",
          value: "email"
        },
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
