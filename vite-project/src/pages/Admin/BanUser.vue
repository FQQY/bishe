<template>
  <div class="banuser-wrapper">
    <a-table 
      :columns="columns" 
      :data-source="dataSource" 
      :pagination="pagination"
      @change="handleChange"
    >
      <template #bodyCell="{ column, record }">
       
        <template v-if="column.key === 'status'">
          <a-tag color="red" v-if="record.delFlag === '1'">已注销</a-tag>
          <a-tag color="purple" v-else-if="record.banFlag === '1'">已封禁</a-tag>
          <a-tag color="green" v-else>正常</a-tag>
        </template>
        <template v-else-if="column.key === 'usrSex'">
          {{ record.usrSex === 0 ? '男':'女'}}
        </template>
        <template v-else-if="column.key === 'usrAuthority'">
          {{ record.usrAuthority === '0' ? '普通用户':'审核员'}}
        </template>
        <template v-else-if="column.key === 'usrRegisterTime'">
          <a-tooltip :title="record.usrRegisterTime.format('YYYY-MM-DD HH:mm:ss')">
            {{record.usrRegisterTime.fromNow()}}
          </a-tooltip>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space>
            <a-popconfirm
              v-if="record.delFlag != '1'" 
              title="Sure to delete?"
              @confirm="handleDel(record.usrId)"
            >
              <a-button
                type="primary" 
                size="small" 
                :style="{fontSize: '12px'}"
                danger
              >注销</a-button>
            </a-popconfirm>
            <a-popconfirm
              v-if="record.delFlag != '1' && record.banFlag === '0'"
              title="确定封禁此用户?"
              @confirm="handleBan(record,1)"
            >
              <a-button
                type="primary" 
                size="small" 
                :style="{fontSize: '12px'}" 
                danger 
                ghost 
              >封禁</a-button>
            </a-popconfirm>
            <a-popconfirm
              v-if="record.delFlag != '1' && record.banFlag === '1'"
              title="确定解封此用户?"
              @confirm="handleBan(record,0)"
            >
              <a-button
                type="primary" 
                size="small" 
                :style="{fontSize: '12px'}"
                ghost 
              >解禁</a-button>
            </a-popconfirm>

          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup>
  import { getCurrentInstance, ref, computed } from "vue";

  import {getUsersPage, updateUserInfo, delUser} from '@/utils/request/api';

  const { proxy }  = getCurrentInstance()
  const columns = [{
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
    key: 'usrRegisterTime',
    dataIndex: 'usrRegisterTime',
  }, {
    title: '状态',
    key: 'status',
    dataIndex: 'status',
  }, {
    title: '身份',
    key: 'usrAuthority',
    dataIndex: 'usrAuthority',
  }, {
    title: '操作',
    key: 'action',
  }];
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
  const dataSource = ref([]);

  function handleChange(pagination) {
    getAllUserByPage(pagination.current, pagination.pageSize)
    current.value = pagination.current
  }

  const getAllUserByPage = async(page, size) => {
 
    const data = await getUsersPage(page,size)
    if(data.list) {
      total.value = data.total
      dataSource.value = data.list
      dataSource.value.forEach( item => item.usrRegisterTime = proxy.$dayjs(item.usrRegisterTime))
    }

  }
  getAllUserByPage(current.value,pageSize.value)

  // flag 0 表示解禁操作 1 代表封禁操作
  const handleBan = async function(record,flag) {
    console.log("准备封禁的用户id",record.usrId);
    
    // 获取到被封禁的用户的详细信息
    const data = await updateUserInfo({
      usrId: record.usrId,
      banFlag: flag+''
    })
    if(data != null) {
      dataSource.value.forEach(item => {
        if(item.usrId === record.usrId) {
          item.banFlag = flag+''
        }
      })
      proxy.$message.success(`该用户已被${flag === 0? '解封':'封禁'}！`)
    }
 
  }

  const handleDel = async function(usrId) {
    console.log("准备注销的用户id",usrId);
  
    const data = await delUser(usrId)
    if(data) {
      dataSource.value.forEach( item => {
        if(item.usrId === usrId) {
          item.delFlag = "1"
        }
      })
      proxy.$message.success("删除成功")
    }

  }
</script>

<style lang="less" scoped>

</style>