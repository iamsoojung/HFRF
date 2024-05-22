import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listLike(userId, success, fail) {
    local.get(`/aptLike/list`, { params: userId }).then(success).catch(fail);
}

function like(likeApt, success, fail) {
    local.post(`/aptLike/like`, JSON.stringify(likeApt)).then(success).catch(fail);
}

function deleteLike(likeApt, success, fail) {
    local.delete(`/aptLike/dislike`, { data: likeApt }).then(success).catch(fail);
}

export { listLike, like, deleteLike }