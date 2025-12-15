<template>
  <div class="category-page">
    <!-- 分类头部 -->
    <header class="category-header">
      <div class="category-info">
        <span class="cat-dot" :style="{ background: getCategoryColor(categoryName) }"></span>
        <div>
          <h1>{{ categoryName }}</h1>
          <p>共 {{ total }} 篇文章</p>
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
    <div v-loading="loading" class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
      <el-empty v-if="!loading && articles.length === 0" description="该分类暂无文章" />
    </div>

    <!-- 分页 -->
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
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getArticlesByCategory } from '@/api/article'
import ArticleCard from '@/components/ArticleCard.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const sortBy = ref('latest')
const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)

const categoryName = computed(() => route.params.name)

const categoryColors = {
  '设计沉思': '#9B59B6',
  '技术探索': '#3498DB',
  '生活随笔': '#1ABC9C',
  '读书笔记': '#E67E22',
  '哲学思辨': '#E74C3C'
}

const getCategoryColor = (name) => categoryColors[name] || '#95A5A6'

// 加载文章列表
const loadArticles = async () => {
  // 注意：这里需要 categoryId，但路由传的是 categoryName
  // 实际项目中需要先根据 categoryName 查询 categoryId
  // 暂时使用 mock 数据的映射
  const categoryIdMap = {
    '设计沉思': 1,
    '技术探索': 2,
    '生活随笔': 3,
    '读书笔记': 4,
    '哲学思辨': 5
  }
  
  const categoryId = categoryIdMap[categoryName.value]
  if (!categoryId) {
    ElMessage.warning('分类不存在')
    return
  }
  
  loading.value = true
  try {
    const res = await getArticlesByCategory(categoryId, currentPage.value, pageSize.value)
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

// 监听分类变化
watch(categoryName, () => {
  currentPage.value = 1
  loadArticles()
})

onMounted(() => {
  loadArticles()
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
