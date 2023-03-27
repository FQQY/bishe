<template>
  <div class="admin-wrapper">
    <a-layout class="layout"> 
      <a-layout-sider 
        v-model:collapsed="collapsed" 
        :trigger="null" 
        class="layout-sider"
        collapsible
      >
        <a-menu
          v-model:openKeys="openKeys"
          v-model:selectedKeys="selectedKeys"
          mode="inline"
          theme="dark"
        >
          <div class="logo flex" >
            <router-link to="/admin">
              <img
                src="@/assets/img/logo.png"
                alt="雪茄达摩鲨"
                :style="{  width:'180px'}"
                v-if="!collapsed"
              />
            </router-link>
          </div>
          <router-link :to="{name: 'systeminfo'}">
            <a-menu-item key="systeminfo">
              <template #icon>
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-gonggao1"></use>
                </svg>
              </template>
              平台概况
            </a-menu-item>
          </router-link>

          <router-link :to="{name: 'addnotice'}">
            <a-menu-item key="addnotice">
              <template #icon>
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-gonggao1"></use>
                </svg>
              </template>
              发布公告
            </a-menu-item>
          </router-link>

          <router-link :to="{name: 'videomanage'}">
            <a-menu-item key="videomanage">
              <template #icon>
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-shipin1"></use>
                </svg>
              </template>
              作品管理
            </a-menu-item>
          </router-link>
          <a-sub-menu key="sub1">
            <template #icon>
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-zuozhe"></use>
              </svg>
            </template>
            <template #title>用户管理</template>

            <router-link :to="{name: 'banuser'}">
              <a-menu-item key="banuser">
                <template #icon>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-fengjin"></use>
                  </svg>
                </template>
                封禁用户
              </a-menu-item>
            </router-link>
            
            <router-link :to="{name: 'askuser'}">
              <a-menu-item key="askuser">
                <template #icon>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-shenqing"></use>
                  </svg>
                </template>
                用户申请
              </a-menu-item>
            </router-link>

            <router-link :to="{name: 'addroot'}">
              <a-menu-item key="addroot">
                <template #icon>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-shenqing"></use>
                  </svg>
                </template>
                添加管理员
              </a-menu-item>
            </router-link>
          </a-sub-menu>

        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header>
          <div class="flex">
            <menu-unfold-outlined
              v-if="collapsed"
              class="trigger"
              @click="() => (collapsed = !collapsed)"
            />
            <menu-fold-outlined v-else class="trigger" @click="() => (collapsed = !collapsed)" />
          </div>
          <div class="flex">
            <a-avatar style="background-color: #87d068;margin-right: 32px">
              <template #icon>
                <AntDesignOutlined />
              </template>
            </a-avatar>
            <div class="flex">
              <a-button 
                type="primary" 
                :style="{fontSize: '12px'}" 
                size="small" 
                @click="logout"
                danger
              >退出</a-button>
            </div>
          </div>
          
        </a-layout-header>
        <a-layout-content
          :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }"
        >
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script setup>
  import { ref, reactive, toRefs } from 'vue';
  import { useRoute, useRouter } from 'vue-router';

  const collapsed = ref(false);
  const router = useRouter()
  const route = useRoute()
  console.log(route.name);
  const logout = () => {
    localStorage.clear()
    router.push("/")
  }


  const state = reactive({
    selectedKeys: [route.name],
    openKeys: ["sub1"],
  });
  const { selectedKeys, openKeys } = toRefs(state);

 

</script>

<style lang="less" scoped>

  .admin-wrapper {
    width: 100vw;
    height: 100vh;
    .layout {
      width: 100%;
      height: 100%;
      
    }
  }
  .layout-sider {
    // background-color: #fff;
    height: 100%;
    .logo {
      width: 100%;
      height: 90px;
    }
  }

  .layout .ant-layout-header {
    margin: 0 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .trigger {
      color: #fff;
      font-size: 18px;
    }
  }

  .flex {
    display: flex;
    align-items: center;
    justify-content: center;
  }


</style>