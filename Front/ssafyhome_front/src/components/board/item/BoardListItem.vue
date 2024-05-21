<script setup>
defineProps({ article: Object });
const today = new Date();
const dateFormat = (date) => {
  let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return date.getFullYear() + '-' + month + '-' + day
}
</script>

<template>
  <tr class="flex text-center p-1 border-b-[1px]">
    <th class="basis-1/12">{{ article.articleNo }}</th>
    <td class="basis-7/12 text-start px-10">
      <router-link
        :to="{ name: 'boardView', params: { articleno: article.articleNo } }"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <div class="basis-4/12 flex">
    <td class="basis-6/12">{{ article.userName }}</td>
    <td class="basis-2/12">{{ article.hit }}</td>
    <div class="basis-4/12">
    <div>
      {{ article.registerTime.slice(0, 10) == dateFormat(today) ?
         article.registerTime.slice(10, 19) : article.registerTime.slice(0, 10) }}
           </div>
    </div>
  </div>
  </tr>
</template>

<style scoped>
</style>
