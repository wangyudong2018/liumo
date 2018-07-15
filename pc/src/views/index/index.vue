<template>
  <div class="container">
    <div class="banner-wrapper">
      <div
        v-for="banner of bannerList"
        :key="banner.fileId"
      >
        <img :src="loadBannersImg + banner.fileId" alt="六漠科技">
      </div>
    </div>
    <div class="contranct-wrapper">
      <div class="contranct-title">
        <spread-head :spreadHead="constract.spreadHead"></spread-head>
      </div>
      <div class="contranct-content">
        <div class="contrant-title">
          <news-title :newsTitle="constract.newsTitle"></news-title>
        </div>
        <div class="contranct-btn-box">
          <button class="contranct-btn">立即咨询</button>
        </div>
      </div>
    </div>
    <div class="specialty-wrapper">
      <div class="specialty-title">
        <spread-head :spreadHead="sprcialty.spreadHead"></spread-head>
      </div>
      <div class="specialty-box clearfix">
        <Specialty
          v-for="specialtyItem of sprcialty.specialtyList"
          :key="specialtyItem.specialtyId"
          :specialtyItem="specialtyItem"
        ></Specialty>
      </div>
    </div>
    <div class="bank-show-wrapper">
      <div class="bank-show-title">
        <spread-head :spreadHead="bankShow.spreadHead"></spread-head>
      </div>
      <div class="bank-show-box">
        <bank-show
          v-for="(bankShow, index) in bankShow.bankShowList"
          :key="bankShow.bankShowId"
          :bankShow="bankShow"
          :index="index"
        ></bank-show>
      </div>
    </div>
    <div class="begins-wrapper">
      <div class="begins-title">
        <spread-head :spreadHead="begins.spreadHead"></spread-head>
      </div>
      <div class="begins-box">
        <begins-here></begins-here>
      </div>
    </div>
  </div>
</template>

<script>
import api, { LOAD_FILE } from '../../api/api'

import spreadHead from '@/components/spreadHead/spreadHead'
import newsTitle from '@/components/newsTitle/newsTitle'
import Specialty from '@/components/specialty/specialty'
import bankShow from '@/components/bankShow/bankShow'
import beginsHere from '@/components/beginsHere/beginsHere'
export default {
  data () {
    return {
      loadBannersImg: LOAD_FILE,
      bannerList: [],
      constract: {
        spreadHead: {
          en: 'Service Runs On Technology',
          zh: '服务是第一生产力',
          color: '#0a67bd',
          fontSize: '64'
        },
        newsTitle: {
          splitWidth: 200,
          splitHeight: 6,
          zhFontSize: 54,
          enFontSize: 20,
          newsTitleZh: '银行服务',
          newsTitleEn: '安全·放心·用心·安心'
        }
      },
      sprcialty: {
        spreadHead: {
          en: 'Walk into liumore',
          zh: '走进六漠'
        },
        specialtyList: [
          {
            specialtyId: '01',
            specialtyImgUrl: './imgs/specialty01.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '专业',
              newsTitleEn: '专业培训，持证上岗'
            }
          },
          {
            specialtyId: '02',
            specialtyImgUrl: './imgs/specialty02.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '安全',
              newsTitleEn: '一站式服务，全流程帮您搞定'
            }
          },
          {
            specialtyId: '03',
            specialtyImgUrl: './imgs/specialty03.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '诚信',
              newsTitleEn: '一站式服务，全流程帮您搞定'
            }
          },
          {
            specialtyId: '04',
            specialtyImgUrl: './imgs/specialty04.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '省心',
              newsTitleEn: '一站式服务，全流程帮您搞定'
            }
          },
          {
            specialtyId: '05',
            specialtyImgUrl: './imgs/specialty05.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '舒心',
              newsTitleEn: '体验非凡，全流程专车接送'
            }
          },
          {
            specialtyId: '06',
            specialtyImgUrl: './imgs/specialty06.png',
            newsTitle: {
              splitWidth: 92,
              splitHeight: 2,
              zhFontSize: 32,
              enFontSize: 20,
              newsTitleZh: '放心',
              newsTitleEn: '精准服务，匹配最优银行'
            }
          }
        ]
      },
      bankShow: {
        spreadHead: {
          en: 'Waiting For you',
          zh: '期待您的到来'
        },
        bankShowList: [
          {
            bankShowId: '01',
            bankShowName: '上海总部',
            bankShowAddress: '上海市凯旋北路1016号长城大厦701',
            bankShowTels: [
              '021-6038-6878',
              '021-6038-6878'
            ]
          },
          {
            bankShowId: '02',
            bankShowName: '北京公司',
            bankShowAddress: '北京市朝阳区第三职业大厦A座2508',
            bankShowTels: [
              '010-6038-6878'
            ]
          },
          {
            bankShowId: '03',
            bankShowName: '北京通州门店',
            bankShowAddress: '北京市朝阳区第三职业大厦A座2508',
            bankShowTels: [
              '010-6038-6878'
            ]
          }
        ]
      },
      begins: {
        spreadHead: {
          en: 'Here We Go',
          zh: '从这里开始'
        }
      }
    }
  },
  created () {
    this.$http.get(api.bannerList(), {channel: 'pc', page: 'index'}, (res) => {
      this.bannerList = res.data
    })
  },
  components: {
    spreadHead,
    newsTitle,
    Specialty,
    bankShow,
    beginsHere
  }
}
</script>

<style scoped>
.banner-wrapper {
  width: 100%;
  height: 700px;
  overflow: hidden;
}

.banner-wrapper img {
  width: 100%;
  height: 700px;
}

.contranct-wrapper {
  margin: 100px 0 140px;
}

.contranct-content {
  position: relative;
  width: 1205px;
  height: 500px;
  margin: 0 auto;
  padding-right: 130px;
  background: url("./imgs/contranct.png") no-repeat;
  background-position: 90px bottom;
}

.contrant-title {
  position: absolute;
  top: 130px;
  right: 70px;
}

.contranct-btn-box {
  position: absolute;
  right: 130px;
  bottom: 50px;
}

.contranct-btn {
  width: 190px;
  height: 60px;
  border: 1px solid #2d86e7;
  border-radius: 5px;
  font-size: 28px;
  background: #ffffff;
  color: #2d86e7;
  cursor: pointer;
}

.specialty-wrapper, .bank-show-wrapper {
  width: 1205px;
  margin: 0 auto;
}

.specialty-box, .bank-show-box, .begins-box {
  margin-top: 120px;
}
</style>
