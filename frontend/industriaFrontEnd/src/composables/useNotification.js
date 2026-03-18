const show = ref(false)
const message = ref('')
const color = ref('success')
const timeout = ref(5000)


const showDialog = ref(false)
const dialogTitle = ref('')
const dialogMessage = ref('')
let resolvePromise = null // Aqui guardamos a "promessa" de resposta

export function useNotification (){


    const success = (msg, duration = 5000) =>{
        message.value = msg
        color.value = 'success'
        timeout.value = duration
        show.value = true
    }

    const error = (msg, duration = 5000) =>{
        message.value = msg
        color.value = 'error'
        timeout.value = duration
        show.value  = true

    }

    const warn = (msg, duration =5000) => {
        message.value = msg
        color.value = 'warning'
        timeout.value = duration
        show.value = true
    }
    // Função para chamar o Dialog de qualquer lugar
    const confirm = (title, msg) => {
        dialogTitle.value = title
        dialogMessage.value = msg
        showDialog.value = true
        
        // Criamos uma promessa que só "resolve" quando o usuário clica nos botões
        return new Promise((resolve) => {
            resolvePromise = resolve
        })
    }

    const handleConfirm = (value) => {
        showDialog.value = false
        if (resolvePromise) resolvePromise(value) // Retorna true ou false
    }

    return {
        success,
        error,
        warn,
        confirm,
        handleConfirm,
        show,
        color,
        timeout,
        message,
        dialogTitle,
        showDialog,
        dialogMessage,
        resolvePromise

    }
}