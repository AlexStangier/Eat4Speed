import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'
import store from './store'
import cookies from 'js-cookie'
import axios from "axios";
import * as VueGoogleMaps from 'vue2-google-maps'
import VideoBackground from 'vue-responsive-video-background-player'

Vue.config.productionTip = false
Vue.prototype.$http = axios;
Vue.prototype.$cookies = cookies;

//https://www.npmjs.com/package/vue2-google-maps
Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyCltkBHd3EaI9C9cUQvGYEMIjhLuETEFH4',
    libraries: 'places'
  },
})

Vue.component('video-background', VideoBackground);

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
