<script setup>
import { RouterView } from "vue-router";
import Footer from '@/components/layout/Footer.vue'
</script>

<template>
  <div style="height: auto; min-height: 100%;">
    <RouterView></RouterView>
  </div>
  <Footer/>
</template>

<style scoped></style>
