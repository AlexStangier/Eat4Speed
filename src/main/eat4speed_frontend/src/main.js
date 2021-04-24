import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'
import store from './store'
import axios from "axios";
import * as VueGoogleMaps from 'vue2-google-maps'

Vue.config.productionTip = false
Vue.prototype.$http = axios;
Vue.prototype.$auth = {
  auth: {
    username: store.state.emailAdresse,
    password: store.state.passwort
  }
};

//https://www.npmjs.com/package/vue2-google-maps
Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyCltkBHd3EaI9C9cUQvGYEMIjhLuETEFH4',
    libraries: 'places'
  },
})

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
