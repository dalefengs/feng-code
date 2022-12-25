import request from '@/utils/request'

// 查询学习任务与题目关联列表
export function listTaskProblem (query) {
  return request({
    url: '/business/taskProblem/list',
    method: 'get',
    params: query
  })
}

// 查询学习任务与题目关联详细
export function getTaskProblem (id) {
  return request({
    url: '/business/taskProblem/' + id,
    method: 'get'
  })
}

// 新增学习任务与题目关联
export function addTaskProblem (data) {
  return request({
    url: '/business/taskProblem',
    method: 'post',
    data: data
  })
}

// 修改学习任务与题目关联
export function updateTaskProblem (data) {
  return request({
    url: '/business/taskProblem',
    method: 'put',
    data: data
  })
}

// 删除学习任务与题目关联
export function delTaskProblem (id) {
  return request({
    url: '/business/taskProblem/' + id,
    method: 'delete'
  })
}
