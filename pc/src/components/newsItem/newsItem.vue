<template>
  <div class="news-item-box fl">
    <img :src="loadImgUrl + newsItem.id">
    <p class="news-item-des">{{newsItem.brief}}</p>
  </div>
</template>

<script>
import { LOAD_NEWS_IMG } from '../../api/api'
export default {
  data () {
    return {
      loadImgUrl: LOAD_NEWS_IMG
    }
  },
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
        // backgroundImage: 'url(' + require('./imgs/news0' + this.newsItem.id + '.png') + ')'
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
    overflow: hidden;
  }

  .news-item-box img {
    width: 100%;
    height: auto;
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
