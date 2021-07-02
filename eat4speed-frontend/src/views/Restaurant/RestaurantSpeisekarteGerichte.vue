<template>
  <v-main>
    <v-card class="mx-5 my-5">
      <v-app-bar color="primary" dark>
        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
        <v-toolbar-title>Speisekarte Gerichte</v-toolbar-title>
      </v-app-bar>
      <v-navigation-drawer
          v-model="drawer"
          absolute
          temporary
      >
        <v-list
            nav
            dense
        >
          <v-list-item-group
              v-model="group"
              active-class="deep-purple--text text--accent-4"
          >
            <router-link to="/restaurant/controlpanel">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-home</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Startseite</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/speisekarteGerichte">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-silverware</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/bestellungen">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-view-headline</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Offene Bestellungen</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/schichtplan">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-watch</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Schichtplan</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/stammdaten">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-account</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Stammdaten</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link to="/restaurant/umsatzstatistik">
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-margin</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Umsatzstatistik</v-list-item-title>
              </v-list-item>
            </router-link>

          </v-list-item-group>
        </v-list>
      </v-navigation-drawer>

      <v-container fill-height fluid>
        <v-layout align-center justify-center>
          <v-flex md6 sm6 xs12>
            <div class="text-h3 mb-10" id="restaurantName"></div>
            <v-col class="d-flex justify-space-between mb-6">
              <v-card-title class="text-h4"> Speisekarte</v-card-title>
              <v-btn
                  :color="selectedButton === 0 ? 'primary' : 'blue-grey'"
                  dark
                  tile
                  align="right"
                  class="mt-5"
                  @click="changeDisplayGerichte"
              >
                Gerichte
              </v-btn>
              <v-btn
                  :color="selectedButton === 1 ? 'primary' : 'blue-grey'"
                  dark
                  tile
                  align="right"
                  class="mt-5"
                  @click="changeDisplayGetraenke"
              >
                Getränke
              </v-btn>
            </v-col>
            <v-divider></v-divider>
            <v-virtual-scroll
                :items="items"
                :item-height="175"
                max-height="500"
            >
              <template v-slot:default="{ item }">

                <v-card
                    flat
                    tile
                    outlined
                >
                  <v-container fluid>
                    <v-row>
                      <v-col
                          cols="4"
                      >
                        <v-card
                            outlined
                            flat
                            tile
                            max-width="300"
                            max-height="300"
                        >
                          <v-img alt="Bild von Essen" min-height="150" max-height="150" max-width="300" :src="item.img"></v-img>
                        </v-card>
                      </v-col>
                      <v-col>
                        <v-row
                          v-for="a in 2"
                          :key="a"
                        >
                          <v-col
                              cols="8"
                          >
                            <v-card
                                v-if="a === 1"
                                flat
                            >
                              {{ item.name }}
                            </v-card>
                            <v-card
                                v-if="a === 1"
                                class="v-list-item__subtitle"
                                flat
                            >
                              <span
                                  v-if="item.description.length <= 40"
                              >
                                {{ item.description }}
                              </span>
                              <span
                                  v-else
                              >
                                {{ item.description.substring(0,38)+".." }}
                              </span>
                            </v-card>
                          </v-col>
                          <v-col>
                            <v-card
                                v-if="a === 2"
                                class="text-right"
                                flat
                            >{{ item.price + ' €'}}
                            </v-card>
                            <v-card
                                v-if="a === 2"
                                class="text-right pt-1"
                                flat
                            >
                              <v-dialog
                                  :retain-focus="false"
                                  v-model="artDialog"
                                  width="500"
                                  persistent
                              >
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn
                                      color="primary"
                                      tile
                                      dark
                                      v-bind="attrs"
                                      v-on="on"
                                      small
                                      bottom
                                      @click="fillDataOfGerichtToAlter(item); currentlyAdding = false"
                                  >
                                    Bearbeiten
                                  </v-btn>
                                </template>
                                <v-card>
                                  <v-form
                                      ref="updateForm"
                                      v-model="valifUpdate"
                                  >
                                    <v-col>
                                      <v-text-field
                                          v-model="gerichtName"
                                          :counter="50"
                                          label="Artikelname"
                                          required
                                          :rules="[rules.required, rules.lettersAndSpacesOnly]"
                                          maxlength="50"
                                      ></v-text-field>
                                      <v-textarea
                                          v-model="gerichtBeschreibung"
                                          :counter="100"
                                          label="Artikelbeschreibung"
                                          required
                                          :rules="[rules.required]"
                                          maxlength="100"
                                      ></v-textarea>
                                      <label>
                                        Bild auswählen
                                        <input type="file" ref="fileUp" id="fileUp" accept="image/*" v-on:change="selectedPictureUp()"/>
                                      </label>
                                      <v-text-field label="Preis in €" v-model="gerichtPreis" type="number"
                                                    append-icon="currency-eur" :rules="[rules.required,rules.price]">
                                      </v-text-field>
                                      <v-checkbox label="Artikel verfügbar?" v-model="gerichtVerfuegbar">
                                      </v-checkbox>
                                      <v-select
                                          ref="KategorieSelect"
                                          v-model="selectedKategorien"
                                          :items="kategorien"
                                          chips
                                          label="Kategorien"
                                          multiple
                                          outlined
                                          block
                                          @click="loadKategorien"
                                      ></v-select>
                                      <v-spacer class="ma-2"></v-spacer>
                                      <v-select
                                          v-model="selectedAllergene"
                                          :items="allergen"
                                          chips
                                          label="Allergene"
                                          multiple
                                          outlined
                                          block
                                          @click="loadAllergene"
                                      ></v-select>
                                      <v-spacer class="ma-2"></v-spacer>
                                      <v-col>
                                        <v-row>
                                          <v-btn
                                              @click="changeGericht(); artDialog = false"
                                              color="primary"
                                              dark
                                              class="justify-center"
                                              :disabled="!valifUpdate"
                                          >
                                            Fertig
                                          </v-btn>
                                          <v-spacer class="mr-2"></v-spacer>
                                          <v-btn
                                              @click="deleteGericht(); artDialog = false"
                                              color="primary"
                                              dark
                                              class="justify-center"
                                          >
                                            Löschen
                                          </v-btn>
                                          <v-spacer class="mr-2"></v-spacer>
                                          <v-btn
                                              @click="artDialog = false; test();"
                                              color="primary"
                                              dark
                                              justify
                                          >
                                            Abbruch
                                          </v-btn>
                                        </v-row>
                                      </v-col>
                                    </v-col>
                                  </v-form>
                                </v-card>
                              </v-dialog>
                            </v-card>
                          </v-col>
                        </v-row>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card>
                <v-divider></v-divider>
              </template>
            </v-virtual-scroll>
            <!------------  Artikel hinzufügen + ------------->
            <v-dialog
                v-model="artDialog2"
                width="500"
                persistent
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    v-bind="attrs"
                    v-on="on"
                    @click="currentlyAdding = true"
                >
                  Artikel hinzufügen
                </v-btn>
              </template>
              <v-card>
                <v-form
                    ref="addForm"
                    v-model="validAdd"
                >
                  <v-col>
                    <v-text-field
                        v-model="gerichtName"
                        :counter="50"
                        label="Artikelname"
                        required
                        maxlength="50"
                        :rules="[rules.required, rules.lettersAndSpacesOnly]"
                    ></v-text-field>
                    <v-textarea
                        v-model="gerichtBeschreibung"
                        :counter="100"
                        label="Artikelbeschreibung"
                        :rules="[rules.required]"
                        required
                        maxlength="100"
                    ></v-textarea>
                    <label>
                      Bild auswählen
                      <input type="file" ref="file" id="file" accept="image/*" v-on:change="selectedPicture()"/>
                    </label>
                    <v-text-field label="Preis in €" v-model="gerichtPreis" type="number" append-icon="currency-eur" maxlength="10" :rules="[rules.required,rules.price]">
                    </v-text-field>
                    <v-checkbox label="Artikel verfügbar?" v-model="gerichtVerfuegbar">
                    </v-checkbox>
                    <v-select
                        ref="KategorieSelect"
                        v-model="selectedKategorien"
                        :items="kategorien"
                        chips
                        label="Kategorien"
                        multiple
                        outlined
                        block
                        @click="loadKategorien"
                    ></v-select>
                    <v-spacer class="ma-2"></v-spacer>
                    <v-select
                        v-model="selectedAllergene"
                        :items="allergen"
                        chips
                        label="Allergene"
                        multiple
                        outlined
                        block
                        @click="loadAllergene"
                    ></v-select>
                    <v-spacer class="ma-2"></v-spacer>
                    <v-col>
                      <v-row>
                        <v-btn
                            @click="addGericht(); artDialog2 = false"
                            color="primary"
                            dark
                            class="justify-center"
                            :disabled="!validAdd"
                        >
                          Fertig
                        </v-btn>
                        <v-spacer class="mr-2"></v-spacer>
                        <v-btn
                            @click="artDialog2 = false; test();"
                            color="primary"
                            dark
                            justify
                        >
                          Abbruch
                        </v-btn>
                      </v-row>
                    </v-col>
                  </v-col>
                </v-form>
              </v-card>
            </v-dialog>
            <!------------  Artikel hinzufügen - ------------->
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "RestaurantSpeisekarteGerichte",
  async mounted() {
    this.displayGetraenke = false;

    await this.getLoggedInRestaurant();
    await this.checkIfVerified();
    await this.loadRestaurantName();
    await this.loadGerichte();
  },
  methods: {
    selectGerichte(){
      this.selectedButton = 0;
    },
    selectGetraenke(){
      this.selectedButton = 1;
    },
    async getLoggedInRestaurant() {
      let response;
      try {
        response = await axios.get("Benutzer/getRestaurant_IDByBenutzername/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      }
      catch (e)
      {
        if(e.response.status === 403 || e.response.status === 401)
        {
          window.location.reload();
        }
      }

      this.restaurantID = response.data[0];
    },
    async checkIfVerified() {
      const response = await axios.get("Restaurant/VERIFIED", this.$store.getters.getLoginData);
      for (let i = 0; i < response.data.length; i++) {
        this.verifiedRestaurants[i] = response.data[i][2];
      }

      if (this.verifiedRestaurants.includes(this.restaurantID)) {
        this.isVerified = true;
      } else {
        this.isVerified = false;
      }
    },
    async loadRestaurantName() {
      const ResponseRestaurant = await axios.get(
          "Restaurant/getAllRestaurantDataByRestaurant_ID/" + this.restaurantID, this.$store.getters.getLoginData);
      const RestaurantData = ResponseRestaurant.data[0];
      document.getElementById("restaurantName").innerHTML = RestaurantData[1];
    },
    async loadGerichte() {

      let gerichtPath;

      if (this.displayGetraenke === false) {
        gerichtPath = "Gericht/getAllGerichtDataRestaurantSpeisekarte/";
      } else {
        gerichtPath = "Gericht/getAllGetraenkDataRestaurantSpeisekarte/";
      }

      const ResponseGerichte = await axios.get(gerichtPath + this.restaurantID, this.$store.getters.getLoginData);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];
        this.gerichtIDs[i] = gerichtData[4];
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        const config = {responseType: "arraybuffer"};
        const responsePicture = await axios.get("/GerichtBilder/getBild/" + this.gerichtIDs[i], config);

        if (responsePicture.status !== 204) {

          let pictureBlob = new Blob([responsePicture.data], {type: responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);

          this.imgs[i] = imageURL;
        } else {
          this.imgs[i] = "";
        }

      }
      this.amountGerichte = 0;
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
    changeDisplayGetraenke() {
      this.selectGetraenke();
      if (this.displayGetraenke === false) {
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

    },
    changeDisplayGerichte() {
      this.selectGerichte();
      if (this.displayGetraenke === true) {
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

    },
    openLogin() {
      this.$refs.Anmeldung.class = "px-4 d-flex"
    },
    computeItems() {

      for (let i = 0; i < this.names.length; i++) {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]

        let entry = {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg
        }
        this.computedItems[i] = entry;
      }
    },
    async loadKategorien() {
      const ResponseAllKategorien = await axios.get("/Kategorie");

      let arrayKategorien = [];
      let it;
      for (it = 0; it < ResponseAllKategorien.data.length; it++) {
        let kategorie = ResponseAllKategorien.data[it];

        arrayKategorien[it] = kategorie;

      }
      this.kategorien = arrayKategorien;
    },
    async test() {
      //console.log(this.value);
      //console.log(this.valueA);
    },
    async loadAllergene() {
      const ResponseAllAllegergene = await axios.get("/Allergene");

      let arrayAllergene = [];
      let it;
      for (it = 0; it < ResponseAllAllegergene.data.length; it++) {
        let allergen = ResponseAllAllegergene.data[it];

        arrayAllergene[it] = allergen;

      }
      this.allergen = arrayAllergene;

    },
    async addGericht() {

      if (this.isVerified) {
        if (this.gerichtVerfuegbar === true) {
          this.gerichtVerfuegbar = 1;
        } else {
          this.gerichtVerfuegbar = 0;
        }

        if (this.displayGetraenke === true) {
          this.istGetraenk = 1;
        } else {
          this.istGetraenk = 0;
        }

        var gericht = {
          beschreibung: this.gerichtBeschreibung,
          name: this.gerichtName,
          restaurant_ID: this.restaurantID,
          verfuegbar: this.gerichtVerfuegbar,
          preis: this.gerichtPreis,
          ist_Getraenk: this.istGetraenk,
          geloescht: 0
        }

        const responseGericht = await axios.post("/Gericht/addGericht", gericht, this.$store.getters.getLoginData);

        this.gericht_ID = responseGericht.data.gericht_ID;

        for (let i = 0; i < this.selectedKategorien.length; i++) {
          let gericht_Kategorie = {
            gericht_ID: this.gericht_ID,
            kategorie: this.selectedKategorien[i]
          }
          await axios.post("/Gericht_Kategorie", gericht_Kategorie, this.$store.getters.getLoginData);

        }
        for (let i = 0; i < this.selectedAllergene.length; i++) {
          let gericht_Allergene = {
            gericht_ID: this.gericht_ID,
            allergen: this.selectedAllergene[i]
          }
          await axios.post("/Gericht_Allergene", gericht_Allergene, this.$store.getters.getLoginData);

        }

        if (this.gerichtBild !== null) {
          const picturedata = new FormData();
          picturedata.append("file", this.gerichtBild);
          picturedata.append("fileName", "Bild" + this.gericht_ID);

          const options = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          };

          await axios.post('/GerichtBilder/upload',
              picturedata, options
          ).then(function () {
            console.log('Picture successfully uploaded');
          })
              .catch(function () {
                console.log('Picture upload error');
              });

        }
        this.loadGerichte();
      } else {
        alert("Nicht verifiziert!");
      }

    },
    async fillDataOfGerichtToAlter(item) {
      this.editedItem = item;

      this.gerichtBild = null;

      //this.gerichtName = item.id;
      //console.log(item.id);
      //console.log(this.editedItem.id);

      const responseGetGericht = await axios.get("/Gericht/" + this.editedItem.id, this.$store.getters.getLoginData);

      this.gerichtName = responseGetGericht.data.name;
      this.gerichtPreis = responseGetGericht.data.preis;
      this.gerichtBeschreibung = responseGetGericht.data.beschreibung;

      if (responseGetGericht.data.verfuegbar === 0) {
        this.gerichtVerfuegbar = false;
      } else {
        this.gerichtVerfuegbar = true;
      }

      const responseGetAllergene = await axios.get("/Gericht_Allergene/getGericht_AllergeneByGericht_ID/" + this.editedItem.id);

      for (let i = 0; i < responseGetAllergene.data.length; i++) {
        this.selectedAllergene[i] = responseGetAllergene.data[i];
      }

      const responseGetKategorie = await axios.get("/Gericht_Kategorie/getGericht_KategorieByGericht_ID/" + this.editedItem.id);

      for (let i = 0; i < responseGetKategorie.data.length; i++) {
        this.selectedKategorien[i] = responseGetKategorie.data[i];
      }
    },
    async changeGericht() {

      if (this.gerichtVerfuegbar === true) {
        this.gerichtVerfuegbar = 1;
      } else {
        this.gerichtVerfuegbar = 0;
      }

      if (this.displayGetraenke === true) {
        this.istGetraenk = 1;
      } else {
        this.istGetraenk = 0;
      }

      let gericht = {
        gericht_ID: this.editedItem.id,
        beschreibung: this.gerichtBeschreibung,
        name: this.gerichtName,
        restaurant_ID: this.restaurantID,
        verfuegbar: this.gerichtVerfuegbar,
        preis: this.gerichtPreis,
        ist_Getraenk: this.istGetraenk
      }

      await axios.put("/Gericht/updateGerichtAllData", gericht, this.$store.getters.getLoginData);

      await axios.delete("Gericht_Allergene/deleteGerichtAllergeneByGerichtID/" + this.editedItem.id, this.$store.getters.getLoginData);
      await axios.delete("Gericht_Kategorie/deleteGerichtKategorieByGerichtID/" + this.editedItem.id, this.$store.getters.getLoginData);

      for (let i = 0; i < this.selectedKategorien.length; i++) {
        let gericht_Kategorie = {
          gericht_ID: this.editedItem.id,
          kategorie: this.selectedKategorien[i]
        }
        await axios.post("/Gericht_Kategorie", gericht_Kategorie, this.$store.getters.getLoginData);

      }
      for (let i = 0; i < this.selectedAllergene.length; i++) {
        let gericht_Allergene = {
          gericht_ID: this.editedItem.id,
          allergen: this.selectedAllergene[i]
        }
        await axios.post("/Gericht_Allergene", gericht_Allergene, this.$store.getters.getLoginData);

      }

      if (this.gerichtBild !== null) {
        const picturedata = new FormData();
        picturedata.append("file", this.gerichtBild);
        picturedata.append("fileName", "Bild" + this.editedItem.id);

        const options = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };

        await axios.post('/GerichtBilder/upload',
            picturedata, options
        ).then(function () {
          //console.log('Picture successfully uploaded');
        })
            .catch(function () {
              //console.log('Picture upload error');
            });
      }

      this.version++;
      this.loadGerichte();

    },
    selectedPicture() {
      console.log(this.$refs)
      if(this.$refs.file.files === undefined)
      {
        for(let i = 0;i<this.$refs.file.length;i++)
        {
          if(this.$refs.file[i].files[0] !== undefined)
          {
            this.gerichtBild = this.$refs.file[i].files[0];
          }
        }
      }
      else
      {
        this.gerichtBild = this.$refs.file.files[0];
      }
    },
    selectedPictureUp() {
      console.log(this.$refs);
      if(this.$refs.fileUp.files === undefined)
      {
        for(let i = 0;i<this.$refs.fileUp.length;i++)
        {
          if(this.$refs.fileUp[i].files[0] !== undefined)
          {
            this.gerichtBild = this.$refs.fileUp[i].files[0];
          }
        }
      }
      else
      {
        this.gerichtBild = this.$refs.fileUp.files[0];
      }
    },
    async deleteGericht() {
      await axios.delete("Gericht_Allergene/deleteGerichtAllergeneByGerichtID/" + this.editedItem.id, this.$store.getters.getLoginData);
      await axios.delete("Gericht_Kategorie/deleteGerichtKategorieByGerichtID/" + this.editedItem.id, this.$store.getters.getLoginData);
      await axios.put("Gericht/deleteGerichtByGericht_ID/"+this.editedItem.id);
      this.loadGerichte();
    }
  },
  data: () => ({
    drawer: false,
    group: null,
    switch1: true,
    artDialog: false,
    artDialog2: false,
    validAdd: false,
    valifUpdate: false,
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurants: [],
    gerichtIDs: [],
    kategorien: [],
    selectedKategorien: [],
    allergen: [],
    selectedAllergene: [],
    verifiedRestaurants: [],
    isVerified: false,
    gerichtName: "",
    gerichtBeschreibung: "",
    gerichtBild: "",
    gerichtPreis: "",
    gerichtVerfuegbar: "",
    restaurantID: "",
    gericht_ID: "",
    computedItems: [],
    version: 0,
    amountGerichte: 4,
    editedItem: "",
    istGetraenk: "",
    displayGetraenke: false,
    currentlyAdding: false,
    selectedButton: 0, //0 = Gerichte; 1 = Getränke
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => (v && v.length >= 8) || "Mindestens 8 Zeichen",
      price: (v) => (v>0 && v<10000&&/^^[0-9]{1,3}((,|\.){1}[0-9]{1,2}){0,1}$/.test(v)) || "Dieser Preis wird nicht akzeptiert",
      lettersAndSpacesOnly: (v) => /^[a-zA-ZöäüÖÄÜß ]+$/.test(v) || "Nur Buchstaben und Leerzeichen sind erlaubt",
    },
  }),

  computed: {
    items() {
      let i = 0
      return Array.from({length: this.amountGerichte}, () => {
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        const cid = this.gerichtIDs[i]
        i++;

        //console.log(this.version);

        return {
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          id: cid
        }
      })
    },
  },
  watch: {
    artDialog2: function (show) {
      if (show) {
        if (this.currentlyAdding) {
          this.gerichtName = ''
          this.gerichtBeschreibung = ''
          this.gerichtBild = ''
          this.gerichtPreis = ''
          this.gerichtVerfuegbar = false
          this.selectedAllergene = ''
          this.selectedKategorien = ''
        }
      }
    }
  }
}
</script>

<style scoped>

</style>