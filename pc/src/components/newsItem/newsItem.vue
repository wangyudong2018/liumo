<template>
  <div class="news-item-box fl" @click="goDetail(newsItem.id)">
    <img :src="loadImgUrl + newsItem.thumbnail">
    <p class="news-item-des">{{newsItem.brief | addLabel}}</p>
  </div>
</template>

<script>
import { LOAD_FILE } from '@/api/api'
export default {
  data () {
    return {
      loadImgUrl: LOAD_FILE
    }
  },
  props: {
    newsItem: {
      type: Object,
      required: true
    },
    newsTypeId: {
      type: String,
      required: true
    }
  },
  methods: {
    goDetail (newsId) {
      if (this.newsTypeId === '01') {
        this.$router.push({ path: '/news/newsDetail/' + newsId })
      } else {
        // window.location.href = this.newsItem.outChain
        window.open(this.newsItem.outChain, '_blank')
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
    cursor: pointer;
  }

  .news-item-box img {
    width: 100%;
    height: 100%;
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
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
</style>
