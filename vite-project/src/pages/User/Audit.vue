<template>
	<div class="audit-wrapper">
		<div class="table-container">
			<a-table
				class="ant-table-striped"
				:columns="columns"
				:data-source="dataSource"
				:pagination="false"
				:row-class-name="
					(_record, index) => (index % 2 === 1 ? 'table-striped' : null)
				"
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
						<a-image :src="record.workPhoto" :height="80" placeholder />
					</template>

					<template v-else-if="column.key === 'action'">
						<a-space :size="10">
							<a-popconfirm
								v-if="dataSource.length"
								title="确定通过吗？"
								@confirm="onPass(record.workId)"
							>
								<a-button type="normal" style="fontSize:12px" size="small">通过</a-button>
							</a-popconfirm>
							<a-popconfirm
								v-if="dataSource.length"
								title="确定拒绝吗？"
								@confirm="onNoPass(record.workId)"
							>
								<a-button type="primary" style="fontSize:12px" size="small" danger>拒绝</a-button>
							</a-popconfirm>
						</a-space>
					</template>

          <template v-else-if="column.key === 'tags'">
            <template v-for="(tag, index) in record.tags" :key="index">
              <a-tag :closable="true" color="blue" @close="handleClose(record,tag.tagName)">
                {{ tag }}
              </a-tag>
            </template>
            <a-input
              v-if="record.inputVisible"
              ref="inputRef"
              v-model:value="tagName"
              type="text"
              size="small"
              :style="{ width: '78px' }"
              @blur="handleInputConfirm(record)"
              @keyup.enter="handleInputConfirm(record)"
            />
            <a-tag v-else style="background: #fff; border-style: dashed" @click="showInput(record)">
              <plus-outlined />
              New Tag
            </a-tag>
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
		<div class="next">
			<a-popconfirm title="继续工作？" @confirm="nextPage()">
				<a-button type="normal" danger>下一批</a-button>
			</a-popconfirm>
		</div>
	</div>
</template>

<script setup>
	import axios from "axios";
	import { getCurrentInstance, onBeforeMount, ref, nextTick } from "vue";
  import { getTags, updateWorkPassStatus, getNoPassWorks, addTag } from '@/utils/request/api';

	const columns = [
		{
			name: "视频封面",
			dataIndex: "workPhoto",
			key: "workPhoto",
			align: "center",
			width: "20%",
		},
		{
			title: "标题",
			dataIndex: "workTitle",
			key: "workTitle",
			ellipsis: true,
			width: "30%",
		},
    {
      title: '标签',
      key: 'tags',
      dataIndex: "tags",
      align: "center"
    },
		{
			title: "发布时间",
			dataIndex: "workUploadTime",
			key: "workUploadTime",
		},
		{
			title: "操作",
			key: "action",
			align: "center",
		},
	];
	const { proxy } = getCurrentInstance();
	let dataSource = ref([]);
	const current = ref(1);
	const pageSize = ref(5);

	const onPass = async (workId) => {
		const data = await updateWorkPassStatus(workId, "1");
		console.log(workId);

    // 一次性添加所有标签
    let record = dataSource.value.find( item => item.workId === workId)
    record.tags.forEach(async tagName => {
      addTag(workId, tagName)
    })
    
    if (data > 0) {
      // 处理过的作品从列表中删除
      dataSource.value = dataSource.value.filter(
        (item) => !(item.workId === workId)
      )
    }
	};
	const onNoPass = async (workId) => {
		console.log(workId);
		const data = await updateWorkPassStatus(workId, "2");
    if (data > 0) {
      dataSource.value = dataSource.value.filter(
        (item) => !(item.workId === workId)
      )
    }
	};

	const nextPage = () => {
		if (dataSource.value.length != 0) {
			proxy.$message.error("请完成当前的工作！");
		} else {
			current.value++;
			getAllNoPassVideos();
			if (dataSource.value.length == 0) {
				proxy.$message.info("暂时没有新的任务");
			} else {
				proxy.$message.success("您的任务已到达！");
			}
		}
	};

	const getAllNoPassWorks = async () => {

    const data = await getNoPassWorks( current.value, pageSize.value );
    console.log(data);
    dataSource.value = data.list;
    dataSource.value.forEach(async (item, index) => {
      item.workUploadTime = proxy.$dayjs(item.workUploadTime);
      // 存放每个视频的所有标签名称
      item.tags = (await getTags(item.workId)).map(tagObj => tagObj.tagName)
      // 控制每个视频是否显示输入框输入标签
      item.inputVisible = false
    });

	};

  const tagName = ref('')
  // 输入标签后先显示在页面上 如果点击了同意按钮后同意发送添加请求
  const handleInputConfirm = async(record) => {
    record.inputVisible = false
    if(tagName.value.trim() === '') return
    // const tag = await addTag(workId, tagName)
    record.tags.push(tagName.value)
    tagName.value = ''
  }
  // 删除已经添加的标签
  const handleClose = (record,tagName) => {
    record.tags = record.tags.filter( tag => tag!== tagName)
  }
  const inputRef = ref()
  const showInput = (record) => {
    record.inputVisible = true;
    nextTick(() => {
      inputRef.value.focus();
    });
  };




	getAllNoPassWorks();
</script>

<style lang="less" scoped>
	.audit-wrapper {
		padding: 2rem;

		.table-container {
			padding: 0 15rem;
		}
	}

	.next {
		position: absolute;
		top: 50%;
		right: 2rem;
		transform: translateX(-50%);
	}

	:deep(.ant-image-img) {
		height: 100%;
		width: 100%;
		border-radius: 5px;
	}
	:deep(.ant-image-img) {
		background-color: #000;
	}
	.ant-table-striped :deep(.table-striped) td {
		background-color: #fafafa;
	}
</style>
