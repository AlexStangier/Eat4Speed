import KundeAnmeldung from '@/views/Kunde/KundeAnmeldung.vue'
import Kunde from '@/views/Kunde/Kunde.vue'
import KundeGericht from '@/views/Kunde/KundeGericht.vue'
import KundeFavorites from '@/views/Kunde/KundeFavorites.vue'
import KundeAuswahlseiteRestaurant from '@/views/Kunde/KundeAuswahlseiteRestaurant.vue'
import KundeEinstellungen from '@/views/Kunde/KundeEinstellungen';
import KundeRestaurants from "@/views/Kunde/KundeRestaurants";

export default [
    {
        path: '/anmeldung',
        name: 'KundeAnmeldung',
        component: KundeAnmeldung
    },
    {
        path: '/kunde',
        name: 'Kunde',
        component: Kunde
    },
    {
        path: '/gericht',
        name: 'Gericht',
        component: KundeGericht
    },
    {
        path: '/favorites',
        name: 'Favorites',
        component: KundeFavorites
    },
    {
        path: '/kundeAuswahlseiteRestaurant',
        name: 'KundeAuswahlseiteRestaurant',
        component: KundeAuswahlseiteRestaurant
    },
    {
        path: '/einstellungen',
        Name: 'Einstellungen',
        component: KundeEinstellungen
    },
    {
        path: '/kundeRestaurants',
        Name: 'KundeRestaurants',
        component: KundeRestaurants
    }
]