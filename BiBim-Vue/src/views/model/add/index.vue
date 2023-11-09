<template>
    <CommonPage show-footer title="添加模型">

        <n-grid x-gap="12" :cols="2">
            <n-gi>
                <n-form ref="formRef" :rules="rules" :label-width="125" :model="modelInfo" label-placement="left"
                    size="large" label-width="auto" style="width: 800px;">
                    <n-form-item label="模  型  名  称" path="deviceName">
                        <n-input v-model:value="modelInfo.deviceName" placeholder="输入模型名称" />
                    </n-form-item>
                    <n-form-item label="模型物理尺寸" path="deviceSize">
                        <n-input v-model:value="modelInfo.deviceSize" placeholder="输入模型物理尺寸" />
                    </n-form-item>
                    <n-form-item label="设备X轴比例" path="scalingX">
                        <n-input v-model:value="modelInfo.scalingX" placeholder="输入设备x轴缩放比例" />
                    </n-form-item>
                    <n-form-item label="设备Y轴比例" path="scalingY">
                        <n-input v-model:value="modelInfo.scalingY" placeholder="输入设备y轴缩放比例" />
                    </n-form-item>
                    <n-form-item label="设备Z轴比例" path="scalingZ">
                        <n-input v-model:value="modelInfo.scalingZ" placeholder="输入设备z轴缩放比例" />
                    </n-form-item>
                    <n-form-item label="设  备  信  息" path="deviceInfo">
                        <n-input v-model:value="modelInfo.deviceInfo" type="textarea" placeholder="输入设备信息" />
                    </n-form-item>
                    <n-form-item label="模  型  上  传" path="deviceUrl">
                        <n-upload :custom-request="uploadCustom" :max="5" v-model:file-list="defaultFileList">
                            <n-upload-dragger>
                                <n-text style="font-size: 16px">
                                    点击或者拖动文件到该区域来上传
                                </n-text>
                                <n-p depth="3" style="margin: 8px 0 0 0">
                                    仅支持gltf格式模型
                                </n-p><n-p depth="3" style="margin: 8px 0 0 0">
                                    请不要上传敏感数据，比如你的银行卡号和密码，信用卡号有效期和安全码
                                </n-p>
                            </n-upload-dragger>
                        </n-upload>
                    </n-form-item>
                    <n-form-item>
                        <n-grid x-gap="12" :cols="6">
                            <n-gi></n-gi>
                            <n-gi></n-gi>
                            <n-gi>
                                <n-button type="success" @click="submit">
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
            </n-gi>
            <n-gi>
                <n-card title="模型预览" style="height: 100%;">
                    <div ref="screenDom"
                        class="m-auto p-15 f-c-c w-750 h-600 rounded-10 card-shadow bg-white bg-opacity-60"></div>
                </n-card>
            </n-gi>
        </n-grid>
    </CommonPage>
</template>
  
<script setup>
import api from '@/api'
import { ref } from "vue"
import { NTag, NButton, useMessage } from 'naive-ui'
import { useRouter } from 'vue-router'
import * as THREE from 'three'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { gsap } from 'gsap'

onMounted(() => {
    // 初始化THree
    initModelShow()
})
var defaultFileList = ref([])
// 模型信息
var modelInfo = ref({
    // 模型名称
    deviceName: "",
    // 设备物理尺寸
    deviceSize: "",
    // 设备x轴缩放比例
    scalingX: "",
    // 设备y轴缩放比例
    scalingY: "",
    // 设备z轴缩放比例
    scalingZ: "",
    // 设备模型链接
    deviceUrl: "",
    // 设备信息
    deviceInfo: "",
    // 创建用户id
    createUserId: "",
})

