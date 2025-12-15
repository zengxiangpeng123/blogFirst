<template>
  <div class="about-page container">
    <div class="about-content">
      <div class="profile-section">
        <el-avatar :src="author.avatar" :size="150" />
        <h1>{{ author.name }}</h1>
        <p class="tagline">设计师 / 开发者 / 终身学习者</p>
      </div>
      
      <section class="intro">
        <h2>关于我</h2>
        <p>你好，我是林清远，一个热爱思考与创造的设计师。</p>
        <p>我相信好的设计源于对生活的观察与思考。在这个博客里，我记录自己在设计、技术、阅读和生活中的所思所想，希望能与志同道合的朋友交流碰撞。</p>
        <p>工作之余，我喜欢阅读、摄影和探索城市中的咖啡馆。每一杯咖啡，每一本书，都是生活中的小确幸。</p>
      </section>
      
      <section class="skills">
        <h2>技能与兴趣</h2>
        <div class="skill-tags">
          <el-tag v-for="skill in skills" :key="skill" effect="plain">{{ skill }}</el-tag>
        </div>
      </section>
      
      <section class="timeline">
        <h2>经历</h2>
        <el-timeline>
          <el-timeline-item timestamp="2022 - 至今" placement="top">
            <h4>高级产品设计师</h4>
            <p>某科技公司，负责核心产品的用户体验设计</p>
          </el-timeline-item>
          <el-timeline-item timestamp="2019 - 2022" placement="top">
            <h4>UI/UX 设计师</h4>
            <p>某互联网公司，参与多个移动端产品设计</p>
          </el-timeline-item>
          <el-timeline-item timestamp="2015 - 2019" placement="top">
            <h4>设计学学士</h4>
            <p>某知名大学，视觉传达设计专业</p>
          </el-timeline-item>
        </el-timeline>
      </section>
      
      <section class="contact">
        <h2>联系我</h2>
        <p>如果你想交流设计、技术或任何有趣的话题，欢迎通过以下方式联系我：</p>
        <div class="contact-links">
          <a :href="`mailto:${author.email}`"><el-icon><Message /></el-icon> {{ author.email }}</a>
          <a :href="author.github" target="_blank"><el-icon><Link /></el-icon> GitHub</a>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { author } from '@/data/mock'

const skills = ['用户体验设计', 'UI设计', 'Vue.js', 'React', '设计系统', '原型设计', '用户研究', '摄影', '阅读']
</script>

<style lang="scss" scoped>
.about-content {
  max-width: 700px;
  margin: 0 auto;
  background: #fff;
  border-radius: 12px;
  padding: 50px;
  box-shadow: 0 2px 15px rgba(0,0,0,0.05);
}

.profile-section {
  text-align: center;
  margin-bottom: 40px;
  
  h1 {
    color: #2C3E50;
    margin: 20px 0 10px;
  }
  
  .tagline {
    color: #95A5A6;
  }
}

section {
  margin-bottom: 35px;
  
  h2 {
    color: #2C3E50;
    font-size: 1.3em;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 2px solid #1ABC9C;
  }
  
  p {
    line-height: 1.9;
    margin-bottom: 10px;
  }
}

.skill-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  
  .el-tag {
    font-size: 14px;
  }
}

.timeline {
  h4 {
    color: #2C3E50;
    margin-bottom: 5px;
  }
  p {
    color: #95A5A6;
    font-size: 14px;
  }
}

.contact-links {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-top: 15px;
  
  a {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #555;
    &:hover { color: #1ABC9C; }
  }
}

@media (max-width: 768px) {
  .about-content { padding: 30px 20px; }
}
</style>
