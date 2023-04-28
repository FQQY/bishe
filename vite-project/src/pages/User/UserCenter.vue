<template>
  <div class="center-wrapper">
    <a-layout>
      <a-layout-sider width="200" style="background: #fff" v-model:collapsed="state.collapsed">
        <a-button type="primary" @click="toggleCollapsed">
            <MenuUnfoldOutlined v-if="state.collapsed" />
            <MenuFoldOutlined v-else />
        </a-button>
        <a-menu
          v-model:openKeys="state.openKeys"
          v-model:selectedKeys="state.selectedKeys"
          mode="inline"
          @select="selectMenu"
          :style="{borderRight: 0 }"
        >
          
          <a-menu-item key="/user/center/info">
            <template #icon>
              <svg class="icon" aria-hidden="true" :style="iconStyle">
                <use xlink:href="#icon-gerenxinxi2"></use>
              </svg>
            </template>
            <span>个人信息</span>
          </a-menu-item>
          <a-menu-item key="/user/center/history">
            <template #icon>
              <svg class="icon" aria-hidden="true" :style="iconStyle">
                <use xlink:href="#icon-lishi2"></use>
              </svg>
            </template>
            <span>观看历史</span>
          </a-menu-item>
          <a-menu-item key="/user/center/favorite">
            <template #icon>
              <svg class="icon" aria-hidden="true" :style="iconStyle">
                <use xlink:href="#icon-shoucang1"></use>
              </svg>
            </template>
            <span>收藏管理</span>
          </a-menu-item>
          <a-menu-item key="/user/center/playlist">
            <template #icon>
              <svg class="icon" aria-hidden="true" :style="iconStyle">
                <use xlink:href="#icon-shaohouguankan"></use>
              </svg>
            </template>
            <span>稍后观看</span>
          </a-menu-item>
          <a-sub-menu>
            <template #icon>
              <svg class="icon" aria-hidden="true" >
                <use xlink:href="#icon-fabu"></use>
              </svg>
            </template>
            <template #title>发布</template>
              <a-menu-item key="/user/center/uploadvideo">
                <template #icon>
                  <svg class="icon" aria-hidden="true" :style="iconStyle">
                    <use xlink:href="#icon-shipinbofang"></use>
                  </svg>
                </template>
                视频
              </a-menu-item>
            
              <a-menu-item key="/user/center/uploadarticle">
                <template #icon>
                  <svg class="icon" aria-hidden="true" :style="iconStyle">
                    <use xlink:href="#icon-wenzhang1"></use>
                  </svg>
                </template>
                文章
              </a-menu-item>
          </a-sub-menu>
          <a-menu-item key="/user/center/audit" v-if="authority === 1">
            <template #icon>
              <InboxOutlined />
            </template>
            <span>审核视频</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>

      <a-layout-content>
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script setup>
  import { reactive, watch } from "vue";
  import { useRoute, useRouter } from "vue-router";

  const router = useRouter()
  const route = useRoute()
  const authority = JSON.parse(localStorage.getItem('authority'))
  const iconStyle = reactive({ fontSize: "15px"}); 

  const state = reactive({
    collapsed: false,
    selectedKeys: [route.fullPath],
    openKeys: ['sub1'],
    preOpenKeys: ['sub1'],
  });

  const toggleCollapsed = () => {
    state.collapsed = !state.collapsed;
    state.openKeys = state.collapsed ? [] : state.preOpenKeys;
  };

  const selectMenu = ({ item, key, selectedKeys }) => {
    router.push(key)
  }

</script>

<style lang="less" scoped>
  .center-wrapper {
    min-width: calc(100vw - 40rem);
		min-height: calc(100vh - 80px);
    display: flex;
    background-color: #eee;

    
  }
</style>