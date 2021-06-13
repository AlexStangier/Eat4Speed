<template>
  <v-main>
    <v-container>
      <v-container>
        <v-row no-gutters align="center">
          <v-col sm="2">
            <v-select
                v-model="selectedBewertung"
                label="Bewertung"
                :items="selectRating"
                clearable="true"
                @change="applyBewertungFilterAndSearch"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"Sterne"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"Sterne"}}
              </template>
            </v-select>
          </v-col>
          <v-col sm="2" offset-sm="1">
            <v-select
                v-model="selectedEntfernung"
                label="Entfernung"
                :items="selectArea"
                clearable="true"
                @change="applyDistanceFilterAndSearch"
            >
              <template v-slot:selection="data">
                {{data.item}} {{"km"}}
              </template>
              <template v-slot:item="data">
                {{data.item}} {{"km"}}
              </template>
            </v-select>
          </v-col>
          <v-col sm="2" offset-sm="1">
            <v-btn>Auf der Karte anzeigen</v-btn>
          </v-col>
          <v-col sm="2" offset-sm="1">

            <v-menu
                bottom
                offset-y
                :close-on-content-click="false"
            >
              <template v-slot:activator="{ on, attrs}">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                >
                  <v-icon> mdi-account </v-icon>
                  Konto
                </v-btn>
              </template>
              <v-list
                  max-width="400"
              >
                <v-list-item>
                  <v-btn :to="{name: 'Favorites'}" text>
                    <v-icon>mdi-heart</v-icon>
                    Favoriten
                  </v-btn>
                </v-list-item>
                <v-list-item>
                  <v-btn text>
                    <v-icon>mdi-map-clock</v-icon>
                    Lieferansicht
                  </v-btn>
                </v-list-item>
                <v-list-item>
                  <v-btn text>
                    <v-icon>mdi-history</v-icon>
                    Bestellhistorie
                  </v-btn>
                </v-list-item>
                <v-list-item>
                  <v-btn :to="{name: 'Einstellungen'}" text>
                    <v-icon>mdi-account-cog-outline</v-icon>
                    Einstellungen
                  </v-btn>
                </v-list-item>
              </v-list>
            </v-menu>

          </v-col>
          <v-col order-sm="12">
            <v-menu
                bottom
                offset-y
                :close-on-content-click="false"
            >
              <template v-slot:activator="{ on, attrs}">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                    @mouseenter="loadAllKategorienAndAllergene"
                >
                  Filter
                </v-btn>
              </template>
              <v-list
                  max-width="400"
                  min-width="400"

              >
                <v-checkbox label="Suche benutzen" v-model="nameOptionActive">
                </v-checkbox>
                <v-checkbox label="Mindestbestellwert benutzen" v-model="mindestbestellwertOptionActive">
                </v-checkbox>
                <v-subheader>Mindestbestellwert</v-subheader>
                <v-list-item>
                  <v-slider
                      v-model="selectedMindestbestellwert"
                      min="5"
                      max="100"
                      step="5"
                      thumb-label
                      prepend-icon="mdi-cash"
                      append-icon="mdi-cash-multiple"
                  >
                    <template v-slot:thumb-label="{ value }">
                      {{value}} {{"€"}}
                    </template>
                  </v-slider>
                </v-list-item>
                <v-list-item>
                  <v-container fluid>
                    <v-checkbox label="Kategorien benutzen" v-model="kategorieOptionActive">
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
                        :key="kategorieVersion"
                    ></v-select>
                    <v-checkbox label="Allergene benutzen" v-model="allergeneOptionActive">
                    </v-checkbox>
                    <v-select
                        ref="AllergeneSelect"
                        v-model="selectedAllergene"
                        :items="allergene"
                        chips
                        label="Allergene"
                        multiple
                        outlined
                        block
                        :key="allergeneVersion"
                    ></v-select>
                  </v-container>
                </v-list-item>
                <v-list-item>
                  <v-btn color="error" @click="()=>{this.mindestbestellwertOptionActive=false;this.kategorieOptionActive=false;this.allergeneOptionActive=false;this.nameOptionActive=true;this.selectedMindestbestellwert=0;this.selectedKategorien=[];this.selectedAllergene=[];}">Filter löschen</v-btn>
                  <v-btn @click="applyFiltersAndSearch" color="blue">Filter anwenden</v-btn>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>
        </v-row>

      </v-container>
      <v-container>
        <v-card class="mx-auto">
          <v-card-title> Gerichte </v-card-title>
          <v-divider></v-divider>
          <v-virtual-scroll
              :items="items"
              item-height="300"
              max-height="600"
          >
            <template v-slot:default="{ item }" v-resize>
              <v-container>
                <v-list-item v-resize :key="version">
                  <v-list-item-content>
                    <v-img alt="Bild von Essen" max-height="250" max-width="250" position="center center" :src="item.img"></v-img>
                  </v-list-item-content>
                  <v-list-item-content>
                    <v-list-item-group class="text-left">
                      <v-list-item-title>{{ item.name }}</v-list-item-title>
                      <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
                      <br>
                      <v-list-item-content>{{item.restaurant}}</v-list-item-content>
                      <v-list-item-content>
                        <v-rating readonly length="5" half-icon="$ratingHalf" half-increments hover="true" dense small="true" :value="item.rating"></v-rating>
                      </v-list-item-content>
                      <v-list-item-content>Entfernung: {{item.distance}} km</v-list-item-content>
                      <v-list-item-content>Verfügbar: {{item.available}}</v-list-item-content>
                    </v-list-item-group>
                  </v-list-item-content>
                  <v-list-item-content></v-list-item-content>
                  <v-list-item-group :key="version" class="text-right">

                    <div v-if="item.isFav === true">
                      <v-tooltip bottom>
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                              @mouseenter="selectItem(item)"  small="true" right
                              @mousedown="deleteFromFavorites"
                              @mouseup="()=>{this.amountGerichte=0;version++}"
                              v-bind="attrs"
                              v-on="on"
                          >
                            <v-icon>mdi-heart</v-icon>
                          </v-btn>
                        </template>
                        <span>Aus Favoriten entfernen</span>
                      </v-tooltip>
                    </div>
                    <div v-else>
                      <v-tooltip bottom>
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                              @mouseenter="selectItem(item)"  small="true" right
                              @mousedown="addToFavorites"
                              @mouseup="()=>{this.amountGerichte=0;version++}"
                              v-bind="attrs"
                              v-on="on"
                          >
                            <v-icon>mdi-heart-outline</v-icon>
                          </v-btn>
                        </template>
                        <span>Zu Favoriten hinzufügen</span>
                      </v-tooltip>
                    </div>
                    <br>
                    <v-list-item-content>
                      Preis: {{ item.price +' €'}}
                      <br>
                      Mindestbestellwert: {{item.minimum}} €
                    </v-list-item-content>
                    <br>
                    <v-btn small="true" bottom="bottom" :to="{name: 'Gericht'}" @mouseover="selectGericht(item)">Details</v-btn>
                    <v-btn small="true" bottom="bottom" @mouseenter="selectItem(item)" @click="setStoreRestaurant_ID">Zur Speisekarte</v-btn>
                    <v-menu
                        bottom
                        offset-y
                        :close-on-content-click="false"
                    >
                      <template v-slot:activator="{ on, attrs}">
                        <v-btn
                            v-bind="attrs"
                            v-on="on"
                            small="true"
                            bottom="bottom"
                            @mouseover="selectItem(item)"
                            @click="gerichtAnzahl=0"
                        >
                          Bestellen
                        </v-btn>
                      </template>
                      <v-list
                          max-width="200"
                          min-width="250"
                          class="text-center"
                      >
                        <v-list-item>
                          <v-text-field label="Anzahl" v-model="gerichtAnzahl" type="number" :rules="countMinMaxRule"></v-text-field>
                        </v-list-item>
                        <v-btn @click="addToCart()" small="small">Zum Warenkorb hinzufügen</v-btn>
                      </v-list>
                    </v-menu>

                    <v-col cols="auto">
                      <v-dialog
                          max-width="80%"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                              v-bind="attrs"
                              v-on="on"
                              small="small"
                              @mouseenter="selectItem(item)"
                              @mousedown="loadKategorien"
                              @click="nameOptionActive=false; kategorieOptionActive=false"
                          >Alternativen</v-btn>
                        </template>
                        <template v-slot:default="dialog">
                          <v-card>
                            <v-toolbar>Alternativen</v-toolbar>
                            <v-card-text>
                              <div class="text-h2 pa-12">Inhalt</div>
                            </v-card-text>
                            <v-card-actions class="justify-end">
                              <v-checkbox label="Name" v-model="nameOptionActive">
                              </v-checkbox>
                              <v-checkbox label="Kategorien" v-model="kategorieOptionActive">
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
                                  :key="kategorieVersion"
                              ></v-select>
                              <v-btn
                                  @mousedown="findAlternatives"
                                  @click="dialog.value = false"
                              >Alternativen</v-btn>
                              <v-btn
                                  @click="dialog.value = false"
                              >Schließen</v-btn>
                            </v-card-actions>
                          </v-card>
                        </template>
                      </v-dialog>
                    </v-col>

                  </v-list-item-group>
                </v-list-item>
                <v-divider></v-divider>
              </v-container>
            </template>
          </v-virtual-scroll>
        </v-card>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Kunde",
  mounted() {
    this.searchString = this.$store.getters.searchString;
    this.searchOptions = this.$store.getters.searchOptions;

    console.log(this.searchString);

    this.getLoggedInKunde();

    this.loadGerichte();
  },
  beforeRouteLeave(to, from, next) {
    this.setStoreGericht_ID();
    this.setStoreSearchOptions();
    next();
  },
  methods: {
    async getLoggedInKunde()
    {
      const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$store.getters.getLoginData.auth.username)
      this.loggedInKunde_ID = response.data[0];
    },
    selectItem(item) {
      console.log("Gericht selected "+item.id);
      this.selectedItem = item;
    },
    selectGericht(item) {
      console.log("Gericht selected "+item.id);
      this.selectedGericht_ID = item.id;
      this.setStoreGericht_ID()
    },
    getStoreSeachString() {
      this.searchString = this.$store.getters.searchString;
    },
    getStoreSearchOptions() {
      this.searchOptions = this.$store.getters.searchOptions;
      this.searchString = this.searchOptions.gerichtName;
    },
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
    },
    setStoreSearchOptions(){
      this.$store.commit("changeSearchOptions", this.searchOptions);
    },
    setStoreGericht_ID() {
      this.$store.commit("changeGericht_ID",this.selectedGericht_ID);
      console.log("changed gericht_ID to "+this.$store.getters.gericht_ID);
    },
    setStoreRestaurant_ID() {
      this.$store.commit("changeSelectedRestaurant_ID",this.selectedItem.restaurantid);
      this.$router.push({name: "KundeAuswahlseiteRestaurant"});
    },
    async findAlternatives() {

      const searchOptions = {
        gericht_ID: this.selectedItem.id,
        kundennummer: this.loggedInKunde_ID,
        gerichtName: this.selectedItem.name,
        kategorien: this.selectedKategorien,
        excludedAllergene: [],
        maxMindestbestellwert: 0,
        maxEntfernung: 0,
        minBewertung: 0,
        useName: this.nameOptionActive,
        useKategorien: this.kategorieOptionActive,
        useAllergene: false,
        useMindestbestellwert: false,
        useEntfernung: false,
        useBewertung: false
      }

      this.searchOptions = searchOptions;

      console.log("Before sending get...")

      const responseAlternatives = await axios.post("Gericht/searchGerichte", searchOptions);

      console.log("After sending get...")

      console.log(responseAlternatives)

      for (let i = 0; i < responseAlternatives.data.length; i++) {
        let gerichtData = responseAlternatives.data[i];

        this.gericht_IDs[i] = gerichtData[0];
        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];

        if(gerichtData[4] === 0)
        {
          this.availabilities[i] = "nicht verfügbar";
        }
        else
        {
          this.availabilities[i] = "verfügbar";
        }
        this.restaurant_IDs[i] = gerichtData[5];
        this.restaurantnamen[i] = gerichtData[6];
        this.minimums[i] = gerichtData[7];
      }

      for (let i = 0; i < responseAlternatives.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_IDs[i],config);

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
      this.amountGerichte = responseAlternatives.data.length;
      this.version++;

    },
    async addToFavorites() {
      var today = new Date();
      const gerichtFavorite = {
        gericht_ID: this.selectedItem.id,
        kundennummer: this.loggedInKunde_ID,
        hinzufuegedatum: today,
        //TODO get anzahl_Bestellungen from Database
        anzahl_Bestellungen: 0
      }

      await axios.post("Favoritenliste_Gerichte", gerichtFavorite);
      this.loadGerichte();
    },
    async deleteFromFavorites(){
      await axios.delete("Favoritenliste_Gerichte/remove/"+this.loggedInKunde_ID+"/"+this.selectedItem.id);
      this.loadGerichte();
    },
    async loadGerichte() {

      this.favoritenlisteGerichte_IDs=[];
      this.hinzufuegedaten=[];

      const ResponseFavoriten = await axios.get("Gericht/getGerichtDataByKundennummer_Favoriten/"+this.loggedInKunde_ID);

      console.log(ResponseFavoriten);
      for(let i = 0; i < ResponseFavoriten.data.length; i++)
      {
        let favData = ResponseFavoriten.data[i];
        this.favoritenlisteGerichte_IDs[i] = favData[0];
        this.hinzufuegedaten[i]= favData[7];
      }

      const ResponseGerichte = await axios.post("Gericht/searchGerichte", this.searchOptions)

      console.log(ResponseGerichte);

      for (let i = 0; i < ResponseGerichte.data.length; i++) {
        let gerichtData = ResponseGerichte.data[i];

        this.gericht_IDs[i] = gerichtData[0];
        this.names[i] = gerichtData[1];
        this.descriptions[i] = gerichtData[2];
        this.prices[i] = gerichtData[3];

        if(gerichtData[4] === 0)
        {
          this.availabilities[i] = "nicht verfügbar";
        }
        else
        {
          this.availabilities[i] = "verfügbar";
        }
        this.restaurant_IDs[i] = gerichtData[5];
        this.restaurantnamen[i] = gerichtData[6];
        this.minimums[i] = gerichtData[7];

        if(this.favoritenlisteGerichte_IDs.includes(gerichtData[0]))
        {
          this.isFavorite[i] = true;
          let index = this.favoritenlisteGerichte_IDs.indexOf(gerichtData[0]);
          this.hinzufuegedatumAssigned[i] = this.hinzufuegedaten[index];
        }
        else
        {
          this.isFavorite[i] = false;
          this.hinzufuegedatumAssigned[i] = null;
        }
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_IDs[i],config);

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

      //console.log("Verarbeitung abgeschlossen")
      console.log(this.imgs);

      for(let i = 0; i<this.restaurant_IDs.length;i++)
      {
        const responseDistance = await axios.get("EntfernungKundeRestaurant/getEntfernungByKundennummerRestaurant_ID/"+this.loggedInKunde_ID+"/"+this.restaurant_IDs[i])

        console.log(responseDistance);

        this.distances[i] = responseDistance.data[0];

      }

      this.amountGerichte = 0;
      this.amountGerichte = ResponseGerichte.data.length;
      this.version++;
    },
    async loadKategorien() {
      const responseGetKategorie = await axios.get("/Gericht_Kategorie/getGericht_KategorieByGericht_ID/"+this.selectedItem.id);

      let arrayKategorien = [];
      for(let i = 0; i<responseGetKategorie.data.length;i++)
      {
        arrayKategorien[i]=responseGetKategorie.data[i];
      }
      this.kategorien = arrayKategorien;
      this.kategorieVersion++;
    },
    async loadAllKategorienAndAllergene() {
      this.loadAllKategorien();
      this.loadAllAllergene();
    },
    async loadAllKategorien() {
      const responseGetKategorie = await axios.get("Kategorie");

      let arrayKategorien = [];
      for(let i = 0; i<responseGetKategorie.data.length;i++)
      {
        arrayKategorien[i]=responseGetKategorie.data[i];
      }
      this.kategorien = arrayKategorien;
      this.kategorieVersion++;
    },
    async loadAllAllergene() {
      const responseGetAllergene = await axios.get("Allergene");

      let arrayAllergene = [];
      for(let i = 0; i<responseGetAllergene.data.length;i++)
      {
        arrayAllergene[i]=responseGetAllergene.data[i];
      }
      this.allergene = arrayAllergene;
      this.allergeneVersion++;
    },
    async applyFiltersAndSearch() {
      if(this.mindestbestellwertOptionActive===true || this.allergeneOptionActive===true)
      {
        this.nameOptionActive = true
      }

      const searchOptions = {
        gericht_ID: -1,
        kundennummer: this.loggedInKunde_ID,
        gerichtName: this.searchString,
        kategorien: this.selectedKategorien,
        excludedAllergene: this.selectedAllergene,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useKategorien: this.kategorieOptionActive,
        useAllergene: this.allergeneOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadGerichte();
    },
    async applyDistanceFilterAndSearch() {

      if(this.selectedEntfernung!==null)
      {
        this.entfernungOptionActive = true;
      }
      else
      {
        this.entfernungOptionActive = false;
        this.selectedEntfernung = 0;
      }
      this.nameOptionActive = true;

      const searchOptions = {
        gericht_ID: -1,
        kundennummer: this.loggedInKunde_ID,
        gerichtName: this.searchString,
        kategorien: this.selectedKategorien,
        excludedAllergene: this.selectedAllergene,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useKategorien: this.kategorieOptionActive,
        useAllergene: this.allergeneOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadGerichte();
    },
    async applyBewertungFilterAndSearch() {

      if(this.selectedBewertung!==null)
      {
        this.bewertungOptionActive = true;
      }
      else
      {
        this.bewertungOptionActive = false;
        this.selectedBewertung = 0;
      }
      this.nameOptionActive = true;
      console.log(this.selectedBewertung);

      const searchOptions = {
        gericht_ID: -1,
        kundennummer: this.loggedInKunde_ID,
        gerichtName: this.searchString,
        kategorien: this.selectedKategorien,
        excludedAllergene: this.selectedAllergene,
        maxMindestbestellwert: this.selectedMindestbestellwert,
        maxEntfernung: this.selectedEntfernung,
        minBewertung: this.selectedBewertung,
        useName: this.nameOptionActive,
        useKategorien: this.kategorieOptionActive,
        useAllergene: this.allergeneOptionActive,
        useMindestbestellwert: this.mindestbestellwertOptionActive,
        useEntfernung: this.entfernungOptionActive,
        useBewertung: this.bewertungOptionActive
      }

      this.searchOptions = searchOptions;

      this.loadGerichte();
    },
    addToCart() {

      console.log("Selected: "+ this.selectedItem.id+", "+this.selectedItem.name);
      let cartGericht = {
        gericht_ID: this.selectedItem.id,
        name: this.selectedItem.name,
        thumbnail: this.selectedItem.img,
        quantity: this.gerichtAnzahl,
        price: this.selectedItem.price
      }

      this.$store.commit("addToCartGerichte", cartGericht);
      console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    }
  },
  data: () => ({
    searchOptions: {},
    searchString: "",
    loggedInKunde_ID: 0,
    amountGerichte: 4,
    selectedGericht_ID: "",
    selectedItem: "",
    version: 0,
    kategorieVersion: 0,
    allergeneVersion: 0,
    gericht_IDs: [],
    names: [],
    descriptions: [],
    prices: [],
    imgs: [],
    restaurant_IDs: [],
    restaurantnamen:[],
    distances: [],
    minimums: [],
    availabilities: [],
    kategorien: [],
    selectedKategorien: [],
    allergene: [],
    selectedAllergene: [],
    selectedMindestbestellwert: 0,
    selectedBewertung: 0,
    selectedEntfernung: 0,
    favoritenlisteGerichte_IDs: [],
    hinzufuegedaten: [],
    hinzufuegedatumAssigned: [],
    isFavorite: [],
    restaurantBewertungen: [],
    nameOptionActive: true,
    kategorieOptionActive: false,
    allergeneOptionActive: false,
    mindestbestellwertOptionActive: false,
    bewertungOptionActive: false,
    entfernungOptionActive: false,
    gerichtAnzahl: 1,
    selectRating: [5,4,3,2,1],
    selectArea: [5,10,20,30,40],
    countMinMaxRule:[
      v => (v && v >= 1) || "Bestellungen müssen über 1 sein",
      v => (v && v < 50) || "Bestellungen über 50 Stück geht nicht",
    ],
  }),
  computed: {
    items(){
      let i = 0
      return Array.from({ length: this.amountGerichte}, () => {
        const cid = this.gericht_IDs[i]
        const cname = this.names[i]
        const cdescription = this.descriptions[i]
        const cprice = this.prices[i]
        const cimg = this.imgs[i]
        const crestaurantid = this.restaurant_IDs[i]
        const crestaurantname = this.restaurantnamen[i]
        const cdistance = this.distances[i]
        const cminimum = this.minimums[i]
        const cavailable = this.availabilities[i]
        const cisFav = this.isFavorite[i]
        const chinzufuegedatum = this.hinzufuegedatumAssigned[i]
        i++;

        return {
          id: cid,
          name: cname,
          description: cdescription,
          price: cprice,
          img: cimg,
          restaurantid: crestaurantid,
          restaurant: crestaurantname,
          distance: cdistance,
          minimum: cminimum,
          available: cavailable,
          isFav: cisFav,
          hinzufuegedatum: chinzufuegedatum,
        }
      })
    }
  },
  watch:{
    '$store.state.searchOptions': function() {
      this.getStoreSearchOptions();
      this.loadGerichte();
    }
  }
}
</script>

<style scoped>

</style>