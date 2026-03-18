<template>
    <container-default :title="productId >0 ?'Edit Product' : 'New Product'" show-toolbar >
      <v-form   ref="formProduct" validate-on="blur" @submit.prevent>
        <v-row class="pt-0">
          <v-col cols="12" >
              <v-text-field
              v-model="form.name"
              label="Product Name"
              variant="outlined"
              density="compact"
              placeholder="ex: e.g., Stainless Steel Bolt 10mm (Pack 50)"
              persistent-placeholder
              hide-details="auto"
              :rules="[rules.required,rules.uniqueName]">
          </v-text-field>
          </v-col>

          <v-col cols="12">
              <v-text-field
                  v-model="form.code"
                  label="Product Code"
                  variant="outlined"
                  density="compact"
                  placeholder="ex: e.g., SKU-100-BLU"
                  persistent-placeholder
                  hide-details="auto"
                  :rules="[rules.required,rules.uniqueCode]"
              ></v-text-field>
          </v-col>
          <v-col cols="12"  >
              <v-text-field
                  v-model="form.price"
                  label="Product Price"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  :rules="[rules.greaterThanZero]"
                  placeholder="ex: 1.223,50"
                  persistent-placeholder
                  @input="onPriceInput">
              </v-text-field>
          </v-col>
          <v-col cols="12">
            <v-btn 
              color="primary" 
              prepend-icon="mdi-plus" 
              variant="tonal"
              class="float-right" 
              size="small"
              rounded="xl"
              @click="addMaterialRow"
            >
              Add Material
            </v-btn>
          </v-col>
          <v-col cols="12">

        <v-card v-if="form.materials.length>0" elevation="1" class="overflow-auto pb-2 mb-2" height="auto">
           
          <v-row v-for="(item,index) in form.materials" :key="index "  class="px-4 mt-3" >
             
          <v-col cols="6">
            <v-autocomplete
              v-model="item.rawMaterialId"
              :items="getAvailableMaterials(index)" 
              item-title="name"
              item-value="id"
              :rules="[rules.required]"
              hide-details="auto"
              label="Select Material"
              density="compact"
              variant="outlined"
              
            ></v-autocomplete>
          </v-col>  

          <v-col cols="6">
            <v-text-field
              v-model="item.quantityNeeded"
              label="Quantity"
              type="number"
              :rules="[rules.required]"
              hide-details="auto"
              density="compact"
              variant="outlined"
              
              
            >
            <template #append>

              <v-icon icon="mdi-delete-outline" size="x-large" @click="form.materials.splice(index, 1)"></v-icon>


            </template>
          </v-text-field>
          </v-col> 

           <!-- <v-col cols="2"  class="d-flex aling-start pa-0">
            <v-icon 
              
              color="error" 
              variant="text" 
              size="large"
              @click="form.materials.splice(index, 1)"
            ></v-icon>
          </v-col> -->
         

        </v-row>
        </v-card>

          </v-col>
            
        </v-row>
      </v-form>

      

    

            


         <template v-slot:footer>
            <FooterActions
            class="w-100" 
            :showDelete="productId > 0"
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
import { useNotification } from '@/composables/useNotification';
import { useMaterialsStore } from '@/stores/materials';
import { useProductsStore } from '@/stores/products';
import { useRoute,useRouter } from 'vue-router';


const route = useRoute()
const router = useRouter()
const store = useProductsStore()
const storeMaterials = useMaterialsStore()
const notification = useNotification()
const isLoading = ref(true)

const productId = ref(null)
const formProduct = ref(null)
const form = ref({
  name: '',
  code: '',
  price: '',
  materials: []
}) 




const rules = {
  required: v => !!v ||  'This field is required',
  min3: v => v?.length >= 3 || 'Min 3 caracteres',

    greaterThanZero: v => {
  // 1. Se não houver valor (vazio ou undefined), exibe mensagem de obrigatório
  if (!v || v.toString().trim() === "") return 'Price is required';

  // 2. Converte para número tratando possíveis vírgulas (caso o input seja text)
  const num = Number(v.toString().replace(',', '.'));

  // 3. Valida se é um número válido e se é maior/igual a 0.01
  return (num >= 0.01) || 'Value must be greater than 0.00';
},

  uniqueName: v => {
    if (!v) return true
    return !store.existProduct(v, form.value.id) || 'Material name is in use. Please choose another one.'
  }
}



onMounted(async () => {
  try {
    productId.value = Number(route.params.id)
    storeMaterials.fetchMaterials()

    console.log('ID recebido:', productId.value)

    if (productId.value > 0) {
      const product = await store.getProductById(productId.value)

      if (product) {
        Object.assign(form.value, {
          name: product.name ?? '',
          code: product.code ?? '',
          price: product.price? Number(product.price).toLocaleString('pt-BR', {minimumFractionDigits: 2,maximumFractionDigits: 2}): '',
          materials: product.materials ?? []
        })
      }
    }

  } catch (err) {
    console.error('Erro ao carregar produto:', err)
  } finally {
    isLoading.value = false
  }
})

const onPriceInput = (e) => {
  // pega só números
  let numbers = e.target.value.replace(/\D/g, '')

  // limita até 8 dígitos → 99999999 (999.999,99)
  numbers = numbers.slice(0, 8)

  const value = Number(numbers) / 100

  form.value.price = value.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
}

const getAvailableMaterials = (currentIndex) => {
  const allMaterials = storeMaterials.materials || [];
  if (!form.value.materials) return allMaterials;

  const usedInOtherRows = form.value.materials
    .filter((_, index) => index !== currentIndex) 
    .filter(id => id > 0); 

  
  return allMaterials.filter(mat => !usedInOtherRows.includes(Number(mat.id)));
}

const addMaterialRow = () => {
  // 1. Garantia de que o objeto existe
  if (!form.value) {
    form.value = { name: '', code: '', price: '', materials: [] };
  }
  if (!form.value.materials) {
    form.value.materials = [];
  }

  // 2. A TRAVA: Só adiciona se o número de linhas for menor que o total de materiais
  const totalAvailable = storeMaterials.materials?.length || 0;
  const currentRows = form.value.materials.length;

  // if (currentRows >= totalAvailable) {
  //   // Opcional: Avisar o usuário que não há mais materiais diferentes para adicionar
  //   notification.warn("You have already added all available materials.");
  //   return; 
  // }

  // 3. Se passou na validação, adiciona a linha
  form.value.materials.push({
    rawMaterialId: null,
    quantityNeeded: 1, // Dica: mude de 0 para 1 para facilitar pro usuário
  });
};



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





</script>