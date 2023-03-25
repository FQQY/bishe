<template>
  <div class="ask-wrapper">
    <a-table
      class="ant-table-striped"
      :columns="columns" 
      :data-source="dataSource" 
      :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
      :pagination="pagination"
      @change="handleChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'usrSex'">
          {{ record.usrSex === 0 ? '男':'女'}}
        </template>
        <template v-else-if="column.key === 'usrRegisterTime'">
          <a-tooltip :title="record.usrRegisterTime.format('YYYY-MM-DD HH:mm:ss')">
            {{record.usrRegisterTime.fromNow()}}
          </a-tooltip>
        </template>
        <template v-else-if="column.key === 'askTime'">
          <a-tooltip :title="record.askTime.format('YYYY-MM-DD HH:mm:ss')">
            {{record.askTime.fromNow()}}
          </a-tooltip>
        </template>
        <template v-if="column.key === 'action'">
           <a-space>
            <a-popconfirm
              v-if="record.delFlag != '1'" 
              title="同意请求？"
              @confirm="handleAgree(record.usrId)"
            >
              <a-button
                type="primary"
                size="small"
                :style="{fontSize: '12px'}"
              >
                同意
              </a-button>
            </a-popconfirm>
            <a-popconfirm
              v-if="record.delFlag != '1'" 
              title="拒绝请求？"
              @confirm="handleRefuse(record.usrId)"
            >
              <a-button
                type="primary"
                size="small"
                :style="{fontSize: '12px'}"
                danger
              >
                拒绝
              </a-button>
            </a-popconfirm>
            
           </a-space>
        </template>

      </template>

    </a-table>
  </div>
</template>

<script setup>
  import axios from "axios"
  import { computed, getCurrentInstance, ref } from "vue"

  import {getUserAskList, agreeUserAsk, refuseUserAsk} from '@/utils/request/api';

  const columns = [
    {
      title: '昵称',
      dataIndex: 'usrName',
      key: 'usrName',
    }, {
      title: '性别',
      dataIndex: 'usrSex',
      key: 'usrSex',
    }, {
      title: '邮箱',
      dataIndex: 'usrEmail',
      key: 'usrEmail',
    }, {
      title: '注册时间',
      dataIndex: 'usrRegisterTime',
      key: 'usrRegisterTime',
    },{
      title: '申请时间',
      dataIndex: 'askTime',
      key: 'askTime',
    },
    {
      title: '操作',
      key: 'action',
    }
  ]

  const { proxy } = getCurrentInstance()
  const dataSource = ref([])

  let total = ref(0)
  let current = ref(1)
  let pageSize = ref(9)
  const pagination = computed(() => ({
    total: total.value,
    current: current.value,
    pageSize: pageSize.value,
    showSizeChanger: false,
    hideOnSinglePage : true
  }))

  const getUserAsk = async(page, size) => {
    const data = await getUserAskList(page,size)
    if(data.list.length != 0) {
      total.value = data.total
      dataSource.value = data.list
      dataSource.value.forEach(item => {
        item.usrRegisterTime = proxy.$dayjs(item.usrRegisterTime)
        item.askTime = proxy.$dayjs(item.askTime)
      })
    }
  }
  getUserAsk(current.value, pageSize.value)

  const handleChange = (pagination) => {
    current.value = pagination.current
    getUserAsk(current.value, pageSize.value)
  }

  const handleAgree = async(usrId) => {
 
    const data = await agreeUserAsk(usrId)
    if(data) {
      dataSource.value.splice(dataSource.value.findIndex(item => item.usrId === usrId),1)
      proxy.$message.success("该用户的权限以上升为审核员")
    }
  }
  const handleRefuse = async(usrId) => {
 
    const data = await refuseUserAsk(usrId)
    if(data) {
      dataSource.value.splice(dataSource.value.findIndex(item => item.usrId === usrId),1)
      proxy.$message.error("已拒绝该用户的请求")
    }

  }

</script>

<style lang="less" scoped>
  .ant-table-striped :deep(.table-striped) td {
    background-color: #fafafa;
  }
</style>