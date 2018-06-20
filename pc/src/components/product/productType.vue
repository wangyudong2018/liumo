<template>
  <div class="pro-item">
    <div class="pro-detail">
      <div class="pro-intro clearfix">
        <p class="pro-label">
          产品介绍：
        </p>
        <p class="pro-des">{{productType.productIntro}}</p>
      </div>
      <div class="pro-suit clearfix">
        <p class="pro-label">适合人群：</p>
        <p class="pro-des">{{productType.productDes}}</p>
      </div>
      <div class="pro-btn-wrapper">
        <button class="pro-btn">立刻申请</button>
      </div>
    </div>
    <div class="pro-topic" :style="topicBg">
      <p class="topic-type">{{productType.loanTypeName}}</p>
      <p class="topic-des">居间服务</p>
      <p class="service-rate">服务费率：<span>{{productType.serviceRate | percent}}</span></p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    productType: {
      type: Object,
      required: true
    }
  },
  computed: {
    topicBg: function () {
      return {
        // 注意加载图片路径时必须使用require()方法，否则不能正确识别图片的路径
        backgroundImage: 'url(' + require('./imgs/pro_topic_bg' + this.productType.loanType + '.png') + ')'
      }
    }
  },
  filters: {
    percent: function (value) {
      if (!value) return ''
      value = value.toString()
      return `${value}%`
    }
  }
}
</script>

<style scoped>
  .pro-item {
    position: relative;
    width: 100%;
    height: 300px;
    margin-top: 160px;
    padding: 40px 0 0 458px;
    box-shadow: 5px 5px 20px 20px #efefef;
  }

  .pro-detail {
    padding-right: 45px;
  }

  .pro-label, .pro-des {
    float: left;
    line-height: 44px;
    font-size: 24px;
    color: #333333;
  }

  .pro-label {
    width: 120px;
  }

  .pro-des {
    width: 580px;
  }

  .pro-btn-wrapper {
    margin-top: 50px;
  }

  .pro-btn {
    display: block;
    width: 300px;
    height: 50px;
    line-height: 50px;
    margin: 0 auto;
    border: none;
    border-radius: 20px;
    font-size: 26px;
    background: #1c84ef;
    color: #ffffff;
    cursor: pointer;
  }

  .pro-topic {
    position: absolute;
    left: 28px;
    bottom: 0px;
    width: 380px;
    height: 380px;
    padding: 46px 0 0 44px;
    background-repeat: no-repeat;
    color: #ffffff;
  }

  .topic-type, .topic-des {
    line-height: 80px;
    font-size: 44px;
  }

  .service-rate {
    margin-top: 26px;
    height: 22px;
    line-height: 22px;
    font-size: 22px;
  }
</style>
