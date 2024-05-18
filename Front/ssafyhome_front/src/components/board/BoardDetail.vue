<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { listComment, writeComment, getModifyComment, modifyComment, deleteComment, getCommentCnt } from "@/api/board";
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;
const article = ref({});
const comments = ref([]);
const commentCount = ref(0);

onMounted(() => {
  getArticle();
  getComments();
  getCommentCount();
  console.log(commentCount)
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

const getComments = () => {
  listComment(
    articleno,
    ({ data }) => {
      comments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getCommentCount = () => {
  getCommentCnt(
    articleno,
    ({ data }) => {
      commentCount.value = data.count;
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

function onDeleteComment(commentNo) {
  deleteComment(
    commentNo,
    (response) => {
      if (response.status == 200) {
        let msg = "댓글삭제가 완료되었습니다.";
        alert(msg);
        comments.value = comments.value.filter(comment => comment.commentNo !== commentNo);
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

function submitComment() {
  const content = document.getElementById("comment-content").value;
  const comment = {
    articleNo: articleno,
    userId: userInfo.value.userId,
    content: content
  };

  writeComment(
    comment,
    () => {
      getComments();
      document.getElementById("comment-content").value = "";
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
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                  stroke="currentColor" class="w-6 h-6 inline-block">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
                </svg>
                <span class="ms-3 font-bold">{{ article.userId }}</span>
              </div>
              <div class="pb-2 border-b-2 col-span-1 text-end">
                {{ article.registerTime }}
              </div>
            </div>
            <div class="text-end mt-3">
              <span class="me-5">조회 : {{ article.hit }}</span>
              <span>댓글 : {{ commentCount }}</span>
            </div>
          </div>
          <div class="my-5">
            {{ article.content }}
          </div>
          <div class="container">
            <div class="flex justify-end">
              <button type="button" class="border-2 rounded-md p-1" @click="moveList">
                글목록
              </button>
              <button v-if="userInfo && userInfo.userId == article.userId" type="button" class="border-2 rounded-md p-1"
                @click="moveModify">
                글수정
              </button>
              <button v-if="userInfo && userInfo.userId == article.userId" type="button" class="border-2 rounded-md p-1"
                @click="onDeleteArticle">
                글삭제
              </button>
            </div>
          </div>
        </div>

        <div class="flex-col w-full mt-4 justify-center items-center" style="min-width: 500px">
          <div class="bg-white w-full dark:bg-gray-700">
            <div class="flex-col w-full mb-2" id="main-comments">
              <span class="text-stone-600 dark:text-gray-400 font-semibold text-lg">Comments</span>
              <div v-if="comments.length === 0" class="flex justify-center border-t border-b py-1" id="no-comments">
                <div class="-space-x-2 dark:text-gray-400">댓글 없음</div>
              </div>
              <div v-else>
                <div v-for="comment in comments" :key="comment.commentNo"
                  class="flex justify-center border-t border-b py-1">
                  <div class="flex items-center space-x-2 dark:text-gray-400">
                    <span class="font-semibold">{{ comment.userId }}:</span>
                    <span>{{ comment.content }}</span>
                    <button v-if="userInfo && userInfo.userId === comment.userId"
                      @click="onDeleteComment(comment.commentNo)"
                      class="text-red-600 hover:text-red-800">Delete</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="px-5 pb-2 border-b-2 col-span-1" v-if="userInfo">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                stroke="currentColor" class="w-6 h-6 inline-block">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
              </svg>
              <span class="ms-3 font-bold">{{ userInfo.userId }}</span>
            </div>
            <div class="mt-3 w-full" id="with-login2" v-if="userInfo">
              <textarea rows="2" class="border p-2 rounded w-full" id="comment-content"
                placeholder="Write something..."></textarea>
            </div>
            <div class="flex my-2 justify-end" id="with-login3" v-if="userInfo">
              <div>
                <button
                  class="px-4 py-1 bg-gray-800 text-white rounded-md font-light hover:bg-gray-700 dark:bg-indigo-600"
                  id="comment-submit" @click="submitComment">
                  등록
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
