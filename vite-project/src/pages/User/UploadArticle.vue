<template>
  <div class="upload-article-wrapper">
    <a-form
      :model="formState"
      ref="addArticleForm"
      :label-col="{ span: 8 }"
      autocomplete="off"
      layout="vertical"
      :rules="rules"
    >
      <div class="form-content">
        <div class="form-left">
          <a-form-item
            label="标题"
            name="workTitle"
          >
            <a-input v-model:value="formState.workTitle" />
          </a-form-item>
          
          <a-form-item
            label="分类"
            name="catId"
            :wrapper-col="{ span: 2 }"
          >
            <a-select
              ref="select"
              v-model:value="formState.catId"
              style="width: 120px"
            >
              <a-select-option value="default">--请选择--</a-select-option>
              <a-select-option 
                v-for="category in categories" 
                :key="category.catId" 
                :value="category.catId"
              >
                {{category.catName}}
              </a-select-option>
              
            </a-select>
          </a-form-item>
        </div>
        <div>
          <a-form-item
            label="内容"
            name="workContent"
          >
            <Editor
              api-key="ngky2m01rfttpl2xbtn0kqxl9q3yt2jsy5sfm4wa2rvlec1b"
              :init="init"
              cloud-channel="6"
              :inline="false"
              v-model="formState.workContent"
            />
          </a-form-item>
        </div>
          

      </div>
          
      <a-form-item>
        <a-button @click="sendArticle">发布</a-button>
      </a-form-item>
    </a-form>
    
  </div>
</template>

<script setup>
  import { reactive, ref } from 'vue';

  import Editor from '@tinymce/tinymce-vue' 
  import { uploadArticle } from '@/utils/request/api';
  import { MyNotification } from '@/utils/util';

	const init = reactive({
    height: 500,
    language:'zh_CN',//注意大小写
    theme:'silver',
    fontsize_formats: "8pt 10pt 12pt 14pt 16pt 18pt 20pt 22pt 24pt 36pt",
    font_formats: "微软雅黑='微软雅黑';宋体='宋体';黑体='黑体';仿宋='仿宋';楷体='楷体';隶书='隶书';幼圆='幼圆';Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Trebuchet MS=trebuchet ms,geneva;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings",
    plugins: [
      'advlist', 'autolink', 'link', 'image', 'lists', 'charmap', 'preview', 'anchor', 'pagebreak',
      'searchreplace', 'wordcount', 'visualblocks', 'visualchars', 'code', 'fullscreen', 'insertdatetime',
      'media', 'table', 'emoticons', 'template', 'help'
    ],
    toolbar:'undo redo | styles | fontfamily fontsize | bold italic | alignleft aligncenter alignright alignjustify | outdent indent'
  })

  const categories = JSON.parse(localStorage.getItem('categories'))
  const userData = JSON.parse(localStorage['userData'])
  const addArticleForm = ref(null)
  const formState = reactive({
    workTitle: '',
    workContent: '',
    catId: "default"
  })
  const rules = {
    workTitle: [
      { required: true, message: '文章标题不能为空！', trigger: ['blur','change']}
    ],
    workContent: [
      { required: true, message: '文章内容不能为空！', trigger: ['blur','change']}
    ]
  }

  const sendArticle = () => {
    if(userData.banFlag === '1') {
      MyNotification("error","警告","您的账号已经被封禁，不能发布文章")
      return
    }
    postArticle()
    addArticleForm.value.resetFields()
    formState.catId = "default"
  }

  const postArticle = async() =>{

    const data = await uploadArticlet(userData.usrId,...formState)
    data > 0 ?
    MyNotification("success","发布文章成功！") :
    MyNotification("error","发布文章失败！")

  }



</script>

<style lang="less" scoped>
  .upload-article-wrapper {
    padding: 16px 24px;
    background-color: #fff;
    margin: 16px 24px;
  }

  .form-content {
    display: flex;
    justify-content: space-between;
  }
</style>