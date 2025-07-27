<script lang="ts" setup>
import { useHandlerMessage } from "@/composoable/commons";
import { AxiosError } from "axios";
import { ref } from "vue";
import Status from "../../model/status";
import Workflow from "../../model/workflow";
import { useWorkflowStore } from "../../store/workflowStore";

const props = defineProps<{
    dialog?: boolean;
    workflow?: Workflow;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const { handlerError, handlerToastSuccess } = useHandlerMessage();

const isVisible = computed(() => props.dialog);
const workflow = ref<Workflow>(new Workflow());
const workflowStore = useWorkflowStore();
const status = ref<Status>(new Status());

const resetForm = () => {};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

watch(
    () => workflowStore.workflow,
    (newVal) => {
        workflow.value = Object.assign(new Workflow(), newVal);
    },
    { immediate: true, deep: true }
);

const addStatus = () => {
    const lastOrder =
        workflow.value.listOfStatus.length > 0
            ? Math.max(...workflow.value.listOfStatus.map((s) => s.order ?? 0))
            : 0;

    // Define a ordem do novo status
    status.value.order = lastOrder + 1;

    // Adiciona o status e reseta
    workflow.value.listOfStatus.push({ ...status.value }); // Usa spread para evitar referência direta
    status.value = new Status();
};

const save = async () => {
    try {
        await workflowStore.save(workflow.value);
        handlerToastSuccess("Workflow salvo com sucesso");

        await workflowStore.findByFilter();

        closeDialog();
    } catch (error: AxiosError | any) {
        handlerError(error);
    }
};
</script>
<template>
    <VDialog v-model="isVisible" max-width="90%">
        <VCard>
            <VCardItem>
                <VCardTitle class="text-h4">Equipe</VCardTitle>
            </VCardItem>
            <VDivider />
            <VCardText>
                <VForm class="mt-6">
                    <VRow>
                        <VCol md="12" cols="12">
                            <VTextField label="Nome" v-nome="workflow.name" />
                        </VCol>
                        <VDivider />
                        <VCol md="12" cols="12">
                            <VCol md="12" cols="12">
                                <VTextField label="Nome Status" v-model="status.name" />
                            </VCol>
                            <VCol md="12" cols="12">
                                <VTextField label="Order" />
                            </VCol>
                            <VCol cols="12" class="d-flex flex-wrap gap-4">
                                <VBtn @click.prevent="addStatus">Adicionar</VBtn>
                            </VCol>
                            <VDivider />
                            <VCard>
                                <VList lines="two" border rounded>
                                    <template
                                        v-for="(status, index) of workflow?.listOfStatus"
                                    >
                                        <VListItem>
                                            <VListItemTitle>
                                                {{ status?.name }}
                                            </VListItemTitle>

                                            <template #append>
                                                <VBtn size="small"> Remover </VBtn>
                                            </template>
                                        </VListItem>
                                    </template>
                                </VList>
                            </VCard>
                        </VCol>
                        <VCol cols="12" class="d-flex flex-wrap gap-4">
                            <VBtn @click="save">Confirmar</VBtn>

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
