import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
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
    path: '/Start',
    name: 'Start',
    component: () => import('../views/Start.vue')
  },
  {
    path: '/customer',
    name: 'Costumer',
    component: ()=> import('../views/Customer')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
