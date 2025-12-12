<template>
  <div class="category-page">
    <!-- 分类头部 -->
    <header class="category-header">
      <div class="category-info">
        <span class="cat-dot" :style="{ background: getCategoryColor(categoryName) }"></span>
        <div>
          <h1>{{ categoryName }}</h1>
          <p>共 {{ filteredArticles.length }} 篇文章</p>
        </div>
      </div>
      
      <!-- 排序 -->
      <div class="sort-options">
        <el-select v-model="sortBy" size="small" style="width: 120px">
          <el-option label="最新发布" value="latest" />
          <el-option label="最多阅读" value="views" />
        </el-select>
      </div>
    </header>

    <!-- 文章列表 -->
    <div class="article-list" v-if="sortedArticles.length > 0">
      <ArticleCard v-for="article in sortedArticles" :key="article.id" :article="article" />
    </div>

    <el-empty v-else description="该分类暂无文章" />

    <!-- 分页 -->
    <el-pagination
      v-if="sortedArticles.length > 0"
      layout="prev, pager, next"
      :total="sortedArticles.length"
      :page-size="10"
      background
      class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { articles } from '@/data/mock'
import ArticleCard from '@/components/ArticleCard.vue'

const route = useRoute()
const sortBy = ref('latest')

const categoryName = computed(() => route.params.name)

const categoryColors = {
  '设计沉思': '#9B59B6',
  '技术探索': '#3498DB',
  '生活随笔': '#1ABC9C',
  '读书笔记': '#E67E22',
  '哲学思辨': '#E74C3C'
}

const getCategoryColor = (name) => categoryColors[name] || '#95A5A6'

const filteredArticles = computed(() => 
  articles.filter(a => a.category === categoryName.value)
)

const sortedArticles = computed(() => {
  const list = [...filteredArticles.value]
  if (sortBy.value === 'views') {
    return list.sort((a, b) => b.views - a.views)
  }
  return list.sort((a, b) => new Date(b.date) - new Date(a.date))
})
</script>

<style lang="scss" scoped>
.category-page {
  max-width: 100%;
}

.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: #fff;
  border-radius: 12px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

.category-info {
  display: flex;
  align-items: center;
  gap: 16px;
  
  .cat-dot {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    flex-shrink: 0;
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
  .category-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
}
</style>
