<template>
  <div id="app" :class="{ 'sidebar-collapsed': !sidebarExpanded, 'full-page': isFullPage }">
    <template v-if="!isFullPage">
      <NavBar :sidebar-expanded="sidebarExpanded" @toggle-sidebar="toggleSidebar" />
      <SideMenu :expanded="sidebarExpanded" />
      
      <main class="main-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
        <FooterBar />
      </main>
      
      <!-- 移动端遮罩 -->
      <transition name="fade">
        <div v-if="sidebarExpanded && isMobile" class="sidebar-overlay" @click="sidebarExpanded = false"></div>
      </transition>
    </template>
    
    <!-- 全屏页面（如写文章） -->
    <template v-else>
      <router-view />
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import NavBar from './components/NavBar.vue'
import SideMenu from './components/SideMenu.vue'
import FooterBar from './components/FooterBar.vue'

const route = useRoute()
const sidebarExpanded = ref(true)
const isMobile = ref(false)

const isFullPage = computed(() => route.meta.hideLayout)

const toggleSidebar = () => {
  sidebarExpanded.value = !sidebarExpanded.value
}

const checkMobile = () => {
  isMobile.value = window.innerWidth <= 768
  if (isMobile.value) {
    sidebarExpanded.value = false
  }
}

onMounted(() => {
  checkMobile()
  window.addEventListener('resize', checkMobile)
})

onUnmounted(() => {
  window.removeEventListener('resize', checkMobile)
})
</script>

<style lang="scss" scoped>
#app {
  min-height: 100vh;
}

.main-content {
  margin-left: 30%;
  margin-top: 56px;
  min-height: calc(100vh - 56px);
  padding: 24px;
  background: #F5F7FA;
  transition: margin-left 0.3s ease;
}

.sidebar-collapsed .main-content {
  margin-left: 64px;
}

@media (min-width: 1200px) {
  .main-content {
    margin-left: 320px;
  }
}

@media (max-width: 900px) {
  .main-content {
    margin-left: 240px;
  }
}

.sidebar-overlay {
  position: fixed;
  inset: 56px 0 0 0;
  background: rgba(0, 0, 0, 0.4);
  z-index: 998;
}

@media (max-width: 768px) {
  .main-content {
    margin-left: 0 !important;
    padding: 16px;
  }
}
</style>
