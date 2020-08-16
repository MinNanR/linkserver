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
            component: () => import('../components/layout/layout.vue'),
            children: [{
                path: "/link",
                component: () => import('../components/page/link.vue'),
                meta: {
                    title: "链接"
                }
            }, {
                path: "/tools",
                component: () => import('../components/page/tools.vue'),
                meta:{
                    title:"工具"
                }
            },{
                path:"/introduction",
                component: () => import('../components/page/introduction.vue'),
                meta:{
                    title:'使用说明'
                }
            }]

        },
        {
            path: '/login',
            component: () => import('../components/page/login.vue'),
            meta: {
                title: '登陆'
            }
        }
    ]
})