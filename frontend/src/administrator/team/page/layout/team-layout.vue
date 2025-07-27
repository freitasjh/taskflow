<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import { AxiosError } from "axios";
import { onBeforeMount, ref } from "vue";
import TeamResponse from "../../model/teamResponse";
import { useTeamStore } from "../../store/teamStore";
import TeamCad from "../view/team-cad.vue";

const teamStore = useTeamStore();
const { handlerError } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const teamPage = computed(() => teamStore.teamPage);
const isDialogVisible = ref(false);
const isTableLoading = ref(false);

const headers = [
    { title: "ID", key: "id" },
    { title: "Equipe", key: "name" },
    { title: "Total de mebros", key: "totalMembers" },
    { title: "Actions", key: "actions" },
];

onBeforeMount(async () => {
    teamStore.resetState();
    await findByFilter();
});

const findByFilter = async () => {
    try {
        isTableLoading.value = true;
        await teamStore.findByFilter();
    } catch (error: AxiosError | any) {
        handlerError(error);
    } finally {
        isTableLoading.value = false;
    }
};

const openCadTeam = async (teamSelected: TeamResponse | null) => {
    try {
        showLoading();
        teamStore.resetTeam();

        if (teamSelected !== null) {
            await teamStore.findById(teamSelected.id);
        } else {
            await teamStore.findEmployee();
        }
        await teamStore.findEmployee();
        isDialogVisible.value = true;
    } catch (error) {
        handlerError(error as AxiosError | any);
    } finally {
        hideLoading();
    }
};
</script>

<template>
    <VRow>
        <VCol cols="12">
            <VCard title="Equipe">
                <VCardText class="position-relative">
                    <div class="d-flex justify-space-between flex-wrap pt-2">
                        <VCol cols="4" class="d-flex gap-4">
                            <VBtn type="submit" size="large" @click="openCadTeam(null)">
                                Adicionar
                            </VBtn>

                            <VTextField density="compact" label="Pesquisar" />
                        </VCol>
                    </div>
                </VCardText>
                <VDivider />
                <VDataTable
                    :headers="headers"
                    :items="teamPage?.content"
                    :loading="isTableLoading"
                >
                    <template #item.actions="{ item }">
                        <div class="d-flex gap-1">
                            <IconBtn size="small">
                                <VIcon icon="ri-pencil-line" @click="openCadTeam(item)" />
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
    <TeamCad :dialog="isDialogVisible" @update:dialog="isDialogVisible = $event" />
</template>
