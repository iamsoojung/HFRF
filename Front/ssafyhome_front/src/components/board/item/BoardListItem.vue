<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="grid grid-cols-8 text-center shadow-sm p-1.5">
    <th scope="col-span-1">{{ article.articleNo }}</th>
    <td class="col-span-4">
      <router-link
        :to="{ name: 'boardView', params: { articleno: article.articleNo } }"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userName }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime }}</td>
  </tr>
  <!-- <tr class="grid grid-cols-8">
              <th class="col-span-1">글번호</th>
              <th class="col-span-4">제목</th>
              <th class="col=span-2">작성자</th>
              <th>조회수</th>
              <th>작성일</th>
            </tr> -->
</template>

<style scoped>
</style>
