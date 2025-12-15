<template>
  <aside class="sidebar">
    <!-- 作者信息卡片 -->
    <div class="widget author-card">
      <el-avatar :src="author.avatar" :size="80" />
      <h3>{{ author.name }}</h3>
      <p>{{ author.bio }}</p>
      <router-link to="/about" class="about-link">了解更多 →</router-link>
    </div>
    
    <!-- 分类目录 -->
    <div class="widget">
      <h4 class="widget-title">分类目录</h4>
      <div class="category-menu">
        <div v-for="cat in categories" :key="cat.name" class="category-group">
          <div class="category-parent" @click="toggleCategory(cat.name)">
            <el-icon class="collapse-icon" :class="{ collapsed: !expandedCategories[cat.name] }">
              <ArrowDown />
            </el-icon>
            <router-link :to="`/category/${cat.name}`" @click.stop class="parent-link">
              {{ cat.name }} <span>({{ cat.count }})</span>
            </router-link>
          </div>
          <transition name="collapse">
            <div v-show="expandedCategories[cat.name]" class="category-children">
              <router-link 
                v-for="child in cat.children" 
                :key="child.name"
                :to="`/category/${cat.name}/${child.name}`"
                class="category-child"
              >
                {{ child.name }} <span>({{ child.count }})</span>
              </router-link>
            </div>
          </transition>
        </div>
      </div>
    </div>
    
    <!-- 热门文章 -->
    <div class="widget">
      <h4 class="widget-title">热门文章</h4>
      <ul class="hot-list">
        <li v-for="(item, index) in hotArticles" :key="item.id">
          <span class="rank">{{ index + 1 }}</span>
          <router-link :to="`/article/${item.id}`">{{ item.title }}</router-link>
        </li>
      </ul>
    </div>
    
    <!-- 订阅 -->
    <div class="widget subscribe">
      <h4 class="widget-title">订阅更新</h4>
      <p>永不错过精彩内容</p>
      <el-input v-model="email" placeholder="输入邮箱地址" size="small" />
      <el-button type="primary" size="small" style="margin-top: 10px; width: 100%">订阅</el-button>
    </div>
  </aside>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ArrowDown } from '@element-plus/icons-vue'
import { author, categories, hotArticles } from '@/data/mock'

const email = ref('')

// 记录每个一级分类的展开状态
const expandedCategories = reactive({})

const toggleCategory = (name) => {
  expandedCategories[name] = !expandedCategories[name]
}
</script>

<style lang="scss" scoped>
.sidebar {
  width: 300px;
  flex-shrink: 0;
}

.widget {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.widget-title {
  color: #2C3E50;
  font-size: 16px;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #1ABC9C;
}

.author-card {
  text-align: center;
  
  h3 {
    color: #2C3E50;
    margin: 15px 0 10px;
  }
  
  p {
    color: #95A5A6;
    font-size: 14px;
    line-height: 1.6;
  }
  
  .about-link {
    display: inline-block;
    margin-top: 15px;
    color: #1ABC9C;
    font-size: 14px;
  }
}

.category-menu {
  .category-group {
    margin-bottom: 8px;
  }
  
  .category-parent {
    display: flex;
    align-items: center;
    padding: 8px 0;
    cursor: pointer;
    
    .collapse-icon {
      transition: transform 0.3s ease;
      color: #95A5A6;
      margin-right: 8px;
      font-size: 12px;
      
      &.collapsed {
        transform: rotate(-90deg);
      }
    }
    
    .parent-link {
      font-size: 14px;
      color: #2C3E50;
      font-weight: 500;
      
      span { 
        color: #95A5A6; 
        font-weight: normal;
      }
      
      &:hover {
        color: #1ABC9C;
      }
    }
    
    &:hover .collapse-icon {
      color: #1ABC9C;
    }
  }
  
  .category-children {
    padding-left: 20px;
    overflow: hidden;
  }
  
  .category-child {
    display: block;
    padding: 6px 12px;
    font-size: 13px;
    color: #666;
    border-left: 2px solid #ECF0F1;
    margin-bottom: 2px;
    
    span { color: #95A5A6; }
    
    &:hover {
      color: #1ABC9C;
      border-left-color: #1ABC9C;
      background: rgba(#1ABC9C, 0.05);
    }
  }
}

.collapse-enter-active,
.collapse-leave-active {
  transition: all 0.3s ease;
}

.collapse-enter-from,
.collapse-leave-to {
  opacity: 0;
  max-height: 0;
}

.collapse-enter-to,
.collapse-leave-from {
  opacity: 1;
  max-height: 300px;
}

.hot-list {
  list-style: none;
  
  li {
    display: flex;
    align-items: flex-start;
    gap: 10px;
    margin-bottom: 12px;
    
    .rank {
      width: 20px;
      height: 20px;
      background: #ECF0F1;
      color: #95A5A6;
      border-radius: 4px;
      font-size: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }
    
    &:nth-child(1) .rank { background: #E74C3C; color: #fff; }
    &:nth-child(2) .rank { background: #F39C12; color: #fff; }
    &:nth-child(3) .rank { background: #1ABC9C; color: #fff; }
    
    a {
      font-size: 14px;
      color: #555;
      line-height: 1.4;
      &:hover { color: #1ABC9C; }
    }
  }
}

.subscribe {
  p {
    color: #95A5A6;
    font-size: 13px;
    margin-bottom: 10px;
  }
}

@media (max-width: 992px) {
  .sidebar { width: 100%; }
}
</style>
