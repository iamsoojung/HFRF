<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
  return (
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1
  );
});

const endPage = computed(() => {
  let lastPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize +
    navigationSize;
  return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
  return (
    parseInt((props.totalPage - 1) / navigationSize) * navigationSize <
    props.currentPage
  );
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
  //   return Array(end - start + 1)
  //     .fill()
  //     .map((val, i) => start + i);
}

function onPageChange(pg) {
  console.log(pg + "로 이동!!!");
  emit("pageChange", pg);
}
</script>

<template>
  <!-- <div class="row">
    <ul class="pagination justify-content-center">
      <li class="page-item">
        <a class="page-link" @click="onPageChange(1)">최신</a>
      </li>
      <li class="page-item">
        <a class="page-link" @click="onPageChange(startPage == 1 ? 1 : startPage - 1)">이전</a>
      </li>
      <template v-for="pg in range(startPage, endPage)" :key="pg">
        <li :class="currentPage === pg ? 'page-item active' : 'page-item'">
          <a class="page-link" @click="onPageChange(pg)">{{ pg }}</a>
        </li>
      </template>
<li class="page-item">
  <a class="page-link" @click="onPageChange(endRange ? totalPage : endPage + 1)">다음</a>
</li>
<li class="page-item"><a class="page-link" @click="onPageChange(totalPage)">마지막</a></li>
</ul>
</div> -->
  <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
    <div>
      <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
        <a @click="onPageChange(startPage == 1 ? 1 : startPage - 1)"
          class="relative inline-flex items-center rounded-l-md px-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
            stroke="currentColor" class="w-4 h-4">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </a>
        <template v-for="pg in range(startPage, endPage)" :key="pg">
          <a class="border border-slate-300 relative z-10 inline-flex items-center px-3 text-sm font-semibold focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            @click="onPageChange(pg)">{{ pg }}</a>
        </template>
        <a @click="onPageChange(endRange ? totalPage : endPage + 1)"
          class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
          <span class="sr-only">Next</span>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
            stroke="currentColor" class="w-4 h-4">
            <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
          </svg>
        </a>
      </nav>
    </div>
  </div>
</template>

<style scoped>
a {
  cursor: pointer;
}
</style>
