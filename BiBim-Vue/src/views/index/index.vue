<template>
  <AppPage :show-footer="true">
    <div flex-1>
      <n-card rounded-10>
        <div flex items-center>
          <icon-custom-logo text-50 color-primary></icon-custom-logo>
          <div ml-20>
            <p text-16>Hello</p>
            <p mt-5 text-12 op-60>今天又是元气满满的一天</p>
          </div>
          <div ml-auto flex items-center>

          </div>
        </div>
      </n-card>

      <n-card title="快速开始" size="small" :segmented="true" mt-15 rounded-10>
        <template #header-extra>
        </template>
        <div flex flex-wrap justify-between>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="智慧大脑" size="small" @click="goToPage('/smartBrain/smartBrain')">
            <p op-60>通过可视化展示，有效直观地查看系统数据统计。</p>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="工业建筑浏览器" size="small" @click="goToPage('/showBim/showBimList')">
            <p op-60>通过3D可视化展示，对工业建筑进行操作。</p>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="区块链浏览器" size="small" @click="goToPage('/blockchain/info')">
            <p op-60>查看区块链的区块高度、积累交易数、24小时交易量等。</p>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="场景编辑" size="small" @click="goToPage('/scene/editor')">
            <p op-60>编辑场景。</p>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="操作日志" size="small" @click="goToPage('/log/operate')">
            <p op-60>查看用户操作日志。</p>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" title="登陆日志" size="small" @click="goToPage('/log/login')">
            <p op-60>查看用户登陆日志。</p>
          </n-card>

        </div>
      </n-card>
    </div>
  </AppPage>
</template>

<script setup>
import { useUserStore } from '@/store'

const userStore = useUserStore()
const router = useRouter()

function goToPage(path){
  router.push(path)
}
</script>
