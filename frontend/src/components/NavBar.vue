<template>
  <header class="navbar">
    <div class="nav-inner">
      <!-- 左侧：汉堡菜单 + Logo -->
      <div class="nav-left">
        <el-icon class="menu-btn" @click="toggleSidebar">
          <Fold v-if="sidebarExpanded" />
          <Expand v-else />
        </el-icon>
        <router-link to="/" class="logo">
          <img src="/images/图标.png" alt="logo" class="logo-icon" />
          <span class="logo-text">静隅思录</span>
        </router-link>
      </div>

      <!-- 中间：搜索框 -->
      <div class="nav-center">
        <div class="search-wrapper">
          <el-input
            v-model="searchText"
            placeholder="搜索文章、标签、分类..."
            class="search-input"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" class="search-btn" @click="handleSearch">搜索</el-button>
        </div>
      </div>

      <!-- 右侧：操作按钮 -->
      <div class="nav-right">
        <el-button type="primary" round class="write-btn">
          <el-icon><EditPen /></el-icon>
          <span>写文章</span>
        </el-button>
        <el-badge :value="3" :max="99" class="msg-badge">
          <el-icon class="nav-icon"><Bell /></el-icon>
        </el-badge>
        <el-dropdown trigger="click">
          <el-avatar :size="36" src="https://api.dicebear.com/7.x/avataaars/svg?seed=Felix" />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item><el-icon><User /></el-icon> 个人中心</el-dropdown-item>
              <el-dropdown-item><el-icon><Setting /></el-icon> 设置</el-dropdown-item>
              <el-dropdown-item divided><el-icon><SwitchButton /></el-icon> 退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { Fold, Expand, EditPen, Bell, User, Setting, SwitchButton } from '@element-plus/icons-vue'

const emit = defineEmits(['toggle-sidebar'])
const props = defineProps({
  sidebarExpanded: { type: Boolean, default: true }
})

const searchText = ref('')

const toggleSidebar = () => {
  emit('toggle-sidebar')
}

const handleSearch = () => {
  if (searchText.value) {
    console.log('搜索:', searchText.value)
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 56px;
  background: #fff;
  border-bottom: 1px solid #E8E8E8;
  z-index: 1000;
}

.nav-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 16px;
  
  .menu-btn {
    font-size: 20px;
    color: #555;
    cursor: pointer;
    padding: 8px;
    border-radius: 6px;
    transition: all 0.2s;
    
    &:hover {
      background: #F5F5F5;
      color: #1ABC9C;
    }
  }
  
  .logo {
    display: flex;
    align-items: center;
    gap: 10px;
    
    .logo-icon {
      width: 32px;
      height: 32px;
      border-radius: 6px;
    }
    
    .logo-text {
      font-size: 1.3em;
      font-weight: 600;
      color: #2C3E50;
      font-family: 'Georgia', serif;
    }
  }
}

.nav-center {
  flex: 1;
  max-width: 500px;
  margin: 0 40px;
  
  .search-wrapper {
    display: flex;
    
    .search-input {
      :deep(.el-input__wrapper) {
        border-radius: 20px 0 0 20px;
        box-shadow: 0 0 0 1px #DCDFE6 inset;
        
        &:hover, &:focus-within {
          box-shadow: 0 0 0 1px #1ABC9C inset;
        }
      }
    }
    
    .search-btn {
      border-radius: 0 20px 20px 0;
      background: #1ABC9C;
      border-color: #1ABC9C;
      padding: 8px 20px;
      
      &:hover {
        background: #16A085;
        border-color: #16A085;
      }
    }
  }
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 20px;
  
  .write-btn {
    background: linear-gradient(135deg, #1ABC9C, #16A085);
    border: none;
    
    span { margin-left: 4px; }
    
    &:hover {
      background: linear-gradient(135deg, #16A085, #138D75);
    }
  }
  
  .nav-icon {
    font-size: 20px;
    color: #555;
    cursor: pointer;
    
    &:hover { color: #1ABC9C; }
  }
  
  .msg-badge {
    cursor: pointer;
    
    :deep(.el-badge__content) {
      background: #E74C3C;
    }
  }
  
  .el-avatar {
    cursor: pointer;
    border: 2px solid transparent;
    transition: border-color 0.2s;
    
    &:hover {
      border-color: #1ABC9C;
    }
  }
}

@media (max-width: 768px) {
  .nav-center { display: none; }
  .write-btn span { display: none; }
  .nav-right { gap: 12px; }
}
</style>
