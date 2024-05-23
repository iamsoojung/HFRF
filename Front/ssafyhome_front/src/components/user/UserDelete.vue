<script setup>
import { storeToRefs } from 'pinia'
import { useMemberStore } from '@/stores/member'
import { useRouter } from 'vue-router'
const router = useRouter()
const memberStore = useMemberStore()
const { userInfo, isValidToken, isLogin } = storeToRefs(memberStore)
const { userDelete, getUserInfo } = memberStore
const deleteUser = async () => {
    let token = sessionStorage.getItem("accessToken");
    if (isLogin.value != false && token) {
        await getUserInfo(token);
    }
    if (!isValidToken.value || userInfo.value.userId == null) {
        isLogin.value = false;
        router.push({ name: "login" });
    } else {
        await userDelete(userInfo.value.userId)
        isLogin.value = false;
        router.push({ name: "home" })
    }
}
</script>

<template>
    <div class="grid grid-cols-4 pt-[50px] mx-20">
        <div class="col-span-1 divide-y-[1px] text-lg text-center mx-10 divide-gray-100">
            <div class="text-gray-500 py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-mypage' }">내 정보</RouterLink>
            </div>
            <div class="text-gray-500 py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-modify' }">내 정보 수정</RouterLink>
            </div>
            <div class="font-bold py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-delete' }">회원 탈퇴</RouterLink>
            </div>
        </div>
        <div class="col-span-3">
            <div class="px-8 border-x-2 t-20" style="min-height: 550px">
                <div class="px-4 sm:px-0">
                    <h3 class="ms-3 text-2xl font-semibold leading-7 text-gray-900">회원 탈퇴</h3>
                    <div>
                        <div class="ms-8 mt-10 text-lg border-gray-100">
                            정말 탈퇴하시겠습니까?
                            <div class="mt-2 text-sm">
                                진짜?
                            </div>
                        </div>
                        <div class="text-end p-4 me-36">
                            <button @click="deleteUser"
                                class="text-xl rounded-md bg-rose-400 px-3 py-2 font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-rose-400">
                                안녕
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>