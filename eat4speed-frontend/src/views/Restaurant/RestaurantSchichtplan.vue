<template>
  <v-main>
    <v-card class="mx-5 my-5">
    <v-app-bar color="primary"  dark>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title>Schichtplan</v-toolbar-title>
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
            <v-list-item-title>Startseite</v-list-item-title>
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
          <template>

              <v-col>
                <v-form v-model="valid">
                <v-card-title>Lieferzeiten</v-card-title>

                <v-row align="center">
                  <v-checkbox label="Montag" v-model="enabled"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog"
                        v-model="times.timePicker[0]"
                        :return-value.sync="times.timesStart[0]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled"
                            v-model="times.timesStart[0]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[0]"
                          v-model="times.timesStart[0]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog.save(times.timesStart[0])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog2"
                        v-model="times.timePickerEnd[0]"
                        :return-value.sync="times.timesEnd[0]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled"
                            v-model="times.timesEnd[0]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[0]"
                          v-model="times.timesEnd[0]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog2.save(times.timesEnd[0])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>

                </v-row>

                <v-row align="center">
                  <v-checkbox label="Dienstag" v-model="enabled1"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog3"
                        v-model="times.timePicker[1]"
                        :return-value.sync="times.timesStart[1]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled1"
                            v-model="times.timesStart[1]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[1]"
                          v-model="times.timesStart[1]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog3.save(times.timesStart[1])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog4"
                        v-model="times.timePickerEnd[1]"
                        :return-value.sync="times.timesEnd[1]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled1"
                            v-model="times.timesEnd[1]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[1]"
                          v-model="times.timesEnd[1]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog4.save(times.timesEnd[1])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-row align="center">
                  <v-checkbox label="Mittwoch" v-model="enabled2"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog5"
                        v-model="times.timePicker[2]"
                        :return-value.sync="times.timesStart[2]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled2"
                            v-model="times.timesStart[2]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[2]"
                          v-model="times.timesStart[2]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog5.save(times.timesStart[2])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog6"
                        v-model="times.timePickerEnd[2]"
                        :return-value.sync="times.timesEnd[2]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled2"
                            v-model="times.timesEnd[2]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[2]"
                          v-model="times.timesEnd[2]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog6.save(times.timesEnd[2])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-row align="center">
                  <v-checkbox label="Donnerstag" v-model="enabled3"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog7"
                        v-model="times.timePicker[3]"
                        :return-value.sync="times.timesStart[3]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled3"
                            v-model="times.timesStart[3]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[3]"
                          v-model="times.timesStart[3]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog7.save(times.timesStart[3])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog8"
                        v-model="times.timePickerEnd[3]"
                        :return-value.sync="times.timesEnd[3]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled3"
                            v-model="times.timesEnd[3]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[3]"
                          v-model="times.timesEnd[3]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog8.save(times.timesEnd[3])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-row align="center">
                  <v-checkbox label="Freitag" v-model="enabled4"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog9"
                        v-model="times.timePicker[4]"
                        :return-value.sync="times.timesStart[4]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled4"
                            v-model="times.timesStart[4]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[4]"
                          v-model="times.timesStart[4]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog9.save(times.timesStart[4])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog10"
                        v-model="times.timePickerEnd[4]"
                        :return-value.sync="times.timesEnd[4]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled4"
                            v-model="times.timesEnd[4]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[4]"
                          v-model="times.timesEnd[4]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog10.save(times.timesEnd[4])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-row align="center">
                  <v-checkbox label="Samstag" v-model="enabled5"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog11"
                        v-model="times.timePicker[5]"
                        :return-value.sync="times.timesStart[5]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled5"
                            v-model="times.timesStart[5]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[5]"
                          v-model="times.timesStart[5]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog11.save(times.timesStart[5])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog12"
                        v-model="times.timePickerEnd[5]"
                        :return-value.sync="times.timesEnd[5]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled5"
                            v-model="times.timesEnd[5]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[5]"
                          v-model="times.timesEnd[5]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog12.save(times.timesEnd[5])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-row align="center">
                  <v-checkbox label="Sonntag" v-model="enabled6"/>
                  <v-spacer></v-spacer>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog13"
                        v-model="times.timePicker[6]"
                        :return-value.sync="times.timesStart[6]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled6"
                            v-model="times.timesStart[6]"
                            label="Von"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required, enabled6]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePicker[6]"
                          v-model="times.timesStart[6]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog13.save(times.timesStart[6])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                  <v-col
                      cols="11"
                      sm="5"
                  >
                    <v-dialog
                        ref="dialog14"
                        v-model="times.timePickerEnd[6]"
                        :return-value.sync="times.timesEnd[6]"
                        persistent
                        width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            :disabled="!enabled6"
                            v-model="times.timesEnd[6]"
                            label="Bis"
                            prepend-icon="mdi-clock-time-four-outline"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            :rules="[rules.required, enabled6]"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="times.timePickerEnd[6]"
                          v-model="times.timesEnd[6]"
                          full-width
                      >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.dialog14.save(times.timesEnd[6])"
                        >
                          OK
                        </v-btn>
                      </v-time-picker>
                    </v-dialog>
                  </v-col>
                </v-row>

                <v-dialog
                    v-model="ok"
                    persistent
                    min-height="100"
                    max-width="240"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        @click="executeAll"
                        :disabled="enableSet"
                        class = "mb-2"
                        color="primary"
                        v-bind="attrs"
                        v-on="on"
                    >
                      Änderungen speichern
                    </v-btn>
                  </template>
                  <v-card class="pa-2" align="center">
                    <h3>Schicht festgelegt</h3>
                    <v-btn color="primary mt-2" @click="ok = false">Ok</v-btn>
                  </v-card>
                </v-dialog>
                </v-form>
              </v-col>

          </template>
        </v-flex>
      </v-layout>
    </v-container>
    </v-card>
  </v-main>
