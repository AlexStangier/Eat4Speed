<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>
          <template>
            <v-card class="px-4">
              <v-col>
                <v-card-title>Lieferzeiten</v-card-title>
                <v-row>
                  <v-checkbox label="Montag" v-model="enabled"/>

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

                <v-row>
                  <v-checkbox label="Dienstag" v-model="enabled1"/>
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

                <v-row>
                  <v-checkbox label="Mittwoch" v-model="enabled2"/>
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

                <v-row>
                  <v-checkbox label="Donnerstag" v-model="enabled3"/>
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

                <v-row>
                  <v-checkbox label="Freitag" v-model="enabled4"/>
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

                <v-row>
                  <v-checkbox label="Samstag" v-model="enabled5"/>
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

                <v-row>
                  <v-checkbox label="Sonntag" v-model="enabled6"/>
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
                    :retain-focus="false"
                    v-model="enabledException"
                    width="290"
                    persistent
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="red"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        small
                        bottom
                    >
                      Ausnahme...
                    </v-btn>

                  </template>

                  <v-card d-flex>
                    <v-col>
                      <v-date-picker v-model="picker"></v-date-picker>
                      <v-checkbox align-center label="Ganztägig"></v-checkbox>

                      <v-col
                          cols="11"
                          sm="5"
                      >
                        <v-dialog
                            ref="dialogEx"
                            v-model="timeException.timePicker"
                            :return-value.sync="timeException.timesStart"
                            persistent
                            width="290px"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                                v-model="timeException.timesStart"
                                label="Von"
                                prepend-icon="mdi-clock-time-four-outline"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                            ></v-text-field>
                          </template>
                          <v-time-picker
                              v-if="timeException.timePicker"
                              v-model="timeException.timesStart"
                              full-width
                          >
                            <v-spacer></v-spacer>
                            <v-btn
                                text
                                color="primary"
                                @click="$refs.dialogEx.save(timeException.timesStart)"
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
                            ref="dialogEx2"
                            v-model="timeException.timePickerEnd"
                            :return-value.sync="timeException.timesEnd"
                            persistent
                            width="290px"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                                v-model="timeException.timesEnd"
                                label="Bis"
                                prepend-icon="mdi-clock-time-four-outline"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                            ></v-text-field>
                          </template>
                          <v-time-picker
                              v-if="timeException.timePickerEnd"
                              v-model="timeException.timesEnd"
                              full-width
                          >
                            <v-spacer></v-spacer>
                            <v-btn
                                text
                                color="primary"
                                @click="$refs.dialogEx2.save(timeException.timesEnd)"
                            >
                              OK
                            </v-btn>
                          </v-time-picker>
                        </v-dialog>
                      </v-col>

                      <v-row justify="space-around" class="mb-2" @click="enabledException=false">
                        <v-btn color="red" dark>
                          Abbruch
                        </v-btn>
                        <v-btn color="green" dark align-end>
                          Bestätigen
                        </v-btn>
                      </v-row>

                    </v-col>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-card>
          </template>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "Schichtplan",
  methods: {},
  data: () => ({
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
  }),
  mounted() {

  },
}
</script>

<style scoped>

</style>