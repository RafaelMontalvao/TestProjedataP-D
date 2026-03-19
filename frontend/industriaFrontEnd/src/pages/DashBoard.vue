

<template>
        <v-progress-linear 
    v-if="productStore.isLoading"
    indeterminate
    />

     <container-default v-if="!productStore.isLoading">

            <v-row>
                <v-col cols="12" sm="4" >
                    <v-card class="mx-auto text-center" max-width="300" max-height="200"  elevation="4" rounded="lg">
                        
                        <v-sheet color="amber-lighten-4" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center">
                            <v-icon color="amber-darken-3" class="mr-2">mdi-star</v-icon>
                            Top Revenue Product

                            </div>
                        </v-sheet>

                        <v-card-text class="py-2 bg-white text-black">
                            
                            <h3 class=" font-weight-bold mb-2">{{ sortedData[0]?.productName }}</h3>
                            

                            
                            <v-divider class="mb-4"></v-divider>

                            
                            <div class="d-flex flex-column gap-2">
                            <div>
                                <span class="text-caption text-grey-darken-1 d-block">TOTAL VALUE</span>
                                <span class="text-h5 font-weight-black text-green-darken-2">
                                R$ {{ sortedData[0]?.totalProductionValue.toLocaleString('pt-BR', {minimumFractionDigits: 2}) }}
                                </span>
                            </div>

                            <v-chip class="ma-2 align-self-center" color="blue-darken-2" variant="tonal" size="small">
                                <v-icon start icon="mdi-package-variant-closed"></v-icon>
                            {{ sortedData[0]?.producibleQuantity }} Units produced
                            </v-chip>
                            </div>
                        </v-card-text>
                    </v-card>
                </v-col>

                <v-col cols="12" sm="4">
                    <v-card class="mx-auto text-center" max-width="300" max-heigh="200" elevation="4" rounded="lg">
                        
                        <v-sheet color="blue-lighten-5" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center text-blue-darken-3">
                                <v-icon color="blue-darken-2" class="mr-2">mdi-calculator</v-icon>
                                Potential Inventory Yield
                            </div>
                        </v-sheet>

                        <v-card-text class="py-2 bg-white text-black">
                            
                            <h3 class=" font-weight-bold mb-2">Total Production Value</h3>
                            <v-divider class="mb-4"></v-divider>
                           
                            <div class="d-flex flex-column gap-2">
                                <div>
                                    <span class="text-caption text-grey-darken-1 d-block">ESTIMATED REVENUE</span>
                                    <span class="text-h5 font-weight-black text-blue-darken-3">
                                        
                                        R$ {{ sortedData.reduce((acc, item) => acc + item.totalProductionValue, 0).toLocaleString('pt-BR', {minimumFractionDigits: 2}) }}
                                    </span>
                                </div>

                                
                                <v-chip class="ma-2 align-self-center" color="green-darken-2" variant="tonal" size="small">
                                    <v-icon start icon="mdi-check"></v-icon>
                                    Maximum Stock Optimization
                                </v-chip>
                            </div>
                            </v-card-text>
                    </v-card>
                </v-col>

                    
                <v-col cols="12" sm="4">
                    <v-card class="mx-auto text-center" max-width="300" max-height="200" elevation="4" rounded="lg">
                       
                        <v-sheet color="deep-purple-lighten-5" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center text-deep-purple-darken-3">
                                <v-icon color="deep-purple-darken-2" class="mr-2">mdi-factory</v-icon>
                                Total Production Volume
                            </div>
                        </v-sheet>
                        <v-card-text class="py-2 bg-white text-black">
                           
                            <h3 class=" font-weight-bold mb-2">Manufacturing Capacity</h3>
                            <v-divider class="mb-4"></v-divider>
                            
                            <div class="d-flex flex-column gap-2">
                                <div>
                                    <span class="text-caption text-grey-darken-1 d-block">TOTAL UNITS</span>
                                    <span class="text-h5 font-weight-black text-deep-purple-darken-3">
                                      
                                        {{ sortedData.reduce((acc, item) => acc + item.producibleQuantity, 0).toLocaleString('pt-BR') }}
                                    </span>
                                </div>
                                <v-chip class="ma-2 align-self-center" color="deep-purple-darken-1" variant="tonal" size="small">
                                    <v-icon start icon="mdi-layers-triple"></v-icon>
                                    Estimated total items
                                </v-chip>
                            </div>
                        </v-card-text>
                    </v-card>


                    
                </v-col>
            </v-row>


              <v-card class="mt-5">
              <v-tabs v-model="activeTab" bg-color="transparent"  align-tabs="center" >
                <v-tab value="plan">Production Plan</v-tab>
                <v-tab value="chart">Analytics View</v-tab>
              </v-tabs>

              <v-card-text>
                <v-window v-model="activeTab">
                  <v-window-item value="plan">

                    <v-card  variant="outlined" elevation="2" class="pa-0">
                        <v-card-item>
                          <v-card-title class="d-flex align-center">
                            
                            Suggested Production Plan
                          </v-card-title>
                          <v-card-subtitle>Prioritized by maximum revenue</v-card-subtitle>
                        </v-card-item>

                        <v-divider></v-divider>

                        <v-list lines="two">
                          <v-list-item
                            v-for="(item, index) in sortedData"
                            :key="item.id"
                            :title="item.name"
                            :subtitle="'Suggested Quantity: ' + item.producibleQuantity + ' units'"
                          >
                            <template v-slot:prepend>
                              <v-avatar  color="green-lighten-3"  size="small" class="text-white">
                                {{ index + 1 }}
                              </v-avatar>
                            </template>

                            <template v-slot:append>
                              <div class="text-right">
                                <div class="text-success font-weight-bold">
                                  + {{item.totalProductionValue.toLocaleString('pt-BR', {minimumFractionDigits: 2})  }}
                                </div>
                                <small class="text-grey">Estimated Revenue</small>
                              </div>
                            </template>
                          </v-list-item>
                        </v-list>
                      </v-card>
                    
                  </v-window-item>

                  <v-window-item value="chart">

                    <v-row >
                      <v-col v-if="!productStore.isLoading" cols="12">
                      <apexchart 
                        v-if="series?.[0]?.data?.length"
                          type="bar" 
                          height="200" 
                          :options="chartOptions" 
                          :series="series"
                      ></apexchart>
                      </v-col>
                        <v-col cols="12" v-if="series[0].data.length === 0"style="padding: 20px; text-align: center;">
                          Analizando o estoque para calcular a rentabilidade...
                        </v-col>
                    </v-row>


                    
                  </v-window-item>
                </v-window>
              </v-card-text>
              </v-card>
              
        
        

        


   
    </container-default>

