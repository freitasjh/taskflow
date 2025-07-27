import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import type { App } from 'vue';

const http: AxiosInstance = axios.create({
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
});

http.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token: string | null = localStorage.getItem('token');
        if (token) {
            config.headers = config.headers || {};
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

http.interceptors.response.use(
    (response: AxiosResponse) => response,
    (error: AxiosError) => {
        console.log(error);
        if (error.response?.status === 403 || error.response?.data === undefined) {
            localStorage.clear();
            window.location.reload();
        } else {
            return Promise.reject(error);
        }
    }
);

export default function (app: App) {
  app.use(http)
}

export { http };
