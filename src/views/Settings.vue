<template>
  <div class="settings-page">
    <header class="page-header">
      <h1>设置</h1>
      <p>管理你的账户和偏好设置</p>
    </header>

    <div class="settings-content">
      <!-- 侧边导航 -->
      <div class="settings-nav">
        <div 
          v-for="item in navItems" 
          :key="item.key"
          :class="['nav-item', { active: activeTab === item.key }]"
          @click="activeTab = item.key"
        >
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.label }}</span>
        </div>
      </div>

      <!-- 设置内容 -->
      <div class="settings-panel">
        <!-- 个人资料 -->
        <div v-show="activeTab === 'profile'" class="panel-section">
          <h3>个人资料</h3>
          <el-form :model="profileForm" label-width="100px" label-position="left">
            <el-form-item label="头像">
              <div class="avatar-upload">
                <el-avatar :size="80" :src="profileForm.avatar" />
                <el-button size="small">更换头像</el-button>
              </div>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="profileForm.name" placeholder="输入昵称" />
            </el-form-item>
            <el-form-item label="个人简介">
              <el-input v-model="profileForm.bio" type="textarea" :rows="3" placeholder="介绍一下自己" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="profileForm.email" placeholder="输入邮箱" />
            </el-form-item>
            <el-form-item label="个人网站">
              <el-input v-model="profileForm.website" placeholder="https://" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveProfile">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>

        <!-- 账户安全 -->
        <div v-show="activeTab === 'security'" class="panel-section">
          <h3>账户安全</h3>
          <div class="security-item">
            <div class="item-info">
              <span class="title">修改密码</span>
              <span class="desc">定期修改密码可以保护账户安全</span>
            </div>
            <el-button @click="showPasswordDialog = true">修改</el-button>
          </div>
          <div class="security-item">
            <div class="item-info">
              <span class="title">两步验证</span>
              <span class="desc">开启后登录需要额外验证</span>
            </div>
            <el-switch v-model="twoFactorEnabled" />
          </div>
          <div class="security-item">
            <div class="item-info">
              <span class="title">登录设备</span>
              <span class="desc">查看和管理已登录的设备</span>
            </div>
            <el-button text type="primary">查看</el-button>
          </div>
        </div>

        <!-- 通知设置 -->
        <div v-show="activeTab === 'notification'" class="panel-section">
          <h3>通知设置</h3>
          <div class="notification-item">
            <div class="item-info">
              <span class="title">评论通知</span>
              <span class="desc">有人评论你的文章时通知</span>
            </div>
            <el-switch v-model="notifications.comment" />
          </div>
          <div class="notification-item">
            <div class="item-info">
              <span class="title">点赞通知</span>
              <span class="desc">有人点赞你的文章时通知</span>
            </div>
            <el-switch v-model="notifications.like" />
          </div>
          <div class="notification-item">
            <div class="item-info">
              <span class="title">关注通知</span>
              <span class="desc">有人关注你时通知</span>
            </div>
            <el-switch v-model="notifications.follow" />
          </div>
          <div class="notification-item">
            <div class="item-info">
              <span class="title">系统通知</span>
              <span class="desc">接收系统公告和更新</span>
            </div>
            <el-switch v-model="notifications.system" />
          </div>
        </div>

        <!-- 外观设置 -->
        <div v-show="activeTab === 'appearance'" class="panel-section">
          <h3>外观设置</h3>
          <el-form label-width="100px" label-position="left">
            <el-form-item label="主题模式">
              <el-radio-group v-model="appearance.theme">
                <el-radio label="light">浅色</el-radio>
                <el-radio label="dark">深色</el-radio>
                <el-radio label="auto">跟随系统</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="主题色">
              <div class="color-options">
                <span 
                  v-for="color in themeColors" 
                  :key="color"
                  :class="['color-item', { active: appearance.primaryColor === color }]"
                  :style="{ background: color }"
                  @click="appearance.primaryColor = color"
                ></span>
              </div>
            </el-form-item>
            <el-form-item label="字体大小">
              <el-slider v-model="appearance.fontSize" :min="12" :max="20" :step="1" show-stops />
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="showPasswordDialog" title="修改密码" width="400px">
      <el-form :model="passwordForm" label-width="100px">
        <el-form-item label="当前密码">
          <el-input v-model="passwordForm.current" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="passwordForm.new" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="passwordForm.confirm" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="changePassword">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, markRaw } from 'vue'
import { User, Lock, Bell, Brush } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('profile')
const showPasswordDialog = ref(false)
const twoFactorEnabled = ref(false)

const navItems = [
  { key: 'profile', label: '个人资料', icon: markRaw(User) },
  { key: 'security', label: '账户安全', icon: markRaw(Lock) },
  { key: 'notification', label: '通知设置', icon: markRaw(Bell) },
  { key: 'appearance', label: '外观设置', icon: markRaw(Brush) }
]

const profileForm = reactive({
  avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix',
  name: '林清远',
  bio: '一个热爱思考与写作的设计师',
  email: 'contact@jingyusilu.com',
  website: ''
})

const passwordForm = reactive({
  current: '',
  new: '',
  confirm: ''
})

const notifications = reactive({
  comment: true,
  like: true,
  follow: true,
  system: true
})

const appearance = reactive({
  theme: 'light',
  primaryColor: '#1ABC9C',
  fontSize: 14
})

const themeColors = ['#1ABC9C', '#3498DB', '#9B59B6', '#E74C3C', '#F39C12', '#2C3E50']

const saveProfile = () => {
  ElMessage.success('保存成功')
}

const changePassword = () => {
  if (passwordForm.new !== passwordForm.confirm) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  showPasswordDialog.value = false
  ElMessage.success('密码修改成功')
}
</script>

<style lang="scss" scoped>
.settings-page {
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 24px;
  
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

.settings-content {
  display: flex;
  gap: 24px;
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

.settings-nav {
  width: 200px;
  background: #FAFAFA;
  padding: 16px;
  border-right: 1px solid #F0F0F0;
  
  .nav-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 12px 16px;
    border-radius: 8px;
    color: #666;
    cursor: pointer;
    margin-bottom: 4px;
    transition: all 0.2s;
    
    &:hover {
      background: #F0F0F0;
    }
    
    &.active {
      background: rgba(#1ABC9C, 0.1);
      color: #1ABC9C;
    }
  }
}

.settings-panel {
  flex: 1;
  padding: 24px;
}

.panel-section {
  h3 {
    font-size: 1.1em;
    color: #2C3E50;
    margin-bottom: 24px;
    padding-bottom: 12px;
    border-bottom: 1px solid #F0F0F0;
  }
}

.avatar-upload {
  display: flex;
  align-items: center;
  gap: 16px;
}

.security-item, .notification-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid #F0F0F0;
  
  &:last-child { border-bottom: none; }
  
  .item-info {
    .title {
      display: block;
      font-size: 14px;
      color: #2C3E50;
      margin-bottom: 4px;
    }
    
    .desc {
      font-size: 12px;
      color: #95A5A6;
    }
  }
}

.color-options {
  display: flex;
  gap: 12px;
  
  .color-item {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    cursor: pointer;
    transition: transform 0.2s;
    
    &:hover { transform: scale(1.1); }
    
    &.active {
      box-shadow: 0 0 0 3px #fff, 0 0 0 5px currentColor;
    }
  }
}

@media (max-width: 768px) {
  .settings-content { flex-direction: column; }
  .settings-nav { width: 100%; border-right: none; border-bottom: 1px solid #F0F0F0; }
}
</style>
