<script setup>
import { ref, onMounted } from 'vue';
import { listNews } from '@/api/news';

const newsList = ref([]);

onMounted(() => {
    getNewsList();
});

const getNewsList = () => {
    listNews(
        ({ data }) => {
            newsList.value = data.items;
        },
        (error) => {
            console.log(error);
        }
    )
};
</script>

<template>
    <div class="container">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">뉴스 게시판</mark>
        </h2>
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <ul class="list-group">
                    <li v-for="news in newsList" :key="news.originallink" class="list-group-item">
                        <a :href="news.originallink" target="_blank">{{ news.title }}</a>
                        <span class="badge bg-primary">{{ news.pubDate }}</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>
