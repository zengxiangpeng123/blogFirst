<template>
  <div class="article-page container" v-loading="loading">
    <div class="content-wrapper">
      <article class="article-main">
        <!-- 文章头部 -->
        <header class="article-header">
          <span class="category">{{ article.categoryName || '未分类' }}</span>
          <h1>{{ article.title }}</h1>
          <div class="meta">
            <span><el-icon><Calendar /></el-icon> {{ formatDate(article.createdAt) }}</span>
            <span><el-icon><View /></el-icon> {{ article.viewCount || 0 }} 次浏览</span>
            <span class="favorite-btn" :class="{ active: isFavorited, disabled: favoriteLoading }" @click="toggleFavorite">
              <el-icon><Star /></el-icon> {{ favoriteLoading ? '处理中...' : (isFavorited ? '已收藏' : '收藏') }}
            </span>
          </div>
        </header>
        
        <!-- 文章正文 -->
        <div class="article-content" v-html="renderedContent"></div>
        
        <!-- 文章尾部 -->
        <footer class="article-footer">
          <div v-if="article.tags" class="tags">
            <el-icon><PriceTag /></el-icon>
            <span v-for="tag in article.tags.split(',')" :key="tag" class="tag">{{ tag }}</span>
          </div>
          
          <div class="copyright-notice">
            <el-icon><Warning /></el-icon>
            本文采用 CC BY-NC-SA 4.0 协议，转载请注明出处。
          </div>
          
          <div class="nav-links">
            <router-link to="/archive" class="prev">← 返回文章列表</router-link>
          </div>
        </footer>
      </article>
      
      <!-- 右侧作者介绍卡片 -->
      <aside class="author-sidebar">
        <div class="author-card">
          <div class="author-header">
            <img :src="authorInfo.avatar || '/images/图标.png'" alt="作者头像" class="author-avatar" />
            <div class="author-basic">
              <h3 class="author-name">{{ authorInfo.nickname || '匿名作者' }}</h3>
              <p class="author-title">博客作者</p>
            </div>
          </div>
          
          <p class="author-bio">{{ authorInfo.bio || '这个人很懒，什么都没写~' }}</p>
          
          <div class="author-actions">
            <el-button type="primary" class="follow-btn" @click="handleFollow">
              <el-icon><Plus /></el-icon> 关注
            </el-button>
            <el-button class="message-btn" @click="handleMessage">
              <el-icon><ChatDotRound /></el-icon> 私信
            </el-button>
          </div>
        </div>
        
        <!-- 作者最近发布的文章 -->
        <div class="hot-articles-card" v-if="authorArticles.length > 0">
          <h4 class="card-title">
            <el-icon><TrendCharts /></el-icon> TA的最近文章
          </h4>
          <ul class="hot-list">
            <li v-for="(item, index) in authorArticles" :key="item.id" class="hot-item">
              <span class="hot-rank" :class="'rank-' + (index + 1)">{{ index + 1 }}</span>
              <router-link :to="`/article/${item.id}`" class="hot-title">{{ item.title }}</router-link>
            </li>
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticleById, getMyArticles } from '@/api/article'
import { clickFavorite } from '@/api/favorite'
import { Star, Plus, ChatDotRound, TrendCharts } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const route = useRoute()
const router = useRouter()
const article = ref({})
const loading = ref(false)
const isFavorited = ref(false)
const userId = ref(null)
const favoriteLoading = ref(false)  // 收藏按钮loading状态

// 作者信息（从文章详情中获取）
const authorInfo = ref({})

// 作者最近发布的文章
const authorArticles = ref([])

// 关注作者
const handleFollow = () => {
  ElMessage.info('关注功能开发中...')
}

// 私信作者
const handleMessage = () => {
  ElMessage.info('私信功能开发中...')
}

