import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
// 导入VueRouter@4
import router from './router'
import { createPinia } from 'pinia'

import { notification,message,Modal } from 'ant-design-vue'
import 'ant-design-vue/es/notification/style/css';
import 'ant-design-vue/es/message/style/css';
import 'ant-design-vue/es/Modal/style/css';
// 阿里巴巴矢量图标库配置
import './assets/icon/iconfont'
// 事件总线
import eventBus from 'vue3-eventbus'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime';
import UpdateLocale from 'dayjs/plugin/UpdateLocale';
import minMax from 'dayjs/plugin/minMax'

dayjs.extend(relativeTime);
dayjs.extend(UpdateLocale);
dayjs.extend(minMax)
dayjs.updateLocale('en', {
  relativeTime: {
    future: "%s后",
    past: "%s前",
    s: '几秒',
    m: "1 分钟",
    mm: "%d 分钟",
    h: "1 小时",
    hh: "%d 小时",
    d: "一天",
    dd: "%d 天",
    M: "一个月",
    MM: "%d 月",
    y: "一年",
    yy: "%d 年"
  }
})

const app = createApp(App);
// 创建pinia实例
const pinia = createPinia()

 
app.use(router)
app.use(pinia)
app.use(eventBus)

// 挂载到全局
app.config.globalProperties.$notification = notification;
app.config.globalProperties.$message = message;
app.config.globalProperties.$Modal = Modal;
app.config.globalProperties.$dayjs = dayjs;

app.mount('#app')
