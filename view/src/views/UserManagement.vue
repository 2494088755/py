<template>
  <div>
    <div style="margin-bottom: 20px;">
      <a-input
        v-model:value="searchKeyword"
        placeholder="请输入用户名、邮箱或电话进行搜索"
        style="width: 300px; margin-right: 10px;"
        @keyup.enter="handleSearch"
      />
      <a-button type="primary" @click="handleSearch">搜索</a-button>
      <a-button type="primary" @click="showAddModal" style="margin-left: 10px">新增用户</a-button>
    </div>
    <a-table 
      :columns="columns" 
      :dataSource="users" 
      rowKey="id" 
      :pagination="pagination"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-button type="primary" @click="showEditModal(record)">编辑</a-button>
          <a-button type="primary" @click="confirmDelete(record)" style="margin-left: 10px">删除</a-button>
        </template>
      </template>
    </a-table>

    <!-- 新增用户模态框 -->
    <a-modal v-model:visible="addModalVisible" title="新增用户" @ok="handleAddUser" @cancel="closeAddModal">
      <a-form :model="newUser" layout="vertical">
        <a-form-item label="用户名">
          <a-input v-model:value="newUser.username" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="newUser.email" />
        </a-form-item>
        <a-form-item label="电话">
          <a-input v-model:value="newUser.phone" />
        </a-form-item>
        <a-form-item label="地址">
          <a-input v-model:value="newUser.address" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 编辑用户模态框 -->
    <a-modal v-model:visible="editModalVisible" title="编辑用户" @ok="handleEdit" @cancel="closeEditModal">
      <a-form :model="editUser" layout="vertical">
        <a-form-item label="用户名">
          <a-input v-model:value="editUser.username" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="editUser.email" />
        </a-form-item>
        <a-form-item label="电话">
          <a-input v-model:value="editUser.phone" />
        </a-form-item>
        <a-form-item label="地址">
          <a-input v-model:value="editUser.address" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 删除确认对话框 -->
    <a-modal v-model:visible="deleteModalVisible" title="确认删除" @ok="handleDelete" @cancel="closeDeleteModal">
      <p>确定要删除该用户吗？</p>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request';

const users = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const addModalVisible = ref(false)
const editModalVisible = ref(false)
const deleteModalVisible = ref(false)
const newUser = ref({})
const editUser = ref({})
const deleteUserId = ref(null)

const columns = [
  {
    title: 'ID',
    dataIndex: 'userId',
    key: 'userId',
    align: 'center'
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    align: 'center'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email',
    align: 'center'
  },
  {
    title: '电话',
    dataIndex: 'phone',
    key: 'phone',
    align: 'center'
  },
  {
    title: '地址',
    dataIndex: 'address',
    key: 'address',
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center'
  }
]

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true
})

// 获取用户列表
const fetchUsers = async () => {
  const params = {
    currentPage: currentPage.value,
    pageSize: pageSize.value,
    keyword: searchKeyword.value
  }
  request.post('/user/list', params).then(res => {
    if (res.code === "200") {
      users.value = res.data.list;
      total.value = res.data.total
    }
  })
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchUsers()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchUsers()
}

// 页码改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchUsers()
}

// 显示新增用户模态框
const showAddModal = () => {
  newUser.value = {}
  addModalVisible.value = true
}

// 关闭新增用户模态框
const closeAddModal = () => {
  addModalVisible.value = false
}

// 显示编辑用户模态框
const showEditModal = (record) => {
  editUser.value = { ...record }
  editModalVisible.value = true
}

// 关闭编辑用户模态框
const closeEditModal = () => {
  editModalVisible.value = false
}

// 确认删除用户
const confirmDelete = (record) => {
  deleteUserId.value = record.userId
  deleteModalVisible.value = true
}

// 关闭删除确认对话框
const closeDeleteModal = () => {
  deleteModalVisible.value = false
}

// 新增用户
const handleAddUser = () => {
  request.post('/user/add', newUser.value).then(res => {
    if (res.code === "200") {
      closeAddModal()
      fetchUsers()
    }
  })
}

// 编辑用户
const handleEdit = () => {
  request.put('/user/update', editUser.value).then(res => {
    if (res.code === "200") {
      closeEditModal()
      fetchUsers()
    }
  })
}

// 删除用户
const handleDelete = () => {
  request.delete(`/user/delete/${deleteUserId.value}`).then(res => {
    if (res.code === "200") {
      closeDeleteModal()
      fetchUsers()
    }
  })
}

// 组件挂载时获取数据
onMounted(() => {
  fetchUsers()
})
</script>