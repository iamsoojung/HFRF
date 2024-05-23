<script setup>
import { reactive, ref } from 'vue';
import Modal from "./AnalysisItem.vue";

const props = defineProps({ likeApt: Object });
const emit = defineEmits(["deleteLike"]);

function deleteLike(aptCode) {
    emit("deleteLike", aptCode);
}

const isModal = ref(false);
const modalData = reactive({
    aptCode: null,
    dongCode: null,
    aptName: null
});
const openModal = () => {
    modalData.aptCode = props.likeApt.aptCode;
    isModal.value = true;
}
</script>

<template>
    <Modal v-if="isModal" @close-modal="isModal = false" :isModal="true" :aptCode="modalData.aptCode"
        :dongCode="modalData.dongCode" />
    <div class="p-3 h-[450px]">
        <div class="border-[1px] shadow-lg border-gray-300 rounded-lg w-full h-full">
            <div class="w-full h-[300px] bg-pink-50 text-center">
                <img class="h-[300px] w-[325px]" :src="`/src/assets/apartment/apartment` + likeApt.imgNum + `.jpg`">
            </div>
            <div class="py-3 px-5 flex justify-between mt-3">
                <div>
                    <div class="font-bold">{{ likeApt.name }}</div>
                    <div class="text-sm text-orange-600">평균 매매가</div>
                    <div>{{ Math.round(likeApt.average / 10000) }}억 {{ likeApt.average % 10000 }}만원</div>
                </div>
                <div>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                        @click="deleteLike(likeApt.aptCode)"
                        class="hover:bg-white hover:text-red-600 ms-auto mb-2 border-[1px] p-1 text-white bg-red-600 rounded-md shadow-md w-7 h-7 my-auto">
                        <path
                            d="m9.653 16.915-.005-.003-.019-.01a20.759 20.759 0 0 1-1.162-.682 22.045 22.045 0 0 1-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 0 1 8-2.828A4.5 4.5 0 0 1 18 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 0 1-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 0 1-.69.001l-.002-.001Z" />
                    </svg>
                    <button type="button"
                        class="rounded-md py-1 text-center bg-blue-500 text-white rounded-s-lg hover:bg-blue-600 px-2 text-sm focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600"
                        @click="openModal">
                        ChatGPT 매물 분석
                    </button>
                </div>
            </div>

        </div>
    </div>
</template>

<style scoped></style>
