<template>
  <div class="category-page container">
    <header class="page-header">
      <h1>分类：{{ categoryName }}</h1>
      <p>共 {{ filteredArticles.length }} 篇文章</p>
    </header>
    
    <div class="article-list">
      <div v-for="article in filteredArticles" :key="article.id" class="article-item hover-card">
        <router-link :to="`/article/${article.id}`">
          <h3>{{ article.title }}</h3>
        </router-link>
        <div class="meta">
          <span>{{ article.date }}</span>
          <span>{{ article.readTime }} 分钟阅读</span>
        </div>
      </div>
      
      <el-empty v-if="!filteredArticles.length" description="该分类暂无文章" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { articles } from '@/data/mock'

const route = useRoute()
const categoryName = computed(() => route.params.name)
const filteredArticles = computed(() => 
  articles.filter(a => a.category === categoryName.value)
)
</script>

<style lang="scss" scoped>
.page-header {
  text-align: center;
  padding: 40px 0;
  margin-bottom: 30px;
  
  h1 {
    color: #2C3E50;
    font-size: 2em;
    margin-bottom: 10px;
  }
  
  p { color: #95A5A6; }
}

.article-list {
  max-width: 800px;
  margin: 0 auto;
}

.article-item {
  background: #fff;
  padding: 20px 25px;
  border-radius: 8px;
  margin-bottom: 15px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  
  h3 {
    color: #2C3E50;
    font-size: 1.1em;
    margin-bottom: 8px;
    &:hover { color: #1ABC9C; }
  }
  
  .meta {
    color: #95A5A6;
    font-size: 13px;
    display: flex;
    gap: 20px;
  }
}
</style>
