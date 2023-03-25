<template>
  <div class="card box-shodow">
    <div class="card-title">
      <slot name="title"></slot>
    </div>
    <div class="card-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
  .card {
    width: 100%;
    background-color: #fff;
    border-radius: 5px;
    text-align: left;
    padding: 10px;
    .card-title {
      font-size: 20px;
      font-weight: 500;
    }

    .card-content {
      font-size: 14px;
      width: 100%;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }

  .box-shadow {
    box-shadow: 0 2px 4px 0 rgb(0 0 0/4%);
  }

</style>