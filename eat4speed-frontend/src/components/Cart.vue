<template>
  <v-menu offset-y>
    <template v-slot:activator="{on}">
      <v-btn class="ml-5" v-on="on" @click="loadGerichteFromStore" icon>
        <v-icon>mdi-cart</v-icon>
        <!-- <v-badge v-if="totalItem > 0" :content="totalItem" inline>
        </v-badge>
        -->
      </v-btn>
    </template>
    <v-card width="400">
      <v-list>
        <v-list-item>

          <v-list-item-content>
            <v-list-item-title class="headline font-weight-bold">Warenkorb</v-list-item-title>
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
              <v-list-item-subtitle>Anzahl: {{ item.quantity }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{ calculateItemPrice(item.quantity, item.price) }} &euro;</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-action>
              <v-btn icon @mouseover="selectGericht(item)" @click="deleteGerichtFromStore" small>
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>


        </v-list>

        <h3>Steuer: {{ parseFloat(calculateCartTax()).toFixed(2) }} &euro; (7% Mwst.)</h3>
        <h3 class="pb-1">Lieferkosten: 2 &euro;</h3>
        <v-divider></v-divider>
        <h2 class="pt-2">Endpreis: {{ parseFloat(calculateCartPrice()).toFixed(2) }} &euro;</h2>

        <v-card-actions>
          <v-dialog
              :disabled="!isLoggedIn"
              width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  v-bind="attrs"
                  v-on="on"
                  color="primary"
                  depressed
                  tile
                  block
              >
                Zeitraum wählen
              </v-btn>
            </template>
            <template v-slot:default="dialog">
              <v-card>
                <v-card-title>
                  Zeitraum wählen
                </v-card-title>
                <v-card-text>
                  <span>Sie können hier Ihren Wunschtermin wählen</span>
                  <v-checkbox label="Liefertermin wählen" v-model="manLiefertermin" ></v-checkbox>
                  <v-container fluid>
                    <v-dialog
                        v-if="manLiefertermin"
                        ref="dialogDate"
                        v-model="dateDialog"
                        :return-value.sync="date"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on,attrs }">
                        <v-text-field
                            v-model="dateFormatted"
                            label="Liefertermin"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        >
                        </v-text-field>
                      </template>
                      <v-date-picker
                          v-model="date"
                          :first-day-of-week="1"
                          locale="DE-de"
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="error"
                            @click="dateDialog = false"
                        >
                          Abbrechen
                        </v-btn>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialogDate.save(date)"
                        >
                          OK
                        </v-btn>
                      </v-date-picker>
                    </v-dialog>

                    <v-dialog
                        v-if="manLiefertermin"
                        ref="dialogTime"
                        v-model="timeDialog"
                        :return-value.sync="time"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="time"
                            label="Lieferzeit"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-model="time"
                          full-width
                          format="24hr"
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="error"
                            @click="timeDialog = false"
                        >
                          Abbrechen
                        </v-btn>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialogTime.save(time)"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-btn block color="primary" depressed tile :disabled="!isLoggedIn" @click="paypalRequest(), dialog.value = false">
                    Checkout
                  </v-btn>
                </v-card-actions>
              </v-card>
            </template>
          </v-dialog>
        </v-card-actions>
      </v-card-text>

      <v-card-text v-else>
        <p>Keine Artikel</p>
      </v-card-text>

    </v-card>

  </v-menu>

