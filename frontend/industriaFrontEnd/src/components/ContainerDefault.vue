

<template>
  <v-toolbar 
      v-if="showToolbar" 
      
      density="compact"
      elevation="1"
      class="mb-4 bg-transparent"
    >
      <v-toolbar-title>
        <v-icon size="small" icon="mdi-arrow-left" class="mr-2" v-tooltip:start="'Voltar'" @click="goBack"></v-icon>
        {{ title }}
      </v-toolbar-title>
      
      <v-spacer></v-spacer>
      
      <slot name="actions"></slot>
    </v-toolbar>
 <v-container fluid class="pa-6"  style="height:100%; overflow-y:auto;">


    <slot class="flex-grow-1"></slot>


    <v-footer v-if="$slots.footer" app color="white" style="position: absolute;" >
       <slot name="footer"></slot>
    </v-footer>
   
  </v-container>

</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  // Verifica se existe histórico no navegador
  if (window.history.state.back) {
    router.back()
  } else {
    console.log("click")
    // Se não tiver para onde voltar, manda para a Dashboard
    router.push({ name: 'dashboard' }) 
  }
}
defineProps({
  // Título que será exibido
  title: {
    type: String,
    default: 'Página'
  },
  // Controle de visibilidade
  showToolbar: {
    type: Boolean,
    default: false
  }
})
</script>