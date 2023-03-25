import {getData, postData} from "@/utils/request/http"

/**
 * @func Login
 * @desc 发送登录请求
 * @param {formData} 接收用户在登录页面填写的数据  
 * @return {Promise} 返回调用封装的post返回的promise
 */
function Login(formData) {
  let isRoot = formData.authority === "2"
  let path = isRoot? "admin":"user"
  let url = `/${path}/login`
  let params

  if(isRoot) {
    params = {
      rootAccountNumber: formData.number,
      rootPassword: formData.password
    }
  } else {
    params = {
      usrEmail: formData.number,
      usrPassword: formData.password
    }
  }
  return postData(url,params)
}

/**
 * @func Register
 * @desc 注册接口
 * @param {val} 用户在注册页面填写的数据 
 * @return {Promise} 
 */
function Register(val) {
  let params = {
    usrName: val.username,
    usrEmail: val.email,
    usrPassword: val.password
  }

  return postData("/register",params)
}

/**
 * @func confirmEmail
 * @desc 确认邮箱是否注册接口
 * @param {params} 请求参数 get 
 * @return {Promise} 
 */
function confirmEmail(params) {
  return getData("/confirmEmail",params)
}

/**
 * @func getVideosPage
 * @desc 获取已经发布的视频 具有分页功能
 * @param {page, size, workTitle=null} 请求参数  
 * @return {Promise} 
 */
function getVideosPage(page, size, workTitle=null) {
  return getData("/query/page/videos",{
    page,
    size,
    workTitle
  })
}

/**
 * @func getArticlesPage
 * @desc 获取已经发布的文章 具有分页功能
 * @param {page, size, workTitle=null} 请求参数  
 * @return {Promise} 
 */
function getArticlesPage(page, size, workTitle=null) {
  return getData("/query/page/articles",{
    page,
    size,
    workTitle
  })
}


/**
 * @func getVideosPageByCategory
 * @desc 获取分类下已经发布的视频 具有分页功能
 * @param {page, size, catId=null} 请求参数  
 * @return {Promise} 
 */
function getVideosPageByCategory(page, size, catId=null) {
  return getData("/query/category/videos",{
    page,
    size,
    catId
  })
}

/**
 * @func getArticlesPageByCategory
 * @desc 获取分类下已经发布的文章 具有分页功能
 * @param {page, size, catId=null} 请求参数  
 * @return {Promise} 
 */
function getArticlesPageByCategory(page, size, catId=null) {
  return getData("/query/category/articles",{
    page,
    size,
    catId
  })
}

/**
 * @func addHistory
 * @desc 添加到历史记录
 * @param {usrId, workId} 将获取到的用户id和作品id添加到历史记录  
 * @return {Promise} 
 */
function addHistory(usrId, workId) {
  return postData("/add/history",{usrId, workId})
}

/**
 * @func getNotices
 * @desc 获取所有公告
 * @param {}  
 * @return {Promise} 
 */
function getNotices() {
  return getData("/query/notices")
}

/**
 * @func getCotegories
 * @desc 获取所有的视频分类
 * @param {}  
 * @return {Promise} 
 */
function getCotegories() {
  return getData("/query/categories")
}

/**
 * @func sendComment
 * @desc 发送评论接口
 * @param {usrId workId comtContent} 用户id 作品id 评论内容 
 * @return {Promise} 
 */
function sendComment(usrId, workId, comtContent) {
  return postData("/add/comment",{
    usrId,
    workId,
    comtContent
  })
}

/**
 * @func createFavoriteBag
 * @desc 创建收藏夹
 * @param {usrId favbName} 用户id 收藏夹名称
 * @return {Promise} 
 */
function createFavoriteBag(usrId, favbName) {
  return postData("/add/favoritebag", { usrId, favbName })
}

/**
 * @func addFavorite
 * @desc 添加收藏
 * @param {usrId workId favbIds} 用户id 作品id 选择的收藏夹id集合
 * @return {Promise} 
 */
function addFavorite(usrId, workId, favbIds) {
  return postData("/add/favorite", {
    usrId,
    workId,
    favbIds
  })
}

