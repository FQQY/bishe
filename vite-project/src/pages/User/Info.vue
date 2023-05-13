<template>
  <div class="info-wrapper">
    <a-descriptions title="用户信息" bordered>
      <template #extra>
        <a-button type="primary" @click="editModal">编辑</a-button>
      </template>
      <a-descriptions-item label="昵称">{{userInfo.usrName}}</a-descriptions-item>
      <a-descriptions-item label="邮箱" :span="2">{{userInfo.usrEmail}}</a-descriptions-item>
      <a-descriptions-item label="性别">{{userInfo.usrSex ? '女' : '男'}}</a-descriptions-item>
      <a-descriptions-item label="密码" :span="2">
        <a-space :size="20">
          <span>{{'*******'}}</span>
          <a-button 
            type="primary" 
            :style="{fontSize: '12px'}" 
            size="small" @click="visible1 = true" 
          >修改</a-button>
        </a-space>
      </a-descriptions-item>
      <a-descriptions-item label="用户身份" :span="3">
        <a-space :size="50">
          {{userInfo.usrAuthority === '1' ? '审核员':'普通用户'}}
          <a-popconfirm
            title="确定申请成为审核员?"
            @confirm="handleApply"
            v-if="userInfo.usrAuthority === '0'"
          >
            <a-button
              type="primary"
              size="small"
              :style="{fontSize: '12px'}"
            > 
              {{userInfo.askFlag !== '0'?'申请中...':'申请'}}
            </a-button>
          </a-popconfirm>
        </a-space>
      </a-descriptions-item>
      <a-descriptions-item label="账号状态" :span="3">
        <a-badge status="processing" :text="userInfo.banFlag === '1' ? '封禁': '正常'" >

        </a-badge>
      </a-descriptions-item>
     
    </a-descriptions>

    <a-button 
      type="primary" 
      size="large" 
      :style="{marginTop: '24px'}" 
      block 
      danger
      @click="onCancelUser"
    >
      注销账号
    </a-button>
     
    <a-modal v-model:visible="visible" title="编辑信息">
      <a-form layout="vertical" :model="formState" ref="infoForm" :rules="rules">
        <a-form-item label="昵称" name="usrName">
          <a-input v-model:value="formState.usrName" placeholder="input placeholder" />
        </a-form-item>
        <a-form-item label="邮箱" name="usrEmail">
          <a-input v-model:value="formState.usrEmail" placeholder="input placeholder" />
        </a-form-item>
        <a-form-item label="性别" name="usrSex">
          <a-radio-group v-model:value="formState.usrSex">
            <a-radio :value="0">男</a-radio>
            <a-radio :value="1">女</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
      <template #footer>
        <a-button key="back" @click="handleCancel">取消</a-button>
        <a-button key="submit" type="primary" @click="handleOk">确定</a-button>
      </template>
    </a-modal>

    <a-modal v-model:visible="visible1" title="修改密码">
      <a-form layout="vertical" :model="formState1" ref="pwdForm" :rules="rules1">
        <a-form-item label="密码" name="password">
          <a-input-password v-model:value="formState1.password" placeholder="请输入修改的密码" />
        </a-form-item>
        <a-form-item label="确认密码" name="checkPassword">
          <a-input-password v-model:value="formState1.checkPassword" placeholder="请再次输入修改后的密码" />
        </a-form-item>
      </a-form>
      <template #footer>
        <a-button key="back" @click="handleCancel1">取消</a-button>
        <a-button key="submit" type="primary" @click="handleOk1">确定</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script setup>
  import axios from 'axios';
  import { ref, reactive, computed, getCurrentInstance } from 'vue'
  import { useUserStore } from '@/store/userStore'

  import { MyNotification } from '@/utils/util';
  import { 
    launchUserApply,
    updateUserInfo,
    delUser
  } from '@/utils/request/api';
  import { useRouter } from 'vue-router';

  const userInfo = reactive(JSON.parse(localStorage.getItem('userData')))
  const userStore = useUserStore()
  const { proxy } = getCurrentInstance()
  const router = useRouter()


  const handleApply = async() => {
    if(userInfo.askFlag === "1") {
      MyNotification("error","申请错误","您已提交过申请，请耐心等待")
      return
    }
    // 发送请求 更新用户的askFlag
    const data = await launchUserApply(userInfo.usrId)
    if(data) {
      // 更新用户数据到浏览器存储
      userInfo.askFlag = "1"
      localStorage.setItem("userData",JSON.stringify(data))
      MyNotification("success","申请已提交","您的请求已提交至后台，请等待管理员对您的账号进行审核后，然后同意您的申请")
    }
  }

  // 修改个人信息
  const visible = ref(false);
  const infoForm = ref(null)
  const rules = reactive({
    usrName:[
      { required: true, message: '必须填写昵称', trigger: ["change", "blur"] }
    ],
    usrEmail:[
      {
				required: true,
				message: "必须填写邮箱！",
				trigger: ["change", "blur"],
			},
			{
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
				pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$/,
				message: "请确保邮箱格式正确！",
				trigger: "blur",
			},
    ],
    usrSex:[
      { required: true, message: '必须选择一个性别', trigger: ["change", "blur"]}
    ],
  })
  const editModal = () => {
    visible.value = true;
  };
  const handleCancel = () => {
    visible.value = false
  }
  const handleOk = async() => {

    const data = await updateUserInfo({
      usrId: userInfo.usrId,
      usrName: formState.usrName,
      usrEmail: formState.usrEmail,
      usrSex: formState.usrSex
    })
    // 将更新之后的用户信息存放到pinia中
    userStore.$patch({
      userData: data
    })
    localStorage.setItem('userData',JSON.stringify(data))
    userInfo.usrName = data.usrName
    userInfo.usrEmail = data.usrEmail
    userInfo.usrSex = data.usrSex
    MyNotification("success","成功","已更新")

    visible.value = false;
  };

  const formState = reactive({
    usrName: userInfo.usrName,
    usrEmail: userInfo.usrEmail,
    usrSex: userInfo.usrSex,
  });
  
  const checkPassword = (rule, value) => {
		if(value.trim() === '') return Promise.reject("不能为空！");
		return value === formState1.password 
			? Promise.resolve()
			: Promise.reject("两次输入的密码不一样！");
	}
  // 更改密码
  const visible1 = ref(false)
  const pwdForm = ref(null)
  const rules1 ={
    password: [
      { required: true, message: '密码不能为空', trigger: ['change', 'blur'] },
      { pattern: /^[A-Za-z0-9_.]+$/, message: "您输入的密码包含除_和.之外的字符!", trigger: "blur" },
    ],
    checkPassword: [
			{ validator: checkPassword, trigger: 'blur' }
    ]
  }

  const formState1 = reactive({
    password: '',
    checkPassword: ''
  })

  const handleCancel1 = () =>{
    pwdForm.value.resetFields()
    visible1.value = false;
  }
  const handleOk1 = async() =>{

    const data = await updateUserInfo({
      usrId: userInfo.usrId,
      usrPassword: formState1.password
    })
    // 将更新之后的用户信息存放到pinia中
    userStore.$patch({
      userData: data
    })
    localStorage.setItem('userData',JSON.stringify(data))
    userInfo.usrPassword = data.usrPassword
      

    pwdForm.value.resetFields()
    visible1.value = false;
    
  }

  const onCancelUser = async() => {
    const data = await delUser(usrId)
    if(data){
      MyNotification("success","成功","此账号已注销")
      router.push("/")
    }

  }

</script>

<style lang="less" scoped>
  .info-wrapper {
    padding: 1rem;
    width: 100%;
    height: 100%;
    background-color: #fff;
  }
</style>