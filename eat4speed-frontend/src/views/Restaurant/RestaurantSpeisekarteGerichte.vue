<template>
  <v-main>
    <v-card class="mx-5 my-5">
    <v-app-bar color="primary"  dark>
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
          <router-link  to="/restaurant/controlpanel">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>ControlPanel</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/speisekarteGerichte"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-silverware</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Speisekarte bearbeiten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/bestellungen"><v-list-item>
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
          <router-link  to="/restaurant/stammdaten"><v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Stammdaten</v-list-item-title>
          </v-list-item>
          </router-link>
          <router-link  to="/restaurant/umsatzstatistik">
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
          <div class="text-h3 mb-10"> Restaurantname</div>
          <v-col class="d-flex justify-space-between mb-6">
            <v-card-title class="text-h4"> Speisekarte</v-card-title>
            <v-btn
                color="red"
                dark
                align="right"
                class="mt-5"
                @click="changeDisplayGerichte"
            >
              Gerichte
            </v-btn>
            <v-btn
                color="red"
                dark
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
              :item-height="300"
              max-height="500"
          >
            <template v-slot:default="{ item }">
              <v-list-item>
                <v-list-item-content>
                  <v-img alt="Bild von Essen" max-height="300" max-width="300" :src="item.img"></v-img>
                </v-list-item-content>
                <v-list-item-content>
                  <v-list-item-group align="left">
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                    <br>
                    <br>
                  </v-list-item-group>
                </v-list-item-content>
                <v-list-item-content></v-list-item-content>
                <v-list-item-group align="left">
                  <v-list-item-content>{{ item.price }}</v-list-item-content>
                  <v-dialog
                      :retain-focus="false"
                      v-model="artDialog"
                      width="500"
                      persistent
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                          color="red"
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
                      <v-col>
                        <v-text-field
                            v-model="gerichtName"
                            :counter="20"
                            label="Artikelname"
                            required
                        ></v-text-field>
                        <v-textarea
                            v-model="gerichtBeschreibung"
                            :counter="100"
                            label="Artikelbeschreibung"
                            required
                        ></v-textarea>
                        <label>
                          Bild auswählen
                          <input type="file" ref="file" id="fileChange" accept="image/*" v-on:change="selectedPicture()"/>
                        </label>
                        <v-text-field label="Preis in €" v-model="gerichtPreis" type="number" append-icon="currency-eur">
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
                                color="red"
                                dark
                                class="justify-center"
                            >
                              Fertig
                            </v-btn>
                            <v-spacer class="mr-2"></v-spacer>
                            <v-btn
                                @click="deleteGericht(); artDialog = false"
                                color="red"
                                dark
                                class="justify-center"
                            >
                              Löschen
                            </v-btn>
                            <v-spacer class="mr-2"></v-spacer>
                            <v-btn
                                @click="artDialog = false; test();"
                                color="red"
                                dark
                                justify
                            >
                              Abbruch
                            </v-btn>
                          </v-row>
                        </v-col>
                      </v-col>
                    </v-card>
                  </v-dialog>
                </v-list-item-group>
              </v-list-item>
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
                  color="red"
                  dark
                  v-bind="attrs"
                  v-on="on"
                  @click="currentlyAdding = true"
              >
                Artikel hinzufügen
              </v-btn>
            </template>
            <v-card>
              <v-col>
                <v-text-field
                    v-model="gerichtName"
                    :counter="20"
                    label="Artikelname"
                    required
                ></v-text-field>
                <v-textarea
                    v-model="gerichtBeschreibung"
                    :counter="100"
                    label="Artikelbeschreibung"
                    required
                ></v-textarea>
                <label>
                  Bild auswählen
                  <input type="file" ref="file" id="file" accept="image/*" v-on:change="selectedPicture()"/>
                </label>
                <v-text-field label="Preis in €" v-model="gerichtPreis" type="number" append-icon="currency-eur">
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
                        color="red"
                        dark
                        class="justify-center"
                    >
                      Fertig
                    </v-btn>
                    <v-spacer class="mr-2"></v-spacer>
                    <v-btn
                        @click="artDialog2 = false; test();"
                        color="red"
                        dark
                        justify
                    >
                      Abbruch
                    </v-btn>
                  </v-row>
                </v-col>
              </v-col>
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
  mounted() {
    this.displayGetraenke=false;

    this.getLoggedInRestaurant();
    this.checkIfVerified();
    this.loadGerichte()
  },
  methods: {
    async getLoggedInRestaurant()
    {
      const response = await axios.get("Benutzer/getRestaurant_IDByBenutzername/"+this.$cookies.get('emailAdresse'));
      this.restaurantID = response.data[0];
    },
    async checkIfVerified()
    {
      const response = await axios.get("Restaurant/VERIFIED");
      for(let i = 0; i<response.data.length;i++)
      {
        this.verifiedRestaurants[i] = response.data[i][2];
      }

      if(this.verifiedRestaurants.includes(this.restaurantID))
      {
        this.isVerified = true;
      }
      else
      {
        this.isVerified = false;
      }
    },
    async loadGerichte() {

      let gerichtPath;

      if(this.displayGetraenke === false)
      {
        gerichtPath = "Gericht/getAllGerichtDataRestaurantSpeisekarte/";
      }
      else{
        gerichtPath = "Gericht/getAllGetraenkDataRestaurantSpeisekarte/";
      }

      const ResponseGerichte = await axios.get(gerichtPath + this.restaurantID);

      console.log(ResponseGerichte);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];
        this.gerichtIDs[i] = gerichtData[4];
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gerichtIDs[i],config);

        console.log(responsePicture);

        if(responsePicture.status !== 204)
        {
          console.log("received Picture")
          console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          console.log(imageURL);

          this.imgs[i] = imageURL;
        }
        else
        {
          this.imgs[i] = "";
        }

      }
      console.log(this.imgs);
      this.amountGerichte = 0;
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
    changeDisplayGetraenke() {
      if(this.displayGetraenke===false)
      {
        this.displayGetraenke = !this.displayGetraenke;
        this.loadGerichte();
      }

    },
    changeDisplayGerichte() {
      if(this.displayGetraenke===true)
      {
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
      console.log(this.computedItems);
      console.log(this.items);

    },
    async loadKategorien() {
      const ResponseAllKategorien = await axios.get("/Kategorie");

      console.log(ResponseAllKategorien);
      let arrayKategorien = [];
      let it;
      for (it = 0; it < ResponseAllKategorien.data.length; it++) {
        let kategorie = ResponseAllKategorien.data[it];

        arrayKategorien[it] = kategorie;

      }
      console.log(arrayKategorien);
      this.kategorien = arrayKategorien;
    },
    async test() {
      console.log(this.value);
      console.log(this.valueA);
    },
    async loadAllergene() {
      const ResponseAllAllegergene = await axios.get("/Allergene");

      console.log(ResponseAllAllegergene);
      let arrayAllergene = [];
      let it;
      for (it = 0; it < ResponseAllAllegergene.data.length; it++) {
        let allergen = ResponseAllAllegergene.data[it];

        arrayAllergene[it] = allergen;

      }
      console.log(arrayAllergene);
      this.allergen = arrayAllergene;

    },
    async addGericht() {

      console.log("it's fine");

      if(this.isVerified)
      {
        if (this.gerichtVerfuegbar === true) {
          this.gerichtVerfuegbar = 1;
        } else {
          this.gerichtVerfuegbar = 0;
        }

        if(this.displayGetraenke === true) {
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
          ist_Getraenk: this.istGetraenk
        }

        const responseGericht = await axios.post("/Gericht/addGericht", gericht);

        this.gericht_ID = responseGericht.data.gericht_ID;

        for (let i = 0; i < this.selectedKategorien.length; i++) {
          let gericht_Kategorie = {
            gericht_ID: this.gericht_ID,
            kategorie: this.selectedKategorien[i]
          }
          await axios.post("/Gericht_Kategorie", gericht_Kategorie);

        }
        for (let i = 0; i < this.selectedAllergene.length; i++) {
          let gericht_Allergene = {
            gericht_ID: this.gericht_ID,
            allergen: this.selectedAllergene[i]
          }
          await axios.post("/Gericht_Allergene", gericht_Allergene);

        }

        if(this.gerichtBild !== null)
        {
          const picturedata = new FormData();
          picturedata.append("file", this.gerichtBild);
          picturedata.append("fileName", "Bild"+this.gericht_ID);

          const options = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          };

          const responsePictureUpload = await axios.post( '/GerichtBilder/upload',
              picturedata,options
          ).then(function(){
            console.log('Picture successfully uploaded');
          })
              .catch(function(){
                console.log('Picture upload error');
              });

          console.log(responsePictureUpload);
        }
        this.loadGerichte();
      }
      else
      {
        alert("Nicht verifiziert!");
      }

    },
    async fillDataOfGerichtToAlter(item) {
      this.editedItem = item;

      this.gerichtBild = null;

      //this.gerichtName = item.id;
      console.log(item.id);
      console.log(this.editedItem.id);

      const responseGetGericht = await axios.get("/Gericht/"+this.editedItem.id);

      this.gerichtName = responseGetGericht.data.name;
      this.gerichtPreis = responseGetGericht.data.preis;
      this.gerichtBeschreibung = responseGetGericht.data.beschreibung;

      if(responseGetGericht.data.verfuegbar === 0)
      {
        this.gerichtVerfuegbar = false;
      }
      else
      {
        this.gerichtVerfuegbar = true;
      }

      const responseGetAllergene = await axios.get("/Gericht_Allergene/getGericht_AllergeneByGericht_ID/"+this.editedItem.id);

      for(let i = 0; i<responseGetAllergene.data.length;i++)
      {
        this.selectedAllergene[i]=responseGetAllergene.data[i];
      }

      const responseGetKategorie = await axios.get("/Gericht_Kategorie/getGericht_KategorieByGericht_ID/"+this.editedItem.id);

      for(let i = 0; i<responseGetKategorie.data.length;i++)
      {
        this.selectedKategorien[i]=responseGetKategorie.data[i];
      }

      console.log(responseGetGericht);
    },
    async changeGericht(){

      console.log("fuck this shit")

      if (this.gerichtVerfuegbar === true) {
        this.gerichtVerfuegbar = 1;
      } else {
        this.gerichtVerfuegbar = 0;
      }

      if(this.displayGetraenke === true) {
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

      const responseGerichtToAlter = await axios.put("/Gericht/updateGerichtAllData", gericht);

      console.log(responseGerichtToAlter);

      await axios.delete("Gericht_Allergene/deleteGerichtAllergeneByGerichtID/"+this.editedItem.id);
      await axios.delete("Gericht_Kategorie/deleteGerichtKategorieByGerichtID/"+this.editedItem.id);

      for (let i = 0; i < this.selectedKategorien.length; i++) {
        let gericht_Kategorie = {
          gericht_ID: this.editedItem.id,
          kategorie: this.selectedKategorien[i]
        }
        await axios.post("/Gericht_Kategorie", gericht_Kategorie);

      }
      for (let i = 0; i < this.selectedAllergene.length; i++) {
        let gericht_Allergene = {
          gericht_ID: this.editedItem.id,
          allergen: this.selectedAllergene[i]
        }
        await axios.post("/Gericht_Allergene", gericht_Allergene);

      }

      if(this.gerichtBild !== null)
      {
        const picturedata = new FormData();
        picturedata.append("file", this.gerichtBild);
        picturedata.append("fileName", "Bild"+this.editedItem.id);

        const options = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };

        const responsePictureUpload = await axios.post( '/GerichtBilder/upload',
            picturedata,options
        ).then(function(){
          console.log('Picture successfully uploaded');
        })
            .catch(function(){
              console.log('Picture upload error');
            });

        console.log(responsePictureUpload);
      }

      this.version++;
      this.loadGerichte();

    },
    selectedPicture() {
      this.gerichtBild = this.$refs.file.files[0];
      console.log(this.gerichtBild);
    },
    async deleteGericht() {
      await axios.delete("Gericht_Allergene/deleteGerichtAllergeneByGerichtID/"+this.editedItem.id);
      await axios.delete("Gericht_Kategorie/deleteGerichtKategorieByGerichtID/"+this.editedItem.id);
      await axios.delete("Gericht/"+this.editedItem.id);
      this.loadGerichte();
    }
  },
  data: () => ({
    drawer: false,
    group: null,
    switch1: true,
    artDialog: false,
    artDialog2: false,
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
    amountGerichte : 4,
    editedItem : "",
    istGetraenk: "",
    displayGetraenke: false,
    currentlyAdding : false
  }),

  computed: {
    items() {
      let i = 0
      console.log("compute");
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
  watch:{
    artDialog2: function(show){
      if(show){
        if(this.currentlyAdding){
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