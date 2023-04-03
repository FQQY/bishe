<template>
  <div class="upload-wrapper">
    <div class="upload-content">
      <UploadCom></UploadCom>
    </div>
    <div class="upload-video-container">
      <div class="table-container">
        <a-table 
          class="ant-table-striped"
          :columns="columns" 
          :data-source="dataSource" 
          :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
          :pagination="pagination"
          @change="changePage"
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
              <a-image :src="record.workPhoto" :width="150" :height="90" placeholder/>
            </template>
            <template v-else-if="column.key === 'passFlag'">
              <span>
                <a-tag v-show="record.passFlag === '0'" color="orange">
                  审核中
                </a-tag>
                <a-tag v-show="record.passFlag === '1'" color="green">
                  已发布
                </a-tag>
                <a-tag v-show="record.passFlag === '2'" color="red">
                  未通过
                </a-tag>
              </span>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-popconfirm
                v-if="dataSource.length"
                title="Sure to delete?"
                @confirm="onDelete(record.workId)"
              >
                <a-button type="text" danger size="small">删除</a-button>
              </a-popconfirm>
            </template>
            <template v-else-if="column.key === 'workUploadTime'">
              <a-tooltip :title="record.workUploadTime.format('YYYY-MM-DD HH:mm:ss')">
                {{record.workUploadTime.fromNow()}}
              </a-tooltip>
            </template>
          </template>
        </a-table>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, computed, getCurrentInstance } from "vue";

  import { delVideo, getUserUploadVideos } from '@/utils/request/api';
  import UploadCom from "@/components/UploadCom.vue"

  const columns = [{
    name: '视频封面',
    dataIndex: 'workPhoto',
    key: 'workPhoto',
    align: "center",
    width: 180
  }, {
    title: '标题',
    dataIndex: 'workTitle',
    key: 'workTitle',
    ellipsis: true,
    width: 300
  }, {
    title: '发布时间',
    dataIndex: 'workUploadTime',
    key: 'workUploadTime',
  }, {
    title: '状态',
    key: 'passFlag',
    dataIndex: 'passFlag',
  }, {
    title: '操作',
    key: 'action',
  }];
  let dataSource = ref([])
  const { proxy } = getCurrentInstance()
  const userData = JSON.parse(localStorage.getItem('userData'))

  let current = ref(1)
  let total = ref(10)
  let pageSize = ref(5)
  const pagination = computed(() => ({
    total: total.value,
    current: current.value,
    pageSize: pageSize.value,
    showSizeChanger: false,
    hideOnSinglePage : true
  }))

  const changePage = (pagination) => {
    current.value = pagination.current;
    getUploadVideos(current.value, pageSize.value)
    console.log(pagination);
  }


  const onDelete = async workId => {
    if(userData.banFlag === '1') {
      MyNotification("error","警告","您的账号已经被封禁，不能删除已经上传的作品")
      return
    }
    console.log(workId);
    const data = await delVideo(userData.usrId,workId)
    if( data > 0) {
      dataSource.value = dataSource.value.filter(item => item.workId !== workId);
    }
  };


  const getUploadVideos = async(page,size) => {
    const data = await getUserUploadVideos(userData.usrId,page,size)
    
    total.value = data.total
    dataSource.value = data.list

    dataSource.value.map(item => {
      let uploadTime = item.workUploadTime
      item.workUploadTime = proxy.$dayjs(uploadTime)
    })
  }


  getUploadVideos(current.value, pageSize.value)

</script>

<style lang="less" scoped>
  .upload-wrapper {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

    padding: 0 2rem;
    .upload-content {
      background-color: #fff;
      width: 40%;
      height: 100%;
    }


    .upload-video-container {
      background-color: #fff;
      width: 60%;
      height: 100%;
    }
  }

  .table-container {
    width: 100%;
    height: 100%;
    padding: 1rem;
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