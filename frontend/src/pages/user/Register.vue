<template>
  <div class="auth-page">
    <!-- 左侧装饰区 -->
    <div class="auth-left">
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

    <!-- 右侧注册区 -->
    <div class="auth-right">
      <div class="auth-box">
        <div class="auth-header">
          <h2>创建账号</h2>
          <p>加入我们，开始你的创作之旅</p>
        </div>

        <el-form 
          ref="formRef" 
          :model="form" 
          :rules="rules" 
          class="auth-form"
          @submit.prevent="handleSubmit"
        >
          <el-form-item prop="username">
            <el-input 
              v-model="form.username" 
              placeholder="用户名"
              size="large"
              :prefix-icon="User"
            />
          </el-form-item>

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

          <el-form-item prop="checkPassword">
            <el-input 
              v-model="form.checkPassword" 
              type="password"
              placeholder="确认密码"
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>

          <el-form-item>
            <el-button 
              type="primary" 
              size="large" 
              class="submit-btn"
              :loading="loading"
              @click="handleSubmit"
            >
              注 册
            </el-button>
          </el-form-item>
        </el-form>

        <div class="switch-mode">
          <span>已有账号？</span>
          <router-link to="/login">立即登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { register } from '@/api/user'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  checkPassword: ''
})

const validateCheckPassword = (rule, value, callback) => {
  if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 4, max: 20, message: '用户名长度为4-20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, message: '密码至少8个字符', trigger: 'blur' }
  ],
  checkPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateCheckPassword, trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await register({
          username: form.username,
          password: form.password,
          checkPassword: form.checkPassword
        })
        console.log('注册响应:', res)
        ElMessage.success('注册成功，请登录')
        setTimeout(() => {
          router.push('/login')
        }, 1000)
      } catch (error) {
        console.error('注册错误:', error)
        ElMessage.error(error.message || error.response?.data?.message || '注册失败，请重试')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style lang="scss" scoped src="./auth.scss"></style>
