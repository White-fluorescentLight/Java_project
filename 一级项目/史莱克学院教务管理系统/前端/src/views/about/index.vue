<template>
  <!-- HTML结构 -->
  <div class="about-container">
    <!-- 背景图片 -->
    <div class="bg-image"></div>
    <!-- 内容区域 -->
    <div class="content">
      <h2>关于系统</h2>
      <p>欢迎使用史莱克学院教务管理系统！</p>
      <p>特别鸣谢！</p>
      <p>QuanWei</p>
      <p>LingMa-Trae</p>
      <p>bilibili-iTheima</p>
      <p>版本：v26.5.13</p>
      <p>开发团队：史莱克学院技术部</p>
    </div>
  </div>
</template>


<style scoped>
/* 容器样式 */
.about-container {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}

/* 背景图片 */
.bg-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../../assets/img3.png');
  background-size: cover;
  background-position: center;
}

/* 内容区域 */
.content {
  position: relative;
  z-index: 10;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
  border-radius: 10px;
  margin: 50px;
  max-width: 600px;
}

.content h2 {
  color: #333;
  margin-bottom: 20px;
}

.content p {
  color: #666;
  line-height: 1.8;
  margin-bottom: 10px;
}
</style>