<template>
  <div class="article-card">
    <div class="container">
      <div class="article-card-title">{{article.workTitle}}</div>
    
      <div class="article-card-avtor">
        <svg class="icon svg-article-card" aria-hidden="true">
            <use xlink:href="#icon-zuozhe"></use>
        </svg>
        {{article.user.usrName}}
      </div>
    </div>

    <div class="img-box">
      文章
    </div>

  </div>
  
</template>

<script setup>

  const props = defineProps({
    article: Object
  })

</script>

<style lang="less" scoped>
  .article-card {
    margin: 0px 30px;
    padding: 10px 0 16px;
    border-bottom: 1px solid rgba(0, 0, 0, .1);
    display: flex;
    justify-content: space-between;
    cursor: pointer;
    .article-card-title {
      padding-top: 8px;
      padding-left: 8px;
      font-size: 18px;
      font-weight: bold;
      margin-bottom: 24px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .container {
      display: flex;
      flex-direction: column;
      align-items: start;
      justify-content: space-between;
    }

    .article-card-avtor {
      font-size: 12px;
      color: #336699;
      padding: 0 8px;
      text-align: left;
      .svg-article-card {
        font-size: 14px;
      }
    }
  }

  .img-box {
    width: 180px;
    height: 110px;
    border-radius: 3px;
    background-color: #336699;
    font-size: 36px;
    font-weight: bold;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    letter-spacing: 6px;
  }

</style>