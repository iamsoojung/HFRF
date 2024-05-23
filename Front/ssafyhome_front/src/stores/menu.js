import { ref } from 'vue';
import { defineStore } from 'pinia'

export const useMenuStore = defineStore("menuStore", () => {
    const menuList = ref([
        { name: "집 찾기", show: true, routeName: "map" },
        { name: "관심 목록", show: true, routeName: "favorite" },
        { name: "매물 등록", show: true, routeName: "property" },
        { name: "질문 게시판", show: true, routeName: "boardList" },
        { name: "뉴스", show: true, routeName: "newsList" },
    ]);

    const loginMenu = ref([
        { name: "로그인", routeName: "login" },
        { name: "로그아웃", routeName: "home" },
    ])

    const changeMenuState = () => {
        const loginMenuIndex = menuList.value.findIndex(item => item.routeName === 'login');
        if (loginMenuIndex !== -1) {
            isLogin.value = !isLogin.value; // 로그인 상태에 따라 로그인 버튼 보이기/숨기기 설정
            const logoutMenuIndex = menuList.value.findIndex(item => item.routeName === 'home');
            if (logoutMenuIndex !== -1) {
                isLogin.value = !isLogin.value; // 로그인 상태에 따라 로그아웃 버튼 보이기/숨기기 설정
            }
        }
    };    
    
    return {
        menuList,
        loginMenu,
        changeMenuState,
    }
}, {persist: {storage: localStorage}});