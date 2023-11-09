const Layout = () => import('@/layout/index.vue')

export default {
  name: 'Users',
  path: '/users',
  component: Layout,
  redirect: '/users',
  meta: {
    title: '用户管理',
    icon: 'material-symbols:supervised-user-circle',
    role: ['ROOT', 'ADMIN'],
    requireAuth: true,
    order: 3,
  },
  children: [
    {
      name: 'UsersInfo',
      path: 'info',
      component: () => import('./info/index.vue'),
      meta: {
        title: '用户信息',
        icon: 'ri:file-user-line',
        role: ['ROOT', 'ADMIN'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'UsersAdd',
      path: 'add',
      component: () => import('./add/index.vue'),
      meta: {
        title: '添加用户',
        icon: 'ph:user-plus-bold',
        role: ['ROOT'],
        requireAuth: true,
        keepAlive: true,
      },
    },
  ],
}
