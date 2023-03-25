<template>
	<div class="mobile-main">
		<a-row class="head" justify="space-between">
			<a-col :span="12">
				<MyCard>
					<div class="notice">
						<svg class="icon svg-notice" aria-hidden="true">
							<use xlink:href="#icon-gonggao"></use>
						</svg>
						<span class="notice-text">{{lastNotice.notContent}}</span>
					</div>
				</MyCard>
			</a-col>

			<a-col :span="11">
				<MyCard>
					<ul class="home-nav">
						<li  class="nav" v-for="category in categories" :key="category.catId" @click="filterVideo(category.catId)">
							<div :ref="(el) => setCategoryRefs(el, category.catId)" class="nav-block">
								<p class="nav-title">{{category.catName}}</p>
							</div>
						</li>
					</ul>
				</MyCard>
			</a-col>
		</a-row>


		<MyCard>
			<a-tabs 
				v-model:activeKey="activeKey" 
				size="large" 
				@change="clearTags()"
				animated
				type="card"
			>
				<a-tab-pane key="1">
					<template #tab>
						<h3 class="tab-title">视频</h3>
					</template>
					<div class="pagination">
						<a-button shape="circle" :style="{marginRight: '20px'}" @click="reload">
							<template #icon><reload-outlined /></template>
						</a-button>
						<a-pagination 
							v-model:current="pageInfo.current[0]" 
							:defaultPageSize="pageInfo.pageSize[0]"
							:total="pageInfo.total[0]" 
							:show-total="total => `共有 ${total} 个`"
							@change="changePage" 
						/>
					</div>
					<div class="video-list">
						<VideoCard 
							v-for="video in videos" 
							:key="video.workId" 
							:videoDetail="video"
							@click="showDetail('showvideo',video)"
						></VideoCard>
					</div>
					
				</a-tab-pane>
				<a-tab-pane key="2">
					<template #tab>
						<h3 class="tab-title">文章</h3>
					</template>
					<div class="pagination">
						<a-button shape="circle" :style="{marginRight: '20px'}" @click="reload">
							<template #icon><reload-outlined /></template>
						</a-button>
						<a-pagination 
							v-model:current="pageInfo.current[1]" 
							:defaultPageSize="pageInfo.pageSize[1]"
							:total="pageInfo.total[1]" 
							:show-total="total => `共有 ${total} 个`"
							@change="changePage" 
						/>
					</div>
					<div class="article-list">
						<ArticleCard 
							v-for="article in articles" 
							:key="article.workId" 
							:article="article"
							@click="showDetail('showarticle',article)"
						></ArticleCard>
					</div>
					
				</a-tab-pane>
				
			</a-tabs>
					
		</MyCard>
	</div>
</template>

