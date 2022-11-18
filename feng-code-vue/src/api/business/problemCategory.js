import request from '@/utils/request'

// 查询问题分类列表
export function listProblemCategory (query) {
  return request({
    url: '/business/problemCategory/list',
    method: 'get',
    params: query
  })
}

// 查询所有问题分类列表
export function listAllProblemCategory () {
  return request({
    url: '/business/problemCategory/list-all',
    method: 'get'
  })
}

// 查询问题分类详细
export function getProblemCategory (id) {
  return request({
    url: '/business/problemCategory/' + id,
    method: 'get'
  })
}

// 新增问题分类
export function addProblemCategory (data) {
  return request({
    url: '/business/problemCategory',
    method: 'post',
    data: data
  })
}

// 修改问题分类
export function updateProblemCategory (data) {
  return request({
    url: '/business/problemCategory',
    method: 'put',
    data: data
  })
}

// 删除问题分类
export function delProblemCategory (id) {
  return request({
    url: '/business/problemCategory/' + id,
    method: 'delete'
  })
}
