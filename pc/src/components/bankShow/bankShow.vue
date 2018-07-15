<template>
  <div class="bank-show-content" :style="bankShowBg">
    <div class="bank-show-intro" :style="leftOrRight">
      <h2 class="bank-show-name">{{bankShow.bankShowName}}</h2>
      <div class="bank-show-address">
        <p class="address-label">地址：</p>
        <p class="address-detail">{{bankShow.bankShowAddress}}</p>
      </div>
      <div class="bank-show-tel">
        <p class="tel-label">电话：</p>
        <div class="tel-detail-box">
          <p class="tel-detail"
            v-for="(tel, index) of bankShow.bankShowTels"
            :key="index"
          >{{tel}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    bankShow: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    }
  },
  computed: {
    bankShowBg: function () {
      return {
        // 注意加载图片路径时必须使用require()方法，否则不能正确识别图片的路径
        backgroundImage: 'url(' + require('./imgs/bankshow' + this.bankShow.bankShowId + '.png') + ')'
      }
    },
    leftOrRight: function () {
      return this.index % 2 === 0 ? {right: 0} : {left: 0}
    }
  }
}
</script>

<style scoped>
  .bank-show-content {
    position: relative;
    width: 100%;
    height: 310px;
    margin-bottom: 75px;
    background: url("./imgs/bankshow01.png") no-repeat;
    background-size: cover;
  }

  .bank-show-intro {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 442px;
    height: 310px;
    padding: 80px 0 0 60px;
    background-color: #2d86e7;
    background: rgba(45, 134, 231, .6);
    color: #ffffff;
  }

  .bank-show-name {
    height: 30px;
    line-height: 30px;
    margin-bottom: 50px;
    font-size: 30px;
    font-weight: 700;
  }

  .address-label, .address-detail, .tel-label, .tel-detail-box {
    float: left;
  }

  .address-label, .address-detail, .tel-label, .tel-detail {
    line-height: 30px;
    font-size: 14px;
  }

  .address-label, .tel-label {
    width: 47px;
  }

  .address-detail, .tel-detail {
    width: 335px;
  }
</style>
