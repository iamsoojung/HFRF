<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from "pinia";
import axios from 'axios';

const memberStore = useMemberStore()
const router = useRouter()
const route = useRoute()
const { userInfo } = storeToRefs(memberStore)
const { type } = defineProps({ type: String });

const checkedNames = ref([])

const files = ref([]);
const realty = ref({
    idx: 0,
    id: "",
    title: "",
    date: "",
    type: "",
    price: "",
    adminPrice: "",
    adminDesc: "",
    roomType: "",
    floorNow: "",
    floorTot: "",
    size: "",
    content: "",
    image: "",
    keyword: "",
    address: "",
    dong: "",
    lat: "",
    lng: ""
})

const handleFileUpload = (event) => {
    files.value = event.target.files;
};

async function writeRealty() {
    const formData = new FormData();
    formData.append("id", userInfo.value.userId)
    formData.append("title", realty.value.title)
    formData.append("type", realty.value.type)
    formData.append("price", realty.value.price)
    formData.append("admin_price", realty.value.adminPrice)
    formData.append("admin_desc", realty.value.adminDesc)
    formData.append("room_type", realty.value.roomType)
    formData.append("floor_now", realty.value.floorNow)
    formData.append("floor_tot", realty.value.floorTot)
    formData.append("size", realty.value.size)
    formData.append("content", realty.value.content)
    formData.append("image", realty.value.image)
    formData.append("keyword", realty.value.keyword)
    formData.append("address", realty.value.address)
    formData.append("lat", realty.value.lat)
    formData.append("lng", realty.value.lng)

    for (let i = 0; i < files.value.length; i++) {
        formData.append("fileInfos", files.value[i]);
    }

    try {
        await axios({
            method: "POST",
            url: 'http://localhost/house',
            mode: "cors",
            headers: {
                "Content-Type": "multipart/form-data",
            },
            data: formData,
        });
        alert("매물 등록이 완료되었습니다.");

    } catch (error) {
        console.error(error);
        alert("매물 등록 중 오류가 발생했습니다.");
    }
}

function onSubmit() {
    let keyword = "";
    if (checkedNames.value.length != 0) {
        keyword = checkedNames.value[0];
        for (let i = 1; i < checkedNames.value.length; i++) {
            keyword += ", " + checkedNames.value[i]
        }
    }
    realty.value.keyword = keyword;
    type === "regist" ? writeRealty() : writeRealty();
}
</script>

