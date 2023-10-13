<template>
    <a-dropdown>
        <div class="user-info">
            <a-avatar size="small" class="head-sculpture" src="https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png" />
            <a class="ant-dropdown-link" @click.prevent>
                欢迎您，超级管理员
                <DownOutlined />
            </a>
        </div>
        <template #overlay>
            <a-menu>
                <a-menu-item key="1" @click="logout()">
                    <LogoutOutlined class="logout-icon" />
                    <span class="logout">退出登录</span>
                </a-menu-item>
            </a-menu>
        </template>
    </a-dropdown>
</template>
  
<script setup lang="ts">
import { DownOutlined, LogoutOutlined } from '@ant-design/icons-vue';

const router = useRouter()
const logout = () => {
    sessionStorage.clear()
    localStorage.removeItem("token")
    router.push("/login")
}
</script>
  
<style scoped>
.head-sculpture {
    background-color: #ff4000;
    margin-right: 5px;
}

.logout-icon {
    color: #727272;
    font-size: 12px;
    margin-right: 10px;
}

.user-info {
    cursor: pointer;
}

.logout {
    color: #727272;
    font-size: 14px;
    cursor: pointer;
    font-family: "PingFang SC";
}

.ant-dropdown-link {
    color: rgba(0, 0, 0, .65);
    font-size: 13px;
    font-family: "PingFang SC";
}
</style>
  