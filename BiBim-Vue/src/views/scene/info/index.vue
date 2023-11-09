<template>
    <CommonPage show-footer title="场景列表">
        <template #action>
            <n-button type="primary" @click="showAdd">
                <TheIcon icon="material-symbols:add-home-work-outline" :size="18" class="mr-5" /> 新建场景
            </n-button>
        </template>
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>
    <!-- 删除场景确认框 -->
    <n-modal v-model:show="deleteSceneShow" :mask-closable="false" preset="dialog" title="删除场景" content="您确定要删除该场景吗？"
        positive-text="确认" negative-text="算了" @positive-click="deleteScene" />
    <!-- 修改信息模态框 -->
    <n-modal v-model:show="updateSceneShow" class="custom-card" preset="card" :style="bodyStyle" title="修改场景信息" size="huge"
        :bordered="false" :segmented="segmented">
        <n-form ref="formRef" label-placement="left" :label-width="110" :model="sceneInfo" :rules="rules" size="large">
            <n-form-item label="id" path="id" v-if="false">
                <n-input v-model:value="sceneInfo.id" placeholder="输入id" />
            </n-form-item>
            <n-form-item label="场景名" path="sceneName">
                <n-input v-model:value="sceneInfo.sceneName" placeholder="输入场景名" />
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

    <!-- 添加信息模态框 -->
    <n-modal v-model:show="addSceneShow" class="custom-card" preset="card" :style="bodyStyle" title="添加场景" size="huge"
        :bordered="false" :segmented="segmented">
        <n-form ref="formRef" label-placement="left" :label-width="110" :model="addSceneInfo" :rules="rules" size="large">
            <n-form-item label="场景名" path="sceneName">
                <n-input v-model:value="addSceneInfo.sceneName" placeholder="输入场景名" />
            </n-form-item>
            <n-form-item>
                <n-grid x-gap="12" :cols="5">
                    <n-gi></n-gi>
                    <n-gi></n-gi>
                    <n-gi>
                        <n-button type="primary" @click="addFormCheck">
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
    getSceneList(page.value)
}

// 表格表头
const columns = [
    {
        title: "id",
        key: "id"
    },
    {
        title: "场景名称",
        key: "sceneName"
    },
    {
        title: "创建用户ID",
        key: "createUserId"
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
                    onClick: () => deleteSceneBtn(row.id)
                },
                { default: () => '删除场景' }
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
                    onClick: () => openUpdateScene(row)
                },
                { default: () => '修改信息' }
            )
        }
    }
]
// 表格数据
var tableData = ref([])
getSceneList(1)
// 获取场景列表信息
async function getSceneList(pageNo) {
    tableData.value.length = 0
    let { data } = await api.getSceneList(pageNo, 10);
    totalPage.value = Math.ceil(data.count / 10.0)
    let sceneList = data.list
    for (let index = 0; index < sceneList.length; index++) {
        var object = {}
        object.id = sceneList[index].id
        object.createUserId = sceneList[index].createUserId
        object.sceneName = sceneList[index].sceneName
        object.createTime = sceneList[index].createTime.substr(0, 19)
        object.updateTime = sceneList[index].updateTime.substr(0, 19)
        tableData.value.push(object)
    }
}

const message = useMessage()
// 修改场景信息
// 模态框
var bodyStyle = { width: "650px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
var updateSceneShow = ref(false)
var sceneInfo = ref({
    id: '',
    sceneName: ''
})
// 打开修改场景信息模态框
async function openUpdateScene(row) {
    updateSceneShow.value = true
    sceneInfo.value.id = row.id
    sceneInfo.value.sceneName = row.sceneName
}
// 修改场景信息
function formCheck() {
    formRef.value?.validate((errors) => {
        if (!errors) {
            updateScene()
        } else {
            message.error("表单验证失败");
        }
    });
}
async function updateScene() {
    let res = await api.updateScene(sceneInfo.value)
    if (res.code === "00000") {
        message.success(res.data)
        updateSceneShow.value = false
        getSceneList(page.value)
    }
}

// 表单验证
const formRef = ref(null);
var rules = ref({
    id: {
        required: true,
        message: '请输入id',
        trigger: ['input', 'blur']
    },
    sceneName: {
        required: true,
        message: '请输入场景名',
        trigger: ['input', 'blur']
    }
})


// 删除场景
var deleteSceneShow = ref(false)
var sceneId
function deleteSceneBtn(id) {
    deleteSceneShow.value = true
    sceneId = id
}
async function deleteScene() {
    let res = await api.deleteScene(sceneId)
    if (res.code === "00000") {
        message.success(res.data)
        getSceneList(page.value)
    }
}

// 添加场景
var addSceneInfo = ref({
    sceneName: ''
})
var addSceneShow = ref(false)
function showAdd() {
    addSceneShow.value = true
    addSceneInfo.value.sceneName = ""
}
function addFormCheck() {
    formRef.value?.validate((errors) => {
        if (!errors) {
            addScene()
        } else {
            message.error("表单验证失败");
        }
    });
}
async function addScene() {
    let res = await api.addScene(addSceneInfo)
    if (res.code === "00000") {
        message.success(res.data)
        getSceneList(page.value)
    }
}
</script>
  