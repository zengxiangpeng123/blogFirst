<template>
  <div class="favorites-page">
    <header class="page-header">
      <div class="header-icon">
        <el-icon :size="32"><Star /></el-icon>
      </div>
      <div>
        <h1>我的收藏</h1>
        <p>收藏的精彩内容，随时回顾</p>
      </div>
    </header>

    <div class="favorites-list" v-if="favorites.length > 0">
      <div v-for="item in favorites" :key="item.id" class="favorite-item hover-card">
        <div class="item-content">
          <router-link :to="`/article/${item.id}`" class="title">{{ item.title }}</router-link>
          <div class="meta">
            <span class="category">{{ item.category }}</span>
            <span class="date">收藏于 {{ item.favoriteDate }}</span>
          </div>
        </div>
        <el-button type="danger" text @click="removeFavorite(item.id)">
          <el-icon><Delete /></el-icon>
        </el-button>
      </div>
    </div>

    <el-empty v-else description="暂无收藏内容">
      <template #image>
        <el-icon :size="60" color="#E0E0E0"><Star /></el-icon>
      </template>
      <el-button type="primary" @click="$router.push('/')">去发现好文章</el-button>
    </el-empty>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Star, Delete } from '@element-plus/icons-vue'

const favorites = ref([
  { id: 1, title: '设计中的留白艺术：少即是多', category: '设计沉思', favoriteDate: '2024-07-16' },
  { id: 2, title: '从Vue2到Vue3：一次渐进式的迁移之旅', category: '技术探索', favoriteDate: '2024-07-12' }
])

const removeFavorite = (id) => {
  favorites.value = favorites.value.filter(f => f.id !== id)
}
</script>

<style lang="scss" scoped>
.favorites-page {
  max-width: 800px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, #FFF9E6 0%, #FFF 100%);
  border-radius: 12px;
  
  .header-icon {
    width: 56px;
    height: 56px;
    background: #FFD93D;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
  }
  
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

.favorites-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.favorite-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
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
      margin-bottom: 8px;
      
      &:hover { color: #1ABC9C; }
    }
    
    .meta {
      display: flex;
      gap: 16px;
      font-size: 13px;
      color: #95A5A6;
      
      .category {
        color: #1ABC9C;
      }
    }
  }
}
</style>
