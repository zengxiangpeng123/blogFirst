<template>
  <div class="subscribe-page">
    <header class="page-header">
      <div class="header-icon">
        <el-icon :size="32"><Bell /></el-icon>
      </div>
      <div>
        <h1>订阅管理</h1>
        <p>管理你的订阅和通知偏好</p>
      </div>
    </header>

    <!-- 订阅表单 -->
    <div class="subscribe-card">
      <h3>邮件订阅</h3>
      <p>订阅后，新文章发布时会通过邮件通知你</p>
      
      <div class="subscribe-form" v-if="!isSubscribed">
        <el-input 
          v-model="email" 
          placeholder="输入你的邮箱地址" 
          size="large"
          :prefix-icon="Message"
        />
        <el-button type="primary" size="large" @click="subscribe">订阅</el-button>
      </div>
      
      <div class="subscribed-info" v-else>
        <el-icon :size="48" color="#1ABC9C"><CircleCheck /></el-icon>
        <p>你已订阅成功！</p>
        <span>{{ subscribedEmail }}</span>
        <el-button type="danger" text @click="unsubscribe">取消订阅</el-button>
      </div>
    </div>

    <!-- 订阅偏好 -->
    <div class="preferences-card">
      <h3>通知偏好</h3>
      <div class="preference-list">
        <div class="preference-item">
          <div class="pref-info">
            <span class="pref-title">新文章通知</span>
            <span class="pref-desc">有新文章发布时通知我</span>
          </div>
          <el-switch v-model="preferences.newArticle" />
        </div>
        <div class="preference-item">
          <div class="pref-info">
            <span class="pref-title">评论回复</span>
            <span class="pref-desc">我的评论被回复时通知我</span>
          </div>
          <el-switch v-model="preferences.commentReply" />
        </div>
        <div class="preference-item">
          <div class="pref-info">
            <span class="pref-title">周报摘要</span>
            <span class="pref-desc">每周发送本周精选文章</span>
          </div>
          <el-switch v-model="preferences.weeklyDigest" />
        </div>
      </div>
    </div>

    <!-- 分类订阅 -->
    <div class="category-subscribe-card">
      <h3>分类订阅</h3>
      <p>选择你感兴趣的分类，只接收相关内容</p>
      <div class="category-list">
        <el-checkbox-group v-model="subscribedCategories">
          <el-checkbox 
            v-for="cat in categories" 
            :key="cat.name" 
            :label="cat.name"
          >
            {{ cat.name }} ({{ cat.count }})
          </el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Bell, Message, CircleCheck } from '@element-plus/icons-vue'
import { categories } from '@/data/mock'
import { ElMessage } from 'element-plus'

const email = ref('')
const isSubscribed = ref(false)
const subscribedEmail = ref('')

const preferences = reactive({
  newArticle: true,
  commentReply: true,
  weeklyDigest: false
})

const subscribedCategories = ref(['设计沉思', '技术探索'])

const subscribe = () => {
  if (!email.value || !email.value.includes('@')) {
    ElMessage.warning('请输入有效的邮箱地址')
    return
  }
  isSubscribed.value = true
  subscribedEmail.value = email.value
  ElMessage.success('订阅成功！')
}

const unsubscribe = () => {
  isSubscribed.value = false
  subscribedEmail.value = ''
  email.value = ''
  ElMessage.info('已取消订阅')
}
</script>

<style lang="scss" scoped>
.subscribe-page {
  max-width: 600px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, #E8F8F5 0%, #FFF 100%);
  border-radius: 12px;
  
  .header-icon {
    width: 56px;
    height: 56px;
    background: #1ABC9C;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
  }
  
  h1 {
    font-size: 1.4em;
    color: #2C3E50;
    margin-bottom: 4px;
  }
  
  p {
    color: #95A5A6;
    font-size: 14px;
  }
}

.subscribe-card,
.preferences-card,
.category-subscribe-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  
  h3 {
    font-size: 1.1em;
    color: #2C3E50;
    margin-bottom: 8px;
  }
  
  > p {
    color: #95A5A6;
    font-size: 14px;
    margin-bottom: 20px;
  }
}

.subscribe-form {
  display: flex;
  gap: 12px;
  
  .el-input { flex: 1; }
}

.subscribed-info {
  text-align: center;
  padding: 20px;
  
  p {
    font-size: 16px;
    color: #2C3E50;
    margin: 12px 0 4px;
  }
  
  span {
    color: #95A5A6;
    font-size: 14px;
  }
  
  .el-button {
    margin-top: 16px;
  }
}

.preference-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.preference-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #F0F0F0;
  
  &:last-child { border-bottom: none; }
  
  .pref-info {
    .pref-title {
      display: block;
      font-size: 14px;
      color: #2C3E50;
      margin-bottom: 4px;
    }
    
    .pref-desc {
      font-size: 12px;
      color: #95A5A6;
    }
  }
}

.category-list {
  .el-checkbox-group {
    display: flex;
    flex-direction: column;
    gap: 12px;
  }
}
</style>
