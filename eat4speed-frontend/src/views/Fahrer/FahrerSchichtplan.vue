<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <template>
            <h1>Schichtplan</h1>
            <v-card class="px-4">

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
    timeException: {
      timePicker: false,
      timePickerEnd: false,

      timesStart: null,
      timesEnd: null
    },
    enabled: false,
    enabled1: false,
    enabled2: false,
    enabled3: false,
    enabled4: false,
    enabled5: false,
    enabled6: false,
    enabledException: false,

    updated: [false, false, false, false, false, false, false]
  }),
  mounted() {
    this.loadZeiten()
  },
  methods: {


    executeAll(){

      if(this.enabled && !this.updated[0]) this.setArbeitstag(0, "Montag")
      else if(this.enabled) this.updateArbeitstag(0, "Montag")
      if(this.enabled1 && !this.updated[1]) this.setArbeitstag(1, "Dienstag")
      else if(this.enabled1) this.updateArbeitstag(1, "Dienstag")
      if(this.enabled2 && !this.updated[2]) this.setArbeitstag(2, "Mittwoch")
      else if(this.enabled2) this.updateArbeitstag(2, "Mittwoch")
      if(this.enabled3 && !this.updated[3]) this.setArbeitstag(3, "Donnerstag")
      else if(this.enabled3) this.updateArbeitstag(3, "Donnerstag")
      if(this.enabled4 && !this.updated[4]) this.setArbeitstag(4, "Freitag")
      else if(this.enabled4) this.updateArbeitstag(4, "Freitag")
      if(this.enabled5 && !this.updated[5]) this.setArbeitstag(5, "Samstag")
      else if(this.enabled5) this.updateArbeitstag(5, "Samstag")
      if(this.enabled6 && !this.updated[6]) this.setArbeitstag(6, "Sonntag")
      else if(this.enabled6) this.updateArbeitstag(6, "Sonntag")
    },
    async loadZeiten() {

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$store.getters.getLoginData.auth.username);
      let StammdatenData = ResponseStammdaten.data[0];

      const ResponseZeiten = await axios.get("Oeffnungszeiten/getAllZeiten/" + StammdatenData[12]);

      for (let i = 0; i < ResponseZeiten.data.length; i++) {
        let zeitData = ResponseZeiten.data[i];

        switch (zeitData[2]){

          case "Montag": this.enabled = true;
            this.times.timesStart[0] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[0] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[0] = true;
            break;
          case "Dienstag": this.enabled1 = true;
            this.times.timesStart[1] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[1] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[1] = true;
            break;
          case "Mittwoch": this.enabled2 = true;
            this.times.timesStart[2] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[2] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[2] = true;
            break;
          case "Donnerstag": this.enabled3 = true;
            this.times.timesStart[3] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[3] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[3] = true;
            break;
          case "Freitag": this.enabled4 = true;
            this.times.timesStart[4] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[4] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[4] = true;
            break;
          case "Samstag": this.enabled5 = true;
            this.times.timesStart[5] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[5] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[5] = true;
            break;
          case "Sonntag": this.enabled6 = true;
            this.times.timesStart[6] = moment(zeitData[0].substring(0, 19)+"+00:00").format("HH:mm")
            this.times.timesEnd[6] = moment(zeitData[1].substring(0, 19)+"+00:00").format("HH:mm")
            this.updated[6] = true;
            break;
        }
      }

      this.version++;
    },
    async setArbeitstag(pos, tag) {

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$store.getters.getLoginData.auth.username);
      let StammdatenData = ResponseStammdaten.data[0];

      let time = {

        anfang: new Date('January 1, 2000 ' + this.times.timesStart[pos] + ':00'),
        ende: new Date('January 1, 2000 ' + this.times.timesEnd[pos] + ':00'),
        wochentag: tag,
        restaurant_ID: StammdatenData[12]
      }

      await axios.post("/Oeffnungszeiten/setArbeitstag", time);
    },
    async updateArbeitstag(pos, tag) {

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$store.getters.getLoginData.auth.username);
      let StammdatenData = ResponseStammdaten.data[0];

      let time = {

        anfang: new Date('January 1, 2000 ' + this.times.timesStart[pos] + ':00'),
        ende: new Date('January 1, 2000 ' + this.times.timesEnd[pos] + ':00'),
        wochentag: tag,
        restaurant_ID: StammdatenData[12]
      }

      await axios.put("/Oeffnungszeiten/updateArbeitstag", time);

    },


  }
}
</script>

<style scoped>

</style>