<template>
  <div class="his-card">
    <div class="his-content" >
      <div class="img-container" @click="showDetail(video.workSrc ? 'showvideo':'showarticle',video)">
        <img v-show="video.workSrc" :src="video.workPhoto" alt="加载失败">
        <div v-show="!video.workSrc" class="article-photo">无</div>
      </div>
      <div class="his-info">
        <p class="vid-title">{{video.workTitle}}&nbsp;&nbsp;&nbsp;<span :style="{fontSize: '12px',color: '#ccc',fontWeight: 'normal'}">{{addTime.fromNow()}}</span></p>
        <div class="del-icon">
          <delete-outlined :style="{fontSize: '16px'}" @click="del()"/>
        </div>
        <div class="vid-author">
          <svg class="icon svg-video-card" aria-hidden="true">
            <use xlink:href="#icon-zuozhe"></use>
          </svg>
          {{video.user.usrName}}  <span>{{video.category.catName}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { computed, getCurrentInstance } from "vue";
  import { useRouter } from "vue-router"

  import {addHistory} from '../utils/request/api';

  const props = defineProps({
    video: Object,
    time: {
      type: String,
      default: ''
    }
  })
  const emit = defineEmits(['deleteEvent'])
  const userData = JSON.parse(localStorage.getItem('userData'))

  const router = useRouter()
  const {proxy} = getCurrentInstance()
  const addTime = computed(() => proxy.$dayjs(props.time))

  const del = () => {
    emit('deleteEvent', props.video)
  }

	const showDetail = async(name,work) => {
		console.log(userData);
		// 播放视频之前 添加到历史记录
		const data = await addHistory(userData.usrId,work.workId)
		if(data > 0){
				work.workSrc == null ?
				localStorage.setItem("currentArticle",JSON.stringify(work)) :
				localStorage.setItem("currentVideo",JSON.stringify(work))
				// 进入视频播放页面 通过路由传递视频的信息对象 包括：视频时长、发布者等详细信息
				router.push({name, params:{workId: work.workId}});
		}
	}


</script>

<style lang="less" scoped>
  .his-card {
    background-color: #fff;
    padding: 10px;
    // height: 130px;
    display: flex;
    align-items: center;
    
    .his-content {
      flex-grow: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      .img-container {
        width: 170px;
        height: 100px;
        margin-right: 20px;
        background-color: #000;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 3px;
        img {
          height: 100%;
          border-radius: 3px;
        }

        .article-photo {
          width: 100%;
          height: 100%;
          font-size: 24px;
          font-weight: bold;
          color: #fff;
          background-color: #ccc;
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 3px;
        }
      }

      .his-info {
        border-bottom: 1px solid #eee;
        flex-grow: 1;
        height: 100px;
        padding: .5rem 10rem .5rem 1rem;
        display: flex;
        align-items: flex-start;
        flex-direction: column;
        justify-content: space-between;
        .vid-title {
          width: 500px;
          font-weight: bold;
          font-size: 16px;
          margin: 0;
          line-height: 20px;
          text-align: left;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .del-icon {
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: end;
          cursor: pointer;
        }
        .vid-author {
          font-size: 12px;
          width: 100%;
          text-align: center;
          span {
            color: #99a2aa;
            border-left: 1px solid #e5e9ef;
            padding-left: 10px;
            margin-left: 10px;
          }
        }
      }

    }
  }

</style>