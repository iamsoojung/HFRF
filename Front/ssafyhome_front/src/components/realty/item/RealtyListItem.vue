<script setup>
import { defineProps } from 'vue';
defineProps({ realty: Object });
</script>

<template>
    <div class="p-3">
    <div class="border-[1px] border-gray-200 rounded-xl shadow-lg h-[455px]">
        <div class="p-4">
            <div class="relative mx-auto w-[290px] bg-red-50 h-[190px] rounded-xl">
                <img class="w-full h-full rounded-lg" :src="`src/assets/file/${realty.fileInfos[0].saveFolder}/${realty.fileInfos[0].saveFile}`"/>
                <div class="left-2 top-2 absolute bg-sky-500 rounded-md p-1 text-white">{{ realty.type }}</div>
            </div>
            <div class="mt-1 text-sm text-gray-400 ms-2">{{ realty.id }}</div>
            <div class="px-2 mb-2 text-2xl font-bold text-slate-700">{{ realty.title }}</div>
            <div class="text-gray-700 space-y-[2px] ms-[12px]">
                <div>{{ realty.address }} {{ realty.detailAddress }}</div>
                <div>가격 : {{ realty.price }}만원</div>
                <div>관리비 : {{ realty.adminPrice }}만원</div>
                <div>관리비 포함 항목 : {{ realty.adminDesc }}</div>
            </div>
            <div class="m-1 flex flex-wrap">
                <div class="bg-sky-500 text-white rounded-md p-1 m-1 text-sm"
                    v-for="keyword in realty.keyword.split(', ')">
                    {{ keyword }}
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<style scoped></style>