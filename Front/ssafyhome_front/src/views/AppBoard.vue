<script setup>
import Header from '@/components/layout/Header.vue';
</script>

<template>
  <Header />
  <div class="min-w-[750px] max-w-[1250px] min-h-[775px] container text-center mt-[120px] mx-auto">
    <div class="font-bold text-3xl mb-[30px]">QnA 게시판</div>
    <RouterView></RouterView>
  </div>
</template>

<style></style>
