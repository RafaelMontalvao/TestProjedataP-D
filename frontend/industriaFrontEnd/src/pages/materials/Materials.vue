<script setup>
import { useMaterialsStore } from '@/stores/materials';  
import ContainerDefault from '@/components/ContainerDefault.vue';
import router from '@/router';


  const name = ref("Materials")

  const materialStore = useMaterialsStore()

  const headers = [
    { title: 'Material Name', value: 'name' },
    { title: 'Stock Quantity', value: 'stockQuantity' },
    { title: '', value: 'edit' }
  ];

  
onMounted(() => {
  materialStore.fetchMaterials();
})

function clickForm(id){
  return router.push(`/form_material/${id}`)

}


</script>

<template>

    <v-progress-linear
      v-if="materialStore.isLoading"
      indeterminate/> 

    

    <container-default v-if="!materialStore.isLoading">

    <v-row>
      <v-col>
        <v-btn    
           @click="clickForm(0)"
          size="small" 
          color="primary" 
          class="float-right" 
          prepend-icon="mdi-plus"
          
        >New Material
      </v-btn>
      </v-col>
    </v-row>

     <v-row>
        <v-col>
            <v-data-table
              :headers="headers"
              elevation="2"
              hover
              :items="materialStore.materials"
              item-key="name"
              hide-default-footer>

            <template v-slot:item.edit="{ item }">
              <v-icon icon="mdi-pencil-outline"size="small"  color="secondary" @click="clickForm(item.id)"></v-icon>
            </template>
          </v-data-table>

        </v-col>
       </v-row>



    

    </container-default>
  
     

  
   
  
</template>