<script setup>
	import { onMounted, reactive, ref, computed, getCurrentInstance } from 'vue'
	import { useRouter } from 'vue-router';
	import { useVideoStore } from '@/store/videoStore'
	import { useArticleStore } from "@/store/articleStore"

	import bus from 'vue3-eventbus';

	import MyCard from "@/components/MyCard.vue";
	import VideoCard from '@/components/VideoCard.vue';
	import ArticleCard from '@/components/ArticleCard.vue'

	import { 
		getVideosPage, 
		getArticlesPage, 
		getVideosPageByCategory, 
		getArticlesPageByCategory,
		addHistory,
		getNotices,
		getCotegories
	} from '@/utils/request/api';


	const categories = ref([])
	
	const activeKey= ref('1');

	const router = useRouter();
	const userData = JSON.parse(localStorage.getItem('userData'));
	const categoryRefs = []
	const setCategoryRefs = (el, catId) => {
		if(el) {
			categoryRefs.push({
				catId,
				el
			})
		}
	}

	const videoStore = useVideoStore()
	const articleStore = useArticleStore()
	// 从pinia中读取
	let videos = computed(() => videoStore.videoPage.list)
	let articles = computed(() => articleStore.articlePage.list)

	const pageInfo = reactive({
		current: [1,1],
		pageSize: [8,3],
		total: [0,0]
	})

	const initCurrentPage = () => {
		pageInfo.current[0] = 1
		pageInfo.current[1] = 1
	}

	/**
	 * @func getWorkPage
	 * @desc 同时分别获取文章和视频 可以选择添加两个筛选条件 分类和标题
	 * @param {catId title} catId 分类id title 视频标题  
	 * @return {void} 
	 */
	const getWorksPage = async(catId=0, title=null) => {
		let videoPage = pageInfo.current[0]
		let articlePage = pageInfo.current[1]

		let videoPageSize = pageInfo.pageSize[0]
		let articlePageSize = pageInfo.pageSize[1]
		let videoData
		let articleData

		if(catId === 0) {
			videoData = await getVideosPage(videoPage, videoPageSize, title)
			articleData = await getArticlesPage(articlePage, articlePageSize, title)
		} else {
			videoData = await getVideosPageByCategory(videoPage, videoPageSize, catId)
			articleData = await getArticlesPageByCategory(articlePage, articlePageSize, catId)
		}

		// 更新总数
		pageInfo.total[1] = articleData.total
		pageInfo.total[0] = videoData.total
		// 更新pinia中的数据
		
		articleStore.$patch({
			articlePage: articleData
		})
		videoStore.$patch({
			videoPage: videoData
		})


	}


	const showDetail = async(name,work) => {
		console.log(userData);
		// 播放视频之前 添加到历史记录
		const data = await addHistory(userData.usrId, work.workId)

		if(data > 0){
			work.workSrc == null ?
			localStorage.setItem("currentArticle",JSON.stringify(work)) :
			localStorage.setItem("currentVideo",JSON.stringify(work))
			// 进入视频播放页面 通过路由传递视频的信息对象 包括：视频时长、发布者等详细信息
			router.push({name, params:{workId: work.workId}});
		}
		
	}


	const clearTags = () => {
		categoryRefs.forEach(categoryRef => {
			// 清除所有标签的选中状态
			categoryRef.el.style.backgroundColor = '#eee'
			categoryRef.el.style.color = '#000'
		})
	}
	// 为0代表没有点击过分类 否则categoryFlag保存点击分类的id
	let categoryFlag = ref(0)
	const changePage = () => {
		const search = localStorage['searchValue'] 
		
		getWorksPage(categoryFlag.value, search)
	}

	// 根据分类筛选视频
	const filterVideo = (catId) =>{
		// 清除所有标签的选中状态
		clearTags()
		categoryRefs.forEach(categoryRef => {

			// 获取点击的分类的dom
			if(categoryRef.catId === catId){
				if(categoryFlag.value === catId) {
					// 第二次点击
					categoryRef.el.style.backgroundColor = '#eee'
					categoryRef.el.style.color = '#000'
					categoryFlag.value = 0
					reload()
				}else {
					// 第一次点击
					categoryRef.el.style.backgroundColor = '#336699';
					categoryRef.el.style.color = '#fff';
					categoryFlag.value = catId;

					initCurrentPage()
					getWorksPage(categoryFlag.value)
				}
			}
		})
	
	}

	let lastNotice = ref({})	
	const { proxy } = getCurrentInstance()
	// 获取所有公告 并对数据进行处理
	const getAllNotices = async() => {

		
		// 获取最近发布的公告
		const getLastNotice = (noticeList) => {
			console.log(noticeList);
			let timeArr = []
			noticeList.forEach(item => {
				item.notUploadTime = proxy.$dayjs(item.notUploadTime)
				timeArr.push(item.notUploadTime)
			})
			let lastTime = proxy.$dayjs.max(timeArr)
			lastNotice.value = noticeList.find(item => item.notUploadTime.isSame(lastTime))
			console.log(lastNotice.value);
		}
		
		// 显示最近发布的公告
		const showLastNotice = () => {
			if(localStorage.getItem("isShowNotice") == "1") {
				let modal
				if(modal) modal.destroy()
				modal = proxy.$Modal.info({
					title: '最新公告',
					content: lastNotice.value.notContent,
					okText: '确定'
				});
				setTimeout(() => {
					modal.destroy()
					localStorage.setItem("isShowNotice",0)
				}, 3000)
			}
		}

		const data = await getNotices()
		getLastNotice(data)
		showLastNotice()
		
	}

	// 获取所有视频 文章 
	getWorksPage()
	getAllNotices()

	const reload = () => {
		// 清除所有标签的选中状态
		clearTags()

		initCurrentPage()
		getWorksPage()
	}
	
	onMounted(async() => {
		// 请求后端获取所有分类
		const data = await getCotegories()
		// 存放到浏览器中
		localStorage.setItem('categories',JSON.stringify(data.data))
		categories.value = data.data
		

		bus.on("searchCallBack",function(searchValue) {
			clearTags()
			initCurrentPage()
			getWorksPage(0,searchValue)
			getWorksPage(0,searchValue)
		})

	})
</script>


<style lang="less" scoped>

	.mobile-main {
		padding: 0 20rem;
	}

	.head {
		padding: 10px 0;

		.notice {
			display: flex;
			align-items: center;
			cursor: pointer;
			.svg-notice {
				margin-right: 8px;
			}

			.notice-text {
				font-size: 15px;
				font-weight: 500;
				line-height: 24px;
			}

		}

		.home-nav {
			// 网格布局
			display: grid;
			grid-template-columns: auto auto auto auto auto auto;
			grid-gap: 8px 10px;
			margin: 0;
			width: 100%;
			.nav {
				cursor: pointer;
				user-select: none;
				
				.nav-block {
					background-color: #eee;
					border-radius: 4px;
					text-align: center;
					padding: 2px 10px;

					.nav-title {
						font-size: 13px;
						font-weight: 500;
						margin: 0;
					}
				}
			}


			.nav:hover {
				.nav-block {
					background-color: #336699;
					color: #fff;
				}
			}


		}
	}

 	// .video-list,.article-list {
	// 	min-height: 452px;
	// }
	.video-list {
		display: flex;
		flex-wrap: wrap;
		align-items: flex-start;
	}
	// .article-list {
	// 	margin-bottom: 8px;
	// }
	.tab-title {
		font-size: 20px;
		font-weight: 500;
	}
	:deep(.ant-tabs-nav-list .ant-tabs-tab) {
		padding: 0 0 5px;
		user-select: none;
	}

	.pagination {
		display: flex;
		align-items: center;
		justify-content: end;
		padding-right: 1rem;
	}

</style>
