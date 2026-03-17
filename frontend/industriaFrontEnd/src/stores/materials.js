import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useApi } from '@/composables/useApi'

export const useMaterialsStore = defineStore('materials', () => {

  const api = useApi() 
  const materials = ref([])
  const isLoading = ref(false)
  const error = ref(null)

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

  return {
    materials,
    isLoading,
    getMaterialsById   ,
    error,
    fetchMaterials
  }
})