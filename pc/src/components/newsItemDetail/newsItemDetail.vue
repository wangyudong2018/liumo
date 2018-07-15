<template>
  <div class="news-detail-wrapper clearfix" @click="goDetail(newsItem.id)">
    <div class="news-detail-img fl">
      <img :src="loadImgUrl + newsItem.thumbnail">
    </div>
    <div class="news-detail-con fl">
      <h2 class="news-detail-title">{{newsItem.title}}</h2>
      <div class="news-detail-text">{{newsItem.brief}} </div>
      <div class="news-detail-other clearfix">
        <div class="news-date fl">{{newsItem.releaseDate}}</div>
        <div class="news-copy fr">{{newsItem.original}}</div>
      </div>
    </div>
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
  }
}
</script>

<style scoped>
  .news-detail-wrapper {
    padding: 50px 0;
    border-bottom: 12px solid #f2f2f2;
    cursor: pointer;
  }

  .news-detail-img {
    width: 360px;
    height: 214px;
    overflow: hidden;
  }

  .news-detail-img img {
    width: 100%;
    height: 100%;
  }

  .news-detail-con {
    position: relative;
    width: 865px;
    height: 214px;
    padding-left: 70px;
  }

  .news-detail-title {
    height: 40px;
    line-height: 40px;
    margin-bottom: 15px;
    font-size: 40px;
    font-weight: 700;
    color: #000000;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .news-detail-text {
    line-height: 40px;
    font-size: 18px;
    color: #666666;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }

  .news-detail-other {
    position: absolute;
    right: 0;
    bottom: 0;
    color: #666666;
  }

  .news-date {
    width: 400px;
    font-size: 24px;
  }

  .news-copy {
    width: 395px;
    text-align: right;
    font-size: 24px;
  }
</style>
