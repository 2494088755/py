<template>
  <div class="book-management">
    <div style="margin-bottom: 20px;">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入书名、作者或出版社进行搜索"
        style="width: 300px; margin-right: 10px;"
        @keyup.enter="handleSearch"
      />
      <el-select v-model="selectedCategory" placeholder="选择类别" style="width: 200px; margin-right: 10px;" @change="handleSearch">
        <el-option
          v-for="category in categories"
          :key="category.id"
          :label="category.name"
          :value="category.id"
        />
      </el-select>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button v-if="isAdmin" type="primary" @click="showAddModal" style="margin-left: 10px">添加图书</el-button>
    </div>
    <el-table :data="books" style="width: 100%">
      <el-table-column prop="title" label="书名" width="180" align="center" />
      <el-table-column prop="author" label="作者" width="180" align="center" />
      <el-table-column prop="publisher" label="出版社" width="180" align="center" />
      <el-table-column prop="isbn" label="ISBN" width="180" align="center" />
      <el-table-column prop="price" label="价格" width="120" align="center" />
      <el-table-column prop="stock" label="库存" width="120" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button v-if="isAdmin" @click="showEditModal(scope.row)">编辑</el-button>
          <el-button v-if="isAdmin" type="danger" @click="confirmDelete(scope.row)">删除</el-button>
          <el-button v-if="!isAdmin" type="primary" @click="handleBorrowBook(scope.row)">借阅</el-button>
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

    <!-- 新增图书模态框 -->
    <el-dialog v-model="addModalVisible" title="新增图书" @close="closeAddModal">
      <el-form :model="newBook" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="newBook.title" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="newBook.author" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="newBook.publisher" />
        </el-form-item>
        <el-form-item label="ISBN">
          <el-input v-model="newBook.isbn" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="newBook.price" type="number" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input v-model="newBook.stock" type="number" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="closeAddModal">取消</el-button>
        <el-button type="primary" @click="handleAddBook">确认</el-button>
      </template>
    </el-dialog>

    <!-- 编辑图书模态框 -->
    <el-dialog v-model="editModalVisible" title="编辑图书" @close="closeEditModal">
      <el-form :model="editBook" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="editBook.title" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="editBook.author" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="editBook.publisher" />
        </el-form-item>
        <el-form-item label="ISBN">
          <el-input v-model="editBook.isbn" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="editBook.price" type="number" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input v-model="editBook.stock" type="number" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="closeEditModal">取消</el-button>
        <el-button type="primary" @click="handleEditBook">确认</el-button>
      </template>
    </el-dialog>

    <!-- 删除确认对话框 -->
    <el-dialog v-model="deleteModalVisible" title="确认删除" width="30%">
      <span>确定要删除该图书吗？</span>
      <template #footer>
        <el-button @click="closeDeleteModal">取消</el-button>
        <el-button type="danger" @click="handleDeleteBook">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request';
import {ElMessage, ElMessageBox} from "element-plus";

const books = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const categories = ref([])
const selectedCategory = ref('')

const addModalVisible = ref(false)
const editModalVisible = ref(false)
const deleteModalVisible = ref(false)
const newBook = ref({})
const editBook = ref({})
const deleteBookId = ref(null)

// 新增一个变量来存储用户身份
const isAdmin = ref(false)

// 获取图书列表
const fetchBooks = async () => {
  const params = {
    currentPage: currentPage.value,
    pageSize: pageSize.value,
    keyword: searchKeyword.value,
    categoryId: selectedCategory.value
  }
  request.post('/book/list', params).then(res => {
    if (res.code === "200") {
      books.value = res.data.list;
      total.value = res.data.total
    } else {
      ElMessage.error(res.msg);
    }
  })
}

// 获取图书类别
const fetchCategories = async () => {
  request.get('/categories').then(res => {
    if (res.code === "200") {
      categories.value = res.data;
    } else {
      ElMessage.error(res.msg);
    }
  })
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchBooks()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchBooks()
}

// 页码改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchBooks()
}

// 显示新增图书模态框
const showAddModal = () => {
  newBook.value = {}
  addModalVisible.value = true
}

// 关闭新增图书模态框
const closeAddModal = () => {
  addModalVisible.value = false
}

// 显示编辑图书模态框
const showEditModal = (row) => {
  editBook.value = { ...row }
  editModalVisible.value = true
}

// 关闭编辑图书模态框
const closeEditModal = () => {
  editModalVisible.value = false
}

// 确认删除图书
const confirmDelete = (row) => {
  deleteBookId.value = row.id
  deleteModalVisible.value = true
}

// 关闭删除确认对话框
const closeDeleteModal = () => {
  deleteModalVisible.value = false
}

// 新增图书
const handleAddBook = () => {
  request.post('/book', newBook.value).then(res => {
    if (res.code === "200") {
      ElMessage.success('新增成功')
      closeAddModal()
      fetchBooks()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑图书
const handleEditBook = () => {
  request.put('/book', editBook.value).then(res => {
    if (res.code === "200") {
      ElMessage.success('编辑成功')
      closeEditModal()
      fetchBooks()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 删除图书
const handleDeleteBook = () => {
  request.delete(`/book/${deleteBookId.value}`).then(res => {
    if (res.code === "200") {
      ElMessage.success('删除成功')
      closeDeleteModal()
      fetchBooks()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 借阅图书
const handleBorrowBook = (row) => {
    // 检查用户是否有借阅中或超期未归还的订单
    request.get('/orders/checkBorrowStatus').then(res => {
        if (res.code === "200" && res.data.hasActiveOrder) {
            ElMessage.warning('您有借阅中的订单，无法再次借阅')
            return
        }
        // 弹出借阅时间选择框
        ElMessageBox.prompt('请输入借阅时间（最多7天）', '借阅', {
            inputPattern: /^[1-7]$/,
            inputErrorMessage: '借阅时间必须在1到7天之间',
        }).then(({ value }) => {
            const borrowTime = parseInt(value)
            const returnTime = new Date(Date.now() + borrowTime * 24 * 60 * 60 * 1000)
            request.post('/orders/borrow', { bookId: row.id, borrowTime, returnTime }).then(res => {
                if (res.code === "200") {
                    ElMessage.success('借阅成功')
                    fetchBooks()
                } else {
                    ElMessage.error(res.msg)
                }
            })
        })
    })
}

// 组件挂载时获取数据
onMounted(() => {
  fetchBooks()
  fetchCategories()
  
  // 获取用户身份
  const userInfo = localStorage.getItem('user')
  if (userInfo && JSON.parse(userInfo).username === 'admin') {
    isAdmin.value = true
  }
})
</script>

<style scoped>
.book-management {
  padding: 20px;
}
</style>