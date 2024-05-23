<script setup>
import { ref, onMounted, watch } from 'vue'
import RealtyListItem from './item/RealtyListItem.vue'
import { listRealty } from '@/api/realty'

const realtyList = ref([])

onMounted(() => {
    getRealtyList()
});

function getRealtyList() {
    listRealty(
        ({ data }) => {
            realtyList.value = data
        },
        (error) => {
            console.log(error)
        }
    );
}
</script>

<template>
    <div>
        <div class="border-b-[1px] pb-2 mb-2 flex justify-between mt-10">
            <div class="text-3xl font-bold">매물 목록</div>
            <RouterLink :to="{ name: 'realtyWrite' }"
                class="px-2 py-1 my-auto hover:bg-gray-900 bg-gray-700 text-white rounded-lg">매물 등록하기</RouterLink>
        </div>
        <div class="flex flex-wrap px-5">
            <RealtyListItem class="sm:basis-1/2 lg:basis-1/3 xl:basis-1/4" v-for="realty in realtyList" :key="realty.idx" :realty="realty" />
        </div>
    </div>
</template>

<style scoped></style>