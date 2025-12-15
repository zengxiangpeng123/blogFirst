import axios from 'axios'

// 创建 axios 实例，baseURL 自动读取环境变量
const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000,
  withCredentials: true  // 允许携带 cookie（用于 session 认证）
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 可以在这里添加 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    // HTTP 请求成功（状态码 2xx）
    const res = response.data
    console.log('响应数据:', res)
    
    // 如果后端返回的数据有 code 字段，根据 code 判断业务是否成功
    if (res.code !== undefined) {
      // code 为 0 或 200 表示成功
      if (res.code === 0 || res.code === 200) {
        return res
      } else {
        // 业务失败
        return Promise.reject(new Error(res.message || '操作失败'))
      }
    }
    
    // 如果没有 code 字段，直接返回数据（兼容某些接口）
    return res
  },
  (error) => {
    // HTTP 请求失败（状态码 4xx, 5xx）
    console.error('请求错误:', error)
    const message = error.response?.data?.message || error.message || '网络错误'
    return Promise.reject(new Error(message))
  }
)

export default request
