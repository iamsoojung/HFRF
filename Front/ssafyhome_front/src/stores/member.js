import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  join,
  modify,
  deleteUser,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const router = useRouter();
    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);

    const userLogin = async (loginUser) => {
      await userConfirm(
        loginUser,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response;
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
            console.log("sessiontStorage에 담았다", isLogin.value);
          } else {
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const getUserInfo = async () => {
      const token = sessionStorage.getItem("accessToken");
      if (token) {
        let decodeToken = jwtDecode(token);
        await findById(
          decodeToken.userId,
          (response) => {
            if (response.status === httpStatusCode.OK) {
              if (response.data.userInfo != null) {
                isValidToken.value = true;
                userInfo.value = response.data.userInfo;
              } else {
                console.log("유저 정보 없음");
                isLogin.value = false;
              }
            } else {
              console.log("유저 정보 없음");
              isLogin.value = false;
            }
          },
          async (error) => {
            console.error(
              "토큰 만료: ",
              error.response.status,
              error.response.statusText
            );
            isLogin.value = false;
            isValidToken.value = false;
            await tokenRegenerate();
          }
        );
      }
    };

    const tokenRegenerate = async () => {
      await tokenRegeneration(
        userInfo,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            await logout(
              userInfo.value.userId,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료");
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                isLogin.value = false;
                userInfo.value = null;
              }
            );
          }
        }
      );
    };

    const userLogout = async () => {
      if (userInfo.value && userInfo.value.userId) {
        console.log("로그아웃 아이디 : " + userInfo.value.userId);
        await logout(
          userInfo.value.userId,
          (response) => {
            if (response.status === httpStatusCode.OK) {
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              sessionStorage.clear();
            } else {
              console.error("유저 정보 없음");
            }
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        console.warn("로그아웃: 사용자 정보가 없습니다.");
      }
    };
    const userJoin = async (joinUser) => {
      await join(
        joinUser,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            alert("회원 가입 성공");
            router.push({ name: "login" });
          } else {
            console.error("유저 정보 없음");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };
    const userModify = async (modifyUser) => {
      await modify(
        modifyUser.value,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            alert("정보 수정 성공");
            router.push({ name: "user-mypage" });
          } else {
            console.error("수정 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };
    const userDelete = async (userId) => {
      await deleteUser(
        userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            alert("회원 탈퇴 성공");
          } else {
            console.error("탈퇴 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
      userJoin,
      userModify,
      userDelete,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  }
);
