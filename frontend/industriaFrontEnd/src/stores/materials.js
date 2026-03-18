import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useApi } from '@/composables/useApi'

export const useMaterialsStore = defineStore('materials', () => {

  const api = useApi() 
  const materials = ref([])
  const isLoading = ref(false)
  const error = ref(null)



  const createMaterial = async(material)=>{
    isLoading.value = true
     try{
      const {data} = await api.post('/raw_material/create', material)
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




const updateMaterial = async (id, material) => {
      isLoading.value = true
      try {
        const { data } = await api.put(`/raw_material/edit/${id}`, material)
        return data
      } catch (err) {
        error.value = err
        throw err
      } finally {
        isLoading.value = false
      }
    };


  const getMaterialsById = async (id) => {
  const { data } = await api.get(`/raw_material/get/${id}`)
  return data
    }

  const fetchMaterials = async () => {
    isLoading.value = true
    try {
      const { data } = await api.get('/raw_material/get')
      materials.value = data
    } finally {
      isLoading.value = false
    }
  }

   const deleteMaterial = async (id) => {
    isLoading.value = true

    try {
      await api.delete(`/raw_material/${id}`)
    } catch (err) {
      error.value = err
      throw err
    } finally {
      isLoading.value = false
    }
  }

  

  const existMaterial = (name, id = null) => {return materials.value.some(e => 
    e.name?.toLowerCase() === name.toLowerCase() &&
    e.id !== id
  )
}

  return {
    isLoading,
    error,
    materials,
    createMaterial,
    updateMaterial,
    fetchMaterials,
    getMaterialsById, 
    deleteMaterial,
    existMaterial
  }
})