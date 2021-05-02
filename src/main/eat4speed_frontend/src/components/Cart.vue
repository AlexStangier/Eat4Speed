<template>
  <v-menu offset-y>
    <template v-slot:activator="{on}">
      <v-btn v-on="on" @click="loadGerichteFromStore" icon>
        <v-icon>mdi-cart</v-icon>
        <!-- <v-badge v-if="totalItem > 0" :content="totalItem" inline>
        </v-badge>
        -->
      </v-btn>
      <v-container>
        <v-row>
          <v-text-field placeholder="Suche..." autofocus clearable
                        v-model="searchString"
          ></v-text-field>
          <v-btn @click="setStoreSearchString"
          >Suchen</v-btn>
          <v-btn @click="setDestinationToGerichte">Gericht</v-btn>
          <v-btn @click="setDestinationToRestaurants">Umgebung</v-btn>
        </v-row>
      </v-container>

    </template>
    <v-card width="400">
      <v-list>
        <v-list-item>

          <v-list-item-content>
            <v-list-item-title class="headline font-weight-bold">Cart</v-list-item-title>
          </v-list-item-content>

        </v-list-item>
      </v-list>

      <v-divider></v-divider>
      <v-card-text v-if="carts.length > 0">

        <v-list :key="version">

          <v-list-item v-for="(item, idx) in carts" :key="idx">
            <v-list-item-avatar>
              <img :src="item.thumbnail">
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ item.quantity }} item{{ item.quantity > 1 ? 's' : '' }}</v-list-item-subtitle>
              <v-list-item-subtitle hidden>{{ item.gericht_ID }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-action>
              <v-btn icon @mouseover="selectGericht(item)" @click="deleteGerichtFromStore" small>
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>


        </v-list>

        <v-card-actions>
          <v-btn block color="primary" rounded>
            Checkout
          </v-btn>
        </v-card-actions>
      </v-card-text>

      <v-card-text v-else>
        <p>No item</p>
      </v-card-text>

    </v-card>

  </v-menu>

</template>

<script>
export default {
  name: "Cart",
  mounted() {
    this.loadGerichteFromStore();
  },
  beforeRouteLeave(to, from, next) {
    console.log("leave");
    this.setStoreSearchString();
    next();
  },
  methods: {
    setStoreSearchString() {
      this.$store.commit("changeSearchString",this.searchString);
      console.log("changed searchString to "+this.$store.getters.searchString);
      if(this.searchDestination === "Gerichte")
      {
        this.$store.commit("changeSearchType", "Gerichte");
        this.$router.push({ name: 'Kunde'});
      }
      else
      {
        this.$store.commit("changeSearchType", "Restaurants")
        this.$router.push({ path: '/kundeRestaurants' });
      }
    },
    setDestinationToGerichte() {
      this.searchDestination = "Gerichte";
    },
    setDestinationToRestaurants() {
      this.searchDestination = "Restaurants";
    },
    selectGericht(item) {
      this.selectedGericht = item;
    },
    loadGerichteFromStore() {
      this.carts = this.$store.getters.getCartGerichte;
      console.log(this.carts);
      this.version++;
    },
    deleteGerichtFromStore() {
      console.log(this.selectedGericht.name);
      this.$store.commit("removeFromCartGerichte",this.selectedGericht);
      this.loadGerichteFromStore();
      this.version++;
    }
  },
  data() {
    return {
      carts: [],
      version:0,
      selectedGericht:"",
      searchString:"",
      searchDestination:""
    };
  },
}
</script>