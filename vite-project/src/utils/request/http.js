import axios from 'axios';

// 默认的接口url前缀
axios.defaults.baseURL = "/demo"
// 默认的请求超时时间
axios.defaults.timeout = 10000
// 默认的post请求的请求头
axios.defaults.headers.post["Content-Type"] = "application/json"

/**
 * @func getData
 * @desc 基于axios.get的简单封装
 * @param {String, Object} url params  
 * @return {Promise} 
 */
function getData(url, params=null) {
  return new Promise((resolve, reject) => {
    axios.get(url, { params }).then(response => {
        if(response.status === 200) {
          resolve(response.data)
        }
      }
    ).catch(error =>{
      console.log(error);
    })
  })
}


/**
 * @func postData
 * @desc 基于axios.post的简单封装
 * @param {String, Object} url params  
 * @return {Promise} 
 */
function postData(url, params,options={}) {
  return new Promise((resolve, reject) => {
    axios.post(url, params,options).then(response => {
        if(response.status === 200) {
          resolve(response.data)
        }
      }
    ).catch(error =>{
      console.log(error);
    })
  })
}



export { getData, postData }



