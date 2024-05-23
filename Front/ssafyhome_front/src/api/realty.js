import { localAxios } from '@/util/http-commons';

const local = localAxios();

function listRealty(success, fail) {
    local.get(`/house`).then(success).catch(fail);
}

function deleteRealty(idx, success, fail) {
    local.delete(`/house/${idx}`).then(success).catch(fail);
}

export { listRealty, deleteRealty }