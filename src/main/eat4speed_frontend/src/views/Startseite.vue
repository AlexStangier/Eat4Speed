<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout justify-center>
        <v-flex md6 sm6 xs12>
          <v-img
              class="justify-center"
              lazy-src="https://i.imgur.com/0lc6ENr.png"
              src="https://i.imgur.com/0lc6ENr.png"
          ></v-img>
          <v-row>
            <v-col>
              <v-text-field label="Suche nach Gericht"
                            ref="Suchfeld"
                            required
                            v-model="searchString"
                            append-icon="mdi-magnify"
                            single-line
                            hide-details
              >
              </v-text-field>
            </v-col>
            <v-btn :disabled="!valid" color="red" dark rounded elevation="15" align="bottom"
                   @click="setStoreSearchString">Los
            </v-btn>
          </v-row>
          <v-row
              style="height: 200px;"
          >
            <v-col>
              <v-btn :disabled="!valid" ref="GerichtButton" color="blue" dark rounded elevation="15" width="200"
                     @click="gerichtFarbe" @mousedown="setDestinationToGerichte">Gericht
              </v-btn>
              <v-btn :disabled="!valid" ref="UmgebungButton" color="red" dark rounded elevation="15" width="200"
                     @click="umbegungFarbe" @mousedown="setDestinationToRestaurants">Umgebung
              </v-btn>
            </v-col>
          </v-row>
          <!--Anmelde Dialog-->
          <v-dialog
              v-model="dialog"
              width="500"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="red"
                  dark
                  v-bind="attrs"
                  v-on="on"
              >
                Anmelden
              </v-btn>
            </template>
            <v-card class="pa-10">
              <v-card-title class="d-flex justify-center">
                Anmeldung
              </v-card-title>
              <v-spacer class="pb-10"></v-spacer>
              <v-btn :to="{ name: 'KundeAnmeldung'}" :disabled="!valid" ref="GerichtButton" color="blue" dark rounded
                     elevation="15" width="200"
                     @click="gerichtFarbe">Kunde
              </v-btn>
              <v-spacer class="pb-10"></v-spacer>
              <v-btn :to="{ name: 'RestaurantAnmeldung'}" :disabled="!valid" ref="GerichtButton" color="blue" dark
                     rounded elevation="15" width="200"
                     @click="gerichtFarbe">Restaurant
              </v-btn>
              <v-spacer class="pb-10"></v-spacer>
              <v-btn :to="{ name: 'FahrerAnmeldung'}" :disabled="!valid" ref="GerichtButton" color="blue" dark rounded
                     elevation="15" width="200"
                     @click="gerichtFarbe">Fahrer
              </v-btn>
            </v-card>
          </v-dialog>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: 'Startseite',
  mounted() {
    this.$store.commit("changeCartGerichte",[]);
  },
  methods: {
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
      if(this.searchDestination === "Gerichte")
      {
        this.$store.commit("changeSearchType","Gerichte");
        this.$router.push({ name: 'Kunde'});
      }
      else
      {
        this.$store.commit("changeSearchType","Restaurants");
        console.log("To Restaurants");
        this.$router.push({path: '/kundeRestaurants'});
      }
    },
    setDestinationToGerichte() {
      this.searchDestination = "Gerichte";
    },
    setDestinationToRestaurants() {
      console.log("Changed Destination to Restaurants");
      this.searchDestination = "Restaurants";
    },
    openLogin() {
      this.$refs.Anmeldung.class = "px-4 d-flex"
    },
    gerichtFarbe() {
      this.$refs.GerichtButton.color = "blue"
      this.$refs.UmgebungButton.color = "red"
      this.$refs.Suchfeld.label = "Suche nach Gericht"
    },
    umbegungFarbe() {
      this.$refs.UmgebungButton.color = "blue"
      this.$refs.GerichtButton.color = "red"
      this.$refs.Suchfeld.label = "Suche nach Umgebung"
    },
    validate() {
      if (this.$refs.loginForm.validate()) {
        // submit form to server/API here...
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data() {
    return {

      valid: true,
      searchString: "",
      salutation: "",
      firstName: "",
      lastName: "",
      street: "",
      houseNumber: "",
      place: "",
      postCode: "",
      email: "",
      phoneNumber: "",
      password: "",
      verify: "",
      loginPassword: "",
      loginEmail: "",
      searchDestination: "",
      loginEmailRules: [
        v => !!v || "Required",
        v => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein"
      ],
      emailRules: [
        v => !!v || "Required",
        v => /.+@.+\..+/.test(v) || "E-Mail muss gültig sein"
      ],
      show1: false,
      rules: {
        required: value => !!value || "Required.",
        min: v => (v && v.length >= 8) || "Mindestens 8 Zeichen"
      }
    }
  }
};
</script>