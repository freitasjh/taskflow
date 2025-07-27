<script setup lang="ts">
import { useTeamStore } from "@/administrator/team/store/teamStore";
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { onBeforeMount } from "vue";
import { useProjectStatustore } from "../../store/projectStatusStore";
import { useProjectStore } from "../../store/projectStore";
import ProjectCad from "../components/project-cad.vue";

const projectStore = useProjectStore();
const projectStatusStore = useProjectStatustore();
const teamStore = useTeamStore();

const { handlerError } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const pageResultProject = computed(() => projectStore.pageResultProject);
const isDialogVisible = ref(false);
const isTableLoading = ref(false);

const headers = [
    { title: "ID", key: "id" },
    { title: "Pojeto", key: "name" },
    { title: "Actions", key: "actions" },
];

onBeforeMount(async () => {
    await findByFilter();
});

const openCadDialog = async () => {
    try {
        showLoading();
        projectStore.resetProject();
        isDialogVisible.value = true;
        await projectStatusStore.findByFilter();
        await teamStore.findByFilter();
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};

const findByFilter = async () => {
    try {
        isTableLoading.value = true;
        await projectStore.findByFilter();
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
            <VCard title="Projeto">
                <VCardText class="position-relative">
                    <div class="d-flex justify-space-between flex-wrap pt-2">
                        <VCol cols="4" class="d-flex gap-4">
                            <VBtn type="submit" size="large" @click="openCadDialog">
                                Adicionar
                            </VBtn>

                            <VTextField density="compact" label="Pesquisar" />
                        </VCol>
                    </div>
                </VCardText>
                <VDivider />
                <VDataTable
                    :headers="headers"
                    :items="pageResultProject?.content"
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
    <ProjectCad :dialog="isDialogVisible" @update:dialog="isDialogVisible = $event" />
</template>
