<template>
  <div class="home container">
    <!-- 轮播图区域 -->
    <section class="hero-carousel">
      <el-carousel height="400px" :interval="5000" arrow="hover" indicator-position="outside">
        <el-carousel-item v-for="(img, index) in carouselImages" :key="index">
          <div class="carousel-slide" :style="{ backgroundImage: `url(${img.src})` }">
            <div class="carousel-content">
              <h1>{{ img.title }}</h1>
              <p>{{ img.desc }}</p>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
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

const carouselImages = [
  { src: '/images/static_index/首页界面图片_1.png', title: '静隅思录', desc: '在喧嚣中寻一处静隅，记录思考与成长' },
  { src: '/images/static_index/首页界面图片_2.png', title: '深度思考', desc: '沉淀知识，探索未知的边界' },
  { src: '/images/static_index/首页界面图片_3.png', title: '生活感悟', desc: '用文字记录生活中的点滴美好' }
]
</script>

<style lang="scss" scoped>
.hero-carousel {
  margin-bottom: 40px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  
  :deep(.el-carousel__indicators--outside) {
    margin-top: 10px;
    
    .el-carousel__indicator--horizontal button {
      width: 30px;
      height: 4px;
      border-radius: 2px;
      background: #ddd;
    }
    
    .el-carousel__indicator--horizontal.is-active button {
      background: #1ABC9C;
    }
  }
}

.carousel-slide {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
  
  &::before {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(to bottom, rgba(0,0,0,0.1), rgba(0,0,0,0.5));
  }
}

.carousel-content {
  position: absolute;
  bottom: 60px;
  left: 50px;
  color: #fff;
  text-shadow: 0 2px 10px rgba(0,0,0,0.3);
  
  h1 {
    font-size: 2.5em;
    margin-bottom: 15px;
    font-family: 'Georgia', serif;
  }
  
  p {
    font-size: 1.2em;
    opacity: 0.95;
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
  
  .carousel-content {
    left: 20px;
    bottom: 40px;
    
    h1 { font-size: 1.8em; }
    p { font-size: 1em; }
  }
}
</style>
