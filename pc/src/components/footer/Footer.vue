<template>
  <div class="footer-wrapper">
    <div class="footer-content">
      <div class="footer-top clearfix">
        <div class="footer-top-lt fl">
          <div class="footer-logo">
            <img src="./imgs/footer-logo.png" alt="六漠科技">
          </div>
          <ul class="footer-links">
            <li><a href="#">关于六漠</a></li>
            <li><a href="#">服务协议</a></li>
            <li><a href="#">诚聘英才</a></li>
            <li><a href="#">联系我们</a></li>
          </ul>
        </div>
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
  position: fixed;
  bottom: 0;
  width: 100%;
  background: #000000;
}

.footer-content {
  width: 925px;
  height: 150px;
  margin: 0 auto;
  padding-top: 24px;
  color: #ffffff;
}

.footer-top-lt {
  width: 700px;
}

.footer-logo {
  width: 325px;
  height: 26px;
}

.footer-links {
  height: 22px;
  padding-bottom: 8px;
  margin: 18px 0 22px 42px;
}

.footer-links li {
  float: left;
  margin-left: 54px;
}

.footer-links li a {
  display: inline-block;
  height: 22px;
  line-height: 14px;
  font-size: 14px;
  color: #ffffff;
}

.footer-top-rt {
  width: 225px;
  text-align: right;
}

.footer-top-rt .hot-tel {
  height: 26px;
  line-height: 26px;
  font-size: 26px;
}

.footer-top-rt .hot-tel-des {
  height: 18px;
  line-height: 18px;
  margin-top: 18px;
  padding-right: 10px;
  font-size: 18px;
}

.footer-bottom {
  text-align: center;
  font-size: 0;
}

.footer-bottom p {
  display: inline-block;
  font-size: 10px;
}

.footer-company {
  margin-left: 48px;
  margin-right: 20px;
}
</style>
