import Vue from 'vue'
import Router from 'vue-router'

import Index from '../views/index/index'
import Service from '../views/service/index'
import News from '../views/news/index'
import About from '../views/about/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/service',
      name: 'Service',
      component: Service
    },
    {
      path: '/news',
      name: 'News',
      component: News
    },
    {
      path: '/about',
      name: 'About',
      component: About
    }
  ]
})