</template>

<script setup >
import ContainerDefault from '@/components/ContainerDefault.vue';
import { useProductsStore } from '@/stores/products';
import { onMounted, ref,watch } from 'vue';
import Products from './products/Products.vue';


const productStore = useProductsStore()
const sortedData = ref([])
const activeTab = ref('plan')


onMounted( ()=> {
    productStore.getBestProducible()
    productStore.fetchProducts()
})




const series = ref([{
  name: "Valor Total Produzível (R$)",
  data: []
}]);

const chartOptions = ref({
  chart: { type: 'bar', height: 200 },
  plotOptions: {
    bar: {
      borderRadius: 6,
      horizontal: true, 
      columnWidth: '30%',
      distributed: true, 
    }
  },
  colors: ['#00E396', '#008FFB', '#FEB019', '#FF4560', '#775DD0'],
  dataLabels: {
    enabled: true,
    formatter: (val) => ` ${val.toLocaleString('pt-BR')}`
  },
  xaxis: { categories: [] },
  title: { 
    text: 'Production Priority: Maximum Revenue with Current Inventory',
    align: 'center' 
  },
  xaxis: { 
    categories: [],
    labels: { rotate: -45, rotateAlways: true } 
  },

 
  yaxis: {
    tickAmount: 20,
    min: 0,
     
    labels: {
      formatter: (val) => `${val.toLocaleString('pt-BR')}`,
      style: { fontSize: '11px' }
    },
    title: { 
      text: 'Revenue Potential',
      style: { fontWeight: 600 }
    }
  },
  tooltip: {
    y: {
      formatter: (val, { dataPointIndex, w }) => {
       
        const item = sortedData.value[dataPointIndex]
        const qtd = item ? item.producibleQuantity : 0;
        return `Total: R$ ${val.toLocaleString('pt-BR')} (${qtd} units available)`;
      }
    }
  }
});


watch(() => productStore.producibles, (newItems) => {
  if (newItems && newItems.length > 0) {
    
    sortedData.value = [...newItems].sort((a, b) => 
      b.totalProductionValue - a.totalProductionValue
    );

    chartOptions.value = {
      ...chartOptions.value,
      xaxis: { categories: sortedData.value.map(i => i.productName) }
    };

    series.value = [{
      name: "Total Potential Revenue",
      data: sortedData.value.map(i => i.totalProductionValue)
    }
    // ,
    // {
    //   name:"Units to Produce",
    //   data: sortedData.value.map(i => i.producibleQuantity)
    // }
    
  ];
  }
});








</script>