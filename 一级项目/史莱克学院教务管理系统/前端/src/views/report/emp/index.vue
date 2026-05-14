<script setup>
import { onMounted } from 'vue'
import * as echarts from 'echarts'
import { queryEmpJobDataApi, queryEmpGenderDataApi } from '@/api/report'

// 页面加载完成后初始化图表
onMounted(() => {
  loadJobChart()
  loadGenderChart()
})

// 获取职位统计数据并渲染柱状图
const loadJobChart = async () => {
  const result = await queryEmpJobDataApi()
  initJobChart(result.data.jobList, result.data.dataList)
}

// 获取性别统计数据并渲染饼图
const loadGenderChart = async () => {
  const result = await queryEmpGenderDataApi()
  initGenderChart(result.data)
}

// 职位统计柱状图
function initJobChart(jobList, dataList) {
  const myChart = echarts.init(document.getElementById('container1'))
  myChart.setOption({
    title: { text: '员工职位统计', textStyle: { fontSize: 20, color: '#6b46c1' }, left: 'center' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    tooltip: {},
    xAxis: { data: jobList, axisLabel: { color: '#555' }, axisLine: { lineStyle: { color: '#ddd' } } },
    yAxis: { axisLabel: { color: '#555' }, axisLine: { lineStyle: { color: '#ddd' } }, splitLine: { lineStyle: { color: '#eee' } } },
    series: [{
      name: '人数',
      type: 'bar',
      data: dataList,
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#9b59b6' },
          { offset: 1, color: '#6b46c1' }
        ]),
        borderRadius: [6, 6, 0, 0]
      }
    }]
  })
}

// 性别统计饼图
function initGenderChart(genderDataList) {
  const myChart = echarts.init(document.getElementById('container2'))
  myChart.setOption({
    title: { text: '员工性别统计', textStyle: { fontSize: 20, color: '#6b46c1' }, left: 'center' },
    tooltip: { trigger: 'item' },
    legend: { top: '10%', left: 'center', textStyle: { color: '#555' } },
    color: ['#6b46c1', '#f093fb'],
    series: [{
      name: '性别',
      type: 'pie',
      radius: ['40%', '70%'],
      itemStyle: { borderRadius: 8, borderColor: '#fff', borderWidth: 3 },
      label: { show: false },
      emphasis: { label: { show: true, fontSize: 22, fontWeight: 'bold', color: '#6b46c1' } },
      data: genderDataList
    }]
  })
}
</script>

<template>
  <div class="report-wrapper">
    <div class="report-container" id="container1"></div>
    <div class="report-container" id="container2"></div>
  </div>
</template>

<style scoped>
.report-wrapper {
  display: flex;
  width: 100%;
  height: calc(100vh - 80px);
  gap: 10px;
  padding: 10px;
}

.report-container {
  flex: 1;
  height: 100%;
  min-width: 400px;
}

#container1 {
  border-right: 1px dashed #ccc;
  padding-right: 10px;
}
</style>