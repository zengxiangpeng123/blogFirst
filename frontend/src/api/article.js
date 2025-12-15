import request from '@/utils/request'

// 获取文章列表
export const getArticles = (params) => {
  return request.get('/articles', { params })
}

// 获取文章详情
export const getArticleById = (id) => {
  return request.get(`/articles/${id}`)
}

// 获取分类列表
export const getCategories = () => {
  return request.get('/categories')
}

// 获取热门文章
export const getHotArticles = () => {
  return request.get('/articles/hot')
}
