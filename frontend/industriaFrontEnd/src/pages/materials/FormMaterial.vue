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
                 color="primary"
                 autofocus>
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6">

                <v-number-input
                label="Stock Quantity"
                v-model="form.stockQuantity"
                variant="outlined"
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
                @excluir="clickDeleteProduct()"
                />
            </template>
        </container-default>


</template>
<script setup>
import ContainerDefault from '@/components/ContainerDefault.vue';
import FooterActions from '@/components/FooterActions.vue';
import { useMaterialsStore } from '@/stores/materials';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRouter } from 'vuetify/lib/composables/router';


const route = useRoute()
const router = useRouter()
const store = useMaterialsStore()

const isLoading = ref(true)  
const materialId = ref(null)
const form = ref({
    id:null,
    name:'',
    stockQuantity: 0

})



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
        console.log('form',form)
    }  catch (err) {
    console.error('Erro ao carregar empreendimento:', err)
  } finally {
    isLoading.value = false
  }
})


const goBack = () => {
 
  if (window.history.state.back) {
    router.back()
  } else {
    console.log("click")
   
    router.push({ name: 'materials' }) 
  }
}

</script>