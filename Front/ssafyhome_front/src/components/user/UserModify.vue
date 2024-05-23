<template>
    <div class="grid grid-cols-4 pt-[50px] mx-20">
        <div class="col-span-1 divide-y-[1px] text-lg text-center mx-10 divide-gray-100">
            <div class="text-gray-500 py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-mypage' }">내 정보</RouterLink>
            </div>
            <div class="font-bold py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-modify' }">내 정보 수정</RouterLink>
            </div>
            <div class="text-gray-500 py-4 text-lg text-center">
                <RouterLink :to="{ name: 'user-delete' }">회원 탈퇴</RouterLink>
            </div>
        </div>
        <div class="col-span-3">
            <div class="px-8 border-x-2 t-20">
                <div class="px-4 sm:px-0">
                    <h3 class="ms-3 text-2xl font-semibold leading-7 text-gray-900">내 정보 수정</h3>
                </div>
                <form class="mt-6 border-gray-100" @submit.prevent="modify">
                    <dl class="divide-y divide-gray-100 mx-10">
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">이름</dt>
                            <input v-model="modifyUser.userName" type="text"
                                class="ps-3 basis-5/6 w-full rounded-md border-0 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />

                        </div>
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">아이디</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{{ userInfo.userId }}
                            </dd>
                        </div>
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">비밀번호</dt>
                            <input v-model="modifyUser.userPwd" type="password"
                                class="ps-3 basis-5/6 w-full rounded-md border-0 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />
                        </div>
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">비밀번호 확인</dt>
                            <input v-model="checkPwd" name="checkPwd" type="password" required maxlength="16"
                                :class="{ 'focus:ring-red-600': isPwdError, 'ring-red-600': isPwdError }"
                                class="ps-3 basis-5/6 w-full rounded-md border-0 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />
                            <div v-if="isPwdError" class="mt-2 text-red-600 text-sm me-3" role="alert">
                                비밀번호가 일치하지 않습니다.
                            </div>
                        </div>
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">이메일</dt>
                            <input v-model="modifyUser.emailId" type="text"
                                class="ps-3 basis-5/6 w-full rounded-md border-0 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />
                            <div class="flex">
                                <span class="me-4 my-auto">@</span>
                                <Listbox name="emailDomain" as="div" v-model="selected">
                                    <div class="relative flex">
                                        <ListboxButton
                                            class="relative w-40 cursor-default rounded-md bg-white py-1 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
                                            <span class="flex items-center">
                                                <span class="ml-3 block truncate">{{ selected.name }}</span>
                                            </span>
                                            <span
                                                class="pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2">
                                                <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
                                            </span>
                                        </ListboxButton>
                                        <transition leave-active-class="transition ease-in duration-100"
                                            leave-from-class="opacity-100" leave-to-class="opacity-0">
                                            <ListboxOptions
                                                class="absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                                                <ListboxOption as="template" v-for="email in emails" :key="email.id"
                                                    :value="email" v-slot="{ active, selected }">
                                                    <li :class="[
                                                        active ? 'bg-indigo-600 text-white' : 'text-gray-900',
                                                        'relative cursor-default select-none py-2 pl-3 pr-9',
                                                    ]">
                                                        <div class="flex items-center">
                                                            <span :class="[
                                                                selected ? 'font-semibold' : 'font-normal',
                                                                'ml-3 block truncate',
                                                            ]">{{ email.name }}</span>
                                                        </div>
                                                        <span v-if="selected" :class="[
                                                            active ? 'text-white' : 'text-indigo-600',
                                                            'absolute inset-y-0 right-0 flex items-center pr-4',
                                                        ]">
                                                            <CheckIcon class="h-5 w-5" aria-hidden="true" />
                                                        </span>
                                                    </li>
                                                </ListboxOption>
                                            </ListboxOptions>
                                        </transition>
                                    </div>
                                </Listbox>
                            </div>
                        </div>
                        <div class="px-4 py-7 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt class="text-lg font-medium leading-6 text-gray-900">소개글</dt>
                            <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">안녕하세요 저는 {{
                                userInfo.userName }}입니다.</dd>
                        </div>
                    </dl>
                    <div class="text-end p-4 me-36">
                        <button type="submit"
                            class="text-xl rounded-md bg-rose-400 px-3 py-2 font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-rose-400">
                            수정
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useMemberStore } from '@/stores/member'
import { ref, reactive, watch } from 'vue'
const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
const { userModify } = memberStore
import { storeToRefs } from 'pinia'
import Swal from 'sweetalert2';

const emails = [
    { id: 1, name: "naver.com" },
    { id: 2, name: "google.com" },
    { id: 3, name: "daum.net" },
];
const selected = ref(emails[0]);
import {
    Listbox,
    ListboxButton,
    ListboxOption,
    ListboxOptions,
} from "@headlessui/vue";
import { CheckIcon, ChevronUpDownIcon } from "@heroicons/vue/20/solid";

const modifyUser = reactive(userInfo)

const checkPwd = ref('')
const isPwdError = ref(false)
const modify = async () => {
    console.log(modifyUser)
    if (isPwdError.value) {
        alert("비밀번호가 다릅니다")
        // Swal.fire("비밀번호가 다릅니다.")
        // Vue.swal('Hello Vue world!!!');
    } else {
        await userModify(modifyUser)
    }
}
watch(() => checkPwd.value, (newParam, oldParam) => {
    isPwdError.value = newParam != modifyUser.value.userPwd
})
watch(() => selected.value, (newParam, oldParam) => {
    modifyUser.value.emailDomain = newParam.name
})
</script>