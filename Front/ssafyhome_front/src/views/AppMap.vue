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
                options.push({ text: apt.apartmentName, value: apt.aptCode });
            });
            aptList.value = options;
            console.log('Received apartment data:', data);
        },
        (error) => {
            console.log(error)
        }
    )
}

const getHouse = () => {
    
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
        <div class="grid grid-cols-2">
            <div class="col-span-1 grid grid-cols-3 justify-between">
                <VSelect class="ms-0 text-sm" :selectOption="sidoList" @onKeySelect="onChangeSido" />
                <VSelect class="text-sm" :selectOption="gugunList" @onKeySelect="onChangeGugun" />
                <VSelect class="me-0 text-sm" :selectOption="dongList" @onKeySelect="onChangeDong"/>
                <div
                    class="overflow-auto border-[1px] border-black mt-5 p-5 min-h-[700px] max-h-[700px] col-span-3 bg-white">
                    <div v-if="aptList.length === 0">아파트 매물이 없네요 ~~ ㅠ ㅋㅋ</div>
                    <div v-else>
                        <div v-for="apt in aptList" :key="apt.value">
                            {{ apt.text }}
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
            <ViewMap class="w-full h-full" />
        </div>
    </div>
</template>

<style scoped></style>
