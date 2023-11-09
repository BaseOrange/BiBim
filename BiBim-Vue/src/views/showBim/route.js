const Layout = () => import('@/layout/index.vue')

export default {
    name: 'ShoWBim',
    path: '/showBim',
    component: Layout,
    redirect: '/showBim',
    meta: {
        title: '工业建筑浏览器',
        order: 2,
    },
    children: [
        {
            name: 'ShowBim',
            path: 'showBimList',
            component: () => import('./list/index.vue'),
            meta: {
                title: '工业建筑浏览器',
                icon: 'icon-park-outline:browser-chrome',
                role: ['ROOT','ADMIN','SCENER','USER'],
                requireAuth: true,
                order: 3,
            },
        },
        {
            name: 'ShowBim1',
            path: 'bimBrowser/:id',
            component: () => import('./show/index.vue'),
            meta: {
                title: '工业建筑浏览器',
                icon: 'icon-park-outline:browser-chrome',
                role: ['ROOT','ADMIN','SCENER','USER'],
                requireAuth: true,
            },
            isHidden: true,
        }
    ]
}
