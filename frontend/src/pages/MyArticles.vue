<template>
  <div class="my-articles-page">
    <!-- 顶部状态标签 -->
    <div class="status-tabs">
      <span 
        v-for="tab in statusTabs" 
        :key="tab.value"
        :class="['status-tab', { active: currentStatus === tab.value }]"
        @click="handleStatusChange(tab.value)"
      >
        {{ tab.label }}({{ tab.count }})
      </span>
      <div class="tab-actions">
        <el-button v-if="currentStatus !== 'draft'" type="warning" size="small">用来推广</el-button>
        <el-button v-if="currentStatus === 'draft'" type="danger" size="small" @click="clearDrafts">清空草稿箱</el-button>
      </div>
    </div>

    <!-- 筛选栏（草稿页面简化） -->
    <div class="filter-row">
      <template v-if="currentStatus !== 'draft'">
        <el-select v-model="filterYear" placeholder="年：不限" size="small" clearable style="width: 100px">
          <el-option label="不限" value="" />
          <el-option label="2025" value="2025" />
          <el-option label="2024" value="2024" />
        </el-select>
        <el-select v-model="filterMonth" placeholder="月：不限" size="small" clearable style="width: 100px">
          <el-option label="不限" value="" />
          <el-option v-for="m in 12" :key="m" :label="m + '月'" :value="m" />
        </el-select>
        <el-select v-model="filterCategory" placeholder="分类专栏：不限" size="small" clearable style="width: 140px">
          <el-option label="不限" value="" />
          <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
        </el-select>
      </template>
      <template v-else>
        <el-select v-model="filterYear" placeholder="年：不限" size="small" clearable style="width: 100px">
          <el-option label="不限" value="" />
          <el-option label="2025" value="2025" />
          <el-option label="2024" value="2024" />
        </el-select>
        <el-select v-model="filterMonth" placeholder="月：不限" size="small" clearable style="width: 100px">
          <el-option label="不限" value="" />
          <el-option v-for="m in 12" :key="m" :label="m + '月'" :value="m" />
        </el-select>
      </template>
      <el-input v-model="searchKeyword" placeholder="请输入标题查询" size="small" style="width: 200px; margin-left: auto" clearable>
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <!-- 文章列表表格 -->
    <div class="article-table">
      <div class="table-header">
        <span class="col-article">文章</span>
        <template v-if="currentStatus !== 'draft'">
          <span class="col-stat">阅读</span>
          <span class="col-stat">点赞</span>
          <span class="col-stat">收藏</span>
        </template>
        <span class="col-action">操作</span>
      </div>

      <div v-loading="loading" class="table-body">
        <div v-for="article in articles" :key="article.id" class="table-row">
          <div class="col-article">
            <div class="article-cover">
              <img v-if="article.cover" :src="article.cover" alt="封面" />
              <span v-else class="no-cover">未设置封面</span>
            </div>
            <div class="article-info">
              <h3 class="article-title" @click="viewArticle(article.id)">{{ article.title }}</h3>
              <div class="article-meta">
                <el-tag v-if="article.isPublished === 1" type="warning" size="small">公开</el-tag>
                <el-tag v-else-if="article.isPublished === 2" type="info" size="small">私密</el-tag>
                <el-tag v-else size="small">草稿</el-tag>
                <span class="date">{{ formatDate(article.createdAt) }}</span>
              </div>
            </div>
          </div>
          <template v-if="currentStatus !== 'draft'">
            <span class="col-stat">{{ article.viewCount || 0 }}</span>
            <span class="col-stat">{{ article.likeCount || 0 }}</span>
            <span class="col-stat">{{ article.favoriteCount || 0 }}</span>
          </template>
          <div class="col-action">
            <el-button link type="primary" size="small" @click="editArticle(article.id)">编辑</el-button>
            <el-button link type="primary" size="small" @click="viewArticle(article.id)">预览</el-button>
            <el-button link type="danger" size="small" @click="deleteArticle(article.id)">删除</el-button>
          </div>
        </div>
        <el-empty v-if="!loading && articles.length === 0" description="暂无文章" />
      </div>
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
import { useRouter, useRoute } from 'vue-router'
import { getArticlePage, getArticleListByUserId, deleteArticle as deleteArticleApi } from '@/api/article'
import { getCurrentUser } from '@/api/user'
import { Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()

const currentStatus = ref('all')
const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)
const userId = ref(null)

// 筛选条件
const filterYear = ref('')
const filterMonth = ref('')
const filterCategory = ref('')
const searchKeyword = ref('')

// 统计数量
const counts = ref({ all: 0, published: 0, private: 0, draft: 0 })

const statusTabs = computed(() => [
  { label: '全部', value: 'all', count: counts.value.all },
  { label: '已发布', value: 'published', count: counts.value.published },
  { label: '仅自己可见', value: 'private', count: counts.value.private },
  { label: '草稿箱', value: 'draft', count: counts.value.draft }
])

const categories = [
  { id: 1, name: '设计沉思' },
  { id: 2, name: '技术探索' },
  { id: 3, name: '生活随笔' },
  { id: 4, name: '读书笔记' },
  { id: 5, name: '哲学思辨' }
]

