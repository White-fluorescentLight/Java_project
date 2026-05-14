<script setup>
// 导入依赖
import { ref, onMounted } from 'vue'
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi } from "@/api/dept"
import { ElMessage, ElMessageBox } from 'element-plus'

// 部门列表
const deptList = ref([])

// 页面初始化
onMounted(() => search())

// 查询部门列表
const search = async () => {
  const result = await queryAllApi()
  if (result.code) deptList.value = result.data
}

// ============ 新增/修改 ============
let dialogFormVisible = ref(false)
let formTitle = ref('')
const deptFormRef = ref()

// 表单数据
const dept = ref({ name: '' })

// 新增部门
const addDept = () => {
  dialogFormVisible.value = true
  formTitle.value = '新增部门'
  dept.value = { name: '' }
  deptFormRef.value && deptFormRef.value.resetFields()
}

// 编辑部门
const edit = async (id) => {
  formTitle.value = '编辑部门'
  const result = await queryByIdApi(id)
  if (result.code) {
    dialogFormVisible.value = true
    dept.value = result.data
  }
}

// 表单校验规则
const rules = ref({
  name: [
    { required: true, message: '部门名称是必填项', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称长度应在2-10位', trigger: 'blur' }
  ]
})

// 保存
const save = async () => {
  if (!deptFormRef.value) return
  deptFormRef.value.validate(async (valid) => {
    if (valid) {
      const api = dept.value.id ? updateApi(dept.value) : addApi(dept.value)
      const result = await api
      if (result.code) {
        ElMessage.success('操作成功')
        dialogFormVisible.value = false
        search()
      } else {
        ElMessage.error(result.msg)
      }
    }
  })
}

// ============ 删除 ============
const deleteById = async (id) => {
  ElMessageBox.confirm('确认删除该部门?', '提示', {
    confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning'
  }).then(async () => {
    const result = await deleteByIdApi(id)
    if (result.code) {
      ElMessage.success('删除成功')
      search()
    } else {
      ElMessage.error(result.msg)
    }
  }).catch(() => ElMessage.info('已取消删除'))
}
</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept"> + 新增部门</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="300" align="center" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- Dialog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
  <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>