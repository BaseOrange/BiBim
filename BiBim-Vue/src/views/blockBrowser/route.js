const Layout = () => import('@/layout/index.vue')

export default {
  name: 'Blockchain',
  path: '/blockchain',
  component: Layout,
  redirect: '/blockBrowser',
  meta: {
    title: '区块链浏览器',
    icon: 'icon-park-outline:blockchain',
    role: ['ROOT','ADMIN','SCENER','USER'],
    requireAuth: true,
    order: 3,
  },
  children: [
    {
      name: 'BlockInfo',
      path: 'info',
      component: () => import('./blockInfo/index.vue'),
      meta: {
        title: '区块链信息',
        icon: 'mdi:information',
        role: ['ROOT','ADMIN','SCENER','USER'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'BlockTrans',
      path: 'transInfo',
      component: () => import('./blockTrans/index.vue'),
      meta: {
        title: '交易列表',
        icon: 'ant-design:transaction-outlined',
        role: ['ROOT','ADMIN','SCENER','USER'],
        requireAuth: true,
        keepAlive: true,
      },
    },
  ],
}
