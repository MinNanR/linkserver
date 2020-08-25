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
  if (to.path != '/login') {
    const token = localStorage.getItem("token")
    if (token == null) {
      next('/login')
    } else {
      next()
    }
  } else {
    next()
  }
  document.title = `${to.meta.title}`

})

const vm = new Vue({
  router,
  $,
  render: h => h(App),
}).$mount('#app')

export default vm
