<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from '@/stores/member';
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import { Quill } from "@vueup/vue-quill";

const memberStore = useMemberStore()
const { userInfo } = memberStore
const router = useRouter();
const route = useRoute();

const { type } = defineProps({ type: String });

const isUseId = ref(false);
const subjectErrMsg = ref("");
const contentErrMsg = ref("");
const fileName = ref(null);

let article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: userInfo.userId,
  userName: "",
  hit: 0,
  registerTime: "",
});

if (type === "modify") {
  const { articleno } = route.params;
  console.log(`${articleno}번 글을 수정합니다.`);
  getModifyArticle(
    articleno,
    ({ data }) => {
      console.log(data)
      article.value = data;
      const playground = document.querySelector('#input-text');
      console.log(JSON.parse(article.value.content))
      quill.value.setContents(JSON.parse(article.value.content));
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
    console.log(quill.value.getContents())
    article.value.content = JSON.stringify(quill.value.getContents())
    type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article.value,
    (response) => {
      const msg = response.status === 201 ? "글 등록이 완료되었습니다." : "글 등록 처리 중 문제가 발생했습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function updateArticle() {
  console.log(`${article.articleNo}번 글을 수정합니다.`, article.value);
  modifyArticle(
    article.value,
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

const toolbarOptions = [
  [{ 'font': [] }],
  [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
  ['blockquote', 'code-block'],
  ['link', 'image', 'video', 'formula'],

  [{ 'list': 'ordered'}, { 'list': 'bullet' }, { 'list': 'check' }],
  [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
  [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
  [{ 'direction': 'rtl' }],                         // text direction

  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
  [{ 'align': [] }],

  ['clean']                                         // remove formatting button
];
const quill = ref();
setTimeout(() => {
  quill.value = new Quill("#editor", {
    modules: {
      toolbar: toolbarOptions
    },
    theme: 'snow',
  },
  3000)
}
)
</script>

<!-- 
    
</script>

<template>
  <button @click="onButton" class="border-2">asdfasdf</button>
  <div>
    
    <div id="playground"></div>
  </div>
{{ text }}
</template>

<style scoped>
</style> -->
<template>
  <form @submit.prevent="onSubmit" class="form" enctype="multipart/form-data">
    <div class="p-1 border-b-[1px] border-gray-400 text-start">
      <label for="userid" class="ms-2 me-4 font-bold">작성자 </label>
      <span>{{ userInfo.userName }}</span>
    </div>
    <div class="p-1 shadow-sm text-start">
      <label for="subject" class="ms-2 me-5 font-bold">제목 </label>
      <input type="text" class="focus:ring-1 focus:ring-gray-200 min-w-[300px] border-0" v-model="article.subject" @input="validateSubject(article.subject)" placeholder="제목을 입력해주세요.." />
      <span v-if="subjectErrMsg" class="error ms-5">{{ subjectErrMsg }}</span>
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <!-- <textarea class="mx-[80px] size-11/12 resize-none " v-model="article.content" @input="validateContent(article.content)" rows="10"></textarea> -->
      <div id="editor" class="min-h-[500px]">
      <div id="input-text"></div>
      <span v-if="contentErrMsg" class="error">{{ contentErrMsg }}</span>
    </div>
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
