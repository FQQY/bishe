<template>
  <div class="video-card">
    <div class="video-card-photo">
      <img :src="videoDetail.workPhoto" alt="">
    </div>
    <div class="video-card-content">
      <p class="video-card-title">{{videoDetail.workTitle}}</p>
      <span class="video-card-avtor">
        <svg class="icon svg-video-card" aria-hidden="true">
          <use xlink:href="#icon-zuozhe"></use>
        </svg>
        {{videoDetail.user.usrName}}
      </span>
    </div>
  </div>
</template>

<script setup>
  
  const props = defineProps({
    videoDetail: Object
  })


</script>

<style lang="less" scoped>
  .video-card {
    width: 24%;
    
    cursor: pointer;
    // margin-bottom: 15px;
    margin: 5px;
    border: .1px solid rgba(0,0,0,.1);

    .video-card-photo>img {
      width: 100%;
      height: 15rem;
      object-fit: contain;
      background-color: #000;
    }

    .video-card-content {
      padding: 10px;
      .video-card-title {
        font-size: 14px;
        font-weight: bold;
        line-height: 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        margin-bottom: 5px;
      }

      .video-card-avtor {
        font-size: 12px;
        color: #aaa;

      }

    }
  }

  .video-card:hover {
    box-shadow: 0 1px 2px -2px rgb(0 0 0 / 16%), 
                0 3px 6px 0 rgb(0 0 0 / 12%), 
                0 5px 12px 4px rgb(0 0 0 / 9%);
  }

</style>