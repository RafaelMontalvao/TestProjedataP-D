
<template>
  <v-app>
    <router-view />
    <v-snackbar v-model=" notification.show.value"
      :color="notification.color.value"
      vertical
      :timeout = "notification.timeout.value">
      {{ notification.message.value }}
    </v-snackbar>

    <v-dialog v-model="notification.showDialog.value"persistent width="300"  >
      <v-card class=" text-center " >
       <v-card-text>{{ notification.dialogMessage.value }}</v-card-text>
        <v-card-actions class="justify-center mt-4">
          <v-btn color="grey-darken-2"variant="outlined" @click="notification.handleConfirm(false)">Cancel</v-btn>
          <v-btn color="error" variant="elevated" @click="notification.handleConfirm(true)">Confirm</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
  
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
import {useNotification} from'@/composables/useNotification';


const notification = useNotification() 
</script>


