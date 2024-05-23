<script setup>
import Header from "@/components/layout/Header.vue";
import { listNews } from '@/api/news';
import { listArticle } from "@/api/board.js";
import { ref, onMounted } from 'vue'
import { initializeChannelIO } from "@/util/initializeChannelIO";

const newsList = ref([]);
const searchText = ref("")
const articles = ref([]);
const notices = ref([]);
const param = {
  pgno: 1,
  spp: 5,
  key: "",
  word: "",
};
const noticeParam = {
  pgno: 1,
  spp: 20,
  key: "user_id",
  word: "admin",
}

onMounted(() => {
  getNoticeList();
  getArticleList();
  initializeChannelIO();
  getNewsList();
});

const getNewsList = () => {
    listNews(
        ({ data }) => {
            newsList.value = data.items.slice(5);
        },
        (error) => {
            console.log(error);
        }
    )
};

const getNoticeList = async () => {
  let count = 0;
  let option = []
  while(count < 5) {
    await listArticle(
      noticeParam,
      ({ data }) => {
        if(data.articles.length == 0) {
          count = 5
        }
        data.articles.forEach((article) => {
          if(count < 5) {
            option.push(article)
            count++
          }
        })
      },
      (error) => {
        console.log(error);
      }
    )
    noticeParam.pgno++
  }
  notices.value = option
}
const getArticleList = async () => {
  let count = 0;
  let option = []
  while(count < 5) {
    await listArticle(
      param,
      ({ data }) => {
        if(data.articles.length == 0) {
          count = 5
        }
        data.articles.forEach((article) => {
          if(article.userId != 'admin') {
          if(count < 5) {
            option.push(article)
            count++
          }
        }
        })
      },
      (error) => {
        console.log(error);
      }
    );
    param.pgno++
  }
  articles.value = option
};
</script>

<template>
  <Header />
  <div class="relative isolate min-h-[805px]">
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
    <div class="min-w-[900px] min-h-[450px] grid grid-cols-2 mx-auto">
      <div class="col-span-1 py-16 sm:py-24 lg:py-28">
        <div class="text-center">
        <h1 class="mb-[50px] text-3xl font-bold tracking-tight text-gray-800 sm:text-5xl">
          어떤 집을 찾고 계세요?
        </h1>
      </div>
      <div class="flex justify-center ">
        <form class="max-w-lg mx-auto">
          <div class="flex items-center">
            <div class="relative flex w-[400px]">
              <input v-model="searchText" type="search" id="search-dropdown"
                class="block w-full pl-5 pr-10 py-2.5 text-sm text-gray-900 rounded-lg border-[1px] border-pink-300 focus:border-none"
                placeholder="지역명, 아파트명을 입력해주세요." required />
              <RouterLink :to="{name: 'map', query: { key: searchText}}"
                class="absolute inset-y-0 right-0 p-2.5 text-sm font-medium h-full text-white bg-pink-400 rounded-e-lg border border-pink-400 hover:bg-pink-500 focus:outline-none">
                <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                  viewBox="0 0 20 20">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                </svg>
                <span class="sr-only">Search</span>
              </RouterLink>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="col-span-1 bg-[url('@/assets/home.jpg')] bg-cover bg-center">
    </div>
    </div>
    <div class="grid grid-cols-3 xl:grid-cols-8 pt-5 pb-20">
      <div class="col-span-1 xl:col-span-2 xl:col-start-2 p-5 ms-5">
        <div class="font-bold text-xl border-t-[1px] pt-2 pb-2">
          공지사항
        </div>
        <div class="grid grid-cols-4 ps-4 pb-1" v-for="notice in notices" :key="notice.articleNo">
          <router-link :to="{ name: 'boardView', params: { articleno: notice.articleNo } }"
            class="col-span-1 truncate">{{ notice.userName }}</router-link>
          <router-link :to="{ name: 'boardView', params: { articleno: notice.articleNo } }"
            class="col-span-3 truncate">{{ notice.subject }}</router-link>
        </div>
      </div>
      <div class="col-span-1 xl:col-span-2 p-5 ms-5">
        <div class="font-bold text-xl border-t-[1px] pt-2 pb-2">
          뉴스
        </div>
        <div class="ps-4" >
          <a :href="news.originallink" v-for="news in newsList" :key="news.orininallink">
            <div class="truncate pb-1" v-html="news.title"></div>
          </a>
        </div>
      </div> 
      <div class="col-span-1 xl:col-span-2 p-5 ms-5">
        <div class="font-bold text-xl border-t-[1px] pt-2 pb-2">
          QnA 게시판
        </div>
        <div class="grid grid-cols-4 ps-4 pb-1" v-for="article in articles" :key="article.articleNo">
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo } }"
            class="col-span-1 truncate">{{ article.userName }}</router-link>
          <router-link :to="{ name: 'boardView', params: { articleno: article.articleNo } }"
            class="col-span-3 truncate">{{ article.subject }}</router-link>
        </div>
      </div>
      <div class="col-span-1"></div>
    </div>
    
  </div>
</template>

<style scoped></style>
