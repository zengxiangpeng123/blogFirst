<template>
  <div class="profile-page">
    <div class="profile-header">
      <div class="profile-cover"></div>
      <div class="profile-info">
        <el-avatar :size="100" :src="userInfo.avatar" />
        <div class="info-content">
          <h1>{{ userInfo.nickname }}</h1>
          <p class="bio">{{ userInfo.bio }}</p>
          <div class="stats">
            <div class="stat-item">
              <span class="value">{{ userInfo.articleCount }}</span>
              <span class="label">文章</span>
            </div>
            <div class="stat-item">
              <span class="value">{{ userInfo.viewCount }}</span>
              <span class="label">阅读量</span>
            </div>
            <div class="stat-item">
              <span class="value">{{ userInfo.likeCount }}</span>
              <span class="label">获赞</span>
            </div>
            <div class="stat-item">
              <span class="value">{{ userInfo.followerCount }}</span>
              <span class="label">粉丝</span>
            </div>
          </div>
        </div>
        <el-button type="primary" @click="$router.push('/settings')">编辑资料</el-button>
      </div>
    </div>

    <div class="stats-section">
      <h2>数据概览</h2>
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-icon" style="background: #E8F8F5;">
            <el-icon :size="24" color="#1ABC9C"><Document /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ userInfo.articleCount }}</span>
            <span class="stat-label">发布文章</span>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #FEF5E7;">
            <el-icon :size="24" color="#F39C12"><View /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ userInfo.viewCount }}</span>
            <span class="stat-label">总阅读量</span>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #FDEDEC;">
            <el-icon :size="24" color="#E74C3C"><Star /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ userInfo.likeCount }}</span>
            <span class="stat-label">获得点赞</span>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #EBF5FB;">
            <el-icon :size="24" color="#3498DB"><ChatDotRound /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ userInfo.commentCount }}</span>
            <span class="stat-label">收到评论</span>
          </div>
        </div>
      </div>
    </div>

    <div class="articles-section">
      <div class="section-header">
        <h2>我的文章</h2>
        <router-link to="/write" class="more-link">写新文章 →</router-link>
      </div>
      <div class="article-list">
        <div v-for="article in myArticles" :key="article.id" class="article-item">
          <div class="article-info">
            <router-link :to="`/article/${article.id}`" class="title">{{ article.title }}</router-link>
            <div class="meta">
              <span class="category">{{ article.categoryName || '未分类' }}</span>
              <span>{{ formatDate(article.createdAt) }}</span>
              <span><el-icon><View /></el-icon> {{ article.viewCount || 0 }}</span>
            </div>
          </div>
          <div class="article-actions">
            <el-button text type="primary" size="small">编辑</el-button>
            <el-button text type="danger" size="small">删除</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Document, View, Star, ChatDotRound } from '@element-plus/icons-vue'
import { getArticleListByUserId } from '@/api/article'
import { getCurrentUser } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()

const userInfo = ref({
  nickname: '',
  avatar: '',
  bio: '',
  articleCount: 0,
  viewCount: 0,
  likeCount: 0,
  followerCount: 0,
  commentCount: 0
})

const myArticles = ref([])
const loading = ref(false)

// 加载用户信息和文章
const loadData = async () => {
  loading.value = true
  try {
    // 获取当前用户信息
    const userRes = await getCurrentUser()
    const user = userRes.data || {}
    
    userInfo.value.nickname = user.nickname || '用户'
    userInfo.value.avatar = user.avatar || '/images/图标.png'
    userInfo.value.bio = user.bio || '这个人很懒，什么都没写~'
    
    // 获取用户文章列表
    const articleRes = await getArticleListByUserId(user.id)
    const allArticles = articleRes.data || []
    
    // 只显示已发布的文章
    const publishedArticles = allArticles.filter(a => a.isPublished === 1)
    myArticles.value = publishedArticles.slice(0, 5)
    
    // 统计数据
    userInfo.value.articleCount = publishedArticles.length
    userInfo.value.viewCount = publishedArticles.reduce((sum, a) => sum + (a.viewCount || 0), 0)
    
  } catch (error) {
    ElMessage.error('请先登录')
    router.push('/login')
  } finally {
    loading.value = false
  }
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 10)
}

onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.profile-page {
  max-width: 900px;
  margin: 0 auto;
}

.profile-header {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  
  .profile-cover {
    height: 120px;
    background: linear-gradient(135deg, #1ABC9C 0%, #16A085 100%);
  }
  
  .profile-info {
    display: flex;
    align-items: flex-start;
    gap: 24px;
    padding: 0 24px 24px;
    margin-top: -50px;
    
    .el-avatar {
      border: 4px solid #fff;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    
    .info-content {
      flex: 1;
      padding-top: 55px;
      
      h1 {
        font-size: 1.5em;
        color: #2C3E50;
        margin-bottom: 8px;
      }
      
      .bio {
        color: #666;
        font-size: 14px;
        margin-bottom: 16px;
      }
      
      .stats {
        display: flex;
        gap: 32px;
        
        .stat-item {
          text-align: center;
          
          .value {
            display: block;
            font-size: 1.2em;
            font-weight: 600;
            color: #2C3E50;
          }
          
          .label {
            font-size: 12px;
            color: #95A5A6;
          }
        }
      }
    }
    
    > .el-button {
      margin-top: 60px;
    }
  }
}

.stats-section, .articles-section {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  
  h2 {
    font-size: 1.1em;
    color: #2C3E50;
    margin-bottom: 20px;
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: #FAFAFA;
  border-radius: 10px;
  
  .stat-icon {
    width: 48px;
    height: 48px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .stat-info {
    .stat-value {
      display: block;
      font-size: 1.3em;
      font-weight: 600;
      color: #2C3E50;
    }
    
    .stat-label {
      font-size: 12px;
      color: #95A5A6;
    }
  }
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  
  h2 { margin-bottom: 0; }
  
  .more-link {
    font-size: 14px;
    color: #1ABC9C;
  }
}

.article-list {
  .article-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 14px 0;
    border-bottom: 1px solid #F0F0F0;
    
    &:last-child { border-bottom: none; }
    
    .title {
      font-size: 15px;
      color: #2C3E50;
      margin-bottom: 6px;
      display: block;
      
      &:hover { color: #1ABC9C; }
    }
    
    .meta {
      display: flex;
      gap: 16px;
      font-size: 13px;
      color: #95A5A6;
      
      .category { color: #1ABC9C; }
    }
  }
}

@media (max-width: 768px) {
  .stats-grid { grid-template-columns: repeat(2, 1fr); }
  .profile-info { flex-direction: column; align-items: center; text-align: center; }
  .profile-info .stats { justify-content: center; }
}
</style>
