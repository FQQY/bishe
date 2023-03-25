<template>
	<div id="wrapper">
		<BaseForm
			:rules="rules"
			:formState="formState"
			errorMsg="登录失败"
			@onFinish="onFinish"
		>
			<template #title>
				<div class="logo">
					<a-image src="../../public/logo.png" :width="150" />
				</div>
				<div class="welcome">
					<h4>Welcome to 雪茄达摩鲨👋</h4>
					<p>请登录您的账号开始体验！</p>
				</div>
			</template>

			<template #formItems="{baseFormRef}"> 
				<a-form-item label="账号" name="number" has-feedback>
					<a-input
						id="number"
						v-model:value="formState.number"
						size="large"
						placeholder="用户输入邮箱，管理员输入账号"
					></a-input>
				</a-form-item>

				<a-form-item label="密码" name="password" has-feedback>
					<a-input-password
						id="password"
						v-model:value="formState.password"
						size="large"
						placeholder="Password"
						autocomplete="off"
					></a-input-password>
				</a-form-item>

				<a-form-item :wrapperCol="{ span: 18 }" name="authority">
					<a-radio-group
						v-model:value="formState.authority"
						name="authority"
						@change="baseFormRef.validate()"
					>
						<a-radio value="0">普通用户</a-radio>
						<a-radio value="1">审核员</a-radio>
						<a-radio value="2">管理员</a-radio>
					</a-radio-group>
				</a-form-item>

				<a-form-item>
					<a-button
						type="primary"
						html-type="submit"
						class="login-form-button"
						size="large"
						block
					>
						登录
					</a-button>
				</a-form-item>

			</template>

			<template #footer>
				<p>
					<span style="color: #aaa">还没有账号？</span>
					<router-link to="/register">马上注册 👉</router-link>
				</p>
			</template>
		</BaseForm>
	</div>
</template>

<script setup>
	import { reactive } from "vue"
	import { useRouter } from 'vue-router'
	import { useUserStore } from '@/store/userStore'

	import { Login } from '@/utils/request/api'
	import { MyNotification, MyMd5 } from "@/utils/util"

	import BaseForm from '@/components/BaseForm.vue';

	const formState = reactive({
		number: "",
		password: "",
	  authority: "0"
	})

	const router = useRouter()

	const checkNumber = (rule,value) => {
		if (value.trim() !== "") {
			if (formState.authority === "2") {
				// 管理员身份登录 number代表管理员账号
				let pattern = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/
				return pattern.test(value)
					? Promise.resolve()
					: Promise.reject("管理员账号不应该包含字符！")
			} else {
				// 其他身份登录 number代表用户邮箱
				//#region
				/*
	         ^：表示匹配字符串的开头。
	         [a-zA-Z0-9._%+-]+：表示匹配邮箱用户名部分，其中包含字母、数字、点（.）、下划线（_）、百分号（%）、加号（+）和减号（-）等字符，并且至少出现一次。
	         @：表示匹配邮箱地址中的“@”符号。
	         [a-zA-Z0-9.-]+：表示匹配邮箱域名部分，其中包含字母、数字、点（.）和减号（-）等字符，并且至少出现一次。
	         \.：表示匹配邮箱域名中的“.”符号，需要使用反斜杠进行转义。
	         [a-zA-Z]{2,}：表示匹配邮箱域名的顶级域名部分，其中只包含字母，并且至少出现两个字符。
	         $：表示匹配字符串的结尾。
	       */
				//#endregion
				let pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$/
				return pattern.test(value)
					? Promise.resolve()
					: Promise.reject("请确保邮箱格式正确！")
			}
		}
	  return Promise.reject("邮箱或账号不能为空！")
	}

	const rules = {
		number: [
			{ require: true, validator: checkNumber, tirgger: "blur" },
		],
		password: [
			{
				required: true,
				message: "密码为必填项！",
				trigger: ["change", "blur"],
			},
			{
				pattern: /^[A-Za-z0-9_.]+$/,
				message: "您输入的密码包含除_和.之外的字符!",
				trigger: "blur",
			},
		],
	}

	const userStore = useUserStore()
	// 提交表单且数据验证成功后回调事件
	const onFinish = async(values) => {
		console.log("Success:", values)
		console.log("md5-password",MyMd5(values.password));
		// 第一次md5加密
		values.password = MyMd5(values.password)
		const data = await Login(values)

		let isRoot = values.authority === "2"
		// 登录失败账号不存在
		if(data == '') {
      MyNotification("error","登录失败",() => (isRoot ? '账号' : "邮箱") + "或密码输入错误，请重新登录！")
      return
    }
		// 管理员登录
		if(values.authority === "2") {
			localStorage.setItem("adminData",JSON.stringify(data))
			router.push("/admin")
			return
		}
		// 用户被封禁
    // if(data.banFlag === "1") {
    //   MyNotification("warning","你的账号已被封禁")
    //   return
    // }
		// 登录身份选择 错误
		if(data.usrAuthority !== values.authority) {
			MyNotification("warning","登录身份选择错误")
			return
		}

		// 身份验证成功
		// 登录成功 提示用户 1秒后跳转首页
		MyNotification("success","登录成功")
		localStorage.setItem("authority",values.authority)
		localStorage.setItem("isShowNotice",1)
		userStore.userData = data;
		localStorage.setItem("userData", JSON.stringify(data))
		router.push("/user")
		return
	}

</script>

