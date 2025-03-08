<template>
  <div class="order-management">
    <el-input v-model="searchUserId" placeholder="请输入用户ID" style="width: 200px; " v-if="isAdmin"/>
    <el-button type="primary" @click="handleSearchByUserId" v-if="isAdmin">查询</el-button>
    <el-select v-model="filterStatus" placeholder="选择状态" style="width: 200px; margin-left: 10px;" @change="handleFilterByStatus">
      <el-option label="全部" value=""/>
      <el-option label="借阅中" value="借阅中"/>
      <el-option label="已归还" value="已归还"/>
      <el-option label="审核中" value="审核中"/>
      <el-option label="超期未归还" value="超期未归还"/>
    </el-select>
    <el-table :data="orders" style="width: 100%">
      <el-table-column prop="orderId" label="订单ID" width="70" align="center"/>
      <el-table-column prop="userId" label="用户ID" width="70" align="center"/>
      <el-table-column prop="username" label="借阅人" width="70" align="center"/>
      <el-table-column prop="bookTitle" label="书籍名称" width="120" align="center"/>
      <el-table-column prop="borrowTime" label="借阅时间(天)" width="80" align="center"/>
      <el-table-column prop="returnTime" label="归还时间" width="160" align="center">
        <template #default="scope">
          {{ formatDate(scope.row.returnTime) }}
        </template>
      </el-table-column>

      <el-table-column label="实际归还时间" width="160" align="center">
        <template #default="scope">
          {{ scope.row.actualReturnTime ? formatDate(scope.row.actualReturnTime) : '未归还' }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="160" align="center">
        <template #default="scope">
          {{ formatDate(scope.row.createdAt) }}
        </template>
      </el-table-column>
<!--      <el-table-column label="更新时间" width="155" align="center" v-if="isAdmin">-->
<!--        <template #default="scope">-->
<!--          {{ formatDate(scope.row.updatedAt) }}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="status" label="状态" width="70" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
<!--          <el-button @click="handleUpdateStatus(scope.row)" v-if="isAdmin">更新状态</el-button>-->
          <el-button type="danger" @click="handleDelete(scope.row)" v-if="isAdmin">删除</el-button>
          <el-button type="success" @click="handleReturn(scope.row)"
                     v-if="!isAdmin"
                     :disabled="scope.row.status !== '借阅中' && scope.row.status !== '超期未归还'

">归还
          </el-button>
          <el-button type="primary" @click="handleConfirmReturn(scope.row)"
                     v-if="isAdmin && scope.row.status === '审核中'">确认归还
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        style="margin-top: 20px; text-align: right;"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
    />

    <!-- 更新状态对话框 -->
    <el-dialog v-model="statusDialogVisible" title="更新订单状态" width="30%">
      <el-select v-model="selectedStatus" placeholder="选择状态">
        <el-option label="借阅中" value="借阅中"/>
        <el-option label="已归还" value="已归还"/>
        <el-option label="审核中" value="审核中"/>
        <el-option label="超期未归还" value="超期未归还"/>
      </el-select>
      <template #footer>
        <el-button @click="statusDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmUpdateStatus">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted} from 'vue'
import request from '@/utils/request';
import {ElMessage} from "element-plus";

const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const statusDialogVisible = ref(false)
const selectedStatus = ref('')
const selectedOrderId = ref(null)
const searchUserId = ref('')
const filterStatus = ref('')
const isAdmin = ref(false)
let intervalId = null

// 格式化时间为年月日时分秒
const formatDate = (timestamp) => {
  const date = new Date(timestamp);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 获取订单列表
const fetchOrders = async () => {
  const params = {
    page: currentPage.value,
    size: pageSize.value,
  }

  // 根据筛选条件添加参数
  if (filterStatus.value) {
    params.status = filterStatus.value
  }

  // 如果是普通用户，添加用户ID参数
  if (!isAdmin.value) {
    const userInfo = localStorage.getItem('user')
    if (userInfo) {
      params.userId = JSON.parse(userInfo).userId
    }
  }

  request.post('/orders/page', params).then(res => {
    if (res.code === "200") {
      orders.value = res.data.data.map(order => {
        // 假设后端返回了bookTitle字段
        order.bookTitle = order.bookTitle || '未知书籍';
        return order;
      });
      total.value = res.data.total;
    } else {
      ElMessage.error(res.msg);
    }
  })
}

// 处理状态筛选
const handleFilterByStatus = () => {
  fetchOrders()
}

// 处理更新状态
const handleUpdateStatus = (row) => {
  selectedOrderId.value = row.orderId
  selectedStatus.value = row.status
  statusDialogVisible.value = true
}

// 确认更新状态
const confirmUpdateStatus = () => {
  request.put(`/orders/${selectedOrderId.value}`, {status: selectedStatus.value}).then(res => {
    if (res.code === "200") {
      ElMessage.success('状态更新成功')
      statusDialogVisible.value = false
      fetchOrders()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 处理删除
const handleDelete = (row) => {
  request.delete(`/orders/${row.orderId}`).then(res => {
    if (res.code === "200") {
      ElMessage.success('删除成功')
      fetchOrders()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 处理归还
const handleReturn = (row) => {
  request.post(`/orders/${row.orderId}/return`).then(res => {
    if (res.code === "200") {
      ElMessage.success('归还申请已提交，等待管理员审核')
      fetchOrders()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleConfirmReturn = (row) => {
  request.post(`/orders/${row.orderId}/confirmReturn`).then(res => {
    if (res.code === "200") {
      ElMessage.success('归还确认成功')
      fetchOrders()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 检测订单是否到期
const checkOrderExpiry = () => {
  orders.value.forEach(order => {
    if (new Date(order.returnTime) < new Date() && order.status !== '已归还') {
      order.status = '超期未归还';
    }
  });
}

// 组件挂载时获取数据
onMounted(() => {
  // 获取用户身份
  const userInfo = localStorage.getItem('user')
  if (userInfo) {
    isAdmin.value = JSON.parse(userInfo).username === 'admin'
  }
  fetchOrders()
  // 每秒检测一次订单是否到期
  intervalId = setInterval(checkOrderExpiry, 1000);
})

// 组件卸载时清除定时器
onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId);
  }
})
</script>

<style scoped>
.order-management {
  padding: 20px;
}
</style>