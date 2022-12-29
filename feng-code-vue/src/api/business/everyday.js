import request from '@/utils/request'

// 查询每日一题列表
export function listEveryday (query) {
  return request({
    url: '/business/everyday/list',
    method: 'get',
    params: query
  })
}

// 查询每日一题列表
export function monthListEveryday (query) {
  return request({
    url: '/business/everyday/month-list',
    method: 'get',
    params: query
  })
}

// 查询每日一题详细
export function getEveryday (id) {
  return request({
    url: '/business/everyday/' + id,
    method: 'get'
  })
}

// 新增每日一题
export function addEveryday (data) {
  return request({
    url: '/business/everyday',
    method: 'post',
    data: data
  })
}

// 修改每日一题
export function updateEveryday (data) {
  return request({
    url: '/business/everyday',
    method: 'put',
    data: data
  })
}

// 删除每日一题
export function delEveryday (id) {
  return request({
    url: '/business/everyday/' + id,
    method: 'delete'
  })
}
