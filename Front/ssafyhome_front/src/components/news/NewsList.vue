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
        <div class="border-y w-full h-1 mb-10"></div>
        <div class="container">
            <div>
                <ul class="grid grid-cols-2">
                    <a :href="news.originallink" target="_blank"
                        class="hover:bg-gray-100 shadow-lg rounded-xl border-2 col-span-1 my-5 py-10 mx-5 px-5"
                        v-for="news in newsList" :key="news.originallink">
                        <a class="font-bold text-2xl">{{ news.title }}</a>
                        <div class="text-xs mb-5">{{ news.pubDate }}</div>
                        <div>
                            {{ news.description }}
                        </div>
                    </a>
                </ul>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
