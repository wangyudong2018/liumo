<template>
  <div class="news-item-box fl" :style="newsTopic">
    <p class="news-item-des">{{newsItem.newsTopic | addLabel}}</p>
  </div>
</template>

<script>
export default {
  props: {
    newsItem: {
      type: Object,
      required: true
    }
  },
  computed: {
    newsTopic: function () {
      return {
        // 注意加载图片路径时必须使用require()方法，否则不能正确识别图片的路径
        backgroundImage: 'url(' + require('./imgs/news' + this.newsItem.newsId + '.png') + ')'
        // backgroundImage: 'url(' + this.newsItem.newsBgUrl + ')'
      }
    }
  },
  filters: {
    addLabel: function (value) {
      if (!value) return ''
      value = value.toString()
      return `主题论坛：${value}`
    }
  }
}
</script>

<style scoped>
  .news-item-box {
    position: relative;
    width: 240px;
    height: 260px;
    margin: 0 30px 54px 30px;
    background: url("./imgs/news01.png");
    background-repeat: no-repeat;
    background-size: cover;
  }

  .news-item-des {
    position: absolute;
    bottom: 30px;
    line-height: 30px;
    padding: 0 28px;
    text-align: center;
    font-size: 16px;
    font-weight: 700;
    color: #ffffff;
  }
</style>
