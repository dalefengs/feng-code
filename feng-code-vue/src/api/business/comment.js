import request from '@/utils/request'

// 查询题目评论列表
export function listComment (query) {
  return request({
    url: '/business/comment/list',
    method: 'get',
    params: query
  })
}

// 查询题目评论详细
export function getComment (id) {
  return request({
    url: '/business/comment/' + id,
    method: 'get'
  })
}

// 新增题目评论
export function addComment (data) {
  return request({
    url: '/business/comment',
    method: 'post',
    data: data
  })
}

// 修改题目评论
export function updateComment (data) {
  return request({
    url: '/business/comment',
    method: 'put',
    data: data
  })
}

// 删除题目评论
export function delComment (id) {
  return request({
    url: '/business/comment/' + id,
    method: 'delete'
  })
}