/**
 * @func getUserFavoriteBag
 * @desc 获取用户的所有收藏夹
 * @param {usrId} 用户id 
 * @return {Promise} 
 */
function getUserFavoriteBags(usrId) {
  return getData("/query/user/favoritebags",{usrId})
}

/**
 * @func addPlayList
 * @desc 添加到稍后播放
 * @param {usrId workId} 用户id 作品id
 * @return {Promise} 
 */
function addPlayList(usrId, workId) {
  return postData("/add/playlist",{usrId, workId})
}

/**
 * @func getWorkComments
 * @desc 获取作品的所有评论
 * @param {workId} 作品id 
 * @return {Promise} 
 */
function getWorkComments(workId) {
  return getData("/query/work/comments",{ workId })
}

/**
 * @func getTags
 * @desc 获取作评的所有标签
 * @param {workId} 作品id  
 * @return {Promise} 
 */
function getTags(workId) {
  return getData("/query/video/tags", { workId })
}

/**
 * @func launchUserApply
 * @desc 标记用户请求接口
 * @param {usrId} 用户id 
 * @return {Promise} 
 */
function launchUserApply(usrId) {
  return postData("/update/send/userask",{usrId})
}

/**
 * @func updateUserInfo
 * @desc 更新用户信息接口 更新基本信息和修改密码都用到
 * @param {param} 请求参数 
 * @return {Promise} 
 */
function updateUserInfo(params) {
  return postData("/update/user/info",params)
}

/**
 * @func getHistories
 * @desc 获取用户的历史记录
 * @param {usrId} 用户id 
 * @return {Promise} 
 */
function getHistories(usrId) {
  return getData("/query/work/history", {usrId})
}

/**
 * @func delWorkHistory
 * @desc 删除用户的一条历史记录
 * @param {usrId workId} 用户id 作品id
 * @return {Promise} 
 */
function delWorkHistory(usrId, workId) {
  return postData("/delete/hsitory", {usrId, workId})
}

/**
 * @func getUser
 * @desc 获取用户的信息
 * @param {usrId} 用户id 
 * @return {Promise} 
 */
function getUser(usrId) {
  return getData("/query/user", { usrId })
}

/**
 * @func getCategory
 * @desc 获取分类
 * @param {catId} 分类id
 * @return {Promise} 
 */
function getCategory(catId) {
  return getData("/query/category", { catId})
}

/**
 * @func delUserFavorite
 * @desc 删除收藏
 * @param {favId usrId} 收藏id  用户id    
 * @return {Promise} 
 */
function delUserFavorite(favId, usrId) {
  return postData("/delete/favorite", { favId, usrId })
}

/**
 * @func getPlayList
 * @desc 获取用户的稍后播放记录
 * @param {usrId} 用户id 
 * @return {Promise} 
 */
function getPlayList(usrId) {
  return getData("/query/work/playlist", {usrId})
}

/**
 * @func delWorkPlayList
 * @desc 删除用户的一条稍后播放记录
 * @param {usrId workId} 用户id 作品id
 * @return {Promise} 
 */
function delWorkPlayList(usrId, workId) {
  return postData("/delete/hsitory", {usrId, workId})
}

/**
 * @func delVideo
 * @desc 删除视频
 * @param {usrId workId}  用户id 作品id
 * @return {Promise} 
 */
function delVideo(usrId, workId) {
  return postData("/delete/video",{ usrId, workId})
}

/**
 * @func getUserUploadVideos
 * @desc 获取用户上传的视频
 * @param {usrId page size} 用户id 当前页 每页尺寸
 * @return {Promise} 
 */
function getUserUploadVideos(usrId, page, size) {
  return getData("/query/upload/videos",{usrId, page, size})
}

/**
 * @func uploadArticle
 * @desc 用户上传文章接口
 * @param {usrId workTitle workContent catId} 用户id 作品标题 作品内容 分类id 
 * @return {Promise} 
 */
function uploadArticle(usrId, workTitle, workContent, catId) {
  return postData("/add/article",{usrId, workTitle, workContent, catId})
}

