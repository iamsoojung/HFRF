<script setup>
import { ref, reactive, watch } from "vue";
import {
  Listbox,
  ListboxButton,
  ListboxOption,
  ListboxOptions,
} from "@headlessui/vue";
import { CheckIcon, ChevronUpDownIcon } from "@heroicons/vue/20/solid";
import { httpStatusCode } from "@/util/http-status";
import { checkId, checkEmail } from "@/api/user";
import { useMemberStore } from '@/stores/member'
import Swal from 'sweetalert2';

const memberStore = useMemberStore()
const { userJoin } = memberStore

const emails = [
  { id: 1, name: "naver.com" },
  { id: 2, name: "google.com" },
  { id: 3, name: "daum.net" },
];
const selected = ref(emails[0]);

const joinUser = reactive({
  userName: "",
  userId: "",
  userPwd: "",
  emailId: "",
  emailDomain: selected.value.name
})
const checkPwd = ref('')
const isPwdError = ref(false)
const isDupId = ref(false)

const join = async () => {
  if (isDupId.value) {
    Swal.fire({
      icon: 'error',
      title: '회원 가입 실패',
      text: "이미 존재하는 ID입니다.",
      confirmButtonColor: '#ff9999',
    });
    // alert("이미 존재하는 ID입니다.")
  } else if (isPwdError.value) {
    Swal.fire({
      icon: 'error',
      title: '회원 가입 실패',
      text: "비밀번호가 다릅니다.",
      confirmButtonColor: '#ff9999',
    });
    // alert("비밀번호가 다릅니다.")
  } else if (isDupEmail.value || emailInputNone.value) {
    Swal.fire({
      icon: 'error',
      title: '회원 가입 실패',
      text: "이메일을 확인해주세요.",
      confirmButtonColor: '#ff9999',
    });
    // alert("이메일을 확인해주세요.")
  } else if (!codeCorrect.value) {
    Swal.fire({
      icon: 'error',
      title: '회원 가입 실패',
      text: "인증번호를 확인해주세요.",
      confirmButtonColor: '#ff9999',
    });
    // alert("인증번호를 확인해주세요.")
  } else {
    await userJoin(joinUser)
  }
}

const checkDup = async () => {
  await checkId(joinUser.userId,
    (response) => {
      if (response.data === 0) {
        isDupId.value = false;
      } else {
        isDupId.value = true;
      }
    },
    (error) => {
      console.log(error)
    }
  )
}

const isDupEmail = ref()
const emailInputNone = ref()
const emailCode = ref()
const inputCode = ref()
const codeCorrect = ref()
const emailCheck = async () => {
  if (joinUser.emailId.length == 0) {
    emailInputNone.value = true
  } else {
    emailInputNone.value = false
    await checkEmail(joinUser.emailId, joinUser.emailDomain,
      (response) => {
        console.log(response);
        if (response.data.isDup === 1) {
          isDupEmail.value = true;
        } else {
          isDupEmail.value = false;
          emailCode.value = response.data.key
        }
      },
      (error) => {
        console.log(error)
      }
    )
  }
}
const checkCode = () => {
  if (emailCode.value == inputCode.value) {
    codeCorrect.value = true
    Swal.fire({
      icon: 'success',
      title: '인증 완료',
      text: "인증되었습니다.",
      confirmButtonColor: '#429f50',
    });
    // alert("인증되었습니다.")
  } else {
    Swal.fire({
      icon: 'error',
      title: '인증 실패',
      text: "인증번호가 다릅니다",
      confirmButtonColor: '#ff9999',
    });
    // alert("인증번호가 다릅니다")
    codeCorrect.value = false
  }
}

