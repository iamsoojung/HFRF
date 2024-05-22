<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const { type } = defineProps({ type: String });

const isUseId = ref(false);
const subjectErrMsg = ref("");
const contentErrMsg = ref("");
const fileName = ref(null);

let article = {
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
};

if (type === "modify") {
  const { articleno } = route.params;
  console.log(`${articleno}번 글을 수정합니다.`);
  getModifyArticle(
    articleno,
    ({ data }) => {
      article = data;
      isUseId.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
  isUseId.value = true;
}

function validateSubject(value) {
  const len = value.length;
  subjectErrMsg.value = len === 0 || len > 30 ? "제목을 확인해 주세요!!!" : "";
}

function validateContent(value) {
  const len = value.length;
  contentErrMsg.value = len === 0 || len > 500 ? "내용을 확인해 주세요!!!" : "";
}

async function getFileName(files) {
  const file = files[0];
  fileName.value = file;
  await base64(file);
}

function base64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const dataURL = e.target.result;
      const previewImage = document.getElementById('preview');
      previewImage.src = dataURL;
      resolve(dataURL);
    };
    reader.onerror = (error) => reject(error);
    reader.readAsDataURL(file);
  });
}

function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article,
    (response) => {
      const msg = response.status === 201 ? "글 등록이 완료되었습니다." : "글 등록 처리 중 문제가 발생했습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function updateArticle() {
  console.log(`${article.articleNo}번 글을 수정합니다.`, article);
  modifyArticle(
    article,
    (response) => {
      const msg = response.status === 200 ? "글 정보가 수정되었습니다." : "글 수정 처리 중 문제가 발생했습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.replace({ name: "boardList" });
}
</script>

<template>
  <QuillEditor theme="snow" toolbar="full"/>

  
  <form @submit.prevent="onSubmit" class="form" enctype="multipart/form-data">
    <div class="mb-3 pb-3 shadow-sm">
      <label for="userid" class="ms-2 me-4">작성자 ID</label>
      :
      <input type="text" class="ms-4" v-model="article.userId" :disabled="isUseId" placeholder="작성자 ID" />
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <label for="subject" class="ms-2 me-4">제목</label>
      :
      <input type="text" class="ms-4" v-model="article.subject" @input="validateSubject(article.subject)" placeholder="제목" />
      <span v-if="subjectErrMsg" class="error">{{ subjectErrMsg }}</span>
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <label class="absolute ms-2" for="content">내용
        <span class="ms-4">:</span>
      </label>
      <textarea class="mx-[80px] size-11/12 resize-none " v-model="article.content" @input="validateContent(article.content)" rows="10"></textarea>
      <span v-if="contentErrMsg" class="error">{{ contentErrMsg }}</span>
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <label for="upload-image" class="mb-2 block">이미지 업로드</label>
      <input type="file" id="upload-image" hidden @change="getFileName($event.target.files)"/>
      <label for="upload-image">
        <img src="@/assets/logo.png" id="preview" width="300px" />
      </label>
    </div>
    <div class="text-center">
      <button type="submit" class="border-solid border-2 p-1 rounded-md" v-if="type === 'regist'">
        글 작성
      </button>
      <button type="submit" class="border-solid border-2 rounded-md p-1 m-2" v-else>
        글 수정
      </button>
      <button type="button" class="border-solid border-2 rounded-md p-1 m-2" @click="moveList">
        목록으로
      </button>
    </div>
  </form>
</template>

<style scoped>
.error {
  color: red;
  font-size: 0.75rem;
}
</style>
