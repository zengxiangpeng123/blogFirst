<template>
  <div class="category-tabs">
    <div class="tabs-container">
      <router-link 
        to="/archive" 
        class="tab-item"
        :class="{ active: isActive('all') }"
      >
        全部
      </router-link>
      <router-link 
        to="/archive?filter=pinned" 
        class="tab-item"
        :class="{ active: isActive('pinned') }"
      >
        置顶
      </router-link>
      <router-link 
        to="/category/设计" 
        class="tab-item"
        :class="{ active: currentCategory === '设计' }"
      >
        设计
      </router-link>
      <router-link 
        to="/category/技术" 
        class="tab-item"
        :class="{ active: currentCategory === '技术' }"
      >
        技术
      </router-link>
      <router-link 
        to="/category/生活" 
        class="tab-item"
        :class="{ active: currentCategory === '生活' }"
      >
        生活
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const currentCategory = computed(() => route.params.name)

const isActive = (type) => {
  if (type === 'all') {
    return route.path === '/archive' && !route.query.filter
  }
  if (type === 'pinned') {
    return route.path === '/archive' && route.query.filter === 'pinned'
  }
  return false
}
</script>

<style lang="scss" scoped>
.category-tabs {
  background: #fff;
  border-bottom: 1px solid #F0F0F0;
  padding: 0;
  position: sticky;
  top: 56px;
  z-index: 99;
}

.tabs-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  gap: 32px;
  overflow-x: auto;
  
  &::-webkit-scrollbar {
    display: none;
  }
}

.tab-item {
  position: relative;
  padding: 16px 0;
  font-size: 14px;
  color: #666;
  white-space: nowrap;
  transition: color 0.2s;
  
  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 2px;
    background: #1ABC9C;
    transform: scaleX(0);
    transition: transform 0.2s;
  }
  
  &:hover {
    color: #1ABC9C;
  }
  
  &.active {
    color: #1ABC9C;
    font-weight: 500;
    
    &::after {
      transform: scaleX(1);
    }
  }
}
</style>
