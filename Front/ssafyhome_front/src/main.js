import { createApp } from "vue";
import { createPinia } from "pinia";
import { useKakao } from 'vue3-kakao-maps/@utils';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import App from "./App.vue";
import router from "./router";
import "./assets/tailwind.css";

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

app.component('QuillEditor', QuillEditor)
app.use(pinia);
app.use(router);
// app.use(quillEditor);

useKakao('eb44bbe512c97ded449fc6692128b411');
app.mount("#app");

