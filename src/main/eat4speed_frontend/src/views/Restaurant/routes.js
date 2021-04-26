import RestaurantAnmeldung from '@/views/Restaurant/RestaurantAnmeldung.vue'
import RestaurantControlPanel from '@/views/Restaurant/RestaurantControlPanel.vue'
import RestaurantSpeisekarteGerichte from '@/views/Restaurant/RestaurantSpeisekarteGerichte.vue'
import RestaurantSpeisekarteGetränke from '@/views/Restaurant/RestaurantSpeisekarteGetränke.vue'
import RestaurantBestellungen from '@/views/Restaurant/RestaurantBestellungen.vue'
import RestaurantBestellübersicht from '@/views/Restaurant/RestaurantBestellübersicht.vue'
import RestaurantStammdaten from '@/views/Restaurant/RestaurantStammdaten.vue'
import RestaurantSchichtplan from '@/views/Restaurant/RestaurantSchichtplan.vue'
import RestaurantUmsatzstatistik from "@/views/Restaurant/RestaurantUmsatzstatistik";

export default [
    {
        path: '/restaurant/anmeldung',
        name: 'RestaurantAnmeldung',
        component: RestaurantAnmeldung
    },
    {
        path: '/restaurant/controlPanel',
        name: 'RestaurantControlPanel',
        component: RestaurantControlPanel
    },
    {
        path: '/restaurant/speisekarteGerichte',
        name: 'RestaurantSpeisekarteGerichte',
        component: RestaurantSpeisekarteGerichte
    },
    {
        path: '/restaurant/speisekarteGetraenke',
        name: 'RestaurantSpeisekarteGetränke',
        component: RestaurantSpeisekarteGetränke
    },
    {
        path: '/restaurant/bestellungen',
        name: 'RestaurantBestellungen',
        component: RestaurantBestellungen
    },
    {
        path: '/restaurant/bestelluebersicht',
        name: 'RestaurantBestellübersicht',
        component: RestaurantBestellübersicht
    },
    {
        path: '/restaurant/stammdaten',
        name: 'RestaurantStammdaten',
        component: RestaurantStammdaten
    },
    {
        path: '/restaurant/schichtplan',
        name: 'RestaurantSchichtplan',
        component: RestaurantSchichtplan
    },
    {
        path: '/restaurant/umsatzstatistik',
        name: 'RestaurantUmsatzstatistik',
        component: RestaurantUmsatzstatistik
    }
]