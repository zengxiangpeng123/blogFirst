import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/Home.vue') },
  { path: '/article/:id', name: 'Article', component: () => import('@/views/Article.vue') },
  { path: '/category/:name', name: 'Category', component: () => import('@/views/Category.vue') },
  { path: '/about', name: 'About', component: () => import('@/views/About.vue') },
  { path: '/projects', name: 'Projects', component: () => import('@/views/Projects.vue') },
  { path: '/archive', name: 'Archive', component: () => import('@/views/Archive.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
