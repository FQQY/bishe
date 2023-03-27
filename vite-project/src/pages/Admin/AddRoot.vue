<template>
  <div>
    <BaseForm
			:rules="rules"
			:formState="formState"
			errorMsg="添加失败"
			@onFinish="onFinish"
		>

      <template #title>
        <h2>添加管理员</h2>
      </template>
      <template #formItems>
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
						确定
					</a-button>
				</a-form-item>
      </template>
    
    </BaseForm>
  </div>
</template>

<script setup>
	import { ref } from 'vue';
  import BaseForm from '@/components/BaseForm.vue';

	import { getData, postData } from '@/utils/request/http';
	import { MyNotification, MyMd5 } from '@/utils/util';

	const formState = ref({
		number: '',
		password: '',
		checkPassword: ''
	})

	const checkNumber = async(rule, value) => {
		if(value.trim() === '') return Promise.reject("不能为空！");
		const data = await getData("/query/exist/rootaccountnumber",{rootAccountNumber:value})
		if(data > 0) {
			return Promise.reject("您输入的账号已存在")
		}else {
			return Promise.resolve()
		}
	}

	const checkPassword = (rule, value) => {
		console.log(value);
		if(value.trim() === '') return Promise.reject("不能为空！");
		return value === formState.value.password 
			? Promise.resolve()
			: Promise.reject("两次输入的密码不一样！");
	}

	const rules = {
		number: [
			{ validator: checkNumber, trigger: 'blur' }
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
	}

	const onFinish = async(values) => {
		values.password = MyMd5(values.password)
		const data = await postData("/add/root",{
			rootAccountNumber: values.number,
			rootPassword: values.password
		})

		if(data > 0) {
			MyNotification("success","成功","添加管理员成功")
		}
	}
</script>

<style lang="less" scoped>

</style>