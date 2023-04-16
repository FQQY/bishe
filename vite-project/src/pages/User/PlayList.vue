<template>
  <a-empty v-if="List.length === 0"></a-empty>
  <div class="palylist-wrapper" v-else>
    <template v-if="List.length !== 0">
      <VideoItem 
        v-for="(item, index) in List" 
        :key="index" 
        :video="item['workDTO']"
        :time="item.addTime"
        @deleteEvent="delHistory"
      />
    </template>
  </div>
  <a-back-top/>
</template>

<script setup>
  import { ref } from "vue";

  import VideoItem from '@/components/VideoItem.vue'
  import { getPlayList, delWorkPlayList } from '@/utils/request/api';
  import { MyNotification } from '@/utils/util';
  
  const userData = JSON.parse(localStorage.getItem('userData'))
  const List = ref([])

  const getWorkPlayList = async() => {
    
    const data = await getPlayList(userData.usrId)
    List.value = data
  }

  const delHistory = async(video) => {

    const isSuccess = await delWorkPlayList(userData.usrId, video.workId)
    if(isSuccess) {
      MyNotification("success","删除成功！")
      getWorkPlayList()
    }
  }


  getWorkPlayList()

</script>

<style lang="less" scoped>
  @test-color: red;
  .palylist-wrapper {
    height: 100%;
    max-height: calc(100vh - 80px);
    overflow-y: scroll;
    padding: 1rem 20rem;
    
  }
</style>