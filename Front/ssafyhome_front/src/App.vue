<script setup>
import { RouterView } from "vue-router";
import Footer from '@/components/layout/Footer.vue'
</script>

<template>
  <div class="bg-white" style="height: auto; min-height: 100%; padding-bottom: 75px">
    <RouterView></RouterView>
  </div>
  <Footer/>
</template>

<style scoped></style>
