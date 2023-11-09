import { defineStore } from 'pinia'
// 菜单store

export const menusStore = defineStore('menus', {
  // 存储菜单信息
  state:() => {
    return {
        menus: []
      }
  }
})