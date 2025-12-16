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

    <!-- 文章列表 -->
    <div class="section-header">
      <h2>最新文章</h2>
      <router-link to="/archive" class="more-link">查看更多 →</router-link>
    </div>
    
    <div v-loading="loading" class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
      <el-empty v-if="!loading && articles.length === 0" description="暂无文章" />
    </div>
    
    <el-pagination
      v-if="total > 0"
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      :current-page="currentPage"
      background
      class="pagination"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllArticles } from '@/api/article'
import ArticleCard from '@/components/ArticleCard.vue'
import { ElMessage } from 'element-plus'

const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)

const carouselImages = [
  { src: '/images/static_index/首页界面图片_1.png', title: '静隅思录', desc: '在喧嚣中寻一处静隅，记录思考与成长' },
  { src: '/images/static_index/首页界面图片_2.png', title: '深度思考', desc: '沉淀知识，探索未知的边界' },
  { src: '/images/static_index/首页界面图片_3.png', title: '生活感悟', desc: '用文字记录生活中的点滴美好' }
]

// 加载文章列表
const loadArticles = async () => {
  loading.value = true
  try {
    const res = await getAllArticles(currentPage.value, pageSize.value)
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('加载文章失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 分页改变
const handlePageChange = (page) => {
  currentPage.value = page
  loadArticles()
}

onMounted(() => {
  loadArticles()
})
</script>

<style lang="scss" scoped>
.home {
  max-width: 900px;
  margin: 0 auto;
}

.hero-carousel {
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
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
    font-size: 2em;
    margin-bottom: 10px;
    font-family: 'Georgia', serif;
  }
  
  p {
    font-size: 1em;
    opacity: 0.9;
  }
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
  
  h2 {
    font-size: 16px;
    color: #333;
    font-weight: 500;
  }
  
  .more-link {
    font-size: 13px;
    color: #1ABC9C;
    
    &:hover {
      color: #16a085;
    }
  }
}

.article-list {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}

.pagination {
  justify-content: center;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .home {
    max-width: 100%;
  }
  
  .carousel-content {
    left: 20px;
    h1 { font-size: 1.5em; }
    p { font-size: 0.9em; }
  }
}
</style>
