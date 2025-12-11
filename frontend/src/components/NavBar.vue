<template>
  <header class="navbar">
    <div class="container nav-inner">
      <router-link to="/" class="logo">静隅思录</router-link>
      
      <nav class="nav-menu" :class="{ active: menuOpen }">
        <router-link to="/">首页</router-link>
        <el-dropdown trigger="hover">
          <span class="nav-link">文章分类 <el-icon><ArrowDown /></el-icon></span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-for="cat in categories" :key="cat.name">
                <router-link :to="`/category/${cat.name}`">{{ cat.name }}</router-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <router-link to="/about">关于我</router-link>
        <router-link to="/projects">作品集</router-link>
        <router-link to="/archive">归档</router-link>
      </nav>
      
      <div class="nav-right">
        <el-input
          v-if="showSearch"
          v-model="searchText"
          placeholder="搜索文章..."
          size="small"
          style="width: 200px"
          @keyup.enter="handleSearch"
        />
        <el-icon class="search-icon" @click="showSearch = !showSearch"><Search /></el-icon>
        <el-icon class="menu-toggle" @click="menuOpen = !menuOpen"><Menu /></el-icon>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { categories } from '@/data/mock'

const menuOpen = ref(false)
const showSearch = ref(false)
const searchText = ref('')

const handleSearch = () => {
  console.log('搜索:', searchText.value)
}
</script>

<style lang="scss" scoped>
.navbar {
  background: #fff;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
}

.logo {
  font-size: 1.4em;
  font-weight: 600;
  color: #2C3E50;
  font-family: 'Georgia', serif;
}

.nav-menu {
  display: flex;
  gap: 30px;
  
  a, .nav-link {
    color: #555;
    font-size: 15px;
    transition: color 0.3s;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 4px;
    
    &:hover, &.router-link-active {
      color: #1ABC9C;
    }
  }
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 15px;
  
  .search-icon {
    font-size: 18px;
    cursor: pointer;
    color: #555;
    &:hover { color: #1ABC9C; }
  }
  
  .menu-toggle {
    display: none;
    font-size: 22px;
    cursor: pointer;
  }
}

@media (max-width: 768px) {
  .nav-menu {
    display: none;
    &.active {
      display: flex;
      flex-direction: column;
      position: absolute;
      top: 60px;
      left: 0;
      right: 0;
      background: #fff;
      padding: 20px;
      box-shadow: 0 5px 10px rgba(0,0,0,0.1);
    }
  }
  .menu-toggle { display: block !important; }
}
</style>