// 加载文章详情
const loadArticle = async () => {
  const id = route.params.id
  if (!id) return
  
  loading.value = true
  try {
    const res = await getArticleById(id)
    article.value = res.data || {}
    // 如果返回了收藏状态，更新isFavorited
    if (article.value.isExist !== undefined) {
      isFavorited.value = article.value.isExist === 1
    }
    // 设置作者信息
    if (article.value.author) {
      authorInfo.value = article.value.author
    } else if (article.value.userId) {
      // 如果没有 author 对象，使用文章中的用户信息
      authorInfo.value = {
        id: article.value.userId,
        nickname: article.value.authorName || '匿名作者',
        avatar: article.value.authorAvatar || '/images/图标.png',
        bio: article.value.authorBio || '这个人很懒，什么都没写~'
      }
    }
    // 加载作者的其他文章
    if (article.value.userId) {
      loadAuthorArticles(article.value.userId)
    }
    // 渲染 Markdown 内容
    if (article.value.content) {
      await renderMarkdown(article.value.content)
    }
  } catch (error) {
    ElMessage.error('加载文章失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 加载作者的最近文章
const loadAuthorArticles = async (authorId) => {
  try {
    const res = await getMyArticles(authorId, 1, 5)
    // 过滤掉当前文章，最多显示5篇
    const currentId = Number(route.params.id)
    authorArticles.value = (res.data?.records || res.data?.list || [])
      .filter(item => item.id !== currentId)
      .slice(0, 5)
  } catch (error) {
    console.error('加载作者文章失败', error)
  }
}

// Markdown 渲染后的内容
const renderedContent = ref('')

// 使用 Vditor 渲染 Markdown
const renderMarkdown = async (content) => {
  if (!content) {
    renderedContent.value = ''
    return
  }
  renderedContent.value = await Vditor.md2html(content, {
    mode: 'light',
    anchor: 1,
    lazyLoadImage: ''
  })
}

// 监听文章内容变化，重新渲染
watch(() => article.value.content, (newContent) => {
  if (newContent) {
    renderMarkdown(newContent)
  }
})

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 10)
}

// 初始化用户信息
const initUserInfo = () => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    const userInfo = JSON.parse(userInfoStr)
    userId.value = userInfo.id
  }
}

// 切换收藏状态
const toggleFavorite = async () => {
  if (!userId.value) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  // 防止重复点击
  if (favoriteLoading.value) return
  favoriteLoading.value = true
  
  try {
    // 调用点击收藏接口，传入当前收藏状态
    const res = await clickFavorite({
      userId: userId.value,
      articleId: Number(route.params.id),
      isExist: isFavorited.value ? 1 : 0  // 当前状态：0-未收藏，1-已收藏
    })
    
    // 根据返回值更新状态（返回1表示收藏成功，返回0表示取消收藏）
    const newStatus = res.data
    isFavorited.value = newStatus === 1
    ElMessage.success(newStatus === 1 ? '收藏成功' : '已取消收藏')
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  } finally {
    favoriteLoading.value = false
  }
}

onMounted(() => {
  initUserInfo()
  loadArticle()
})
</script>

<style lang="scss" scoped>
.content-wrapper {
  display: flex;
  gap: 30px;
}

.article-main {
  flex: 1;
  background: #fff;
  border-radius: 8px;
  padding: 40px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  max-width: 800px;
}

