<template>
  <div class="about-page">
    <!-- 顶部个人信息卡片 -->
    <div class="profile-banner">
      <div class="profile-main">
        <el-avatar :src="userInfo.avatar || '/images/图标.png'" :size="100" />
        <div class="profile-info">
          <div class="name-row">
            <h1>{{ userInfo.nickname || '用户' }}</h1>
            <el-button v-if="isOwner" type="primary" size="small" @click="$router.push('/settings')">编辑资料</el-button>
          </div>
          <p class="bio">{{ userInfo.bio || '这个人很懒，什么都没写~' }}</p>
          <div class="stats-row">
            <div class="stat-item">
              <span class="num">{{ stats.articleCount }}</span>
              <span class="label">文章</span>
            </div>
            <div class="stat-item">
              <span class="num">{{ stats.viewCount }}</span>
              <span class="label">阅读量</span>
            </div>
            <div class="stat-item">
              <span class="num">{{ stats.favoriteCount }}</span>
              <span class="label">被收藏</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 数据概览 -->
    <div class="data-overview">
      <h2>数据概览</h2>
      <div class="overview-grid">
        <div class="overview-card">
          <div class="icon-wrap" style="background: rgba(64, 158, 255, 0.1)">
            <el-icon style="color: #409EFF"><Document /></el-icon>
          </div>
          <div class="data-info">
            <span class="num">{{ stats.articleCount }}</span>
            <span class="label">发布文章</span>
          </div>
        </div>
        <div class="overview-card">
          <div class="icon-wrap" style="background: rgba(255, 153, 0, 0.1)">
            <el-icon style="color: #FF9900"><View /></el-icon>
          </div>
          <div class="data-info">
            <span class="num">{{ stats.viewCount }}</span>
            <span class="label">总阅读量</span>
          </div>
        </div>
        <div class="overview-card">
          <div class="icon-wrap" style="background: rgba(255, 102, 102, 0.1)">
            <el-icon style="color: #FF6666"><Star /></el-icon>
          </div>
          <div class="data-info">
            <span class="num">{{ stats.favoriteCount }}</span>
            <span class="label">被收藏</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 最新文章 -->
    <div class="recent-articles">
      <h2>最新文章</h2>
      <div v-loading="loading" class="article-list">
        <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
        <el-empty v-if="!loading && articles.length === 0" description="暂无发布的文章" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCurrentUser } from '@/api/user'
import { getArticleListByUserId } from '@/api/article'
import ArticleCard from '@/components/ArticleCard.vue'
import { Document, View, Star } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const userInfo = ref({})
const isOwner = ref(true)
const loading = ref(false)
const articles = ref([])
const stats = ref({
  articleCount: 0,
  viewCount: 0,
  favoriteCount: 0
})

const loadUserInfo = async () => {
  try {
    const res = await getCurrentUser()
    userInfo.value = res.data || {}
    loadArticles(res.data.id)
  } catch (error) {
    ElMessage.error('获取用户信息失败')
  }
}

const loadArticles = async (userId) => {
  loading.value = true
  try {
    const res = await getArticleListByUserId(userId)
    const allArticles = res.data || []
    // 只显示已发布的文章
    const publishedArticles = allArticles.filter(a => a.isPublished === 1)
    articles.value = publishedArticles
    stats.value.articleCount = publishedArticles.length
    // 计算总阅读量
    stats.value.viewCount = publishedArticles.reduce((sum, a) => sum + (a.viewCount || 0), 0)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style lang="scss" scoped>
.about-page {
  max-width: 100%;
}

.profile-banner {
  background: linear-gradient(135deg, #1ABC9C, #16A085);
  border-radius: 12px;
  padding: 30px;
  margin-bottom: 24px;
}

.profile-main {
  display: flex;
  align-items: center;
  gap: 24px;
  
  .el-avatar {
    border: 3px solid rgba(255,255,255,0.3);
  }
}

.profile-info {
  flex: 1;
  color: #fff;
  
  .name-row {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 8px;
    
    h1 {
      font-size: 1.5em;
      margin: 0;
    }
    
    .el-button {
      background: rgba(255,255,255,0.2);
      border: 1px solid rgba(255,255,255,0.3);
      color: #fff;
      
      &:hover {
        background: rgba(255,255,255,0.3);
      }
    }
  }
  
  .bio {
    opacity: 0.9;
    font-size: 14px;
    margin-bottom: 16px;
  }
}

.stats-row {
  display: flex;
  gap: 30px;
  
  .stat-item {
    text-align: center;
    
    .num {
      display: block;
      font-size: 1.3em;
      font-weight: 600;
    }
    
    .label {
      font-size: 12px;
      opacity: 0.8;
    }
  }
}

.data-overview, .recent-articles {
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

.overview-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.overview-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: #FAFAFA;
  border-radius: 10px;
  
  .icon-wrap {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    
    .el-icon {
      font-size: 24px;
    }
  }
  
  .data-info {
    .num {
      display: block;
      font-size: 1.4em;
      font-weight: 600;
      color: #2C3E50;
    }
    
    .label {
      font-size: 12px;
      color: #95A5A6;
    }
  }
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

@media (max-width: 900px) {
  .overview-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .profile-main {
    flex-direction: column;
    text-align: center;
  }
  
  .name-row {
    justify-content: center;
  }
  
  .stats-row {
    justify-content: center;
  }
  
  .overview-grid {
    grid-template-columns: 1fr;
  }
}
</style>
