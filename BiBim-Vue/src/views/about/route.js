const Layout = () => import('@/layout/index.vue')

export default {
    name: 'About',
    path: '/about',
    component: Layout,
    redirect: '/about',
    meta: {
        order: 10,
    },
    children: [
        {
            name: 'About',
            path: 'about',
            component: () => import('./index.vue'),
            meta: {
                title: '关于系统',
                icon: 'simple-icons:aboutdotme',
                role: ['ROOT','ADMIN','SCENER','USER'],
                requireAuth: true,
                order: 3,
            },
        },
    ]
}
