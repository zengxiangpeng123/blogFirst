<template>
  <div class="write-page">
    <!-- 顶部导航 -->
    <header class="header">
      <div class="header-left">
        <router-link to="/" class="logo">
          <img src="/images/图标.png" alt="logo" class="logo-icon" />
          <span class="logo-text">静隅思录</span>
        </router-link>
        <span class="back-btn" @click="handleBack">‹</span>
        <el-dropdown trigger="click">
          <div class="article-type">
            <span>{{ articleTypeText }}</span>
            <span class="arrow">▼</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="articleForm.isPublished = 1">发布文章</el-dropdown-item>
              <el-dropdown-item @click="articleForm.isPublished = 2">仅自己可见</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="header-right">
        <el-avatar :size="32" src="/images/图标.png" />
      </div>
    </header>

    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="tool-group">
        <el-tooltip content="撤销" placement="bottom">
          <button class="tool-btn" @click="undo">↩</button>
        </el-tooltip>
        <span class="tool-label">撤消</span>
        <el-tooltip content="重做" placement="bottom">
          <button class="tool-btn" @click="redo">↪</button>
        </el-tooltip>
        <span class="tool-label">重做</span>
      </div>
      <div class="tool-divider"></div>
      <div class="tool-group">
        <el-dropdown trigger="click">
          <button class="tool-btn">H</button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="insertText('# ', '')">H1 标题</el-dropdown-item>
              <el-dropdown-item @click="insertText('## ', '')">H2 标题</el-dropdown-item>
              <el-dropdown-item @click="insertText('### ', '')">H3 标题</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <span class="tool-label">格式</span>
        <button class="tool-btn" @click="insertText('**', '**')"><b>B</b></button>
        <span class="tool-label">加粗</span>
        <button class="tool-btn" @click="insertText('*', '*')"><i>I</i></button>
        <span class="tool-label">斜体</span>
        <button class="tool-btn" @click="insertText('~~', '~~')"><s>S</s></button>
        <span class="tool-label">删除</span>
      </div>
      <div class="tool-divider"></div>
      <div class="tool-group">
        <el-dropdown trigger="click">
          <button class="tool-btn">☰</button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="insertOrderedList">有序列表 (Ctrl+Shift+[)</el-dropdown-item>
              <el-dropdown-item @click="insertUnorderedList">无序列表 (Ctrl+Shift+])</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <span class="tool-label">列表</span>
        <button class="tool-btn" @click="insertText('> ', '')">"</button>
        <span class="tool-label">引用</span>
        <button class="tool-btn" @click="insertText('---\n', '')">—</button>
        <span class="tool-label">分隔</span>
      </div>
      <div class="tool-divider"></div>
      <div class="tool-group">
        <button class="tool-btn" @click="insertText('```\n', '\n```')">&lt;/&gt;</button>
        <span class="tool-label">代码</span>
        <button class="tool-btn" @click="insertText('![图片](', ')')">🖼</button>
        <span class="tool-label">图像</span>
        <button class="tool-btn" @click="insertText('[链接](', ')')">🔗</button>
        <span class="tool-label">链接</span>
        <button class="tool-btn" @click="insertTable">▦</button>
        <span class="tool-label">表格</span>
      </div>
    </div>

    <!-- 主编辑区 -->
    <div class="main">
      <!-- 左侧大纲 -->
      <aside class="sidebar-outline">
        <div class="outline-header">大纲</div>
        <div class="outline-content" id="outline-container"></div>
      </aside>

      <!-- 右侧编辑区 -->
      <div class="editor-wrapper">
        <!-- 标题 + 内容在同一个滚动容器内 -->
        <div class="title-row">
          <input 
            v-model="articleForm.title" 
            type="text" 
            class="title-input" 
            placeholder="请输入文章标题（5～100个字）" 
            maxlength="100"
          />
          <div class="title-count">
            <span class="icon">✎</span>
            <span>{{ titleTip }}</span>
          </div>
        </div>
        <div id="vditor" class="vditor-container"></div>
      </div>
    </div>

    <!-- 底部状态栏 -->
    <footer class="footer">
      <div class="footer-left">
        <span class="word-count">共 {{ wordCount }} 字</span>
        <button class="settings-btn" @click="showSettingsDialog = true">发文设置 ▼</button>
      </div>
      <div class="footer-right">
        <el-dropdown split-button @click="saveDraft">
          保存草稿
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="saveDraft">保存草稿</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <el-button @click="showSettingsDialog = true">定时发布</el-button>
        <el-button type="primary" :loading="loading" @click="publishArticle">发布文章</el-button>
      </div>
    </footer>

    <!-- 离开确认弹窗 -->
    <el-dialog 
      v-model="showLeaveDialog" 
      width="420px" 
      :show-close="true" 
      :close-on-click-modal="false"
      class="leave-confirm-dialog"
      align-center
    >
      <div class="leave-dialog">
        <p class="leave-title">重新加载此网站?</p>
        <p class="leave-desc">系统可能不会保存您所做的更改。</p>
      </div>
      <template #footer>
        <div class="leave-footer">
          <el-button class="leave-btn cancel" @click="showLeaveDialog = false">取消</el-button>
          <el-button class="leave-btn confirm" type="primary" @click="confirmLeave">确认离开</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 发布设置弹窗 -->
    <el-dialog v-model="showSettingsDialog" title="发布设置" width="500px">
      <el-form :model="articleForm" label-width="80px">
        <el-form-item label="分类" required>
          <el-select v-model="articleForm.categoryId" placeholder="选择分类" style="width: 100%">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="articleForm.tags" multiple filterable allow-create placeholder="添加标签" style="width: 100%">
            <el-option v-for="tag in tagOptions" :key="tag" :label="tag" :value="tag" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面">
          <el-input v-model="articleForm.cover" placeholder="输入封面图片URL" />
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="articleForm.summary" type="textarea" :rows="3" placeholder="文章摘要，不填则自动截取" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="可见性">
          <el-radio-group v-model="articleForm.isPublished">
            <el-radio :value="1">公开发布</el-radio>
            <el-radio :value="2">仅自己可见</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="设置">
          <el-checkbox v-model="isPinnedChecked">置顶文章</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showSettingsDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="publishArticle">确认发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { addArticle, updateArticle, getArticleById } from '@/api/article'
