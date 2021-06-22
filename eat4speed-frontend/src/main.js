import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'
import store from './store'
import cookies from 'js-cookie'
import axios from "axios";
import * as VueGoogleMaps from 'vue2-google-maps'
import VideoBackground from 'vue-responsive-video-background-player'
import VueMeta from 'vue-meta'
import moment from "moment";

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

moment.locale('de');

Vue.use(VueMeta);

router.beforeEach(async (to, from, next) => {
    const email = cookies.get('emailAdresse');
    let role = null;

    if (email === undefined && to && !to.fullPath.includes('/anmeldung')) {
        if (to.fullPath.includes('/admin') ||
            to.fullPath.includes('/fahrer') ||
            to.fullPath.includes('/restaurant')) {
            next(from);
            return;
        }
    } else {
        await axios.get(`Benutzer/getRoleByEmail/${email}`)
            .then((response) => {
                if (response.status === 200) {
                    role = response.data;
                }
            }, (error) => {
                console.log(error);
            });

        if (role && to && !to.fullPath.includes('/anmeldung')) {
            if ((to.fullPath.includes('/admin') && role !== 'admin') ||
                (to.fullPath.includes('/fahrer') && role !== 'fahrer') ||
                (to.fullPath.includes('/restaurant') && role !== 'restaurant')) {
                next(from);
                return;
            }
        }
    }

    next();
})

new Vue({
    vuetify,
    router,
    store,
    render: h => h(App)
}).$mount('#app')
