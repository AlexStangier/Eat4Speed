import FahrerAnmeldung from '@/views/Fahrer/FahrerAnmeldung.vue'
import FahrerFahrtenplan from '@/views/Fahrer/FahrerFahrtenplan.vue'
import FahrerSchichtplan from "@/views/Fahrer/FahrerSchichtplan";

export default [
    {
        path: '/fahrer/anmeldung',
        name: 'FahrerAnmeldung',
        component: FahrerAnmeldung
    },
    {
        path: '/fahrer/fahrtenplan',
        name: 'FahrerFahrtenplan',
        component: FahrerFahrtenplan
    },
    {
        path: '/fahrer/schichtplan',
        name: 'FahrerSchichtplan',
        component: FahrerSchichtplan
    }
]