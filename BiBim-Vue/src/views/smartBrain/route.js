const Layout = () => import('@/layout/index.vue')

export default {
    name: 'SmartBrain',
    path: '/smartBrain',
    component: Layout,
    redirect: '/smartBrain',
    children: [
        {
            name: 'SmartBrain',
            path: 'smartBrain',
            component: () => import('./index.vue'),
            meta: {
                title: '智慧大脑',
                icon: 'carbon:machine-learning-model',
                role: ['ROOT','ADMIN','SCENER','USER'],
                requireAuth: true,
                order: 1,
            },
        },
    ]
}
