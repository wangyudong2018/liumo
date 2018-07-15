<template>
  <div class="news-type-wrapper">
    <news-title :newsTitle="newsType.newsTitle"></news-title>
    <div class="news-type-content clearfix">
      <news-item
        v-for="newsItem of newsType.newsList"
        :key="newsItem.newsId"
        :newsItem="newsItem"
        :newsTypeId="newsType.newsTypeId"
        ></news-item>
    </div>
    <div class="loading-more-wrapper">
      <button class="loading-more-btn" @click="loadMore(newsType.newsTypeId)">查看更多</button>
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
  },
  methods: {
    loadMore (newsTypeId) {
      // 以下两种方式都可以控制路由跳转，第一种需要导入 router
      // router.push({path: '/news/newsItemList'})
      this.$router.push({path: '/news/newsItemList/' + newsTypeId})
    }
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