import { ElMessage } from 'element-plus'
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const router = useRouter()
const route = useRoute()
const isEditMode = ref(false)  // 是否编辑模式
const articleId = ref(null)    // 编辑时的文章ID
let vditor = null              // Vditor 实例
const showSettingsDialog = ref(false)
const showLeaveDialog = ref(false)
const loading = ref(false)
const wordCount = ref(0)
const isPinnedChecked = ref(false)


const articleForm = reactive({
  title: '',
  content: '',
  categoryId: null,
  tags: [],
  summary: '',
  cover: '',
  isPublished: 1
})

const articleTypeText = computed(() => {
  return articleForm.isPublished === 1 ? '发布文章' : '仅自己可见'
})

const titleTip = computed(() => {
  const len = articleForm.title.length
  if (len < 5) return `还需输入${5 - len}个字`
  return `${len} / 100`
})

const categories = [
  { id: 1, name: '设计沉思' },
  { id: 2, name: '技术探索' },
  { id: 3, name: '生活随笔' },
  { id: 4, name: '读书笔记' },
  { id: 5, name: '哲学思辨' }
]

const tagOptions = ['设计', '技术', '前端', 'Vue', 'React', '用户体验', '生活', '读书', '思考']

const goBack = () => router.back()

// 点击返回按钮
const handleBack = () => {
  // 如果有内容，显示确认弹窗
  if (articleForm.title || articleForm.content) {
    showLeaveDialog.value = true
  } else {
    goBack()
  }
}

// 确认离开
const confirmLeave = () => {
  showLeaveDialog.value = false
  goBack()
}

const updateWordCount = () => {
  const content = vditor ? vditor.getValue() : articleForm.content
  wordCount.value = content.replace(/\s/g, '').length
}



// 工具栏操作 - 调用 Vditor API
const insertText = (before, after) => {
  if (!vditor) return
  const selected = vditor.getSelection() || ''
  vditor.insertValue(before + selected + after)
}

const insertTable = () => {
  if (!vditor) return
  const table = `| 列1 | 列2 | 列3 |
| --- | --- | --- |
| 内容 | 内容 | 内容 |
`
  vditor.insertValue(table)
}

// 撤销/重做
const undo = () => vditor?.undo()
const redo = () => vditor?.redo()

// 插入有序列表
const insertOrderedList = () => {
  if (!vditor) return
  const selected = vditor.getSelection() || ''
  if (selected) {
    // 如果有选中文本，将每行转为有序列表
    const lines = selected.split('\n')
    const listText = lines.map((line, index) => `${index + 1}. ${line}`).join('\n')
    vditor.insertValue(listText)
  } else {
    vditor.insertValue('1. ')
  }
}

