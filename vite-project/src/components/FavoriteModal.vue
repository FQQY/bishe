<template>
  <!-- 对话框 -->
  <a-modal v-model:visible="visible" title="收藏">
    <a-checkbox-group v-model:value="favbIds" style="width: 100%">
      <div :style="{margin: '10px'}" v-for="favbag in favbags" :key="favbag.favbId">
        <a-checkbox :value="favbag.favbId">{{favbag.favbName}}</a-checkbox>
        <span :style="{fontSize: '12px',color: '#aaa'}">{{favbag.favList.length}}个内容</span>
      </div>
    </a-checkbox-group>
    <template #footer>
      <a-button  @click="handleCreate">新建</a-button>
      <a-button type="primary" :loading="loading" @click="handleOk">确定</a-button>
    </template>

    <a-modal v-model:visible="visible1" title="新建收藏夹" @ok="handleNewBag">
      <a-form layout="horizontal">
        <a-form-item label="收藏夹名字">
          <a-input id="favbagName" v-model:value="favbagName"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-modal>
</template>

<script setup>
  import { computed, getCurrentInstance, ref, reactive, onBeforeUnmount  } from 'vue';
  import bus from 'vue3-eventbus';
  
  import { 
    sendComment, 
    createFavoriteBag, 
    addFavorite,
    getUserFavoriteBags,
    addPlayList,
    getWorkComments,
    getTags
  } from "@/utils/request/api"

  const props = defineProps({
    workId: {
      type: String,
      required: true
    }
  })



  // 收藏模块
  // 存放收藏夹
  const favbags = ref([])
  // 
  const visible = ref(false)
  // 
  const visible1 = ref(false)
  //
  const loading = ref(false)
  // 收集选择的收藏夹的id 
  const favbIds = ref([]);
  const favbagName = ref("")
  const userData = JSON.parse(localStorage.getItem('userData'))
  const { proxy } = getCurrentInstance()

  // 绑定事件 父组件需要展示此Modal是触发
  bus.on("isShowFavoriteModal",(isShow) => {
    visible.value = isShow
    getFavbags(userData.usrId)
  })
  // 组将销毁前 解绑事件
  onBeforeUnmount(() => {
    bus.off("isShowFavoriteModal")
  })

  // 发送请求获取当前登录用户的所有收藏夹信息
  const getFavbags = async(usrId) => {

    const data = await getUserFavoriteBags(usrId)
    console.log("收藏夹",data);
    favbags.value = data

  }


  // 用户点击新建收藏夹的回调
  const handleCreate = () => {
    visible1.value = true;
  }

  // 用户点击确定添加收藏的回调函数 
  const handleOk = async(e) => {
    loading.value = true;
    const data = await addFavorite( userData.usrId, props.workId, favbIds.value)
    if(data === -1) {
      proxy.$message.error('已经存在')
    }else {
      proxy.$message.success('添加成功')
    }
    loading.value = false;
    visible.value = false;
    favbIds.value.splice(0)
  };

  // 用户点击新建发送创建文件夹的请求
  const handleNewBag = async () => {
    console.log(favbagName.value);
 
    const data = await createFavoriteBag(userData.usrId, favbagName.value)
    if(data > 0) {
      getFavbags(userData.usrId)
      visible1.value = false
      // 提示用户新建收藏夹成功
      proxy.$message.success('收藏夹创建成功！')
    }

  }


</script>

<style lang="less" scoped>

</style>