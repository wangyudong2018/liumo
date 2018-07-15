<template>
  <div>
    <div class="content-wrapper">
      <div class="banner-wrapper">
        <div
          v-for="banner of bannerList"
          :key="banner.fileId"
        >
          <img :src="loadBannersImg + banner.fileId" alt="六漠科技">
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
            v-for="(productType, index) of productTypeList"
            :key="productType.prdType"
            :productType="productType"
            :index="index"
            @maskEvent="showMask"
          ></propduct-type>
        </div>
      </div>
    </div>
    <div class="mask-wrapper" v-show="isShowMask">
      <mask-wrapper>
        <div class="mask-content">
          <div class="mask-ct-top clearfix">
            <div class="mask-left fl">
              <div class="mask-logo">
                <img src="./imgs/mask_logo.png">
              </div>
              <div class="mask-title">
                <img src="./imgs/mask_title.png" alt="六漠科技">
              </div>
            </div>
            <div class="mask-right fl">
              <img src="./imgs/mask_slogan.png" alt="选银行，就走六漠科技">
            </div>
            <div class="mask-iphone">
              <img src="./imgs/mask_iphone.png">
            </div>
          </div>
          <div class="mask-ct-bottom">
            <div class="mask-code">
              <img src="./imgs/begins_code.png" alt="扫码下载六漠科技APP">
            </div>
            <p class="mask-des">扫码下载六漠科技APP,即刻申请流程</p>
          </div>
          <div class="close-mask" @click="hideMask"></div>
        </div>
      </mask-wrapper>
    </div>
  </div>
</template>

<script>
import api, { LOAD_FILE } from '../../api/api'
import propductType from '../../components/product/productType'
import Mask from '../../components/mask/mask'
export default {
  data () {
    return {
      isShowMask: false,
      loadBannersImg: LOAD_FILE,
      bannerList: [],
      productTypeList: []
    }
  },
  created () {
    this.$http.get(api.bannerList(), {channel: 'pc', page: 'product'}, (res) => {
      this.bannerList = res.data
    })
    this.$http.get(api.productList(), {}, (res) => {
      this.productTypeList = res.data
    })
  },
  components: {
    'propduct-type': propductType,
    'mask-wrapper': Mask
  },
  methods: {
    showMask () {
      this.isShowMask = true
    },
    hideMask () {
      this.isShowMask = false
    }
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

.mask-content {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  width: 860px;
  height: 700px;
  margin: auto;
  z-index: 101;
  background-color: #ffffff;
}

.mask-ct-top {
  position: relative;
  height: 160px;
  padding: 28px 0 28px 70px;
  background: url("./imgs/mask_ct_bg.png") no-repeat;
  background-size: 100% 100%;
}

.mask-logo, .mask-title {
  width: 80px;
  height: 80px;
  overflow: hidden;
}

.mask-title {
  height: 19px;
  margin-top: 5px;
}

.mask-right {
  width: 710px;
  height: 104px;
  padding: 22px 374px 7px 36px;
}

.mask-iphone {
  position: absolute;
  right: 60px;
  bottom: 0px;
  width: 280px;
  height: 247px;
  overflow: hidden;
}

.mask-logo img, .mask-title img, .mask-right img, .mask-iphone img {
  width: 100%;
  height: auto;
}

.mask-ct-bottom {
  height: 540px;
  padding-bottom: 70px;
}

.mask-code {
  width: 320px;
  height: 320px;
  margin: 54px auto;
  overflow: hidden;
}

.mask-code img {
  width: 100%;
  height: auto;
}

.mask-des {
  height: 32px;
  line-height: 32px;
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  color: #000000;
}

.close-mask {
  position: absolute;
  top: -70px;
  right: -100px;
  width: 60px;
  height: 60px;
  background: url("./imgs/close_mask.png") no-repeat;
  background-size: contain;
  cursor: pointer;
}
</style>
