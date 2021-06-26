<template>
  <v-main>
    <v-container>
      <v-container fluid>
        <v-row no-gutters class="align-center">
          <v-col
              v-for="k in 4"
              :key="k"
              cols="3"
          >
            <v-card
                v-if="k === 1"
                flat
            >
              <v-select
                  v-model="selectedBewertung"
                  label="Bewertung"
                  :items="selectRating"
                  clearable
                  @change="applyBewertungFilterAndSearch"
              >
                <template v-slot:selection="data">
                  {{data.item}} {{"Sterne"}}
                </template>
                <template v-slot:item="data">
                  {{data.item}} {{"Sterne"}}
                </template>
              </v-select>
            </v-card>
            <v-card
                v-if="k === 2"
                flat
            >
              <v-select
                  class="ml-10"
                  v-model="selectedEntfernung"
                  label="Entfernung"
                  :items="selectArea"
                  :disabled="!isUserLoggedInBoolean"
                  clearable
                  @change="applyDistanceFilterAndSearch"
              >
                <template v-slot:selection="data">
                  {{data.item}} {{"km"}}
                </template>
                <template v-slot:item="data">
                  {{data.item}} {{"km"}}
                </template>
              </v-select>
            </v-card>
            <v-card
                v-if="k === 4"
                flat
                class="text-right"
            >
              <v-menu
                  bottom
                  left
                  offset-y
                  tile
                  :close-on-content-click="false"
                  max-width="400"
                  min-width="400"
                  max-height="400"
              >
                <template v-slot:activator="{ on, attrs}">
                  <v-btn
                      v-bind="attrs"
                      v-on="on"
                      @mouseenter="loadAllKategorienAndAllergene"
                      color="primary"
                      tile
                  >
                    Filter
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <h2
                        class="text-uppercase"
                    >
                      Filter
                    </h2>
                  </v-list-item>
                  <v-list-item>
                    <v-container fluid>
                      <v-checkbox
                          label="Suchbegriff benutzen"
                          v-model="nameOptionActive"
                      ></v-checkbox>
                    </v-container>
                  </v-list-item>
                  <v-subheader
                      class="text-uppercase"
                  >
                    Mindestbestellwert
                  </v-subheader>
                  <v-list-item>
                    <v-container fluid>
                      <v-checkbox
                          label="Mindestbestellwert benutzen"
                          v-model="mindestbestellwertOptionActive"
                      ></v-checkbox>
                      <v-slider
                          v-model="selectedMindestbestellwert"
                          min="5"
                          max="100"
                          step="5"
                          thumb-label
                          prepend-icon="mdi-cash"
                          append-icon="mdi-cash-multiple"
                          v-if="mindestbestellwertOptionActive"
                      >
                        <template v-slot:thumb-label="{ value }">
                          {{value}} {{"€"}}
                        </template>
                      </v-slider>
                    </v-container>
                  </v-list-item>
                  <v-subheader
                      class="text-uppercase"
                  >
                    Kategorien
                  </v-subheader>
                  <v-list-item>
                    <v-container fluid>
                      <v-checkbox
                          label="Kategorien benutzen"
                          v-model="kategorieOptionActive"
                      ></v-checkbox>
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
                          v-if="kategorieOptionActive"
                      >
                        <template v-slot:selection="{ item, index}">
                          <v-chip
                              v-if="index < 3"
                          >
                            <span>{{ item }}</span>
                          </v-chip>
                          <v-chip
                              v-if="index === 3"
                          >
                            (+ {{ selectedKategorien.length - 3 }} andere)
                          </v-chip>
                        </template>
                      </v-select>
                    </v-container>
                  </v-list-item>
                  <v-subheader
                      class="text-uppercase"
                  >
                    Allergene
                  </v-subheader>
                  <v-list-item>
                    <v-container fluid>
                      <v-checkbox
                          label="Allergene benutzen"
                          v-model="allergeneOptionActive"
                      ></v-checkbox>
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
                          v-if="allergeneOptionActive"
                      >
                        <template v-slot:selection="{ item, index}">
                          <v-chip
                              v-if="index < 3"
                          >
                            <span>{{ item }}</span>
                          </v-chip>
                          <v-chip
                              v-if="index === 3"
                          >
                            (+ {{ selectedAllergene.length - 3 }} andere)
                          </v-chip>
                        </template>
                      </v-select>
                    </v-container>
                  </v-list-item>
                  <v-container>
                    <v-row>
                      <v-col
                          class="justify-start"
                      >
                        <v-btn
                            color="error"
                            tile
                            @click="()=>{this.mindestbestellwertOptionActive=false;this.kategorieOptionActive=false;this.allergeneOptionActive=false;this.nameOptionActive=true;this.selectedMindestbestellwert=0;this.selectedKategorien=[];this.selectedAllergene=[];}"
                        >
                          Filter löschen
                        </v-btn>
                      </v-col>
                      <v-col
                          class="justify-end"
                      >
                        <v-btn
                            color="primary"
                            tile
                            @click="applyFiltersAndSearch"
                        >
                          Filter anwenden
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-list>
              </v-menu>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
      <v-container fluid>
        <v-card class="mx-auto">
          <v-card-title> Gerichte </v-card-title>
          <v-divider></v-divider>
          <v-virtual-scroll
              :items="items"
              :item-height="270"
              max-height="650"
              :key="gerichteKey"
          >
            <template v-slot:default="{ item }" v-resize>

              <v-card
                  flat
                  tile
              >
                <v-container fluid>
                  <v-row>
                    <v-col
                        cols="3"
                    >
                      <v-card
                          flat
                          tile
                          outlined
                      >
                        <v-img alt="Bild von Essen" min-height="230" max-height="230" max-width="400" position="center center" :src="item.img"></v-img>
                      </v-card>
                    </v-col>
                    <v-col>
                      <v-row
                          v-for="a in 3"
                          :key="a"
                      >
                        <v-col
                            cols="5"
                        >
                          <v-card
                              v-if="a === 1"
                              flat
                              class="text-h5 text-decoration-underline"
                          >
                            {{ item.name }}
                          </v-card>
                          <v-card
                              ref="cardDescription"
                              v-if="a === 1"
                              flat
                              class="subtitle-1"
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
                          <v-card
                              v-if="a === 2"
                              flat
                              class="subtitle-1"
                          >
                            {{item.restaurant}}
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="text-left"
                          >
                            <v-rating readonly length="5" half-icon="$ratingHalf" half-increments dense small :value="item.rating"></v-rating>
                          </v-card>
                          <v-card
                              v-if="a === 2 && isUserLoggedInBoolean"
                              flat
                              class="subtitle-1"
                          >
                            Entfernung: {{item.distance+' km'}}
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="subtitle-1"
                          >
                            <span>
                              Verfügbar
                              <v-icon v-if="item.available == 'verfügbar'" color="green">
                                mdi-check
                              </v-icon>
                              <v-icon v-if="item.available != 'verfügbar'" color="red">
                                mdi-close
                              </v-icon>
                            </span>
                          </v-card>
                        </v-col>
                        <v-col>
                          <v-card
                              v-if="a === 1"
                              flat
                              :key="version"
                              class="text-right"
                          >
                            <div v-if="item.isFav === true">
                              <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn
                                      @mouseenter="selectItem(item)"
                                      small
                                      right
                                      @mousedown="deleteFromFavorites"
                                      @mouseup="()=>{this.amountGerichte=0;version++}"
                                      v-bind="attrs"
                                      v-on="on"
                                      icon
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
                                      @mouseenter="selectItem(item)"  small right
                                      @mousedown="addToFavorites"
                                      @mouseup="()=>{this.amountGerichte=0;version++}"
                                      v-bind="attrs"
                                      v-on="on"
                                      icon
                                  >
                                    <v-icon>mdi-heart-outline</v-icon>
                                  </v-btn>
                                </template>
                                <span>Zu Favoriten hinzufügen</span>
                              </v-tooltip>
                            </div>
                          </v-card>

                          <v-card
                              v-if="a === 2"
                              flat
                              class="subtitle-1 text-right"
                          >
                            Preis: {{ item.price +' €'}}
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="subtitle-1 text-right"
                          >
                            Mindestbestellwert: {{item.minimum +' €'}}
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="text-right"
                          >
                            <v-dialog
                                max-width="50%"
                            >
                              <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                    v-bind="attrs"
                                    v-on="on"
                                    small
                                    color="primary"
                                    tile
                                    @mouseenter="fillAllergene(item)"
                                >
                                  Allergene
                                </v-btn>
                              </template>
                              <template v-slot:default="dialog">
                                <v-card>
                                  <v-container>
                                    <v-row
                                        class="pa-2"
                                    >
                                      <v-select
                                          readonly
                                          disabled
                                          :items="allergeneGericht"
                                          v-model="allergeneGericht"
                                          chips
                                          label="Allergene"
                                          multiple
                                          :key="allergeneKey"
                                      >

                                      </v-select>
                                    </v-row>
                                    <v-row
                                        class="pa-2"
                                        justify="end"
                                    >
                                      <v-btn
                                          class="ml-1 justify-end"
                                          @click="dialog.value = false"
                                          color="error"
                                          tile
                                      >
                                        Schließen
                                      </v-btn>
                                    </v-row>
                                  </v-container>
                                </v-card>
                              </template>
                            </v-dialog>
                          </v-card>
                          <v-card
                              v-if="a === 2"
                              flat
                              class="text-right pt-2"
                          >
                              <v-btn
                                  small
                                  color="primary"
                                  tile
                                  class="ml-1"
                                  :to="{name: 'Gericht'}"
                                  @mouseover="selectGericht(item)"
                              >
                                Details
                              </v-btn>
                              <v-btn
                                  small
                                  class="ml-1"
                                  color="primary"
                                  tile
                                  @mouseenter="selectItem(item)"
                                  @click="setStoreRestaurant_ID"
                              >
                                Zur Speisekarte
                              </v-btn>
                            <v-dialog
                                max-width="50%"
                            >
                              <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                    v-bind="attrs"
                                    v-on="on"
                                    small
                                    class="ml-1"
                                    color="primary"
                                    tile
                                    @mouseenter="selectItem(item)"
                                    @mousedown="loadKategorien"
                                    @click="nameOptionActive=false; kategorieOptionActive=false"
                                >
                                  Alternativen
                                </v-btn>
                              </template>
                              <template v-slot:default="dialog">
                                <v-card>
                                  <v-toolbar>Alternativen</v-toolbar>
                                  <v-container>
                                    <v-row no-gutters>
                                      <v-checkbox
                                          label="Name"
                                          v-model="nameOptionActive"
                                          :disabled="kategorieOptionActive"
                                      >
                                      </v-checkbox>
                                      <v-checkbox
                                          class="ml-2"
                                          label="Kategorien"
                                          v-model="kategorieOptionActive"
                                          :disabled="nameOptionActive"
                                      >
                                      </v-checkbox>
                                    </v-row>
                                    <v-row>
                                      <v-container>
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
                                            v-if="kategorieOptionActive"
                                        >
                                        </v-select>
                                      </v-container>
                                    </v-row>
                                    <v-row no-gutters class="justify-end">
                                      <v-btn
                                          class="ml-1"
                                          @mousedown="findAlternatives"
                                          @click="dialog.value = false"
                                          color="primary"
                                          tile
                                          :disabled="(!kategorieOptionActive) && (!nameOptionActive)"
                                      >
                                        Suchen
                                      </v-btn>
                                      <v-btn
                                          class="ml-1"
                                          @click="dialog.value = false"
                                          color="error"
                                          tile
                                      >
                                        Schließen
                                      </v-btn>
                                    </v-row>
                                  </v-container>
                                </v-card>
                              </template>
                            </v-dialog>
                            <v-menu
                                bottom
                                left
                                offset-y
                                :close-on-content-click="false"
                            >
                              <template v-slot:activator="{ on, attrs}">
                                <v-btn
                                    v-bind="attrs"
                                    v-on="on"
                                    small
                                    class="ml-1"
                                    color="primary"
                                    :disabled="item.available !== 'verfügbar'"
                                    tile
                                    @mouseover="selectItem(item)"
                                    @click="gerichtAnzahl=1"
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
                                <v-btn
                                    @click="addToCart()"
                                    small
                                    color="primary"
                                    tile
                                    :disabled="gerichtAnzahl < 1 || gerichtAnzahl > 50 || item.available !== 'verfügbar'"
                                >
                                  Zum Warenkorb hinzufügen
                                </v-btn>
                              </v-list>
                            </v-menu>
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
          <v-card
              v-if="amountGerichte === -1"
              tile
          >
            <v-row justify="center">
              <v-progress-circular
                  indeterminate
                  color="primary"
                  :size="70"
                  :width="7"
              ></v-progress-circular>
            </v-row>
          </v-card>
          <v-card
              v-if="amountGerichte === 0"
              tile
              class="text-center text-h5"
          >
            Es wurden keine Gerichte gefunden
          </v-card>
        </v-card>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  name: "Kunde",
  async mounted() {
    this.searchString = this.$store.getters.searchString;
    this.searchOptions = this.$store.getters.searchOptions;

    //console.log(this.searchString);
    await this.checkLoggedInUser();
    await this.getLoggedInKunde();
    await this.getAllEntfernungenAndBewertungen();
    this.loadGerichte();
  },
  beforeRouteLeave(to, from, next) {
    this.setStoreGericht_ID();
    this.setStoreSearchOptions();
    next();
  },
  methods: {
    async checkLoggedInUser() {
      if (this.$cookies.get('emailAdresse') !== undefined) {
        this.isUserLoggedInBoolean = true;
      }
    },
    async getLoggedInKunde()
    {
      if(this.isUserLoggedInBoolean)
      {
        const response = await axios.get("Benutzer/getKundennummerByBenutzername/"+this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData)
        this.loggedInKunde_ID = response.data[0];
      }

    },
    async getAllEntfernungenAndBewertungen()
    {
      if(this.isUserLoggedInBoolean)
      {
        const responseEntfernungen = await axios.get("EntfernungKundeRestaurant/getEntfernungByKundennummer/"+this.loggedInKunde_ID, this.$store.getters.getLoginData);
        for(let i = 0; i<responseEntfernungen.data.length; i++)
        {
          this.distanceRestaurant_IDs[i] = responseEntfernungen.data[i][0];
          this.distancesUnassigned[i] = responseEntfernungen.data[i][1];
        }
      }
      const ResponseBewertungen = await axios.get("Bewertung/getAverageBewertungAndCountBewertungAllRestaurants", this.$store.getters.getLoginData);
      for(let i = 0; i<ResponseBewertungen.data.length;i++)
      {
        this.bewertungAvgUnassigned[i] = ResponseBewertungen.data[i][0];
        this.bewertungRestaurants[i] = ResponseBewertungen.data[i][2];
      }
    },
    selectItem(item) {
      //console.log("Gericht selected "+item.id);
      this.selectedItem = item;
    },
    selectGericht(item) {
      //console.log("Gericht selected "+item.id);
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
      //console.log("changed searchString to "+this.$store.getters.searchString);
    },
    setStoreSearchOptions(){
      this.$store.commit("changeSearchOptions", this.searchOptions);
    },
    setStoreGericht_ID() {
      this.$store.commit("changeGericht_ID",this.selectedGericht_ID);
      //console.log("changed gericht_ID to "+this.$store.getters.gericht_ID);
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

      const responseAlternatives = await axios.post("Gericht/searchGerichte", searchOptions, this.$store.getters.getLoginData);

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

        //console.log(responsePicture);

        if(responsePicture.status !== 204)
        {
          //console.log("received Picture")
          //console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          //console.log(imageURL);

          this.imgs[i] = imageURL;
        }
        else
        {
          this.imgs[i] = "";
        }

      }
      //console.log(this.imgs);
      this.amountGerichte = 0;
      this.amountGerichte = responseAlternatives.data.length;
      this.version++;
      this.gerichteKey++;

    },
    async addToFavorites() {
      if(!this.isUserLoggedInBoolean)
      {
        alert("Sie müssen sich einloggen, um Favoriten hinzufügen zu können!")
        return;
      }

      if(this.selectedItem.bestellradius<this.selectedItem.distance)
      {
        alert("Sie befinden sich außerhalb des Bestellradius")
        return;
      }
      var today = new Date();
      const gerichtFavorite = {
        gericht_ID: this.selectedItem.id,
        kundennummer: this.loggedInKunde_ID,
        hinzufuegedatum: today,
        anzahl_Bestellungen: 0
      }

      await axios.post("Favoritenliste_Gerichte", gerichtFavorite, this.$store.getters.getLoginData);
      this.loadGerichte();
    },
    async deleteFromFavorites(){
      await axios.delete("Favoritenliste_Gerichte/remove/"+this.loggedInKunde_ID+"/"+this.selectedItem.id, this.$store.getters.getLoginData);
      this.loadGerichte();
    },
    async loadGerichte() {

      this.gericht_IDs = [];
      this.favoritenlisteGerichte_IDs=[];
      this.hinzufuegedaten=[];
      if(this.isUserLoggedInBoolean)
      {
        const ResponseFavoriten = await axios.get("Gericht/getGerichtDataByKundennummer_Favoriten/"+this.loggedInKunde_ID, this.$store.getters.getLoginData);
        //console.log(ResponseFavoriten);
        for(let i = 0; i < ResponseFavoriten.data.length; i++)
        {
          let favData = ResponseFavoriten.data[i];
          this.favoritenlisteGerichte_IDs[i] = favData[0];
          this.hinzufuegedaten[i]= favData[7];
        }
      }

      const ResponseGerichte = await axios.post("Gericht/searchGerichte", this.searchOptions, this.$store.getters.getLoginData)

      //console.log(ResponseGerichte);

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
        this.bestellradius[i] = gerichtData[8];

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
        //console.log(this.bewertungRestaurants);

        if(this.bewertungRestaurants.includes(gerichtData[5])===true)
        {
          let index = this.bewertungRestaurants.indexOf(gerichtData[5]);
          this.restaurantBewertungen[i] = this.bewertungAvgUnassigned[index];
        }
        else
        {
          this.restaurantBewertungen[i] = 0;
        }

        let index = this.distanceRestaurant_IDs.indexOf(gerichtData[5]);
        this.distances[i] = this.distancesUnassigned[index];
      }

      for (let i = 0; i < ResponseGerichte.data.length; i++)
      {
        const config = { responseType:"arraybuffer" };
        const responsePicture = await axios.get("/GerichtBilder/getBild/"+this.gericht_IDs[i],config);

        //console.log(responsePicture);

        if(responsePicture.status !== 204)
        {
          //console.log("received Picture")
          //console.log(responsePicture.data);

          let pictureBlob = new Blob([responsePicture.data], { type : responsePicture.headers["content-type"]})

          let imageURL = URL.createObjectURL(pictureBlob);
          //console.log(imageURL);

          this.imgs[i] = imageURL;
        }
        else
        {
          this.imgs[i] = "";
        }

      }

      //console.log("Verarbeitung abgeschlossen")
      //console.log(this.imgs);

      this.amountGerichte = 0;
      this.amountGerichte = this.gericht_IDs.length;
      this.version++;
      this.gerichteKey++;
    },
    async loadKategorien() {
      const responseGetKategorie = await axios.get("/Gericht_Kategorie/getGericht_KategorieByGericht_ID/"+this.selectedItem.id, this.$store.getters.getLoginData);

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
      const responseGetKategorie = await axios.get("Kategorie", this.$store.getters.getLoginData);

      let arrayKategorien = [];
      for(let i = 0; i<responseGetKategorie.data.length;i++)
      {
        arrayKategorien[i]=responseGetKategorie.data[i];
      }
      this.kategorien = arrayKategorien;
      this.kategorieVersion++;
    },
    async loadAllAllergene() {
      const responseGetAllergene = await axios.get("Allergene", this.$store.getters.getLoginData);

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
    async fillAllergene(item)
    {
      this.selectedItem = item;
      this.allergeneGericht = [];
      const responseAllergene = await axios.get("Gericht_Allergene/getGericht_AllergeneByGericht_ID/"+this.selectedItem.id, this.$store.getters.getLoginData);
      for(let i = 0; i<responseAllergene.data.length; i++)
      {
        this.allergeneGericht[i] = responseAllergene.data[i];
      }
      this.allergeneKey++;
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
      //console.log(this.selectedBewertung);

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

      if(this.isUserLoggedInBoolean)
      {
        if(this.selectedItem.bestellradius<this.selectedItem.distance)
        {
          alert("Sie befinden sich außerhalb des Bestellradius")
          return;
        }
      }
      //console.log("Selected: "+ this.selectedItem.id+", "+this.selectedItem.name);
      let cartGericht = {
        gericht_ID: this.selectedItem.id,
        name: this.selectedItem.name,
        thumbnail: this.selectedItem.img,
        quantity: this.gerichtAnzahl,
        price: this.selectedItem.price,
        restaurant_ID: this.selectedItem.restaurantid,
        num: Math.random() * (999999 - 1) + 1
      }
      this.$store.commit("addToCartGerichte", cartGericht);
      //console.log("Current Cart: "+this.$store.getters.getCartGerichte[0]);
    }
  },
  data: () => ({
    searchOptions: {},
    searchString: "",
    loggedInKunde_ID: 0,
    amountGerichte: -1,
    selectedGericht_ID: "",
    isUserLoggedInBoolean: false,
    selectedItem: "",
    bewertungAvgUnassigned: [],
    bewertungRestaurants: [],
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
    bestellradius: [],
    distances: [],
    distancesUnassigned: [],
    distanceRestaurant_IDs: [],
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
      v => (v && v <= 50) || "Bestellungen über 50 Stück geht nicht",
    ],
    allergeneGericht: [],
    allergeneKey: 0,
    gerichteKey: 0,
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
        const cbestellradius = this.bestellradius[i]
        const cbewertung = this.restaurantBewertungen[i]
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
          bestellradius: cbestellradius,
          rating: cbewertung
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