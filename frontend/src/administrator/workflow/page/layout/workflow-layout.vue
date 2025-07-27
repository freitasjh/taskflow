<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { onBeforeMount } from "vue";
import WorkflowResponse from "../../model/workflowResponse";
import { useWorkflowStore } from "../../store/workflowStore";
import WorkflowCad from "../view/workflow-cad.vue";

const workflowStore = useWorkflowStore();
const { handlerError } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const workflowPageResult = computed(() => workflowStore.workflowPageResult);
const isDialogVisible = ref(false);
const isTableLoading = ref(false);

const tableHeaders = [
    { title: "ID", key: "id" },
    { title: "Nome", key: "name" },
    { title: "Total de status", key: "totalStatus" },
    { title: "Actions", key: "actions" },
];

onBeforeMount(async () => {
    await findByFilter();
});

const openCad = async (workflowSelect: WorkflowResponse | null) => {
    try {
        showLoading();
        isDialogVisible.value = true;
    } catch (error) {
        handlerError(error as AxiosError | any);
    } finally {
        hideLoading();
    }
};

const findByFilter = async () => {
    try {
        isTableLoading.value = true;
        await workflowStore.findByFilter();
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        isTableLoading.value = false;
    }
};
</script>

<template>
    <VRow>
        <VCol cols="12">
            <VCard title="Workflow">
                <VCardText class="position-relative">
                    <div class="d-flex justify-space-between flex-wrap pt-2">
                        <VCol cols="4" class="d-flex gap-4">
                            <VBtn type="submit" size="large" @click="openCad(null)">
                                Adicionar
                            </VBtn>

                            <VTextField density="compact" label="Pesquisar" />
                        </VCol>
                    </div>
                </VCardText>
                <VDivider />
                <VDataTable
                    :headers="tableHeaders"
                    :items="workflowPageResult?.content"
                    :loading="isTableLoading"
                >
                    <template #item.actions="{ item }">
                        <div class="d-flex gap-1">
                            <IconBtn size="small">
                                <VIcon icon="ri-pencil-line" />
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
    <WorkflowCad :dialog="isDialogVisible" @update:dialog="isDialogVisible = $event" />
</template>
