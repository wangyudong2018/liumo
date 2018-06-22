<template>
  <div class="news-media-wrapper">
    <news-title :newsTitle="newsMedia.newsTitle"></news-title>
    <div class="news-media-weibo">
      <div class="weibo-box"></div>
      <div class="weibo-content">
        <div class="weibo-logo">
          <img src="./imgs/weibo_logo.png" alt="六漠科技官方微博">
        </div>
        <p class="weibo-des">扫码关注六漠科技官方微博</p>
        <p class="weibo-link"><a href="#">https://ibaotu.com/sucai/418733.html</a></p>
      </div>
    </div>
    <div class="news-media-wechat">
      <div class="wechat-box"></div>
      <div class="wechat-content">
        <div class="wechat-logo-box clearfix">
          <div class="wechat-logo fl">
            <img src="./imgs/wechat_logo.png" alt="六漠科技官方微信">
          </div>
          <p class="wechat-platform fl">微信|公众平台</p>
        </div>
        <p class="wechat-des">扫码关注六漠科技官方微信</p>
      </div>
    </div>
  </div>
</template>

<script>
import newsTitle from '../newsTitle/newsTitle'
export default {
  props: {
    newsMedia: {
      type: Object,
      required: true
    }
  },
  components: {
    newsTitle
  }
}
</script>

<style scoped>
  .news-media-wrapper {
    padding-bottom: 10px;
  }

  .news-media-weibo, .news-media-wechat {
    position: relative;
    width: 100%;
    height: 244px;
    margin-top: 60px;
    margin-bottom: 172px;
    box-shadow: 5px 5px 20px 20px #efefef;
  }

  .news-media-weibo {
    padding-left: 552px;
  }

  .news-media-wechat {
    padding-right: 552px;
  }

  .weibo-content {
    padding: 30px 0 40px;
  }

  .weibo-logo {
    width: 202px;
    height: 69px;
    margin-bottom: 40px;
  }

  .weibo-des, .wechat-des {
    height: 24px;
    font-size: 24px;
    font-weight: 700;
  }

  .weibo-link {
    margin-top: 20px;
    font-size: 18px;
  }

  .weibo-link a {
    padding-bottom: 8px;
    border-bottom: 2px solid #2d86e7;
    color: #2d86e7;
  }

  .wechat-content {
    float: right;
    width: 328px;
    padding: 50px 0 60px;
  }

  .wechat-logo-box {
      margin-bottom: 40px;
  }

  .wechat-logo {
    width: 70px;
    height: 70px;
  }

  .wechat-logo img {
    width: 100%;
    height: auto;
  }

  .wechat-platform {
    width: 258px;
    height: 70px;
    line-height: 70px;
    font-size: 30px;
    font-weight: 700;
  }

  .weibo-box, .wechat-box {
    position: absolute;
    top: -72px;
    width: 242px;
    height: 388px;
  }

  .weibo-box {
    left: 190px;
    background: url("./imgs/weibo_code.png") no-repeat;
  }

  .wechat-box {
    right: 190px;
    background: url("./imgs/wechat_code.png") no-repeat;
  }
</style>
