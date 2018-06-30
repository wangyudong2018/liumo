<template>
  <div class="nav-wrapper">
    <div class="nav-content">
      <div class="nav-left fl">
        <div class="nav-logo fl">
          <img src="./imgs/logo.png" alt="六漠科技LOGO">
        </div>
        <div class="nav-title fl">
          <img src="./imgs/title.png" alt="六漠科技">
        </div>
        <div class="nav-location fl">
          <i class="iconfont icon-coordinates_fill"></i>
          <span class="city-name">{{currentPosition}}</span>
        </div>
      </div>
      <div class="nav-right fr">
        <ul>
          <li><router-link to="/">首页</router-link></li>
          <li><router-link to="/service">精选服务</router-link></li>
          <li><router-link to="/news">新闻中心</router-link></li>
          <li><router-link to="/about">了解六漠</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jQuery'
import { loadAMapJS } from '../../utils/asyncLoadJS'
const loadUrl = 'http://api.map.baidu.com/getscript?v=3.0&ak=DTvpddNZXw3opkwd9bt7sd9RZLash8yk&services=&t=20180626110404'
export default {
  data () {
    return {
      currentPosition: '',
      loadedAMapJS: false
    }
  },
  created () {
    // 判断是否加载过
    if (!this.loadedAMapJS) {
      loadAMapJS().then(() => {
        this.loadedAMapJS = true
      })
    }
  },
  mounted () {
    // 还可以再进行优化
    let interval = setInterval(() => {
      if (this.loadedAMapJS) {
        let timer = setInterval(() => {
          if ($('script[src="' + loadUrl + '"]').length > 0) {
            $.getScript(loadUrl, () => {
              clearInterval(timer)
              clearInterval(interval)
              this.getCurrentLocation()
            })
          }
        }, 30)
      }
    }, 300)
  },
  methods: {
    getCurrentLocation () {
      /* global BMap,BMAP_STATUS_SUCCESS */
      let self = this
      new BMap.Geolocation().getCurrentPosition(function (r) {
        this.getStatus() === BMAP_STATUS_SUCCESS ? self.currentPosition = r.address.city : self.currentPosition = ''
      })
    }
  }
}
</script>

<style scoped>
.nav-wrapper {
  height: 120px;
  background: #ffffff;
}

.nav-content {
  width: 1205px;
  height: 120px;
  margin: 0 auto;
  padding: 32px 0;
}

.nav-left {
  width: 530px;
  height: 56px;
}

.nav-right {
  width: 675px;
}

.nav-logo {
  width: 56px;
  height: 56px;
  overflow: hidden;
}

.nav-title {
  width: 205px;
  height: 56px;
  padding: 3px 0 3px 15px;
  overflow: hidden;
}

.nav-logo img, .nav-title img {
  width: 100%;
  height: auto;
}

.nav-location {
  width: 269px;
  padding: 20px 0 6px 30px;
  font-size: 0;
  color: #838383;
}

.nav-location .iconfont {
  display: inline-block;
  width: 30px;
  vertical-align: middle;
  font-size: 30px;
}

.nav-location .city-name {
  display: inline-block;
  width: 200px;
  height: 30px;
  line-height: 30px;
  margin-left: 9px;
  vertical-align: middle;
  font-size: 28px;
}

.nav-right li {
  float: left;
  margin-left: 90px;
}

.nav-right li a {
  display: inline-block;
  height: 56px;
  line-height: 56px;
  font-size: 22px;
  font-weight: 700;
  color: #000000;
}
</style>
