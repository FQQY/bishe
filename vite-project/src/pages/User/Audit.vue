<template>
  <div class="audit-wrapper">
    <div class="table-container">
      <a-table 
        class="ant-table-striped"
        :columns="columns" 
        :data-source="dataSource"
        :pagination="false"
        :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
      >
        <template #headerCell="{ column }">
          <template v-if="column.key === 'workPhoto'">
            <span>
              <picture-outlined />
              {{column.name}}
            </span>
          </template>
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'workPhoto'">
            <a-image :src="record.workPhoto"  :height="80" placeholder/>
          </template>
 
          <template v-else-if="column.key === 'action'">
            <a-space :size="10">
              <a-popconfirm
                v-if="dataSource.length"
                title="确定通过吗？"
                @confirm="onPass(record.workId)"
              >
                <a-button type="normal" >通过</a-button>
              </a-popconfirm>
              <a-popconfirm
                v-if="dataSource.length"
                title="确定拒绝吗？"
                @confirm="onNoPass(record.workId)"
              >
                <a-button type="primary" danger >拒绝</a-button>
              </a-popconfirm>
            </a-space>
          </template>
          <template v-else-if="column.key === 'workUploadTime'">
            <a-tooltip :title="record.workUploadTime.format('YYYY-MM-DD HH:mm:ss')">
              {{record.workUploadTime.fromNow()}}
            </a-tooltip>
          </template>
        </template>
      </a-table>
    </div>
    <div class="next">
      <a-popconfirm
        title="继续工作？"
        @confirm="nextPage()"
      >
        <a-button type="normal" danger>下一批</a-button>
      </a-popconfirm>
    </div>
  </div>
</template>

<script setup>
  import axios from 'axios';
  import { getCurrentInstance, onBeforeMount, ref } from 'vue';


  const columns = [{
    name: '视频封面',
    dataIndex: 'workPhoto',
    key: 'workPhoto',
    align: "center",
    width: "20%"
  }, {
    title: '标题',
    dataIndex: 'workTitle',
    key: 'workTitle',
    ellipsis: true,
    width: "40%"
  }, {
    title: '发布时间',
    dataIndex: 'workUploadTime',
    key: 'workUploadTime',
  },
  {
    title: '操作',
    key: 'action',
    align: "center"
  }];
  const { proxy } = getCurrentInstance()
  let dataSource = ref([])
  const current = ref(1)
  const pageSize = ref(5)

  const onPass = (workId) => {
    updateWorkPassStatus(workId,"1");
    console.log(workId);
  }
  const onNoPass = (workId) => {
    console.log(workId);
    updateWorkPassStatus(workId,"2");
  }

  const nextPage = () => {
    if(dataSource.value.length != 0) {
      proxy.$message.error("请完成当前的工作！")
    }else {
      current.value++
      getAllNoPassVideos()
      if(dataSource.value.length == 0) {
        proxy.$message.info("暂时没有新的任务")      
      }else {
        proxy.$message.success("您的任务已到达！")
      }
      
    }
   
  }

  const updateWorkPassStatus = async(workId, passFlag) => {
    try {
      const res = await axios.post('/demo/update/work/pass/status',{
        workId,
        passFlag
      })
      if(res.status === 200 && res.data > 0) {
        dataSource.value = dataSource.value.filter( item => !(item.workId===workId))
      }
    } catch (error) {
      console.log("更新视频状态失败:", error);
    }
  }

  const getAllNoPassWorks = async() => {
    try {
      const res = await axios.get(`/demo/query/nopassworks?page=${current.value}&size=${pageSize.value}`);
      console.log(res);
      dataSource.value = res.data.list
      dataSource.value.forEach(item => {
        item.workUploadTime = proxy.$dayjs(item.workUploadTime)
      })
    } catch (error) {
      console.log("请求未审核的视频列表失败：",error);
    }
  }
  getAllNoPassWorks()

  
</script>

<style lang="less" scoped>

  .audit-wrapper {
    padding: 2rem;

    .table-container {
      padding: 0 15rem;
    }
  }

  .next {
    position: absolute;
    top: 50%;
    right: 2rem;
    transform: translateX(-50%);
  }

  :deep(.ant-image-img) {
    height: 100%;
    width: 100%;
    border-radius: 5px;
  }
  :deep(.ant-image-img) {
    background-color: #000;
  }
  .ant-table-striped :deep(.table-striped) td {
    background-color: #fafafa;
  }
</style>