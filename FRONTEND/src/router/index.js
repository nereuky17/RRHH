import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import ListadoEmpresa from '@/components/ListadoEmpresa.vue'
import InsertarEmpleado from '@/components/InsertarEmpleado.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/listado',
      name: 'listado',
     component: ListadoEmpresa
    },
    {
      path: '/insertar',
      name: 'insertar',
      component: InsertarEmpleado
    }
  ]
})

export default router
