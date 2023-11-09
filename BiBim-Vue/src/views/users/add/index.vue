<template>
    <CommonPage show-footer title="添加用户">
        <n-form ref="formRef" :label-width="100" :model="userInfo" :rules="rules" label-placement="left" size="large"
            label-width="auto" style="width: 800px;">
            <n-form-item label="用户名" path="username">
                <n-input v-model:value="userInfo.username" placeholder="输入用户名" />
                <n-button type="primary">检查是否可用</n-button>
            </n-form-item>

            <n-form-item label="密码" path="password">
                <n-input v-model:value="userInfo.password" type="password" placeholder="输入密码" />
            </n-form-item>
            <n-form-item label="确认密码" path="repassword">
                <n-input v-model:value="userInfo.repassword" type="password" placeholder="输入确认密码" />
            </n-form-item>
            <n-form-item label="电子邮箱" path="email">
                <n-input v-model:value="userInfo.email" placeholder="输入电子邮箱" />
            </n-form-item>
            <n-form-item label="手机号" path="telnumber">
                <n-input v-model:value="userInfo.telnumber" placeholder="输入手机号" />
            </n-form-item>
            <n-form-item>
                <n-grid x-gap="12" :cols="6">
                    <n-gi></n-gi>
                    <n-gi></n-gi>
                    <n-gi>
                        <n-button type="success" @click="formCheck">
                            提交
                        </n-button>
                    </n-gi>
                    <n-gi>

                        <n-button type="warning" @click="reset">
                            重置
                        </n-button>
                    </n-gi>
                    <n-gi></n-gi>
                </n-grid>
            </n-form-item>
        </n-form>
    </CommonPage>
</template>
  
<script setup>
import api from '@/api'
import { NTag, NButton, useMessage } from 'naive-ui'
import { ref } from "vue"
import { useRouter } from 'vue-router'

var userInfo = ref({
    username: '',
    password: '',
    repassword: '',
    email: '',
    telnumber: ''
})
// 表单验证
const formRef = ref(null);
var rules = ref({
    username: {
        required: true,
        message: '请输入用户名',
        trigger: ['input']
    },
    password: {
        required: true,
        message: '请输入密码',
        trigger: ['input']
    },
    repassword: [
        {
            required: true,
            message: "请再次输入密码",
            trigger: ["input", "blur"]
        },
        {
            validator: validatePasswordStartWith,
            message: "两次密码输入不一致",
            trigger: "input"
        },
        {
            validator: validatePasswordSame,
            message: "两次密码输入不一致",
            trigger: ["blur", "password-input"]
        }
    ],
    email: {
        required: true,
        message: '请输入正确的电子邮箱',
        trigger: ['input'],
        validator: (rule, value) => {
            return /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value);
        }
    },
    telnumber: {
        required: true,
        message: '请输入正确的手机号',
        trigger: ['input'],
        validator: (rule, value) => {
            return /^1[3456789]\d{9}$/.test(value);
        }
    }
})
// 两次密码验证
function validatePasswordStartWith(rule, value) {
    return !!userInfo.value.password && userInfo.value.password.startsWith(value) && userInfo.value.password.length >= value.length;
}
function validatePasswordSame(rule, value) {
    return value === userInfo.value.password;
}

const message = useMessage()
function formCheck() {
    formRef.value?.validate((errors) => {
        if (!errors) {
            register()
        } else {
            message.error("表单验证失败");
        }
    });
}

// 注册用户
const router = useRouter()
async function register() {
    let res = await api.register(userInfo.value)
    if (res.code === "00000") {
        router.push("/users/info")
        message.success(res.data);
    }
}

// 重置表单
function reset() {
    userInfo.value.username = ''
    userInfo.value.password = ''
    userInfo.value.repassword = ''
    userInfo.value.email = ''
    userInfo.value.telnumber = ''
}
</script>
  