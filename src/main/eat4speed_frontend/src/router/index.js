import Vue from 'vue'
import VueRouter from 'vue-router'
//import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Start',
    component: () => import('../views/Start.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/login-driver',
    name: 'LoginDriver',
    component: () => import('../views/LoginDriver.vue')
  },
  {
    path: '/login-customer',
    name: 'LoginCustomer',
    component: () => import('../views/LoginCustomer.vue')
  },
  {
    path: '/login-restaurant',
    name: 'LoginRestaurant',
    component: () => import('../views/Login-Restaurant.vue')
  },
  {
    path: '/customer',
    name: 'Costumer',
    component: ()=> import('../views/Customer')
  },
  {
    path: '/restaurant-controlpanel',
    name: 'restaurant-controlpanel',
    component: () => import('../views/Restaurant-ControlPanel.vue')
  },
  {
    path: '/restaurant-speisekarte-gerichte',
    name: 'restaurant-speisekarte-gerichte',
    component: () => import('../views/Restaurant_Speisekarte_Gerichte.vue')
  },
  {
    path: '/login-admin',
    name: 'LoginAdmin',
    component: () => import('../views/LoginAdmin.vue')
  },
  {
    path: '/admin-verification',
    name: 'AdminVerification',
    component: () => import('../views/AdminVerification.vue')
  },
  {
    path:'/dish',
    name: 'Dish',
    component: () => import('../views/Dish')
  },
  {
    path: '/restaurant-bestellungen',
    name: 'RestaurantBestellungen',
    component: () => import('../views/Restaurant_Bestellungen.vue')
  },
  {
    path: '/restaurant-bestelluebersicht',
    name: 'RestaurantBestellübersicht',
    component: () => import('../views/Restaurant_Bestellübersicht.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
