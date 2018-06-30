<template>
  <div class="news-wrapper">
    <div class="news-content">
      <news-type
        v-for="newsType in newsTypeList"
        :key="newsType.newsTypeId"
        :newsType="newsType"
      ></news-type>
      <news-media :newsMedia="newsMedia"></news-media>
    </div>
  </div>
</template>

<script>
import api from '../../api/api'
import newsType from '../../components/newsType/newsType'
import newsMedia from '../../components/newsMedia/newsMedia'
export default {
  data () {
    return {
      newsTypeList: [
        {
          newsTypeId: '01',
          newsTitle: {newsTitleZh: '新闻发布', newsTitleEn: 'NEWS RELEASE'},
          newsList: []
        },
        {
          newsTypeId: '02',
          newsTitle: {newsTitleZh: '媒体报道', newsTitleEn: 'MEDIA REPORTS'},
          newsList: []
        }
      ],
      newsMedia: {
        newsTitle: {
          newsTitleZh: '社交媒体',
          newsTitleEn: 'SOCIAL MEDIA'
        }
      }
    }
  },
  components: {
    newsType,
    newsMedia
  },
  mounted () {
    this.$http.get(api.releaseList(), {category: 'news', limit: 100, offset: 0}, (res) => {
      this.newsTypeList[0].newsList = res.data.rows
    })
    this.$http.get(api.releaseList(), {category: 'media', limit: 100, offset: 0}, (res) => {
      this.newsTypeList[1].newsList = res.data.rows
    })
  }
}
</script>

<style scoped>
.new-wrapper {
  width: 100%;
}

.news-content {
  width: 1205px;
  margin: 0 auto;
}
</style>
