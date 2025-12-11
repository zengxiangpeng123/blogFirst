<template>
  <footer class="footer">
    <div class="container footer-inner">
      <p class="slogan">在喧嚣中寻一处静隅，记录思考与成长</p>
      
      <div class="social-links">
        <a :href="author.github" target="_blank"><el-icon><Link /></el-icon> GitHub</a>
        <a :href="`mailto:${author.email}`"><el-icon><Message /></el-icon> 邮箱</a>
      </div>
      
      <p class="copyright">© 2024 静隅思录. All Rights Reserved.</p>
    </div>
  </footer>
</template>

<script setup>
import { author } from '@/data/mock'
</script>

<style lang="scss" scoped>
.footer {
  background: #F0F0F0;
  padding: 40px 0;
  margin-top: 50px;
}

.footer-inner {
  text-align: center;
}

.slogan {
  color: #555;
  font-size: 15px;
  margin-bottom: 20px;
}

.social-links {
  display: flex;
  justify-content: center;
  gap: 25px;
  margin-bottom: 20px;
  
  a {
    color: #95A5A6;
    display: flex;
    align-items: center;
    gap: 5px;
    font-size: 14px;
    
    &:hover { color: #1ABC9C; }
  }
}

.copyright {
  color: #95A5A6;
  font-size: 13px;
}
</style>
