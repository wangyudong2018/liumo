<template>
  <div class="news-detail-wrapper">
    <div class="layout-width layout-center detail-wrapper">
      <div class="detail-title-box">
        <h2 class="detail-title">{{newsDetail.title}}</h2>
        <p class="detail-label">
          <span class="label">{{newsDetail.original}}</span>
          <span class="label">{{''}}</span>
          <span class="label">{{newsDetail.releaseDate}}</span>
        </p>
      </div>
      <div class="detail-content" v-html="newsDetail.content"></div>
    </div>
    <div class="begins-wrapper">
      <begins-here></begins-here>
    </div>
  </div>
</template>

<script>
import api from '@/api/api'
import beginsHere from '@/components/beginsHere/beginsHere'
export default {
  data () {
    return {
      newsDetail: {}
    }
  },
  created () {
    this.$http.get(api.release(this.$route.params.newsId), {}, (res) => {
      this.newsDetail = Object.assign({}, this.newsDetail, res.data)
    })
  },
  components: {
    beginsHere
  }
}
</script>

<style scoped>
  .detail-wrapper {
    margin-top:70px;
  }

  .detail-title {
    line-height: 70px;
    font-size: 50px;
    font-weight: 700;
    color: #000000;
  }

  .detail-label {
    padding: 26px 0 30px;
    font-size: 22px;
    color: #666666;
  }

  .label {
    display: inline-block;
    height: 22px;
    line-height: 22px;
    padding-right: 120px;
  }

  .detail-img-box {
    width: 100%;
    height: 600px;
    overflow: hidden;
  }

  .detail-img-box img {
    width: 100%;
    height: 100%;
  }

  .detail-content {
    margin: 20px 0 240px;
  }

  .detail-content-pragraph {
    line-height: 38px;
    font-size: 18px;
  }
</style>

<style>
  .detail-content p {
    line-height: 28px;
    font-size: 16px!important;
  }
</style>