// 插入无序列表
const insertUnorderedList = () => {
  if (!vditor) return
  const selected = vditor.getSelection() || ''
  if (selected) {
    // 如果有选中文本，将每行转为无序列表
    const lines = selected.split('\n')
    const listText = lines.map(line => `- ${line}`).join('\n')
    vditor.insertValue(listText)
  } else {
    vditor.insertValue('- ')
  }
}

// 快捷键处理（只处理 Vditor 没有的快捷键）
const handleKeydown = (e) => {
  // Ctrl/Cmd + S: 保存草稿
  if ((e.ctrlKey || e.metaKey) && e.key === 's') {
    e.preventDefault()
    saveDraft()
    return
  }
  
  // Ctrl/Cmd + Enter: 发布
  if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
    e.preventDefault()
    showSettingsDialog.value = true
    return
  }
  
  // Ctrl/Cmd + Shift + P: 预览
  if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.key === 'P') {
    e.preventDefault()
    if (vditor) {
      const currentMode = vditor.getCurrentMode()
      vditor.setMode(currentMode === 'ir' ? 'sv' : 'ir')
    }
    return
  }
  
  // Ctrl/Cmd + Shift + [: 有序列表 (使用 code 因为 shift 会改变 key 值)
  if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.code === 'BracketLeft') {
    e.preventDefault()
    insertOrderedList()
    return
  }
  
  // Ctrl/Cmd + Shift + ]: 无序列表
  if ((e.ctrlKey || e.metaKey) && e.shiftKey && e.code === 'BracketRight') {
    e.preventDefault()
    insertUnorderedList()
    return
  }
}
// Vditor 内置快捷键说明：
// Ctrl+B: 加粗, Ctrl+I: 斜体, Ctrl+K: 链接
// Ctrl+Shift+K: 代码块, Ctrl+Z: 撤销, Ctrl+Y: 重做

const saveDraft = async () => {
  if (!articleForm.title) {
    ElMessage.warning('请输入文章标题')
    return
  }

  const userInfoStr = localStorage.getItem('userInfo')
  if (!userInfoStr) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  const userInfo = JSON.parse(userInfoStr)
  // 从 Vditor 获取最新内容
  const content = vditor ? vditor.getValue() : articleForm.content

  try {
    await addArticle({
      userId: userInfo.id,
      categoryId: articleForm.categoryId,
      title: articleForm.title,
      content: content || '',
      summary: articleForm.summary || '',
      cover: articleForm.cover || '',
      tags: articleForm.tags.join(','),
      isPinned: isPinnedChecked.value ? 1 : 0,
      isPublished: 0
    })
    ElMessage.success('草稿已保存')
  } catch (error) {
    ElMessage.error(error.message || '保存失败')
  }
}

const publishArticle = async () => {
  if (!articleForm.title || articleForm.title.length < 5) {
    ElMessage.warning('标题至少需要5个字')
    return
  }
  
  // 从 Vditor 获取最新内容
  const content = vditor ? vditor.getValue() : articleForm.content
  
  if (!content) {
    ElMessage.warning('请输入文章内容')
    return
  }
  if (!articleForm.categoryId) {
    showSettingsDialog.value = true
    ElMessage.warning('请选择文章分类')
    return
  }

  const userInfoStr = localStorage.getItem('userInfo')
  if (!userInfoStr) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  const userInfo = JSON.parse(userInfoStr)

  loading.value = true
  try {
    const articleData = {
      userId: userInfo.id,
      categoryId: articleForm.categoryId,
      title: articleForm.title,
      content: content,
      summary: articleForm.summary || content.substring(0, 200),
      cover: articleForm.cover || '',
      tags: articleForm.tags.join(','),
      isPinned: isPinnedChecked.value ? 1 : 0,
      isPublished: articleForm.isPublished
    }

    if (isEditMode.value && articleId.value) {
      // 编辑模式：更新文章
      articleData.id = articleId.value
      await updateArticle(articleData)
      ElMessage.success('文章更新成功！')
    } else {
      // 新增模式
      await addArticle(articleData)
      ElMessage.success('文章发布成功！')
    }

    showSettingsDialog.value = false
    setTimeout(() => router.push('/my-articles'), 1000)
  } catch (error) {
    ElMessage.error(error.message || '发布失败')
  } finally {
    loading.value = false
  }
}

