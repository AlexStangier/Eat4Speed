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
              <v-col align="center"

                      class="mb-10">
                <v-btn
                   @click="morgenFarbe"
                   :color="btnType === 0 ? 'primary' : 'blue-grey'"
                    class = "mb-2"

                    dark
                    bottom
                   height="100"
                >
                  Frühschicht
                  <v-spacer></v-spacer>
                  von 7:00 - 15:00
                </v-btn>
                <v-btn
                    @click="nachmittagFarbe"
                    :color="btnType === 1 ? 'primary' : 'blue-grey'"
                    class = "mb-2"

                    dark
                    height="100"
                    bottom
                >
                  Spätschicht
                  <v-spacer></v-spacer>
                  von 15:00 - 22:00
                </v-btn>
                <v-btn
                    @click="nachtFarbe"
                    :color="btnType === 2 ? 'primary' : 'blue-grey'"
                    class = "mb-2"

                    dark
                    height="100"
                    bottom
                >
                  Nachtschicht
                  <v-spacer></v-spacer>
                  von 22:00 - 22:00
                </v-btn>
              </v-col>
              <v-btn
                  @click="setSchicht(btnType)"
                  color= "primary"
                  class = "mb-2"
                  dark
                  bottom
              >
                Auswahl bestätigen
              </v-btn>
              <v-row>
              <v-btn
                  class = "mb-2 mr-2"
                  color="red"
                  dark
              >
                Pause beginnen...
              </v-btn>
                <h4>Übrige Zeit: 1:00h</h4>
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

    morning: false,
    afternoon: false,
    night: false,
    btnType: 0

  }),
  mounted() {
    //this.loadZeiten()
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

    async setSchicht(tag) {

      const response = await axios.post("Benutzer/getIdByEmail",{ email: this.$store.getters.getLoginData.auth.username });
      const fahrer_id = await axios.get("Fahrer/get/" + response.data);
      console.log(response.data)
      console.log(fahrer_id.data[0])

      let anfang;
      let ende;
      switch (tag){
        case 0: {
          anfang = "7:00"
          ende = "15:00"
          break
        }
        case 1: {
          anfang = "15:00"
          ende = "23:00"
          break
        }
        case 2: {
          anfang = "23:00"
          ende = "7:00"
          break
        }
      }

      let time = {

        anfang: new Date('January 1, 2000 ' + anfang + ':00'),
        ende: new Date('January 1, 2000 ' + ende + ':00'),
        fahrernummer: fahrer_id.data[0],

      }

      console.log(time)

      await axios.post("/Schichten/setSchicht", time);
    },
  }
}
</script>

<style scoped>

</style>