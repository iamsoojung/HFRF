<template>
    <div class="grid grid-cols-4 mt-52 mx-20">
        <div class="col-span-1 divide-y-[1px] text-lg text-center mx-10 divide-gray-100">
            <div class="font-bold py-4 text-lg text-center">
                <RouterLink :to="{name: 'user-mypage'}">내 정보</RouterLink>
            </div>
            <div class="text-gray-500 py-4 text-lg text-center">
                <RouterLink :to="{name: 'user-modify'}">내 정보 수정</RouterLink>
            </div>
            <div class="text-gray-500 py-4 text-lg text-center">회원 탈퇴</div>
        </div>
        <div class="col-span-3">
            <div class="px-8 border-x-2 t-20">
                <div class="px-4 sm:px-0">
                    <h3 class="ms-3 text-2xl font-semibold leading-7 text-gray-900">회원 정보</h3>
                </div>
                <div class="mt-6 border-gray-100">
                    <dl class="divide-y divide-gray-100 mx-10">
                        <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">이름</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.userName
                                }}</dd>
                        </div>
                        <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">아이디</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.userId }}
                            </dd>
                        </div>
                        <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">비밀번호</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">⦁⦁⦁⦁⦁⦁⦁⦁
                            </dd>
                        </div>
                        <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">이메일</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.emailId
                                }}@{{ userInfo.emailDomain }}</dd>
                        </div>
                        <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">소개글</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">안녕하세요 저는 {{
                                userInfo.userName }}입니다.</dd>
                        </div>
                    </dl>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useMemberStore } from '@/stores/member'
const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
import Header from '../layout/Header.vue';
import Footer from '@/components/layout/Footer.vue'
import { storeToRefs } from 'pinia'
</script>