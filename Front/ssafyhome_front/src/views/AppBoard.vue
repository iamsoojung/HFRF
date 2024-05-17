<script setup>
import Header from '@/components/layout/Header.vue';
</script>

<template>
  <Header />
  <div class="min-w-[1000px] container text-center mt-[120px] mx-auto mb-10">
    <div class="xl:text-3xl mb-[30px]">QnA 게시판</div>
    <RouterView></RouterView>
  </div>
</template>

<style></style>
