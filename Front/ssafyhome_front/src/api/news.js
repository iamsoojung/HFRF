import { localAxios } from '@/util/http-commons';

const local = localAxios();

function listNews(success, fail) {
    local.get(`/news/list`).then(success).catch(fail);
}

export { listNews };