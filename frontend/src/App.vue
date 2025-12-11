<template>
  <div id="app">
    <NavBar />
    <main class="main-content">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    <FooterBar />
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
import FooterBar from './components/FooterBar.vue'
</script>

<style lang="scss" scoped>
.main-content {
  min-height: calc(100vh - 180px);
  padding: 30px 0;
}
</style>
