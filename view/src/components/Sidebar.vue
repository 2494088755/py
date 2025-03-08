<!-- components/Sidebar.vue -->
<template>
  <el-menu
      :default-active="'dashboard'"
      mode="vertical"
      background-color="#2c3e50"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleMenuClick"
      class="custom-menu"
      :collapse="collapsed"
  >
    <el-menu-item index="dashboard" class="menu-item" :collapse="collapsed">
      <el-icon>
        <House/>
      </el-icon>
      <span>仪表盘</span>
    </el-menu-item>
    <el-menu-item index="user" v-if="isAdmin" class="menu-item" :collapse="collapsed">
      <el-icon>
        <user/>
      </el-icon>
      <span>用户管理</span>
    </el-menu-item>
    <el-menu-item index="book" class="menu-item" :collapse="collapsed">
      <el-icon>
        <Goods/>
      </el-icon>
      <span v-if="isAdmin">图书管理</span>
      <span v-else>图书借阅</span>
    </el-menu-item>
    <el-menu-item index="order" class="menu-item" :collapse="collapsed">
      <el-icon>
        <shopping-cart/>
      </el-icon>
      <span v-if="isAdmin">订单管理</span>
      <span v-else>借阅记录</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import {useRouter} from 'vue-router'
import {onMounted, ref} from "vue";
import {ElMenu, ElMenuItem, ElIcon} from 'element-plus'
import {User, ShoppingCart, House, Goods} from '@element-plus/icons-vue'

const router = useRouter()
const isAdmin = ref(false)

// 接收父组件传递的 collapsed 状态
const props = defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
})

function handleMenuClick(index) {
  // 根据 index 跳转到对应页面
  if (index === 'dashboard') {
    router.push({name: 'Dashboard'})
  } else if (index === 'user') {
    router.push({name: 'UserManagement'})
  } else if (index === 'book') {
    router.push({name: 'BookManagement'})
  } else if (index === 'order') {
    router.push({name: 'OrderManagement'})
  }
}

// 进入页面获取本地储存的用户信息
onMounted(() => {
  const userInfo = localStorage.getItem('user')
  if (userInfo) {
    const user = JSON.parse(userInfo)
    console.log('User info:', user) // 添加日志，检查用户信息
    isAdmin.value = user.username === 'admin'
    console.log('isAdmin:', isAdmin.value) // 添加日志，检查 isAdmin 的值
  }
})
</script>

<style scoped>
.custom-menu {
  border-right: none;
  background-color: #2c3e50;
}

.menu-item {
  margin: 8px 0;
  transition: all 0.3s ease;
  color: #ffffff;
}

.menu-item:hover {
  background-color: #34495e !important;
  border-radius: 10px;
  transform: translateX(1px);
}

.el-menu-item.is-active {
  background-color: #34495e;
  color: #ffd04b;
}
</style>