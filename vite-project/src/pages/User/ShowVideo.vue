<template>
  <div class="wrapper">
    <div class="video-wrapper">
      <video :src="currentVideo.workSrc" controls></video>
    </div>
    <div class="comment">
      <a-tabs v-model:activeKey="activeKey" size="small" :style="{height: '100%'}">
        <!-- 视频简介tab -->
				<a-tab-pane key="1">
					<template #tab>
						<h3 class="tab-title">简介</h3>
					</template>
          <div class="video-detail">
            <!-- 显示视频标题 -->
            <a-typography-paragraph
              :ellipsis="{rows: 2, tooltip: true}"
              :content="currentVideo.workTitle"
              :style="{ fontSize: '20px'}"
              strong
            />
            
            <div class="video-tags" :style="{paddingBottom: '8px'}">
              <a-tag color="#336699" v-for="tag in tags" :key="tag.tagId">{{tag.tagName}}</a-tag>
            </div>
            <a-typography-paragraph
              class="video-content"
              :ellipsis="{rows: 3, tooltip: true}"
              :content="currentVideo.workContent"
            />
            
            <p class="video-time">
              <span> {{currentVideo.category.catName}}</span>
              {{videoUploadTime}}
            </p>
            <div class="video-control">
              <ul>
                <li @click="onClickFavorite">
                  <svg class="icon" aria-hidden="true" :style="iconStyle">
                    <use xlink:href="#icon-shoucang1"></use>
                  </svg>
                  <p>收藏</p>
                </li>
                <li @click="onClickPlayList">
                  <svg class="icon" aria-hidden="true" :style="iconStyle">
                    <use xlink:href="#icon-shaohouguankan"></use>
                  </svg>
							    <p>稍后观看</p>
                </li>
                <li @click="activeKey = '2'">
                  <svg class="icon" aria-hidden="true" :style="iconStyle">
                    <use xlink:href="#icon-pinglun1"></use>
                  </svg>
                  <p>评论</p>
                </li>
              </ul>
            </div>
          </div>
				</a-tab-pane>
        <!-- 评论tab -->
				<a-tab-pane key="2" >
					<template #tab>
						<h3 class="tab-title">评论</h3>
					</template>
          <!-- 显示评论及发送评论模块 -->
          <div>
            <CommentCom :workId="props.workId" />
          </div>
				</a-tab-pane>
			</a-tabs>

      <FavoriteModal
        :workId="props.workId"
      />
        
    </div>
  </div>
</template>

<script setup>
  import { computed, onBeforeMount, reactive, ref, getCurrentInstance, watch } from 'vue'
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
  
  const iconStyle = reactive({ fontSize: "25px" });

  // 获取当前组件实例对象
	const { proxy } = getCurrentInstance()
  const userData = JSON.parse(localStorage.getItem('userData'))
  // 当前播放视频的详细信息对象
  const currentVideo = computed(() => JSON.parse(localStorage['currentVideo']) )
  
  // 计算属性 视频的上传时间 格式化
  const videoUploadTime = computed(() => currentVideo.value.workUploadTime.substring(0,10))
  // 存放视频标签的数组
  const tags = ref([])


  // tab选项卡
  const activeKey = ref("1");



  // 显示收藏夹Modal
  const onClickFavorite = () => {
    bus.emit("isShowFavoriteModal",true)
  }

  // 点击稍后播放按钮
  const onClickPlayList = async() => {
    const data = await addPlayList( userData.usrId, props.workId)
    if(data > 0) {
      // 添加成功
      proxy.$message.success('已添加到稍后播放列表！')
    }
  }




  // 监听activeKey
  // 更改tab选项卡时执行
  watch(activeKey, (newActiveKey) => {
    if(newActiveKey === '2') {
      // 当前选项卡为评论
      // 触发子组件绑定在事件总线上的事件
      // 通知评论模块子组件 发送请求 获取所有的评论
      bus.emit("isGetComments")
    }
  })
  


  // 发起请求请求后端 获取视频的所有标签
  onBeforeMount(async() => {
    const data = await getTags(props.workId)
    tags.value = data
  })

  

</script>

<style lang="less" scoped>

  .wrapper {
    width: 100%;
    height: calc(100vh - 80px);
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #fff;

    .video-wrapper {
      flex-grow: 1;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      video {
        height: 100%;
        width: 100%;
        background-color: #000;
      }
    }

    .comment {
      width: 20%;
      height: 100%;
      padding: 10px;
      margin-left: 10px;


      .tab-title {
        font-size: 20px;
        font-weight: 500;
      }

      .video-detail {
        text-align: left;
        p {
          margin: 0;
          padding: 0;
        }

        


        .video-content {
          font-size: 16px;
          line-height: 20px;
          margin-bottom: 8px;
          letter-spacing: 1px;
        }

        .video-time {
          font-size: 12px;
          height: 15px;
          line-height: 15px;
          span {
            color: #aaa;
            font-size: 14px;
            display: inline-block;
            padding-right: 8px;
            margin-right: 8px;
            border-right: 1px solid #ccc;
          }
        }

        .video-control {
          border-radius: 3px;
          padding: 8px 50px;
          margin-top: 3rem;
          ul {
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: space-between;
            li {
              display: flex;
              flex-direction: column;
              align-items: center;
              font-size: 12px;
              .icon {
                font-size: 20px;
                margin-bottom: 1px;
              }
            }

            li:hover {
              color: #336699;
            }
          }
        }
      }

    }
  }

  :deep(.ant-tabs-nav-list .ant-tabs-tab) {
		padding: 0;
	}

  :deep(.ant-tabs-nav) {
    margin-bottom: 8px;
  }

  

</style>