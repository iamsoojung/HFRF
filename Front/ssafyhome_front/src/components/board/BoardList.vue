<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoardList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "boardWrite" });
};
</script>

<template>
  <div>
    <div>
      <div class="flex justify-end mb-3">
        <button type="button" class="border-solid border-2 p-1 rounded-md me-10" @click="moveWrite">글쓰기</button>
      </div>
      <div>
        <table class="container">
          <thead>
            <tr
              class="grid grid-cols-8 shadow-sm p-1 text-white bg-gradient-to-r from-red-500 via-green-300 to-purple-500">
              <th class="col-span-1">글번호</th>
              <th class="col-span-4">제목</th>
              <th>작성자</th>
              <th>조회수</th>
              <th>작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem v-for="article in articles" :key="article.articleNo" :article="article"></BoardListItem>
          </tbody>
        </table>
      </div>
      <div class="mt-8">
        <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
        <form class="mt-3 d-flex">
          <div>
            <VSelect class="border-solid border-2 ps-2 p-0.5 rounded-md shadow-sm" :selectOption="selectOption"
              @onKeySelect="changeKey" />
            <input type="text" class="border-solid border-2 relative px-3 m-3 p-0.5 rounded-md shadow-sm"
              v-model="param.word" placeholder="검색어..." />
            <button class="border-solid border-2 px-2 p-1 rounded-md shadow-sm bg-black text-white" type="button"
              @click="getArticleList">
              검색
            </button>
          </div>
        </form>
      </div>

    </div>
  </div>
</template>

<style scoped></style>
