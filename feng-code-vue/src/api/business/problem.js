import request from '@/utils/request'

// 查询题目管理列表
export function listProblem (query) {
  return request({
    url: '/business/problem/list',
    method: 'get',
    params: query
  })
}
// 查询题目管理列表
export function listProblemSet (query) {
  return request({
    url: '/business/problem/problem-set-list',
    method: 'get',
    params: query
  })
}
// 查询题目管理列表
export function listProblemTask (query) {
  return request({
    url: '/business/problem/problem-task-list',
    method: 'get',
    params: query
  })
}

// 查询题目管理详细
export function getProblem (id) {
  return request({
    url: '/business/problem/' + id,
    method: 'get'
  })
}

// 新增题目管理
export function addProblem (data) {
  return request({
    url: '/business/problem',
    method: 'post',
    data: data
  })
}

// 修改题目管理
export function updateProblem (data) {
  return request({
    url: '/business/problem',
    method: 'put',
    data: data
  })
}

// 删除题目管理
export function delProblem (id) {
  return request({
    url: '/business/problem/' + id,
    method: 'delete'
  })
}
