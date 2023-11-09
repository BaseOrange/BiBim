<template id="test">
    <!-- Threejs渲染元素 -->
    <div flex-1 id="threeDOM" ref="screenDom">
    </div>

    <!-- 模态框-->
    <n-modal v-model:show="deviceInfoModelShow" class="custom-card" preset="card" :style="bodyStyle" title="设备信息"
        size="huge" :bordered="false" :segmented="segmented">
        <div v-show="!spinShow" style="font-size: 20px;">
            <n-alert type="info">
                设备名称：<n-input v-model:value="deviceInfo.deviceName" :autosize="{ minRows: 20, maxRows: 20 }" type="text"
                    placeholder="设备名称" />
            </n-alert>
            <n-alert type="info">
                设备开关状态：
                <n-switch v-model:value="deviceInfo.deviceSwitch" />
            </n-alert>
            <n-alert type="info">
                设备故障状态：
                <n-tag type="success" v-show="deviceInfo.deviceState === 1">
                    正常
                </n-tag>
                <n-tag type="error" v-show="deviceInfo.deviceState === 0">
                    故障
                </n-tag>
            </n-alert>
            <n-alert type="info">
                设备温度：
                <n-tag :type="deviceInfo.deviceTemp > 30 ? `error` : `success`">
                    {{ deviceInfo.deviceTemp }}℃
                </n-tag>
            </n-alert>
            <n-button type="primary" @click="submitBtn">提交</n-button>
        </div>
        <n-spin size="large" v-show="spinShow" />
    </n-modal>
</template>
  
<script setup>
import { useRouter } from "vue-router"
import { NTag, NButton, useMessage } from 'naive-ui'
import api from '@/api'
import { ref } from "vue"
import * as THREE from 'three'
import { OrbitControls } from 'three/addons/controls/OrbitControls'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
const message = useMessage()
onMounted(
    () => {
        initModelShow()
        getSceneDeviceDeployInfo()
    }
)
// 获取场景id
const router = useRouter();
var fullPath = router.currentRoute.value.fullPath
var sceneId = router.currentRoute.value.params.id

