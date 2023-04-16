<template>
	<div>
		<a-table
			class="ant-table-striped"
			:columns="columns"
			:data-source="dataSource"
			:row-class-name="
				(_record, index) => (index % 2 === 1 ? 'table-striped' : null)
			"
			:pagination="pagination"
			@change="changePage"
		>
			<template #headerCell="{ column }">
				<template v-if="column.key === 'workPhoto'">
					<span>
						<picture-outlined />
						{{ column.name }}
					</span>
				</template>
			</template>

			<template #bodyCell="{ column, record }">
				<template v-if="column.key === 'workPhoto'">
					<template v-if="record.workSrc != null">
						<a-image
							:src="record.workPhoto"
							:width="150"
							:height="90"
							placeholder
						/>
					</template>
					<span v-else>无</span>
				</template>

        <template v-else-if="column.key === 'tags'">
          <template v-for="tag in record.tags" :key="tag.tagId">
            <a-tag color="blue">
              {{ tag.tagName }}
            </a-tag>
          </template>
        </template>

				<template v-else-if="column.key === 'passFlag'">
					<span>
						<a-tag v-show="record.passFlag === '0'" color="orange">
							审核中
						</a-tag>
						<a-tag v-show="record.passFlag === '1'" color="green">
							已发布
						</a-tag>
						<a-tag v-show="record.passFlag === '2'" color="red"> 未通过 </a-tag>
					</span>
				</template>
				<template v-else-if="column.key === 'action'">
					<a-popconfirm
						v-if="dataSource.length"
						title="Sure to delete?"
						@confirm="onDelete(record.workId)"
					>
						<a-button type="primary" danger ghost size="small" :style="{fontSize: '12px'}">下架</a-button>
					</a-popconfirm>
				</template>
				<template v-else-if="column.key === 'workUploadTime'">
					<a-tooltip
						:title="record.workUploadTime.format('YYYY-MM-DD HH:mm:ss')"
					>
						{{ record.workUploadTime.fromNow() }}
					</a-tooltip>
				</template>
			</template>
		</a-table>
	</div>
</template>

<script setup>
	// 此组件和用户端的上传视频相似 后期可以考虑组件化以下
	import { ref, computed, getCurrentInstance } from "vue";
	import { getWorksPage, delWork, getTags } from "@/utils/request/api";

	const columns = [
		{
			name: "封面",
			dataIndex: "workPhoto",
			key: "workPhoto",
			align: "center",
			width: 180,
		},
		{
			title: "标题",
			dataIndex: "workTitle",
			key: "workTitle",
			ellipsis: true,
			width: "30%",
		},
		{
			title: "标签",
			key: "tags",
			dataIndex: "tags",
			align: "center",
      width: "20%",
		},
		{
			title: "发布时间",
			dataIndex: "workUploadTime",
			key: "workUploadTime",
		},
		{
			title: "状态",
			key: "passFlag",
			dataIndex: "passFlag",
		},
		{
			title: "操作",
			key: "action",
		},
	];
	const { proxy } = getCurrentInstance();
	let dataSource = ref([]);
	let current = ref(1);
	let total = ref(10);
	let pageSize = ref(4);
	const pagination = computed(() => ({
		total: total.value,
		current: current.value,
		pageSize: pageSize.value,
		showSizeChanger: false,
		hideOnSinglePage: true,
	}));

	const changePage = (pagination) => {
		current.value = pagination.current;
		getUploadWorks(current.value, pageSize.value);
	};

	const getUploadWorks = async (page, size) => {
		const data = await getWorksPage(page, size);

		total.value = data.total;
		dataSource.value = data.list;
		dataSource.value.forEach(async(item) => {
			let uploadTime = item.workUploadTime;
			item.workUploadTime = proxy.$dayjs(uploadTime);

      // 存放每个视频的所有标签名称
      item.tags = await getTags(item.workId)
		});
	};

	const onDelete = async (workId) => {
		console.log(workId);

		const data = await delWork(workId);
		if (data > 0) {
			dataSource.value = dataSource.value.filter(
				(item) => item.workId !== workId
			);
		}
	};

	getUploadWorks(current.value, pageSize.value);
</script>

<style lang="less" scoped>
	.ant-table-striped :deep(.table-striped) td {
		background-color: #fafafa;
	}

	:deep(.ant-image-img) {
		object-fit: contain;
		height: 100%;
	}
</style>
