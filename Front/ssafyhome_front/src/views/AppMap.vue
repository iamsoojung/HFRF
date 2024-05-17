<script setup>
import ViewMap from '@/components/map/ViewMap.vue'
import Header from "@/components/layout/Header.vue";
</script>

<template>
    <Header />
    <div class="fixed z-10 bg-red-200 min-h-[770px] ms-5 my-5 w-[20%] opacity-50">
        나는 경기도 안양의 이준영이다
    </div>
    <div class="border-y-2 grid">
        <div class="min-h-[805px]">
            <ViewMap class="w-full h-full" />
        </div>
    </div>
</template>

<style scoped>
</style>