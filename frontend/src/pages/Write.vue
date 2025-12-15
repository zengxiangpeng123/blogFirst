<template>
  <div class="write-page">
    <header class="write-header">
      <div class="header-left">
        <el-button text @click="goBack">
          <el-icon><ArrowLeft /></el-icon>
          返回
        </el-button>
        <span class="save-status">{{ saveStatus }}</span>
      </div>
      <div class="header-right">
        <el-button @click="saveDraft">保存草稿</el-button>
        <el-button type="primary" @click="showPublishDialog = true">发布文章</el-button>
      </div>
    </header>

    <div class="write-content">
      <input 
        v-model="articleForm.title" 
        class="title-input" 
        placeholder="请输入文章标题..."
        maxlength="100"
      />
      <div class="editor-container">
        <el-input
          v-model="articleForm.content"
          type="textarea"
          :rows="20"
          placeholder="开始写作..."
          class="content-editor"
        />
      </div>
    </div>

    <el-dialog v-model="showPublishDialog" title="发布设置" width="500px">
      <el-form :model="articleForm" label-width="80px">
        <el-form-item label="分类">
          <el-select v-model="articleForm.categoryId" placeholder="选择分类" style="width: 100%">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="articleForm.tags" multiple filterable allow-create placeholder="添加标签（用逗号分隔）" style="width: 100%">
            <el-option v-for="tag in tagOptions" :key="tag" :label="tag" :value="tag" />
          </el-select>
          <div style="font-size: 12px; color: #95A5A6; margin-top: 4px;">
            已选标签：{{ articleForm.tags.join(', ') || '无' }}
          </div>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="articleForm.summary" type="textarea" :rows="3" placeholder="文章摘要" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="设置">
          <el-checkbox v-model="articleForm.isPinned">置顶文章</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPublishDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="publishArticle">确认发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft } from '@element-plus/icons-vue'
import { addArticle } from '@/api/article'
import { ElMessage } from 'element-plus'

const router = useRouter()
const showPublishDialog = ref(false)
const saveStatus = ref('已保存')
const loading = ref(false)

const articleForm = reactive({
  title: '',
  content: '',
  categoryId: null,
  tags: [],
  summary: '',
  isPinned: 0,
  isPublished: 1
})

// 分类列表（实际应该从后端获取）
const categories = [
  { id: 1, name: '设计沉思' },
  { id: 2, name: '技术探索' },
  { id: 3, name: '生活随笔' },
  { id: 4, name: '读书笔记' },
  { id: 5, name: '哲学思辨' }
]

const tagOptions = ['设计', '技术', '前端', 'Vue', 'React', '用户体验', '生活', '读书', '思考']

const goBack = () => router.back()

const saveDraft = () => {
  saveStatus.value = '保存中...'
  setTimeout(() => {
    saveStatus.value = '已保存'
    ElMessage.success('草稿已保存')
  }, 500)
}

const publishArticle = async () => {
  // 表单验证
  if (!articleForm.title) {
    ElMessage.warning('请输入文章标题')
    return
  }
  if (!articleForm.content) {
    ElMessage.warning('请输入文章内容')
    return
  }
  if (!articleForm.categoryId) {
    ElMessage.warning('请选择文章分类')
    return
  }
  
  // 获取当前用户ID
  const userInfoStr = localStorage.getItem('userInfo')
  if (!userInfoStr) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  const userInfo = JSON.parse(userInfoStr)
  
  loading.value = true
  try {
    // 将标签数组转换为逗号分隔的字符串
    const tagsString = articleForm.tags.join(',')
    
    await addArticle({
      userId: userInfo.id,
      categoryId: articleForm.categoryId,
      title: articleForm.title,
      content: articleForm.content,
      summary: articleForm.summary || articleForm.content.substring(0, 200),
      tags: tagsString,
      isPinned: articleForm.isPinned ? 1 : 0,
      isPublished: 1
    })
    
    showPublishDialog.value = false
    ElMessage.success('文章发布成功！')
    setTimeout(() => {
      router.push('/')
    }, 1000)
  } catch (error) {
    console.error('发布失败:', error)
    ElMessage.error(error.message || '发布失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.write-page {
  min-height: 100vh;
  background: #F5F7FA;
  display: flex;
  flex-direction: column;
}

.write-header {
  position: sticky;
  top: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 24px;
  background: #fff;
  border-bottom: 1px solid #E8E8E8;
  z-index: 100;
  
  .header-left {
    display: flex;
    align-items: center;
    gap: 16px;
    
    .save-status {
      font-size: 13px;
      color: #95A5A6;
    }
  }
  
  .header-right {
    display: flex;
    gap: 12px;
  }
}

.write-content {
  flex: 1;
  max-width: 900px;
  width: 100%;
  margin: 0 auto;
  padding: 24px;
}

.title-input {
  width: 100%;
  font-size: 2em;
  font-weight: 600;
  color: #2C3E50;
  border: none;
  outline: none;
  padding: 16px 0;
  margin-bottom: 16px;
  background: transparent;
  
  &::placeholder {
    color: #BDC3C7;
  }
}

.editor-container {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  padding: 20px;
}

.content-editor {
  :deep(.el-textarea__inner) {
    border: none;
    box-shadow: none;
    font-size: 16px;
    line-height: 1.8;
  }
}
</style>
