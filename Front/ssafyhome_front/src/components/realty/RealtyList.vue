<script setup>
import { ref, onMounted } from 'vue'
import RealtyListItem from './item/RealtyListItem.vue'
import { listRealty } from '@/api/realty'

const realtyList = ref([])

function getRealtyList() {
    listRealty(
        ({data}) => {
            realtyList.value = data
        },
        (error) => {
            console.log(error)
        }
    );
}

onMounted(() => {
    getRealtyList()
});
</script>

<template>
   <div class="w-[860px] mt-10 mx-auto">
        <div class="border-b-[1px] pb-2 mb-2 flex justify-between">
            <div class="text-3xl font-bold">매물 목록</div>
            <RouterLink :to="{name: 'realtyWrite'}" class="px-2 py-1 my-auto hover:bg-gray-900 bg-gray-700 text-white rounded-lg">매물 등록하기</RouterLink>
        </div>
        <div class="flex flex-wrap">
            <RealtyListItem v-for="realty in realtyList" :key="realty.idx" :realty="realty"/>
        </div>
    </div>
</template>

<style scoped>

</style>