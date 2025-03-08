<template>
  <a-layout style="min-height: 100vh">
    <!-- 侧边栏 -->
    <a-layout-sider collapsible v-model:collapsed="collapsed" style="background-color: #2c3e50;">
      <Sidebar :collapsed="collapsed"/>
    </a-layout-sider>
    <!-- 主体部分 -->
    <a-layout>
      <!-- 顶部导航 -->
      <a-layout-header style="background: #fff; padding: 0;  width: 100%; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);">
        <Header/>
      </a-layout-header>
      <!-- 内容区域 -->
      <a-layout-content style="padding: 20px; background-color: #f0f2f5;">
        <router-view />
      </a-layout-content>
      <!-- 底部 -->
      <a-layout-footer style="text-align: center; background-color: #fff; padding: 16px;">
        ©图书后台管理系统
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { ref } from 'vue'
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'

const collapsed = ref(false)
</script>

<style scoped>
/* 可根据需要自定义样式 */
</style>