<template>
	<div class="history-wrapper">
		<template v-if="List.length !== 0">
			<VideoItem
				v-for="(item, index) in List"
				:key="index"
				:video="item['workDTO']"
				:time="item.viewTime"
				@deleteEvent="delHistory"
			/>
		</template>
		<a-empty v-else/>
		<a-back-top :visibilityHeight="200"/>
	</div>
	
</template>

<script setup>
	import { ref } from "vue";

	import VideoItem from "@/components/VideoItem.vue";
	import { getHistories, delWorkHistory } from '@/utils/request/api';
	import { MyNotification } from '@/utils/util';

	const userData = JSON.parse(localStorage.getItem("userData"));
	const List = ref([]);

	const getWorkHistory = async () => {
		const data = await getHistories(userData.usrId);
		List.value = data
	};

	const delHistory = async (video) => {
	
		const isSuccess = await delWorkHistory(userData.usrId, video.workId);
		if (isSuccess) {
			MyNotification("success","删除成功！");
			getWorkHistory();
		}
	};


	getWorkHistory();
</script>

<style lang="less" scoped>
	@test-color: red;
	.history-wrapper {
		height: 100%;
		max-height: calc(100vh - 80px);
		overflow-y: scroll;
		padding: 1rem 20rem;
	}
</style>
