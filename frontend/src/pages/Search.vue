<template>
  <div class="search-page">
    <div class="search-header">
      <h2>
        <el-icon><Search /></el-icon>
        搜索结果
      </h2>
      <p class="search-info">
        关键词 "<span class="keyword">{{ keyword }}</span>" 
        共找到 <span class="count">{{ total }}</span> 篇文章
      </p>
    </div>

    <div v-loading="loading" class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
      <el-empty v-if="!loading && articles.length === 0" description="未找到相关文章" />
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
import { ref, watch, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { searchArticles } from '@/api/article'
import ArticleCard from '@/components/ArticleCard.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const keyword = ref('')
const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)

const loadSearchResults = async () => {
  if (!keyword.value) return
  
  loading.value = true
  try {
    const res = await searchArticles(keyword.value, currentPage.value, pageSize.value)
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('搜索失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadSearchResults()
}

// 监听路由参数变化
watch(
  () => route.query.keyword,
  (newKeyword) => {
    keyword.value = newKeyword || ''
    currentPage.value = 1
    loadSearchResults()
  },
  { immediate: true }
)
</script>

<style lang="scss" scoped>
.search-page {
  max-width: 900px;
  margin: 0 auto;
}

.search-header {
  margin-bottom: 24px;
  
  h2 {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 1.4em;
    color: #2C3E50;
    margin-bottom: 8px;
  }
  
  .search-info {
    color: #666;
    font-size: 14px;
    
    .keyword {
      color: #1ABC9C;
      font-weight: 500;
    }
    
    .count {
      color: #1ABC9C;
      font-weight: 600;
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
</style>
