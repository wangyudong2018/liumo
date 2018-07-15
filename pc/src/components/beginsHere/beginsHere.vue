<template>
  <div class="begins-content">
    <div class="begins-container">
      <div class="begins-phone">
        <img src="./imgs/begins_phone.png" alt="">
      </div>
      <div class="begins-other">
        <div class="begins-other-top clearfix">
          <div class="begins-other-tl">
            <div class="begins-logo"></div>
            <div class="begins-name"></div>
          </div>
          <div class="begins-other-tr"></div>
        </div>
        <div class="begins-other-bottom clearfix">
          <div class="begins-other-bl">
            <div class="begins-btn-ios"></div>
            <div class="begins-btn-and"></div>
          </div>
          <div class="begins-other-br">
            <img src="./imgs/begins_code.png" alt="">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.begins-content {
  width: 100%;
  height: 560px;
  background: url("./imgs/begins_bg.png") no-repeat;
  background-size: cover;
}

.begins-container {
  position: relative;
  width: 1205px;
  margin: 0 auto;
}

.begins-phone {
  position: absolute;
  top: -100px;
  left: 80px;
  width: 280px;
  height: 566px;
  overflow: hidden;
}

.begins-phone img {
  width: 100%;
  height: auto;
}

.begins-other {
  padding-top: 60px;
  padding-left: 530px;
}

.begins-other-tl {
  float: left;
  width: 110px;
}

.begins-logo {
  width: 110px;
  height: 110px;
  background: url("./imgs/begins_logo.png") no-repeat;
  background-size: cover;
}

.begins-name {
  width: 110px;
  height: 24px;
  margin-top: 16px;
  background: url("./imgs/begins_name.png") no-repeat;
  background-size: cover;
}

.begins-other-tr {
  float: left;
  width: 407px;
  height: 108px;
  margin: 33px 108px 9px 50px;
  background: url("./imgs/begins-des.png") no-repeat;
  background-size: cover;
}

.begins-other-bottom {
  padding: 80px 0 0 18px;
}

.begins-other-bl {
  float: left;
  width: 330px;
}

.begins-btn-ios,
.begins-btn-and {
  height: 60px;
  background-size: cover;
}

.begins-btn-ios {
  margin-bottom: 16px;
  background: url("./imgs/begins_btn_ios.png") no-repeat;
}

.begins-btn-and {
  background: url("./imgs/begins_btn_and.png") no-repeat;
}

.begins-other-br {
  float: left;
  width: 136px;
  height: 136px;
  margin-left: 55px;
}

.begins-other-br img {
  width: 100%;
  height: auto;
}
</style>
