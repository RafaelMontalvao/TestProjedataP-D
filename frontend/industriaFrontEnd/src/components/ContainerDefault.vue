

<template>
  <v-toolbar 
      v-if="showToolbar" 
      color="white" 
      elevation="1"
      class="mb-4"
    >
      <v-toolbar-title class="text-h6 font-weight-bold color-primary">
        {{ title }}
      </v-toolbar-title>
      
      <v-spacer></v-spacer>
      
      <slot name="actions"></slot>
    </v-toolbar>
 <v-container fluid class="pa-4"  style="height:100%; overflow-y:auto;">


    <slot></slot>
   
  </v-container>

</template>

<script setup>
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