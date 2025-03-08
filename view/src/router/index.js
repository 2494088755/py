import { createRouter, createWebHistory } from 'vue-router'
import { defineAsyncComponent } from 'vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: defineAsyncComponent(() => import('../views/LoginView.vue')),
    },
    {
      path: '/register',
      name: 'register',
      component: defineAsyncComponent(() => import('../views/RegisterView.vue')),
    },
    {
      path: '/',
      component: defineAsyncComponent(() => import('@/components/AppLayout.vue')),
      meta: { requiresAuth: true },  // 需要登录
      children: [
        {
          path: '',
          name: 'Dashboard',
          component: defineAsyncComponent(() => import('@/views/Dashboard.vue')),
          meta: { requiresAuth: true },  // 需要登录
        },
        {
          path: 'user',
          name: 'UserManagement',
          meta: { requiresAuth: true },  // 需要登录
          component: defineAsyncComponent(() => import('@/views/UserManagement.vue')),
        },
        {path: 'order',
          name: 'OrderManagement',
          meta: { requiresAuth: true },  // 需要登录
          component: defineAsyncComponent(() => import('@/views/OrderManagement.vue')),

        },
        {
          path: 'book',
          name: 'BookManagement',
          meta: { requiresAuth: true },  // 需要登录
          component: defineAsyncComponent(() => import('@/views/BookManagement.vue')),
        }
      ]
    },
  ],
})
// router/index.js
router.beforeEach((to, from, next) => {
  // 如果目标路由需要身份验证
  if (to.meta.requiresAuth) {
    console.log('需要身份验证')
    // 假设登录后会在 localStorage 保存 user 对象或 token
    const user = localStorage.getItem('user')
    if (!user) {
      // 未登录，重定向到登录页，并把当前路由作为重定向参数
      next({ name: 'login', query: { redirect: to.fullPath } })
      return
    }
  }
  next()
})


export default router