// 监听URL参数变化
watch(() => route.query.status, (status) => {
  currentStatus.value = status || 'all'
  currentPage.value = 1
  if (userId.value) {
    loadArticles()
  }
})

// 监听搜索关键词变化（防抖）
let searchTimer = null
watch(searchKeyword, () => {
  clearTimeout(searchTimer)
  searchTimer = setTimeout(() => {
    currentPage.value = 1
    loadArticles()
  }, 300)
})

const handleStatusChange = (value) => {
  currentStatus.value = value
  currentPage.value = 1
  if (value === 'all') {
    router.push('/my-articles')
  } else {
    router.push(`/my-articles?status=${value}`)
  }
}

// 加载各状态的文章数量
const loadCounts = async () => {
  if (!userId.value) return
  
  try {
    // 获取用户所有文章，然后在前端统计
    const res = await getArticleListByUserId(userId.value)
    const allArticles = res.data || []
    
    counts.value = {
      all: allArticles.length,
      published: allArticles.filter(a => a.isPublished === 1).length,
      private: allArticles.filter(a => a.isPublished === 2).length,
      draft: allArticles.filter(a => a.isPublished === 0).length
    }
  } catch (error) {
    console.error('加载统计失败', error)
  }
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
    
    if (currentStatus.value === 'published') {
      params.isPublished = 1
    } else if (currentStatus.value === 'private') {
      params.isPublished = 2
    } else if (currentStatus.value === 'draft') {
      params.isPublished = 0
    }
    
    if (filterCategory.value) {
      params.categoryId = filterCategory.value
    }
    
    // 关键词搜索
    if (searchKeyword.value.trim()) {
      params.keyword = searchKeyword.value.trim()
    }
    
    const res = await getArticlePage(params)
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('加载文章失败')
  } finally {
    loading.value = false
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 19)
}

const editArticle = (id) => {
  router.push(`/write?id=${id}`)
}

const viewArticle = (id) => {
  router.push(`/article/${id}`)
}

const deleteArticle = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteArticleApi(id)
    ElMessage.success('删除成功')
    loadArticles()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const clearDrafts = async () => {
  try {
    await ElMessageBox.confirm('确定要清空所有草稿吗？此操作不可恢复！', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    // TODO: 调用批量删除草稿API
    ElMessage.success('草稿已清空')
    loadArticles()
  } catch (error) {
    // 取消操作
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadArticles()
}

onMounted(async () => {
  if (route.query.status) {
    currentStatus.value = route.query.status
  }
  
  try {
    const res = await getCurrentUser()
    userId.value = res.data.id
    loadCounts()
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

.status-tabs {
  display: flex;
  align-items: center;
  gap: 24px;
  padding: 16px 0;
  border-bottom: 1px solid #E8E8E8;
  margin-bottom: 16px;
  
  .status-tab {
    font-size: 14px;
    color: #666;
    cursor: pointer;
    padding-bottom: 8px;
    border-bottom: 2px solid transparent;
    
    &:hover {
      color: #1ABC9C;
    }
    
    &.active {
      color: #1ABC9C;
      font-weight: 500;
      border-bottom-color: #1ABC9C;
    }
  }
  
  .tab-actions {
    margin-left: auto;
  }
}

.filter-row {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 0;
  margin-bottom: 16px;
}

.article-table {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}

.table-header {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  background: #FAFAFA;
  border-bottom: 1px solid #E8E8E8;
  font-size: 13px;
  color: #666;
}

.table-body {
  min-height: 200px;
}

.table-row {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #F0F0F0;
  
  &:hover {
    background: #FAFAFA;
  }
  
  &:last-child {
    border-bottom: none;
  }
}

.col-article {
  flex: 1;
  display: flex;
  gap: 16px;
  min-width: 0;
}

.col-stat {
  width: 80px;
  text-align: center;
  font-size: 14px;
  color: #666;
}

.col-action {
  width: 150px;
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.article-cover {
  width: 100px;
  height: 70px;
  border-radius: 6px;
  overflow: hidden;
  background: #F5F5F5;
  flex-shrink: 0;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .no-cover {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    font-size: 12px;
    color: #999;
  }
}

.article-info {
  flex: 1;
  min-width: 0;
  
  .article-title {
    font-size: 15px;
    color: #2C3E50;
    margin-bottom: 8px;
    cursor: pointer;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    
    &:hover {
      color: #1ABC9C;
    }
  }
  
  .article-meta {
    display: flex;
    align-items: center;
    gap: 8px;
    
    .date {
      font-size: 12px;
      color: #999;
    }
  }
}

.pagination {
  justify-content: center;
  margin-top: 24px;
}

@media (max-width: 768px) {
  .status-tabs {
    flex-wrap: wrap;
    gap: 12px;
  }
  
  .filter-row {
    flex-wrap: wrap;
  }
  
  .col-stat {
    display: none;
  }
  
  .col-action {
    width: auto;
  }
}
</style>