<template>
    <form @submit.prevent="onSubmit" enctype="multipart/form-data" class="max-w-[800px] mx-auto">
        <div class="flex border-b-[1px] mb-4">
            <div class="my-auto w-[80px] text-center font-bold me-4" for="title">제목</div>
            <input type="text" id="title" v-model="realty.title" class="border-none w-full" placeholder="제목을 입력하세요">
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4">
            <div class="my-auto w-[80px] text-center font-bold me-4" for="content">내용</div>
            <textarea class="w-full rounded-lg border-gray-500" id="content" rows="4"
                v-model="realty.content"></textarea>
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4 justify-between">
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-4">거래 방식</div>
                <div class="my-auto">
                    <input type="radio" id="monthly" value="월세" name="type-radio-group" v-model="realty.type"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="monthly"
                        class="ms-2 me-3 text-sm font-medium text-gray-900 dark:text-gray-300">월세</label>
                    <input type="radio" id="charter" value="전세" name="type-radio-group" v-model="realty.type"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="charter"
                        class="ms-2 me-3 text-sm font-medium text-gray-900 dark:text-gray-300">전세</label>
                    <input type="radio" id="trading" value="매매" name="type-radio-group" v-model="realty.type"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="trading"
                        class="ms-2 me-3 text-sm font-medium text-gray-900 dark:text-gray-300">매매</label>
                </div>
            </div>
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-3" for="price">가격</div>
                <input type="number" id="price" class="border-[1px] border-gray-400 rounded-lg w-[225px]"
                    v-model="realty.price">
            </div>
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4 justify-between">
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-4">방 개수</div>
                <div class="my-auto">
                    <input type="radio" id="one" value="원룸" name="room-type-radio-group" v-model="realty.roomType"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="one" class="ms-2 me-3 text-sm font-medium text-gray-900">원룸</label>
                    <input type="radio" id="two" value="투룸" name="room-type-radio-group" v-model="realty.roomType"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="two" class="ms-2 me-3 text-sm font-medium text-gray-900 ">투룸</label>
                    <input type="radio" id="three-plus" value="쓰리룸+" name="room-type-radio-group"
                        v-model="realty.roomType"
                        class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-0">
                    <label for="three-plus" class="ms-2 me-3 text-sm font-medium text-gray-900">쓰리룸+</label>
                </div>
            </div>
            <div class="flex">
                <label class="my-auto w-[80px] text-center font-bold me-4" for="admin-price">관리비</label>
                <input type="number" id="admin-price" class="border-[1px] border-gray-400 rounded-lg w-[225px]"
                    v-model="realty.adminPrice">
            </div>
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4 justify-between">
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-4" for="floor-now">층수</div>
                <input class="border-[1px] border-gray-400 rounded-lg w-[225px]" type="number" id="floor-now"
                    v-model="realty.floorNow">
            </div>
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-4" for="admin-desc">관리비<br>포함 항목</div>
                <input class="border-[1px] border-gray-400 rounded-lg w-[225px]" type="text" id="admin-desc"
                    v-model="realty.adminDesc">
            </div>
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4 justify-between">
            <div class="flex">
                <div class="my-auto w-[80px] text-center font-bold me-4" for="floor-tot">건물 전체 층</div>
                <input class="border-[1px] border-gray-400 rounded-lg w-[225px]" type="number" id="floor-tot"
                    v-model="realty.floorTot">
            </div>
            <div class="flex">
                <label class="my-auto w-[80px] text-center font-bold me-4" for="size">면적</label>
                <input class="border-[1px] border-gray-400 rounded-lg w-[225px]" type="number" id="size"
                    v-model="realty.size">
            </div>
        </div>
        <div class="flex border-b-[1px] pb-4 mb-4">
            <div class="my-auto w-[80px] text-center font-bold me-4">키워드</div>
            <div class="my-auto">
                <input type="checkbox" id="safe" value="안전" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="safe" class="ms-2 me-3 text-sm font-medium text-gray-900">안전</label>
                <input type="checkbox" id="clean" value="청결" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="clean" class="ms-2 me-3 text-sm font-medium text-gray-900">청결</label>
                <input type="checkbox" id="silent" value="조용" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="silent" class="ms-2 me-3 text-sm font-medium text-gray-900">조용</label>
                <input type="checkbox" id="view" value="뷰맛집" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="view" class="ms-2 me-3 text-sm font-medium text-gray-900">뷰맛집</label>
                <input type="checkbox" id="transport" value="역세권" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="transport" class="ms-2 me-3 text-sm font-medium text-gray-900">역세권</label>
                <input type="checkbox" id="new" value="신축" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="new" class="ms-2 me-3 text-sm font-medium text-gray-900">신축</label>
                <input type="checkbox" id="cheap" value="갓성비" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="cheap" class="ms-2 me-3 text-sm font-medium text-gray-900">갓성비</label>
                <input type="checkbox" id="infra" value="인프라" v-model="checkedNames"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-0">
                <label for="infra" class="ms-2 text-sm font-medium text-gray-900">인프라</label>
            </div>
        </div>
        <div class="flex">
            <div class="my-auto w-[80px] text-center font-bold me-4">이미지</div>
            <input type="file" class="form-control border" id="upfile" @change="handleFileUpload" multiple>
        </div>

        <div class="text-end">
            <button class="me-2 bg-blue-500 text-white hover:bg-blue-600 rounded-lg py-1 px-2 border-[1px]"
                type="submit">등록</button>
            <RouterLink :to="{ name: 'realtyList'}" class="hover:bg-red-600 hover:text-white rounded-lg py-[6px] px-2 border-[1px]">취소</RouterLink>
        </div>
    </form>
</template>

<style scoped></style>
