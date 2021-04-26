<template>
  <v-main>
    <v-container fill-height fluid>
      <v-layout align-center justify-center>
        <v-flex md6 sm6 xs12>



            <v-row>
              <v-col
                  cols="12"
                  sm="6"
              >
                <v-date-picker
                    v-model="dates"
                    range
                    @change="iterateDate"
                ></v-date-picker>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
              >
                <v-text-field
                    v-model="dateRangeText"
                    label="Date range"
                    prepend-icon="mdi-calendar"
                    readonly
                ></v-text-field>
                model: {{ dates }}
              </v-col>
            </v-row>





              <v-sheet
                  class="v-sheet--offset mx-auto"
                  color="cyan"
                  elevation="12"
                  max-width="calc(100% - 32px)"
              >
                <v-sparkline

                    :labels="iteratedDates"
                    :value="value"
                    color="white"
                    line-width="2"
                    padding="16"
                ></v-sparkline>
              </v-sheet>





        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import moment from 'moment';
export default {

  data () {
    return{
      labels: [
        '15.04',
        '16.04',
        '17.04',
        '18.04',
        '19.04',
        '20.04',
        '21.04',
        '22.04',
      ],
          value:[ ],
          dates : ['2021-09-10', '2021-09-20'],
      iteratedDates :[]
    };
  },
  methods:{
    async iterateDate() {
      this.iteratedDates=[];
      this.value=[];
      var date0;
      var date1;
      if (this.dates[0] > this.dates[1]) {
        date0 = new Date(this.dates[1]);
        date1 = new Date(this.dates[0]);
      } else {
        date1 = new Date(this.dates[1]);
        date0 = new Date(this.dates[0]);
      }


      //var datesList = [];
      for (var d = date0; d <= date1; d.setDate(d.getDate() + 1)) {
        const date = moment(d, "YYYYMMDD").format("YYYY.MM.DD");
        this.iteratedDates.push(date.toString());
        this.value.push(Math.random());
      }
      console.log(this.iteratedDates);

    }
  },
  computed: {
    dateRangeText () {
      return this.dates.join(' ~ ')
    },

  },
}
</script>

<style scoped>
.v-sheet--offset {
  top: -24px;
  position: relative;
}
</style>