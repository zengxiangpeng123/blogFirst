import request from '@/utils/request'

/**
 * 获取文章分页列表（通用查询）
 * @param {Object} params - 查询参数
 * @param {Number} params.userId - 用户ID（可选）
 * @param {Number} params.categoryId - 分类ID（可选）
 * @param {Number} params.isPinned - 是否置顶（可选）
 * @param {Number} params.isPublished - 是否发布（可选，默认1）
 * @param {Number} params.pageNum - 页码（默认1）
 * @param {Number} params.pageSize - 每页数量（默认10）
 */
export const getArticlePage = (params = {}) => {
  // 设置默认值
  const defaultParams = {
    pageNum: 1,
    pageSize: 10,
    ...params
  }
  return request.get('/article/page', { params: defaultParams })
}

// 获取所有文章（首页、归档页）
export const getAllArticles = (pageNum = 1, pageSize = 10) => {
  return getArticlePage({
    isPublished: 1,  // 只显示已发布的
    pageNum,
    pageSize
  })
}

// 获取我的文章（个人中心、作品集）
export const getMyArticles = (userId, pageNum = 1, pageSize = 10) => {
  return getArticlePage({
    userId,
    pageNum,
    pageSize
  })
}

// 获取分类下的文章
export const getArticlesByCategory = (categoryId, pageNum = 1, pageSize = 10) => {
  return getArticlePage({
    categoryId,
    isPublished: 1,
    pageNum,
    pageSize
  })
}

// 获取文章详情
export const getArticleById = (id) => {
  return request.get(`/article/${id}`)
}

// 添加文章
export const addArticle = (data) => {
  return request.post('/article/add', data)
}

// 更新文章
export const updateArticle = (data) => {
  return request.put('/article/update', data)
}

// 删除文章
export const deleteArticle = (id) => {
  return request.delete(`/article/delete/${id}`)
}
