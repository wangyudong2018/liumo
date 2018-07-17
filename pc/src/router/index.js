import Vue from 'vue'
import Router from 'vue-router'

const Index = () => import('../views/index/index')
const Service = () => import('../views/service/index')
const News = () => import('../views/news/index')
const About = () => import('../views/about/index')
const JoinUS = () => import('../views/joinUs/joinUs')
const Protocol = () => import('../views/protocol/index')
const NewsTypeList = () => import('../views/news/newsDic/newsTypeList')
const NewsItemList = () => import('../views/news/newsDic/newsItemList')
const NewsDetail = () => import('../views/news/newsDic/newsDetail')

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
      component: News,
      redirect: '/news/newsTypeList',
      children: [
        {
          path: 'newsTypeList',
          name: 'newsTypeList',
          component: NewsTypeList
        },
        {
          path: 'newsItemList/:newsTypeId',
          name: 'newsItemList',
          component: NewsItemList
        },
        {
          path: 'newsDetail/:newsId',
          name: 'newsDetail',
          component: NewsDetail
        }
      ]
    },
    {
      path: '/about',
      name: 'About',
      component: About
    },
    {
      path: '/joinus',
      name: 'joinUS',
      component: JoinUS
    },
    {
      path: '/protocol',
      name: 'protocol',
      component: Protocol
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})