// 更新大纲
const updateOutline = () => {
  const outlineContainer = document.getElementById('outline-container')
  if (!outlineContainer || !vditor) return
  
  const content = vditor.getValue()
  const headings = content.match(/^#{1,3}\s+.+$/gm) || []
  
  let html = '<ul class="outline-list">'
  headings.forEach(heading => {
    const level = heading.match(/^#+/)[0].length
    const text = heading.replace(/^#+\s+/, '')
    html += `<li class="outline-item level-${level}">${text}</li>`
  })
  html += '</ul>'
  
  outlineContainer.innerHTML = headings.length > 0 ? html : '<p class="outline-empty">暂无大纲</p>'
}

// 初始化 Vditor 编辑器
const initVditor = (content = '') => {
  vditor = new Vditor('vditor', {
    minHeight: 400,
    mode: 'wysiwyg',  // 所见即所得模式，支持 HTML 渲染
    placeholder: '开始写作...',
    theme: 'classic',
    icon: 'ant',
    toolbar: [],  // 隐藏工具栏，使用页面顶部的工具栏
    tab: '\t',    // 启用 Tab 键，支持列表缩进
    outline: {
      enable: false  // 禁用自带大纲，使用自定义大纲
    },
    cache: {
      enable: false
    },
    preview: {
      markdown: {
        autoSpace: true,
        mark: true,
        sanitize: false  // 禁用 HTML 过滤，允许所有 HTML 标签
      },
      hljs: {
        lineNumber: true
      }
    },
    after: () => {
      if (content) {
        vditor.setValue(content)
      }
      updateWordCount()
      updateOutline()
    },
    input: () => {
      articleForm.content = vditor.getValue()
      updateWordCount()
      updateOutline()
    }
  })
}

// 页面加载时检查是否是编辑模式
onMounted(async () => {
  // 注册快捷键
  document.addEventListener('keydown', handleKeydown)
  
  const id = route.query.id
  if (id) {
    isEditMode.value = true
    articleId.value = Number(id)
    // 先加载文章，再初始化编辑器
    try {
      const res = await getArticleById(id)
      const article = res.data
      if (article) {
        articleForm.title = article.title || ''
        articleForm.content = article.content || ''
        articleForm.categoryId = article.categoryId
        articleForm.tags = article.tags ? article.tags.split(',').filter(t => t) : []
        articleForm.summary = article.summary || ''
        articleForm.cover = article.cover || ''
        articleForm.isPublished = article.isPublished || 1
        isPinnedChecked.value = article.isPinned === 1
        initVditor(article.content || '')
      }
    } catch (error) {
      ElMessage.error('加载文章失败')
      initVditor('')
    }
  } else {
    initVditor('')
  }
})

// 组件销毁时清理
onBeforeUnmount(() => {
  // 移除快捷键监听
  document.removeEventListener('keydown', handleKeydown)
  
  if (vditor) {
    vditor.destroy()
    vditor = null
  }
})
</script>

<style lang="scss" scoped>
.write-page {
  height: 100vh;
  background: #f5f5f5;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 顶部导航 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 50px;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  flex-shrink: 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;

  .logo-icon {
    width: 28px;
    height: 28px;
    border-radius: 6px;
  }

  .logo-text {
    font-size: 16px;
    font-weight: 600;
    color: #2C3E50;
    font-family: 'Georgia', serif;
  }
}

.back-btn {
  font-size: 24px;
  color: #666;
  cursor: pointer;
  padding: 4px 8px;

  &:hover {
    color: #1ABC9C;
  }
}

.article-type {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 16px;
  color: #333;
  cursor: pointer;

  &:hover {
    color: #1ABC9C;
  }

  .arrow {
    font-size: 12px;
    color: #666;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 工具栏 */
.toolbar {
  display: flex;
  align-items: center;
  padding: 8px 20px;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  gap: 4px;
  flex-wrap: wrap;
  flex-shrink: 0;
  position: sticky;
  top: 50px;
  z-index: 99;
}

.tool-group {
  display: flex;
  align-items: center;
  gap: 2px;
}

.tool-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  background: transparent;
  color: #666;
  cursor: pointer;
  border-radius: 4px;
  font-size: 14px;

  &:hover {
    background: rgba(26, 188, 156, 0.1);
    color: #1ABC9C;
  }
}

.tool-divider {
  width: 1px;
  height: 20px;
  background: #e8e8e8;
  margin: 0 8px;
}

.tool-label {
  font-size: 12px;
  color: #999;
  margin-left: 2px;
  margin-right: 8px;
}

/* 主编辑区 */
.main {
  flex: 1;
  display: flex;
  overflow: hidden;
  min-height: 0;
}

/* 左侧大纲 */
.sidebar-outline {
  width: 200px;
  background: #fff;
  border-right: 1px solid #e8e8e8;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  
  .outline-header {
    padding: 12px 16px;
    font-size: 14px;
    font-weight: 500;
    color: #2C3E50;
    border-bottom: 1px solid #f0f0f0;
  }
  
  .outline-content {
    flex: 1;
    overflow-y: auto;
    padding: 8px 0;
  }
  
  :deep(.outline-list) {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  :deep(.outline-item) {
    padding: 6px 16px;
    font-size: 13px;
    color: #666;
    cursor: pointer;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    
    &:hover {
      color: #1ABC9C;
      background: rgba(26, 188, 156, 0.05);
    }
    
    &.level-1 { padding-left: 16px; font-weight: 500; }
    &.level-2 { padding-left: 28px; }
    &.level-3 { padding-left: 40px; font-size: 12px; }
  }
  
  :deep(.outline-empty) {
    padding: 16px;
    font-size: 13px;
    color: #999;
    text-align: center;
  }
}

/* 右侧编辑区容器 */
.editor-wrapper {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  background: #fff;
}

/* 标题区域 */
.title-row {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 40px 16px;
  border-bottom: 1px solid #f0f0f0;
}

.title-input {
  width: 100%;
  max-width: 600px;
  font-size: 24px;
  font-weight: 600;
  color: #2C3E50;
  border: none;
  outline: none;
  background: transparent;
  text-align: center;

  &::placeholder {
    color: #bbb;
  }
}

.title-count {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #999;
  margin-top: 8px;

  .icon {
    color: #ccc;
  }
}

/* Vditor 编辑器容器 */
.vditor-container {
  border: none !important;
  
  :deep(.vditor) {
    border: none !important;
    
    .vditor-wysiwyg {
      padding: 20px 40px;
      font-size: 16px;
      line-height: 1.8;
    }
    
    .vditor-ir {
      padding: 20px 40px;
      font-size: 16px;
      line-height: 1.8;
    }
    
    .vditor-reset {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
    }
  }
}

/* 底部状态栏 */
.footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  background: #fff;
  border-top: 1px solid #e8e8e8;
  flex-shrink: 0;
  position: sticky;
  bottom: 0;
  z-index: 100;
}

.footer-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.word-count {
  font-size: 13px;
  color: #999;
}

.settings-btn {
  font-size: 13px;
  color: #666;
  background: none;
  border: none;
  cursor: pointer;

  &:hover {
    color: #1ABC9C;
  }
}

/* 离开确认弹窗 */
:deep(.leave-confirm-dialog) {
  .el-dialog__header {
    padding: 16px 20px 0;
    margin-right: 0;
    
    .el-dialog__headerbtn {
      top: 16px;
      right: 16px;
      width: 24px;
      height: 24px;
      
      .el-dialog__close {
        font-size: 16px;
        color: #999;
      }
    }
  }
  
  .el-dialog__body {
    padding: 30px 40px 20px;
  }
  
  .el-dialog__footer {
    padding: 0 40px 30px;
  }
}

.leave-dialog {
  text-align: center;

  .leave-title {
    font-size: 16px;
    font-weight: 500;
    color: #333;
    margin-bottom: 16px;
  }

  .leave-desc {
    font-size: 14px;
    color: #999;
    line-height: 1.5;
  }
}

.leave-footer {
  display: flex;
  justify-content: center;
  gap: 16px;
  
  .leave-btn {
    min-width: 88px;
    height: 36px;
    border-radius: 4px;
    font-size: 14px;
    
    &.cancel {
      background: #fff;
      border: 1px solid #ddd;
      color: #666;
      
      &:hover {
        border-color: #ccc;
        color: #333;
      }
    }
    
    &.confirm {
      background: #1ABC9C;
      border-color: #1ABC9C;
      
      &:hover {
        background: #16A085;
        border-color: #16A085;
      }
    }
  }
}

.footer-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 响应式 */
@media (max-width: 1024px) {
  .sidebar-outline {
    display: none;
  }

  .title-row,
  .content-area {
    padding: 16px 20px;
  }

  .content-hints {
    left: 20px;
  }
}

@media (max-width: 768px) {
  .toolbar {
    overflow-x: auto;
    flex-wrap: nowrap;

    .tool-label {
      display: none;
    }
  }

  .footer {
    flex-direction: column;
    gap: 12px;

    .footer-left,
    .footer-right {
      width: 100%;
      justify-content: center;
    }
  }
}
</style>
