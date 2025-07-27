<script lang="ts" setup>
import VerticalNavSectionTitle from "@/@layouts/components/VerticalNavSectionTitle.vue";
import VerticalNavGroup from "@layouts/components/VerticalNavGroup.vue";
import VerticalNavLink from "@layouts/components/VerticalNavLink.vue";
</script>

<template>
    <!-- 👉 Dashboards -->
    <VerticalNavGroup
        :item="{
            title: 'Dashboards',
            badgeContent: '5',
            badgeClass: 'bg-error',
            icon: 'ri-home-smile-line',
        }"
    >
        <VerticalNavLink
            :item="{
                title: 'Analytics',
                to: '/dashboard',
            }"
        />
    </VerticalNavGroup>

    <!-- 👉 Apps & Pages -->
    <VerticalNavSectionTitle
        :item="{
            heading: 'Administrador',
        }"
    />

    <VerticalNavLink
        :item="{
            title: 'Funcionario',
            icon: 'ri-user-settings-line',
            to: '/employee',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Equipe',
            icon: 'ri-user-settings-line',
            to: '/team',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Workflow',
            icon: 'ri-user-settings-line',
            to: '/workflow',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Cadastro Kanban',
            icon: 'ri-user-settings-line',
            to: '/kanban-board',
        }"
    />
    <VerticalNavGroup
        :item="{
            title: 'Projetos',
            icon: 'ri-bar-chart-box-line',
        }"
    >
        <VerticalNavLink
            :item="{
                title: 'Projetos',
                icon: 'ri-bar-chart-box-line',
                to: '/project',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'Categorias',
                icon: 'ri-bar-chart-box-line',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'Status',
                icon: 'ri-bar-chart-box-line',
                to: '/project-status',
            }"
        />
    </VerticalNavGroup>

    <!-- 👉 Tarefas -->
    <VerticalNavSectionTitle
        :item="{
            heading: 'Tarefas',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Tarefas',
            icon: 'ri-text',
            to: '/typography',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Icons',
            icon: 'ri-remixicon-line',
            to: '/icons',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Cards',
            icon: 'ri-bar-chart-box-line',
            to: '/cards',
        }"
    />

    <!-- 👉 Forms & Tables -->
    <VerticalNavSectionTitle
        :item="{
            heading: 'Forms & Tables',
        }"
    />
    <VerticalNavLink
        :item="{
            title: 'Form Layouts',
            icon: 'ri-layout-4-line',
            to: '/form-layouts',
        }"
    />

    <VerticalNavLink
        :item="{
            title: 'Tables',
            icon: 'ri-table-alt-line',
            to: '/tables',
        }"
    />
</template>
