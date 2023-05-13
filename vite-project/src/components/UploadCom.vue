<template>
  <div class="upload-container">
    <a-upload-dragger
      v-model:fileList="fileList"
      :before-upload="beforeUpload"
      :maxCount="1"
      listType="picture"
      name="file"
      @change="handleChange"
    >
      <div>
        <p class="ant-upload-drag-icon">
          <inbox-outlined></inbox-outlined>
        </p>
        <p class="ant-upload-text">单击或拖动文件到此区域以上传</p>
        <p class="ant-upload-hint">
          支持单个上传。严禁上传公司数据或其他带状文件
        </p>
      </div>
    </a-upload-dragger>


    <a-modal v-model:visible="visible" title="上传视频">
      <template #footer>
        <a-button key="back" @click="handleReset">重置</a-button>
        <a-button key="submit" type="primary" :loading="uploading" @click="handleUpload">
          {{ uploading ? '上传中' : '上传' }}
        </a-button>
      </template>
      <a-form
        ref="formRef"
        :model="formState"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 18 }"
        autocomplete="off"
      >
        <a-form-item
          label="标题"
          name="workTitle"
        >
          <a-input 
            v-model:value="formState.workTitle" 
            placeholder="标题"
          />
        </a-form-item>

        <a-form-item
          label="内容"
          name="workContent"
        >
          <a-textarea 
            v-model:value="formState.workContent" 
            show-count :maxlength="100" 
            placeholder="请填写详细信息" 
            allow-clear 
          />
        </a-form-item>

        <a-form-item
          label="分类"
          name="category"
        >
          <a-select
            ref="select"
            v-model:value="formState.catId"
            style="width: 120px"
          >
            <a-select-option value="default">--请选择--</a-select-option>
            <a-select-option 
              v-for="category in categories" 
              :key="category.catId" 
              :value="category.catId"
            >
              {{category.catName}}
            </a-select-option>
            
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
  import { ref, reactive, getCurrentInstance } from 'vue';
  import axios from 'axios';
  import { MyNotification } from '@/utils/util';
  import {postData} from '@/utils/request/http';

  
  const { proxy } = getCurrentInstance()
  const userData = JSON.parse(localStorage.getItem('userData'))
  const categories = JSON.parse(localStorage.getItem('categories'))
  const emits = defineEmits(['refresh'])

  // 上传组件
  const fileList = ref([])
  // beforeUpload 返回 false 后，手动上传文件。
  const beforeUpload = file => {
    fileList.value = [file];
    console.log(file);
    return false;
  }

  const handleChange = info => {
    visible.value = info.fileList.length !== 0
  }

  // 对话框
  const visible = ref(false)
  const uploading = ref(false)

  const handleReset = () => formRef.value.resetFields()

  const handleUpload = async () => {

    if(userData.banFlag === '1') {
      MyNotification("error","警告","您的账号已经被封禁，不能删除已经上传的作品")
      return
    }

    uploading.value = true
    const formData = new FormData()
    formData.append('file', fileList.value[0].originFileObj)
    formData.append('workTitle',formState.workTitle)
    formData.append('workContent',formState.workContent)
    formData.append('catId',formState.catId)
    formData.append('usrId',userData.usrId)
 
    const data = await postData("/upload/video",formData,{headers: {"Content-Type": "multipart/form-data"}})
    console.log(data);
    if(data.data > 0) {
      emits("refresh")
      MyNotification("success","成功","上传成功");
      uploading.value = false
      visible.value = false
      fileList.value.splice(0)
    }
  };

  // 表单
  const formState = reactive({
    workTitle: '',
    workContent: '',
    catId: 'default'
  })
  const formRef = ref(null)


</script>

<style lang="less" scoped>
  .upload-container {
    width: 100%;
    height: 50%;
    margin: auto;
    background-color: #fff;
    padding: 1rem;

  }
  :deep(.ant-upload.ant-upload-drag .ant-upload-btn) {
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>