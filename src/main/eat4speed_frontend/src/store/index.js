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
    searchString:"",
    gericht_ID:"",
    cartGerichte:[]
  },
  plugins: [vuexLocal.plugin],
  mutations: {
    changeSearchString: (state, payload) => {
      state.searchString = payload;
    },
    changeGericht_ID: (state, payload) => {
      state.gericht_ID = payload;
    } ,
    addToCartGerichte: (state, payload) => {
      state.cartGerichte.push(payload);
    },
    removeFromCartGerichte: (state, payload) => {

      for(let i = 0; i<state.cartGerichte.length;i++)
      {
        if(state.cartGerichte[i].gericht_ID===payload.gericht_ID)
        {
          state.cartGerichte.splice(i,1);
        }
      }

      },
    changeCartGerichte: (state, payload) => {
      state.cartGerichte = payload;
    },
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
    searchString: state => {
      return state.searchString;
    },
    gericht_ID: state => {
      return state.gericht_ID;
    },
    getCartGerichte: state => {
      return state.cartGerichte;
    },
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
