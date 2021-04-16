import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)

// TODO: use cookies to expire login data
const vuexLocal = new VuexPersistence({
  storage: window.localStorage
})

export default new Vuex.Store({
  state: {
    emailAdresse: '',
    passwort: ''
  },
  plugins: [vuexLocal.plugin],
  mutations: {
    saveLoginData(state, payload) {
      state.emailAdresse = payload.emailAdresse;
      state.passwort = payload.passwort;
    }
  },
  actions: {
  },
  modules: {
  }
})
