<template>
  <div>
    <div class="wapper">
				<div class="logo">
					<router-link to="/user">
						<img
							src="@/assets/img/logo.png"
							alt="雪茄达摩鲨"
							:style="{ height: '8rem' }"
						/>
					</router-link>
				</div>
				<div class="search">
					<a-input-search
						v-model:value="value"
						placeholder="input search text"
						style="width: 50rem"
            size="large"
						@search="onSearch"
						@focus="value = ''"
					/>
				</div>
				<div class="user">
					<a-dropdown placement="bottom" overlayClassName="header-dropdown" :trigger="['click']">
						<a-avatar
							shape="square"
							size="large"
							:style="{ backgroundColor: '#ffbf00', verticalAlign: 'middle', margin:'0 10px', cursor: 'pointer' }"
						>
							{{userData.usrName.slice(0,1).toUpperCase()}}
						</a-avatar>
						<template #overlay>
							<a-menu class="dropdown-menu">
								<a-menu-item key="0" class="dropdown-menu-item">
									<template #icon>
										<UserOutlined/>
									</template>
									<router-link to="/user/center">
										<span class="dropdown-menu-item-content">个人中心</span>
									</router-link>
								</a-menu-item>
								<a-menu-item key="1" class="dropdown-menu-item" @click="logout">
									<template #icon>
										<LogoutOutlined/>
									</template>
									
									<span class="dropdown-menu-item-content">退出登录</span>
								
								</a-menu-item>
							</a-menu>
						</template>
					</a-dropdown>
					<ul class="user_list">
						<li @click="jumpCenter('/user/center/history')">
							<clock-circle-outlined :style="iconStyle"/>
							<p>观看历史</p>
						</li>
						<li @click="jumpCenter('/user/center/favorite')">	
							<star-outlined :style="iconStyle"/>
							<p>收藏</p>
						</li>
						<li @click="jumpCenter('/user/center/playlist')">
							<history-outlined :style="iconStyle"/>
							<p>稍后观看</p>
						</li>
					</ul>
        </div>
			</div>
  </div>
</template>

<script setup>
  import { getCurrentInstance, reactive, ref } from "vue";

	import { useRouter } from "vue-router";
	import bus from 'vue3-eventbus';
	import { MyNotification } from '@/utils/util';

  const iconStyle = reactive({fontSize: '20px'});

	const { proxy } = getCurrentInstance()
	const userData = JSON.parse(localStorage.getItem('userData'));
	const value = ref("");

	const router = useRouter()
 	
	const onSearch = async(searchValue) => {
		console.log("use value", searchValue);
		console.log("or use this.value", value.value);
		bus.emit('searchCallBack',searchValue)
		localStorage.searchValue = searchValue
	};

	const logout = () => {
		localStorage.clear();
		MyNotification("success","已退出")
		router.push('/')
	}

	const jumpCenter = (path) => {
		router.push(path)
	} 
</script>

<style lang="less" scoped>
  @p-color: #336699;
	.wapper {
		width: 100%;
		height: 80px;
		background: #fff;
		box-shadow: 0 2px 4px 0 rgb(0 0 0/4%);
		display: flex;
		align-items: center;
		justify-content: space-between;
    padding: 0 20rem;

		.search {
			display: flex;
			align-items: center;
			justify-content: center;
			box-shadow: 0 2px 5px 1px rgb(64 60 67/16%);
		}

		.user {
			display: flex;
			align-items: center;
		
			.ant-avatar:hover {
				box-shadow: 0 2px 5px 1px rgb(64 60 67/16%);
			}

			.user_list {
				display: flex;
				align-items: center;
				margin: 0;
				height: 8rem;

				li {
					display: flex;
					align-items: center;
					flex-direction: column;
					justify-content: space-between;
					cursor: pointer;
					margin: 0 10px;
					
					p{
						line-height: 12px;
						font-size: 12px;
						margin: 8px 0 0 0;
					}
				}

				li:hover {
					color: @p-color
				}
			}
		}
	}

	.header-dropdown {
		
		.dropdown-menu {
			border-radius: 5px;
			padding: 10px 5px;
		}
	}
</style>