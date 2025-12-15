<template>
  <aside class="side-menu" :class="{ collapsed: !expanded }">
    <nav class="menu-nav">
      <!-- 主导航 -->
      <div class="menu-section">
        <router-link to="/" class="menu-item" exact-active-class="active">
          <el-icon><HomeFilled /></el-icon>
          <span v-show="expanded">首页</span>
        </router-link>
        <router-link to="/archive" class="menu-item" exact-active-class="active">
          <el-icon><Document /></el-icon>
          <span v-show="expanded">全部文章</span>
        </router-link>
        <router-link to="/profile" class="menu-item" exact-active-class="active">
          <el-icon><Edit /></el-icon>
          <span v-show="expanded">我的文章</span>
        </router-link>
        <router-link to="/projects" class="menu-item" exact-active-class="active">
          <el-icon><Folder /></el-icon>
          <span v-show="expanded">我的作品</span>
        </router-link>
        <router-link to="/about" class="menu-item" exact-active-class="active">
          <el-icon><UserFilled /></el-icon>
          <span v-show="expanded">关于我</span>
        </router-link>
      </div>

      <!-- 分割线 -->
      <div class="menu-divider" v-show="expanded"></div>

      <!-- 分类导航 -->
      <div class="menu-section" v-show="expanded">
        <div class="section-title">文章分类</div>
        <router-link
          v-for="cat in categories"
          :key="cat.name"
          :to="`/category/${cat.name}`"
          class="menu-item category-item"
          exact-active-class="active"
        >
          <span class="cat-dot" :style="{ background: getCategoryColor(cat.name) }"></span>
          <span>{{ cat.name }}</span>
          <span class="cat-count">{{ cat.count }}</span>
        </router-link>
      </div>

      <!-- 分割线 -->
      <div class="menu-divider" v-show="expanded"></div>

      <!-- 快捷操作 -->
      <div class="menu-section">
        <router-link to="/favorites" class="menu-item" exact-active-class="active">
          <el-icon><Star /></el-icon>
          <span v-show="expanded">收藏</span>
        </router-link>
        <router-link to="/history" class="menu-item" exact-active-class="active">
          <el-icon><Clock /></el-icon>
          <span v-show="expanded">历史</span>
        </router-link>
        <router-link to="/subscribe" class="menu-item" exact-active-class="active">
          <el-icon><Bell /></el-icon>
          <span v-show="expanded">订阅</span>
        </router-link>
      </div>
    </nav>

    <!-- 底部 -->
    <div class="menu-footer" v-show="expanded">
      <div class="footer-links">
        <a href="#">关于</a>
        <a href="#">隐私</a>
        <a href="#">条款</a>
      </div>
      <p>© 2024 静隅思录</p>
    </div>
  </aside>
</template>

<script setup>
import { categories } from '@/data/mock'
import { HomeFilled, Document, Folder, UserFilled, Star, Clock, Bell, Edit } from '@element-plus/icons-vue'

defineProps({
  expanded: { type: Boolean, default: true }
})

const categoryColors = {
  '设计沉思': '#9B59B6',
  '技术探索': '#3498DB',
  '生活随笔': '#1ABC9C',
  '读书笔记': '#E67E22',
  '哲学思辨': '#E74C3C'
}

const getCategoryColor = (name) => categoryColors[name] || '#95A5A6'
</script>

<style lang="scss" scoped>
.side-menu {
  position: fixed;
  top: 56px;
  left: 0;
  bottom: 0;
  width: 30%;
  max-width: 320px;
  min-width: 240px;
  background: #fff;
  border-right: 1px solid #E8E8E8;
  display: flex;
  flex-direction: column;
  transition: width 0.3s ease;
  z-index: 999;
  overflow-y: auto;
  
  &.collapsed {
    width: 64px;
    min-width: 64px;
    
    .menu-item {
      justify-content: center;
      padding: 12px;
    }
  }
}

.menu-nav {
  flex: 1;
  padding: 16px 12px;
}

.menu-section {
  margin-bottom: 8px;
}

.section-title {
  font-size: 12px;
  color: #95A5A6;
  padding: 8px 12px;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  border-radius: 8px;
  color: #555;
  font-size: 14px;
  transition: all 0.2s;
  margin-bottom: 4px;
  
  .el-icon {
    font-size: 18px;
    flex-shrink: 0;
  }
  
  &:hover {
    background: #F5F7FA;
    color: #1ABC9C;
  }
  
  &.active {
    background: rgba(#1ABC9C, 0.1);
    color: #1ABC9C;
    font-weight: 500;
  }
}

.category-item {
  .cat-dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    flex-shrink: 0;
  }
  
  .cat-count {
    margin-left: auto;
    font-size: 12px;
    color: #95A5A6;
    background: #F0F0F0;
    padding: 2px 8px;
    border-radius: 10px;
  }
}

.menu-divider {
  height: 1px;
  background: #E8E8E8;
  margin: 12px 0;
}

.menu-footer {
  padding: 16px;
  border-top: 1px solid #E8E8E8;
  
  .footer-links {
    display: flex;
    gap: 16px;
    margin-bottom: 8px;
    
    a {
      font-size: 12px;
      color: #95A5A6;
      
      &:hover { color: #1ABC9C; }
    }
  }
  
  p {
    font-size: 11px;
    color: #BDC3C7;
  }
}

@media (max-width: 768px) {
  .side-menu {
    transform: translateX(-100%);
    
    &:not(.collapsed) {
      transform: translateX(0);
      box-shadow: 5px 0 20px rgba(0,0,0,0.1);
    }
  }
}
</style>
