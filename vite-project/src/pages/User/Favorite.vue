<template>
  <a-empty v-if="favoritebags.length === 0"></a-empty>
  <div class="favorite-wrapper" v-else>
    <a-collapse 
      v-model:activeKey="activeKey"
      accordion
    >
      <a-collapse-panel v-for="favoritebag in favoritebags" :key="favoritebag.favbId" :header="favoritebag.favbName">
        <template v-if="favoritebags.length !== 0">
          <VideoItem 
            v-for="favorite in favoritebag.favList" 
            :key="favorite.favId" 
            :video="favorite.work"
            @deleteEvent="delFavorite(favorite.favId)"
          ></VideoItem>
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
  </div>
</template>

<script setup>
  import { ref } from "vue";

  import VideoItem from '@/components/VideoItem.vue'
  import { getUserFavoriteBags, getUser, getCategory, delUserFavorite, delFavoriteBag, clearFavoriteBag } from '@/utils/request/api';
  import { MyNotification } from '@/utils/util';
  
  const userData = JSON.parse(localStorage.getItem('userData'))
  const activeKey = ref([])
  const favoritebags = ref([])

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

  const delFavorite = async(favId) => {

    const data = await delUserFavorite(favId,userData.usrId)
    data & refreshData()
  }

  const onDelFavoriteBag = async(favbId) => {
    // 删除收藏夹和收藏夹里的所有作品
    const data = await delFavoriteBag(userData.usrId, favbId)
    data & refreshData()
  }

  const onClearFavoriteBag = async(favbId) => {
    // 清空收藏夹的内容
    const data = await clearFavoriteBag(userData.usrId, favbId)
    data & refreshData()
  }

  const refreshData = () => {
    MyNotification("success","删除成功！")
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