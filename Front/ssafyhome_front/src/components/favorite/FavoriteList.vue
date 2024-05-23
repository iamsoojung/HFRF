<script setup>
import { ref, onMounted } from 'vue'
import FavoriteListItem from './item/FavoriteListItem.vue';
import { listLike, deleteLike } from '@/api/aptLike';
import { dealByDong, aptNameByCode } from '@/api/map';
import { storeToRefs } from 'pinia'
import { useMemberStore } from '@/stores/member'

const memberStore = useMemberStore()
const { userInfo, isLogin } = storeToRefs(memberStore)
const likeAptList = ref([])

const getAverageDealAmount = (aparts) => {
    let totalDealAmount = 0;
    aparts.forEach((apt) => {
        totalDealAmount += parseFloat(apt.dealAmount);
    });
    let average = totalDealAmount / aparts.length;
    return Math.round(average);
}

const getLikeList = () => {
    if (isLogin.value) {
        listLike(
            userInfo.value,
            ({ data }) => {
                likeAptList.value = data
                likeAptList.value.forEach((like) => {
                    dealByDong(
                        { aptCode: like.aptCode },
                        ({ data }) => {
                            like.average = getAverageDealAmount(data)
                        }
                    )
                    like.imgNum = Math.floor(Math.random() * 20) + 1;

                    aptNameByCode(
                        { aptCode: like.aptCode },
                        ({ data }) => {
                            like.name = data;
                        }
                    )
                })
            },
            (error) => {
                console.log(error)
            }
        );
    }
};

const likeApt = ref({
    userId: "",
    aptCode: "",
})
const onDeleteLike = (aptCode) => {
    likeApt.value.aptCode = aptCode;
    likeApt.value.userId = userInfo.value.userId;
    deleteLike(
        likeApt.value,
        (response) => {
            console.log(response)
        },
        (error) => {
            console.log(error)
        }
    )
    for (let i = 0; i < likeAptList.value.length; i++) {
        if (likeAptList.value[i].aptCode == aptCode) {
            likeAptList.value.splice(i, 1);
            i--;
        }
    }
}

onMounted(() => {
    getLikeList
});
</script>

<template>
    <div class="mx-20 mt-10">
        <div class="text-3xl font-bold border-b-[1px] pb-2 mb-2">
            관심 목록
        </div>
        <div v-show="likeAptList.length == 0" class="text-center mt-[300px] text-lg">관심 매물이 없습니다.</div>
        <div class="flex flex-wrap">
            <FavoriteListItem v-for="likeApt in likeAptList" :key="likeApt.aptCode" :likeApt="likeApt"
                class="sm:basis-1/2 xl:basis-1/3" @deleteLike="onDeleteLike" />
        </div>
    </div>

</template>

<style scoped></style>