<template>
  <div class="content-wrapper">
    <div class="banner-wrapper">
      <div
        v-for="banner of bannerList"
        :key="banner.id"
      >
        <img :src="loadBannersImg + banner.id" alt="六漠科技">
      </div>
    </div>
    <div class="step-wrapper">
      <div class="step-content">
        <ul>
          <li>
            <div class="step-icon">
              <img src="./imgs/icon_01.png" alt="1.在线咨询(APP、电话)">
            </div>
            <p class="step-des">1.在线咨询(APP、电话)</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/icon_02.png" alt="2.到店申请">
            </div>
            <p class="step-des">2.到店申请</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/icon_03.png" alt="3.匹配最优银行">
            </div>
            <p class="step-des">3.匹配最优银行</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/icon_04.png" alt="4.银行审批放款">
            </div>
            <p class="step-des">4.银行审批放款</p>
          </li>
        </ul>
      </div>
    </div>
    <div class="pro-wrapper">
      <div class="pro-content">
        <propduct-type
          v-for="productType of productTypeList"
          :key="productType.loanType"
          :productType="productType"
        ></propduct-type>
      </div>
    </div>
  </div>
</template>

<script>
import api, { LOAD_BANNERS_IMG } from '../../api/api'
import propductType from '../../components/product/productType'
export default {
  data () {
    return {
      loadBannersImg: LOAD_BANNERS_IMG,
      bannerList: [],
      productTypeList: []
    }
  },
  created () {
    this.$http.get(api.bannerList(), {lmType: '02'}, (res) => {
      this.bannerList = res.data
    })
  },
  components: {
    propductType
  },
  mounted () {
    this.$http.get(api.productList(), {}, (res) => {
      this.productTypeList = res.data
    })
  }
}
</script>

<style scoped>
.banner-wrapper {
  width: 100%;
  height: 500px;
  overflow: hidden;
}

.banner-wrapper img {
  width: 100%;
  height: 500px;
}

.step-wrapper {
  width: 100%;
  height: 396px;
  background: url("./imgs/index_part02_bg.png") no-repeat;
  background-size: 100% 100%;
  overflow: hidden;
}

.step-content {
  width: 1205px;
  margin: 0 auto;
  padding: 100px 0;
}

.step-content li {
  float: left;
  position: relative;
  width: 301px;
}

.step-transition {
    position: absolute;
    top: 64px;
    right: -48px;
    width: 100px;
    height: 12px;
    background: url("./imgs/icon_05.png") no-repeat;
}

.step-icon {
  width: 140px;
  height: 140px;
  margin: 0 auto;
}

.step-icon img {
    width: 100%;
    height: auto;
}

.step-des {
  height: 26px;
  line-height: 26px;
  margin-top: 30px;
  text-align: center;
  font-size: 26px;
  color: #ffffff;
}

.pro-wrapper {
  width: 100%;
}

.pro-content {
    width: 1205px;
    margin: 0 auto;
    padding-bottom: 140px;
}
</style>
