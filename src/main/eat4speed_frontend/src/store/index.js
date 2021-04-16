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
    username: '',
    password: ''
  },
  plugins: [vuexLocal.plugin],
  mutations: {
    saveLoginData(state, payload) {
      state.username = payload.username;
      state.password = payload.password;
    }
  },
  actions: {
  },
  modules: {
  }
})
