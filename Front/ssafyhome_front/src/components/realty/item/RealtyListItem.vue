<script setup>
import { defineProps } from 'vue';
import { storeToRefs } from "pinia";
import { useMemberStore } from '@/stores/member';
import { deleteRealty } from '@/api/realty'
const props = defineProps({ realty: Object });

const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
function onDeleteRealty(idx) {
    console.log(props)
    deleteRealty(
        idx,
        (response) => {
            props.realty.idx = 0
        },
        (error) => {
            console.log(error)
        }
    )
}
function toggleDetail() {
    props.realty.detail = !props.realty.detail
}
</script>

<template>
    <div v-show="realty.idx != 0" class="p-3">
    <div class="border-[1px] border-gray-200 rounded-xl shadow-lg min-h-[455px]" @click="toggleDetail()">
        <div class="p-4 relative">
            <div class="relative mx-auto w-[290px] h-[190px] rounded-xl border-[1px]">
                <img alt="이미지 없음" class="text-center w-full h-full rounded-lg" :src="`src/assets/file/${realty.fileInfos.length != 0 ? realty.fileInfos[0].saveFolder : null}/${realty.fileInfos.length != 0 ? realty.fileInfos[0].saveFile : null}`"/>
                <div class="left-2 top-2 absolute bg-sky-500 rounded-md p-1 text-white">{{ realty.type }}</div>
                <button @click="onDeleteRealty(realty.idx)" v-show="userInfo.userId == realty.id" class="right-2 top-2 absolute bg-red-500 hover:bg-red-600 rounded-md p-1 text-white">삭제</button>
            </div>
            <div class="mt-1 text-sm text-gray-400 ms-2">{{ realty.id }}</div>
            <div class="px-2 mb-1 text-2xl font-bold text-slate-700">{{ realty.title }}</div>
            <div v-show="realty.detail" class="absolute bg-amber-50 border-[1px] rounded-lg p-2 shadow-xl w-[90%] text-center">"{{ realty.content}}"</div>
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