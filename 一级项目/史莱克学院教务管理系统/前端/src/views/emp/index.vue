<script setup>
// 导入依赖
import { ref, watch, onMounted } from 'vue'
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi } from "@/api/emp"
import { queryAllApi } from "@/api/dept"
import { ElMessage, ElMessageBox } from 'element-plus'

// 下拉选项
const jobs = ref([
  { name: '班主任', value: 1 }, { name: '讲师', value: 2 },
  { name: '学工主管', value: 3 }, { name: '教研主管', value: 4 },
  { name: '咨询师', value: 5 }, { name: '其他', value: 6 }
])
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
const depts = ref([])

// 搜索条件
const searchEmp = ref({ name: '', gender: '', date: [], begin: '', end: '' })

// 监听日期范围
watch(() => searchEmp.value.date, (newVal) => {
  if (newVal.length === 2) {
    searchEmp.value.begin = newVal[0]
    searchEmp.value.end = newVal[1]
  } else {
    searchEmp.value.begin = ''
    searchEmp.value.end = ''
  }
})

// 页面初始化
onMounted(() => {
  search()
  queryAllDepts()
  getToken()
})

// 获取token（用于图片上传）
const token = ref('')
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser && loginUser.token) token.value = loginUser.token
}

// 获取部门列表
const queryAllDepts = async () => {
  const result = await queryAllApi()
  if (result.code) depts.value = result.data
}

// 表格数据
const empList = ref([])

// 分页配置
const currentPage = ref(1)
const pageSize = ref(8)
const background = ref(true)
const total = ref(0)

// 查询列表
const search = async () => {
  const result = await queryPageApi(
    searchEmp.value.name, searchEmp.value.gender,
    searchEmp.value.begin, searchEmp.value.end,
    currentPage.value, pageSize.value
  )
  if (result.code) {
    empList.value = result.data.rows
    total.value = result.data.total
  }
}

// 清空搜索
const clear = () => {
  searchEmp.value = { name: '', gender: '', date: [], begin: '', end: '' }
  search()
}

// 分页事件
const handleSizeChange = () => search()
const handleCurrentChange = () => search()

// ============ 新增/修改 ============
let dialogVisible = ref(false)
let dialogTitle = ref('新增员工')
const empFormRef = ref()

// 表单数据
const employee = ref({
  username: '', name: '', gender: '', phone: '', job: '',
  salary: '', deptId: '', entryDate: '', image: '', exprList: []
})

// 新增员工
const addEmp = () => {
  dialogVisible.value = true
  dialogTitle.value = '新增员工'
  employee.value = {
    username: '', name: '', gender: '', phone: '', job: '',
    salary: '', deptId: '', entryDate: '', image: '', exprList: []
  }
  empFormRef.value && empFormRef.value.resetFields()
}

// 编辑员工
const edit = async (id) => {
  const result = await queryInfoApi(id)
  if (result.code) {
    dialogVisible.value = true
    dialogTitle.value = '修改员工'
    employee.value = result.data
    // 处理工作经历日期
    const exprList = employee.value.exprList
    if (exprList && exprList.length > 0) {
      exprList.forEach(expr => expr.exprDate = [expr.begin, expr.end])
    }
  }
}

// 图片上传
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data
}
const beforeAvatarUpload = (rawFile) => {
  if (!['image/jpeg', 'image/png'].includes(rawFile.type)) {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}

// 工作经历
const addExprItem = () => {
  employee.value.exprList.push({ company: '', job: '', begin: '', end: '', exprDate: [] })
}
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}

// 监听工作经历变化
watch(() => employee.value.exprList, () => {
  if (employee.value.exprList?.length > 0) {
    employee.value.exprList.forEach(expr => {
      expr.begin = expr.exprDate[0]
      expr.end = expr.exprDate[1]
    })
  }
}, { deep: true })

// 表单校验
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2-20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2-10个字符之间', trigger: 'blur' }
  ],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
})

// 保存
const save = async () => {
  if (!empFormRef.value) return
  empFormRef.value.validate(async (valid) => {
    if (valid) {
      const api = employee.value.id ? updateApi(employee.value) : addApi(employee.value)
      const result = await api
      if (result.code) {
        ElMessage.success('保存成功')
        dialogVisible.value = false
        search()
      } else {
        ElMessage.error(result.msg)
      }
    }
  })
}

// ============ 删除 ============
const selectedIds = ref([])
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 删除单个
const deleteById = (id) => {
  ElMessageBox.confirm('确认删除该员工?', '提示', {
    confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning'
  }).then(async () => {
    const result = await deleteApi(id)
    if (result.code) {
      ElMessage.success('删除成功')
      search()
    } else {
      ElMessage.error(result.msg)
    }
  }).catch(() => ElMessage.info('已取消删除'))
}

// 批量删除
const deleteByIds = () => {
  ElMessageBox.confirm('确认删除选中员工?', '提示', {
    confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning'
  }).then(async () => {
    if (selectedIds.value.length > 0) {
      const result = await deleteApi(selectedIds.value)
      if (result.code) {
        ElMessage.success('删除成功')
        search()
      } else {
        ElMessage.error(result.msg)
      }
    } else {
      ElMessage.info('请选择要删除的数据')
    }
  }).catch(() => ElMessage.info('已取消删除'))
}
</script>

<template>
  <h1>员工管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" />
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择">
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>

      <el-form-item label="入职时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+ 新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column
        prop="name"
        label="姓名"
        width="120"
        align="center"
      />

      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>

      <el-table-column label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" class="avatar-circle" />
        </template>
      </el-table-column>


      <el-table-column
        prop="deptName"
        label="所属部门"
        width="120"
        align="center"
      />

      <el-table-column prop="job" label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job === 1">班主任</span>
          <span v-else-if="scope.row.job === 2">讲师</span>
          <span v-else-if="scope.row.job === 3">学工主管</span>
          <span v-else-if="scope.row.job === 4">教研主管</span>
          <span v-else-if="scope.row.job === 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="entryDate"
        label="入职日期"
        width="180"
        align="center"
      />

      <el-table-column
        prop="updateTime"
        label="最后操作时间"
        width="200"
        align="center"
      />

      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)">
            <el-icon><EditPen /></el-icon> 编辑
          </el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">
            <el-icon><Delete /></el-icon> 删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页条 -->
 <div class="container">
  <el-pagination
    v-model:current-page="currentPage"
    v-model:page-size="pageSize"
    :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
    :background="background"
    layout="total, sizes, prev, pager, next, jumper"
    :total="total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
 </div>
  <!-- 新增/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
      <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
        <!-- 基本信息 -->
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token': token}"
                :show-file-list="false" 
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="employee.image" :src="employee.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        

        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3" v-for="expr in employee.exprList">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.exprData" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      
      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
   </el-dialog>
</template>

<style scoped>
.container {
  margin: 20px 0;
}

.avatar {
  height: 40px;
  border-radius: 50%;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
  border-radius: 50%;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}

.avatar-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
</style>