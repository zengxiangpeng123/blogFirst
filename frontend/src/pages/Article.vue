<template>
  <div class="article-page container">
    <div class="content-wrapper">
      <article class="article-main">
        <!-- 文章头部 -->
        <header class="article-header">
          <span class="category">{{ article.category }}</span>
          <h1>{{ article.title }}</h1>
          <div class="meta">
            <span><el-icon><User /></el-icon> {{ author.name }}</span>
            <span><el-icon><Calendar /></el-icon> {{ article.date }}</span>
            <span><el-icon><Clock /></el-icon> {{ article.readTime }} 分钟阅读</span>
            <span><el-icon><View /></el-icon> {{ article.views }} 次浏览</span>
          </div>
        </header>
        
        <!-- 文章正文 -->
        <div class="article-content" v-html="renderedContent"></div>
        
        <!-- 文章尾部 -->
        <footer class="article-footer">
          <div class="tags">
            <el-icon><PriceTag /></el-icon>
            <span v-for="tag in article.tags" :key="tag" class="tag">{{ tag }}</span>
          </div>
          
          <div class="share">
            <span>分享到：</span>
            <el-button size="small" circle><el-icon><Share /></el-icon></el-button>
          </div>
          
          <div class="copyright-notice">
            <el-icon><Warning /></el-icon>
            本文采用 CC BY-NC-SA 4.0 协议，转载请注明出处。
          </div>
          
          <div class="nav-links">
            <router-link to="/" class="prev">← 上一篇</router-link>
            <router-link to="/" class="next">下一篇 →</router-link>
          </div>
        </footer>
      </article>
      
      <!-- 目录侧边栏 -->
      <aside class="toc-sidebar">
        <div class="toc-widget">
          <h4>目录</h4>
          <ul>
            <li><a href="#什么是留白">什么是留白</a></li>
            <li><a href="#留白的价值">留白的价值</a></li>
            <li><a href="#如何运用留白">如何运用留白</a></li>
            <li><a href="#结语">结语</a></li>
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { articleDetail as article, author } from '@/data/mock'

// 简单的 Markdown 渲染
const renderedContent = computed(() => {
  return article.content
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

.toc-sidebar {
  width: 220px;
  flex-shrink: 0;
  
  .toc-widget {
    position: sticky;
    top: 80px;
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    
    h4 {
      color: #2C3E50;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 2px solid #1ABC9C;
    }
    
    ul {
      list-style: none;
      
      li {
        margin-bottom: 10px;
        
        a {
          color: #555;
          font-size: 14px;
          &:hover { color: #1ABC9C; }
        }
      }
    }
  }
}

@media (max-width: 992px) {
  .content-wrapper { flex-direction: column; }
  .toc-sidebar { width: 100%; }
  .article-main { padding: 25px; }
}
</style>
