<template><v-main>
  <v-container>
    <v-row>
      <v-col cols="1"
      >
        <v-btn small to="/customer">Zurück</v-btn>
      </v-col>
    </v-row>
    <v-container>
      <v-container>
        <v-row>
          <v-col
              v-for="a in 2"
              :key="a"
          >
            <v-img v-if="a === 1" src="https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg" alt="Bild von Essen" max-height="500" max-width="500"></v-img>
            <v-content v-if="a === 2" align="left"> <!--Information-->
              <v-row
                  v-for="b in 10"
                  :key="b"
              >
                <v-col
                    v-for="c in 3"
                    :key="c"
                >
                  <v-content v-if="b === 1 & c === 1">
                    <h1>Burger</h1>
                  </v-content>
                  <v-content v-if="b === 1 & c === 3" align="right">
                    <v-btn small="true" right="right">
                      <v-icon>mdi-heart</v-icon>
                    </v-btn>
                  </v-content>
                  <v-content v-if="b === 2 & c === 1">
                    <h3>Bob's Burger</h3>
                  </v-content>
                  <v-content v-if="b === 10 & c === 1">
                    Anzahl
                  </v-content>
                  <v-content v-if="b === 10 & c === 2">
                    Preis
                  </v-content>
                  <v-content v-if="b === 10 & c === 3">
                    <v-btn>Bestellen</v-btn>
                  </v-content>
                </v-col>
              </v-row>
            </v-content>
          </v-col>
        </v-row>
        <br>
        <v-textarea outlined readonly no-resize rows="8" value="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.

Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet,"></v-textarea>
        </v-container>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "KundeDish"
}
</script>

<style scoped>

</style>