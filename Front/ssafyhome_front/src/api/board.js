import { localAxios } from "@/util/http-commons";

const local = localAxios();

// 게시글
async function listArticle(param, success, fail) {
  await local.get(`/board`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`/board/${articleno}`).then(success).catch(fail);
}

// function registArticle(article, success, fail) {
//   local.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
// }

function getModifyArticle(articleno, success, fail) {
  local.get(`/board/modify/${articleno}`).then(success).catch(fail);
}

// function modifyArticle(article, success, fail) {
//   local.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
// }

function deleteArticle(articleno, success, fail) {
  local.delete(`/board/${articleno}`).then(success).catch(fail);
}

// 댓글
function listComment(articleNo, success, fail) {
  local.get(`/comment/list?articleNo=${articleNo}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  local.post(`/comment/write`, JSON.stringify(comment)).then(success).catch(fail);
}

function getModifyComment(commentNo, success, fail) {
  local.get(`/comment/modify/${commentNo}`).then(success).catch(fail);
}

function modifyComment(comment, success, fail) {
  local.put(`/comment/modify`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteComment(commentNo, success, fail) {
  local.delete(`/comment/delete?commentNo=${commentNo}`).then(success).catch(fail);
}

function getCommentCnt(articleNo, success, fail) {
  local.get(`/comment/count?articleNo=${articleNo}`).then(success).catch(fail);
}

// 파일 업로드
function sendFileInfo(fileData, success, fail) {

  const formData = new FormData();
  formData.append("fileData", fileData);

  local.post(`/board/upload`, formData, {
    headers: {
      'Context-Type': 'multipart/form-data'
    }
  }).then(success).catch(fail)
} 

export {
  listArticle,
  detailArticle,
  // registArticle,
  getModifyArticle,
  // modifyArticle,
  deleteArticle,
  listComment,
  writeComment,
  getModifyComment,
  modifyComment,
  deleteComment,
  getCommentCnt,
  sendFileInfo
};
