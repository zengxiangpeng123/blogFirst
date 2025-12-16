import request from '@/utils/request'

/**
 * 用户注册
 * @param {Object} data - { username, password, checkPassword }
 */
export const register = (data) => {
  return request.post('/user/register', data)
}

/**
 * 用户登录
 * @param {Object} data - { username, password }
 */
export const login = (data) => {
  return request.post('/user/login', data)
}

/**
 * 用户登出
 */
export const logout = () => {
  return request.post('/user/logout')
}

/**
 * 获取当前登录用户信息
 */
export const getCurrentUser = () => {
  return request.post('/user/current')
}

/**
 * 更新用户信息
 * @param {Object} data - { id, nickname, bio, email, avatar }
 */
export const updateUser = (data) => {
  return request.put('/user/update', data)
}
