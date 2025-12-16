<template>
  <div class="my-projects-page">
    <header class="page-header">
      <div class="header-content">
        <h1>我的作品</h1>
        <p>共 {{ projects.length }} 个作品</p>
      </div>
    </header>

    <div v-loading="loading" class="projects-grid">
      <div v-for="project in projects" :key="project.id" class="project-card">
        <div class="project-cover">
          <img :src="project.cover" :alt="project.name" />
        </div>
        <div class="project-info">
          <h3>{{ project.name }}</h3>
          <p>{{ project.description }}</p>
          <div class="project-tags">
            <span v-for="tag in project.tags" :key="tag" class="tag">{{ tag }}</span>
          </div>
          <div class="project-links">
            <a v-if="project.github" :href="project.github" target="_blank">
              <el-icon><Link /></el-icon> GitHub
            </a>
            <a v-if="project.demo" :href="project.demo" target="_blank">
              <el-icon><View /></el-icon> Demo
            </a>
          </div>
        </div>
      </div>
      <el-empty v-if="!loading && projects.length === 0" description="暂无作品" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Link, View } from '@element-plus/icons-vue'
import { projects as mockProjects } from '@/data/mock'

const projects = ref([])
const loading = ref(false)

onMounted(() => {
  // TODO: 替换为API调用
  projects.value = mockProjects
})
</script>

<style lang="scss" scoped>
.my-projects-page {
  max-width: 100%;
}

.page-header {
  margin-bottom: 24px;
  
  .header-content {
    h1 {
      font-size: 1.5em;
      color: #2C3E50;
      margin-bottom: 8px;
    }
    
    p {
      color: #95A5A6;
      font-size: 14px;
    }
  }
}

.projects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
}

.project-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  transition: transform 0.2s, box-shadow 0.2s;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0,0,0,0.08);
  }
  
  .project-cover {
    height: 180px;
    overflow: hidden;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
  
  .project-info {
    padding: 16px;
    
    h3 {
      font-size: 16px;
      color: #2C3E50;
      margin-bottom: 8px;
    }
    
    p {
      font-size: 14px;
      color: #666;
      margin-bottom: 12px;
    }
  }
  
  .project-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-bottom: 12px;
    
    .tag {
      font-size: 12px;
      padding: 2px 8px;
      background: #F5F7FA;
      color: #666;
      border-radius: 4px;
    }
  }
  
  .project-links {
    display: flex;
    gap: 16px;
    
    a {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: 13px;
      color: #1ABC9C;
      
      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
