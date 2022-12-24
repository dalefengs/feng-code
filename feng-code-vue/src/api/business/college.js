import request from '@/utils/request'

// 查询学院管理列表
export function listCollege (query) {
  return request({
    url: '/business/college/list',
    method: 'get',
    params: query
  })
}

// 查询学院管理列表
export function listCollegeAll () {
  return request({
    url: '/business/college/list-all',
    method: 'get'
  })
}

// 查询学院管理列表
export function listCollegeAllAny () {
  return request({
    url: '/business/college/list-all-any',
    method: 'get'
  })
}

// 查询学院管理详细
export function getCollege (id) {
  return request({
    url: '/business/college/' + id,
    method: 'get'
  })
}

// 新增学院管理
export function addCollege (data) {
  return request({
    url: '/business/college',
    method: 'post',
    data: data
  })
}

// 修改学院管理
export function updateCollege (data) {
  return request({
    url: '/business/college',
    method: 'put',
    data: data
  })
}

// 删除学院管理
export function delCollege (id) {
  return request({
    url: '/business/college/' + id,
    method: 'delete'
  })
}
