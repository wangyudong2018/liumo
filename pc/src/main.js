// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import iview from 'iview'
import 'iview/dist/styles/iview.css'

import '@/assets/styles/base.css'
import '@/assets/iconfont/iconfont.css'

import http from './api/index'
Vue.prototype.$http = http

Vue.config.productionTip = false

Vue.use(iview)

// 安装百度统计
router.beforeEach((to, from, next) => {
  // _hmt.push(['_trackPageview', pageURL]) 必须是以"/"（斜杠）开头的相对路径
  if (window._htm) {
    if (to.path) window._htm.push(['_trackPageView', '/#' + to.fullPath])
  }
  next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
