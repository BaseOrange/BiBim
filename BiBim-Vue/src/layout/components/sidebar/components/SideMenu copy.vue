<template>
  <n-menu class="side-menu" accordion :indent="18" :collapsed-icon-size="22" :collapsed-width="64"
    :options="test" />
</template>

<script setup>
import api from '@/api'
import { h } from "vue"
import { menusStore } from '@/store'
import { RouterLink } from "vue-router";

getMenus()
const menusStoreConst = menusStore()
var menuArray1 = []

var menuOptions = menusStoreConst.menus

var test = [
  {
    label: () => h(
      "a",
      {
        href: "https://baike.baidu.com/item/%E4%B8%94%E5%90%AC%E9%A3%8E%E5%90%9F1",
        target: "_blank",
        rel: "noopenner noreferrer"
      },
      "且听风吟"
    ),
    key: "hear-the-wind-sing",
    //icon: renderIcon(BookIcon)
  }]
  console.log(JSON.parse(JSON.stringify(test[0])));



// TODO 获取菜单列表
async function getMenus() {
  const res = await api.getMenus()
  // 将数据处理成菜单可接受的格式
  const menuArray = res.data
  for (var i = 0; i < menuArray.length; i++) {
    var menusInfo = {}
    menusInfo.label = () => h(
      RouterLink,
      {
        to: {
          name: "home",
          params: {
            lang: "zh-CN"
          }
        }
      },
      { default: () => "回家" }
    )
    menusInfo.key = menuArray[i].id+""
    //menusInfo.ico = ""
    menuArray1.push(menusInfo)
  }
  
  menusStoreConst.menus = menuArray1
  console.log(JSON.parse(JSON.stringify(menuArray1[0])));
  // console.log(menuArray1);
}

</script>

<style lang="scss">
.side-menu:not(.n-menu--collapsed) {
  .n-menu-item-content {
    &::before {
      left: 5px;
      right: 5px;
    }

    &.n-menu-item-content--selected,
    &:hover {
      &::before {
        border-left: 4px solid var(--primary-color);
      }
    }
  }
}
</style>
