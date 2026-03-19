import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useApi } from '@/composables/useApi'

export const useProductsStore = defineStore('products', () => {

  const api = useApi() 
  const products = ref([])
  const producibles = ref([])
  const isLoading = ref(false)
  const error = ref(null)


  const createProduct = async(product)=>{
    isLoading.value = true
     try{
      const {data} = await api.post('/products/create', product)
      return data
    }
    catch (err){
      error.value = err
      throw err
    }
    finally{
      isLoading.value = false
    }
  };


    const updateProduct = async (id, product) => {
      isLoading.value = true
      try {
        const { data } = await api.put(`/products/edit/${id}`, product)
        return data
      } catch (err) {
        error.value = err
        throw err
      } finally {
        isLoading.value = false
      }
    };

   

  const fetchProducts = async () => {
    isLoading.value = true
    try {
      const { data } = await api.get('/products/get')
      products.value = data
    } finally {
      isLoading.value = false
    }
  };

    const getBestProducible = async () => {
    isLoading.value = true
    try {
      const { data } = await api.get('/products/available')
      producibles.value = data
    } finally {
      isLoading.value = false
    }
  };

   const getProductById = async (id) => {
      const { data } = await api.get(`/products/get/${id}`)
      return data
    };

   const deleteProduct = async (id) => {
  isLoading.value = true

  try {
    await api.delete(`/products/delete/${id}`)
  } catch (err) {
    error.value = err
    throw err
  } finally {
    isLoading.value = false
  }
}

 const existProduct = (name, currentId) => {
 
  if (!products.value) return false;

  return products.value.some(p => 
    (p.name || '').toLowerCase() === (name || '').toLowerCase() && 
    Number(p.id) !== Number(currentId)
  );
};




  return {
    products,
    producibles,
    isLoading,
    getProductById,
    error,
    fetchProducts,
    createProduct,
    updateProduct,
    deleteProduct,
    existProduct,
    getBestProducible
  }
})