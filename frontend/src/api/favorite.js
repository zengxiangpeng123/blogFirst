import request from '@/utils/request'

/**
 * 点击收藏/取消收藏
 * @param {Object} params - 参数
 * @param {Number} params.userId - 用户ID
 * @param {Number} params.articleId - 文章ID
 * @param {Number} params.isExist - 当前收藏状态（0-未收藏，1-已收藏）
 * @returns 操作后的收藏状态（0-取消收藏，1-收藏成功）
 */
export const clickFavorite = (params) => {
  return request.post('/favorite/click', params)
}

// 获取收藏列表
export const getFavoriteList = (params = {}) => {
  return request.get('/favorite/list', { params })
}
