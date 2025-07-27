<script lang="ts" setup>
import KanbanBoard from "@/administrator/kanban/model/kanbanBoard";
import { useKanbanBoardStore } from "@/administrator/kanban/store/kanbanBoardStore";
import Workflow from "@/administrator/workflow/model/workflow";
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { defineProps } from "vue";

const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const kanbanBoardStore = useKanbanBoardStore();
const props = defineProps<{
    dialog?: boolean;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const isVisible = computed(() => props.dialog);
const kanbanBoard = ref<KanbanBoard>(new KanbanBoard());

const teamSelected = computed({
    get: () => kanbanBoardStore.teamSelected,
    set: (value) => {
        kanbanBoardStore.teamSelected = value;
    },
});

const workflowSelected = computed({
    get: () => kanbanBoardStore.workflowSelected,
    set: (value) => {
        kanbanBoardStore.workflowSelected = value;
    },
});

watch(
    () => kanbanBoardStore.KanbanBoard,
    (newVal) => {
        kanbanBoard.value = Object.assign(new Workflow(), newVal);
    },
    { immediate: true, deep: true }
);

const resetForm = () => {
    kanbanBoardStore.resetForm();
};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

const save = async () => {
    try {
        showLoading();
        if (teamSelected.value !== null) {
            kanbanBoard.value.teamId = teamSelected.value?.id;
        }

        if (workflowSelected.value !== null) {
            kanbanBoard.value.workflowId = workflowSelected.value?.id;
        }

        await kanbanBoardStore.save(kanbanBoard?.value);
        closeDialog();
    } catch (error: AxiosError | any) {
    } finally {
        hideLoading();
    }
};
</script>
<template>
    <VDialog v-model="isVisible" max-width="800px">
        <VCard>
            <VCardItem>Cadastro Kanban Board</VCardItem>
            <VDivider />
            <VCardText>
                <VForm class="mt-6">
                    <VRow>
                        <VCol md="12" cols="12">
                            <VTextField v-model="kanbanBoard.name" label="Nome" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VSelect
                                label="Equipe"
                                :items="kanbanBoardStore.teamPageResult?.content"
                                item-title="name"
                                v-model="teamSelected"
                                return-object
                            />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VSelect
                                label="Workflow"
                                :items="kanbanBoardStore.workflowPageResult?.content"
                                item-title="name"
                                v-model="workflowSelected"
                                return-object
                            />
                        </VCol>
                        <VCol cols="12" class="d-flex flex-wrap gap-4">
                            <VBtn @click="save">Save changes</VBtn>

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
