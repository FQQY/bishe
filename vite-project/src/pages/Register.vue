<template>
	<div id="wrapper">
		<BaseForm
			:rules="rules"
			:formState="formState"
			errorMsg="注册失败"
			@onFinish="onFinish"
		>
			<template #title>
				<div class="logo">
					<a-image src="../../public/logo.png" :width="150" />
				</div>
				<div class="welcome">
					<h4>Start here System! 🚀</h4>
						<p>请注册您的账号开始体验！</p>
				</div>
			</template>

			<template #formItems>
				<a-form-item label="昵称" name="username" has-feedback>
					<a-input
						id="username"
						v-model:value="formState.username"
						size="large"
						placeholder="Username"
					></a-input>
				</a-form-item>

				<a-form-item label="邮箱" name="email" has-feedback>
					<a-input
						id="email"
						v-model:value="formState.email"
						size="large"
						placeholder="Email"
					></a-input>
				</a-form-item>

				<a-form-item label="密码" name="password" has-feedback>
					<a-input-password
						id="password"
						v-model:value="formState.password"
						size="large"
						placeholder="Password"
					></a-input-password>
				</a-form-item>

				<a-form-item label="确认密码" name="checkPassword" has-feedback>
					<a-input-password
						id="checkPassword"
						v-model:value="formState.checkPassword"
						size="large"
						placeholder="Confirm your password"
					></a-input-password>
				</a-form-item>

				<a-form-item>
					<a-button
						type="primary"
						html-type="submit"
						class="login-form-button"
						size="large"
						block
					>
						注册
					</a-button>
				</a-form-item>
			</template>

			<template #footer>
				<p>
					<span style="color: #aaa">已有账号？</span>
					<router-link to="/">马上登录 👉</router-link>
				</p>
			</template>
		</BaseForm>
	
	</div>
</template>

<script setup>
	import { reactive } from "vue";
  import { useRouter } from 'vue-router'
	import { useUserStore } from '@/store/userStore'

	import { Register, confirmEmail } from '@/utils/request/api'
	import { MyNotification, MyMd5 } from "@/utils/util"

	import BaseForm from '@/components/BaseForm.vue';

	const formState = reactive({
		username: "",
		email: "",
		password: "",
		checkPassword: ""
	});

  const router = useRouter();

	const checkEmail = async(rule,value) => {
		if (value.trim() !== "") {

			let pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$/
			if(pattern.test(value)) {
				// 发送请求确认输入的邮箱是否注册过
				let data = await confirmEmail({
					email: formState.email
				})
				
				if(data > 0) {
					return Promise.reject("该邮箱已经被注册过了！");
				}else {
					return Promise.resolve();
				}

			} else {
				return Promise.reject('邮箱格式不对！');
			}
			
		}
    return Promise.reject("邮箱不能为空！");
	};

	const checkPassword = (rule, value) => {
		if(value.trim() === '') return Promise.reject("不能为空！");
		return value === formState.password 
			? Promise.resolve()
			: Promise.reject("两次输入的密码不一样！");
	}

	const rules = {
		username: [
			{ required: true, message: '昵称不能为空！', trigger: "blur" },
		],
		email: [
			{ validator: checkEmail, trigger: 'blur' },
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
		checkPassword: [
			{ validator: checkPassword, trigger: 'blur' }
		]
	};

	const userStore = useUserStore()
  // 提交表单且数据验证成功后回调事件
	const onFinish = async(values) => {
		console.log("Success:", values);
		console.log("md5-password",MyMd5(values.password));
		// 第一次md5加密
		values.password = MyMd5(values.password)
    //请求后端 注册接口
		const data = await Register(values)
		// 
		if(data === "") {
			MyNotification("warning","注册失败")
		}
		// 注册成功 像浏览器存贮登陆身份 跳转页面 提示用户
		localStorage.setItem("authority",0);
		localStorage.setItem("isShowNotice",1)
		userStore.userData = data
		localStorage.setItem("userData", JSON.stringify(data))
		setTimeout(() => {
			router.push('/')
		}, 1000);
		MyNotification("success","注册成功!","请登录")
	};

</script>

