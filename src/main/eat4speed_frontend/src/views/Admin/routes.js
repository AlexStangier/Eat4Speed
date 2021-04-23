import AdminAnmeldung from '@/views/Admin/AdminAnmeldung.vue'
import AdminVerifizierungFahrer from '@/views/Admin/AdminVerifizierungFahrer.vue'
import AdminVerifizierungRestaurants from '@/views/Admin/AdminVerifizierungRestaurants.vue'

export default [
    {
        path: '/admin/anmeldung',
        name: 'AdminAnmeldung',
        component: AdminAnmeldung
    },
    {
        path: '/admin/verifizierungFahrer',
        name: 'AdminVerifizierungFahrer',
        component: AdminVerifizierungFahrer
    },
    {
        path: '/admin/verifizierungRestaurants',
        name: 'AdminVerifizierungRestaurants',
        component: AdminVerifizierungRestaurants
    },
]
