<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div>
      <div>
        <h2 class="my-3 py-3 shadow-sm text-center text-xl">
          글쓰기
        </h2>
      </div>
      <div class="text-start">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
