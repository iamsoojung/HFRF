<script setup>
defineProps({ likeApt: Object });
</script>

<template>
    <div class="p-3 h-[450px]">
        <div class="border-2 w-full h-full">
            <div class="w-full h-[350px] bg-pink-50 text-center">대충 집 사진</div>
            <div class="py-3 px-5 flex justify-between">
                <div>
                <div>{{ likeApt.aptCode }}</div>
                <div>{{ likeApt.dongCode }}</div>
                </div>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                    class="hover:bg-red-500 hover:text-white hover:border-red-600 border-[1px] p-1 text-red-600 bg-white rounded-md shadow-md w-8 h-8 my-auto">
                    <path
                        d="m9.653 16.915-.005-.003-.019-.01a20.759 20.759 0 0 1-1.162-.682 22.045 22.045 0 0 1-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 0 1 8-2.828A4.5 4.5 0 0 1 18 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 0 1-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 0 1-.69.001l-.002-.001Z" />
                </svg>
            </div>
        </div>
    </div>
</template>

<style scoped></style>