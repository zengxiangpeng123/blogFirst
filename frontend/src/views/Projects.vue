<template>
  <div class="projects-page container">
    <header class="page-header">
      <h1>作品集</h1>
      <p>我的一些项目与实践</p>
    </header>
    
    <div class="projects-grid">
      <div v-for="project in projects" :key="project.id" class="project-card hover-card">
        <div class="cover">
          <img :src="project.cover" :alt="project.name" />
        </div>
        <div class="info">
          <h3>{{ project.name }}</h3>
          <p>{{ project.description }}</p>
          <div class="tags">
            <span v-for="tag in project.tags" :key="tag" class="tag">{{ tag }}</span>
          </div>
          <div class="links">
            <a v-if="project.github" :href="project.github" target="_blank">
              <el-button size="small"><el-icon><Link /></el-icon> GitHub</el-button>
            </a>
            <a v-if="project.demo" :href="project.demo" target="_blank">
              <el-button size="small" type="primary"><el-icon><View /></el-icon> 演示</el-button>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { projects } from '@/data/mock'
</script>

<style lang="scss" scoped>
.page-header {
  text-align: center;
  padding: 40px 0;
  margin-bottom: 30px;
  
  h1 {
    color: #2C3E50;
    font-size: 2em;
    margin-bottom: 10px;
  }
  p { color: #95A5A6; }
}

.projects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 25px;
}

.project-card {
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  
  .cover {
    height: 180px;
    overflow: hidden;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s;
    }
  }
  
  &:hover .cover img {
    transform: scale(1.05);
  }
  
  .info {
    padding: 20px;
    
    h3 {
      color: #2C3E50;
      margin-bottom: 10px;
    }
    
    p {
      color: #555;
      font-size: 14px;
      line-height: 1.6;
      margin-bottom: 12px;
    }
    
    .tags {
      margin-bottom: 15px;
    }
    
    .links {
      display: flex;
      gap: 10px;
    }
  }
}
</style>
