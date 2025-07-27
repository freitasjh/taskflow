<script lang="ts" setup>
import { useEmployeeStore } from "@/administrator/employee/store/employeeStore";
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import Employee from "../../model/employee";
import EmployeeCad from "../view/employee-cad.vue";

const employeeStore = useEmployeeStore();
const pageEmployee = computed(() => employeeStore.employeePage);
const { handlerError } = useHandlerMessage();
const isDialogVisible = ref(false);
const isTableLoading = ref(false);

const { showLoading, hideLoading } = useLoader();

const headers = [
    { title: "ID", key: "id" },
    { title: "Nome", key: "name" },
    { title: "E-mail", key: "email" },
    { title: "Telefone", key: "phone" },
    { title: "Celular", key: "cellphone" },
    { title: "CPF", key: "federalId" },
    { title: "Actions", key: "actions" },
];

onBeforeMount(async () => {
    employeeStore.clearState(); //Limpa o estado sempre que entar na tela de lista de employee.

    await findByFilter();
});

const findByFilter = async () => {
    try {
        isTableLoading.value = true;
        await employeeStore.findByFilter();
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        isTableLoading.value = false;
    }
};

const openCadEmployee = async (employeeSelected: Employee | null) => {
    try {
        showLoading();
        employeeStore.clearEmployee();
        if (employeeSelected !== null) {
            await employeeStore.findById(employeeSelected.id);
        }

        isDialogVisible.value = true;
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
</script>
<template>
    <div>
        <VRow>
            <VCol cols="12">
                <VCard title="Funcionarios">
                    <VCardText class="position-relative">
                        <div class="d-flex justify-space-between flex-wrap pt-2">
                            <VCol cols="4" class="d-flex gap-4">
                                <VBtn
                                    type="submit"
                                    size="large"
                                    @click="openCadEmployee(null)"
                                >
                                    Adicionar
                                </VBtn>

                                <VTextField density="compact" label="Pesquisar" />
                            </VCol>
                        </div>
                    </VCardText>
                    <VDivider />
                    <VDataTable
                        :headers="headers"
                        :items="pageEmployee?.content"
                        :loading="isTableLoading"
                    >
                        <template #item.actions="{ item }">
                            <div class="d-flex gap-1">
                                <IconBtn size="small">
                                    <VIcon
                                        icon="ri-pencil-line"
                                        @click="openCadEmployee(item)"
                                    />
                                </IconBtn>
                                <IconBtn size="small">
                                    <VIcon icon="ri-delete-bin-line" />
                                </IconBtn>
                            </div>
                        </template>
                    </VDataTable>
                </VCard>
            </VCol>
        </VRow>
        <EmployeeCad
            :dialog="isDialogVisible"
            @update:dialog="isDialogVisible = $event"
        />
    </div>
</template>

<style></style>
