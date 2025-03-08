import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from "@/router/index.js";

const request = axios.create({
    baseURL: '/api',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    // timeout: 5000,
    // 如果是跨域请求，需要加上下面这句
    withCredentials: true

})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    // config.headers['Content-Type'] = 'application/json;charset=utf-8';
    // config.headers['Access-Control-Allow-Origin'] = '*';
    // // config.headers['token'] = user.token;  // 设置请求头
    // if (localStorage.getItem('token')) {
    //     config.headers['Authorization'] =localStorage.getItem('token');
    // }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        // if (response.data.code !== 200) {
        //     return Promise.reject(response.data.msg)
        // }
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        if ('401'.indexOf(error.response.status) !== -1) {
            ElMessage.error('登陆过期，请重新登录');
            router.push('/login');
        }
        return Promise.reject(error);
    }
)

export default request