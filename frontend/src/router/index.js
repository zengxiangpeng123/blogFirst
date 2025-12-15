import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/pages/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('@/pages/user/Login.vue'), meta: { hideLayout: true } },
  { path: '/register', name: 'Register', component: () => import('@/pages/user/Register.vue'), meta: { hideLayout: true } },
  { path: '/article/:id', name: 'Article', component: () => import('@/pages/Article.vue') },
  { path: '/category/:name', name: 'Category', component: () => import('@/pages/Category.vue') },
  { path: '/about', name: 'About', component: () => import('@/pages/About.vue') },
  { path: '/projects', name: 'Projects', component: () => import('@/pages/Projects.vue') },
  { path: '/archive', name: 'Archive', component: () => import('@/pages/Archive.vue') },
  { path: '/favorites', name: 'Favorites', component: () => import('@/pages/Favorites.vue') },
  { path: '/history', name: 'History', component: () => import('@/pages/History.vue') },
  { path: '/subscribe', name: 'Subscribe', component: () => import('@/pages/Subscribe.vue') },
  { path: '/profile', name: 'Profile', component: () => import('@/pages/Profile.vue') },
  { path: '/settings', name: 'Settings', component: () => import('@/pages/Settings.vue') },
  { path: '/write', name: 'Write', component: () => import('@/pages/Write.vue'), meta: { hideLayout: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
