<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.replace({ name: "boardList" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3 pb-3 shadow-sm">
      <label for="userid" class="ms-2 me-4">작성자 ID</label>
      :
      <input type="text" class="ms-4" v-model="article.userId" :disabled="isUseId" placeholder="작성자 ID" />
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <label for="subject" class="ms-2 me-4">제목</label>
      :
      <input type="text" class="ms-4" v-model="article.subject" placeholder="제목" />
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <label class="absolute ms-2" for="content">내용
        <span class="ms-4">:</span>
      </label>
      <textarea class="mx-[80px] size-11/12" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="text-center">
      <button type="submit" class="border-solid border-2 p-1 rounded-md" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="border-solid border-2 rounded-md p-1 m-2" v-else>
        글수정
      </button>
      <button type="button" class="border-solid border-2 rounded-md p-1 m-2" @click="moveList">
        목록으로
      </button>
    </div>
  </form>
</template>

<style scoped></style>
