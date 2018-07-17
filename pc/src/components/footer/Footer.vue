<template>
  <div class="footer-wrapper">
    <div class="footer-content">
      <div class="footer-top clearfix">
        <div class="footer-top-lt fl">
          <div class="footer-logo">
            <img src="./imgs/footer-logo.png" alt="六漠科技">
          </div>
        </div>
        <ul class="footer-links fl">
            <li><router-link to="/protocol">服务协议</router-link></li>
            <li class="split-line"></li>
            <li><router-link to="/joinus">诚聘英才</router-link></li>
          </ul>
        <div class="footer-top-rt fr">
          <div class="hot-tel">400-9999-715</div>
          <p class="hot-tel-des">唯一官方热线</p>
        </div>
      </div>
      <div class="footer-bottom">
        <p>copyright 2015-2018</p>
        <p class="footer-company">六漠（上海）网络科技有限公司</p>
        <p>版权所有ICP证：沪D8-20180808</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style>
.footer-wrapper {
  width: 100%;
  background: #000000;
}

.footer-content {
  width: 925px;
  height: 100px;
  margin: 0 auto;
  padding-top: 24px;
  color: #ffffff;
}

.footer-top-lt {
  width: 396px;
}

.footer-logo {
  width: 325px;
  height: 26px;
}

.footer-links {
  width: 262px;
  height: 26px;
  line-height: 26px;
}

.footer-links li {
  float: left;
  padding: 0 20px;
}

.footer-links .split-line {
  height: 16px;
  padding: 0;
  margin-top: 5px;
  border-left: 1px solid #ffffff;
}

.footer-links li a {
  display: inline-block;
  height: 26px;
  line-height: 26px;
  font-size: 18px;
  color: #ffffff;
}

.footer-top-rt {
  width: 267px;
}

.footer-top-rt .hot-tel, .footer-top-rt .hot-tel-des {
  float: left;
  height: 26px;
  line-height: 26px;
  text-align: left;
  font-size: 18px;
}

.footer-top-rt .hot-tel-des {
  padding-left: 10px;
}

.footer-bottom {
  padding-top: 15px;
  text-align: center;
  font-size: 0;
}

.footer-bottom p {
  display: inline-block;
  font-size: 14px;
}

.footer-company {
  margin-left: 48px;
  margin-right: 20px;
}
</style>
