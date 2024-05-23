import { localAxios } from "@/util/http-commons";

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

function listAptByDong(param, success, fail) {
  local.get(`/house/apt?dongCode=${param.dongCode}`).then(success).catch(fail);
}

function dealByDong(param, success, fail) {
  local
    .get(`/house/aptDeal?aptCode=${param.aptCode}`)
    .then(success)
    .catch(fail);
}

function listAptByName(aptName, success, fail) {
  local
    .get(`/house/aptName?aptName=${aptName.aptName}`)
    .then(success)
    .catch(fail);
}
export {
  listSido,
  listGugun,
  listDong,
  listAptByDong,
  dealByDong,
  listAptByName,
};
