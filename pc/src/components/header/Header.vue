<template>
  <div class="nav-wrapper">
    <div class="nav-content">
      <div class="nav-left fl">
        <div class="nav-logo fl">
          <img src="./imgs/logo.png" alt="六漠科技">
        </div>
        <div class="nav-title fl">
          <h1>六漠科技</h1>
        </div>
        <div class="nav-location fl">
          <i class="iconfont icon-coordinates_fill"></i>
          <span class="city-name">北京</span>
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
export default {}
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
  width: 185px;
  height: 56px;
  margin-left: 20px;
}

.nav-title h1 {
  line-height: 56px;
  font-size: 44px;
}

.nav-location {
  width: 120px;
  height: 56px;
  margin-left: 30px;
  font-size: 0;
  color: #838383;
}

.nav-location .iconfont {
  font-size: 28px;
}

.nav-location .city-name {
  display: inline-block;
  margin-left: 20px;
  padding-top: 16px;
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