</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "Cart",
  props: {
    isLoggedIn: {
      type: Boolean
    }
  },
  mounted() {
    this.loadGerichteFromStore();
  },
  computed: {
    isUserLoggedIn() {
      return this.$cookies.get('emailAdresse') !== undefined;
    },
    computedDateFormatted() {
      return this.formatDate(this.date)
    }
  },
  watch: {
    date() {
      this.dateFormatted = this.formatDate(this.date)
    }
  },
  methods: {
    roundToTwo(num) {
      return (Math.round(num + "e+2")  + "e-2");
    },
    selectGericht(item) {
      this.selectedGericht = item;
    },
    loadGerichteFromStore() {
      this.carts = this.$store.getters.getCartGerichte;
      this.version++;
    },
    deleteGerichtFromStore() {
      this.$store.commit("removeFromCartGerichte", this.selectedGericht);
      this.loadGerichteFromStore();
      this.version++;
    },
    calculateItemPrice(price, amount) {
      return price * amount;
    },
    calculateCartPrice() {
      let cartPrice = 0;
      this.carts.forEach(value => {
        cartPrice = cartPrice + this.calculateItemPrice(value.quantity, value.price);
      });
      return this.roundToTwo((cartPrice * 1.07) + 2);
    },
    calculateCartTax() {
      let cartPrice = 0;
      this.carts.forEach(value => {
        cartPrice = cartPrice + this.calculateItemPrice(value.quantity, value.price);
      });
      return this.roundToTwo(cartPrice * 0.07);
    },
    async getCustomerId() {
      let id;
      await this.$http.post('/Benutzer/getIdByEmail', {
        email: this.$cookies.get('emailAdresse')
      }, this.$store.getters.getLoginData).then((response) => {
        if (response.status === 200) {
          id = response.data;
        }
      }, () => {
        this.$router.push({name: "KundeAnmeldung"});
      });

      return id;
    },
    setTimespamp()
    {
      if(this.demandIsNow === true)
      {
        this.timestampCustomerDemand = moment().format("HH:mm");
      }


    },
    async checkOeffnungszeiten()
    {
      //let demandDay = this.timestampCustomerDemand.getDay();
      this.problemGerichte = [];
      const format = "HH:mm";



      if(this.manLiefertermin === false)
      {
        this.timestampCustomerDemand = moment().format("HH:mm");
        //console.log(this.timestampCustomerDemand);
        this.timestampCustomerDemand = moment(this.timestampCustomerDemand,format);
        //console.log(this.timestampCustomerDemand);
        this.timestampCustomerDemandDatabase = this.timestampCustomerDemand;

        let dateTimespamp = this.timestampCustomerDemand.toDate();
        //console.log(dateTimespamp.getDay());
        this.dayOfWeek = dateTimespamp.getDay();
      }
      else
      {
        let now = moment().format("HH:mm");
        //console.log(this.timestampCustomerDemand);
        let nowFormatted = moment(now,format);

        //console.log(this.date);
        //console.log(moment(this.date,"YYYY/MM/DD"));
        let demandMoment = moment(this.date,"YYYY/MM/DD");
        // console.log(demandMoment);
        let demandMomentDate = demandMoment.toDate();
        this.dayOfWeek = demandMomentDate.getDay();
        //console.log(this.dayOfWeek);

        // console.log(this.time);

        let demandMomentTime = moment(this.time,format);
        // console.log(demandMomentTime);
        demandMomentDate.setHours(demandMomentTime.hours());
        demandMomentDate.setMinutes(demandMomentTime.minutes());
        // console.log(demandMomentDate);

        let trueDate = moment(demandMomentDate);
        // console.log(trueDate);

        if(trueDate.isBefore(nowFormatted))
        {
          alert("Datum/ Uhrzeit ungültig");
          this.oeffnungszeitenOkay = false;
          return;
        }

        this.timestampCustomerDemand = demandMomentTime;
        this.timestampCustomerDemandDatabase = trueDate;
      }

      let demandDay = this.dayOfWeek;

      let demandTime = this.timestampCustomerDemand;

      var cartItems = this.$store.getters.getCartGerichte;
      for(let i = 0; i<cartItems.length; i++)
      {
        let resOk = false;
        let responseOeffnungszeiten = await axios.get("Oeffnungszeiten/getAllZeitenWochentag/"+cartItems[i].restaurant_ID+"/"+demandDay, this.$store.getters.getLoginData);
        for(let e = 0; e< responseOeffnungszeiten.data.length; e++)
        {
          let anfang = responseOeffnungszeiten.data[e][0];
          let ende = responseOeffnungszeiten.data[e][1];
          let anfangTime = moment(anfang.substring(0, 19)+"+00:00").format("HH:mm");
          let anfangTimeTwo = moment(anfangTime,format);
          let endeTime = moment(ende.substring(0, 19)+"+00:00").format("HH:mm");
          let endeTimeTwo = moment(endeTime,format);

          if(demandTime.isBetween(anfangTimeTwo,endeTimeTwo))
          {
            resOk = true;
            // console.log("ok "+e);
          }
        }
        if(resOk===false)
        {
          this.problemGerichte.push(cartItems[i].name);
          this.oeffnungszeitenOkay = false;
        }
      }

      if(this.problemGerichte.length===0)
      {
        this.oeffnungszeitenOkay = true;
        // console.log("test");
      }
      else {
        let alertString = "Die folgenden Gerichte können zu der ausgewählten Öffnungszeit nicht bestellt werden: ";

        let alertStingGerichte = "";
        for(let i = 0; i<this.problemGerichte.length; i++)
        {
          alertStingGerichte = alertStingGerichte + " " + this.problemGerichte[i]
          if(i<this.problemGerichte.length-1)
          {
            alertStingGerichte = alertStingGerichte + ",";
          }
          else{
            alertStingGerichte = alertStingGerichte + ""
          }
        }

        alertString = alertString + alertStingGerichte;

        alert(alertString);
      }

    },
    async paypalRequest() {

      await this.checkOeffnungszeiten();

      if(this.oeffnungszeitenOkay === false)
      {
        return;
      }

      const items = [];

      this.$store.getters.getCartGerichte.forEach(item => {
        for (let i = 0; i < item.quantity; i++) {
          items.push(item.gericht_ID);
        }
      });

      const customerId = await this.getCustomerId();

      // console.log(customerId);

      this.$http.post('/Bestellung/add', {
        items: items,
        customerId: customerId,
        timestamp: moment(this.timestampCustomerDemandDatabase).unix()
      }, this.$store.getters.getLoginData).then((response) => {
        if (response.status === 201) {

          this.$http.post('/Bestellung/pay', {
            jobId: response.data.auftrags_ID
          }, this.$store.getters.getLoginData).then((response) => {
            if (response.status === 200) {
              this.$store.commit("deleteCartGerichte");
              this.version++;
              this.$router.push({name: "BezahlungErfolgreich", params: { cart: this.carts, payment: response.data }});
            }
          });
        }
      });
    },
    formatDate (date) {
      if (!date) return null

      const [year, month, day] = date.split('-')
      return `${day}.${month}.${year}`
    },
  },
  data() {
    return {
      carts: [],
      version: 0,
      selectedGericht: "",
      dateDialog: false,
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      dateFormatted: this.formatDate((new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)),
      timeDialog: false,
      time: null,
      manLiefertermin: false,
      demandIsNow: true,
      hour: "",
      minute: "",
      dayOfWeek:"",
      problemGerichte: [],
      timestampCustomerDemand: "",
      timestampCustomerDemandDatabase: "",
      oeffnungszeitenOkay: false,
    };
  },
}
</script>