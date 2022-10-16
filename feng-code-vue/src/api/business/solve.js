import request from '@/utils/request'

// 查询题解列表列表
export function listSolve (query) {
  return request({
    url: '/business/solve/list',
    method: 'get',
    params: query
  })
}

// 查询题解列表详细
export function getSolve (id) {
  return request({
    url: '/business/solve/' + id,
    method: 'get'
  })
}

// 新增题解列表
export function addSolve (data) {
  return request({
    url: '/business/solve',
    method: 'post',
    data: data
  })
}

// 修改题解列表
export function updateSolve (data) {
  return request({
    url: '/business/solve',
    method: 'put',
    data: data
  })
}

// 删除题解列表
export function delSolve (id) {
  return request({
    url: '/business/solve/' + id,
    method: 'delete'
  })
}
