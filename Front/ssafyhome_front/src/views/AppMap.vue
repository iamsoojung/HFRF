<script setup>
import { ref, onMounted } from 'vue';
import { listSido, listGugun, listDong, listAptByDong, dealByDong } from '@/api/map';
import ViewMap from '@/components/map/ViewMap.vue'
import VSelect from '@/components/common/VSelect.vue';
import Header from "@/components/layout/Header.vue";
const sidoList = ref([{ text: "시도선택", value: "" }]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const dongList = ref([{ text: "동선택", value: "" }]);
const aptList = ref([]);

const isDetail = ref(false)
const toggleDetail = () => {
    isDetail.value = false
}
const detailApt = ref([])

onMounted(() => {
    getSidoList();
})

const getSidoList = () => {
    listSido(
        ({ data }) => {
            let options = [];
            options.push({ text: "시도선택", value: "" });
            data.forEach((sido) => {
                options.push({ text: sido.sidoName, value: sido.sidoCode });
            });
            sidoList.value = options;
        },
        (error) => {
            console.log(error)
        }
    );
};

const onChangeSido = (value) => {
    listGugun(
        { sido: value },
        ({ data }) => {
            let options = [];
            options.push({ text: "구군선택", value: "" });
            data.forEach((gugun) => {
                options.push({ text: gugun.gugunName, value: gugun.gugunCode });
            });
            gugunList.value = options;
        },
        (error) => {
            console.log(error);
        }
    );
};

const onChangeGugun = (value) => {
    listDong(
        { gugun: value },
        ({ data }) => {
            let options = [];
            options.push({ text: "동선택", value: "" });
            data.forEach((dong) => {
                options.push({ text: dong.dongName, value: dong.dongCode });
            });
            dongList.value = options;
        },
        (error) => {
            console.log(error)
        }
    )
}

const onChangeDong = (value) => {
    console.log('Selected dong:', value);
    listAptByDong(
        { dongCode: value },
        ({ data }) => {
            let options = [];
            data.forEach((apt) => {
                console.log(apt)
                options.push({ dong: apt.dong, gugunName: apt.gugunname, text: apt.apartmentName, value: apt.aptCode, lat: apt.lat, lng: apt.lng });
            });
            aptList.value = options;
            console.log('Received apartment data:', data);
        },
        (error) => {
            console.log(error)
        }
    )
}

const getHouse = (value, text) => {
    dealByDong(
        { aptCode: value },
        ({ data }) => {
            console.log(data)
            detailApt.value = data
            detailApt.value.text = text
            isDetail.value = true
        },
        (error) => {
            console.log(error)
        }
    )
}

const isOpenMenu = ref(true)
const toggleMenu = () => {
    isOpenMenu.value = !isOpenMenu.value
}
</script>

<template>
    <Header />
    <div v-if="isOpenMenu" class="fixed z-10 my-5">
        <div class="fixed"></div>
        <div class="flex grid-cols-2">
            <div class="col-span-1 grid grid-cols-3 justify-between">
                <VSelect class="text-sm" style="margin-left: 5px" :selectOption="sidoList"
                    @onKeySelect="onChangeSido" />
                <VSelect class="text-sm" style="margin-left: 5px" :selectOption="gugunList"
                    @onKeySelect="onChangeGugun" />
                <VSelect class="text-sm" style="margin-left: 5px" :selectOption="dongList"
                    @onKeySelect="onChangeDong" />
                <div
                    class="overflow-auto border-[1px] border-black mt-5 min-h-[700px] max-h-[700px] col-span-3 bg-white">
                    <div v-if="isDetail">
                        <div class="w-full h-[200px] border-2 bg-[url('@/assets/home.jpg')] bg-cover">
                            <div @click="toggleDetail" class="absolute m-2 text-2xl font-bold">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                    stroke-width="1.5" stroke="currentColor" class="text-white w-7 h-7">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M6.75 15.75 3 12m0 0 3.75-3.75M3 12h18" />
                                </svg>
                            </div>
                            <div class="mt-24 ms-3">
                                <div class="font-bold text-2xl text-white">
                                    {{ detailApt.text }}
                                </div>
                            </div>
                        </div>
                        <div class="p-5">
                            <div class="text-lg mb-2">실거래가</div>
                            <div class="flex border-b-[1px] pt-2">
                                <div>
                                    계약일
                                </div>
                                <div class="ms-auto">
                                    실거래정보
                                </div>
                            </div>
                            <div>
                                <div class="flex border-b-[1px] py-2" v-for="detail in detailApt"
                                    :key="detailApt.aptCode">
                                    <div class="my-auto">
                                        {{ detail.dealYear % 100 }}. {{ detail.dealMonth }}. {{ detail.dealDay }}
                                    </div>
                                    <div class="ms-auto">
                                        <div class="text-end">
                                            매매가 {{ detail.dealAmount }}
                                        </div>
                                        <div class="text-gray-500 text-sm text-end">
                                            {{ detail.area }}㎡ | {{ detail.floor }}층
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-else-if="aptList.length === 0">아파트 매물이 없네요 ~~ ㅠ ㅋㅋ</div>
                    <div v-else>
                        <div class="flex p-3 border-b-[1px] " v-for="apt in aptList" :key="apt.value">
                            <div class="h-[100px] w-[125px] border-[1px] me-3">
                                <img alt="이미지" class="w-20" />
                            </div>
                            <div @click="getHouse(apt.value, apt.text)">
                                <div class="font-bold text-lg">{{ apt.text }}</div>
                                <div class="text-md text-gray-600">{{ apt.gugunName }} {{ apt.dong }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button @click="toggleMenu" class="my-auto w-5 h-48 bg-pink-300 border-[1px] border-l-0 border-black">
            </button>
        </div>
    </div>
    <div v-if="!isOpenMenu" class="flex fixed z-10 my-5 min-h-[700px]">
        <button @click="toggleMenu" class="my-auto w-5 h-48 bg-pink-300 border-[1px] border-l-0 border-black">
        </button>
    </div>
    <div class="border-y-2 grid">
        <div class="min-h-[805px]">
            <ViewMap class="w-full h-full" :aptList="aptList"/>
        </div>
    </div>
</template>

<style scoped></style>
