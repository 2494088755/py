<!-- components/Header.vue -->
<template>
  <div class="header-container">
    <div class="logo">后台管理系统</div>
    <div class="user-info">
      <a-dropdown>
        <span class="user-dropdown">
          {{ username }}
          <DownOutlined class="arrow-icon" />
        </span>
        <template #overlay>
          <a-menu>
            <a-menu-item @click="handleLogout">退出登录</a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import { useRouter } from 'vue-router'
import { DownOutlined } from '@ant-design/icons-vue'
import { Dropdown as ADropdown, Menu as AMenu, MenuItem as AMenuItem } from 'ant-design-vue'

const router = useRouter()
const username = ref('管理员')

function handleLogout() {
  localStorage.clear()
  // 刷新页面
  router.go(0)
}

onMounted(() => {
  const userInfo = localStorage.getItem('user')
  if (userInfo) {
    username.value =JSON.parse(userInfo).username
  }
})
</script>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.logo {
  font-size: 18px;
  font-weight: bold;
  color: #2c3e50;
}

.user-info {
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 8px 12px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.user-info:hover {
  background-color: #e6f7ff;
}

.user-dropdown {
  display: inline-flex;
  align-items: center;
  margin-top: 0;
  color: #2c3e50;
}

.arrow-icon {
  margin-left: 4px;
  transition: transform 0.3s ease;
  color: #2c3e50;
}

.user-info:hover .arrow-icon {
  transform: rotate(180deg);
}
</style>