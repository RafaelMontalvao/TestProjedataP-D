

<template>
        <v-progress-linear 
    v-if="productStore.isLoading"
    indeterminate
    />

     <container-default v-if="!productStore.isLoading">

            <v-row>
                <v-col cols="4" >
                    <v-card class="mx-auto text-center" max-width="300" elevation="4" rounded="lg">
                        <!-- Cabeçalho de Destaque -->
                        <v-sheet color="amber-lighten-4" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center">
                            <v-icon color="amber-darken-3" class="mr-2">mdi-star</v-icon>
                            Top Revenue Product

                            </div>
                        </v-sheet>

                        <v-card-text class="py-6 bg-white text-black">
                            <!-- Nome do Produto -->
                            <h3 class="text-h6 font-weight-bold mb-4">{{ sortedData[0]?.productName }}</h3>
                            
                            <v-divider class="mb-4"></v-divider>

                            <!-- Valores -->
                            <div class="d-flex flex-column gap-2">
                            <div>
                                <span class="text-caption text-grey-darken-1 d-block">TOTAL VALUE</span>
                                <span class="text-h5 font-weight-black text-green-darken-2">
                                R$ {{ sortedData[0]?.totalProductionValue.toLocaleString('pt-BR', {minimumFractionDigits: 2}) }}
                                </span>
                            </div>

                            <v-chip class="ma-2 align-self-center" color="blue-darken-2" variant="tonal" size="small">
                                <v-icon start icon="mdi-package-variant-closed"></v-icon>
                            {{ sortedData[0]?.producibleQuantity }} unidades produzidas
                            </v-chip>
                            </div>
                        </v-card-text>
                    </v-card>
                </v-col>

                <v-col cols="4">
                    <v-card class="mx-auto text-center" max-width="300" elevation="4" rounded="lg">
                        
                        <v-sheet color="blue-lighten-5" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center text-blue-darken-3">
                                <v-icon color="blue-darken-2" class="mr-2">mdi-calculator</v-icon>
                                Potential Inventory Yield
                            </div>
                        </v-sheet>

                        <v-card-text class="py-6 bg-white text-black">
                            
                            <h3 class="text-h6 font-weight-bold mb-4">Total Production Value</h3>
                            <v-divider class="mb-4"></v-divider>
                           
                            <div class="d-flex flex-column gap-2">
                                <div>
                                    <span class="text-caption text-grey-darken-1 d-block">ESTIMATED REVENUE</span>
                                    <span class="text-h5 font-weight-black text-blue-darken-3">
                                        
                                        R$ {{ sortedData.reduce((acc, item) => acc + item.totalProductionValue, 0).toLocaleString('pt-BR', {minimumFractionDigits: 2}) }}
                                    </span>
                                </div>

                                <!-- Badge de Eficiência -->
                                <v-chip class="ma-2 align-self-center" color="green-darken-2" variant="tonal" size="small">
                                    <v-icon start icon="mdi-check"></v-icon>
                                    Maximum Stock Optimization
                                </v-chip>
                            </div>
                            </v-card-text>
                    </v-card>
                </v-col>

                    
                <v-col cols="4">
                    <v-card class="mx-auto text-center" max-width="300" elevation="4" rounded="lg">
                       
                        <v-sheet color="deep-purple-lighten-5" class="pa-2">
                            <div class="text-overline font-weight-black d-flex align-center justify-center text-deep-purple-darken-3">
                                <v-icon color="deep-purple-darken-2" class="mr-2">mdi-factory</v-icon>
                                Total Production Volume
                            </div>
                        </v-sheet>
                        <v-card-text class="py-6 bg-white text-black">
                           
                            <h3 class="text-h6 font-weight-bold mb-4">Manufacturing Capacity</h3>
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

                    
        

                <v-row >
                <v-col v-if="!productStore.isLoading" cols="12">
                <apexchart 
                 v-if="series?.[0]?.data?.length"
                    type="bar" 
                    height="350" 
                    :options="chartOptions" 
                    :series="series"
                ></apexchart>
                </v-col>
                </v-row>
                <v-col cols="12" v-if="series[0].data.length === 0"style="padding: 20px; text-align: center;">
                Analizando o estoque para calcular a rentabilidade...
                </v-col>

        


   
    </container-default>

</template>

<script setup >
import ContainerDefault from '@/components/ContainerDefault.vue';
import { useProductsStore } from '@/stores/products';
import { onMounted, ref,watch } from 'vue';
import Products from './products/Products.vue';


const productStore = useProductsStore()
const sortedData = ref([])



onMounted( ()=> {
    productStore.getBestProducible()
    productStore.fetchProducts()
})




const series = ref([{
  name: "Valor Total Produzível (R$)",
  data: []
}]);

const chartOptions = ref({
  chart: { type: 'bar', height: 350 },
  plotOptions: {
    bar: {
      borderRadius: 6,
      horizontal: true, 
      columnWidth: '55%',
      distributed: true, 
    }
  },
  colors: ['#00E396', '#008FFB', '#FEB019', '#FF4560', '#775DD0'],
  dataLabels: {
    enabled: true,
    formatter: (val) => `R$ ${val.toLocaleString('pt-BR')}`
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
      formatter: (val) => `R$ ${val.toLocaleString('pt-BR')}`,
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
    
    // Ordenamos e SALVAMOS na nossa ref local
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
    }];
  }
});








</script>