watch(() => joinUser.userId, (newParam, oldParam) => {
  if (newParam.length != 0) {
    checkDup()
  }
})
watch(() => checkPwd.value, (newParam, oldParam) => {
  isPwdError.value = newParam != joinUser.userPwd
})
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <RouterLink :to="{ name: 'home' }" class="m-1.5 p-1.5">
        <span class="sr-only">Go Home</span>
        <img class="mx-auto h-[140px] w-auto" src="@/assets/logo_onlyHouse.png" alt="logo" />
      </RouterLink>
      <h2 class="mt-10 text-center text-4xl font-bold leading-9 tracking-tight text-gray-900">
        회원가입
      </h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full max-w-xl content-center">
      <form class="space-y-7" @submit.prevent="join">
        <div class="flex flex-row">
          <div class="text-lg basis-2/6 my-auto">이름</div>
          <input v-model="joinUser.userName" type="text" required
            class="ps-3 basis-5/6 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
        </div>
        <div class="relative flex flex-row">
          <div class="text-lg basis-2/6 my-auto">아이디</div>
          <input v-model="joinUser.userId" name="userId" type="text" required
            :class="{ 'focus:ring-indigo-600': !isDupId, 'ring-red-600': isDupId }"
            class="ps-3 basis-5/6 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />
          <div v-if="isDupId" class="absolute right-0 mt-2 text-red-600 text-sm me-3" role="alert">
            이미 존재하는 ID입니다.
          </div>
          <div v-else-if="joinUser.userId.length != 0" class="absolute right-0 mt-2 text-green-600 text-sm me-3"
            role="alert">
            사용 가능한 ID입니다.
          </div>
        </div>
        <div class="flex flex-row">
          <div class="text-lg basis-2/6 my-auto">비밀번호</div>
          <input v-model="joinUser.userPwd" name="userPwd" type="password" required maxlength="16"
            class="ps-3 basis-5/6 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
        </div>
        <div class="relative flex flex-row">
          <div class="text-lg basis-2/6 my-auto">비밀번호 확인</div>
          <input v-model="checkPwd" name="checkPwd" type="password" required maxlength="16"
            :class="{ 'focus:ring-indigo-600': !isPwdError, 'ring-red-600': isPwdError }"
            class="ps-3 basis-5/6 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6" />
          <div v-if="isPwdError" class="absolute right-0 mt-2 text-red-600 text-sm me-3" role="alert">
            비밀번호가 일치하지 않습니다.
          </div>
        </div>
        <div class="grid grid-rows-2">
          <div class="flex">
            <div class="text-lg basis-2/6 my-auto">이메일</div>
            <div class="relative flex items-center basis-5/6">
              <div class="absolute right-[-120px] z-10">
                <div @click="emailCheck"
                  class="hover:cursor-pointer hover:bg-slate-200 shadow-sm border-[1px] rounded-lg p-1 px-3">이메일 인증
                </div>
              </div>
              <input v-model="joinUser.emailId" name="emailId" type="text" required
                class="ps-3 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
              <span class="mx-2">@</span>
              <Listbox name="emailDomain" as="div" v-model="selected">
                <div class="relative flex">
                  <ListboxButton
                    class="relative w-40 cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
                    <span class="flex items-center">
                      <span class="ml-3 block truncate">{{ selected.name }}</span>
                    </span>
                    <span class="pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2">
                      <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
                    </span>
                  </ListboxButton>
                  <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                    leave-to-class="opacity-0">
                    <ListboxOptions
                      class="absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                      <ListboxOption as="template" v-for="email in emails" :key="email.id" :value="email"
                        v-slot="{ active, selected }">
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
          <div class="relative flex">
            <div class="text-lg basis-2/6 my-auto"></div>
            <div class="mt-2 basis-5/6 text-red-600 text-sm" v-show="emailInputNone">이메일을 입력해주세요.</div>
            <div class="mt-2 basis-5/6 text-red-600 text-sm" v-show="isDupEmail">다른 이메일을 입력해주세요.</div>
            <input v-show="emailCode == null ? false : true" v-model="inputCode"
              class="absolute mt-5 w-[160px] border-[1px] right-0 text-sm p-1.5 px-6 rounded-lg border-gray-300">
            <div class="mt-5 absolute right-[-75px] z-10">
              <div v-show="emailCode == null ? false : true" @click="checkCode"
                class="hover:cursor-pointer hover:bg-slate-200 shadow-sm border-[1px] rounded-lg p-1 px-3">확인</div>
            </div>
          </div>
        </div>
        <div class="text-center p-5">
          <button type="submit"
            class="text-2xl rounded-md bg-rose-400 px-3 py-2 font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-rose-400">
            가입
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
