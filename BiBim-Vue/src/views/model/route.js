const Layout = () => import('@/layout/index.vue')

export default {
  name: 'Model',
  path: '/model',
  component: Layout,
  redirect: '/model',
  meta: {
    title: '模型管理',
    icon: 'arcticons:smartthings',
    role: ['ROOT','ADMIN','SCENER'],
    requireAuth: true,
    order: 4,
  },
  children: [
    {
      name: 'ModelInfo',
      path: 'info',
      component: () => import('./info/index.vue'),
      meta: {
        title: '模型信息',
        icon: 'fluent-mdl2:modeling-view',
        role: ['ROOT','ADMIN','SCENER'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'ModelAdd',
      path: 'add',
      component: () => import('./add/index.vue'),
      meta: {
        title: '添加模型',
        icon: 'carbon:model-reference',
        role: ['ROOT','ADMIN','SCENER'],
        requireAuth: true,
        keepAlive: true,
      },
    },
  ],
}
