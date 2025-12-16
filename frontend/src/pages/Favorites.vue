<template>
  <div class="favorites-page">
    <header class="page-header">
      <div class="header-icon">
        <el-icon :size="32"><Star /></el-icon>
      </div>
      <div>
        <h1>我的收藏</h1>
        <p>收藏的精彩内容，随时回顾</p>
      </div>
    </header>

    <div class="favorites-list" v-loading="loading" v-if="favorites.length > 0 || loading">
      <div v-for="item in favorites" :key="item.id" class="favorite-item hover-card">
        <div class="item-content">
          <router-link :to="`/article/${item.articleId}`" class="title">{{ item.title }}</router-link>
          <div class="meta">
            <span class="category">{{ item.categoryName || '未分类' }}</span>
            <span class="date">收藏于 {{ formatDate(item.createdAt) }}</span>
          </div>
        </div>
        <el-button type="danger" text @click="removeFavorite(item)">
          <el-icon><Delete /></el-icon>
        </el-button>
      </div>
    </div>

    <el-empty v-else description="暂无收藏内容">
      <template #image>
        <el-icon :size="60" color="#E0E0E0"><Star /></el-icon>
      </template>
      <el-button type="primary" @click="$router.push('/')">去发现好文章</el-button>
    </el-empty>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Star, Delete } from '@element-plus/icons-vue'
import { getFavoriteList, clickFavorite } from '@/api/favorite'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const favorites = ref([])
const loading = ref(false)
const userId = ref(null)

// 加载收藏列表
const loadFavorites = async () => {
  if (!userId.value) return
  
  loading.value = true
  try {
    const res = await getFavoriteList({ userId: userId.value })
    favorites.value = res.data || []
  } catch (error) {
    console.error('加载收藏失败', error)
  } finally {
    loading.value = false
  }
}

// 取消收藏
const removeFavorite = async (item) => {
  try {
    await ElMessageBox.confirm('确定要取消收藏这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await clickFavorite({
      userId: userId.value,
      articleId: item.articleId,
      isExist: 1  // 当前是已收藏状态
    })
    
    ElMessage.success('已取消收藏')
    // 从列表中移除
    favorites.value = favorites.value.filter(f => f.id !== item.id)
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('操作失败')
    }
  }
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 10)
}

onMounted(() => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    const userInfo = JSON.parse(userInfoStr)
    userId.value = userInfo.id
    loadFavorites()
  } else {
    ElMessage.warning('请先登录')
    router.push('/login')
  }
})
</script>

<style lang="scss" scoped>
.favorites-page {
  max-width: 800px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, #FFF9E6 0%, #FFF 100%);
  border-radius: 12px;
  
  .header-icon {
    width: 56px;
    height: 56px;
    background: #FFD93D;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
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

.favorites-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.favorite-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  
  .item-content {
    flex: 1;
    
    .title {
      font-size: 15px;
      color: #2C3E50;
      font-weight: 500;
      display: block;
      margin-bottom: 8px;
      
      &:hover { color: #1ABC9C; }
    }
    
    .meta {
      display: flex;
      gap: 16px;
      font-size: 13px;
      color: #95A5A6;
      
      .category {
        color: #1ABC9C;
      }
    }
  }
}
</style>
