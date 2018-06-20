<template>
  <div class="content-wrapper">
    <div class="banner-wrapper"></div>
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
import propductType from '../../components/product/productType'
export default {
  data () {
    return {
      productTypeList: [
        {
          loanType: '01',
          loanTypeName: '银行抵押贷款',
          serviceRate: '2',
          productIntro: '提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。',
          productDes: '名下有可抵押房产的客户。'
        },
        {
          loanType: '02',
          loanTypeName: '银行信用贷款',
          serviceRate: '2',
          productIntro: '提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。',
          productDes: '名下有可抵押房产的客户。'
        },
        {
          loanType: '03',
          loanTypeName: '银行按揭贷款',
          serviceRate: '2',
          productIntro: '提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。',
          productDes: '名下有可抵押房产的客户。'
        }
      ]
    }
  },
  components: {
    propductType
  }
}
</script>

<style>
.banner-wrapper {
  width: 100%;
  height: 500px;
  background: url("./imgs/index_part01_bg.png") no-repeat;
}

.step-wrapper {
  width: 100%;
  height: 396px;
  background: url("./imgs/index_part02_bg.png") no-repeat;
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
