import KundeAnmeldung from '@/views/Kunde/KundeAnmeldung.vue'
import Kunde from '@/views/Kunde/Kunde.vue'
import KundeDish from '@/views/Kunde/KundeDish.vue'

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
        path: '/dish',
        name: 'dish',
        component: KundeDish
    },
]