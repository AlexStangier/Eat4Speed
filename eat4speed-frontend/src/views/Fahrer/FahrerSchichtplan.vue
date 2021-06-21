<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout  justify-center>
        <v-flex md6 sm6 xs12>
          <template>
            <h1>Schichtplan</h1>
            <v-card class="px-4">
              <v-card-title>Bitte wählen Sie Ihre Schicht</v-card-title>
              <v-spacer></v-spacer>
              <v-btn :to="{name: 'FahrerFahrtenplan'}">
                <v-icon>
                  mdi-arrow-left
                </v-icon>
                Fahrtenplan
              </v-btn>
              <v-col align="center">
                <v-btn
                   @click="morgenFarbe"
                   :color="btnType === 0 ? 'primary' : 'blue-grey'"
                    class = "mb-2"
                    :disabled="morning"
                   height="100"
                >
                  Frühschicht
                  <v-spacer></v-spacer>
                  von 7:00 - 15:00
                </v-btn>
                <v-btn
                    @click="nachmittagFarbe"
                    :color="btnType === 1 ? 'primary' : 'blue-grey'"
                    class = "mb-2 ml-2"
                    :disabled="afternoon"
                    height="100"
                >
                  Spätschicht
                  <v-spacer></v-spacer>
                  von 15:00 - 22:00
                </v-btn>
                <v-btn
                    @click="nachtFarbe"
                    :color="btnType === 2 ? 'primary' : 'blue-grey'"
                    class = "mb-2"
                    :disabled="night"
                    height="100"
                >
                  Nachtschicht
                  <v-spacer></v-spacer>
                  von 22:00 - 22:00
                </v-btn>
              </v-col>
              <v-col>
              <v-dialog
                  v-model="ok"
                  persistent
                  min-height="100"
                  max-width="290"
              >
                <template v-slot:activator="{ on, attrs }">
              <v-btn
                  @click="setSchicht(btnType), ok=true"
                  color= "primary"
                  class = "mb-2"
                  :disabled="confirm"
                  v-bind="attrs"
                  v-on="on"
              >
                Auswahl bestätigen
              </v-btn>
                </template>
                <v-card>
                  <h3>Schicht festgelegt</h3>
                  <v-btn @click="ok = false">Ok</v-btn>
                </v-card>
              </v-dialog>

              <v-btn
                  @click="setPause"
                  class = "mb-2 ml-2"
                  :color=pauseColor
                  dark
                  id="pause"
              >
                Pause beginnen...
              </v-btn>

              </v-col>
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

    pause: 1,
    morning: false,
    afternoon: false,
    night: false,
    confirm : false,
    ok : false,
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
      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$cookies.get('emailAdresse') });
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data);
      const fahrer_id = fahrer_id_data.data[0]

      await axios.put("Fahrer/setPause/" + fahrer_id[0] + "/" + this.pause)
      await this.loadZeiten()
    },

    async setSchicht(tag) {

      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$cookies.get('emailAdresse') });
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data);
      const fahrer_id = fahrer_id_data.data[0]

      let anfang;
      let ende;
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
          break
        }
      }
      let anfangMoment = moment().second(0).hour(anfang).minute(0).toDate();
      let endeMoment = moment().second(0).hour(ende).minute(0).toDate();

      let time = {

        anfang: anfangMoment,
        ende: endeMoment,
        fahrernummer: fahrer_id[0],

      }

      console.log(time)

      if(this.timeInDB){
        await axios.put("/Schichten/updateSchicht", time);
      }
      else{
        await axios.post("/Schichten/setSchicht", time);
      }

      await this.loadZeiten();
    },
    async calcIfPause(){
      const amountPause = await axios.get("Fahrer/getAmountInPause");
      const amountFahrer = await axios.get("Schichten/getAmountActiveSchicht");
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
      //await this.calcIfPause()
      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$cookies.get('emailAdresse') });
      const fahrer_id_data = await axios.get("Fahrer/get/" + response.data);
      const fahrer_id = fahrer_id_data.data[0];
      const schichtdata = await axios.get("/Schichten/getSchicht/" + fahrer_id[0]);
      if(schichtdata.data.length == 0){
        this.timeInDB = false;
      }
      else {
        if(await this.calcIfPause()){
          document.getElementById("pause").innerHTML = "Zu viele Fahrer in Pause"
          this.pauseColor = "red"
          this.pause = 0
        }
        else {
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
        const schicht = schichtdata.data[0];
        if(moment().isSameOrBefore(moment(schicht[1].substring(0, 19)+"+00:00"), 'hour')){
          this.morning = true;
          this.afternoon = true;
          this.night = true;
          this.confirm = true;
          console.log(moment(schicht[1].substring(0, 16)+ "+0000").toDate());
        }
        if(moment().isBetween(moment().hour(7), moment().hour(15), 'hour')){
          this.morning = true;
        }
        if(moment().isBetween(moment().hour(15), moment().hour(22), 'hour')){
          this.afternoon = true;
        }
        if(moment().isBetween(moment().hour(22), moment().hour(7).add(1, 'd'), 'hour')){
          this.night = true;
        }
      }
    },


  }
}
</script>

<style scoped>

</style>