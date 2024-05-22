<script setup>
import { defineProps, defineEmits, onMounted, ref } from 'vue';
import { dealByDong } from '@/api/map';
import { OpenAI } from 'openai'

// 모달이 열릴 때 매물 정보 가져오기
onMounted(() => {
    if (props.isModal) {
        getHouse(props.aptCode);
    }
});

// props 받기
const props = defineProps({
    isModal: {
        type: Boolean,
        required: true
    },
    aptCode: {
        type: String,
        required: true
    }
});

// 매물 정보 상태 변수
const houseData = ref([])
// aptCode로 매물 정보 가져오기
const getHouse = (value) => {
    dealByDong(
        { aptCode: value },
        ({ data }) => {
            if (Array.isArray(data)) {
                houseData.value = data;
            } else {
                houseData.value = [data]; // 단일 객체인 경우 배열로 감싸기
            }
        },
        (error) => {
            console.log(error);
        }
    );
};

// ChatGPT의 결과를 담을 상태 변수
const gptResult = ref('');
// 로딩 스피너 제어할 변수
const loadingSpinner = ref(true);
// chatgpt api
const getGPTResponse = async () => {
    try {
        gptResult.value = '';
        loadingSpinner.value = false;

        let data = houseData.value
        if (!data) {
            console.log("매물 정보가 로드되지 않았습니다.");
            return;
        }
        console.log(data)

        const openai = new OpenAI({
            apiKey: `${import.meta.env.VITE_OPENAI_API_KEY}`,
            dangerouslyAllowBrowser: true,
        })

        // 데이터를 문자열로 변환하여 prompt에 추가
        let prompt = "매물 정보에 다음 데이터가 있습니다:\n";
        for (const entry of data) {
            prompt += `거래 일자: ${entry.dealYear}년 ${entry.dealMonth}월 ${entry.dealDay}일, `;
            prompt += `거래 금액: ${entry.dealAmount}만원, `;
            prompt += `면적: ${entry.area}㎡, `;
            prompt += `층수: ${entry.floor}층\n`;
        }
        prompt += "최근 매물 분석을 간단한 레포트 형식으로 알려줘.";

        const response = await openai.chat.completions.create({
            messages: [
                {
                    role: 'user',
                    content: prompt,
                },
            ],
            model: 'gpt-3.5-turbo',
        })
        console.log(prompt)
        // console.log('chatGPT 결과: ', response.choices[0].message.content)

        gptResult.value = response.choices[0].message.content;
        loadingSpinner.value = false;
    } catch (error) {
        console.log('chatGPT: 🚨 에러가 발생했습니다.')
        console.log(error)
    }
}

// 모달 닫기
const emit = defineEmits(['close-modal']);
const closeModal = () => {
    emit('close-modal');
};
</script>

<template>
    <div class="modal-wrap" v-if="props.isModal" @click="closeModal">
        <div class="modal" @click.stop>
            <h5>ChatGPT가 해주는 매물 분석~~!</h5>
            <button class="close-button" @click="getGPTResponse">해봐</button>
            <div>{{ props.aptCode }}</div>
            <p v-if="gptResult">{{ gptResult }}</p>
            <p v-else-if="!loadingSpinner">
            <div role="status">
                <svg aria-hidden="true" class="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
                    viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                        fill="currentColor" />
                    <path
                        d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                        fill="currentFill" />
                </svg>
                <span class="sr-only">Loading...</span>
            </div>
            </p>
            <button class="close-button" @click="closeModal">닫기</button>
        </div>
    </div>
</template>

<style scoped>
.modal-wrap {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(37, 37, 37, 0.4);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal {
    background: #fff;
    border-radius: 20px;
    width: 300px;
    max-width: 90%;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    position: relative;
    padding: 20px;
}

.modal h5 {
    margin: 0;
    padding: 20px 0;
    border-bottom: 1px solid #eee;
    font-size: 18px;
    font-weight: 500;
}

.close-button {
    background-color: #ff5c5c;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 20px;
}

.close-button:hover {
    background-color: #ff3b3b;
}

.close-button:focus {
    outline: none;
}
</style>
