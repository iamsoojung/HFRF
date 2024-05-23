<script setup>
import { ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/member'
import { useMenuStore } from '@/stores/menu'
import Swal from 'sweetalert2';

const router = useRouter()

const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore
const { changeMenuState } = useMenuStore()

const loginUser = ref({
  userId: '',
  userPwd: '',
})

const login = async () => {
  try {
    await userLogin(loginUser.value);
    let token = sessionStorage.getItem("accessToken");

    if (isLogin.value) {
      getUserInfo(token);
      changeMenuState();
      router.push("/");
    } else {
      throw new Error("로그인 실패");
    }
  } catch (error) {
    Swal.fire({
      icon: 'error',
      title: '로그인 실패',
      text: '아이디와 비밀번호를\n 다시 확인해주세요!',
      confirmButtonColor: '#ff9999',
    });
  }
};

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <RouterLink :to="{ name: 'home' }" class="-m-1.5 p-1.5">
        <span class="sr-only">Go Home</span>
        <img class="mx-auto h-[140px] w-auto" src="@/assets/logo_onlyHouse.png" alt="logo" />
      </RouterLink>
      <h2 class="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-gray-900">
        로그인
      </h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" action="#" method="POST">
        <div>
          <label for="userId" class="block text-md font-medium leading-6 text-gray-900">아이디
          </label>
          <div class="mt-2">
            <input type="text"
              class="px-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              v-model="loginUser.userId" placeholder="아이디..." />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-md font-medium leading-6 text-gray-900">비밀번호</label>
            <div class="text-sm">
              <a href="#" class="font-semibold text-rose-400 hover:text-rose-500">비밀번호를 잊으셨나요?</a>
            </div>
          </div>
          <div class="mt-2">
            <input type="password"
              class="px-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              v-model="loginUser.userPwd" @keyup.enter="login" placeholder="비밀번호..." />
          </div>
        </div>
        <div v-if="isLoginError === true">
          <div class="text-red-600" role="alert">아이디 또는 비밀번호를 확인해주세요.</div>
        </div>
        <div>
          <button @click="login" type="button"
            class="flex w-full justify-center rounded-md bg-rose-400 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-rose-400">
            로그인
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        아직 회원이 아니신가요?
        {{ " " }}
        <RouterLink :to="{ name: 'join' }" class="font-semibold leading-6 text-rose-400 hover:text-rose-500">회원가입
        </RouterLink>
      </p>
    </div>
  </div>
</template>

<style scoped></style>
