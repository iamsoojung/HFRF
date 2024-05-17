<script setup>
import Header from "@/components/layout/Header.vue";
import { listArticle } from "@/api/board.js";
import { ref, onMounted } from 'vue'

const isDropdownOpen = ref(false)

const categories = [
  { id: 1, name: "Mockups" },
  { id: 2, name: "Templates" },
  { id: 3, name: "Design" },
  { id: 4, name: "Logos" }
]

const selectedCategory = ref({ id: 1, name: "All categories" })

const searchText = ref("")

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value
}

const selectCategory = (category) => {
  selectedCategory.value = category;
  isDropdownOpen.value = false;
}

const search = () => {
  // search logic here
}

const articles = ref([]);
const param = {
  pgno: 1,
  spp: 5,
  key: "",
  word: "",
}; 

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  listArticle(
    param,
    ({ data }) => {
      articles.value = data.articles;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <Header />
  <div class="relative isolate px-6 lg:px-8" style="min-height: 650px">
    <div class="absolute inset-x-0 -top-40 -z-10 transform-gpu overflow-hidden blur-3xl sm:-top-80" aria-hidden="true">
      <div
        class="relative left-[calc(50%-11rem)] aspect-[1155/678] w-[36.125rem] -translate-x-1/2 rotate-[30deg] bg-gradient-to-tr from-[#fc9c9c] to-[#fff9a0] opacity-30 sm:left-[calc(50%-30rem)] sm:w-[72.1875rem]"
        style="
          clip-path: polygon(
            74.1% 44.1%,
            100% 61.6%,
            97.5% 26.9%,
            85.5% 0.1%,
            80.7% 2%,
            72.5% 32.5%,
            60.2% 62.4%,
            52.4% 68.1%,
            47.5% 58.3%,
            45.2% 34.5%,
            27.5% 76.7%,
            0.1% 64.9%,
            17.9% 100%,
            27.6% 76.8%,
            76.1% 97.7%,
            74.1% 44.1%
          );
        "></div>
    </div>
    <div class="mx-auto py-16 sm:py-24 lg:py-28">
      <div class="mb-20 flex justify-center">
        <form class="max-w-lg mx-auto" @submit.prevent="search">
          <div class="flex items-center">
            <div class="relative">
              <button id="dropdown-button" @click="toggleDropdown"
                class="w-36 z-10 flex-shrink-0 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-pink-50 border border-red-200 rounded-s-lg hover:bg-pink-100 focus:ring-4 focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600"
                type="button">
                {{ selectedCategory.name }}
                <svg :class="{ 'transform rotate-180': isDropdownOpen }" class="w-2.5 h-2.5 ms-auto" aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="m1 1 4 4 4-4" />
                </svg>
              </button>
              <div id="dropdown" :class="{ 'block': isDropdownOpen, 'hidden': !isDropdownOpen }"
                class="z-20 absolute top-full left-0 mt-1 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdown-button">
                  <li v-for="category in categories" :key="category.id">
                    <button type="button" @click="selectCategory(category)"
                      class="w-full px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                      {{ category.name }}
                    </button>
                  </li>
                </ul>
              </div>
            </div>
            <div class="relative flex w-96">
              <input v-model="searchText" type="search" id="search-dropdown"
                class="block w-full pl-5 pr-10 py-2.5 text-sm text-gray-900 rounded-e-lg border-s-gray-50 border-s-2 border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-s-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500"
                placeholder="Search Mockups, Logos, Design Templates..." required />
              <button type="submit"
                class="absolute inset-y-0 right-0 p-2.5 text-sm font-medium h-full text-white bg-pink-400 rounded-e-lg border border-pink-400 hover:bg-pink-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                  viewBox="0 0 20 20">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                </svg>
                <span class="sr-only">Search</span>
              </button>
            </div>
          </div>
        </form>
      </div>
      <div class="text-center">
        <h1 class="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">
          안녕하세요
        </h1>
        <p class="mt-6 text-2xl leading-8 text-gray-600">반갑습니다</p>
      </div>
    </div>
    <div class="grid grid-cols-4">
      <div class="col-span-1"></div>
      <div class="col-span-1 p-5 ms-5">
        <div class="font-bold text-xl border-t-[1px] pt-2 pb-2">
          공지사항
        </div>
        <div class="grid grid-cols-4 ps-4 pb-1" v-for="article in articles" :key="article.articleNo">
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo }}" class="col-span-1">{{ article.userName }}</router-link>
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo }}" class="col-span-3">{{ article.subject }}</router-link>
        </div>
      </div>
      <div class="col-span-1 p-5 ms-5">
        <div class="font-bold text-xl border-t-[1px] pt-2 pb-2">
          QnA 게시판
        </div>
        <div class="grid grid-cols-4 ps-4 pb-1" v-for="article in articles" :key="article.articleNo">
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo }}" class="col-span-1">{{ article.userName }}</router-link>
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo }}" class="col-span-3">{{ article.subject }}</router-link>
        </div>
      </div>
      <div class="col-span-1"></div>
    </div>
    <div
      class="absolute inset-x-0 top-[calc(100%-13rem)] -z-10 transform-gpu overflow-hidden blur-3xl sm:top-[calc(100%-30rem)]"
      aria-hidden="true">
      <div
        class="relative left-[calc(50%+3rem)] aspect-[1155/678] w-[36.125rem] -translate-x-1/2 bg-gradient-to-tr from-[#fc9c9c] to-[#fff9a0] opacity-30 sm:left-[calc(50%+36rem)] sm:w-[72.1875rem]"
        style="
          clip-path: polygon(
            74.1% 44.1%,
            100% 61.6%,
            97.5% 26.9%,
            85.5% 0.1%,
            80.7% 2%,
            72.5% 32.5%,
            60.2% 62.4%,
            52.4% 68.1%,
            47.5% 58.3%,
            45.2% 34.5%,
            27.5% 76.7%,
            0.1% 64.9%,
            17.9% 100%,
            27.6% 76.8%,
            76.1% 97.7%,
            74.1% 44.1%
          );
        "></div>
    </div>
  </div>
</template>

<style scoped></style>
