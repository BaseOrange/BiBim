const Layout = () => import('@/layout/index.vue')

export default {
    name: 'Scene',
    path: '/scene',
    component: Layout,
    redirect: '/scene',
    meta: {
        title: '场景管理',
        icon: 'material-symbols:home-work-outline',
        role: ['ROOT', 'ADMIN', 'SCENER'],
        requireAuth: true,
        order: 5,
    },
    children: [
        {
            name: 'SceneInfo',
            path: 'info',
            component: () => import('./info/index.vue'),
            meta: {
                title: '场景信息',
                icon: 'uil:file-info-alt',
                role: ['ROOT', 'ADMIN', 'SCENER'],
                requireAuth: true,
                keepAlive: true,
            },
        },
        {
            name: 'Editor',
            path: 'editor',
            component: () => import('./editor/index.vue'),
            meta: {
                title: '场景编辑',
                icon: 'icon-park-outline:editor',
                role: ['ROOT', 'ADMIN', 'SCENER'],
                requireAuth: true,
                keepAlive: true,
            },
        },
        {
            name: 'SceneEditor',
            path: 'sceneEditor/:id',
            component: () => import('./sceneEditor/index.vue'),
            isHidden: true,
        }
    ],
}
