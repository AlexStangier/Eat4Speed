<template>
  <v-main>
    <v-container>
      <h1>Einstellungen</h1>
      <v-container>
        <v-form
            ref="form"
            v-model="valid"
            lazy-validation
        >
          <v-select
              v-model="select"
              :items="anrede"
              :rules="[v => !!v || 'Wählen Sie eine Anrede']"
              label="Anrede"
              required
          ></v-select>

          <v-text-field
              v-model="firstname"
              :rules="[v => !!v || 'Vorname wird benötigt']"
              label="Vorname"
              required
          ></v-text-field>

          <v-text-field
              v-model="lastname"
              :rules="[v => !!v || 'Nachname wird benötigt']"
              label="Nachname"
              required
          ></v-text-field>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="street"
                  :rules="[v => !!v || 'Strasse wird benötigt']"
                  label="Strasse"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="houseNumber"
                  :rules="[v => !!v || 'Hausnummer wird benötigt']"
                  label="Hausnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="town"
                  :rules="[v => !!v || 'Ort wird benötigt']"
                  label="Ort"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="zip"
                  :rules="[v => !!v || 'Postleitzahl wird benötigt']"
                  label="Postleitzahl"
                  required
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row no-gutters>
            <v-col>
              <v-text-field
                  v-model="email"
                  :rules="emailRules"
                  label="E-mail"
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  v-model="phone"
                  :rules="[v => !!v || 'Telefonnummer wird benötigt']"
                  label="Telefonnummer"
                  required
              ></v-text-field>
            </v-col>
          </v-row>


          <v-btn
              :disabled="!valid"
              @click="validate"
          >
            Ändern
          </v-btn>

          <v-btn
              @click="reset"
          >
            Reset Form
          </v-btn>

          <v-btn
              color="error"
          >
            Konto löschen
          </v-btn>
        </v-form>
      </v-container>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "CustomerSettings",
  data: () => ({
    valid: true,
    select: null,
    anrede: [
      '-',
      'Frau',
      'Herr',
    ],
    firstname: '',
    lastname: '',
    street: '',
    houseNumber: '',
    houseNumberRules: [
      v => !!v || 'Hausnummer wird benötigt',
      v => /.number/.test(v) || 'Nummer muss eine Zahl sein'
    ],
    town: '',
    zip: '',
    email: '',
    emailRules: [
      v => !!v || 'E-mail wird benötigt',
      v => /.+@.+/.test(v) || 'E-mail muss korrekt sein'
    ],
    phone: ''
  }),
  methods: {
    validate () {
      if (this.$refs.form.validate()) {
        this.snackbar = true
      }
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    }
  }
}
</script>

<style scoped>

</style>