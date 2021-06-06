import FahrerAnmeldung from '@/views/Fahrer/FahrerAnmeldung.vue'
import FahrerFahrtenplan from '@/views/Fahrer/FahrerFahrtenplan.vue'
import FahrerScore from '@/views/Fahrer/FahrerScore.vue'

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
        path: '/fahrer/fahrerscore',
        name: 'FahrerScore',
        component: FahrerScore
    },
]