/**
 * @func updateNoticeLevel
 * @desc 修改公告的等级
 * @param {notId notLevel} 公告id 公告级别
 * @return {Promise} 
 */
function updateNoticeLevel(notId, notLevel) {
  return postData("/update/notice",{notId, notLevel})
}

/**
 * @func delNotice
 * @desc 删除公告
 * @param {notId} 公告id
 * @return {Promise} 
 */
function delNotice(notId) {
  return postData("/delete/notice",{notId})
}

/**
 * @func getNoticesPage
 * @desc 获取所有的公告
 * @param {page size} 当前页 每页的尺寸
 * @return {Promise} 
 */
function getNoticesPage(page, size) {
  return getData("/query/page/notices",{page, size})
}

/**
 * @func addNotice
 * @desc 添加公告
 * @param {rootId notContent notLevel} 管理员id 公告内容 公告等级
 * @return {Promise} 
 */
function addNotice(rootId, notContent, notLevel) {
  return postData("/add/notice",rootId, notContent, notLevel)
}

/**
 * @func getWorksPage
 * @desc 获取所有上传的作品
 * @param {page size}  
 * @return {Promise} 
 */
function getWorksPage(page, size) {
  return getData("/query/all/page/works",{page, size})
}

/**
 * @func delWork
 * @desc 管理员删除作品接口
 * @param {workId}  
 * @return {Promise} 
 */
function delWork(workId) {
  return postData("/delete/work",{workId})
}

/**
 * @func getUsersPage
 * @desc 获取所有用户 分页
 * @param {page size}  
 * @return {Promise} 
 */
function getUsersPage(page, size) {
  return getData("/query/page/users",{page, size})
}

/**
 * @func delUser
 * @desc 管理员注销用户接口
 * @param {usrId}  
 * @return {Promise} 
 */
function delUser(usrId) {
  return postData("/delete/user",{usrId})
}

/**
 * @func getUserAskList
 * @desc 获取所有用户的申请 
 * @param {page size}  
 * @return {Promise} 
 */
function getUserAskList(page, size) {
  return getData("/query/user/ask",{page, size})
}

/**
 * @func agreeUserAsk
 * @desc 同意用户请求接口
 * @param {usrId}  
 * @return {Promise} 
 */
function agreeUserAsk(usrId) {
  return postData("/update/agree/userask",{usrId})
}

/**
 * @func refuseUserAsk
 * @desc 拒绝用户请求接口
 * @param {usrId}  
 * @return {Promise} 
 */
function refuseUserAsk(usrId) {
  return postData("/update/refuse/userask",{usrId})
}

/**
 * @func delFavoriteBag
 * @desc 删除收藏夹接口
 * @param {usrId favbId} 用户id 收藏夹id
 * @return {Promise} 
 */ 
function delFavoriteBag(usrId, favbId) {
  return postData("/delete/favoritebag",{usrId, favbId})
}

/**
 * @func clearFavoriteBag
 * @desc 清空收藏夹接口
 * @param {usrId favbId} 用户id 收藏夹id
 * @return {Promise} 
 */ 
function clearFavoriteBag(usrId, favbId) {
  return postData("/delete/clear/favoritebag",{usrId, favbId})
}


export { 
  Login, 
  Register, 
  confirmEmail, 
  getVideosPage, 
  getArticlesPage,
  getVideosPageByCategory, 
  getArticlesPageByCategory,
  addHistory,
  getNotices,
  getCotegories,
  sendComment,
  createFavoriteBag,
  addFavorite,
  getUserFavoriteBags,
  addPlayList,
  getWorkComments,
  getTags,
  launchUserApply,
  updateUserInfo,
  getHistories,
  delWorkHistory,
  getUser,
  getCategory,
  delUserFavorite,
  getPlayList,
  delWorkPlayList,
  delVideo,
  getUserUploadVideos,
  uploadArticle,
  updateNoticeLevel,
  delNotice,
  getNoticesPage,
  addNotice,
  getWorksPage,
  delWork,
  getUsersPage,
  delUser,
  getUserAskList,
  agreeUserAsk,
  refuseUserAsk,
  delFavoriteBag,
  clearFavoriteBag
}