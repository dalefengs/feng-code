import request from '@/utils/request'

// 查询任务管理列表
export function listQueue (query) {
  return request({
    url: '/business/queue/list',
    method: 'get',
    params: query
  })
}

// 查询任务管理列表
export function listRank (query) {
  return request({
    url: '/business/queue/rank-list',
    method: 'get',
    params: query
  })
}

// 查询任务管理列表
export function listCheck (query) {
  return request({
    url: '/business/queue/check-list',
    method: 'get',
    params: query
  })
}

// 提交记录
export function submitListQueue (query) {
  return request({
    url: '/business/queue/submit-list',
    method: 'get',
    params: query
  })
}

// 查询任务管理详细
export function getQueue (id) {
  return request({
    url: '/business/queue/' + id,
    method: 'get'
  })
}

// 新增任务管理
export function addQueue (data) {
  return request({
    url: '/business/queue',
    method: 'post',
    data: data
  })
}

// 批阅题目
export function checkQueue (data) {
  return request({
    url: '/business/queue/check',
    method: 'post',
    data: data
  })
}

// 修改任务管理
export function updateQueue (data) {
  return request({
    url: '/business/queue',
    method: 'put',
    data: data
  })
}

// 删除任务管理
export function delQueue (id) {
  return request({
    url: '/business/queue/' + id,
    method: 'delete'
  })
}

// 执行任务
export function excuteQueue (id) {
  return request({
    url: '/business/excute/' + id,
    method: 'POST'
  })
}
