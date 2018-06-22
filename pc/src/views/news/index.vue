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
import newsType from '../../components/newsType/newsType'
import newsMedia from '../../components/newsMedia/newsMedia'
export default {
  data () {
    return {
      newsTypeList: [
        {
          newsTypeId: '01',
          newsTitle: {newsTitleZh: '新闻发布', newsTitleEn: 'NEWS RELEASE'},
          newsList: [
            {
              newsId: '01',
              newsUrl: '',
              newsBgUrl: './imgs/news01.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '02',
              newsUrl: '',
              newsBgUrl: './imgs/news02.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '03',
              newsUrl: '',
              newsBgUrl: './imgs/news03.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '04',
              newsUrl: '',
              newsBgUrl: './imgs/news04.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '05',
              newsUrl: '',
              newsBgUrl: './imgs/news05.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '06',
              newsUrl: '',
              newsBgUrl: './imgs/news06.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '07',
              newsUrl: '',
              newsBgUrl: './imgs/news07.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '08',
              newsUrl: '',
              newsBgUrl: './imgs/news08.png',
              newsTopic: '金融业还需开放力度'
            }
          ]
        },
        {
          newsTypeId: '02',
          newsTitle: {newsTitleZh: '媒体报道', newsTitleEn: 'MEDIA REPORTS'},
          newsList: [
            {
              newsId: '01',
              newsUrl: '',
              newsBgUrl: './imgs/news01.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '02',
              newsUrl: '',
              newsBgUrl: './imgs/news02.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '03',
              newsUrl: '',
              newsBgUrl: './imgs/news03.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '04',
              newsUrl: '',
              newsBgUrl: './imgs/news04.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '05',
              newsUrl: '',
              newsBgUrl: './imgs/news05.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '06',
              newsUrl: '',
              newsBgUrl: './imgs/news06.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '07',
              newsUrl: '',
              newsBgUrl: './imgs/news07.png',
              newsTopic: '金融业还需开放力度'
            },
            {
              newsId: '08',
              newsUrl: '',
              newsBgUrl: './imgs/news08.png',
              newsTopic: '金融业还需开放力度'
            }
          ]
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
