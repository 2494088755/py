<template>
  <a-card title="注册" style="width: 300px; margin: 100px auto; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
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
        <a-input v-model:value="formState.username" placeholder="请输入用户名" />
      </a-form-item>

      <a-form-item
          label="密&nbsp;&nbsp;&nbsp;&nbsp;码"
          name="password"
          :rules="[{ required: true, message: '请输入密码!' }]"
      >
        <a-input-password v-model:value="formState.password" placeholder="请输入密码" />
      </a-form-item>

      <a-form-item
          label="确认密码"
          name="checkPassword"
          :rules="[{ required: true, message: '请确认密码!' }]"
      >
        <a-input-password v-model:value="formState.checkPassword" placeholder="请确认密码" />
      </a-form-item>

      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%; margin-bottom: 10px;">注册</a-button>
        <a-button type="link" @click="goToLogin" style="width: 100%; text-align: center;">返回登录</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'; // 直接引入 ElMessage
import request from '@/utils/request';

const router = useRouter();
const formState = reactive({
  username: '',
  password: '',
  checkPassword: '',
});

const onFinish = values => {
  console.log('Success:', values);
  // 这里可以添加注册逻辑
  request.post('/user/register', formState).then(res => {
    if (res.code === "200") {
      // localStorage.setItem('token', res.data.token);
      // router.push('/');
      // 提示后等待3秒跳转到登陆界面
      ElMessage.success("注册成功，即将跳转到登陆界面...");
      setTimeout(() => {
        router.push('/login');
      }, 3000);
    } else {
      ElMessage.error(res.msg); // 直接使用 ElMessage
    }
  });

};

const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

const goToLogin = () => {
  router.push('/login');
};
</script>
