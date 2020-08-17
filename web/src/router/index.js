import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path:'/',
            redirect:'/link'
        },
        {
            path: '/',
            component: () => import('../components/server/layout/layout.vue'),
            children: [{
                path: "/link",
                component: () => import('../components/server/page/link.vue'),
                meta: {
                    title: "链接"
                }
            }, {
                path: "/tools",
                component: () => import('../components/server/page/tools.vue'),
                meta:{
                    title:"工具"
                }
            },{
                path:"/introduction",
                component: () => import('../components/server/page/introduction.vue'),
                meta:{
                    title:'使用说明'
                }
            }]

        },
        {
            path: '/login',
            component: () => import('../components/login.vue'),
            meta: {
                title: '登陆'
            }
        },
        {
            path:'/manager',
            component: () => import('../components/manager/layout/layout.vue'),
            meta:{
                title:'管理平台'
            },
            children:[
                {
                    path:"/",
                    component: () => import('../components/manager/page/index.vue'),
                    meta:{
                        title:'管理平台'
                    }
                },
                {
                    path:"link",
                    component: () => import('../components/manager/page/link.vue'),
                    meta:{
                        title:'管理平台'
                    }
                },
                {
                    path:"tools",
                    component: () => import('../components/manager/page/tools.vue'),
                    meta:{
                        title:'管理平台'
                    }
                },
                {
                    path:"introduction",
                    component: () => import('../components/manager/page/introduction.vue'),
                    meta:{
                        title:'管理平台'
                    }
                },
                {
                    path:"user",
                    component: () => import('../components/manager/page/user.vue'),
                    meta:{
                        title:'管理平台'
                    }
                },
                {
                    path:"logs",
                    component: () => import('../components/manager/page/logs.vue'),
                    meta:{
                        title:'管理平台'
                    }
                }
            ]
        }
    ]
})