</template>

<script>
import axios from "axios";
import moment from "moment";


export default {
  name: "RestaurantSchichtplan",
  data: () => ({
    drawer: false,
    group: null,
    switch1: true,
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
    ok: false,

    updated: [false, false, false, false, false, false, false],
    enableSet: false,
    valid: false,

    rules: {
       required: (value) => !!value || "Required.",
    },
  }),
  mounted() {
    this.loadZeiten()
  },
  computed: {
  },
  methods: {


    executeAll(){
      this.enableSet = true;

      if(this.enabled && !this.updated[0]) this.setArbeitstag(0, "Montag")
      else if(this.enabled) this.updateArbeitstag(0, "Montag")
      if(!this.enabled && this.times.timesStart[0]!= null) this.deleteZeit(1)

      if(this.enabled1 && !this.updated[1]) this.setArbeitstag(1, "Dienstag")
      else if(this.enabled1) this.updateArbeitstag(1, "Dienstag")
      if(!this.enabled1 && this.times.timesStart[1]!= null) this.deleteZeit(2)

      if(this.enabled2 && !this.updated[2]) this.setArbeitstag(2, "Mittwoch")
      else if(this.enabled2) this.updateArbeitstag(2, "Mittwoch")
      if(!this.enabled2 && this.times.timesStart[2]!= null) this.deleteZeit(3)

      if(this.enabled3 && !this.updated[3]) this.setArbeitstag(3, "Donnerstag")
      else if(this.enabled3) this.updateArbeitstag(3, "Donnerstag")
      if(!this.enabled3 && this.times.timesStart[3]!= null) this.deleteZeit(4)

      if(this.enabled4 && !this.updated[4]) this.setArbeitstag(4, "Freitag")
      else if(this.enabled4) this.updateArbeitstag(4, "Freitag")
      if(!this.enabled4 && this.times.timesStart[4]!= null) this.deleteZeit(5)

      if(this.enabled5 && !this.updated[5]) this.setArbeitstag(5, "Samstag")
      else if(this.enabled5) this.updateArbeitstag(5, "Samstag")
      if(!this.enabled5 && this.times.timesStart[5]!= null) this.deleteZeit(6)

      if(this.enabled6 && !this.updated[6]) this.setArbeitstag(6, "Sonntag")
      else if(this.enabled6) this.updateArbeitstag(6, "Sonntag")
      if(!this.enabled6 && this.times.timesStart[6]!= null) this.deleteZeit(0)
    },
    async loadZeiten() {
    this.enableSet = false;

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      let StammdatenData = ResponseStammdaten.data[0];

      const ResponseZeiten = await axios.get("Oeffnungszeiten/getAllZeiten/" + StammdatenData[12], this.$store.getters.getLoginData);

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
      if(this.enabled && pos == 0 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled1 && pos == 1 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled2 && pos == 2 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled3 && pos == 3 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled4 && pos == 4 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled5 && pos == 5 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled6 && pos == 6 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }



      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      let StammdatenData = ResponseStammdaten.data[0];

      let time = {

        anfang: new Date('January 1, 2000 ' + this.times.timesStart[pos] + ':00'),
        ende: new Date('January 1, 2000 ' + this.times.timesEnd[pos] + ':00'),
        wochentag: tag,
        restaurant_ID: StammdatenData[12]
      }

      await axios.post("/Oeffnungszeiten/setArbeitstag", time, this.$store.getters.getLoginData);
    },
    async updateArbeitstag(pos, tag) {
      if(this.enabled && pos == 0 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled1 && pos == 1 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled2 && pos == 2 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled3 && pos == 3 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled4 && pos == 4 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled5 && pos == 5 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }
      if(this.enabled6 && pos == 6 && (this.times.timesStart[pos]==null || this.times.timesEnd[pos]==null)){
        alert("Bitte Werte eingeben! Laden Sie die Seite neu.");
        return;
      }

      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      let StammdatenData = ResponseStammdaten.data[0];


        let time = {
          anfang: new Date('January 1, 2000 ' + this.times.timesStart[pos] + ':00'),
          ende: new Date('January 1, 2000 ' + this.times.timesEnd[pos] + ':00'),
          wochentag: tag,
          restaurant_ID: StammdatenData[12]
        }
        await axios.put("/Oeffnungszeiten/updateArbeitstag", time, this.$store.getters.getLoginData);
    },

    async deleteZeit(pos){
      const ResponseStammdaten = await axios.get("Benutzer/getBenutzerByLogin/" + this.$cookies.get('emailAdresse'), this.$store.getters.getLoginData);
      let StammdatenData = ResponseStammdaten.data[0];

      const Oeffnungszeiten = await axios.get("/Oeffnungszeiten/getAllZeitenWochentag/" + StammdatenData[12] + "/" + pos, this.$store.getters.getLoginData);
      let OeffnungszeitenData = Oeffnungszeiten.data[0];

      await axios.delete("/Oeffnungszeiten/deleteZeitenOeffnungszeiten_ID/" + OeffnungszeitenData[2], this.$store.getters.getLoginData);
    }


  }
}
</script>

<style scoped>

</style>