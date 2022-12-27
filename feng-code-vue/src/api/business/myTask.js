import request from '@/utils/request'

// 查询学习任务列表
export function listMyTask (query) {
  return request({
    url: '/business/taskJoin/my-task-list',
    method: 'get',
    params: query
  })
}
