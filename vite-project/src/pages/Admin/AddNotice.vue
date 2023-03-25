<template>
  <div class="notice-container">
    <div class="notice-add" :class="isShow ? 'change':''">
      <div class="button-wrapper">
        <a-button type="primary" shape="round" @click="isShow = !isShow">
          <template #icon>
            <plus-circle-outlined />
          </template>
          <template v-if="isShow">
            发布评论
          </template>
        </a-button>
      </div>
      <div class="form-wrapper" v-if="isShow">
        <a-form
          :model="formState"
          name="basic"
          autocomplete="off"
          :hideRequiredMark="true"
          ref="noticeForm"
        >
          <a-form-item
            name="notContent"
            :rules="[{ required: true, message: '请输入公告内容！', trigger: ['blur','change']}]"
          >
            <a-textarea
              v-model:value="formState.notContent"
              placeholder="请不要超过限定的字数"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              show-count :maxlength="100"
            />
          </a-form-item>
          <a-form-item
            :colon="false"
            name="notLevel"
            :wrapperCol="{span: 15}"
          >
            <a-rate v-model:value="formState.notLevel" :tooltips="desc" />
            <span class="ant-rate-text">{{ desc[formState.notLevel - 1] }}</span>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="sendNotice" :loading="loading.sendButLoading">发送</a-button>
          </a-form-item>
      </a-form>
      </div>
       
    </div>
    <div class="notice-table">
      <a-table 
        :columns="columns" 
        :data-source="dataSource" 
        :pagination="paginations"
        @change="changePage"
      >
        <template #headerCell="{ column }">
          <span>
            {{column.title}}
          </span>
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'rootAccountNumber'">
              {{ record.root.rootAccountNumber }}
          </template>
          <template v-else-if="column.key === 'notLevel'">
            <span>
              <a-tag
                :color="colors[record.notLevel - 1]"
              >
                {{ desc[record.notLevel - 1] }}
              </a-tag>
            </span>
          </template>
          <template v-else-if="column.key === 'notUploadTime'">
            <span>
              <a-tooltip :title="record.notUploadTime.format('YYYY-MM-DD HH:mm:ss')">
                {{record.notUploadTime.fromNow()}}
              </a-tooltip>
            </span>
          </template>
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a-popconfirm placement="topRight" ok-text="是" cancel-text="否" @confirm="confirmEdit(record)">
                <template #title>
                  已经发布的公告只能修改公告等级，是否继续？
                </template>
                <a-button type="primary" ghost size="small" :style="{fontSize: '13px'}">编辑</a-button>
              </a-popconfirm>
              <a-popconfirm placement="bottom" ok-text="是" cancel-text="否" @confirm="confirmDel(record.notId)">
                <template #title>
                  是否删除此条公告？
                </template>
                <a-button type="primary" danger ghost size="small" :style="{fontSize: '13px'}">删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>

      <a-modal v-model:visible="visible" :width="300" title="修改">
        <template #footer>
          <a-button key="back" @click="visible = false">取消</a-button>
          <a-button key="submit" type="primary" :loading="loading.modalLoading" @click="handleOk">确定</a-button>
        </template>
        <div>
          <a-rate v-model:value="editNotice.notLevel" :tooltips="desc" />
          <span :style="{display: 'inline-block',marginLeft: '8px'}">{{ desc[editNotice.notLevel - 1] }}</span>
        </div>
      </a-modal>
      
    </div>
  </div>
</template>

<script setup>
  import { computed, getCurrentInstance, reactive, ref } from "vue";

  import {updateNoticeLevel, delNotice, getNoticesPage, addNotice} from '@/utils/request/api';
  import {MyNotification} from '@/utils/util';

  const { proxy } = getCurrentInstance()
  const isShow = ref(false)
  const loading = reactive({
    sendButLoading: false,
    modalLoading: false
  })

  const desc = ref(['信息通知', '提醒通知', '一般通知', '重要通知', '紧急通知'])
  const colors = ref(['blue','green','purple','orange','red'])

  let current = ref(1)
  let total = ref(10)
  let pageSize = ref(9)
  const paginations = computed(() => ({
    total: total.value,
    current: current.value,
    pageSize: pageSize.value,
    showSizeChanger: false,
    hideOnSinglePage: true
  }))
 
  const columns = [{
    title: '发布者',
    dataIndex: 'rootAccountNumber',
    key: 'rootAccountNumber',
  },{
    title: '公告内容',
    dataIndex: 'notContent',
    key: 'notContent',
    width: '60%'
  }, {
    title: '发布时间',
    dataIndex: 'notUploadTime',
    key: 'notUploadTime'
  }, {
    title: '公告等级',
    key: 'notLevel',
    dataIndex: 'notLevel',
  }, {
    title: '操作',
    key: 'action',
  }];
  const dataSource = ref([])
  const changePage = (paginations) => {
    current.value = paginations.current
    getAllNotice(paginations.current,paginations.pageSize)
  }

  // 存放需要编辑的notice对象
  const editNotice = ref({})
  // 存放编辑之前的公告等级
  const oldLevel = ref(0)
  const visible = ref(false)
  const confirmEdit = (notice) => {
    editNotice.value = {...notice}
    oldLevel.value = notice.notLevel
    visible.value = true

  }
  const handleOk = async() => {
    if(oldLevel === editNotice.value.notLevel) return;
    loading.modalLoading = true
 
    const data = await updateNoticeLevel(editNotice.value.notId,editNotice.value.notLevel)
    if(data > 0) {
      // 重新查询所有公告
      getAllNotice(current.value, pageSize.value)
      // 还原为初始值
      editNotice.value = {}
      oldLevel.value = 0
      loading.modalLoading = false
      visible.value = false
    }

  }
  const confirmDel = async(notId) => {

    const data = await delNotice(notId+'')
    if(data) {
      dataSource.value = dataSource.value.filter(item => !(item.notId === notId))
      MyNotification("success","删除成功！")
    }
  }

  const getAllNotice = async(page, size) => {

    const data = await getNoticesPage(page,size)
    if(data.list.length != 0) {
      total.value = data.total
      dataSource.value = data.list
      dataSource.value.forEach(item => {
        item.notUploadTime = proxy.$dayjs(item.notUploadTime)
      })
    }
  }

  getAllNotice(current.value, pageSize.value)

  // 管理员登录localStorage里存放的就是管理员信息
  const formState = ref({
    notContent: "",
    notLevel: 1,
  })
  const rootData = JSON.parse(localStorage.getItem('userData'));
  const noticeForm = ref(null)
  const sendNotice = async() => {

    loading.sendButLoading = true
    const data = await addNotice(rootData.rootId,formState.value.notContent,formState.value.notLevel)
    if(data != null) {
      noticeForm.value.resetFields()
      dataSource.value.push(data)
      loading.sendButLoading = false
    }else {
      console.log("添加公告错误",error);
    }

  }
</script> 

<style lang="less" scoped>
  .notice-container {
    display: flex;

    .notice-add {
      padding-right: 24px;
      .button-wrapper {
        text-align: left;
        padding: 16px 8px;
      }
      .form-wrapper {
        padding-right: 16px;
      }
    }

    .notice-add.change {
      width: 30%;
    }
    .notice-table {
      flex-grow: 1;
    }
  }
</style>