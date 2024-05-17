<script setup>
import Header from '@/components/layout/Header.vue';
</script>

<template>
  <Header />
  <div class="container text-center mt-[200px] mx-auto">
    <div class="text-3xl mb-[30px]">자유롭게 글쓰는 공간</div>
    <RouterView></RouterView>
  </div>
</template>

<style></style>
