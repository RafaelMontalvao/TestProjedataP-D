<template>


    <container-default :title="materialId>0 ? 'Edit Material' : 'New Material'" show-toolbar >
        <v-form  class="mt-4" ref="formMAterial" validate-on="blur" @submit.prevent>
            <v-row>
                <v-col cols="12" sm="6">
                    <v-text-field
                    label="Material Name"
                    v-model="form.name"
                    variant="outlined"
                    density="compact"
                    :rules="[rules.required, rules.uniqueName]"
                    hide-details="auto"
                    placeholder="Ex: Aluminum Sheet 2mm"
                    persistent-placeholder

                    autofocus>
                    </v-text-field>
                </v-col>
                <v-col cols="12" sm="6">

                    <v-text-field
                    label="Stock Quantity"
                    v-model.number="form.stockQuantity"
                    variant="outlined"
                    hide-details="auto"
                    type="number"
                    :min="0"
                    :precision="2"
                    :step="0.01"
                    density="compact">
                    </v-text-field>
                </v-col>
            </v-row>
        </v-form>

         <template v-slot:footer>
                <FooterActions
                class="w-100" 
                :showDelete="materialId > 0"
                @salvar="clickSave"
                @cancelar="goBack"
                @excluir="clickDelete"
                />
        </template>
        </container-default>


</template>
<script setup>
import ContainerDefault from '@/components/ContainerDefault.vue';
import FooterActions from '@/components/FooterActions.vue';
import { useMaterialsStore } from '@/stores/materials';
import { onMounted, ref } from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { useNotification } from '@/composables/useNotification';


const route = useRoute()
const router = useRouter()
const store = useMaterialsStore()
const notification = useNotification()

const isLoading = ref(true)  
const materialId = ref(null)
const formMAterial = ref(null);
const form = ref({
    id:null,
    name:'',
    stockQuantity: null
})

const rules = {
    required: v => !!v || 'This field is required',

    uniqueName: v => {
    if (!v) return true
    return !store.existMaterial(v, form.value.id) || 'Material name is in use. Please choose another one.'
  }

}


const goBack = () => {
 
  router.back(); 
  setTimeout(() => {
    if (route.name === 'formmaterials') {      
       router.push({ name: 'materials' });
    }
  }, 100);
}


onMounted(async () => {

    try {
        materialId.value = Number(route.params.id)
        console.log('id recebeido', route.params.id)
        
        if (materialId.value>0){
            const material = await store.getMaterialsById(materialId.value)

            if(material){
                Object.assign(form.value, material)
            }
        }
       
    }  catch (err) {
    console.error('Erro ao carregar empreendimento:', err)
  } finally {
    isLoading.value = false
  }
})


async function clickSave(){
     try{
    
        const {valid} = await formMAterial.value.validate()
        if(!valid) return
   
        const formSend = {...form.value}
        if (materialId.value>0){
            await store.updateMaterial( materialId.value,formSend)
            console.log('form edit', formSend )
            notification.success('Material updtated')
        }else{
            await store.createMaterial(formSend)
            notification.success('Material Created')
            console.log('novo', form)
        }
        console.log('tentando voltar')
        setTimeout(() => {
             goBack();
            }, 500)
                
           
    }catch (error){
            const msg = 
                    error?.response?.data?.erros?.[0] ||
                     error?.response?.data?.message ||
                        error?.message ||
                    
                    'Failed to save Material'
                    notification.error(msg)
                    
    }finally{
        isLoading.value = false
       
    }
}

async function clickDelete() {
        
    if (form.value.products?.length > 0) {
            notification.error('Product has association')
            return
        }

       
        const confirmed = await notification.confirm(
            'Delete Material', 
            `Are you sure you want to delete ${form.value.name}?`
        )

      
        if (confirmed) {
                try {
                isLoading.value = true
                await store.deleteMaterial(materialId.value)
                notification.success('Material Deleted')
                goBack()
                } catch (err) {
                const backendMessage = err.response?.data?.erros?.[0]
                notification.error(backendMessage || 'Failed to delete material')
                } finally {
                isLoading.value = false
                }
            }
        }
    





</script>