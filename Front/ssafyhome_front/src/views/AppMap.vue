<script setup>
import { ref, reactive, onMounted } from 'vue';
import { listSido, listGugun, listDong, listAptByDong, dealByDong } from '@/api/map';
import ViewMap from '@/components/map/ViewMap.vue'
import VSelect from '@/components/common/VSelect.vue';
import Header from "@/components/layout/Header.vue";
import Chart from '@/components/map/Chart.vue';

const sidoList = ref([{ text: "시도선택", value: "" }]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const dongList = ref([{ text: "동선택", value: "" }]);
const aptList = ref([]);

const isDetail = ref(false)
const toggleDetail = () => {
    isDetail.value = false
}
const detailApt = ref(reactive([]));

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

const noAptMsg = ref("지역을 선택해주세요.")
const onChangeDong = (value) => {
    console.log('Selected dong:', value);
    listAptByDong(
        { dongCode: value },
        ({ data }) => {
            let options = [];
            data.forEach((apt) => {
                options.push({ dong: apt.dong, gugunName: apt.gugunname, text: apt.apartmentName, value: apt.aptCode, lat: apt.lat, lng: apt.lng });
            });
            aptList.value = options;
            noAptMsg.value = "매물이 없습니다."
        },
        (error) => {
            console.log(error)
        }
    )
}
const getAverageDealAmount = () => {
    let totalDealAmount = 0;
    detailApt.value.forEach((apt) => {
        totalDealAmount += parseFloat(apt.dealAmount);
    });
    const average = totalDealAmount / detailApt.value.length;
    return Math.round(average);
};
const avarageDealAmount = ref()
const getHouse = (value, text) => {
    dealByDong(
        { aptCode: value },
        ({ data }) => {
            detailApt.value = data;
            detailApt.value.text = text;
            isDetail.value = true;
            avarageDealAmount.value = getAverageDealAmount()
        },
        (error) => {
            console.log(error);
        }
    );
};



const isOpenMenu = ref(true)
const toggleMenu = () => {
    isOpenMenu.value = !isOpenMenu.value
}

</script>

<template>
    <Header />
    <div v-show="isOpenMenu" class="fixed z-10 my-5 ">
        <div class="fixed"></div>
        <div class="flex grid-cols-2">
            <div class="z-10 col-span-1 flex-cols-3 bg-white shadow-xl border-[1px] border-gray-200 rounded-lg">
                <VSelect class="text-xs" :selectOption="sidoList"
                    @onKeySelect="onChangeSido" />
                <VSelect class="text-xs" style="margin-left: 5px" :selectOption="gugunList"
                    @onKeySelect="onChangeGugun" />
                <VSelect class="text-xs" style="margin-left: 5px" :selectOption="dongList"
                    @onKeySelect="onChangeDong" />
                <div
                    class="overflow-auto
                     mt-5 ms-1 min-h-[700px] max-h-[700px] col-span-3 bg-white z-10">
                    <div v-if="isDetail">
                        <div class="relative w-full h-[200px] border-2 bg-[url('@/assets/home.jpg')] bg-cover">
                            <div @click="toggleDetail" class="absolute m-2 text-2xl font-bold">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                    stroke-width="1.5" stroke="currentColor" class="hover:cursor-pointer text-white w-7 h-7">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M6.75 15.75 3 12m0 0 3.75-3.75M3 12h18" />
                                </svg>
                                
                            </div>
                            
                            <div class="mt-24 ms-3">
                                <div class="font-bold text-2xl text-white">
                                    {{ detailApt.text }}
                                </div>
                            </div>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                fill="currentColor" class="hover:bg-red-500 hover:text-white hover:border-red-600 border-[1px] p-1 text-red-600 bg-white rounded-md shadow-md w-8 h-8 absolute bottom-4 right-4">
                                <path
                                    d="m9.653 16.915-.005-.003-.019-.01a20.759 20.759 0 0 1-1.162-.682 22.045 22.045 0 0 1-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 0 1 8-2.828A4.5 4.5 0 0 1 18 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 0 1-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 0 1-.69.001l-.002-.001Z" />
                        </svg>
                        </div>
                        <Chart class="ms-10" :detailApt="detailApt" />
                        <div class="p-5">
                            <div class="mt-3 text-xl font-bold mb-4">
                                <span class="text-red-600">평균 매매가</span>
                                <span class="ms-2">{{ Math.round(avarageDealAmount / 10000) }}억 {{ avarageDealAmount % 10000
                                    }}만</span>
                            </div>

                            <div class="text-sm font-bold">실거래가</div>
                            <div class="flex border-b-[1px] pt-2">
                                <div>
                                    계약일
                                </div>
                                <div class="ms-auto">
                                    실거래정보
                                </div>
                            </div>
                            <div>
                                <div class="text-sm flex border-b-[1px] py-2" v-for="detail in detailApt"
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
                    <div class="text-center mt-[80%]" v-else-if="aptList.length === 0">{{ noAptMsg }}</div>
                    <div v-else>
                        <div class="relative" v-for="apt in aptList" :key="apt.value">
                        <div class="hover:cursor-pointer hover:bg-gray-100 flex p-3 border-b-[1px]"
                            @click="getHouse(apt.value, apt.text)">
                            <div class="w-32 bg-gradient-to-r from-cyan-400 to-blue-300 h-[100px] border-[1px] me-3 rounded-md">
                                <img class="bg-cover" />
                            </div>
                            <div class="relative w-full">
                                <div class="font-bold">{{ apt.text }}</div>
                                <div class="text-sm text-gray-600">{{ apt.gugunName }} {{ apt.dong }}</div>
                            </div>
                        </div>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                fill="currentColor" class="hover:bg-red-500 hover:text-white hover:border-red-600 border-[1px] p-1 text-red-600 rounded-md shadow-md w-6 h-6 absolute bottom-3 right-3">
                                <path
                                    d="m9.653 16.915-.005-.003-.019-.01a20.759 20.759 0 0 1-1.162-.682 22.045 22.045 0 0 1-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 0 1 8-2.828A4.5 4.5 0 0 1 18 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 0 1-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 0 1-.69.001l-.002-.001Z" />
                        </svg>
                        </div>
                    </div>
                </div>
            </div>
            <button @click="toggleMenu"
                class="rounded-md ms-[-5px] my-auto w-5 h-48 bg-gray-700 hover:bg-gray-800 border-[1px] border-l-0 border-gray-400">
            </button>
        </div>
    </div>
    <div v-if="!isOpenMenu" class="flex fixed z-10 my-5 min-h-[700px]">
        <button @click="toggleMenu"
            class="ms-[-5px] rounded-md my-auto w-5 h-48 bg-gray-700 hover:bg-gray-800 border-[1px] border-l-0 border-gray-400">
        </button>
    </div>
    <div class="border-y-2 grid">
        <div class="min-h-[805px]">
            <ViewMap class="w-full h-full" :aptList="aptList" />
        </div>
    </div>
</template>

<style scoped></style>