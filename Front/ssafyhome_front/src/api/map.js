import { localAxios } from '@/util/http-commons';

const local = localAxios();

function listSido(success, fail) {
    local.get(`/house/sido`).then(success).catch(fail);
}

function listGugun(param, success, fail) {
    local.get(`/house/gugun`, { params: param }).then(success).catch(fail);
}

function listDong(param, success, fail) {
    local.get(`/house/dong`, { params: param }).then(success).catch(fail);
}

function listApt(param, success, fail) {
    local.get(`/house/apt`, { params: param }).then(success).catch(fail);
}

export { listSido, listGugun, listDong, listApt }