import request from '@/utils/request'

// 查询问题分类列表
export function listProblem-category (query) {
  return request({
    url: '/business/problem-category/list',
    method: 'get',
    params: query
  })
}

// 查询问题分类详细
export function getProblem-category (id) {
  return request({
    url: '/business/problem-category/' + id,
    method: 'get'
  })
}

// 新增问题分类
export function addProblem-category (data) {
  return request({
    url: '/business/problem-category',
    method: 'post',
    data: data
  })
}

// 修改问题分类
export function updateProblem-category (data) {
  return request({
    url: '/business/problem-category',
    method: 'put',
    data: data
  })
}

// 删除问题分类
export function delProblem-category (id) {
  return request({
    url: '/business/problem-category/' + id,
    method: 'delete'
  })
}
