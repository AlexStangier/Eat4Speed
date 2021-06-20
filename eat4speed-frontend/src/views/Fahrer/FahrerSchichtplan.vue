<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout  justify-center>
        <v-flex md6 sm6 xs12>
          <template>
            <h1>Schichtplan</h1>
            <v-card class="px-4">
              <v-card-title>Bitte wählen Sie Ihre Schicht (möglich zwischen 0:00 und 22:00 Uhr)</v-card-title>
              <v-spacer></v-spacer>
              <v-btn :to="{name: 'FahrerFahrtenplan'}">
                <v-icon>
                  mdi-arrow-left
                </v-icon>
                Fahrtenplan
              </v-btn>
              <v-row class="pa-2">
              <v-col align="center" >
                <v-btn
                   @click="morgenFarbe"
                   :color="btnType === 0 ? 'primary' : 'blue-grey'"
                   class = "ma-1 white--text"
                   :disabled="morning"
                   height="100"
                   width="240"
                >
                  Frühschicht (7:00 - 15:00)
                </v-btn>
                <v-btn
                    @click="nachmittagFarbe"
                    :color="btnType === 1 ? 'primary' : 'blue-grey'"
                    class = "ma-1 white--text"
                    :disabled="afternoon"
                    height="100"
                    width="240"
                >
                  Spätschicht (15:00 - 23:00)
                </v-btn>
                <v-btn
                    @click="nachtFarbe"
                    :color="btnType === 2 ? 'primary' : 'blue-grey'"
                    class = "ma-1 white--text"
                    :disabled="night"
                    height="100"
                    width="240"
                >
                  Nachtschicht (23:00 - 7:00)
                </v-btn>
              </v-col>
              </v-row>
              <v-row>
              <v-col>
              <v-dialog
                  v-model="ok"
                  persistent
                  min-height="100"
                  max-width="240"
              >
                <template v-slot:activator="{ on, attrs }">
              <v-btn
                  @click="setSchicht(btnType), ok=true"
                  color="primary"
                  class = "mb-2  mr-2"
                  :disabled="confirm"
                  v-bind="attrs"
                  v-on="on"
              >
                Auswahl bestätigen
              </v-btn>
                </template>
                <v-card class="pa-2" align="center">
                  <h3>Schicht festgelegt</h3>
                  <v-btn color="primary mt-2" @click="ok = false">Ok</v-btn>
                </v-card>
              </v-dialog>
                <v-btn
                    @click="setPause"
                    class = "mb-2 white--text"
                    :color=pauseColor
                    :disabled="pauseDisabled"
                    id="pause"
                >
                  Pause beginnen...
                </v-btn>
              </v-col>
              </v-row>
            </v-card>
          </template>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "FahrerSchichtplan",
  data: () => ({
    tooManyMorning: false,
    tooManyAfternoon: false,

    times: {
      timePicker: [false, false, false, false, false, false, false],
      timePickerEnd: [false, false, false, false, false, false, false],

      timesStart: [null, null, null, null, null, null, null],
      timesEnd: [null, null, null, null, null, null, null],
    },


    pauseDisabled: false,
    pause: 1,
    morning: false,
    afternoon: false,
    night: false,
    confirm : false,
    ok : false,
    ok2 : false,
    btnType: 0,
    timeInDB: true,
    pauseColor: "green"

  }),
  mounted() {
    this.loadZeiten()
  },
  methods: {

    morgenFarbe(){
      this.btnType=0
    },
    nachmittagFarbe(){
      this.btnType=1
    },
    nachtFarbe(){
      this.btnType=2
    },

    async setPause(){
      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$store.getters.getLoginData.auth.username });
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data);
      const fahrer_id = fahrer_id_data.data[0]

      await axios.put("Fahrer/setPause/" + fahrer_id[0] + "/" + this.pause)
      await this.loadZeiten()
    },

    async setSchicht(tag) {

      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$store.getters.getLoginData.auth.username });
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data);
      const fahrer_id = fahrer_id_data.data[0]

      let anfang;
      let ende;
      let nextday;
      switch (tag){
        case 0: {
          anfang = "7"
          ende = "15"
          break
        }
        case 1: {
          anfang = "15"
          ende = "23"
          break
        }
        case 2: {
          anfang = "23"
          ende = "7"
          nextday = true
          break
        }
      }
      let anfangMoment = moment().second(0).hour(anfang).minute(0).toDate();
      let endeMoment
      if(nextday) {
        endeMoment = moment().second(0).hour(ende).minute(0).add(1, "day").toDate();
      }
      else{
        endeMoment = moment().second(0).hour(ende).minute(0).toDate();
      }

      let time = {

        anfang: anfangMoment,
        ende: endeMoment,
        fahrernummer: fahrer_id[0],

      }

      if(this.timeInDB){
        await axios.put("/Schichten/updateSchicht", time, this.$store.getters.getLoginData);
      }
      else{
        await axios.post("/Schichten/setSchicht", time, this.$store.getters.getLoginData);
      }

      await this.loadZeiten();
    },
    async calcIfPause(){
      const amountPause = await axios.get("Fahrer/getAmountInPause", this.$store.getters.getLoginData);
      const amountFahrer = await axios.get("Schichten/getAmountActiveSchicht", this.$store.getters.getLoginData);
      if(((amountFahrer.data - amountPause.data) <= 2) && amountFahrer.data != 1 &&
          !(amountFahrer.data == 2 && amountPause.data < 2))
      {
        return true
      }
      else{
        return false
      }
    },
    async loadZeiten(){

      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$store.getters.getLoginData.auth.username }, this.$store.getters.getLoginData);
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data, this.$store.getters.getLoginData);
      const fahrer_id = fahrer_id_data.data[0];
      const schichtdata = await axios.get("/Schichten/getSchicht/" + fahrer_id[0], this.$store.getters.getLoginData);

      if(schichtdata.data.length == 0){
        this.timeInDB = false;
        this.pauseDisabled = true;
        document.getElementById("pause").innerHTML = "Pause nicht verfügbar"
        this.calcVisibility()
      }
      else {
        const schicht = schichtdata.data[0];
        if(await this.calcIfPause()){
          document.getElementById("pause").innerHTML = "Pause nicht verfügbar"
          this.pauseDisabled = true;
          this.pauseColor = "red"
          this.pause = 0
        }
        else {
          if(moment().isBetween(moment(schicht[0].substring(0, 19)+"+00:00"), moment(schicht[1].substring(0, 19)+"+00:00"), "minute")) {
            this.pauseDisabled = false;
          }
          else{
            document.getElementById("pause").innerHTML = "Pause nicht verfügbar"
            this.pauseDisabled = true;
            this.pauseColor = "red"
            this.pause = 0
          }
          if (fahrer_id[1] == 1) {
            document.getElementById("pause").innerHTML = "Pause beenden..."
            this.pauseColor = "red"
            this.pause = 0
          } else {
            document.getElementById("pause").innerHTML = "Pause beginnen..."
            this.pauseColor = "green"
            this.pause = 1
          }
        }


        this.calcVisibility();

        //dieser Abschnitt nur für Fahrer die gerade in einer Schicht sind
        if(moment().isSameOrBefore(moment(schicht[1].substring(0, 19)+"+00:00"), 'day')){
          if(moment().hour(7).isSame(moment(schicht[0].substring(0, 19)+"+00:00"), "hour")){
            this.afternoon = true;
            this.night = true;
            this.morning = false;
            this.morgenFarbe();
          }
          if(moment().hour(15).isSame(moment(schicht[0].substring(0, 19)+"+00:00"), "hour")){
            this.morning = true;
            this.night = true;
            this.afternoon = false;
            this.nachmittagFarbe();
          }
          if(moment().hour(23).isSame(moment(schicht[0].substring(0, 19)+"+00:00"), "hour")){
            this.afternoon = true;
            this.morning = true;
            this.nachtFarbe();
          }
          this.confirm = true;
        }
      }
    },

    calcVisibility(){

      if(moment().isBetween(moment().hour(7).minute(0), moment().hour(15).minute(0), 'minute')){
        this.morning = true;
        this.afternoon = false;
        this.night = false;
        if(!this.timeInDB) this.nachmittagFarbe();

      }
      if(moment().isBetween(moment().hour(15).minute(0), moment().hour(22).minute(0), 'minute')){
        this.morning = true;
        this.afternoon = true;
        this.night = false;
        if(!this.timeInDB) this.nachtFarbe();
      }
      if(moment().isAfter(moment().hour(23).minute(0), "minute") || moment().isBefore(moment().hour(7).minute(0), "minute")){
        this.morning = true;
        this.afternoon = true;
        this.night = true;
      }
    }

  }
}
</script>

<style scoped>

</style>