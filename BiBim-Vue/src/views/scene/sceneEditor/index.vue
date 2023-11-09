<template id="test">
    <div flex-1>
        <n-button type="primary" style="margin-right: 10px;" @click="openAddModel">
            添加模型
        </n-button>
        <n-button type="error" style="margin-right: 10px;" @click="removeModel">
            删除模型
        </n-button>
        <n-button type="success" style="margin-right: 10px;" @click="saveScene">
            保存场景
        </n-button>
        <n-tag type="success" size="large" round>
            操作提示： R:调整旋转。S:调整缩放。T:调整位置。+:放大手柄。-:缩小手柄。
        </n-tag>
    </div>
    <!-- Threejs渲染元素 -->
    <div flex-1 id="threeDOM" ref="screenDom">
    </div>

    <!-- 添加模型模态框-->
    <n-modal v-model:show="addModelShow" class="custom-card" preset="card" :style="bodyStyle" title="请选择模型" size="huge"
        :bordered="false" :segmented="segmented">
        <n-scrollbar style="max-height: 200px">
            <n-radio-group v-model:value="modelId" name="modelGroup">
                <a v-for="model in modelList">
                    <n-radio :key="model.id" :value="model.id">
                        {{ model.deviceName }}
                    </n-radio>
                    <br>
                </a>
            </n-radio-group>
        </n-scrollbar>
        <n-button type="primary" style="margin-right: 10px;" @click="addModelToScene">
            添加到场景中
        </n-button>
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
import { TransformControls } from 'three/addons/controls/TransformControls.js'

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
var addModelShow = ref(false)
var modelId = ref()
var bodyStyle = { width: "600px" }
var segmented = {
    content: "soft",
    footer: "soft"
}
// 场景模型列表
var modelList = ref([])
// 打开添加模型模态框
async function openAddModel() {
    addModelShow.value = true
    let data = await api.getModelList(1, 100000)
    modelList.value = data.data.list
}
// 将模型添加到场景中，并记录在模型列表
async function addScene() {
    addModelShow.value = false
}
// 添加模型到场景
async function addModelToScene() {
    addModelShow.value = false
    let data = await api.getModelInfo(modelId.value)
    let modelInfo = {}
    modelInfo.id = - Math.round(Math.random() * (100000 - 10000) + 10000)
    modelInfo.url = data.data.deviceUrl
    modelInfo.scalingX = 1
    modelInfo.scalingY = 1
    modelInfo.scalingZ = 1
    modelInfo.positionX = 0
    modelInfo.positionY = 0
    modelInfo.positionZ = 0
    modelInfo.sceneId = sceneId
    modelInfo.deviceId = modelId.value
    deviceDeploy.push(modelInfo)
    loadModel(modelInfo)
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
const transformControls = new TransformControls(camera, renderer.domElement);
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

    //创建坐标轴
    const axisHelper = new THREE.AxesHelper(1000);
    scene.add(axisHelper)

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
    // controls.enabled = false;
    transformControls.setSize(0.5);
    transformControls.setMode("translate");
    //当除了移动控件的其他控件触发时，禁止移动控件
    transformControls.addEventListener('dragging-changed', function (event) {

        controls.enabled = !event.value;

    });
    scene.add(transformControls);

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
            // 不是位置控制器才能进行移动
            if (tempVar.isTransformControls !== true) {
                transformControls.detach(transformControls.object)
                transformControls.attach(tempVar);
            }
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
        mouse.y = -((event.clientY - 145) / height) * 2 + 1;

        //通过鼠标点击的位置(二维坐标)和当前相机的矩阵计算出射线位置
        raycaster.setFromCamera(mouse, camera);

        // 获取与射线相交的对象数组，其中的元素按照距离排序，越近的越靠前
        var intersects = raycaster.intersectObjects(scene.children, true);

        //返回选中的对象
        return intersects;
    }


    //键盘响应
    window.addEventListener('keydown', function (event) {

        switch (event.keyCode) {

            case 82: // R
                transformControls.setMode('rotate');
                break;
            case 83: // S
                transformControls.setMode('scale');
                break;
            case 84: // T
                transformControls.setMode('translate');
                break;
            case 187:
            case 107: // +, =, num+
                transformControls.setSize(transformControls.size + 0.1);
                break;
            case 189:
            case 109: // -, _, num-
                transformControls.setSize(Math.max(transformControls.size - 0.1, 0.1));
                break;
        }

    });
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

// 保存场景
const message = useMessage()
async function saveScene() {
    for (var i = 0; i < scene.children.length; i++) {
        if (scene.children[i].userData.id !== undefined) {
            for (let j = 0; j < deviceDeploy.length; j++) {
                if (deviceDeploy[j].id === scene.children[i].userData.id) {
                    deviceDeploy[j].scalingX = scene.children[i].scale.x
                    deviceDeploy[j].scalingY = scene.children[i].scale.y
                    deviceDeploy[j].scalingZ = scene.children[i].scale.z
                    deviceDeploy[j].positionX = scene.children[i].position.x
                    deviceDeploy[j].positionY = scene.children[i].position.y
                    deviceDeploy[j].positionZ = scene.children[i].position.z
                }
            }
        }
    }

    for (let j = 0; j < deviceDeploy.length; j++) {
        delete deviceDeploy[j].isDelete
        delete deviceDeploy[j].remarks
        delete deviceDeploy[j].updateBy
        delete deviceDeploy[j].createBy
        delete deviceDeploy[j].updateTime
        delete deviceDeploy[j].createTime
    }
    // 获取deviceDeploy时，要清除不必要的信息。例如更新时间
    // 遍历scene对象，将数据写入deviceDeploy
    let data = await api.updataDeviceDeployInfo(deviceDeploy)
    if (data.code === "00000") {
        message.success(data.data)
        location.reload()
    }
}

// 删除模型
async function removeModel() {
    if (transformControls.object === undefined) {
        message.error("请选择要删除的设备!")
        return
    }
    let deviceId = transformControls.object.userData.id
    if (deviceId > 0) {
        scene.remove(transformControls.object)
        transformControls.detach(transformControls.object)
        let data = await api.deleteDeviceDeploy(deviceId)
        if (data.code === "00000") {
            message.success(data.data)
        }
    } else {
        // 新增模型只删除场景中的模型
        scene.remove(transformControls.object)
        transformControls.detach(transformControls.object)
    }

    // 删除场景部署设备列表中的数据
    for (let j = 0; j < deviceDeploy.length; j++) {
        if (deviceDeploy[j].id === deviceId) {
            delete deviceDeploy[j]
        }
    }
}
</script>
  