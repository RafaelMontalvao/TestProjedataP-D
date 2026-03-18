import DashBoard from '@/pages/DashBoard.vue'
import FormMaterial from '@/pages/materials/FormMaterial.vue'
import Materials from '@/pages/materials/Materials.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/layouts/DefaultLayout.vue'),
      children:[
       {
          path: '',
          redirect: 'dashboard',
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: DashBoard,
        },
         {
          path: 'materials',
          name: 'materials',
          component: Materials,
        },
          {
          path: 'form_material/:id',
          name: 'formmaterials',
          component: FormMaterial,
        },


       
      ]
    }],

})

export default router
