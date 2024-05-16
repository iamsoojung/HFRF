<template>
    <Header />
    <div class="px-8 mx-40 mt-52">
        <div class="px-8 border-x-2 t-20">
            <div class="px-4 sm:px-0">
                <h3 class="text-lg font-semibold leading-7 text-gray-900">회원 정보</h3>
            </div>
            <dl class="divide-y divide-gray-100">
            <div class="mt-6 border-t border-gray-100">
                <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dt class="text-lg font-medium leading-6 text-gray-900">이름</dt>
                    <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.userName }}</dd>
                </div>
                    <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                        <dt class="text-lg font-medium leading-6 text-gray-900">아이디</dt>
                        <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.userId }}</dd>
                    </div>
                    <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                        <dt class="text-lg font-medium leading-6 text-gray-900">비밀번호</dt>
                        <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">⦁⦁⦁⦁⦁⦁⦁⦁
                        </dd>
                    </div>
                    <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                        <dt class="text-lg font-medium leading-6 text-gray-900">이메일</dt>
                        <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.emailId }}@{{ userInfo.emailDomain }}</dd>
                    </div>
                    <div class="px-4 py-8 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                        <dt class="text-lg font-medium leading-6 text-gray-900">소개글</dt>
                        <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">안녕하세요 저는 {{ userInfo.userName }}입니다.</dd>
                    </div>
                </dl>
            </div>
        </div>
    </div>
    <Footer/>
</template>

<script setup>
import { useMemberStore } from '@/stores/member'
const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
import Header from '../layout/Header.vue';
import Footer from '@/components/layout/Footer.vue'
import { storeToRefs } from 'pinia'
</script>