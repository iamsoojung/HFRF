import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function userConfirm(param, success, fail) {
    await local.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    console.log(local.defaults.headers["Authorization"]);
    await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    const refreshToken = sessionStorage.getItem("refreshToken");
    await local.post(`/user/refresh`, user, { headers: { refreshToken } }).then(success).catch(fail);
}

async function logout(userid, success, fail) {
    await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function join(joinUser, success, fail) {
    await local.post(`/user/join`, joinUser).then(success).catch(fail);
}

async function checkId(userId, success, fail) {
    await local.get(`/user/${userId}`).then(success).catch(fail);
}

async function modify(modifyUser, success, fail) {
    await local.post(`/user/${modifyUser.userId}`, modifyUser).then(success).catch(fail)
}
export { userConfirm, findById, tokenRegeneration, logout, join, checkId, modify };