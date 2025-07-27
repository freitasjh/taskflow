<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import ProjectStatus from "../../model/projectStatus";
import { useProjectStatustore } from "../../store/projectStatusStore";

const projectStatusStore = useProjectStatustore();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const props = defineProps<{
    dialog?: boolean;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const isVisible = computed(() => props.dialog);
const projectStatus = ref<ProjectStatus>(new ProjectStatus());

watch(
    () => projectStatusStore.projectStatus,
    (newVal) => {
        projectStatus.value = Object.assign(new ProjectStatus(), newVal);
    },
    { immediate: true, deep: true }
);

const resetForm = () => {
    projectStatusStore.resetProjectStatus();
};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

const save = async () => {
    try {
        showLoading();

        await projectStatusStore.save(projectStatus.value);

        handlerToastSuccess("Status salvo com sucesso");
        await projectStatusStore.findByFilter();
        closeDialog();
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
                <VCardTitle class="text-h4">Status Projeto</VCardTitle>
            </VCardItem>
            <VDivider />
            <VCardText>
                <VRow>
                    <VCol>
                        <VCol md="12" cols="12">
                            <VTextField v-model="projectStatus.name" label="Nome" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VTextField
                                v-model="projectStatus.description"
                                label="Descrição"
                            />
                        </VCol>
                    </VCol>
                    <VDivider />
                    <VCol cols="12" class="d-flex flex-wrap gap-4">
                        <VBtn @click="save()">Salvar</VBtn>

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
            </VCardText>
        </VCard>
    </VDialog>
</template>
