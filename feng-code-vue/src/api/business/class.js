import request from '@/utils/request'

// 查询班级管理列表
export function listClass (query) {
  return request({
    url: '/business/class/list',
    method: 'get',
    params: query
  })
}

// 通过学院 Id 查询班级管理列表
export function listClassByCollegeId (query) {
  return request({
    url: '/business/class/list-by-college-id',
    method: 'get',
    params: query
  })
}

// 查询班级管理详细
export function getClass (id) {
  return request({
    url: '/business/class/' + id,
    method: 'get'
  })
}

// 新增班级管理
export function addClass (data) {
  return request({
    url: '/business/class',
    method: 'post',
    data: data
  })
}

// 修改班级管理
export function updateClass (data) {
  return request({
    url: '/business/class',
    method: 'put',
    data: data
  })
}

// 删除班级管理
export function delClass (id) {
  return request({
    url: '/business/class/' + id,
    method: 'delete'
  })
}
