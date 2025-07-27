<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import avatar1 from "@images/avatars/avatar-1.png";
import { AxiosError } from "axios";
import { defineProps } from "vue";
import Team from "../../model/team";
import { useTeamStore } from "../../store/teamStore";

const teamStore = useTeamStore();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();

const props = defineProps<{
    dialog?: boolean;
    team?: Team;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const team = ref<Team>(new Team());

const isVisible = computed(() => props.dialog);
watch(
    () => teamStore.team,
    (newVal) => {
        team.value = Object.assign(new Team(), newVal);
    },
    { immediate: true, deep: true }
);
const pageEmployee = computed(() => teamStore.employeePage);
const employeeSelected = computed({
    get: () => teamStore.employeeSelected,
    set: (value) => {
        teamStore.employeeSelected = value;
    },
});

const resetForm = () => {
    teamStore.resetTeam();
};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

const save = async () => {
    try {
        showLoading();
        handlerToastSuccess("Equipe salva com sucesso");

        await teamStore.save(team.value);
        await teamStore.findByFilter();

        closeDialog();
    } catch (error) {
        handlerError(error as AxiosError | any);
    } finally {
        hideLoading();
    }
};

const addMember = async () => {
    try {
        if (employeeSelected.value === null) {
            handlerError("Informe o funcionario");
            return;
        }

        team.value.addMember(employeeSelected.value);
        employeeSelected.value = null;
    } catch (error) {
        handlerError(error as any);
    }
};

const removeMember = async (index: number) => {
    console.log(index);
    team.value.members.splice(index, 1);
};
</script>

<template>
    <VDialog v-model="isVisible" max-width="800px">
        <VCard>
            <VCardItem>
                <VCardTitle class="text-h4">Equipe</VCardTitle>
            </VCardItem>
            <VDivider />
            <VCardText>
                <VForm class="mt-6">
                    <VRow>
                        <VCol md="12" cols="12">
                            <VTextField v-model="team.name" label="Nome" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VTextField v-model="team.description" label="Descrição" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VTextField v-model="team.prefix" label="Prefixo" />
                        </VCol>
                        <VCol md="12" cols="12">
                            <VSelect
                                label="Funcionario"
                                :items="pageEmployee?.content"
                                item-title="name"
                                v-model="employeeSelected"
                                return-object
                            >
                            </VSelect>
                        </VCol>
                        <VCol md="12" cols="12">
                            <VBtn @click="addMember">Adicionar</VBtn>
                        </VCol>
                        <VDivider />
                        <VCol md="12" cols="12" v-if="team.hasMembers()">
                            <VCard>
                                <VList lines="two" border rounded>
                                    <template v-for="(member, index) of team?.members">
                                        <VListItem>
                                            <template #prepend>
                                                <VAvatar :image="avatar1" />
                                            </template>
                                            <VListItemTitle>
                                                {{ member.employee?.name }}
                                            </VListItemTitle>
                                            <!-- <VListItemSubtitle class="mt-1">
                                                <VBadge
                                                    dot
                                                    location="start center"
                                                    offset-x="2"
                                                    :color="
                                                        resolveStatusColor[user.status]
                                                    "
                                                    class="me-3"
                                                >
                                                    <span class="ms-4">{{
                                                        user.status
                                                    }}</span>
                                                </VBadge>

                                                <span class="text-xs text-disabled">{{
                                                    user.lastVisited
                                                }}</span>
                                            </VListItemSubtitle> -->

                                            <template #append>
                                                <VBtn
                                                    size="small"
                                                    @click="removeMember(index)"
                                                >
                                                    Remover
                                                </VBtn>
                                            </template>
                                        </VListItem>
                                        <VDivider
                                            v-if="index !== team.members.length - 1"
                                        />
                                    </template>
                                </VList>
                            </VCard>
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
