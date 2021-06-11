import Vue from 'vue'
import VueRouter from 'vue-router'
import adminRoutes from '@/views/Admin/routes'
import fahrerRoutes from '@/views/Fahrer/routes'
import kundeRoutes from '@/views/Kunde/routes'
import restaurantRoutes from '@/views/Restaurant/routes'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Startseite',
    component: () => import('../views/Startseite.vue')
  },
  {
    path: '/bezahlungErfolgreich',
    name: 'BezahlungErfolgreich',
    props: true,
    component: () => import('../views/Kunde/KundeBezahlungErfolgreich.vue')
  },
  {
    path: '*',
    component: () => import('../components/NotFoundPage.vue')
  },
  ...adminRoutes,
  ...fahrerRoutes,
  ...kundeRoutes,
  ...restaurantRoutes
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

export default router
