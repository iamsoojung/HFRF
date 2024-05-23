<template>
  <div class="mb-[100px]"></div>
  <header class="absolute inset-x-0 top-0 z-50">
    <nav class="flex items-center justify-between lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1">
        <RouterLink :to="{ name: 'home' }" class="p-1.5">
          <span class="sr-only">Go Home</span>
          <img class="h-20 w-auto" src="@/assets/logo.png" alt="" />
        </RouterLink>
      </div>
      <div class="flex lg:hidden">
        <button type="button" class="m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
          @click="mobileMenuOpen = true">
          <span class="sr-only">Open main menu</span>
          <Bars3Icon class="h-6 w-6" aria-hidden="true" />
        </button>
      </div>
      <div class="hidden lg:flex lg:gap-x-32">
        <template v-for="item in menuList" :key="item.routeName">
          <template v-if="item.show">
            <RouterLink :to="{ name: item.routeName }" class="hover:font-bold text-lg font-semibold leading-6 text-gray-900">{{
              item.name }}</RouterLink>
          </template>
        </template>
      </div>
      <div class="hidden lg:flex lg:flex-1 lg:justify-end me-5">
        <template v-if="isLogin">
          <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton
                class="shadow-lg border-[1px] border-gray-300 hover:border-gray-600 me-4 relative flex rounded-full text-sm">
                <span class="absolute -inset-1.5" />
                <span class="sr-only">Open user menu</span>
                <img class="h-10 w-10 bg- rounded-full"
                  src="@/assets/logo_onlyHouse.png"
                  alt="" />
              </MenuButton>
            </div>
            <transition enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95">
              <MenuItems
                class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <MenuItem v-slot="{ active }">
                <RouterLink :to="{name: 'user-mypage'}"
                  :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">회원 정보</RouterLink>
                </MenuItem>
                <MenuItem v-slot="{ active }" @click="logout">
                <a href="#" :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">Logout</a>
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
          <span class="my-auto" v-if="userInfo && userInfo.userId">
            <span class="font-bold">
              {{ userInfo.userName }}
            </span>
            님, 안녕하세요
          </span>
        </template>
        <template v-else>
          <RouterLink :to="{ name: 'login' }"
            class="my-auto me-2 text-xl font-semibold leading-6 text-gray-900 hover:text-gray-700">
            로그인
          </RouterLink>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
            stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 4.5 21 12m0 0-7.5 7.5M21 12H3" />
          </svg>
        </template>
      </div>
    </nav>
    <Dialog class="lg:hidden" @close="mobileMenuOpen = false" :open="mobileMenuOpen">
      <div class="fixed inset-0 z-50" />
      <DialogPanel
        class="fixed inset-y-0 right-0 z-50 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
        <div class="flex items-center justify-end">
          <div class="flex">
          <div v-show="isLogin" class="flex me-5">
            <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton
                class="shadow-lg border-[1px] border-gray-300 hover:border-gray-600 me-4 relative flex rounded-full text-sm">
                <span class="absolute -inset-1.5" />
                <span class="sr-only">Open user menu</span>
                <img class="h-10 w-10 bg- rounded-full"
                  src="@/assets/logo_onlyHouse.png"
                  alt="" />
              </MenuButton>
            </div>
            <transition enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95">
              <MenuItems
                class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <MenuItem v-slot="{ active }">
                <RouterLink :to="{name: 'user-mypage'}"
                  :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">회원 정보</RouterLink>
                </MenuItem>
                <MenuItem v-slot="{ active }" @click="logout">
                <a href="#" :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">Logout</a>
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
          <div>
            
          </div>
        </div>
          <button type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700" @click="mobileMenuOpen = false">
            <span class="sr-only">Close menu</span>
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        </div>
        
        <div class="mt-6 flow-root">
          <div class="my-6 divide-y divide-gray-500/10">
            <template v-for="item in menuList" :key="item.routeName">
              <template v-if="item.show">
                <div class="space-y-2 py-6">
                  <RouterLink :to="{ name: item.routeName }" class="text-lg font-semibold leading-6 text-gray-900">{{
                    item.name }}</RouterLink>
                </div>
              </template>
            </template>
            <div v-show="!isLogin" class="py-6 flex justify-end me-3">
              <RouterLink :to="{ name: 'login' }"
                class="my-auto me-2 text-xl font-semibold leading-6 text-gray-900 hover:text-gray-700">
                로그인
              </RouterLink>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 4.5 21 12m0 0-7.5 7.5M21 12H3" />
              </svg>
            </div>
          </div>
        </div>
      </DialogPanel>
    </Dialog>
  </header>
</template>

<script setup>
import { ref } from "vue";
import { Dialog, DialogPanel, Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/vue";
import { Bars3Icon, XMarkIcon } from "@heroicons/vue/24/outline";
import { useMenuStore } from '@/stores/menu';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const menuStore = useMenuStore();
const memberStore = useMemberStore();
const { menuList, loginMenu } = storeToRefs(menuStore);
const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore
const { changeMenuState } = menuStore;

const router = useRouter();
const logout = async () => {
  await userLogout();
  changeMenuState(); // 로그아웃 후 메뉴 상태 변경
  router.push({ name: "home" }); // 로그아웃 후 홈 페이지로 이동
};

const mobileMenuOpen = ref(false);

</script>

<style scoped></style>
