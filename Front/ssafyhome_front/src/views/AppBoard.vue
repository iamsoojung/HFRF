<script setup>
import Header from '@/components/layout/Header.vue';
import Footer from "@/components/layout/Footer.vue"
</script>

<template>
  <Header />
  <div class="container text-center mt-[200px] mx-auto">
    <div class="text-3xl mb-[30px]">QnA 게시판</div>
    <RouterView />
  </div>
  <Footer></Footer>
</template>

<style></style>
