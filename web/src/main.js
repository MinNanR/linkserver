import Vue from 'vue'
import App from './App.vue'
import router from './router'

import $ from 'jquery'
import 'jquery'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap/dist/js/bootstrap.min.js'
import VueCookies from 'vue-cookies'

import request from '@/utils/request.js'
import config from '@/utils/config.js'

Vue.prototype.baseUrl = config.baseUrl

Vue.config.productionTip = false
Vue.prototype.request = request

Vue.use(VueCookies)

router.beforeEach((to, from, next) => {
  if (to.path == '/unauthorized' || to.path == '/login') {
    next()
  } else {
    const token = localStorage.getItem("token")
    let whiteList = localStorage.getItem("whiteList")
    whiteList = whiteList == null ? [] : whiteList
    if (token == null) {
      next('/login')
    } else if (whiteList.indexOf(to.path) != -1) {
      next()
    } else {
      next('/unauthorized')
    }
  }
  document.title = `${to.meta.title}`

})

const vm = new Vue({
  router,
  $,
  render: h => h(App),
}).$mount('#app')

export default vm
