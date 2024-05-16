<script setup>
import ViewMap from '@/components/map/ViewMap.vue'
import Header from "@/components/layout/Header.vue";
import Footer from '@/components/layout/Footer.vue';
</script>

<template>
    <Header />
    <div class="mt-[200px] ">
        <ViewMap class="shadow-2xl border-solid border-2 border-rose-300 rounded-lg" />
    </div>
    <Footer />
</template>

<style scoped></style>