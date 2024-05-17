<script setup>
import ViewMap from '@/components/map/ViewMap.vue'
import Header from "@/components/layout/Header.vue";
</script>

<template>
    <Header />
    <div class="grid xl:grid-cols-6">
        <div class="col-span-1 bg-pink-100"></div>
        <div class="min-h-[805px] xl:col-span-5">
            <ViewMap class="w-full h-full" />
        </div>
    </div>
</template>

<style scoped>
</style>