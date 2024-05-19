<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="grid grid-cols-8 text-center shadow-sm p-1">
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
    <td class="hidden xl:block">{{ article.registerTime }}</td>
    <td class="block :hidden">{{ article.registerTime.slice(-0, -8) }}</td>
  </tr>
</template>

<style scoped>
</style>
