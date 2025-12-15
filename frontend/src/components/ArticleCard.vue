<template>
  <article class="article-card hover-card" :class="{ pinned: article.pinned }">
    <div class="card-header">
      <span class="category">{{ article.category }}</span>
      <span v-if="article.pinned" class="pin-badge">置顶</span>
    </div>
    
    <router-link :to="`/article/${article.id}`">
      <h2 class="title">{{ article.title }}</h2>
    </router-link>
    
    <div class="meta">
      <span><el-icon><Calendar /></el-icon> {{ article.date }}</span>
      <span><el-icon><Clock /></el-icon> {{ article.readTime }} 分钟</span>
      <span><el-icon><View /></el-icon> {{ article.views }}</span>
    </div>
    
    <p class="summary">{{ article.summary }}</p>
    
    <div class="card-footer">
      <div class="tags">
        <span v-for="tag in tags" :key="tag" class="tag">{{ tag }}</span>
      </div>
      <router-link :to="`/article/${article.id}`" class="read-more">继续阅读 →</router-link>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  article: { type: Object, required: true }
})

// 将标签字符串转换为数组
const tags = computed(() => {
  if (!props.article.tags) return []
  // 如果已经是数组，直接返回
  if (Array.isArray(props.article.tags)) return props.article.tags
  // 如果是字符串，按逗号分割
  return props.article.tags.split(',').filter(tag => tag.trim())
})
</script>

<style lang="scss" scoped>
.article-card {
  background: #fff;
  border-radius: 8px;
  padding: 25px;
  margin-bottom: 20px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  
  &.pinned {
    border-left: 4px solid #1ABC9C;
  }
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
  
  .category {
    padding: 3px 10px;
    background: rgba(#1ABC9C, 0.1);
    color: #1ABC9C;
    border-radius: 3px;
    font-size: 12px;
  }
  
  .pin-badge {
    padding: 2px 8px;
    background: #E74C3C;
    color: #fff;
    border-radius: 3px;
    font-size: 11px;
  }
}

.title {
  color: #2C3E50;
  font-size: 1.3em;
  margin-bottom: 12px;
  line-height: 1.4;
  
  &:hover { color: #1ABC9C; }
}

.meta {
  display: flex;
  gap: 20px;
  color: #95A5A6;
  font-size: 13px;
  margin-bottom: 15px;
  
  span {
    display: flex;
    align-items: center;
    gap: 5px;
  }
}

.summary {
  color: #555;
  line-height: 1.8;
  margin-bottom: 15px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  
  .read-more {
    color: #1ABC9C;
    font-size: 14px;
  }
}
</style>
