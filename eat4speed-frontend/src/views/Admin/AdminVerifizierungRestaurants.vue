<template>
  <v-app>
    <v-main>
      <h1 class="subheading">Verifizierung Restaurant</h1>
      <v-card>
        <v-row class="ma-2">
          <v-col>
            <v-btn :to="{ name: 'AdminVerifizierungFahrer'}" width="275px" color="primary" depressed tile>Zur Fahrer Verifizierung</v-btn>
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
                        @change="reloadRestaurant"
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
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminVerifizierungRestaurants",
  methods: {
    async reloadRestaurant(){

      console.log(this.select);
      console.log(this.select.value)

      if(this.select.value === 1) {
        const ResponseAllRestaurant = await axios.get("/Restaurant/ALL", this.$store.getters.getLoginData);

        console.log(ResponseAllRestaurant);
        console.log(ResponseAllRestaurant.data);
        console.log(ResponseAllRestaurant.data[0])

        var test = ResponseAllRestaurant.data[0];

        console.log(test[0]);

        this.allRestaurant = ResponseAllRestaurant;

        var arrayAllRestaurant = [];

        let it;
        for (it = 0; it < ResponseAllRestaurant.data.length; it++) {
          let restaurant = ResponseAllRestaurant.data[it];

          let entry = {
            vorname: restaurant[0],
            nachname: restaurant[1],
            restaurant_Id: restaurant[2],
            name_Des_Restaurants: restaurant[3],
            strasse: restaurant[4],
            hausnummer: restaurant[5],
            ort: restaurant[6],
            postleitzahl: restaurant[7],
            verifiziert: restaurant[8],
            email: restaurant[9]
          };
          arrayAllRestaurant[it] = entry;

        }



        this.data = arrayAllRestaurant;

        console.log(arrayAllRestaurant);
      }

      if(this.select.value === 2) {
        const ResponseNotVerifiedRestaurant = await axios.get("/Restaurant/NOT_VERIFIED", this.$store.getters.getLoginData);

        console.log(ResponseNotVerifiedRestaurant);
        console.log(ResponseNotVerifiedRestaurant.data);
        console.log(ResponseNotVerifiedRestaurant.data[0])

        this.allRestaurant = ResponseNotVerifiedRestaurant;

        var arrayNotVerifiedRestaurant = [];

        let it;
        for (it = 0; it < ResponseNotVerifiedRestaurant.data.length; it++) {
          let restaurant = ResponseNotVerifiedRestaurant.data[it];

          let entry = {
            vorname: restaurant[0],
            nachname: restaurant[1],
            restaurant_Id: restaurant[2],
            name_Des_Restaurants: restaurant[3],
            strasse: restaurant[4],
            hausnummer: restaurant[5],
            ort: restaurant[6],
            postleitzahl: restaurant[7],
            verifiziert: restaurant[8],
            email: restaurant[9]
          };
          arrayNotVerifiedRestaurant[it] = entry;

        }


        this.data = arrayNotVerifiedRestaurant;

        console.log(arrayNotVerifiedRestaurant);
      }

      if(this.select.value === 3) {
        const ResponseVerifiedRestaurant = await axios.get("/Restaurant/VERIFIED", this.$store.getters.getLoginData);

        console.log(ResponseVerifiedRestaurant);
        console.log(ResponseVerifiedRestaurant.data);
        console.log(ResponseVerifiedRestaurant.data[0])

        this.allRestaurant = ResponseVerifiedRestaurant;

        var arrayVerifiedRestaurant = [];

        let it;
        for (it = 0; it < ResponseVerifiedRestaurant.data.length; it++) {
          let restaurant = ResponseVerifiedRestaurant.data[it];

          let entry = {
            vorname: restaurant[0],
            nachname: restaurant[1],
            restaurant_Id: restaurant[2],
            name_Des_Restaurants: restaurant[3],
            strasse: restaurant[4],
            hausnummer: restaurant[5],
            ort: restaurant[6],
            postleitzahl: restaurant[7],
            verifiziert: restaurant[8],
            email: restaurant[9]
          };
          arrayVerifiedRestaurant[it] = entry;

        }

        this.data = arrayVerifiedRestaurant;

        console.log(arrayVerifiedRestaurant);
      }

    },
    setCurrentRowItem(item)
    {
      this.currentRowItem = item;
      console.log(item);
      console.log(this.currentRowItem);
    },
    async deleteBewerbung() {

      const deleteBe = {
        emailAdresse: this.currentRowItem.email,
        loeschbegruendung: this.deleteReason
      }
      await axios.post("Blacklist",deleteBe, this.$store.getters.getLoginData);

      await axios.put("Benutzer/deleteBenutzerByEmail/"+this.currentRowItem.email, this.$store.getters.getLoginData);
      this.reloadRestaurant();
    },
    async verifyBewerbung() {

      await axios.put("Restaurant/updateVerifiziert/"+this.currentRowItem.restaurant_Id, this.$store.getters.getLoginData);

      console.log(this.currentRowItem);
      console.log(this.currentRowItem.restaurant_Id);

      this.reloadRestaurant();
    }
  },
  mounted() {
    this.data = [
      {
        anrede: '',
      }
    ]
    this.reloadRestaurant();
  },
  data() {
    return {
      data: [],
      acceptDialog: false,
      deleteDialog: false,
      allRestaurant: "",
      currentRowItem: "",
      restaurantSelection: "",
      search: '',
      deleteReason: "",
      select: {text: 'Alle Bewerbungen', value: 1},
      items: [
        {text: 'Alle Bewerbungen', value: 1},
        {text: 'Nicht verifizierte Restaurants', value: 2},
        {text: 'Verifizierte Restaurants', value: 3},
      ],
      headers: [
        {
          text: "Email",
          value: "email"
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
          text: 'Restaurant_ID',
          value: 'restaurant_Id'
        },
        {
          text: 'Restaurantname',
          value: 'name_Des_Restaurants'
        },
        {
          text: 'Straße',
          value: 'strasse'
        },
        {
          text: 'Hausnummer',
          value: 'hausnummer'
        },
        {
          text: 'Ort',
          value: 'ort'
        },
        {
          text: 'PLZ',
          value: 'postleitzahl'
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
