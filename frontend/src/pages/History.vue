<template>
  <div class="history-page">
    <header class="page-header">
      <div class="header-icon">
        <el-icon :size="32"><Clock /></el-icon>
      </div>
      <div class="header-text">
        <h1>浏览历史</h1>
        <p>最近阅读的文章</p>
      </div>
      <el-button v-if="historyList.length > 0" type="danger" text @click="clearHistory">
        清空历史
      </el-button>
    </header>

    <div class="history-content" v-if="historyList.length > 0">
      <div v-for="(group, date) in groupedHistory" :key="date" class="date-group">
        <div class="date-label">{{ formatDateLabel(date) }}</div>
        <div class="history-items">
          <div v-for="item in group" :key="item.id" class="history-item hover-card">
            <div class="item-content">
              <router-link :to="`/article/${item.id}`" class="title">{{ item.title }}</router-link>
              <div class="meta">
                <span class="category">{{ item.category }}</span>
                <span class="time">{{ item.viewTime }}</span>
              </div>
            </div>
            <el-button type="info" text @click="removeHistory(item.id)">
              <el-icon><Close /></el-icon>
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <el-empty v-else description="暂无浏览记录">
      <template #image>
        <el-icon :size="60" color="#E0E0E0"><Clock /></el-icon>
      </template>
      <el-button type="primary" @click="$router.push('/')">去看看文章</el-button>
    </el-empty>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { Clock, Close } from '@element-plus/icons-vue'

const historyList = ref([
  { id: 1, title: '设计中的留白艺术：少即是多', category: '设计沉思', viewDate: '2024-07-16', viewTime: '14:30' },
  { id: 2, title: '从Vue2到Vue3：一次渐进式的迁移之旅', category: '技术探索', viewDate: '2024-07-16', viewTime: '10:15' },
  { id: 3, title: '夏日午后的咖啡馆', category: '生活随笔', viewDate: '2024-07-15', viewTime: '20:45' },
  { id: 4, title: '《设计心理学》读后感', category: '读书笔记', viewDate: '2024-07-14', viewTime: '16:20' }
])

const groupedHistory = computed(() => {
  const groups = {}
  historyList.value.forEach(item => {
    if (!groups[item.viewDate]) {
      groups[item.viewDate] = []
    }
    groups[item.viewDate].push(item)
  })
  return groups
})

const formatDateLabel = (date) => {
  const today = new Date().toISOString().split('T')[0]
  const yesterday = new Date(Date.now() - 86400000).toISOString().split('T')[0]
  if (date === today) return '今天'
  if (date === yesterday) return '昨天'
  return date
}

const removeHistory = (id) => {
  historyList.value = historyList.value.filter(h => h.id !== id)
}

const clearHistory = () => {
  historyList.value = []
}
</script>

<style lang="scss" scoped>
.history-page {
  max-width: 800px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, #E8F4FD 0%, #FFF 100%);
  border-radius: 12px;
  
  .header-icon {
    width: 56px;
    height: 56px;
    background: #3498DB;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
  }
  
  .header-text {
    flex: 1;
    
    h1 {
      font-size: 1.4em;
      color: #2C3E50;
      margin-bottom: 4px;
    }
    
    p {
      color: #95A5A6;
      font-size: 14px;
    }
  }
}

.date-group {
  margin-bottom: 24px;
  
  .date-label {
    font-size: 13px;
    color: #95A5A6;
    margin-bottom: 12px;
    padding-left: 4px;
  }
}

.history-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.history-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 18px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  
  .item-content {
    flex: 1;
    
    .title {
      font-size: 15px;
      color: #2C3E50;
      font-weight: 500;
      display: block;
      margin-bottom: 6px;
      
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
</style>
