<template>
  <div class="article-page container" v-loading="loading">
    <div class="content-wrapper">
      <article class="article-main">
        <!-- 文章头部 -->
        <header class="article-header">
          <span class="category">{{ article.categoryName || '未分类' }}</span>
          <h1>{{ article.title }}</h1>
          <div class="meta">
            <span><el-icon><Calendar /></el-icon> {{ formatDate(article.createdAt) }}</span>
            <span><el-icon><View /></el-icon> {{ article.viewCount || 0 }} 次浏览</span>
          </div>
        </header>
        
        <!-- 文章正文 -->
        <div class="article-content" v-html="renderedContent"></div>
        
        <!-- 文章尾部 -->
        <footer class="article-footer">
          <div v-if="article.tags" class="tags">
            <el-icon><PriceTag /></el-icon>
            <span v-for="tag in article.tags.split(',')" :key="tag" class="tag">{{ tag }}</span>
          </div>
          
          <div class="copyright-notice">
            <el-icon><Warning /></el-icon>
            本文采用 CC BY-NC-SA 4.0 协议，转载请注明出处。
          </div>
          
          <div class="nav-links">
            <router-link to="/archive" class="prev">← 返回文章列表</router-link>
          </div>
        </footer>
      </article>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleById } from '@/api/article'
import { ElMessage } from 'element-plus'

const route = useRoute()
const article = ref({})
const loading = ref(false)

// 加载文章详情
const loadArticle = async () => {
  const id = route.params.id
  if (!id) return
  
  loading.value = true
  try {
    const res = await getArticleById(id)
    article.value = res.data || {}
  } catch (error) {
    ElMessage.error('加载文章失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 简单的 Markdown 渲染
const renderedContent = computed(() => {
  const content = article.value.content || ''
  return content
    .replace(/^## (.+)$/gm, '<h2 id="$1">$1</h2>')
    .replace(/^### (.+)$/gm, '<h3>$1</h3>')
    .replace(/^> (.+)$/gm, '<blockquote>$1</blockquote>')
    .replace(/```(\w+)?\n([\s\S]*?)```/g, '<pre><code>$2</code></pre>')
    .replace(/\n\n/g, '</p><p>')
    .replace(/^(.+)$/gm, (match) => {
      if (match.startsWith('<')) return match
      return `<p>${match}</p>`
    })
})

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').substring(0, 10)
}

onMounted(() => {
  loadArticle()
})
</script>

<style lang="scss" scoped>
.content-wrapper {
  display: flex;
  gap: 30px;
}

.article-main {
  flex: 1;
  background: #fff;
  border-radius: 8px;
  padding: 40px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  max-width: 800px;
}

.article-header {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ECF0F1;
  
  .category {
    display: inline-block;
    padding: 4px 12px;
    background: rgba(#1ABC9C, 0.1);
    color: #1ABC9C;
    border-radius: 3px;
    font-size: 13px;
    margin-bottom: 15px;
  }
  
  h1 {
    color: #2C3E50;
    font-size: 2em;
    line-height: 1.4;
    margin-bottom: 20px;
  }
  
  .meta {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    color: #95A5A6;
    font-size: 14px;
    
    span {
      display: flex;
      align-items: center;
      gap: 5px;
    }
  }
}

.article-footer {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #ECF0F1;
  
  .tags {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
    color: #95A5A6;
  }
  
  .share {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
    color: #555;
  }
  
  .copyright-notice {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 15px;
    background: #F9F9F9;
    border-radius: 6px;
    color: #95A5A6;
    font-size: 14px;
    margin-bottom: 20px;
  }
  
  .nav-links {
    display: flex;
    justify-content: space-between;
    
    a {
      color: #1ABC9C;
      &:hover { text-decoration: underline; }
    }
  }
}

@media (max-width: 992px) {
  .content-wrapper { flex-direction: column; }
  .article-main { padding: 25px; }
}
</style>
