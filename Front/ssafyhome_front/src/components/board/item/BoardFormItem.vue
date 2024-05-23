<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from '@/stores/member';
import { getModifyArticle, modifyArticle } from "@/api/board";
import { Quill } from "@vueup/vue-quill";
import { storeToRefs } from "pinia"
import axios from 'axios';
import Swal from 'sweetalert2';

const memberStore = useMemberStore()
const router = useRouter();
const route = useRoute();
const { userInfo } = storeToRefs(memberStore)
const { type } = defineProps({ type: String });

const isUseId = ref(false);
const subjectErrMsg = ref("");
const contentErrMsg = ref("");
const files = ref([]);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: userInfo.userId,
  userName: "",
  hit: 0,
  registerTime: "",
});

const eventlogin = () => {
  article.value.userId = userInfo.value.userId;
}

if (type === "modify") {
  const { articleno } = route.params;
  console.log(`${articleno}번 글을 수정합니다.`);
  getModifyArticle(
    articleno,
    ({ data }) => {
      console.log(data)
      article.value = data;
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

// async function getFileName(files) {
//   const file = files[0];
//   fileName.value = file;
//   await base64(file);
// }

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
    // alert(subjectErrMsg.value);
    Swal.fire({
      icon: 'error',
      title: subjectErrMsg.value,
      confirmButtonColor: '#ff9999',
    })
  } else if (contentErrMsg.value) {
    // alert(contentErrMsg.value);
    Swal.fire({
      icon: 'success',
      title: 'contentErrMsg.value',
      confirmButtonColor: '#ff9999',
    })
  } else {
    article.value.content = JSON.stringify(quill.value.getContents())
    type === "regist" ? writeArticle() : updateArticle();
  }
}

const handleFileUpload = (event) => {
  files.value = event.target.files;
};

async function writeArticle() {
  // registArticle(
  //   article.value,
  //   (response) => {
  //     const msg = response.status === 201 ? "글 등록이 완료되었습니다." : "글 등록 처리 중 문제가 발생했습니다.";
  //     alert(msg);
  //     moveList();
  //   },
  //   (error) => console.log(error)
  // );
  const formData = new FormData();
  formData.append("userId", article.value.userId);
  formData.append("userName", article.value.userName);
  formData.append("subject", article.value.subject);
  formData.append("content", article.value.content);
  formData.append("hit", article.value.hit);
  for (let i = 0; i < files.value.length; i++) {
    formData.append("fileInfos", files.value[i]);
  }

  try {
    await axios({
      method: "POST",
      url: 'http://localhost/board',
      mode: "cors",
      headers: {
        "Content-Type": "multipart/form-data",
      },
      data: formData,
    });
    // 성공 시에 필요한 작업 수행
    Swal.fire({
      icon: 'success',
      title: '글 등록이 완료되었습니다.',
      confirmButtonColor: '#429f50',
    })
    moveList();
  } catch (error) {
    // 실패 시에 필요한 작업 수행
    console.error(error);
    Swal.fire({
      icon: 'error',
      title: '글 등록 중 오류가 발생했습니다.',
      confirmButtonColor: '#ff9999',
    })
  }
}

async function updateArticle() {
  console.log(`${article.articleNo}번 글을 수정합니다.`, article.value);
  modifyArticle(
    article.value,
    (response) => {
      // const msg = response.status === 200 ? "글 정보가 수정되었습니다." : "글 수정 처리 중 문제가 발생했습니다.";
      // alert(msg);
      if (response.status === 200) {
        Swal.fire({
          icon: 'success',
          title: '글 정보가 수정되었습니다.',
          confirmButtonColor: '#429f50',
        })
      } else {
        Swal.fire({
          icon: 'error',
          title: '글 수정 처리 중 문제가 발생했습니다.',
          confirmButtonColor: '#ff9999',
        })
      }
      moveList();
    },
    (error) => console.log(error)
  );
  // const formData = new FormData();
  // formData.append("articleno", route.params.articleno)
  // formData.append("userId", article.value.userId);
  // formData.append("userName", article.value.userName);
  // formData.append("subject", article.value.subject);
  // formData.append("content", article.value.content);
  // formData.append("hit", article.value.hit);
  // for (let i = 0; i < files.value.length; i++) {
  //   formData.append("fileInfos", files.value[i]);
  // }

  // try {
  //   await axios({
  //     method: "PUT",
  //     url: 'http://localhost/board',
  //     mode: "cors",
  //     headers: {
  //       "Content-Type": "multipart/form-data",
  //     },
  //     data: formData,
  //   });
  //   // 성공 시에 필요한 작업 수행
  //   alert("글 수정이 완료되었습니다.");
  //   moveList();
  // } catch (error) {
  //   // 실패 시에 필요한 작업 수행
  //   console.error(error);
  //   alert("글 수정 중 오류가 발생했습니다.");
  // }
}

function moveList() {
  router.replace({ name: "boardList" });
}

const toolbarOptions = [
  [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
  ['blockquote', 'code-block'],
  ['link', 'image', 'formula'],

  [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'list': 'check' }],
  [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
  [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
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
  },)
  if (type === 'modify') {
    quill.value.setContents(JSON.parse(article.value.content));
  }
  quill.value.on(Quill.events.TEXT_CHANGE, update)
}, 150
)
function formatDelta(delta) {
  return JSON.stringify(delta.ops, null, 2)
}
function update(delta) {
  const contents = quill.value.getContents();
  console.log(contents)
  let html = formatDelta(contents)
  if (delta) {
    html = html + formatDelta(delta);
  }
  article.value.content = html;
}
</script>
<template>
  <form @submit.prevent="onSubmit" class="form" enctype="multipart/form-data">
    <div class="p-2 border-b-[1px] border-gray-400 text-start">
      <label for="userid" class="ms-2 me-4 font-bold text-lg">작성자 </label>
      <span>{{ userInfo.userName }}</span>
    </div>
    <div class="p-2 shadow-sm text-start">
      <label for="subject" class="ms-2 me-5 font-bold text-lg text-end">제목 </label>
      <input type="text" class="focus:ring-0 focus:ring-gray-200 w-[600px] border-0 focus:border-0" v-model="article.subject"
        @input="validateSubject(article.subject)" placeholder="제목을 입력해주세요.." />
      <span v-if="subjectErrMsg" class="error">{{ subjectErrMsg }}</span>
    </div>
    <div class="mb-3 pb-3 shadow-sm">
      <div id="editor" class="min-h-[500px]">
        <div id="input-text"></div>
        <span v-if="contentErrMsg" class="error">{{ contentErrMsg }}</span>
      </div>
    </div>
    <div class="mb-3 pb-3 shadow-sm text-start">
      <label for="upload-image" class="mb-2 block">이미지</label>
      <input type="file" class="form-control border" id="upfile" @change="handleFileUpload" multiple>
    </div>
    <div class="text-center">
      <button type="submit" class="bg-green-500 text-white hover:bg-green-600 p-1 rounded-md" v-if="type === 'regist'" @click="eventlogin">
        글 작성
      </button>
      <button type="submit" class="bg-blue-500 text-white hover:bg-blue-600 rounded-md p-1 m-2" v-else>
        글 수정
      </button>
      <button type="button" class="hover:bg-gray-100 border-solid border-[1px] rounded-md p-1 m-2" @click="moveList">
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
