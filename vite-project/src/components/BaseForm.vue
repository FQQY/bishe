<template>
  <div>
    <a-card class="login-card">
			<slot name="title"></slot>

			<a-form
				ref="baseFormRef"
				:model="props.formState"
				class="base-form"
				layout="vertical"
				:rules="props.rules"
				@finish="onFinish"
				@finishFailed="onFinishFailed"
				hideRequiredMark
			>
        <a-form-item>
          <slot name="formItems" :baseFormRef="baseFormRef"/>
          <a-form-item>
            <a-button
              type="ghost"
              size="large"
              block
              @click="resetForm"
            >
              重置
            </a-button>
          </a-form-item>
        </a-form-item>
			</a-form>

			<slot name="footer"></slot>
		</a-card>
  </div>
</template>

<script setup>
  import { ref, getCurrentInstance } from "vue"

	import { MyNotification } from "@/utils/util"

  const props = defineProps({
    formState:{
      type: Object,
      required: true
    },
    rules: {
      type: Object,
    },
    errorMsg: String
  })

	// 获取当前组件实例对象
	const { proxy } = getCurrentInstance()

	// 提交表单且数据验证成功后回调事件
	const onFinish = (values) => {
		proxy.$emit("onFinish",values)
	}

	// 提交表单且数据验证失败后回调事件
	const onFinishFailed = () => {
		// 登录失败 提示用户
		console.log("Failed:", props.errorInfo)
		MyNotification("warning",props.errorMsg,"您输入的格式有误，请检查完毕后再次登录！")
	}
	// 清空表单
	const baseFormRef = ref()

	const resetForm = (e) => {
		baseFormRef.value.resetFields()
		MyNotification("success","已重置")
	}
</script>

<style lang="less">
  .login-card {
		width: 40rem;
		box-shadow: 0 2px 5px 1px rgb(64 60 67/16%);
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		border-radius: .5rem;
    .base-form {
      width: 100%;
    }
		.logo {
			padding-bottom: 1rem;
		}

		.welcome {
			text-align: left;

			h4 {
				color: #5e5873;
				font-weight: 500;
				letter-spacing: 0.05rem;
				font-size: 1.78rem;
			}
			p {
				color: #697a8d;
				font-size: 14px;
			}
		}
	}
</style>