.article-header {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ECF0F1;
  
  .category {
    display: inline-block;
    padding: 4px 12px;
    background: rgba(#1ABC9C, 0.1);
    color: #1ABC9C;
    border-radius: 3px;
    font-size: 13px;
    margin-bottom: 15px;
  }
  
  h1 {
    color: #2C3E50;
    font-size: 2em;
    line-height: 1.4;
    margin-bottom: 20px;
  }
  
  .meta {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    color: #95A5A6;
    font-size: 14px;
    
    span {
      display: flex;
      align-items: center;
      gap: 5px;
    }
    
    .favorite-btn {
      cursor: pointer;
      padding: 4px 12px;
      border-radius: 4px;
      transition: all 0.2s;
      
      &:hover {
        background: rgba(26, 188, 156, 0.1);
        color: #1ABC9C;
      }
      
      &.active {
        color: #1ABC9C;
        
        &:hover {
          background: rgba(231, 76, 60, 0.1);
          color: #E74C3C;
        }
      }
      
      &.disabled {
        pointer-events: none;
        opacity: 0.6;
      }
    }
  }
}

.article-footer {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #ECF0F1;
  
  .tags {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
    color: #95A5A6;
  }
  
  .share {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
    color: #555;
  }
  
  .copyright-notice {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 15px;
    background: #F9F9F9;
    border-radius: 6px;
    color: #95A5A6;
    font-size: 14px;
    margin-bottom: 20px;
  }
  
  .nav-links {
    display: flex;
    justify-content: space-between;
    
    a {
      color: #1ABC9C;
      &:hover { text-decoration: underline; }
    }
  }
}

/* 文章内容 Markdown 渲染样式 */
.article-content {
  :deep(h1) {
    font-size: 1.8em;
    font-weight: 600;
    color: #2C3E50;
    margin: 24px 0 16px;
    padding-bottom: 8px;
    border-bottom: 1px solid #eee;
  }
  
  :deep(h2) {
    font-size: 1.5em;
    font-weight: 600;
    color: #2C3E50;
    margin: 20px 0 12px;
  }
  
  :deep(h3) {
    font-size: 1.25em;
    font-weight: 600;
    color: #2C3E50;
    margin: 16px 0 10px;
  }
  
  :deep(p) {
    margin: 12px 0;
    line-height: 1.8;
    color: #333;
  }
  
  :deep(strong) {
    font-weight: 600;
    color: #2C3E50;
  }
  
  :deep(em) {
    font-style: italic;
  }
  
  :deep(del) {
    text-decoration: line-through;
    color: #999;
  }
  
  :deep(blockquote) {
    margin: 16px 0;
    padding: 12px 20px;
    border-left: 4px solid #1ABC9C;
    background: #f9f9f9;
    color: #666;
  }
  
  :deep(code) {
    padding: 2px 6px;
    background: #f5f5f5;
    border-radius: 3px;
    font-family: 'Consolas', monospace;
    font-size: 0.9em;
    color: #e74c3c;
  }
  
  :deep(pre) {
    margin: 16px 0;
    padding: 16px;
    background: #2d2d2d;
    border-radius: 6px;
    overflow-x: auto;
    
    code {
      padding: 0;
      background: transparent;
      color: #f8f8f2;
    }
  }
  
  :deep(ul), :deep(ol) {
    margin: 12px 0;
    padding-left: 24px;
  }
  
  :deep(li) {
    margin: 6px 0;
    line-height: 1.6;
  }
  
  :deep(hr) {
    margin: 24px 0;
    border: none;
    border-top: 1px solid #eee;
  }
  
  :deep(a) {
    color: #1ABC9C;
    text-decoration: none;
    &:hover { text-decoration: underline; }
  }
  
  :deep(img) {
    max-width: 100%;
    border-radius: 4px;
    margin: 12px 0;
  }
  
  :deep(table) {
    width: 100%;
    border-collapse: collapse;
    margin: 16px 0;
    
    th, td {
      padding: 10px 12px;
      border: 1px solid #ddd;
      text-align: left;
    }
    
    th {
      background: #f5f5f5;
      font-weight: 600;
    }
  }
}

/* 右侧作者介绍卡片 */
.author-sidebar {
  width: 300px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.author-card {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  
  .author-header {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 16px;
  }
  
  .author-avatar {
    width: 64px;
    height: 64px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid #1ABC9C;
  }
  
  .author-basic {
    flex: 1;
  }
  
  .author-name {
    font-size: 18px;
    font-weight: 600;
    color: #2C3E50;
    margin: 0 0 4px 0;
  }
  
  .author-title {
    font-size: 13px;
    color: #95A5A6;
    margin: 0;
  }
  
  .author-bio {
    font-size: 14px;
    color: #666;
    line-height: 1.6;
    margin: 0 0 16px 0;
  }
  
  .author-actions {
    display: flex;
    gap: 10px;
    
    .follow-btn {
      flex: 1;
      background: #1ABC9C;
      border-color: #1ABC9C;
      
      &:hover {
        background: #16A085;
        border-color: #16A085;
      }
    }
    
    .message-btn {
      flex: 1;
      color: #1ABC9C;
      border-color: #1ABC9C;
      
      &:hover {
        background: rgba(26, 188, 156, 0.1);
      }
    }
  }
}

.hot-articles-card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  
  .card-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 16px;
    font-weight: 600;
    color: #2C3E50;
    margin: 0 0 16px 0;
    
    .el-icon {
      color: #E74C3C;
    }
  }
  
  .hot-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  .hot-item {
    display: flex;
    align-items: flex-start;
    gap: 10px;
    padding: 10px 0;
    border-bottom: 1px solid #f5f5f5;
    
    &:last-child {
      border-bottom: none;
    }
  }
  
  .hot-rank {
    width: 20px;
    height: 20px;
    border-radius: 4px;
    background: #95A5A6;
    color: #fff;
    font-size: 12px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    
    &.rank-1 { background: #E74C3C; }
    &.rank-2 { background: #E67E22; }
    &.rank-3 { background: #F1C40F; }
  }
  
  .hot-title {
    font-size: 14px;
    color: #333;
    line-height: 1.4;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    
    &:hover {
      color: #1ABC9C;
    }
  }
}

@media (max-width: 992px) {
  .content-wrapper { flex-direction: column; }
  .article-main { padding: 25px; }
  .author-sidebar { 
    width: 100%; 
    order: -1;
  }
}
</style>
