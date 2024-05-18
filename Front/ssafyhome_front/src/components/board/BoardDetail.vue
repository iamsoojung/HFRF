<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;
const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "boardList" });
}

function moveModify() {
  router.push({ name: "boardModify", params: { articleno } });
}

function onDeleteArticle() {
  deleteArticle(
    articleno,
    (response) => {
      if (response.status == 200) {
        let msg = "글삭제가 완료되었습니다.";
        alert(msg);
        moveList();
      }
    },
    (error) => {
      console.log(error);
    }
  );
}
</script>

<template>
  <div class="container">
    <div>
      <div class="text-start">
        <div class="text-lg my-2 border-y-2 p-1">
          <h2 class="px-5">
            {{ article.articleNo }}. {{ article.subject }}
          </h2>
        </div>
        <div>
          <div>
            <div class="grid grid-cols-2">
              <div class="px-5 pb-2 border-b-2 col-span-1">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6 inline-block"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z"
                  />
                </svg>
                <span class="ms-3 font-bold">{{ article.userId }}</span>
              </div>
              <div class="pb-2 border-b-2 col-span-1 text-end">
                {{ article.registerTime }}
              </div>
            </div>
            <div class="text-end mt-3">
              <span class="me-5">조회 : {{ article.hit }}</span>
              <span>댓글 : ?</span>
            </div>
          </div>
          <div class="my-5">
            {{ article.content }}
          </div>
          <div class="container">
          <div class="flex justify-end">
            <button
              type="button"
              class="border-2 rounded-md p-1"
              @click="moveList"
            >
              글목록
            </button>
            <button
              v-if="userInfo && userInfo.userId == article.userId"
              type="button"
              class="border-2 rounded-md p-1"
              @click="moveModify"
            >
              글수정
            </button>
            <button
              v-if="userInfo && userInfo.userId == article.userId"
              type="button"
              class="border-2 rounded-md p-1"
              @click="onDeleteArticle"
            >
              글삭제
            </button>
          </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
