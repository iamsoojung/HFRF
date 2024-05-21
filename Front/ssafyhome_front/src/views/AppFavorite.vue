<script setup>
import Header from '@/components/layout/Header.vue';
import FavoriteList from '@/components/favorite/FavoriteList.vue'
</script>

<template>
    <Header/>
    <div class="border-t-[1px] min-h-[805px] flex">
        <div class="basis-2/12 bg-gray-100"></div>
        <div class="basis-8/12">
            <FavoriteList/>
        </div>
        <div class="basis-2/12 bg-gray-100"></div>
    </div>
</template>

<style scoped>

</style>