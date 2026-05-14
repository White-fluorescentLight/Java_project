<script setup>
// 导入依赖
import { ref } from 'vue'
import { loginApi } from '@/api/login'  // 登录接口
import { ElMessage } from 'element-plus'  // 消息提示
import { useRouter } from 'vue-router'  // 路由跳转

// 登录表单数据
const loginForm = ref({
  username: '',  // 用户名
  password: ''   // 密码
})
const router = useRouter()  // 路由实例

// 登录函数
const login = async () => {
  try {
    const result = await loginApi(loginForm.value)
    if (result.code) {
      ElMessage.success(`欢迎`)
      localStorage.setItem('loginUser', JSON.stringify(result.data))  // 存储登录信息
      router.push('/index')  // 跳转到首页
    } else {
      ElMessage.error(result.msg || '登录失败')
    }
  } catch (error) {
    ElMessage.error('网络异常或服务未启动')
  }
}

// 重置表单
const clear = () => {
  loginForm.value = { username: '', password: '' }
}
</script>

<template>
  <!-- 页面容器 -->
  <div id="container">
    <!-- 登录对话框 -->
    <div class="login-box">
      <!-- 登录表单 -->
      <el-form label-width="80px">
        <!-- 标题 -->
        <p class="title">史莱克学院教务系统</p>

        <!-- 用户名输入框 -->
        <el-form-item label="用户名">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" />
        </el-form-item>

        <!-- 密码输入框 -->
        <el-form-item label="密码">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" />
        </el-form-item>

        <!-- 按钮组 -->
        <el-form-item>
          <el-button type="primary" class="btn" @click="login">登 录</el-button>
          <el-button type="info" class="btn" @click="clear">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
/* 页面容器样式 - 全屏背景 */
#container {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  overflow: hidden;
  background-image: url('../../assets/img2.png');  /* 背景图片 */
  background-repeat: no-repeat;
  background-size: cover;
}

/* 登录对话框样式 - 浅紫半透风 */
.login-box {
  position: relative;
  z-index: 10;
  max-width: 400px;
  margin: 18% auto;  /* 居中定位 */
  padding: 30px;
  background-color: rgba(245, 235, 250, 0.85);
  border: 1px solid rgba(200, 150, 230, 0.5); 
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(180, 100, 220, 0.3); 
}

/* 标题样式 */
.title {
  font-size: 28px;
  text-align: center;
  margin-bottom: 25px;
  font-weight: bold;
}

/* 按钮基础样式 */
.btn {
  width: 120px;
  margin-top: 20px;
}

/* 登录按钮 - 紫色 */
.login-box :deep(.el-button--primary) {
  /* !important 是 CSS 中的一个特殊标记，用于强制应用样式，覆盖其他冲突的样式。它会提高样式的优先级 */
  background-color: #9b59b6 !important;
  border-color: #9b59b6 !important;
}

.login-box :deep(.el-button--primary:hover) {
  background-color: #8e44ad !important;
  border-color: #8e44ad !important;
}

/* 重置按钮 - 浅紫色 */
.login-box :deep(.el-button--info) {
  background-color: rgba(200, 150, 230, 0.3) !important;
  border-color: rgba(200, 150, 230, 0.5) !important;
  color: #666 !important;
}

.login-box :deep(.el-button--info:hover) {
  background-color: rgba(200, 150, 230, 0.5) !important;
  border-color: rgba(200, 150, 230, 0.7) !important;
}
</style>