<template>
  <div class="dashboard-container">
    <div class="cards-container" v-if="isAdmin">
      <el-card class="card">
        <div class="card-content">
          <el-icon><User /></el-icon>
          <span>用户总数</span>
          <h3>1,234</h3>
        </div>
      </el-card>
      <el-card class="card">
        <div class="card-content">
          <el-icon><Goods /></el-icon>
          <span>图书总数</span>
          <h3>5,678</h3>
        </div>
      </el-card>
      <el-card class="card">
        <div class="card-content">
          <el-icon><ShoppingCart /></el-icon>
          <span>订单总数</span>
          <h3>9,876</h3>
        </div>
      </el-card>
    </div>
    <!-- 图表容器 -->
    <div class="chart-container" v-if="isAdmin"> <!-- 修改：添加 v-if 控制显示 -->
      <el-card>
        <h3>图书借阅趋势</h3>
        <div class="chart-placeholder" ref="chartRef"></div>
      </el-card>
    </div>
    <!-- 非管理员用户显示的图片 -->
    <div v-else class="non-admin-image"> <!-- 新增：非管理员用户显示的图片 -->
      <img src="@/assets/生成图书管理系统封面.png" alt="Non Admin Image" style="width: 1200px;height: 550px">
    </div>
  </div>
</template>

<script setup>
import { User, Goods, ShoppingCart } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref(null)
const isAdmin = ref(false) // 新增：用于存储用户是否为管理员

onMounted(() => {
  const userInfo = localStorage.getItem('user')
  if (userInfo) {
    const user = JSON.parse(userInfo)
    isAdmin.value = user.username === 'admin' // 根据用户信息设置 isAdmin 值
  }

  if (isAdmin.value) {
    const chart = echarts.init(chartRef.value)
    const option = {
      xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'line',
          smooth: true
        }
      ]
    }
    chart.setOption(option)
  }
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.cards-container {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.card {
  flex: 1;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.card-content .el-icon {
  font-size: 40px;
  color: #409EFF;
}

.card-content span {
  margin-top: 10px;
  font-size: 16px;
  color: #606266;
}

.card-content h3 {
  margin-top: 10px;
  font-size: 24px;
  color: #303133;
}

.chart-container {
  margin-top: 30px;
}

.chart-placeholder {
  height: 300px;
  background-color: #f5f7fa;
  border-radius: 8px;
  margin-top: 20px;
}

</style>