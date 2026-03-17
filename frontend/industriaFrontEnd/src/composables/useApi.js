import axios from "axios";

export function useApi(baseUrl) {
    const apiBaseUrl = baseUrl || import.meta.env.VITE_API_BASE_URL;

    const apiClient = axios.create({

        baseURL: apiBaseUrl,
        timeout: 30000,
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
    });

    apiClient.interceptors.response.use(
        (response) => response,
        (error) => {
            console.error("Erro na Requisição",error.response?.data || error.message);
            return Promise.reject(error);
            }
        );
        return apiClient;
          

    
}
 