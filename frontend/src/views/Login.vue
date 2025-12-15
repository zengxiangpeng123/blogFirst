<template>
  <div class="login-page">
    <!-- 左侧装饰区 -->
    <div class="login-left">
      <div class="brand">
        <img src="/images/图标.png" alt="logo" class="logo" />
        <h1>静隅思录</h1>
        <p>在喧嚣中寻一处静隅，记录思考与成长</p>
      </div>
      <div class="decoration">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="circle circle-3"></div>
      </div>
    </div>

    <!-- 右侧登录区 -->
    <div class="login-right">
      <div class="login-box">
        <div class="login-header">
          <h2>{{ isLogin ? '欢迎回来' : '创建账号' }}</h2>
          <p>{{ isLogin ? '登录你的账号，继续探索' : '加入我们，开始你的创作之旅' }}</p>
        </div>

        <el-form 
          ref="formRef" 
          :model="form" 
          :rules="rules" 
          class="login-form"
          @submit.prevent="handleSubmit"
        >
          <!-- 用户名 -->
          <el-form-item prop="username">
            <el-input 
              v-model="form.username" 
              placeholder="用户名"
              size="large"
              :prefix-icon="User"
            />
          </el-form-item>

          <!-- 邮箱（注册时显示） -->
          <el-form-item v-if="!isLogin" prop="email">
            <el-input 
              v-model="form.email" 
              placeholder="邮箱"
              size="large"
              :prefix-icon="Message"
            />
          </el-form-item>

          <!-- 密码 -->
          <el-form-item prop="password">
            <el-input 
              v-model="form.password" 
              type="password"
              placeholder="密码"
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>

          <!-- 确认密码（注册时显示） -->
          <el-form-item v-if="!isLogin" prop="confirmPassword">
            <el-input 
              v-model="form.confirmPassword" 
              type="password"
              placeholder="确认密码"
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>

          <!-- 记住我 & 忘记密码 -->
          <div class="form-options" v-if="isLogin">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <a href="#" class="forgot-link">忘记密码？</a>
          </div>

          <!-- 提交按钮 -->
          <el-form-item>
            <el-button 
              type="primary" 
              size="large" 
              class="submit-btn"
              :loading="loading"
              @click="handleSubmit"
            >
              {{ isLogin ? '登 录' : '注 册' }}
            </el-button>
          </el-form-item>
        </el-form>

        <!-- 切换登录/注册 -->
        <div class="switch-mode">
          <span>{{ isLogin ? '还没有账号？' : '已有账号？' }}</span>
          <a @click="toggleMode">{{ isLogin ? '立即注册' : '立即登录' }}</a>
        </div>

        <!-- 第三方登录 -->
        <div class="third-party">
          <div class="divider">
            <span>其他方式</span>
          </div>
          <div class="social-icons">
            <div class="social-icon" title="微信登录">
              <svg viewBox="0 0 24 24" width="24" height="24">
                <path fill="currentColor" d="M9.5,4C5.36,4 2,6.69 2,10C2,11.89 3.08,13.56 4.78,14.66L4,17L6.5,15.5C7.39,15.81 8.37,16 9.41,16C9.15,15.37 9,14.7 9,14C9,10.69 12.13,8 16,8C16.19,8 16.38,8 16.56,8.03C15.54,5.69 12.78,4 9.5,4M6.5,6.5A1,1 0 0,1 7.5,7.5A1,1 0 0,1 6.5,8.5A1,1 0 0,1 5.5,7.5A1,1 0 0,1 6.5,6.5M11.5,6.5A1,1 0 0,1 12.5,7.5A1,1 0 0,1 11.5,8.5A1,1 0 0,1 10.5,7.5A1,1 0 0,1 11.5,6.5M16,9C12.69,9 10,11.24 10,14C10,16.76 12.69,19 16,19C16.67,19 17.31,18.92 17.91,18.75L20,20L19.38,18.13C20.95,17.22 22,15.71 22,14C22,11.24 19.31,9 16,9M14,11.5A1,1 0 0,1 15,12.5A1,1 0 0,1 14,13.5A1,1 0 0,1 13,12.5A1,1 0 0,1 14,11.5M18,11.5A1,1 0 0,1 19,12.5A1,1 0 0,1 18,13.5A1,1 0 0,1 17,12.5A1,1 0 0,1 18,11.5Z"/>
              </svg>
            </div>
            <div class="social-icon" title="GitHub登录">
              <svg viewBox="0 0 24 24" width="24" height="24">
                <path fill="currentColor" d="M12,2A10,10 0 0,0 2,12C2,16.42 4.87,20.17 8.84,21.5C9.34,21.58 9.5,21.27 9.5,21C9.5,20.77 9.5,20.14 9.5,19.31C6.73,19.91 6.14,17.97 6.14,17.97C5.68,16.81 5.03,16.5 5.03,16.5C4.12,15.88 5.1,15.9 5.1,15.9C6.1,15.97 6.63,16.93 6.63,16.93C7.5,18.45 8.97,18 9.54,17.76C9.63,17.11 9.89,16.67 10.17,16.42C7.95,16.17 5.62,15.31 5.62,11.5C5.62,10.39 6,9.5 6.65,8.79C6.55,8.54 6.2,7.5 6.75,6.15C6.75,6.15 7.59,5.88 9.5,7.17C10.29,6.95 11.15,6.84 12,6.84C12.85,6.84 13.71,6.95 14.5,7.17C16.41,5.88 17.25,6.15 17.25,6.15C17.8,7.5 17.45,8.54 17.35,8.79C18,9.5 18.38,10.39 18.38,11.5C18.38,15.32 16.04,16.16 13.81,16.41C14.17,16.72 14.5,17.33 14.5,18.26C14.5,19.6 14.5,20.68 14.5,21C14.5,21.27 14.66,21.59 15.17,21.5C19.14,20.16 22,16.42 22,12A10,10 0 0,0 12,2Z"/>
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Message } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const isLogin = ref(true)
const loading = ref(false)
const rememberMe = ref(false)

