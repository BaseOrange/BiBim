<template>
    <n-watermark content="隐私信息 注意保护" cross fullscreen :font-size="16" :line-height="16" :width="384"
        :height="384" :x-offset="12" :y-offset="60" :rotate="-15" />

    <CommonPage show-footer title="用户列表">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>

    <!-- 删除用户确认框 -->
    <n-modal v-model:show="deleteModelShow" :mask-closable="false" preset="dialog" title="删除用户" content="您确定要删除该用户吗？"
        positive-text="确认" negative-text="算了" @positive-click="deleteUser" />
    <!-- 重置密码确认框 -->
    <n-modal v-model:show="resetModelShow" :mask-closable="false" preset="dialog" title="重置密码"
        content="您确定要重置密码吗？新密码为：用户名123**" positive-text="确认" negative-text="算了" @positive-click="resetUser" />
    <!-- 修改信息模态框 -->
    <n-modal v-model:show="updateModelShow" class="custom-card" preset="card" :style="bodyStyle" title="修改用户信息" size="huge"
        :bordered="false" :segmented="segmented">
        <n-form ref="formRef" label-placement="left" :label-width="80" :model="userInfo" :rules="rules" size="large">
            <n-form-item label="id" path="id" v-if="false">
                <n-input v-model:value="userInfo.id" placeholder="输入id" />
            </n-form-item>
            <n-form-item label="用户名" path="username">
                <n-input v-model:value="userInfo.username" placeholder="输入用户名" />
            </n-form-item>
            <n-form-item label="邮箱" path="email">
                <n-input v-model:value="userInfo.email" placeholder="输入邮箱" />
            </n-form-item>
            <n-form-item label="手机号" path="telnumber">
                <n-input v-model:value="userInfo.telnumber" placeholder="输入手机号" />
            </n-form-item>
            <n-form-item label="状态">
                <n-tag v-if="statusSwitch === true" type="success">启用</n-tag>
                <n-tag v-if="statusSwitch === false" type="error">禁用</n-tag>
                <n-switch v-model:value="statusSwitch" />
            </n-form-item>
            <n-form-item>
                <n-grid x-gap="12" :cols="5">
                    <n-gi></n-gi>
                    <n-gi></n-gi>
                    <n-gi>
                        <n-button type="primary" @click="formCheck">
                            提交
                        </n-button>
                    </n-gi>
                    <n-gi></n-gi>
                    <n-gi></n-gi>
                </n-grid>
            </n-form-item>
        </n-form>
    </n-modal>
</template>
  
<script setup>
import api from '@/api'
import { NTag, NButton, useMessage } from 'naive-ui'
import { ref } from "vue"

// 分页插件
var page = ref(1)
var totalPage = ref(0)
function pageChange() {
    getUserList(page.value)
}

// 表格表头
const columns = [
    {
        title: "id",
        key: "id"
    },
    {
        title: "用户名",
        key: "username"
    },
    {
        title: "邮箱",
        key: "email"
    },
    {
        title: "手机号",
        key: "telnumber"
    },
    {
        title: "创建时间",
        key: "createTime"
    },
    {
        title: "更新时间",
        key: "updateTime"
    },
    {
        title: "状态",
        key: "status",
        render(row) {
            let tagValue
            let tagType
            if (row.status === 0) {
                tagValue = '正常'
                tagType = 'success'
            } else {
                tagValue = '禁用'
                tagType = 'error'
            }
            return h(
                NTag,
                {
                    style: {
                        marginRight: '6px'
                    },
                    type: tagType,
                    bordered: false
                },
                {
                    default: () => tagValue
                }
            )

        }
    },
    {
        title: "重置密码",
        key: "operation",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'warning',
                    onClick: () => resetUserBtn(row.id)
                },
                { default: () => '重置密码' }
            )
        }
    },
    {
        title: "删除",
        key: "delete",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'error',
                    onClick: () => deleteUserBtn(row.id)
                },
                { default: () => '删除用户' }
            )
        }
    },
    {
        title: "修改",
        key: "operation",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'primary',
                    onClick: () => openUpdateModel(row)
                },
                { default: () => '修改信息' }
            )
        }
    }
]
// 表格数据
var tableData = ref([])
getUserList(1)
// 获取用户列表信息
async function getUserList(pageNo) {
    tableData.value.length = 0
    let { data } = await api.getUserList(pageNo, 10);
    totalPage.value = Math.ceil(data.countTotal / 10.0)
    let userList = data.userList
    for (let index = 0; index < userList.length; index++) {
        var object = {}
        object.id = userList[index].id
        object.username = userList[index].username
        object.email = userList[index].email
        object.telnumber = userList[index].telnumber
        object.createTime = userList[index].createTime.substr(0, 19)
        object.updateTime = userList[index].updateTime.substr(0, 19)
        object.status = userList[index].status
        tableData.value.push(object)
    }
}

// 重置密码id
var userId = ""
// 重置密码模态框变量
var resetModelShow = ref(false)
const message = useMessage()
function resetUserBtn(id) {
    resetModelShow.value = true
    userId = id
}
async function resetUser() {
    let res = await api.resetUser(userId)
    if (res.code === "00000") {
        message.success(res.data)
    }
}

// 删除用户模态框变量
var deleteModelShow = ref(false)
function deleteUserBtn(id) {
    deleteModelShow.value = true
    userId = id
}
async function deleteUser() {
    let res = await api.deleteUser(userId)
    if (res.code === "00000") {
        message.success(res.data)
        getUserList(page.value)
    }
}

// 修改用户信息
// 模态框
var bodyStyle = { width: "600px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
var statusSwitch = ref(true)
var updateModelShow = ref(false)
var userInfo = ref({
    id: '',
    username: '',
    email: '',
    telnumber: '',
    status: ''
})
// 打开修改用户信息模态框
function openUpdateModel(row) {
    updateModelShow.value = true
    userInfo.value.id = row.id
    userInfo.value.username = row.username
    userInfo.value.email = row.email
    userInfo.value.telnumber = row.telnumber
    userInfo.value.status = row.status
    if (row.status === 1) {
        statusSwitch.value = false
    } else {
        statusSwitch.value = true
    }
}
// 修改用户信息
function formCheck() {
    formRef.value?.validate((errors) => {
        if (!errors) {
            updateUser()
        } else {
            message.error("表单验证失败");
        }
    });
}
async function updateUser() {
    if (statusSwitch.value === true) {
        userInfo.value.status = "0"
    } else {
        userInfo.value.status = "1"
    }
    let res = await api.updateUser(userInfo.value)
    if (res.code === "00000") {
        message.success(res.data)
        updateModelShow.value = false
        getUserList(page.value)
    }
}

// 表单验证
const formRef = ref(null);
var rules = ref({
    id: {
        required: true,
        message: '请输入id',
        trigger: ['input']
    },
    username: {
        required: true,
        message: '请输入用户名',
        trigger: ['input']
    },
    email: {
        required: true,
        message: '请输入电子邮箱',
        trigger: ['input']
    },
    telnumber: {
        required: true,
        message: '请输入手机号',
        trigger: ['input']
    }
})
</script>
  