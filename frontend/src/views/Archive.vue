<template>
  <div class="archive-page">
    <!-- 页面头部 -->
    <header class="page-header">
      <div class="header-content">
        <h1>全部文章</h1>
        <p>共 {{ articles.length }} 篇文章，记录思考与成长</p>
      </div>
      
      <!-- 筛选和排序 -->
      <div class="filter-bar">
        <div class="filter-tabs">
          <span 
            v-for="tab in filterTabs" 
            :key="tab.value"
            :class="['tab', { active: activeTab === tab.value }]"
            @click="activeTab = tab.value"
          >
            {{ tab.label }}
          </span>
        </div>
        <div class="sort-options">
          <el-select v-model="sortBy" size="small" style="width: 120px">
            <el-option label="最新发布" value="latest" />
            <el-option label="最多阅读" value="views" />
            <el-option label="阅读时长" value="readTime" />
          </el-select>
        </div>
      </div>
    </header>

    <!-- 文章列表 -->
    <div class="article-list">
      <ArticleCard v-for="article in sortedArticles" :key="article.id" :article="article" />
    </div>

    <!-- 分页 -->
    <el-pagination
      v-if="sortedArticles.length > 0"
      layout="prev, pager, next"
      :total="sortedArticles.length"
      :page-size="10"
      background
      class="pagination"
    />

    <el-empty v-if="sortedArticles.length === 0" description="暂无文章" />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { articles } from '@/data/mock'
import ArticleCard from '@/components/ArticleCard.vue'

const activeTab = ref('all')
const sortBy = ref('latest')

const filterTabs = [
  { label: '全部', value: 'all' },
  { label: '置顶', value: 'pinned' },
  { label: '设计', value: '设计沉思' },
  { label: '技术', value: '技术探索' },
  { label: '生活', value: '生活随笔' }
]

const filteredArticles = computed(() => {
  if (activeTab.value === 'all') return articles
  if (activeTab.value === 'pinned') return articles.filter(a => a.pinned)
  return articles.filter(a => a.category === activeTab.value)
})

const sortedArticles = computed(() => {
  const list = [...filteredArticles.value]
  switch (sortBy.value) {
    case 'views':
      return list.sort((a, b) => b.views - a.views)
    case 'readTime':
      return list.sort((a, b) => b.readTime - a.readTime)
    default:
      return list.sort((a, b) => new Date(b.date) - new Date(a.date))
  }
})
</script>

<style lang="scss" scoped>
.archive-page {
  max-width: 100%;
}

.page-header {
  margin-bottom: 24px;
  
  .header-content {
    margin-bottom: 20px;
    
    h1 {
      font-size: 1.5em;
      color: #2C3E50;
      margin-bottom: 8px;
    }
    
    p {
      color: #95A5A6;
      font-size: 14px;
    }
  }
}

.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

.filter-tabs {
  display: flex;
  gap: 8px;
  
  .tab {
    padding: 6px 14px;
    border-radius: 16px;
    font-size: 13px;
    color: #666;
    cursor: pointer;
    transition: all 0.2s;
    
    &:hover {
      background: #F5F7FA;
    }
    
    &.active {
      background: rgba(#1ABC9C, 0.1);
      color: #1ABC9C;
      font-weight: 500;
    }
  }
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.pagination {
  justify-content: center;
  margin-top: 24px;
}

@media (max-width: 768px) {
  .filter-bar {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }
  
  .filter-tabs {
    flex-wrap: wrap;
  }
}
</style>
