<template>
  <div class="news-type-wrapper">
    <news-title :newsTitle="newsType.newsTitle"></news-title>
    <div class="news-type-content clearfix">
      <news-item
        v-for="newsItem of newsType.newsList"
        :key="newsItem.newsId"
        :newsItem="newsItem"
        ></news-item>
    </div>
    <div class="loading-more-wrapper">
      <button class="loading-more-btn">查看更多</button>
    </div>
  </div>
</template>

<script>
import newsTitle from '../newsTitle/newsTitle'
import newsItem from '../newsItem/newsItem'
export default {
  props: {
    newsType: {
      type: Object,
      required: true
    }
  },
  components: {
    newsTitle,
    newsItem
  }
}
</script>

<style scoped>
  .loading-more-wrapper {
    margin: 6px 0 60px;
  }

  .loading-more-btn {
    display: block;
    width: 220px;
    height: 48px;
    line-height: 48px;
    margin: 0 auto;
    border: 1px solid #2d86e7;
    border-radius: 5px;
    font-size: 20px;
    background: #ffffff;
    color: #2d86e7;
    cursor: pointer;
  }
</style>
