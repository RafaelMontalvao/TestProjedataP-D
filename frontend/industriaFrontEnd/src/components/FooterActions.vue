<script setup>
        const props = defineProps({
        showDelete: {
            type: Boolean,
            default: true
        },
        drawer: {
            type: Boolean,
            default: false
        }
        })

        const emit = defineEmits([
        'salvar',
        'cancelar',
        'excluir'
        ])
        </script>

    <template>
        <v-footer density="compact" style="min-height: 48px !important; height: 48px !important;" class="bg-transparente"  >
            <v-toolbar density="compact" width="100%"  class=" bg-transparent pr-0 pb-4">
            <v-btn color="error" variant="elevated" size="large" density="comfortable" rounded="xl"  v-if="showDelete"  @click="$emit('excluir')">Excluir</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="grey" variant="elevated" size="large" density="comfortable"rounded="xl" @click="$emit('cancelar')">Cancelar</v-btn>
            <v-btn color="success" class="ml-2" size="large" density="comfortable"rounded="xl"variant="elevated"  @click="$emit('salvar')">Salvar</v-btn>
        </v-toolbar>


        </v-footer>
         
              
    
    </template>
