<template>
  <a-card title="登陆图书管理系统" style="width: 300px; margin: 100px auto;">
    <a-form
        :model="formState"
        name="basic"
        autocomplete="off"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
    >
      <a-form-item
          label="用户名"
          name="username"
          :rules="[{ required: true, message: '请输入用户名!' }]"
      >
        <a-input v-model:value="formState.username"/>
      </a-form-item>

      <a-form-item
          label="密    码"
          name="password"
          :rules="[{ required: true, message: '请输入密码!' }]"
      >
        <a-input-password v-model:value="formState.password"/>
      </a-form-item>
      <a-form-item> <!--        添加记住我单选框-->
        <a-checkbox v-model:checked="formState.keepSession">记住我</a-checkbox>
      </a-form-item>
      <a-form-item style="margin-left: 80px">
        <a-button type="primary" html-type="submit">登录</a-button>
        <a-button type="link" @click="goToRegister">注册</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script setup>
import {reactive} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from 'element-plus'; // 直接引入 ElMessage
import request from '@/utils/request';

const router = useRouter();
const formState = reactive({
  username: '',
  password: '',
  keepSession: false
});

const onFinish = values => {
  request.post('/user/login', formState).then(res => {
    if (res.code === "200") {
      console.log(res.data)
      localStorage.setItem('user', JSON.stringify(res.data));
      // router.push('/');
      ElMessage.success("登陆成功");
      setTimeout(() => {
        router.push('/');
      }, 1000);
    } else {
      ElMessage.error(res.msg); // 直接使用 ElMessage
    }
  });
};

const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

const goToRegister = () => {
  router.push('/register');
};
</script>
