import { createRouter, createWebHistory } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken, isLogin } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  console.log(isValidToken.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin.value != false && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value.userId == null) {
    isLogin.value = false;
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
      path: "/notice",
      name: "notice",
      component: () => import("../views/AppNotice.vue"),
      redirect: { name: "notice-list" },
      children: [
        {
          path: "/notice",
          name: "noticeList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "/notice/view/:articleno",
          name: "noticeView",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "/notice/write",
          name: "noticeWrite",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "/notice/modify/:articleno",
          name: "noticeModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "user",
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
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/AppBoard.vue"),
      redirect: { name: "board-list" },
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
        },
        {
          path: "/modify/:articleno",
          name: "boardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
