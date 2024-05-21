<script setup>
import FavoriteListItem from './item/FavoriteListItem.vue';
</script>

<template>
    <div class="mx-20 mt-10">
        <div class="text-3xl font-bold border-b-[1px] pb-2">
            관심 목록
        </div>
        <div class="flex flex-wrap">
            <FavoriteListItem v-for="_ in 3" class="basis-1/3"/>
            <FavoriteListItem v-for="_ in 3" class="basis-1/3"/>
        </div>
    </div>

</template>

<style scoped></style>