<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import ProjectStatusResponse from "../../model/projectStatusResponse";
import { useProjectStatustore } from "../../store/projectStatusStore";
import ProjectStatusCad from "../components/project-status-cad.vue";

const projectStatusStore = useProjectStatustore();
const pageResultProjectStatus = computed(
    () => projectStatusStore.pageResultProjectStatus
);

const { handlerError } = useHandlerMessage();
const isDialogVisible = ref(false);
const isTableLoading = ref(false);

const { showLoading, hideLoading } = useLoader();

const headers = [
    { title: "ID", key: "id" },
    { title: "Nome", key: "name" },
    { title: "Descrição", key: "description" },
    { title: "Actions", key: "actions" },
];

onBeforeMount(async () => {
    projectStatusStore.resetState();

    await findByFilter();
});

const openDialogCad = async (projectStatusResponse: ProjectStatusResponse | null) => {
    try {
        showLoading();
        projectStatusStore.resetProjectStatus();

        if (projectStatusResponse !== null) {
            await projectStatusStore.findById(projectStatusResponse.id);
        }

        isDialogVisible.value = true;
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const findByFilter = async () => {
    try {
        isTableLoading.value = true;
        await projectStatusStore.findByFilter();
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        isTableLoading.value = false;
    }
};
</script>

<template>
    <VRow>
        <VCol>
            <VCard title="Status de Projeto">
                <VCardText class="position-relative">
                    <div class="d-flex justify-space-between flex-wrap pt-2">
                        <VCol cols="4" class="d-flex gap-4">
                            <VBtn type="submit" size="large" @click="openDialogCad(null)">
                                Adicionar
                            </VBtn>

                            <VTextField density="compact" label="Pesquisar" />
                        </VCol>
                    </div>
                </VCardText>
                <VDivider />
                <VDataTable
                    :headers="headers"
                    :items="pageResultProjectStatus?.content"
                    :loading="isTableLoading"
                >
                    <template #item.actions="{ item }">
                        <div class="d-flex gap-1">
                            <IconBtn size="small">
                                <VIcon
                                    icon="ri-pencil-line"
                                    @click="openDialogCad(item)"
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
    <ProjectStatusCad
        :dialog="isDialogVisible"
        @update:dialog="isDialogVisible = $event"
    />
</template>