// 表单验证
const formRef = ref(null)
var rules = ref({
    deviceName: {
        required: true,
        message: '请输入设备名称',
        trigger: ['input', 'blur']
    },
    deviceSize: {
        required: true,
        message: '请输入设备物理尺寸',
        trigger: ['input', 'blur']
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
    deviceUrl: {
        required: true,
        message: '请上传模型',
        trigger: ['input', 'blur']
    }
    ,
    deviceInfo: {
        required: true,
        message: '请输入设备信息',
        trigger: ['input']
    }
})

//获取上传链接
async function getUploadUrl(file) {
    // 获取扩展名
    let extension = file.file.name.substring(file.file.name.lastIndexOf(".") + 1)
    if (extension !== "gltf") {
        message.error("仅支持gltf格式模型")
        return false
    }
    let data = await api.getUploadModelUrl(extension)
    if (data.code = "00000") {
        modelInfo.value.deviceUrl = data.data
        return true
    }
    // 上传链接出现错误
    return false
}

// 自定义上传
const message = useMessage()
var isSuccess = false
async function uploadCustom({
    file,
    data,
    headers,
    withCredentials,
    action,
    onFinish,
    onError,
    onProgress
}) {
    defaultFileList.value.length = 0
    await getUploadUrl(file)
    await api.uploadModel(modelInfo.value.deviceUrl, file.file)
    isSuccess = true
    modelInfo.value.deviceUrl = modelInfo.value.deviceUrl.substring(0, modelInfo.value.deviceUrl.indexOf("?"))
    addModelShow()
    console.log(scene.toJSON());
}

// 重置
function reset() {
    modelInfo.value.deviceName = ""
    modelInfo.value.deviceSize = ""
    modelInfo.value.scalingX = ""
    modelInfo.value.scalingY = ""
    modelInfo.value.scalingZ = ""
    modelInfo.value.deviceUrl = ""
    modelInfo.value.deviceInfo = ""
    modelInfo.value.createUserId = ""
}

// 提交
const router = useRouter()
async function addModel() {
    if (isSuccess === true) {
        let data = await api.addModel(modelInfo.value);
        if (data.code === "00000") {
            router.push("/model/info")
        }
    }
}
function submit(e) {
    formRef.value?.validate((errors) => {
        if (!errors) {
            addModel()
        } else {
            message.error("表单信息有误");
        }
    });
}


// THREE
var screenDom = ref(null)
var scene = new THREE.Scene()
function initModelShow() {
    // 创建相机
    let camera = new THREE.PerspectiveCamera(45, 950 / 650, 0.1, 1000)
    camera.position.set(0, 0, 10)
    // 创建渲染器
    let renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true })
    renderer.setSize(950, 650)

    // 将画布添加到页面中
    screenDom.value.appendChild(renderer.domElement)

    // 修改透明背景
    // scene.background = new THREE.Color(0xa0a0a0)
    // renderer.setClearAlpha(0)

    function render() {
        requestAnimationFrame(render)
        renderer.render(scene, camera)
    }
    render()
}
function addModelShow() {
    scene.clear()
    //添加灯光
    let light1 = new THREE.DirectionalLight(0xffffff, 1)
    light1.position.set(0, 0, 1)
    scene.add(light1)
    let light2 = new THREE.DirectionalLight(0xffffff, 0.5)
    light2.position.set(0, 0, -1)
    scene.add(light2)
    let light3 = new THREE.DirectionalLight(0xffffff, 0.5)
    light3.position.set(-1, 1, 1)
    scene.add(light3)
    // 设置解压缩加载器
    let loader = new GLTFLoader()
    loader.load(modelInfo.value.deviceUrl, (gltf) => {
        gltf.scene.scale.set(0.3, 0.3, 0.3)
        gltf.scene.position.set(0, -1.5, 0)
        scene.add(gltf.scene)

        window.addEventListener('mousemove', (e) => {
            let x = (e.clientX / 950) * 2 - 1
            let y = (e.clientY / 650) * 2 - 1

            let timeLine = gsap.timeline()
            timeLine.to(gltf.scene.rotation, {
                duration: 0.5,
                x: y,
                y: x,
                duration: 1,
            })
        })
    })
}

</script>