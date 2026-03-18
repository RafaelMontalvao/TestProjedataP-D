
import { defineStore } from "pinia"
import { ref } from "vue"
import { useApi } from "@/composables/useApi"



export const useAssociationStore = defineStore('association',()=>{
    const api = useApi()
    const associations = ref([])
    const isLoading = ref(false)
    const error = ref(null)


    const associationProductMaterial = async(currentProductId,productMaterial)=>{
        console.log('currentID', currentProductId)
    isLoading.value = true
     try{
      const {data} = await api.post(`/product_material/${currentProductId}/materials`, productMaterial)
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




    const deleteAssociation = async(id) =>{

        isLoading = true
        try{
            await api.delete(`/product_material/delete/${id}`)
        }
        catch(err){
            error.value =  err
            throw err
        }
        finally{
            isLoading.value = false
        }
    }




    return{
        associations,
        isLoading,
        error,
        deleteAssociation,
        associationProductMaterial
    }

})