<template>
  <div class="my-articles-page">
    <header class="page-header">
      <div class="header-content">
        <h1>我的文章</h1>
        <p>共 {{ total }} 篇文章</p>
      </div>
      
      <div class="filter-bar">
        <div class="filter-tabs">
          <span 
            v-for="tab in filterTabs" 
            :key="tab.value"
            :class="['tab', { active: activeTab === tab.value }]"
            @click="handleTabChange(tab.value)"
          >
            {{ tab.label }}
          </span>
        </div>
        <div class="header-actions">
          <el-select v-model="sortBy" size="small" style="width: 120px">
            <el-option label="最新发布" value="latest" />
            <el-option label="最多阅读" value="views" />
          </el-select>
          <el-button type="primary" size="small" @click="$router.push('/write')">
            <el-icon><EditPen /></el-icon>
            写文章
          </el-button>
        </div>
      </div>
    </header>

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
import { useRouter } from 'vue-router'
import { getArticlePage } from '@/api/article'
import { getCurrentUser } from '@/api/user'
import ArticleCard from '@/components/ArticleCard.vue'
import { EditPen } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

const activeTab = ref('all')
const sortBy = ref('latest')
const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)
const userId = ref(null)

// 分类ID映射
const categoryMap = {
  design: 1,
  tech: 2,
  life: 3
}

const filterTabs = [
  { label: '全部', value: 'all' },
  { label: '置顶', value: 'pinned' },
  { label: '设计', value: 'design' },
  { label: '技术', value: 'tech' },
  { label: '生活', value: 'life' }
]

const handleTabChange = (value) => {
  activeTab.value = value
  currentPage.value = 1
  loadArticles()
}

const loadArticles = async () => {
  if (!userId.value) return
  
  loading.value = true
  try {
    const params = {
      userId: userId.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    }
    
    if (activeTab.value === 'pinned') {
      params.isPinned = 1
    } else if (categoryMap[activeTab.value]) {
      params.categoryId = categoryMap[activeTab.value]
    }
    
    const res = await getArticlePage(params)
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('加载文章失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadArticles()
}

onMounted(async () => {
  try {
    const res = await getCurrentUser()
    userId.value = res.data.id
    loadArticles()
  } catch (error) {
    ElMessage.error('请先登录')
    router.push('/login')
  }
})
</script>

<style lang="scss" scoped>
.my-articles-page {
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

.header-actions {
  display: flex;
  gap: 12px;
  align-items: center;
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
