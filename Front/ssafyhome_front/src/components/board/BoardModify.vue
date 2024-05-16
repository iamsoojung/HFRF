<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div>
      <div>
        <h2 class="my-3 py-3 shadow-sm text-center">
          글수정
        </h2>
      </div>
      <div class="text-start">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