// 打开添加模型模态框
var deviceInfoModelShow = ref(false)
var bodyStyle = { width: "600px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
// 场景模型列表
var modelList = ref([])
// 打开添加模型模态框
var spinShow = ref(true)
function openDeviceInfoModel(tempVar) {
    spinShow.value = true
    deviceInfoModelShow.value = true
    getDeviceDeploy(tempVar)
}

// THREE
var screenDom = ref(null)
const width = window.innerWidth
const height = window.innerHeight
const k = width / height
const s = 100
var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(45, k, 0.1, 1000)
var renderer = new THREE.WebGLRenderer({ antialias: true });
function initModelShow() {
    // 1. 创建空白场景

    camera.position.set(7.6, 69.5, 115.0) // 设置相机位置xyz

    // 3. 创建渲染器

    renderer.setPixelRatio(window.devicePixelRatio); // 设置设备像素比率。通常用于HiDPI设备，以防止输出画布模糊。
    renderer.setSize(width, height); // 设置渲染器大小 ***
    renderer.setClearColor(0xb9d3ff, 1); //设置背景颜色
    renderer.shadowMap.enabled = true;
    // 将画布添加到页面中
    screenDom.value.appendChild(renderer.domElement)


    // 添加地面网格
    const gridHelper = new THREE.GridHelper(100, 50);
    scene.add(gridHelper);


    //创建光源
    const light = new THREE.PointLight(0xffffff, 2); // 点光源 颜色为白色，强度为
    light.position.set(40, 40, 20); // 设置灯源位置
    // light.castShadow = true; // 允许生成阴影
    scene.add(light); // 添加到场景中

    //创建环境光
    const ambient = new THREE.AmbientLight(0x444444);
    scene.add(ambient);

    // 创建控制器
    let controls = new OrbitControls(camera, renderer.domElement);
    controls.enableDamping = true
    controls.dampingFactor = 0.05;

    // 鼠标双击触发的方法
    function onMouseDblclick(event) {
        // 获取 raycaster 和所有模型相交的数组，其中的元素按照距离排序，越近的越靠前
        var intersects = getIntersects(event)

        // 获取选中最近的 Mesh 对象
        if (intersects.length != 0 && intersects[0].object instanceof THREE.Mesh) {

            // 获取父对象
            let tempVar = intersects[0].object
            while (1) {
                if (tempVar.parent.type === "Scene") {
                    break;
                } else {
                    tempVar = tempVar.parent
                }
            }

            // TODO 此处进行弹窗操作
            openDeviceInfoModel(tempVar)

        } else {
            console.log("未选中 Mesh!");
        }
    }

    // 获取与射线相交的对象数组
    function getIntersects(event) {
        event.preventDefault();
        console.log("event.clientX:" + event.clientX)
        console.log("event.clientY:" + event.clientY)

        // 声明 raycaster 和 mouse 变量
        var raycaster = new THREE.Raycaster();
        var mouse = new THREE.Vector2();
        // 通过鼠标点击位置,计算出 raycaster 所需点的位置,以屏幕为中心点,范围 -1 到 1
        mouse.x = ((event.clientX - 220) / width) * 2 - 1;
        mouse.y = -((event.clientY - 115) / height) * 2 + 1;

        //通过鼠标点击的位置(二维坐标)和当前相机的矩阵计算出射线位置
        raycaster.setFromCamera(mouse, camera);

        // 获取与射线相交的对象数组，其中的元素按照距离排序，越近的越靠前
        var intersects = raycaster.intersectObjects(scene.children, true);

        //返回选中的对象
        return intersects;
    }

    addEventListener('dblclick', onMouseDblclick, false);
    animate();

    function animate() {
        requestAnimationFrame(animate);
        controls.update();
        renderer.render(scene, camera);
    }
}

// 获得场景中的部署设备表
var deviceDeploy
async function getSceneDeviceDeployInfo() {
    let data = await api.getSceneDeviceDeployInfo(sceneId)
    deviceDeploy = data.data
    //console.log(deviceDeploy);
    loadModelByList()
}
// 加载模型列表中的
async function loadModelByList() {
    for (let i = 0; i < deviceDeploy.length; i++) {
        let data = await api.getModelInfo(deviceDeploy[i].deviceId)
        let modelInfo = {}
        modelInfo.id = deviceDeploy[i].id
        modelInfo.url = data.data.deviceUrl
        modelInfo.scalingX = deviceDeploy[i].scalingX
        modelInfo.scalingY = deviceDeploy[i].scalingY
        modelInfo.scalingZ = deviceDeploy[i].scalingZ
        modelInfo.positionX = deviceDeploy[i].positionX
        modelInfo.positionY = deviceDeploy[i].positionY
        modelInfo.positionZ = deviceDeploy[i].positionZ
        loadModel(modelInfo)
    }
}

// 模型加载
function loadModel(modelInfo) {
    let loader = new GLTFLoader()
    loader.load(modelInfo.url, (gltf) => {
        //gltf.scene.scale.set(modelInfo.scalingX, modelInfo.scalingY, modelInfo.scalingZ)
        //gltf.scene.position.set(modelInfo.positionX, modelInfo.positionY, modelInfo.positionZ)
        let object3D = new THREE.Object3D();
        object3D.scale.set(modelInfo.scalingX, modelInfo.scalingY, modelInfo.scalingZ)
        object3D.position.set(modelInfo.positionX, modelInfo.positionY, modelInfo.positionZ)
        object3D.userData = { id: modelInfo.id }
        object3D.add(gltf.scene)
        scene.add(object3D)
    })
}

// 获取部署设备信息
var deviceId
var deviceInfo = ref({
    // 设备名称
    deviceName: "",
    // 设备启动状态 1为启动 0为关闭
    deviceSwitch: "",
    // 设备故障状态 1为正常 0为故障
    deviceState: "",
    // 设备温度
    deviceTemp: "",
    // 设备
})
async function getDeviceDeploy(tempVar) {
    //console.log(tempVar.userData.id);
    let data = await api.getDeviceDeployInfo(tempVar.userData.id)
    deviceId = data.data.id
    var obj = JSON.parse(data.data.deviceInfo)
    deviceInfo.value.deviceName = obj.deviceName
    deviceInfo.value.deviceSwitch = obj.deviceSwitch === 1 ? true : false
    deviceInfo.value.deviceState = obj.deviceState
    deviceInfo.value.deviceTemp = obj.deviceTemp
    spinShow.value = false
    // console.log(data.data.deviceInfo);
}

async function submitBtn() {
    //console.log(JSON.stringify(deviceInfo.value))
    let deviceInfoVo = {
        id: "",
        deviceInfo: ""
    }
    deviceInfoVo.id = deviceId
    deviceInfoVo.deviceInfo = JSON.stringify(deviceInfo.value)
    let data = await api.updataDeviceInfo(deviceInfoVo)
    if (data.code === "00000") {
        message.success("保存成功")
        deviceInfoModelShow.value = false
    }
}
</script>
  