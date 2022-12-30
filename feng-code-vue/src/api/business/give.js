import request from '@/utils/request'

// 查询点赞列表
export function listGive (query) {
  return request({
    url: '/business/give/list',
    method: 'get',
    params: query
  })
}

// 查询点赞详细
export function getGive (id) {
  return request({
    url: '/business/give/' + id,
    method: 'get'
  })
}

// 新增点赞
export function addGive (data) {
  return request({
    url: '/business/give',
    method: 'post',
    data: data
  })
}

// 修改点赞
export function updateGive (data) {
  return request({
    url: '/business/give',
    method: 'put',
    data: data
  })
}

// 删除点赞
export function delGive (id) {
  return request({
    url: '/business/give/' + id,
    method: 'delete'
  })
}
