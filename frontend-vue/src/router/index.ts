import {createRouter, createWebHistory} from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import LayoutPage from "@/pages/layout/LayoutPage.vue";
import LoginPage from "@/pages/login/LoginPage.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: LoginPage,
        },
        {
            path: '/',
            name: 'layout',
            component: LayoutPage,
            redirect: '/home',
            children: [
                {
                    path: '/aiChatbot',
                    name: 'aiChatbot',
                    component: () => import('@/pages/aiChatbot/AIChatbot.vue')
                },
                {
                    path: '/home',
                    name: 'home',
                    component: () => import('@/pages/home/Home.vue')
                },
                {
                    path: '/user',
                    name: 'user',
                    component: () => import('@/pages/system/user/UserList.vue')
                },
                {
                    path: '/userProfile',
                    name: 'userProfile',
                    component: () => import('@/pages/system/user/UserProfile.vue')
                },
                {
                    path: '/permission',
                    name: 'permission',
                    component: () => import('@/pages/system/permission/PermissionList.vue')
                },
                {
                    path: '/group',
                    name: 'group',
                    component: () => import('@/pages/system/group/GroupList.vue')
                },
                {
                    path: '/role',
                    name: 'role',
                    component: () => import('@/pages/system/role/RoleList.vue')
                },
                {
                    path: '/notFound',
                    name: 'notFound',
                    component: () => import('@/pages/notFound/NotFoundPage.vue')
                }
            ]
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'notFoundRedirect',
            redirect: "/notFound"
        }
    ],
})

//配置 NProgress
NProgress.configure({showSpinner: false})
router.beforeEach((to, from, next) => {
    NProgress.start()
    next()
})
router.afterEach(() => {
    NProgress.done()
})

export default router
