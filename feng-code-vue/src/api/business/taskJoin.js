import request from '@/utils/request'

// 查询学习任务学生参与列表
export function listTaskJoin (query) {
  return request({
    url: '/business/taskJoin/list',
    method: 'get',
    params: query
  })
}

// 查询学习任务学生参与详细
export function getTaskJoin (id) {
  return request({
    url: '/business/taskJoin/' + id,
    method: 'get'
  })
}

// 新增学习任务学生参与
export function addTaskJoin (data) {
  return request({
    url: '/business/taskJoin',
    method: 'post',
    data: data
  })
}

// 修改学习任务学生参与
export function updateTaskJoin (data) {
  return request({
    url: '/business/taskJoin',
    method: 'put',
    data: data
  })
}

// 删除学习任务学生参与
export function delTaskJoin (id) {
  return request({
    url: '/business/taskJoin/' + id,
    method: 'delete'
  })
}
