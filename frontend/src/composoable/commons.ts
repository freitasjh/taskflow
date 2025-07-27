import type { AxiosError } from "axios";
import type { SweetAlert2 } from "sweetalert2";
import { inject } from "vue";
import { useLoading } from "vue-loading-overlay";

// Interface para os erros de validação retornados pela API
interface ValidationError {
    message: string;
}

// Interface para a resposta de erro da API
interface ApiErrorResponse {
    msg: string;
    errors?: ValidationError[];
}

// Interface para erros do Axios, tipando corretamente o response.data
interface CustomAxiosError extends AxiosError<ApiErrorResponse> {}

export function useLoader() {
    const $loading = useLoading({});
    let loader: any;
    let isLoading = false;

    const showLoading = () => {
        if (!isLoading) {
            loader = $loading.show({
                opacity: 0.3,
                color: "#8c57ff",
            });
            isLoading = true;
        }
    };

    const hideLoading = () => {
        if (isLoading) {
            loader.hide();
            isLoading = false;
        }
    };

    return { showLoading, hideLoading };
}

// Composable
export function useHandlerMessage() {
    // Injetar SweetAlert2 com tipagem
    const swal = inject<SweetAlert2>("$swal");

    // Verificar se swal foi injetado corretamente
    if (!swal) {
        throw new Error("SweetAlert2 ($swal) não foi injetado corretamente.");
    }

    // Manipula erros de validação
    const handlerErrorValidation = (error: CustomAxiosError): void => {
        let errorMessage = "";
        if (error.response?.data?.errors) {
            errorMessage = error.response.data.errors
                .map((err: ValidationError) => `<p><b>${err.message}</b></p>`)
                .join("");
        }

        // Exibir mensagem de erro com Swal
        swal.fire({
            icon: "error",
            title: error.response?.data?.msg ?? "Erro de Validação",
            html: errorMessage,
        });
    };

    // Manipula erros genéricos
    const handlerError = (error: CustomAxiosError | string): void => {
        let message = "";

        if (typeof error === "string") {
            message = error;
        } else if (
            error.response &&
            error.response.data &&
            error.response.data.msg
        ) {
            if (
                error.response.data.errors &&
                Array.isArray(error.response.data.errors)
            ) {
                handlerErrorValidation(error);
                return;
            } else {
                message = error.response.data.msg;
            }
        } else {
            message = error.message || "Erro desconhecido";
        }

        // Exibir mensagem de erro com Swal
        swal.fire({
            icon: "error",
            title: "Oops...",
            text: message,
        });
    };

    // Manipula notificações de sucesso (toast)
    const handlerToastSuccess = (message: string): void => {
        const Toast = swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast: HTMLElement) => {
                toast.onmouseenter = swal.stopTimer.bind(swal);
                toast.onmouseleave = swal.resumeTimer.bind(swal);
            },
        });

        Toast.fire({
            icon: "success",
            title: message,
        });
    };

    // Manipula modais de sucesso
    const handlerModalSuccess = (message: string): void => {
        swal.fire({
            title: "Success",
            text: message,
            icon: "success",
        });
    };

    return {
        handlerError,
        handlerErrorValidation,
        handlerToastSuccess,
        handlerModalSuccess,
    };
}
