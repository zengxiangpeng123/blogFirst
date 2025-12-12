import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/Home.vue') },
  { path: '/article/:id', name: 'Article', component: () => import('@/views/Article.vue') },
  { path: '/category/:name', name: 'Category', component: () => import('@/views/Category.vue') },
  { path: '/about', name: 'About', component: () => import('@/views/About.vue') },
  { path: '/projects', name: 'Projects', component: () => import('@/views/Projects.vue') },
  { path: '/archive', name: 'Archive', component: () => import('@/views/Archive.vue') },
  { path: '/favorites', name: 'Favorites', component: () => import('@/views/Favorites.vue') },
  { path: '/history', name: 'History', component: () => import('@/views/History.vue') },
  { path: '/subscribe', name: 'Subscribe', component: () => import('@/views/Subscribe.vue') },
  { path: '/profile', name: 'Profile', component: () => import('@/views/Profile.vue') },
  { path: '/settings', name: 'Settings', component: () => import('@/views/Settings.vue') },
  { path: '/write', name: 'Write', component: () => import('@/views/Write.vue'), meta: { hideLayout: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
