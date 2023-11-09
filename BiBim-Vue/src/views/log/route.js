const Layout = () => import('@/layout/index.vue')

export default {
  name: 'Log',
  path: '/log',
  component: Layout,
  redirect: '/log',
  meta: {
    title: '系统日志',
    icon: 'material-symbols:logo-dev-outline',
    role: ['ROOT','ADMIN'],
    requireAuth: true,
    order: 9,
  },
  children: [
    {
      name: 'LoginLog',
      path: 'login',
      component: () => import('./login/index.vue'),
      meta: {
        title: '登录日志',
        icon: 'ic:outline-log-in',
        role: ['ROOT','ADMIN'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'OperateLog',
      path: 'operate',
      component: () => import('./operate/index.vue'),
      meta: {
        title: '操作日志',
        icon: 'ic:outline-app-registration',
        role: ['ROOT','ADMIN'],
        requireAuth: true,
        keepAlive: true,
      },
    },
  ],
}