const form = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (!isLogin.value && value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const toggleMode = () => {
  isLogin.value = !isLogin.value
  formRef.value?.resetFields()
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      
      // 模拟登录请求
      setTimeout(() => {
        loading.value = false
        if (isLogin.value) {
          ElMessage.success('登录成功')
          router.push('/')
        } else {
          ElMessage.success('注册成功，请登录')
          isLogin.value = true
        }
      }, 1000)
    }
  })
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #1ABC9C 0%, #16A085 50%, #138D75 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  padding: 40px;
  
  .brand {
    text-align: center;
    color: #fff;
    z-index: 1;
    
    .logo {
      width: 80px;
      height: 80px;
      border-radius: 16px;
      margin-bottom: 24px;
      box-shadow: 0 8px 32px rgba(0,0,0,0.2);
    }
    
    h1 {
      font-size: 2.5em;
      font-weight: 600;
      margin-bottom: 16px;
      font-family: 'Georgia', serif;
    }
    
    p {
      font-size: 1.1em;
      opacity: 0.9;
      max-width: 300px;
    }
  }
  
  .decoration {
    position: absolute;
    inset: 0;
    
    .circle {
      position: absolute;
      border-radius: 50%;
      background: rgba(255,255,255,0.1);
    }
    
    .circle-1 {
      width: 300px;
      height: 300px;
      top: -100px;
      left: -100px;
    }
    
    .circle-2 {
      width: 200px;
      height: 200px;
      bottom: 10%;
      right: -50px;
    }
    
    .circle-3 {
      width: 150px;
      height: 150px;
      bottom: 30%;
      left: 10%;
    }
  }
}

.login-right {
  width: 480px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  background: #fff;
}

.login-box {
  width: 100%;
  max-width: 360px;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
  
  h2 {
    font-size: 1.8em;
    color: #2C3E50;
    margin-bottom: 8px;
  }
  
  p {
    color: #95A5A6;
    font-size: 14px;
  }
}

.login-form {
  .el-form-item {
    margin-bottom: 20px;
  }
  
  :deep(.el-input__wrapper) {
    padding: 4px 15px;
    border-radius: 8px;
    box-shadow: 0 0 0 1px #E0E0E0 inset;
    
    &:hover {
      box-shadow: 0 0 0 1px #1ABC9C inset;
    }
    
    &.is-focus {
      box-shadow: 0 0 0 2px rgba(#1ABC9C, 0.3) inset;
    }
  }
  
  :deep(.el-input__inner) {
    height: 44px;
  }
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  
  .forgot-link {
    font-size: 13px;
    color: #1ABC9C;
    
    &:hover {
      text-decoration: underline;
    }
  }
}

.submit-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  border-radius: 8px;
  background: linear-gradient(135deg, #1ABC9C, #16A085);
  border: none;
  
  &:hover {
    background: linear-gradient(135deg, #16A085, #138D75);
  }
}

.switch-mode {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: #666;
  
  a {
    color: #1ABC9C;
    font-weight: 500;
    cursor: pointer;
    margin-left: 4px;
    
    &:hover {
      text-decoration: underline;
    }
  }
}

.third-party {
  margin-top: 32px;
  
  .divider {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    
    &::before, &::after {
      content: '';
      flex: 1;
      height: 1px;
      background: #E8E8E8;
    }
    
    span {
      padding: 0 16px;
      font-size: 12px;
      color: #95A5A6;
    }
  }
  
  .social-icons {
    display: flex;
    justify-content: center;
    gap: 20px;
  }
  
  .social-icon {
    width: 44px;
    height: 44px;
    border-radius: 50%;
    border: 1px solid #E8E8E8;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: #666;
    transition: all 0.2s;
    
    &:hover {
      border-color: #1ABC9C;
      color: #1ABC9C;
      transform: translateY(-2px);
    }
  }
}

@media (max-width: 900px) {
  .login-left {
    display: none;
  }
  
  .login-right {
    width: 100%;
  }
}
</style>
