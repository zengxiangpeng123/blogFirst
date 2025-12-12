<template>
  <div class="write-page">
    <!-- 顶部工具栏 -->
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

    <!-- 编辑区域 -->
    <div class="write-content">
      <!-- 标题输入 -->
      <input 
        v-model="articleForm.title" 
        class="title-input" 
        placeholder="请输入文章标题..."
        maxlength="100"
      />

      <!-- 富文本编辑器 -->
      <div class="editor-container">
        <Toolbar
          :editor="editorRef"
          :defaultConfig="toolbarConfig"
          mode="default"
          class="editor-toolbar"
        />
        <Editor
          v-model="articleForm.content"
          :defaultConfig="editorConfig"
          mode="default"
          class="editor-content"
          @onCreated="handleCreated"
        />
      </div>
    </div>

    <!-- 发布设置对话框 -->
    <el-dialog v-model="showPublishDialog" title="发布设置" width="500px">
      <el-form :model="articleForm" label-width="80px">
        <el-form-item label="分类">
          <el-select v-model="articleForm.category" placeholder="选择分类" style="width: 100%">
            <el-option 
              v-for="cat in categories" 
              :key="cat.name" 
              :label="cat.name" 
              :value="cat.name" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select 
            v-model="articleForm.tags" 
            multiple 
            filterable 
            allow-create 
            placeholder="添加标签"
            style="width: 100%"
          >
            <el-option v-for="tag in tagOptions" :key="tag" :label="tag" :value="tag" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图">
          <el-upload
            class="cover-uploader"
            action="#"
            :show-file-list="false"
            :auto-upload="false"
            @change="handleCoverChange"
          >
            <img v-if="articleForm.cover" :src="articleForm.cover" class="cover-preview" />
            <div v-else class="cover-placeholder">
              <el-icon :size="32"><Plus /></el-icon>
              <span>上传封面</span>
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input 
            v-model="articleForm.summary" 
            type="textarea" 
            :rows="3" 
            placeholder="文章摘要，不填则自动截取"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="设置">
          <el-checkbox v-model="articleForm.pinned">置顶文章</el-checkbox>
          <el-checkbox v-model="articleForm.allowComment">允许评论</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPublishDialog = false">取消</el-button>
        <el-button type="primary" @click="publishArticle">确认发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, shallowRef, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, Plus } from '@element-plus/icons-vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'
import { categories } from '@/data/mock'
import { ElMessage } from 'element-plus'

const router = useRouter()
const editorRef = shallowRef()
const showPublishDialog = ref(false)
const saveStatus = ref('已保存')

const articleForm = reactive({
  title: '',
  content: '',
  category: '',
  tags: [],
  cover: '',
  summary: '',
  pinned: false,
  allowComment: true
})

const tagOptions = ['设计', '技术', '前端', 'Vue', 'React', '用户体验', '生活', '读书', '思考']

const toolbarConfig = {
  excludeKeys: ['fullScreen']
}

const editorConfig = {
  placeholder: '开始写作...',
  MENU_CONF: {
    uploadImage: {
      customUpload: async (file, insertFn) => {
        // 模拟上传，实际项目中替换为真实上传逻辑
        const url = URL.createObjectURL(file)
        insertFn(url, file.name, url)
      }
    }
  }
}

const handleCreated = (editor) => {
  editorRef.value = editor
}

const goBack = () => {
  router.back()
}

const saveDraft = () => {
  saveStatus.value = '保存中...'
  setTimeout(() => {
    saveStatus.value = '已保存'
    ElMessage.success('草稿已保存')
  }, 500)
}

const handleCoverChange = (file) => {
  articleForm.cover = URL.createObjectURL(file.raw)
}

const publishArticle = () => {
  if (!articleForm.title) {
    ElMessage.warning('请输入文章标题')
    return
  }
  if (!articleForm.content) {
    ElMessage.warning('请输入文章内容')
    return
  }
  if (!articleForm.category) {
    ElMessage.warning('请选择文章分类')
    return
  }
  
  showPublishDialog.value = false
  ElMessage.success('文章发布成功！')
  router.push('/')
}

onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor) editor.destroy()
})
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
}

.editor-toolbar {
  border-bottom: 1px solid #E8E8E8;
}

.editor-content {
  min-height: 500px;
  
  :deep(.w-e-text-container) {
    min-height: 500px;
  }
  
  :deep(.w-e-text-placeholder) {
    font-style: normal;
    color: #BDC3C7;
  }
}

.cover-uploader {
  .cover-preview {
    width: 200px;
    height: 120px;
    object-fit: cover;
    border-radius: 6px;
  }
  
  .cover-placeholder {
    width: 200px;
    height: 120px;
    border: 2px dashed #DCDFE6;
    border-radius: 6px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #95A5A6;
    cursor: pointer;
    transition: border-color 0.2s;
    
    &:hover {
      border-color: #1ABC9C;
      color: #1ABC9C;
    }
    
    span {
      margin-top: 8px;
      font-size: 13px;
    }
  }
}
</style>
