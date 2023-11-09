<template>
  <AppPage :show-footer="true" bg-cover :style="{ backgroundImage: `url(${bgImg})` }">
    <div
      ref="screenDom"
      style="position: absolute; left: 10%; transform: translateY(15%)"
      class="m-auto p-15 f-c-c w-1000 h-700 rounded-10 card-shadow bg-white bg-opacity-60"
    ></div>
    <div
      style="position: absolute; right: 10%; transform: translateY(40%)"
      class="mr-auto p-15 f-c-c w-400 h-500 rounded-10 card-shadow bg-white bg-opacity-60"
    >
      <div w-320 flex-col px-20 py-35>
        <h5 f-c-c text-24 font-normal color="#6a6a6a"><icon-custom-logo mr-10 text-50 color-primary />{{ title }}</h5>
        <div mt-30>
          <n-input
            v-model:value="loginInfo.username"
            autofocus
            class="text-16 items-center h-50 pl-10"
            placeholder="admin"
            :maxlength="20"
          />
        </div>
        <div mt-30>
          <n-input
            v-model:value="loginInfo.password"
            class="text-16 items-center h-50 pl-10"
            type="password"
            show-password-on="mousedown"
            placeholder="123456"
            :maxlength="20"
            @keydown.enter="handleLogin"
          />
        </div>

        <div mt-20>
          <n-checkbox :checked="isRemember" label="记住我" :on-update:checked="(val) => (isRemember = val)" />
        </div>

        <div mt-20>
          <n-button w-full h-50 rounded-5 text-16 type="primary" :loading="loading" @click="handleLogin">
            登录
          </n-button>
        </div>
      </div>
    </div>
  </AppPage>
</template>

<script setup>
import { lStorage, setToken } from '@/utils'
import { useStorage } from '@vueuse/core'
import bgImg from '@/assets/images/login_bg.webp'
import api from './api'
import { addDynamicRoutes } from '@/router'
import * as THREE from 'three'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { gsap } from 'gsap'



// ThreeJs代码
let screenDom = ref(null)
onMounted(() => {
  // 创建场景
  let scene = new THREE.Scene()
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
  loader.load('./model/cunchucang.gltf', (gltf) => {
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
})

// 登录模块
const title = import.meta.env.VITE_TITLE

const router = useRouter()
const { query } = useRoute()

const loginInfo = ref({
  username: '',
  password: '',
})

initLoginInfo()

function initLoginInfo() {
  const localLoginInfo = lStorage.get('loginInfo')
  if (localLoginInfo) {
    loginInfo.value.username = localLoginInfo.username || ''
    loginInfo.value.password = localLoginInfo.password || ''
  }
}

const isRemember = useStorage('isRemember', false)
const loading = ref(false)
async function handleLogin() {
  const { username, password } = loginInfo.value
  if (!username || !password) {
    $message.warning('请输入用户名和密码')
    return
  }
  try {
    loading.value = true
    $message.loading('正在验证...')
    const res = await api.login({ username, password: password.toString() })
    $message.success('登录成功')
    setToken(res.data.token)
    if (isRemember.value) {
      lStorage.set('loginInfo', { username, password })
    } else {
      lStorage.remove('loginInfo')
    }
    await addDynamicRoutes()
    if (query.redirect) {
      const path = query.redirect
      Reflect.deleteProperty(query, 'redirect')
      router.push({ path, query })
    } else {
      router.push('/')
    }
  } catch (error) {
    console.error(error)
    $message.removeMessage()
  }
  loading.value = false
}
</script>
