<template>
    <a-spin size="large" :spinning="isLoading">
        <div class="container">
            <div class="login-content">
                <img class="logo-icon" src="../../assets/image/blacklogo.png" alt="" />
                <h1 class="title">反洗钱名单监测系统</h1>
                <a-form :model="loginForm" name="Login" :rules="rules" @finish="onSubmit">
                    <a-form-item name="username">
                        <a-input size="large" allow-clear v-model:value="loginForm.username" placeholder="请输入用户名">
                            <template #prefix>
                                <user-outlined style="color: rgb(192, 192, 192)" />
                            </template>
                        </a-input>
                    </a-form-item>
                    <a-form-item name="password">
                        <a-input-password size="large" v-model:value="loginForm.password" placeholder="请输入密码">
                            <template #prefix>
                                <lock-outlined style="color: rgb(192, 192, 192)" />
                            </template>
                        </a-input-password>
                    </a-form-item>
                    <a-form-item name="remember">
                        <a-checkbox v-model:checked="loginForm.remember">记住密码</a-checkbox>
                    </a-form-item>
                    <a-form-item>
                        <a-button size="large" block type="primary" html-type="submit">登录</a-button>
                    </a-form-item>
                </a-form>
            </div>
        </div>
    </a-spin>
</template>
  
<script setup lang="ts">
import { post } from "@/service"
import { Form } from "ant-design-vue";
import { message } from "ant-design-vue";
import { useUserStore } from "@/store";
import { useCommon } from "@/hooks"
import { encryptString, decryptString } from "@/utils";
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';

interface FormState {
    username: string;
    password: string;
    remember: boolean;
}

const loginForm = reactive<FormState>({
    username: "",
    password: "",
    remember: false,
});

const rules = reactive({
    username: [{ required: true, message: "请输入用户名" }],
    password: [{ required: true, message: "请输入密码" }],
});

const router = useRouter();
const userStore = useUserStore();
const { validate } = Form.useForm(loginForm, rules);

const isLoading = ref<boolean>(false);

const onSubmit = async () => {
    shouldRememberPassword();
    try {
        await Promise.all([validate()]);
        isLoading.value = true;
        const res = await post<response<UserApi.UserInfo>>("login", loginForm);
        if (res.code === 200) {
            message.success("登录成功");
            router.push("/");
            useCommon().getDictArray();
            userStore.setToken(res.result.token);
            isLoading.value = false;
        } else {
            message.error(res.message);
            isLoading.value = false;
        }
    } catch (error) {
        isLoading.value = false;
        message.error("登录失败");
    }
};

const shouldRememberPassword = (): void => {
    if (loginForm.remember) {
        localStorage.setItem("USER", encryptString(JSON.stringify(loginForm)));
    } else {
        localStorage.removeItem("USER");
    }
};

onMounted(() => {
    if (localStorage.getItem("USER")) {
        const user = JSON.parse(decryptString(localStorage.getItem("USER") as string));
        loginForm.remember = Boolean(user);
        loginForm.username = user.username;
        loginForm.password = user.password;
    }
});
</script>
  
<style lang="scss" scoped>
.container {
    width: 100%;
    min-height: 100vh;
    background: url(@/assets/image/background.svg) no-repeat;
    background-size: 100%;
    position: relative;
}

.login-content {
    width: 350px;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);

    .logo-icon {
        width: 350px;
    }

    .title {
        text-align: justify;
        margin: 20px 0 0 0;
        color: #262626;
    }

    .title:after {
        display: inline-block;
        width: 100%;
        content: "";
    }
}

.ant-tabs :deep(.ant-tabs-tab) {
    width: 100px !important;
    justify-content: center;
}
</style>
  