<template>
  <a-empty v-if="favoritebags.length === 0"></a-empty>
  <div class="favorite-wrapper" v-else>
    <a-collapse 
      v-model:activeKey="activeKey"
      accordion
    >
      <a-collapse-panel 
        v-for="favoritebag in favoritebags" 
        :key="favoritebag.favbId" 
        :header="favoritebag.favbName"
      >
        <template v-if="favoritebag.favList.length !== 0">
          <VideoItem 
            v-for="favorite in favoritebag.favList" 
            :key="favorite.favId" 
            :video="favorite.work"
            :time="favorite.work.workUploadTime"
            @deleteEvent="delFavorite(favorite.favId)"
          ></VideoItem>
        </template>
        <template v-else>
          <a-empty></a-empty>
        </template>

        <template #extra>
          <a-space>
            <a-button 
              type="primary"
              size="small"
              :style="{fontSize: '12px'}"
              @click.stop="onClearFavoriteBag(favoritebag.favbId)"
            >清空</a-button>

            <a-button 
              type="primary" 
              danger 
              size="small"
              :style="{fontSize: '12px'}"
              @click.stop="onDelFavoriteBag(favoritebag.favbId)"
            >删除</a-button>
          </a-space>
        </template>
      </a-collapse-panel>
    </a-collapse>
    
    <a-button 
      type="primary" 
      block 
      size="large"
      :style="{marginTop: '40px'}"
      @click="addFavoriteBag"
    >添加</a-button>

    <a-modal v-model:visible="visible1" title="添加收藏夹" @ok="handleNewBag">
      <a-input id="favbagName" v-model:value="favbagName"/>
    </a-modal>
  </div>
</template>

<script setup>
  import { ref } from "vue";

  import VideoItem from '@/components/VideoItem.vue'
  import { getUserFavoriteBags, getUser, getCategory, delUserFavorite, delFavoriteBag, clearFavoriteBag, createFavoriteBag } from '@/utils/request/api';
  import { MyNotification } from '@/utils/util';
  
  const userData = JSON.parse(localStorage.getItem('userData'))
  const activeKey = ref([])
  const favoritebags = ref([])
  const favbagName = ref("")
  const visible1 = ref(false)

  const getFavorite = async() => {
    const data = await getUserFavoriteBags(userData.usrId)
    favoritebags.value = data

    // 获取每一个收藏视频的发布者和类别
    favoritebags.value.forEach( favoritebag => {
      favoritebag.favList.forEach( async item => {
        let data = await getUser(item.work.usrId)
        item.work.user = data
        data = await getCategory(item.work.catId)
        item.work.category = data
      })
    })
      
  }

  const addFavoriteBag = () => {
    visible1.value = true;
  }

  // 创建收藏夹
  const handleNewBag = async () => {
    console.log(favbagName.value);
    if(favbagName.value.trim() === '') {
      MyNotification("warning","警告","收藏夹的名字不能为空！")
    }else {
      const data = await createFavoriteBag(userData.usrId, favbagName.value)
      if(data === -1) {
        MyNotification("error","失败","该收藏夹已经存在！")
        return 
      }
      
      visible1.value = false
      refreshData("添加成功！")
    }
    favbagName.value = ''
  }


  const delFavorite = async(favId) => {

    const data = await delUserFavorite(favId,userData.usrId)
    data & refreshData("删除成功！")
  }

  const onDelFavoriteBag = async(favbId) => {
    // 删除收藏夹和收藏夹里的所有作品
    const data = await delFavoriteBag(userData.usrId, favbId)
    data & refreshData("删除成功！")
  }

  const onClearFavoriteBag = async(favbId) => {
    // 清空收藏夹的内容
    const data = await clearFavoriteBag(userData.usrId, favbId)
    data & refreshData("删除成功！")
  }

  const refreshData = (msg) => {
    MyNotification("success","成功",msg)
    getFavorite()
  }

  getFavorite()

</script>

<style lang="less" scoped>
  .favorite-wrapper {
    height: 100%;
    background-color: #eee;
    max-height: calc(100vh - 80px);
    overflow-y: scroll;
    padding: 1rem 20rem;
  }
</style>