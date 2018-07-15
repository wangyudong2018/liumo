<template>
  <div class="news-list-wrapper">
    <div class="news-title-box">
      <news-title :newsTitle="newsTypeId === '01' ? newsPublic : mediaPublic"></news-title>
    </div>
    <div class="new-list-box">
      <div class="layout-width layout-center news-banner" @click="goDetail(newsStickItem.id)">
        <img :src="loadImgUrl + newsItemList.thumbnail">
        <div class="topic-text">{{newsStickItem.title}}</div>
      </div>
      <div class="layout-width layout-center news-list-box">
        <news-item-detail
          v-for="newsItem in newsItemList"
          :key="newsItem.id"
          :newsItem="newsItem"
          :newsTypeId="newsTypeId"
        ></news-item-detail>
        <div class="news-page">
          <Page
            size="small"
            :total="count"
            :page-size="pageSize"
            @on-change="changePage"
          ></Page>
        </div>
      </div>
    </div>
    <div class="news-begin-box">
      <begins-here></begins-here>
    </div>
  </div>
</template>

<script>
import api, { LOAD_FILE } from '../../../api/api'
import newsTitle from '@/components/newsTitle/newsTitle'
import beginsHere from '@/components/beginsHere/beginsHere'
import newsItemDetail from '@/components/newsItemDetail/newsItemDetail'
export default {
  data () {
    return {
      count: 0, // 总记录数
      pageSize: 7, // 每页显示7条数据
      currentPage: 1, // 当前页码
      newsTypeId: '01',
      loadImgUrl: LOAD_FILE,
      newsStickItem: {},
      newsItemList: [],
      newsPublic: {
        newsTitleZh: '新闻发布',
        newsTitleEn: 'NEWS RELEASE'
      },
      mediaPublic: {
        newsTitleZh: '媒体报道',
        newsTitleEn: 'MEDIA REPORTS'
      },
      params: {
        channel: 'pc',
        category: '01',
        stick: '0',
        limit: '7',
        offset: '0'
      }
    }
  },
  created () {
    this.newsTypeId = this.$route.params.newsTypeId // 获取媒体类型（新闻发布、媒体报道）
    this.$http.get(api.releaseList(), {channel: 'pc', category: this.newsTypeId, stick: '1', limit: '1', offset: '0'}, (res) => {
      if (res.data.rows && res.data.rows.length > 0) {
        // 这个函数执行太晚了
        this.newsStickItem = Object.assign({}, this.newsStickItem, res.data.rows[0])
      }
    })
  },
  mounted () {
    this.getNewsItemList()
  },
  methods: {
    getNewsItemList () {
      let offset = (this.currentPage - 1) * this.pageSize // 计算偏移量
      this.params = Object.assign({}, this.params, {category: this.newsTypeId, offset: offset})
      this.$http.get(api.releaseList(), this.params, (res) => {
        if (res.data.rows && res.data.rows.length > 0) {
          this.newsItemList = res.data.rows
        }
        this.count = res.data.total
      })
    },
    changePage (page) {
      this.currentPage = page
      this.getNewsItemList()
    },
    goDetail (newsId) {
      if (this.newsTypeId === '01') {
        this.$router.push({ path: '/news/newsDetail/' + newsId })
      } else {
        // window.location.href = this.newsItem.outChain
        window.open(this.newsItem.outChain, '_blank')
      }
    }
  },
  components: {
    newsTitle,
    beginsHere,
    newsItemDetail
  }
}
</script>

<style scoped>
.news-banner {
  position: relative;
  width: 1225px;
  height: 300px;
  margin-top: 30px;
  overflow: hidden;
  cursor: pointer;
}

.news-banner img {
  width: 100%;
  height: 100%;
}

.topic-text {
  position: absolute;
  top: 50%;
  left: 0;
  height: 48px;
  line-height: 48px;
  margin-top: -24px;
  text-align: center;
  font-size: 48px;
  font-weight: 700;
  color: #ffffff;
}

.news-page {
  margin-top: 45px;
  text-align: right;
}

.news-begin-box {
  margin-top: 140px;
}
</style>
