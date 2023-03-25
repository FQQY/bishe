<template>
  <div class="comment-wrapper">
    <div class="article-comment">
      <!-- 评论列表 -->
      <a-list
        class="comment-list"
        :header="`${commentList.length} 条评论`"
        item-layout="horizontal"
        :data-source="commentList"
      >
        <template #renderItem="{ item }">
          <a-list-item :style="{padding: '0'}">
            <a-comment :author="item.user.usrName">
              <template #avatar>
                <a-avatar
                  shape="square"
                  :style="{ backgroundColor:colorList[Math.round(Math.random()*colorList.length)], verticalAlign: 'middle' }"
                >
                  {{ item.user.usrName[0].toUpperCase() }}
                </a-avatar>
              </template>
              <template #content>
                <p>
                  {{ item.comtContent }}
                </p>
              </template>
              <template #datetime>
                <a-tooltip :title="item.comtTime.format('YYYY-MM-DD HH:mm:ss')">
                  <span>{{ item.comtTime.fromNow() }}</span>
                </a-tooltip>
              </template>
            </a-comment>
          </a-list-item>
        </template>
      </a-list>
    </div>
    <!-- 发布评论 -->
    <div class="send-comment" ref="comment">
      <a-comment>
        <template #content>
          <a-form-item>
            <a-textarea v-model:value="comtContent" :rows="3" />
          </a-form-item>

          <a-button :loading="submitting" type="primary" @click="handleSubmit">
            发布评论
          </a-button>
        </template>
      </a-comment>
    </div>
  </div>
</template>

<script setup>
  import { getCurrentInstance, onUnmounted, ref, onBeforeUnmount  } from 'vue';
  import bus from "vue3-eventbus"

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

  const colorList = ['#f56a00', '#7265e6', '#ffbf00', '#00a2ae', '#f8df70', '#fcc307', '#695e45', 'fa7e23', 'f7cdbc', 'ed5126', '481e1c']
  const submitting = ref(false)
  // 文章的评论列表
  const commentList = ref([])
  // 用户输入的评论内容
  const comtContent = ref('')

  const userData = JSON.parse(localStorage.getItem('userData'))
  const { proxy } = getCurrentInstance()

    // 发送请求获取视频的所有评论
  const getComments = async(workId) => {
   
    const comments = await getWorkComments(workId)
    if(comments.length != 0) {
      
      commentList.value = comments
      commentList.value.map(comment => {
        let comtTime = comment.comtTime;
        comment.comtTime = proxy.$dayjs(comtTime)
      })
    }
  }

  const handleSubmit = async() => {
    if(userData.banFlag === '1') {
      MyNotification("error","警告","您的账号已经被封禁，不能删除已经上传的作品")
      return
    }

    const data = await sendComment(userData.usrId, props.workId, comtContent.value)
    if(data >0 ){
      getComments(props.workId);
      comtContent.value = '';
      proxy.$message.success("发布成功！");
    }

  }

  // 父组件触发此时间告诉组件应该发起请求
  bus.on("isGetComments",() => {
    getComments(props.workId)
  })

  getComments(props.workId)

  // 组件销毁前 解绑事件
  onBeforeUnmount(() => {
    bus.off("isGetComments")
  })

</script>

<style lang="less" scoped>
  :deep(.ant-comment) {
    .ant-comment-inner {
      padding: 8px 0;
      .ant-comment-avatar {
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .ant-comment-content {
        .ant-comment-content-detail {
          p {
            text-align: left;
            font-size: 14px;
          }
        }
      }
    }
  }
  :deep(.ant-list-header) {
    text-align: left;
    padding: 8px 0;
  }

  .comment-wrapper {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .article-comment {
    max-height: 460px;
    
    overflow: auto;
    .comment-list {
      height: 100%;
    }
  }


</style>