<template>
    <container-default :title="productId >0 ?'Edit Product' : 'New Product'" show-toolbar >
      <v-form   ref="formProduct" validate-on="blur" @submit.prevent>
        <v-row class="pt-0">
          <v-col cols="12" sm="4" >
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
          <v-col cols="12" sm=4>
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
          <v-col cols="12" sm="4"  >
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

             <v-empty-state v-if="form.materials.length<1"
              icon="mdi mdi-alert-outline"
              text="To proceed with product registration, please add at least one production material."
              title="No materials associated"
            ></v-empty-state>

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
              :min="0"
              :precision="2"
              :step="0.01"
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
            @salvar="clickSave"
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
import { useAssociationStore } from '@/stores/association';
import { useMaterialsStore } from '@/stores/materials';
import { useProductsStore } from '@/stores/products';
import { useRoute,useRouter } from 'vue-router';


const route = useRoute()
const router = useRouter()
const store = useProductsStore()
const storeMaterials = useMaterialsStore()
const storeAsccociation = useAssociationStore()
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
  
  if (!v || v.toString().trim() === "") return 'Price is required';

 
  const num = Number(v.toString().replace(',', '.'));

  
  return (num >= 0.01) || 'Value must be greater than 0.00';
},

  uniqueName: v => {
  if (!v) return true;
  try {
    const currentId = productId.value || 0;
    const alreadyExists = !!store.existProduct(v, currentId);

   
    if (alreadyExists) {
      return 'Product name is already in use.';
    }
    
    return true;

  } catch (error) {
    
    console.error("Erro na validação uniqueName:", error);
    return true; 
  }
},
 uniqueCode: v => {
    if (productId.value > 0) return true

    const exists = store.products.some(
      p => p.code?.toLowerCase() === v?.toLowerCase()
    )

    return !exists || 'Já existe produto com esse código'
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
const parseMoneyToBigDecimal = (value) => {
  if (!value) return 0

  return Number(
    value
      .replace(/\./g, '')
      .replace(',', '.')
  )
}

const getAvailableMaterials = (currentIndex) => {
  // 1. Pega todos os materiais do store
  const allMaterials = storeMaterials.materials || [];

  // 2. Proteção: se o formulário ou a lista não existem, retorna tudo
  if (!form.value || !form.value.materials) return allMaterials;

  // 3. Pega os IDs que já foram selecionados nas OUTRAS linhas
  const usedInOtherRows = form.value.materials
    .filter((item, index) => {
      // Filtra: não pode ser a linha atual E precisa ter um ID selecionado
      return index !== currentIndex && item.rawMaterialId;
    })
    .map(item => Number(item.rawMaterialId)); // Extrai apenas o ID numérico

  // 4. Retorna apenas os materiais que NÃO estão na lista de usados
  return allMaterials.filter(mat => !usedInOtherRows.includes(Number(mat.id)));
}

const addMaterialRow = () => {
    if (!form.value) {
    form.value = { name: '', code: '', price: '', materials: [] };
  }
  if (!form.value.materials) {
    form.value.materials = [];
  }
 
  const totalAvailable = storeMaterials.materials?.length || 0;
  const currentRows = form.value.materials.length;

   if (currentRows >= totalAvailable) {
  
    notification.warn("You have already added all available materials.");
   return; 
   }

  // 3. Se passou na validação, adiciona a linha
  form.value.materials.push({
    rawMaterialId: null,
    quantityNeeded: 0, // Dica: mude de 0 para 1 para facilitar pro usuário
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


async function clickSave(){

  try{

  const { valid } = await formProduct.value.validate();

  if(form.value.materials.length<1){
   notification.warn("Please add at least one material to proceed.");
    console.log('Tentativa de salvar sem materiais');
    return; 
  }
  
  if (!valid) return;

  const { materials, ...productData } = form.value;
  const formSend = {
    ...productData, price: parseMoneyToBigDecimal(form.value.price)
  }
  let savedProduct = null

  if(productId.value > 0){
    await store.updateProduct(productId.value, formSend)
    console.log('produto atualizado')
  }else{
    savedProduct = await store.createProduct(formSend)
    console.log('produto salvo com sucesso')
  }
  const currentProductId = productId.value || savedProduct?.id

  if(!currentProductId){
    throw new Error("Product Not Found")
  }

  console.log('current productID', currentProductId)
  const savedProductMaterials = (materials || [] )
    .filter(item => item.rawMaterialId !== null)
    .map(item => ({
      productId: currentProductId,
      rawMaterialId : item.rawMaterialId,
      quantityNeeded: item.quantityNeeded
    }));

    await storeAsccociation.associationProductMaterial(currentProductId,savedProductMaterials)

    setTimeout(()=> {goBack()},1000)
  }catch (error) {
    // Tratamento de erro detalhado com a sua lógica de notificações
    const msg =
      error?.response?.data?.erros?.[0] ||
      error?.response?.data?.message ||
      error?.message ||
      'Erro ao salvar produto';

    notification.error(msg);
    console.error("Erro no salvamento:", error);
  } finally {
    isLoading.value = false;
  }
}






</script>