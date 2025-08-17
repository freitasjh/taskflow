<script lang="ts" setup>
import { useHandlerMessage, useLoader } from "@/composoable/commons";
import avatar1 from "@images/avatars/avatar-1.png";
import { AxiosError } from "axios";
import { vMaska } from "maska/vue";
import { defineProps } from "vue";
import { useLocale } from "vuetify";
import { useEmployeeStore } from "../../store/employeeStore";

const employeeStore = useEmployeeStore();
const { handlerError, handlerToastSuccess } = useHandlerMessage();
const { showLoading, hideLoading } = useLoader();
const { t } = useLocale();
const props = defineProps<{
    dialog?: boolean;
}>();

const emit = defineEmits<{
    (e: "update:dialog", value: boolean): void;
}>();

const isVisible = computed(() => props.dialog);
const employee = computed(() => employeeStore.employee);

const accountData = {
    avatarImg: avatar1,
};

const refInputEl = ref<HTMLElement>();

const accountDataLocal = ref(structuredClone(accountData));

const resetForm = () => {
    employeeStore.resetEmployee();
};

// changeAvatar function
const changeAvatar = (file: Event) => {
    const fileReader = new FileReader();
    const { files } = file.target as HTMLInputElement;

    if (files && files.length) {
        fileReader.readAsDataURL(files[0]);

        fileReader.onload = () => {
            if (typeof fileReader.result === "string")
                accountDataLocal.value.avatarImg = fileReader.result;
        };
    }
};

// reset avatar image
const resetAvatar = () => {
    accountDataLocal.value.avatarImg = accountData.avatarImg;
};

const closeDialog = () => {
    resetForm();
    emit("update:dialog", false);
};

const save = async () => {
    try {
        showLoading();
        await employeeStore.saveEmployee(employee.value);
        handlerToastSuccess("Funcionario salvo com sucesso");
        await employeeStore.findByFilter();
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
                <VCardTitle class="text-h5">{{ $t("employees") }}</VCardTitle>
            </VCardItem>
            <VDivider />

            <VCardText class="mt-3">
                <VRow>
                    <VCol cols="12">
                        <VCard>
                            <!-- <VCardText class="d-flex">
                                <VAvatar
                                    rounded="lg"
                                    size="100"
                                    class="me-6"
                                    :image="accountDataLocal.avatarImg"
                                />

                                <form class="d-flex flex-column justify-center gap-5">
                                    <div class="d-flex flex-wrap gap-2">
                                        <VBtn
                                            color="primary"
                                            @click="refInputEl?.click()"
                                        >
                                            <VIcon
                                                icon="ri-upload-cloud-line"
                                                class="d-sm-none"
                                            />
                                            <span class="d-none d-sm-block"
                                                >Upload new photo</span
                                            >
                                        </VBtn>

                                        <input
                                            ref="refInputEl"
                                            type="file"
                                            name="file"
                                            accept=".jpeg,.png,.jpg,GIF"
                                            hidden
                                            @input="changeAvatar"
                                        />

                                        <VBtn
                                            type="reset"
                                            color="error"
                                            variant="outlined"
                                            @click="resetAvatar"
                                        >
                                            <span class="d-none d-sm-block">Reset</span>
                                            <VIcon
                                                icon="ri-refresh-line"
                                                class="d-sm-none"
                                            />
                                        </VBtn>
                                    </div>

                                    <p class="text-body-1 mb-0">
                                        Allowed JPG, GIF or PNG. Max size of 800K
                                    </p>
                                </form>
                            </VCardText> -->

                            <!-- <VDivider /> -->

                            <VCardText>
                                <!-- 👉 Form -->
                                <VForm class="mt-6">
                                    <VRow>
                                        <VCol md="6" cols="12">
                                            <VTextField
                                                v-model="employee.name"
                                                label="Nome"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.federalId"
                                                label="CPF"
                                                v-maska="'###.###.###-##'"
                                                placeholder="000.000.000-00"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.email"
                                                label="E-mail"
                                                placeholder="johndoe@gmail.com"
                                                type="email"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VSelect
                                                v-model="employee.departament"
                                                label="Departamento"
                                                :items="['DEV', 'ADMIN', 'TEC']"
                                                placeholder="Select Country"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.phone"
                                                label="Telefone"
                                                placeholder="+1 (917) 543-9876"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.cellphone"
                                                label="Celular"
                                                placeholder="+1 (917) 543-9876"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.username"
                                                label="Usuario"
                                            />
                                        </VCol>
                                        <VCol cols="12" md="6">
                                            <VTextField
                                                v-model="employee.password"
                                                label="Senha"
                                                type="password"
                                            />
                                        </VCol>
                                        <!-- 👉 Form Actions -->
                                        <VCol cols="12" class="d-flex flex-wrap gap-4">
                                            <VBtn @click="save">{{ t("save") }}</VBtn>

                                            <VBtn
                                                color="secondary"
                                                variant="outlined"
                                                type="reset"
                                                @click.prevent="closeDialog"
                                            >
                                                {{ $t("cancel") }}
                                            </VBtn>
                                        </VCol>
                                    </VRow>
                                </VForm>
                            </VCardText>
                        </VCard>
                    </VCol>
                </VRow>
            </VCardText>
        </VCard>
    </VDialog>
</template>
