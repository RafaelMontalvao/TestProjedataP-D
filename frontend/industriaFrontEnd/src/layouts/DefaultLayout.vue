<script setup lang="ts">
import router from '@/router';
import { ref } from 'vue';

const drawer = ref(true);

const items = [
    {
    title : 'Dashboard',
    name :'dashboard'
    },
    // {
    // title : 'Products',
    // name : 'products'
    // },
    {
     title: 'Materials',
    name: 'materials'
}
]
</script>
<!-- :to="{name:item.name}" -->
<template>
    <v-layout>
        <v-app-bar color="green-lighten-3"elevation="1">
            <v-app-bar-nav-icon @click.stop="drawer = !drawer">
            </v-app-bar-nav-icon>
            <v-toolbar-title class="text-h4">
               Projeto P&D
            </v-toolbar-title>
        </v-app-bar>
        <v-navigation-drawer v-model="drawer" app elevation="1">
            <v-list class="pl-1">
                <v-list-item v-for="item in items"
                :key="item.name"
                :title="item.title"
                :to="{name:item.name}"
                
                >
                </v-list-item>

            </v-list>
        </v-navigation-drawer>

        <v-main style="height: 100dvh;">
            <router-view/>
        </v-main>
    </v-layout>

</template>