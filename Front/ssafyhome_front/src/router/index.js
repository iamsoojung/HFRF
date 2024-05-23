import { createRouter, createWebHistory } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import Swal from 'sweetalert2';

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken, isLogin } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;
  
  let token = sessionStorage.getItem("accessToken");
  if (isLogin.value != false && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value.userId == null) {
    isLogin.value = false;
    Swal.fire({
      icon: 'error',
      title: '로그인 후 이용하세요.',
      confirmButtonColor: '#ff9999',
  });
    next({ name: "login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/AppHome.vue"),
    },
    {
      path: "/map",
      name: "map",
      component: () => import("../views/AppMap.vue"),
    },
    {
      path: "/favorite",
      name: "favorite",
      component: () => import("../views/AppFavorite.vue"),
      beforeEnter: onlyAuthUser
    },
    {
      path: "/house",
      name: "realty",
      component: () => import("../views/AppRealty.vue"),
      redirect: { name: "realtyList" },
      children: [
        {
          path: "/house",
          name: "realtyList",
          component: () => import("@/components/realty/RealtyList.vue"),
          beforeEnter: onlyAuthUser
        },
        {
          path: "/house",
          name: "realtyWrite",
          component: () => import("@/components/realty/RealtyWrite.vue"),
          beforeEnter: onlyAuthUser
        },
      ]
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/AppUser.vue"),
      children: [
        {
          path: "/user/login",
          name: "login",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "/user/join",
          name: "join",
          component: () => import("@/components/user/UserJoin.vue"),
        },
        {
          path: "/user/mypage",
          name: "user-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserMyPage.vue"),
        },
        {
          path: "/user/modify",
          name: "user-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserModify.vue"),
        },
        {
          path: "/user/delete",
          name: "user-delete",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserDelete.vue"),
        }
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/AppBoard.vue"),
      redirect: { name: "boardList" },
      children: [
        {
          path: "/board",
          name: "boardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "/view/:articleno",
          name: "boardView",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "/board",
          name: "boardWrite",
          component: () => import("@/components/board/BoardWrite.vue"),
          beforeEnter: onlyAuthUser
        },
        {
          path: "/modify/:articleno",
          name: "boardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/news",
      name: "news",
      component: () => import("@/views/AppNews.vue"),
      children: [
        {
          path: "/news",
          name: "newsList",
          component: () => import("@/components/news/NewsList.vue"),
        }
      ]
    }
  ],
});

export default router;
