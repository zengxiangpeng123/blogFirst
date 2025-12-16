<template>
  <article class="article-card" :class="{ pinned: article.isPinned }">
    <div class="card-content">
      <div class="card-main">
        <div class="card-header">
          <span v-if="article.isPinned" class="pin-badge">置顶</span>
          <span class="author">{{ article.authorName || '匿名用户' }}</span>
        </div>
        
        <router-link :to="`/article/${article.id}`" class="title-link">
          <h2 class="title">{{ article.title }}</h2>
        </router-link>
        
        <p class="summary">{{ article.summary || '暂无摘要' }}</p>
        
        <div class="card-footer">
          <div class="meta">
            <span class="category">{{ article.categoryName || '未分类' }}</span>
            <span class="stat"><el-icon><View /></el-icon> {{ article.viewCount || 0 }}</span>
            <span class="stat"><el-icon><Star /></el-icon> {{ article.favoriteCount || 0 }}</span>
            <span class="date">{{ formatDate(article.createdAt) }}</span>
          </div>
          <div class="tags" v-if="tags.length">
            <span v-for="tag in tags.slice(0, 3)" :key="tag" class="tag">{{ tag }}</span>
          </div>
        </div>
      </div>
      
      <div class="card-cover" v-if="article.cover">
        <img :src="article.cover" alt="封面" />
      </div>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'
import { View, Star } from '@element-plus/icons-vue'

const props = defineProps({
  article: { type: Object, required: true }
})

// 将标签字符串转换为数组
const tags = computed(() => {
  if (!props.article.tags) return []
  if (Array.isArray(props.article.tags)) return props.article.tags
  return props.article.tags.split(',').filter(tag => tag.trim())
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const now = new Date()
  const diff = now - date
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  
  if (days === 0) return '今天'
  if (days === 1) return '昨天'
  if (days < 7) return `${days}天前`
  if (days < 30) return `${Math.floor(days / 7)}周前`
  return dateStr.substring(0, 10)
}
</script>

<style lang="scss" scoped>
.article-card {
  background: #fff;
  border-radius: 4px;
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  transition: background 0.2s;
  
  &:hover {
    background: #fafafa;
  }
  
  &.pinned {
    background: linear-gradient(to right, rgba(26, 188, 156, 0.03), transparent);
    border-left: 3px solid #1ABC9C;
    padding-left: 13px;
  }
}

.card-content {
  display: flex;
  gap: 16px;
}

.card-main {
  flex: 1;
  min-width: 0;
}

.card-cover {
  width: 120px;
  height: 80px;
  flex-shrink: 0;
  border-radius: 4px;
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  
  .author {
    font-size: 12px;
    color: #666;
  }
  
  .pin-badge {
    padding: 1px 6px;
    background: #ff6b6b;
    color: #fff;
    border-radius: 2px;
    font-size: 10px;
  }
}

.title-link {
  display: block;
  margin-bottom: 8px;
}

.title {
  color: #333;
  font-size: 16px;
  font-weight: 500;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  
  &:hover { color: #1ABC9C; }
}

.summary {
  color: #666;
  font-size: 13px;
  line-height: 1.6;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.meta {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 12px;
  color: #999;
  
  .category {
    color: #1ABC9C;
  }
  
  .stat {
    display: flex;
    align-items: center;
    gap: 3px;
  }
  
  .date {
    color: #bbb;
  }
}

.tags {
  display: flex;
  gap: 6px;
  
  .tag {
    padding: 2px 8px;
    background: #f5f5f5;
    color: #666;
    border-radius: 2px;
    font-size: 11px;
  }
}

@media (max-width: 768px) {
  .card-cover {
    width: 80px;
    height: 56px;
  }
  
  .title {
    font-size: 14px;
  }
  
  .summary {
    -webkit-line-clamp: 1;
  }
  
  .tags {
    display: none;
  }
}
</style>
