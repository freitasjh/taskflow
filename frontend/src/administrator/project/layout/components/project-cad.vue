<script setup lang="ts">
import { useTeamStore } from "@/administrator/team/store/teamStore";
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import Project from "../../model/project";
import { useProjectStatustore } from "../../store/projectStatusStore";
import { useProjectStore } from "../../store/projectStore";

const projectStore = useProjectStore();
const projectStatusStore = useProjectStatustore();
const teamStore = useTeamStore();

const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const props = defineProps<{
    dialog?: boolean;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const project = ref<Project>(new Project());
const isVisible = computed(() => props.dialog);
const projectStatusPage = computed(() => projectStatusStore.pageResultProjectStatus);
const pageTeam = computed(() => teamStore.teamPage);

watch(
    () => projectStore.project,
    (newVal) => {
        project.value = Object.assign(new Project(), newVal);
    },
    { immediate: true, deep: true }
);

const resetForm = () => {
    projectStore.resetProject();
};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

const save = async () => {
    try {
        showLoading();
        await projectStore.save(project.value);
        handlerToastSuccess("Projeto salvo");
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        hideLoading();
    }
};
</script>
<template>
    <VDialog v-model="isVisible" max-width="800px">
        <VCard>
            <VCardItem>
                <VCardTitle class="text-h4">Projeto</VCardTitle>
            </VCardItem>
            <VDivider />
            <VCardText>
                <VForm class="mt-6">
                    <VRow>
                        <VCol md="12" cols="12">
                            <VTextField v-model="project.name" label="Nome" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VTextField v-model="project.description" label="Descrição" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VTextField v-model="project.prefix" label="Prefixo" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VSelect
                                label="Status"
                                :items="projectStatusPage?.content"
                                item-title="name"
                                v-model="projectStore.projectStatusSelect"
                                return-object
                            >
                            </VSelect>
                        </VCol>
                        <VCol md="12" cols="12">
                            <VSelect
                                label="Equipe"
                                :items="pageTeam?.content"
                                item-title="name"
                                v-model="projectStore.teamSelected"
                                return-object
                            >
                            </VSelect>
                        </VCol>
                        <VDivider />
                        <VCol cols="12" class="d-flex flex-wrap gap-4">
                            <VBtn @click="save">Salvar</VBtn>

                            <VBtn
                                color="secondary"
                                variant="outlined"
                                type="reset"
                                @click.prevent="closeDialog"
                            >
                                Cancelar
                            </VBtn>
                        </VCol>
                    </VRow>
                </VForm>
            </VCardText>
        </VCard>
    </VDialog>
</template>
