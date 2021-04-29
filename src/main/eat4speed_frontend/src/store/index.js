import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'
import Cookies from 'js-cookie'

Vue.use(Vuex)

const in30Minutes = 1/48;

const vuexLocal = new VuexPersistence({
  storage: window.localStorage
})

export default new Vuex.Store({
  state: {
  },
  plugins: [vuexLocal.plugin],
  mutations: {
    saveLoginData: (state, payload) => {
      Cookies.set('emailAdresse', payload.emailAdresse, { expires: in30Minutes, secure: true });
      Cookies.set('passwort', payload.passwort, { expires: in30Minutes, secure: true });
    }
  },
  actions: {
    deleteLoginDate: () => {
      Cookies.remove('emailAdresse');
      Cookies.remove('passwort');
    },
  },
  getters: {
    getLoginData: () => ({
      auth: {
        username: Cookies.get('emailAdresse'),
        password: Cookies.get('passwort')
      }
    }),
  },
  modules: {
  }
})
