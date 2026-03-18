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

                    <v-number-input
                    label="Stock Quantity"
                    v-model="form.stockQuantity"
                    variant="outlined"
                    hide-details="auto"
                    :min="0"
                    density="compact">
                    </v-number-input>
                </v-col>
            </v-row>
        </v-form>

         <template v-slot:footer>
                <FooterActions
                class="w-100" 
                :showDelete="materialId > 0"
                @salvar="clickSave()"
                @cancelar="goBack"
                @excluir="clickDelete()"
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
    stockQuantity: 0
})

const rules = {
    required: v => !!v || 'This field is required',

    uniqueName: v => {
    if (!v) return true
    return !store.existMaterial(v, form.value.id) || 'Material name is in use. Please choose another one.'
  }

}


const goBack = () => {
  console.log("Tentando voltar...");
  console.log("Histórico do Navegador (Length):", window.history.length);
  
  // O back() retorna uma Promise no Vue Router 4
  router.back();
  
  // Se após 100ms ele não saiu da página, algo bloqueou
  setTimeout(() => {
    if (route.name === 'formmaterials') {
      console.warn("router.back() falhou. Forçando push...");
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
         // 1. Validação de associação (igual você fez)
    if (form.value.products?.length > 0) {
            notification.error('Product has association')
            return
        }

        // 2. Chamar o dialog global e ESPERAR a resposta
        const confirmed = await notification.confirm(
            'Delete Material', 
            `Are you sure you want to delete ${form.value.name}?`
        )

        // 3. Se o usuário clicou em OK
        if (confirmed) {
                try {
                isLoading.value = true
                await store.deleteMaterial(materialId.value)
                notification.success('Material Deleted')
                router.back()
                } catch (err) {
                notification.error('Failed to delete')
                } finally {
                isLoading.value = false
                }
            }
        }
    





</script>