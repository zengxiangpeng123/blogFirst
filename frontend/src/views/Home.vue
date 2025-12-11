<template>
  <div class="home">
    <!-- 轮播图区域 -->
    <section class="hero-carousel">
      <el-carousel height="320px" :interval="5000" arrow="hover">
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
      <!-- 主内容区 -->
      <div class="main-area">
        <!-- 文章列表 -->
        <div class="section-header">
          <h2>最新文章</h2>
          <router-link to="/archive" class="more-link">查看更多 →</router-link>
        </div>
        
        <div class="article-list">
          <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
        </div>
        
        <el-pagination
          layout="prev, pager, next"
          :total="50"
          :page-size="10"
          background
          class="pagination"
        />
      </div>

      <!-- 右侧推荐区 -->
      <aside class="recommend-area">
        <!-- 作者卡片 -->
        <div class="widget author-widget">
          <el-avatar :size="64" :src="author.avatar" />
          <h3>{{ author.name }}</h3>
          <p>{{ author.bio }}</p>
          <router-link to="/about" class="profile-link">查看主页</router-link>
        </div>

        <!-- 热门文章 -->
        <div class="widget">
          <div class="widget-header">
            <h4>热门推荐</h4>
          </div>
          <ul class="hot-list">
            <li v-for="(item, index) in hotArticles" :key="item.id">
              <span class="rank" :class="`rank-${index + 1}`">{{ index + 1 }}</span>
              <router-link :to="`/article/${item.id}`">{{ item.title }}</router-link>
            </li>
          </ul>
        </div>

        <!-- 标签云 -->
        <div class="widget">
          <div class="widget-header">
            <h4>热门标签</h4>
          </div>
          <div class="tag-cloud">
            <el-tag v-for="tag in tags" :key="tag" effect="plain" class="tag-item">{{ tag }}</el-tag>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { articles, hotArticles, author } from '@/data/mock'
import ArticleCard from '@/components/ArticleCard.vue'

const carouselImages = [
  { src: '/images/static_index/首页界面图片_1.png', title: '静隅思录', desc: '在喧嚣中寻一处静隅，记录思考与成长' },
  { src: '/images/static_index/首页界面图片_2.png', title: '深度思考', desc: '沉淀知识，探索未知的边界' },
  { src: '/images/static_index/首页界面图片_3.png', title: '生活感悟', desc: '用文字记录生活中的点滴美好' }
]

const tags = ['Vue', '设计', '前端', '生活', '读书', '哲学', '用户体验', 'JavaScript']
</script>

<style lang="scss" scoped>
.hero-carousel {
  margin-bottom: 24px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.08);
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
    background: linear-gradient(to right, rgba(0,0,0,0.5), transparent);
  }
}

.carousel-content {
  position: absolute;
  top: 50%;
  left: 40px;
  transform: translateY(-50%);
  color: #fff;
  text-shadow: 0 2px 10px rgba(0,0,0,0.3);
  
  h1 {
    font-size: 2.2em;
    margin-bottom: 12px;
    font-family: 'Georgia', serif;
  }
  
  p {
    font-size: 1.1em;
    opacity: 0.9;
  }
}

.content-wrapper {
  display: flex;
  gap: 24px;
}

.main-area {
  flex: 1;
  min-width: 0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  
  h2 {
    font-size: 1.2em;
    color: #2C3E50;
    position: relative;
    padding-left: 12px;
    
    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 4px;
      height: 18px;
      background: #1ABC9C;
      border-radius: 2px;
    }
  }
  
  .more-link {
    font-size: 14px;
    color: #1ABC9C;
  }
}

.pagination {
  justify-content: center;
  margin-top: 24px;
}

.recommend-area {
  width: 280px;
  flex-shrink: 0;
}

.widget {
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

.author-widget {
  text-align: center;
  
  h3 {
    color: #2C3E50;
    margin: 12px 0 8px;
    font-size: 1.1em;
  }
  
  p {
    color: #95A5A6;
    font-size: 13px;
    line-height: 1.5;
    margin-bottom: 12px;
  }
  
  .profile-link {
    display: inline-block;
    padding: 6px 16px;
    background: rgba(#1ABC9C, 0.1);
    color: #1ABC9C;
    border-radius: 20px;
    font-size: 13px;
    
    &:hover {
      background: #1ABC9C;
      color: #fff;
    }
  }
}

.widget-header {
  margin-bottom: 12px;
  
  h4 {
    font-size: 15px;
    color: #2C3E50;
    padding-bottom: 10px;
    border-bottom: 1px solid #E8E8E8;
  }
}

.hot-list {
  list-style: none;
  
  li {
    display: flex;
    align-items: flex-start;
    gap: 10px;
    margin-bottom: 12px;
    
    .rank {
      width: 20px;
      height: 20px;
      background: #E8E8E8;
      color: #95A5A6;
      border-radius: 4px;
      font-size: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
      
      &.rank-1 { background: #E74C3C; color: #fff; }
      &.rank-2 { background: #F39C12; color: #fff; }
      &.rank-3 { background: #1ABC9C; color: #fff; }
    }
    
    a {
      font-size: 14px;
      color: #555;
      line-height: 1.4;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      
      &:hover { color: #1ABC9C; }
    }
  }
}

.tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  
  .tag-item {
    cursor: pointer;
    border-color: #E8E8E8;
    
    &:hover {
      color: #1ABC9C;
      border-color: #1ABC9C;
    }
  }
}

@media (max-width: 1024px) {
  .recommend-area { display: none; }
}

@media (max-width: 768px) {
  .carousel-content {
    left: 20px;
    h1 { font-size: 1.6em; }
    p { font-size: 0.95em; }
  }
}
</style>
