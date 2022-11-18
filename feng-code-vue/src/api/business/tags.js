import request from '@/utils/request'

// 查询题目标签列表
export function listTags (query) {
  return request({
    url: '/business/tags/list',
    method: 'get',
    params: query
  })
}

// 查询所有题目标签列表
export function listAllTags () {
  return request({
    url: '/business/tags/list-all',
    method: 'get'
  })
}

// 查询题目标签详细
export function getTags (id) {
  return request({
    url: '/business/tags/' + id,
    method: 'get'
  })
}

// 新增题目标签
export function addTags (data) {
  return request({
    url: '/business/tags',
    method: 'post',
    data: data
  })
}

// 修改题目标签
export function updateTags (data) {
  return request({
    url: '/business/tags',
    method: 'put',
    data: data
  })
}

// 删除题目标签
export function delTags (id) {
  return request({
    url: '/business/tags/' + id,
    method: 'delete'
  })
}
