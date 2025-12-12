<template>
  <div class="profile-page">
    <!-- 个人信息卡片 -->
    <div class="profile-header">
      <div class="profile-cover"></div>
      <div class="profile-info">
        <el-avatar :size="100" :src="userInfo.avatar" />
        <div class="info-content">
          <h1>{{ userInfo.name }}</h1>
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

    <!-- 数据统计 -->
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

    <!-- 数据图表 -->
    <div class="chart-section">
      <h2>近期数据</h2>
      <div class="chart-container" ref="chartRef"></div>
    </div>

    <!-- 我的文章 -->
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
              <span class="category">{{ article.category }}</span>
              <span>{{ article.date }}</span>
              <span><el-icon><View /></el-icon> {{ article.views }}</span>
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
import { ref, onMounted, onUnmounted } from 'vue'
import { Document, View, Star, ChatDotRound } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { articles } from '@/data/mock'

const chartRef = ref(null)
let chartInstance = null

const userInfo = ref({
  name: '林清远',
  avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix',
  bio: '一个热爱思考与写作的设计师，专注于用户体验与产品设计。',
  articleCount: 45,
  viewCount: 12580,
  likeCount: 892,
  followerCount: 256,
  commentCount: 134
})

const myArticles = ref(articles.slice(0, 5))

const initChart = () => {
  if (!chartRef.value) return
  chartInstance = echarts.init(chartRef.value)
  
  const option = {
    tooltip: { trigger: 'axis' },
    legend: { data: ['阅读量', '点赞数'], top: 10 },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: { type: 'value' },
    series: [
      {
        name: '阅读量',
        type: 'line',
        smooth: true,
        areaStyle: { opacity: 0.3 },
        itemStyle: { color: '#1ABC9C' },
        data: [120, 132, 101, 134, 90, 230, 210]
      },
      {
        name: '点赞数',
        type: 'line',
        smooth: true,
        areaStyle: { opacity: 0.3 },
        itemStyle: { color: '#3498DB' },
        data: [20, 32, 21, 34, 19, 50, 41]
      }
    ]
  }
  chartInstance.setOption(option)
}

onMounted(() => {
  initChart()
  window.addEventListener('resize', () => chartInstance?.resize())
})

onUnmounted(() => {
  chartInstance?.dispose()
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

.stats-section, .chart-section, .articles-section {
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

.chart-container {
  height: 300px;
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
