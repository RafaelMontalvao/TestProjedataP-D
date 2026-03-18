<script setup>
import { onMounted } from 'vue';
import { useProductsStore } from '@/stores/products';
import ContainerDefault from '@/components/ContainerDefault.vue';
import router from '@/router';



const productStore = useProductsStore()
const headers = [
    { title: 'Product Name', value: 'name' },
    { title: 'Product Code', value: 'code',align:'end' },
    { title: 'Product Price', value: 'price', align:'end' },
    { title: 'Materials Needs', value: 'materials', align:'start' },
    { title: '', value: 'edit' }
  ];


onMounted( ()=> {
 productStore.fetchProducts()

})



  function formatPrice(value) {
  if (value == null) return '0,00'
  
  return Number(value).toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
}

function clickForm(id){
   return router.push(`/products/form_product/${id}`)

}

</script>
 
<template>
    <v-progress-linear 
    v-if="productStore.isLoading"
    indeterminate
    />

    <container-default v-if="!productStore.isLoading">
        <v-row>
        <v-col>
          <v-btn    
            size="small" 
            color="green-lighten-3" 
            class="float-right" 
            variant="elevated"
            rounded="xl"
            prepend-icon="mdi-plus"
            @click="clickForm(0)"
             
            >New Product</v-btn>
        </v-col>
      </v-row>

       <v-row>
        <v-col>
            <v-data-table
              :headers="headers"
              elevation="2"
              hover
                mobile-breakpoint="sm"
              :items="productStore.products"
              item-key="name"
              :disable-sort="true"
              :items-per-page="-1"
              hide-default-footer>

            <template v-slot:item.edit="{ item }">
              <v-icon icon="mdi-pencil-outline"size="x-small"  color="grey-darken-2" @click="clickForm(item.id)" v-tooltip="'Editar'"></v-icon>
            </template>

               <template v-slot:item.price="{ item }">
               {{ formatPrice(item.price) }}
            </template>

            <template v-slot:item.materials="{ item }">
              <span v-for="material in item.materials?.slice(0,3)" :key="material.id">
                <v-chip label size="x-small" density="comfortable" variant="outlined" color="secondary" class="ml-1">{{material.rawMaterialName}} - {{ material.quantityNeeded }} unid.</v-chip>
              </span>
              

          
              <v-chip  v-if="item.materials?.length > 3" variant="outlined" label class="ml-1" color="secondary" density="comfortable" size="x-small" >
                  <v-tooltip :text="item.materials?.slice(3).map(m => `${m.rawMaterialName} - ${m.quantityNeeded} unid.`).join(', ')" activator="parent"
                              location="bottom"></v-tooltip>
                              {{ `+${item.materials?.length - 3} ` }}
              </v-chip> 

            </template> 

             </v-data-table>

        </v-col>
       </v-row>


    </container-default>
    
</template>