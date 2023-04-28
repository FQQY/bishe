<template>
  <div class="article-wrapper">
    <div class="article-container">
      <div class="article-content">
        <a-typography-title 
          :ellipsis="{ rows: 2 }"
          :style="{
            lineHeight: '56px'
          }"
          :content="currentArticle.workTitle"
        />
        <div :style="{marginBottom: '40px'}">
          <span>{{proxy.$dayjs(currentArticle.workUploadTime).format('YYYY-MM-DD HH:mm')}}</span>&nbsp;&nbsp;
          <span>{{currentArticle.user.usrName}}</span>
        </div>

        <a-typography-paragraph>
          <p  class="text" v-html="currentArticle.workContent"></p>
        </a-typography-paragraph>
        <!-- 显示评论及发送评论模块 -->
        <div>
          <CommentCom :workId="props.workId" />
        </div>
    
      </div> 
      <!-- 操作按钮 -->
      <div class="article-do">
        <a-button shape="circle" size="large" @click="onClickFavorite">
          <template #icon>
            <svg class="icon" aria-hidden="true" :style="iconStyle">
              <use xlink:href="#icon-shoucang1"></use>
            </svg>
          </template>
        </a-button>
        <a-button shape="circle" size="large" @click="onClickPlayList">
          <template #icon>
            <svg class="icon" aria-hidden="true" :style="iconStyle">
              <use xlink:href="#icon-shaohouguankan"></use>
            </svg>
          </template>
        </a-button>
        <a-button shape="circle" size="large" @click="scrollToComment">
          <template #icon>
            <svg class="icon" aria-hidden="true" :style="iconStyle">
              <use xlink:href="#icon-pinglun1"></use>
            </svg>
          </template>
        </a-button>      
      </div>
    </div>

    <!-- 收藏弹框 -->
    <FavoriteModal 
      :workId="props.workId"
    />

  </div>
  <a-back-top/> 
</template>

<script setup>
  import { computed, getCurrentInstance, ref, reactive } from 'vue';
  import bus from 'vue3-eventbus';

  import FavoriteModal from '@/components/FavoriteModal.vue';  
  import CommentCom from '@/components/CommentCom.vue';

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
    workId: String
  })
  
  const { proxy } = getCurrentInstance()
  const userData = JSON.parse(localStorage.getItem('userData'))
  const iconStyle = reactive({ fontSize: "25px" });
  // 当前文章的详细信息对象
  const currentArticle = computed(() => JSON.parse(localStorage['currentArticle']))

  
  // 点击收藏按钮
  // 显示收藏夹Modal
  const onClickFavorite = () => {
    bus.emit("isShowFavoriteModal",true)
  }

  // 点击页面滚动到评论区域
  const comment = ref()
  const scrollToComment = () => {
    comment.value.scrollIntoView(true)
  }

  // 点击稍后播放按钮
  const onClickPlayList = async() => {
    const data = await addPlayList(userData.usrId,props.workId)
    if(data > 0) {
      // 添加成功
      proxy.$message.success('已添加到稍后播放列表！')
    }
  }


</script>

<style lang="less" scoped>
  .article-wrapper {
    background-color: #fff;
    min-height: calc(100vh - 80px);
    .article-container {
      margin: 0 20rem;
      width: 65%;
      .article-content {
        text-align: left;
        padding: 16px;
        .text {
          text-align: left;
          
        }
      }

      .article-do {
        height: 170px;
        position: fixed;
        right: 100px;
        bottom: 50%;
        transform: translateY(50%);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        text-align: center;
      }
    }
  }

</style>