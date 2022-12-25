import request from '@/utils/request'

// 查询学习任务列表
export function listTask (query) {
  return request({
    url: '/business/task/list',
    method: 'get',
    params: query
  })
}

// 查询学习任务详细
export function getTask (id) {
  return request({
    url: '/business/task/' + id,
    method: 'get'
  })
}

// 新增学习任务
export function addTask (data) {
  return request({
    url: '/business/task',
    method: 'post',
    data: data
  })
}

// 修改学习任务
export function updateTask (data) {
  return request({
    url: '/business/task',
    method: 'put',
    data: data
  })
}

// 删除学习任务
export function delTask (id) {
  return request({
    url: '/business/task/' + id,
    method: 'delete'
  })
}
