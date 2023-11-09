<template>
    <n-watermark content="机要信息 注意保护" cross fullscreen :font-size="16" :line-height="16" :width="384" :height="384"
        :x-offset="12" :y-offset="60" :rotate="-15" />
    <CommonPage show-footer title="登录日志">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>
</template>
  
<script setup>
import api from '@/api'
import { NTag, NButton, useMessage } from 'naive-ui'
import { ref } from "vue"

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
        title: "IP地址",
        key: "ipaddr"
    },
    {
        title: "状态",
        key: "status",
        render(row) {
            let tagValue
            let tagType
            if (row.status === 0) {
                tagValue = '成功'
                tagType = 'success'
            } else {
                tagValue = '失败'
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
        title: "信息",
        key: "msg"
    },
    {
        title: "访问时间",
        key: "accessTime"
    },
    {
        title: "日志创建时间",
        key: "createTime"
    }
]
// 表格数据
var tableData = ref([])
// 分页插件
var page = ref(1)
var totalPage = ref(0)
function pageChange() {
    getLoginLog(page)
}

// 获取登录日志
getLoginLog(page)
async function getLoginLog(page) {
    tableData.value.length = 0
    let data = await api.getLoginLog(page.value, 10)
    totalPage.value = Math.ceil(data.data.count / 10.0)
    let logList = data.data.data
    for (let index = 0; index < logList.length; index++) {
        var object = {}
        object.id = logList[index].id
        object.username = logList[index].username
        object.ipaddr = logList[index].ipaddr
        object.status = logList[index].status
        object.msg = logList[index].msg
        object.accessTime = logList[index].accessTime
        object.createTime = logList[index].createTime
        tableData.value.push(object)
    }
}

</script>
  