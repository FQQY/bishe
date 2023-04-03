<template>
  <div class="system-wrapper">
    <div ref="user" class="user"></div>
    <div ref="video" class="video"></div>
    <div ref="work" class="work"></div>
  </div>
</template>

<script setup>
  import { computed, getCurrentInstance, onBeforeMount, onMounted, reactive, ref } from 'vue';

  import * as echarts from 'echarts';
  import {getData} from '@/utils/request/http';


  onMounted(() => {
    userInit()
    getAllUser()

    videoInit()
    getAllVideo()

    // workEcharts.workInit()
  })

  const user = ref()
  const userList = ref([])
  let userChart
  const userChartOption = (param=[]) => userChart.setOption({
      title: {
        text: '用户状态饼状图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          // name: ['注销',"封禁","正常"],
          type: 'pie',
          radius: ['40%', '70%'],
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          data:param,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    })
  const getAllUser = async() => {
    const data = await getData("/query/users")
    userList.value = data
    // 注销用户个数
    let delUserCount = data.filter(user => user.delFlag === "1").length
    // 封禁用户个数
    let banUserCount = data.filter(user => user.banFlag === "1").length
    // 其他用户个数
    let normalUserCount = data.length-delUserCount-banUserCount
    userChartOption([
      { value: delUserCount, name: '注销' },
      { value: banUserCount, name: '封禁' },
      { value: normalUserCount, name: '正常' }
    ])
  }
  async function userInit(option) {
    userChart = echarts.init(user.value);

    userChartOption() // 绘制图表

    window.onresize = function () { //自适应大小
      userChart.resize();
    };
  }

  const video = ref()
  const videoListGroupByDate = ref([])
  let videoChart 
  const videoChartOption = (param=[]) => videoChart.setOption({
    title: {
      text: '上传作品时间分布表',
      x: 'center'
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      data: [...param.map(item => item.name),],
      min: 1
    },
    yAxis: {
      type: 'value',
      boundaryGap: [0, '100%'],
      splitLine: {
        show: false
      }
    },
    series: [
      {
        name: '发布的作品数量',
        type: 'line',
        showSymbol: false,
        data: param
      }
    ]
  })
  const getAllVideo = async() => {
    const data = await getData("/query/all/works")
    // 将视频根据上传日期进行分类
    data.forEach(item => item.workUploadTime = item.workUploadTime.slice(0,10))
    videoListGroupByDate.value = groupByDate(data)
    // 装配到数据中 发送给echarts
    let param = []
    // 先对所有作品按照时间排序
    videoListGroupByDate.value.sort((a,b) => new Date(a.workUploadTime) - new Date(b.workUploadTime))
    videoListGroupByDate.value.forEach(item => {
      param.push({ name: item.workUploadTime, value: [item.workUploadTime, item.List.length]})
    })
    console.log("param",param);
    videoChartOption(param)
  }

  // 视频图标的初始化
  async function videoInit(option) {
    videoChart = echarts.init(video.value);

    videoChartOption() // 绘制图表

    window.onresize = function () { //自适应大小
      videoChart.resize();
    };
  }

  //
  const work = ref()
  let workChart
  const workEcharts = {
    workChartOption(){
      workChart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['视频','文章'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52]
          }
        ]
      })
    },
    workInit() {
      workChart = echarts.init(work.value);

      this.workChartOption() // 绘制图表

      window.onresize = function () { //自适应大小
        workChart.resize();
      };
    }
  }


  // 根据上传时间给请求获得的视频分类
  function groupByDate(list) {
    let dataArr = []
    list.map( mapItem => {
      if(dataArr.length === 0) {
        dataArr.push({workUploadTime: mapItem.workUploadTime, List: [mapItem]})
      } else {
        let res = dataArr.some( item => {
          if(mapItem.workUploadTime === item.workUploadTime) {
            item.List.push(mapItem)
            return true
          }
        })

        if(!res) {
          dataArr.push({workUploadTime: mapItem.workUploadTime, List: [mapItem]})
        }
      }
    })

    return dataArr
  }

</script>

<style lang="less" scoped>
  .system-wrapper {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 1fr 1fr;
    .user {
      height: 100%;
      padding: 8px;
    }
    .video {
      height: 100%;
      padding: 8px;
    }
  }
</style>