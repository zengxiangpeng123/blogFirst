<template>
  <div class="home container">
    <!-- 头图区域 -->
    <section class="hero">
      <h1>静隅思录</h1>
      <p>在喧嚣中寻一处静隅，记录思考与成长</p>
    </section>
    
    <div class="content-wrapper">
      <!-- 文章列表 -->
      <div class="article-list">
        <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
        
        <el-pagination
          layout="prev, pager, next"
          :total="50"
          :page-size="10"
          background
          style="justify-content: center; margin-top: 30px;"
        />
      </div>
      
      <!-- 侧边栏 -->
      <Sidebar />
    </div>
  </div>
</template>

<script setup>
import { articles } from '@/data/mock'
import ArticleCard from '@/components/ArticleCard.vue'
import Sidebar from '@/components/Sidebar.vue'
</script>

<style lang="scss" scoped>
.hero {
  text-align: center;
  padding: 60px 0;
  background: linear-gradient(135deg, #2C3E50 0%, #3498DB 100%);
  border-radius: 12px;
  margin-bottom: 40px;
  color: #fff;
  
  h1 {
    font-size: 2.5em;
    margin-bottom: 15px;
    font-family: 'Georgia', serif;
  }
  
  p {
    font-size: 1.1em;
    opacity: 0.9;
  }
}

.content-wrapper {
  display: flex;
  gap: 30px;
  
  .article-list {
    flex: 1;
  }
}

@media (max-width: 992px) {
  .content-wrapper {
    flex-direction: column;
  }
}
</style>
