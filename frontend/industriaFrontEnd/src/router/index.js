import DashBoard from '@/pages/DashBoard.vue'
import FormMaterial from '@/pages/materials/FormMaterial.vue'
import Materials from '@/pages/materials/Materials.vue'
import FormProduct from '@/pages/products/FormProduct.vue'
import Products from '@/pages/products/Products.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/layouts/DefaultLayout.vue'),
      children: [
        {
          path: '',
          redirect: 'dashboard',
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: DashBoard,
        },
        
        // --- SEÇÃO DE MATERIAIS ---
        {
          path: 'materials',
          children: [
            {
              path: '', // URL: /materials
              name: 'materials',
              component: Materials,
            },
            {
              path: 'form_material/:id', // URL: /materials/form_material/1
              name: 'formmaterials',
              component: FormMaterial,
            },
          ]
        },

        // --- SEÇÃO DE PRODUTOS ---
        {
          path: 'products',
          children: [
            {
              path: '', // URL: /products
              name: 'products',
              component: Products,
            },
            {
              path: 'form_product/:id', // URL: /products/form_product/1
              name: 'formProduct',
              component: FormProduct,
            }
          ]
        }
      ]
    }
  ]
})

export default router
