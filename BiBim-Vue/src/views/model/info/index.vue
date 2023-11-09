<template>
    <CommonPage show-footer title="模型列表">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>
    <!-- 删除用户确认框 -->
    <n-modal v-model:show="deleteModelShow" :mask-closable="false" preset="dialog" title="删除模型" content="您确定要删除该模型吗？"
        positive-text="确认" negative-text="算了" @positive-click="deleteModel" />
    <!-- 修改信息模态框 -->
    <n-modal v-model:show="updateModelShow" class="custom-card" preset="card" :style="bodyStyle" title="修改模型信息" size="huge"
        :bordered="false" :segmented="segmented">
        <n-form ref="formRef" label-placement="left" :label-width="110" :model="modelInfo" :rules="rules" size="large">
            <n-form-item label="id" path="id" v-if="false">
                <n-input v-model:value="modelInfo.id" placeholder="输入id" />
            </n-form-item>
            <n-form-item label="设备名" path="deviceName">
                <n-input v-model:value="modelInfo.deviceName" placeholder="输入设备名" />
            </n-form-item>
            <n-form-item label="设备物理尺寸" path="deviceSize">
                <n-input v-model:value="modelInfo.deviceSize" placeholder="输入设备物理尺寸" />
            </n-form-item>
            <n-form-item label="X轴缩放" path="scalingX">
                <n-input v-model:value="modelInfo.scalingX" placeholder="输入X轴缩放" />
            </n-form-item>
            <n-form-item label="Y轴缩放" path="scalingY">
                <n-input v-model:value="modelInfo.scalingY" placeholder="输入Y轴缩放" />
            </n-form-item>
            <n-form-item label="Z轴缩放" path="scalingZ">
                <n-input v-model:value="modelInfo.scalingZ" placeholder="输入Z轴缩放" />
            </n-form-item>
            <n-form-item label="模型信息" path="deviceInfo">
                <n-input v-model:value="modelInfo.deviceInfo" placeholder="输入模型信息" />
            </n-form-item>
            <n-form-item label="模型链接" path="deviceUrl">
                <n-input v-model:value="modelInfo.deviceUrl" placeholder="输入模型链接" disabled="true" />
            </n-form-item>
            <n-form-item label="创建用户id" path="createUserId">
                <n-input v-model:value="modelInfo.createUserId" placeholder="输入创建用户id" disabled="true" />
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
    getModelList(page.value)
}

// 表格表头
const columns = [
    {
        title: "id",
        key: "id"
    },
    {
        title: "添加用户id",
        key: "createUserId"
    },
    {
        title: "设备名",
        key: "deviceName"
    },
    {
        title: "设备物理尺寸",
        key: "deviceSize"
    },
    {
        title: "X轴缩放",
        key: "scalingX"
    },
    {
        title: "Y轴缩放",
        key: "scalingY"
    },
    {
        title: "Z轴缩放",
        key: "scalingZ"
    },
    {
        title: "设备信息",
        key: "deviceInfo"
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
        title: "删除",
        key: "delete",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'error',
                    onClick: () => deleteModelBtn(row.id)
                },
                { default: () => '删除模型' }
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
getModelList(1)
// 获取模型列表信息
async function getModelList(pageNo) {
    tableData.value.length = 0
    let { data } = await api.getModelList(pageNo, 10);
    totalPage.value = Math.ceil(data.count / 10.0)
    let modelList = data.list
    for (let index = 0; index < modelList.length; index++) {
        var object = {}
        object.id = modelList[index].id
        object.createUserId = modelList[index].createUserId
        object.deviceName = modelList[index].deviceName
        object.deviceSize = modelList[index].deviceSize
        object.scalingX = modelList[index].scalingX
        object.scalingY = modelList[index].scalingY
        object.scalingZ = modelList[index].scalingZ
        object.deviceInfo = modelList[index].deviceInfo
        object.createTime = modelList[index].createTime.substr(0, 19)
        object.updateTime = modelList[index].updateTime.substr(0, 19)
        tableData.value.push(object)
    }
}

const message = useMessage()
// 修改模型信息
// 模态框
var bodyStyle = { width: "650px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
var updateModelShow = ref(false)
var modelInfo = ref({
    id: '',
    deviceName: '',
    deviceSize: '',
    scalingX: '',
    scalingY: '',
    scalingZ: '',
    deviceInfo: '',
    deviceUrl: '',
    createUserId: ''
})
// 打开修改模型信息模态框
async function openUpdateModel(row) {
    updateModelShow.value = true
    let data = await api.getModelInfo(row.id)
    modelInfo.value.id = data.data.id
    modelInfo.value.deviceName = data.data.deviceName
    modelInfo.value.deviceSize = data.data.deviceSize
    modelInfo.value.scalingX = data.data.scalingX
    modelInfo.value.scalingY = data.data.scalingY
    modelInfo.value.scalingZ = data.data.scalingZ
    modelInfo.value.deviceInfo = data.data.deviceInfo
    modelInfo.value.deviceUrl = data.data.deviceUrl
    modelInfo.value.createUserId = data.data.createUserId

}
// 修改模型信息
function formCheck() {
    formRef.value?.validate((errors) => {
        if (!errors) {
            updateModel()
        } else {
            message.error("表单验证失败");
        }
    });
}
async function updateModel() {
    let res = await api.updateModel(modelInfo.value)
    if (res.code === "00000") {
        message.success(res.data)
        updateModelShow.value = false
        getModelList(page.value)
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
    deviceName: {
        required: true,
        message: '请输入设备名',
        trigger: ['input']
    },
    deviceSize: {
        required: true,
        message: '请输入设备物理尺寸',
        trigger: ['input']
    },
    scalingX: {
        required: true,
        validator(rule, value) {
            if (!value) {
                return new Error("请输入X轴缩放");
            } else if (!/^[1-9][0-9]*([\.][0-9]{1,2})?$/.test(value)) {
                return new Error("缩放应该为浮点数，例如1.0");
            }
            return true;
        },
        trigger: ['input', 'blur']
    },
    scalingY: {
        required: true,
        validator(rule, value) {
            if (!value) {
                return new Error("请输入Y轴缩放");
            } else if (!/^[1-9][0-9]*([\.][0-9]{1,2})?$/.test(value)) {
                return new Error("缩放应该为浮点数，例如1.0");
            }
            return true;
        },
        trigger: ['input', 'blur']
    },
    scalingZ: {
        required: true,
        validator(rule, value) {
            if (!value) {
                return new Error("请输入Z轴缩放");
            } else if (!/^[1-9][0-9]*([\.][0-9]{1,2})?$/.test(value)) {
                return new Error("缩放应该为浮点数，例如1.0");
            }
            return true;
        },
        trigger: ['input', 'blur']
    },
    deviceInfo: {
        required: true,
        message: '请输入设备信息',
        trigger: ['input']
    },
})


// 删除模型
var deleteModelShow = ref(false)
var modelId
function deleteModelBtn(id) {
    deleteModelShow.value = true
    modelId = id
}
async function deleteModel() {
    let res = await api.deleteModel(modelId)
    if (res.code === "00000") {
        message.success(res.data)
        getModelList(page.value)
    }
}
</script>
  