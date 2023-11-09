<template>
    <n-watermark content="机要信息 注意保护" cross fullscreen :font-size="16" :line-height="16" :width="384" :height="384"
        :x-offset="12" :y-offset="60" :rotate="-15" />
    <CommonPage show-footer title="操作日志">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>

    <n-modal v-model:show="showModal" class="custom-card" preset="card" :style="bodyStyle" title="操作日志信息" size="huge"
        :bordered="false" :segmented="segmented">
        <h2>日志编号：{{ logInfo.id }}</h2>
        <h2>操作链接：{{ logInfo.operUrl }}</h2>
        <h2>操作方法：{{ logInfo.method }}</h2>
        <h2>操作参数：{{ logInfo.operParam }}</h2>
        <h2>返回信息：</h2>
        <n-card>
            {{ logInfo.jsonResult }}
        </n-card>
        <h2>异常信息：</h2>
        <n-card>
            {{ logInfo.errorMsg }}
        </n-card>
        <h2>操作IP：{{ logInfo.operIp }}</h2>
        <h2>操作时间：{{ logInfo.createTime }}</h2>
    </n-modal>
</template>
  
<script setup>
import api from '@/api'
import { NButton } from 'naive-ui'
import { ref } from "vue"

// 表格表头
const columns = [
    {
        title: "id",
        key: "id"
    },
    {
        title: "所属模块",
        key: "title"
    },
    {
        title: "SQL方式",
        key: "businessType"
    },
    {
        title: "请求类型",
        key: "requestMethod"
    },
    {
        title: "操作类型",
        key: "operatorType"
    },
    {
        title: "操作用户",
        key: "operName"
    },
    {
        title: "部门名称",
        key: "deptName"
    },
    {
        title: "请求时间",
        key: "createTime"
    },
    {
        title: "操作",
        key: "operation",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'success',
                    onClick: () => getOperateInfo(row.id)
                },
                { default: () => '详细信息' }
            )
        }
    }
]
// 表格数据
var tableData = ref([])
// 分页插件
var page = ref(1)
var totalPage = ref(0)
function pageChange() {
    getOperaLog(page)
}

// 获取操作日志
getOperaLog(page)
async function getOperaLog(page) {
    tableData.value.length = 0
    let data = await api.getOperaLog(page.value, 10)
    totalPage.value = Math.ceil(data.data.count / 10.0)
    let logList = data.data.data
    for (let index = 0; index < logList.length; index++) {
        var object = {}
        object.id = logList[index].id
        object.title = logList[index].title
        object.businessType = logList[index].businessType
        object.requestMethod = logList[index].requestMethod
        object.operatorType = logList[index].operatorType
        object.operName = logList[index].operName
        object.deptName = logList[index].deptName
        object.createTime = logList[index].createTime
        tableData.value.push(object)
    }
}

// 日志详细信息
var logInfo = ref({
    id: "",
    operUrl: "",
    method: "",
    operParam: "",
    jsonResult: "",
    errorMsg: "",
    operIp: "",
    createTime: "",
})

// 模态框参数
var bodyStyle = { width: "1000px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
var showModal = ref(false)
// 获取操作日志信息
async function getOperateInfo(id) {
    let data = await api.getOperaLogInfo(id)
    logInfo.value.id = data.data.id != "" ? data.data.id : "空"
    logInfo.value.operUrl = data.data.operUrl != "" ? data.data.operUrl : "空"
    logInfo.value.method = data.data.method != "" ? data.data.method : "空"
    logInfo.value.operParam = data.data.operParam != "" ? data.data.operParam : "空"
    logInfo.value.jsonResult = data.data.jsonResult != "" ? data.data.jsonResult : "空"
    logInfo.value.errorMsg = data.data.errorMsg != "" ? data.data.errorMsg : "空"
    logInfo.value.operIp = data.data.operIp != "" ? data.data.operIp : "空"
    logInfo.value.createTime = data.data.createTime != "" ? data.data.createTime : "空"

    showModal.value = true
}


</script>
  