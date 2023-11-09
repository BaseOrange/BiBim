<template>
    <n-watermark content="机要信息 注意保护" cross fullscreen :font-size="16" :line-height="16" :width="384" :height="384"
        :x-offset="12" :y-offset="60" :rotate="-15" />

    <CommonPage show-footer title="区块链交易列表">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>

    <n-modal v-model:show="showModal" class="custom-card" preset="card" :style="bodyStyle" title="交易信息" size="huge"
        :bordered="false" :segmented="segmented">
        <n-button type="info" dashed>
            <h1>交易信息</h1>
        </n-button>
        <h2>交易id：{{ transactionInfo.transId }}</h2>
        <h2>交易类型：{{ transactionInfo.transType }}</h2>
        <h2>交易状态：{{ transactionInfo.transStatus }}</h2>
        <h2>交易发送组织：{{ transactionInfo.transSendOrg }}</h2>
        <h2>交易发起用户：{{ transactionInfo.transSendUser }}</h2>
        <h2>发起用户地址：{{ transactionInfo.transSendAddr }}</h2>
        <h2>交易发起时间：{{ transactionInfo.transSendTime }}</h2>
        <n-divider />
        <n-button type="info" dashed>
            <h1>合约执行信息</h1>
        </n-button>
        <h2>目标合约：{{ transactionInfo.targetContract }}</h2>
        <h2>合约读写集哈希：{{ transactionInfo.contractIoHash }}</h2>
        <h2>合约执行结果码：{{ transactionInfo.contractExeCode }}</h2>
        <h2>合约执行信息：{{ transactionInfo.contractExeInfo }}</h2>
        <h2>合约调用方法：{{ transactionInfo.contractCallMethod }}</h2>
        <h2>合约调用结果：</h2>
        <n-card>
            {{ transactionInfo.contractExeRes }}
        </n-card>
    </n-modal>
</template>
  
<script setup>
import api from '@/api'
import { NTag, NButton } from 'naive-ui'
import { ref } from "vue"

// 分页插件
var page = ref(1)
var totalPage = ref(0)
function pageChange() {
    getTransactionList(page.value)
}

// 表格表头
const columns = [
    {
        title: "区块高度",
        key: "BlockHeight"
    },
    {
        title: "交易id",
        key: "TxId"
    },
    {
        title: "发起组织",
        key: "SenderOrg"
    },
    {
        title: "发起用户",
        key: "Sender"
    },
    {
        title: "目标合约",
        key: "ContractName"
    },
    {
        title: "交易状态",
        key: "Status",
        render(row) {
            let tagValue
            let tagType
            if (row.Status === 'SUCCESS') {
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
        title: "上链时间",
        key: "Timestamp"
    },
    {
        title: "操作",
        key: "operation",
        render(row) {
            return h(
                NButton,
                {
                    type: "primary",
                    onClick: () => getTransactionInfo(row.TxId)
                },
                { default: () => '详情' }
            )
        }
    }
]
// 表格数据
var tableData = ref([])
getTransactionList(1)
// 获取区块链交易信息
async function getTransactionList(pageNo) {
    pageNo--
    let { data } = await api.getTransactionList(pageNo, 10);
    // 交易数量总数
    let totalCount = data.Response.TotalCount
    // 设置页面数量
    totalPage.value = Math.ceil(totalCount / 10.0)

    tableData.value.length = 0
    var list = data.Response.GroupList
    for (let index = 0; index < list.length; index++) {
        var object = {}
        object.BlockHeight = list[index].BlockHeight
        object.TxId = list[index].TxId
        object.SenderOrg = list[index].SenderOrg
        object.Sender = list[index].Sender
        object.ContractName = list[index].ContractName
        object.Status = list[index].Status
        object.Timestamp = Timestamp2Date(list[index].Timestamp)
        tableData.value.push(object)
    }
}
// 时间戳转日期
function Timestamp2Date(Timestamp) {
    let now = new Date(Timestamp * 1000),
        y = now.getFullYear(),
        m = now.getMonth() + 1,
        d = now.getDate();
    return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + now.toTimeString().substr(0, 8);
}

// 模态框
var bodyStyle = { width: "1000px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
var showModal = ref(false)
var transactionInfo = ref({
    transId: "",
    transType: "",
    transStatus: "",
    transSendOrg: "",
    transSendUser: "",
    transSendAddr: "",
    transSendTime: "",
    targetContract: "",
    contractIoHash: "",
    contractExeCode: "",
    contractExeInfo: "",
    contractCallMethod: "",
    contractExeRes: ""
})
// 获取交易信息
async function getTransactionInfo(TxId) {
    let { data } = await api.getTransactionInfo(TxId)
    transactionInfo.value.transId = TxId
    transactionInfo.value.transType = data.Response.Data.TxType
    transactionInfo.value.transStatus = data.Response.Data.ContractMessage
    transactionInfo.value.transSendOrg = data.Response.Data.OrgId
    transactionInfo.value.transSendUser = data.Response.Data.Sender
    transactionInfo.value.transSendAddr = data.Response.Data.UserAddr
    transactionInfo.value.transSendTime = Timestamp2Date(data.Response.Data.Timestamp)
    transactionInfo.value.targetContract = data.Response.Data.ContractName
    transactionInfo.value.contractIoHash = data.Response.Data.RwSetHash
    transactionInfo.value.contractExeCode = data.Response.Data.ContractResultCode
    transactionInfo.value.contractExeInfo = data.Response.Data.ContractMessage
    transactionInfo.value.contractCallMethod = data.Response.Data.ContractMethod
    transactionInfo.value.contractExeRes = data.Response.Data.ContractResult
    console.log(transactionInfo.value);
    showModal.value = true
}

</script>
  