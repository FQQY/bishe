import { createRouter, createWebHashHistory } from 'vue-router'


const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../pages/Login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../pages/Register.vue')
  },
  {
    path: '/user',
    name: 'user',
    redirect: '/user/index',
    component: () => import('../pages/User/UserLayout.vue'),
    children: [
      {
        path: 'center',
        name: 'usercenter',
        redirect: '/user/center/info',
        component: () => import('../pages/User/UserCenter.vue'),
        children:[
          {
            path: 'info',
            name: 'userinfo',
            component: () => import('../pages/User/Info.vue')
          },
          {
            path: 'history',
            name: 'videohistory',
            component: () => import('../pages/User/History.vue')
          },
          {
            path: 'favorite',
            name: 'videofavorite',
            component: () => import('../pages/User/Favorite.vue')
          },
          {
            path: 'playlist',
            name: 'playlist',
            component: () => import('../pages/User/PlayList.vue')
          },
          {
            path: 'uploadvideo',
            name: 'uploadvideo',
            component: () => import('../pages/User/UploadVideo.vue')
          },
          {
            path: 'uploadarticle',
            name: 'uploadarticle',
            component: () => import('../pages/User/UploadArticle.vue')
          },
          {
            path: 'audit',
            name: 'videoaudit',
            component: () => import("../pages/User/Audit.vue")
          }
        ]
      },
      {
        path: 'index',
        name: 'userindex',
        component: () => import('../pages/User/UserIndex.vue')
      },
      {
        path: 'video/:workId',
        name: 'showvideo',
        component: () => import('../pages/User/ShowVideo.vue'),
        props: true
      },
      {
        path: 'article/:workId',
        name: 'showarticle',
        component: () => import('../pages/User/ShowAricle.vue'),
        props: true
      }
    ],
    meta: { isAuthenticated: true }
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import("../pages/Admin/AdminLayout.vue"),
    redirect: '/admin/systeminfo',
    children: [
      {
        path: 'systeminfo',
        name: 'systeminfo',
        component: () => import('../pages/Admin/SystemInfo.vue')
      },
      {
        path: 'addnotice',
        name: 'addnotice',
        component: () => import('../pages/Admin/AddNotice.vue')
      },
      {
        path: 'videomanage',
        name: 'videomanage',
        component: () => import('../pages/Admin/VideoManage.vue')
      },
      {
        path: 'banuser',
        name: 'banuser',
        component: () => import('../pages/Admin/BanUser.vue')
      },
      {
        path: 'askuser',
        name: 'askuser',
        component: () => import('../pages/Admin/AskUser.vue')
      },
    ],
    meta: { isAuthenticated: true }
  },
  // 将匹配所有内容并将其放在 `$route.params.pathMatch` 下
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('../pages/NotFound.vue')} 
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})


router.beforeEach((to, from, next) => {
  // 判断用户是否登录
  if(to.meta.isAuthenticated) {
    let userData = localStorage.getItem("userData")
    if(userData == null){
      next(from.fullPath)
    }else {
      next()
    }
  }else {
    next()
  }

})

export default router;