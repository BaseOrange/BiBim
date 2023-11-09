<template>
    <CommonPage show-footer title="场景编辑">
        <n-data-table :columns="columns" :data="tableData" />
        <n-pagination style="margin-top: 20px;" v-model:page="page" :page-count="totalPage" @click="pageChange()" />
    </CommonPage>
</template>
  
<script setup>
import api from '@/api'
import { NTag, NButton, useMessage } from 'naive-ui'
import { ref } from "vue"
import { useRouter } from "vue-router"

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
        title: "编辑",
        key: "operation",
        render(row) {
            return h(
                NButton,
                {
                    strong: true,
                    type: 'primary',
                    onClick: () => openSceneEditor(row)
                },
                { default: () => '编辑场景' }
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

// 跳转路由编辑页面
const router = useRouter();
function openSceneEditor(row) {
    router.push("/scene/sceneEditor/" + row.id)
}
</script>
  
  