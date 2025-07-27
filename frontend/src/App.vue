<script setup lang="ts">
import UpgradeToPro from '@/components/UpgradeToPro.vue'
</script>

<template>
    <VApp>
        <RouterView />
        <UpgradeToPro />
    </VApp>
</template>

<style lang="scss">
.swal2-container {
    z-index: 11000 !important;
}
</style>
