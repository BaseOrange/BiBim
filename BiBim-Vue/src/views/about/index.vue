<template>
  <AppPage :show-footer="true">
    <div flex-1>
      <n-card title="前端框架" size="small" :segmented="true" mt-15 rounded-10>
        <div flex flex-wrap justify-between>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Vite" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/vite.svg" width="80" />
              </n-gi>
              <n-gi>
                <h1>本项目使用Vue生态工具Vite进行打包</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Vue" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/vue.svg" width="80" />
              </n-gi>
              <n-gi>
                <h1>本项目前端框架使用Vue</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Pinia" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/pinia.svg" width="50" />
              </n-gi>
              <n-gi>
                <h1>符合直觉的Vue.js状态管理库</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Vue Naive Admin"
            size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/VueNaiveAdmin.svg" width="80" />
              </n-gi>
              <n-gi>
                <h1>一个基于Vue3的轻量级后台管理模板，简单而优雅</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Threejs" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/threejs.png" width="80" />
              </n-gi>
              <n-gi>
                <h1>前端可视化3D框架</h1>
              </n-gi>
            </n-grid>

          </n-card>
        </div>
      </n-card>
    </div>
    <div flex-1>
      <n-card title="后端框架" size="small" :segmented="true" mt-15 rounded-10>
        <div flex flex-wrap justify-between>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="SpringBoot"
            size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/springboot.svg" width="80" />
              </n-gi>
              <n-gi>
                <h1>Spring Boot是用来简化新Spring应用的初始搭建以及开发过程的框架</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="SpringSecurity"
            size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/springsecurity.svg" width="50" />
              </n-gi>
              <n-gi>
                <h1>Spring官方的权限控制框架，可以很好的搭配SpringBoot使用</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="MybatisPlus"
            size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/mybatisplus.svg" width="80" />
              </n-gi>
              <n-gi>
                <h1>在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Swagger" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/swagger.svg" width="100" />
              </n-gi>
              <n-gi>
                <h1>一个规范且完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。</h1>
              </n-gi>
            </n-grid>
          </n-card>
        </div>
      </n-card>
    </div>
    <div flex-1>
      <n-card title="数据库、对象存储和区块链" size="small" :segmented="true" mt-15 rounded-10>
        <div flex flex-wrap justify-between>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="MariaDB" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/Mariadb.svg" width="120" />
              </n-gi>
              <n-gi>
                <h1>MariaDB数据库管理系统是MySQL的一个分支</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Redis" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/redis.jpg" width="90" />
              </n-gi>
              <n-gi>
                <h1>Redis即远程字典服务，是一个开源的Key-Value数据库</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="Minio" size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/minio.png" width="80" />
              </n-gi>
              <n-gi>
                <h1>基于 Golang 编写的开源对象存储套件</h1>
              </n-gi>
            </n-grid>
          </n-card>
          <n-card class="w-370 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="ChainMaker"
            size="small">
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <img src="@/assets/logo/ChainMaker.png" width="80" />
              </n-gi>
              <n-gi>
                <h1>由北京市政府牵头开发的国产区块链开源平台</h1>
              </n-gi>
            </n-grid>
          </n-card>
        </div>
      </n-card>
    </div>
  </AppPage>
</template>

<script setup>
import { useUserStore } from '@/store'

const userStore = useUserStore()
</script>
