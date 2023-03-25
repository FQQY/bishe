import { notification } from 'ant-design-vue'
import 'ant-design-vue/es/notification/style/css';

import md5 from 'js-md5';


let MyNotification = (type, msg, description='') => {
  notification[type]({
    message: msg,
    description,
    duration: 1
  })
}

let MyMd5 = (num) => {
  return md5(num);
}



export { 
  MyNotification,
  MyMd5
}
