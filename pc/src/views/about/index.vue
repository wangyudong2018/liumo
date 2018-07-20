<template>
  <div class="protocol-wrapper">
    <div class="protocol-banner">
      <img src="./imgs/join_us_banner.png" alt="了解六漠">
      <div class="protocol-slogan">
        <p class="slogan-one">了解六漠</p>
        <p class="slogan-two">看六漠科技的服务理念</p>
      </div>
    </div>
    <div class="layout-width layout-center about-content">
      <div class="header">
        <spread-head :spreadHead="companySpreadHead"></spread-head>
      </div>
      <div class="company-section clearfix">
        <div class="img-box fl">
          <img src="./imgs/join_us_banner.png" alt="">
        </div>
        <div class="content-box fr">
          <p>微贷网于2011年7月8日上线运营。作为国内“互联网+汽车金融”的先行者，微贷网打造了车贷垂直市场的标准发展模式，为中小企业与个人用户提供一站式金融服务。</p>
          <p>微贷网作为中国互联网金融协会理事单位、杭州市互联网金融协会执行会长单位、浙江互联网金融联合副会长单位、车贷联盟会长单位，一直致力于推动行业自律建设贡献自身力量。微贷网作为中国互联网金融协会理事单位、杭州市互联网金融协会执行会长单位、浙江互联网金融联合副会长单位、车贷联盟会长单位，一直致力于推动行业自律建设贡献自身力量。</p>
        </div>
      </div>
      <div class="header">
        <spread-head :spreadHead="teamSpreadHead"></spread-head>
      </div>
      <div class="team-section clearfix">
        <div class="team-content-box fl">
          <div class="team-title-box">
            <h2 class="team-title">六漠团队</h2>
          </div>
          <div class="team-content">
            <p>微贷网于2011年7月8日上线运营。作为国内“互联网+汽车金融”的先行者，微贷网打造了车贷垂直市场的标准发展模式，为中小企业与个人用户提供一站式金融服务。</p>
            <p>微贷网于2011年7月8日上线运营。作为国内“互联网+汽车金融”的先行者，微贷网打造了车贷垂直市场的标准发展模式，为中小企业与个人用户提供一站式金融服务。</p>
            <p>微贷网于2011年7月8日上线运营。作为国内“互联网+汽车金融”的先行者，微贷网打造了车贷垂直市场的标准发展模式，为中小企业与个人用户提供一站式金融服务。</p>
          </div>
        </div>
        <div class="team-img-box fl">
          <img src="./imgs/join_us_banner.png" alt="团队介绍">
          <div class="team-intro">
            <h3>服务为您</h3>
            <p>为客户提供丰富的服务经验和专业知识；</p>
            <p>确保客户能够享受高质、高效以及个性化的服务资源。</p>
          </div>
        </div>
      </div>
      <div class="header">
        <spread-head :spreadHead="allSpreadHead"></spread-head>
      </div>
      <div class="all-section clearfix">
        <div class="all-left fl">
          <div class="all-left-t">
            <div class="liumo-icon">
              <img src="./imgs/liumo_logo.png">
            </div>
            <div class="liumo-slogan">
              <img src="./imgs/liumo_slogan.png">
            </div>
          </div>
          <div class="all-description">以“每个人都可以定制自己的潮牌”为理念，为用户提供专属、个性、潮酷的定制服务，鼓励年轻人做自己生活的设计师、做自己创意的主理人。黑科技、大数据、云计算和人工智能技术，打造个性化的潮流定制电商平台。</div>
          <div class="all-download clearfix">
            <div class="download-btn-wrapper">
              <div class="and-btn">
                <img src="./imgs/and_download_btn.png">
              </div>
              <div class="ios-btn">
                <img src="./imgs/ios_download_btn.png">
              </div>
            </div>
            <div class="download-qrcode">
              <img src="./imgs/download_code.png">
            </div>
          </div>
        </div>
        <div class="all-right fl">
          <img src="./imgs/mobile.png">
        </div>
      </div>
    </div>
    <div class="step-wrapper">
      <div class="step-content">
        <ul>
          <li>
            <div class="step-icon">
              <img src="./imgs/zixun.png" alt="1.在线咨询(APP、电话)">
            </div>
            <p class="step-des">1.在线咨询</p>
            <p class="step-des">通过APP、电话咨询六漠科技</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/mendain.png" alt="2.到店申请">
            </div>
            <p class="step-des">2.到店申请</p>
            <p class="step-des">可到北京上海门面店申请服务</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/yinhang.png" alt="3.匹配最优银行">
            </div>
            <p class="step-des">3.匹配最优银行</p>
            <p class="step-des">六漠为您提供</p>
            <span class="step-transition"></span>
          </li>
          <li>
            <div class="step-icon">
              <img src="./imgs/fangkuan.png" alt="4.银行审批放款">
            </div>
            <p class="step-des">4.银行审批放款</p>
            <p class="step-des">通过APP、电话咨询六漠科技</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { LOAD_FILE } from '@/api/api'

import spreadHead from '@/components/spreadHead/spreadHead'
export default {
  data () {
    return {
      loadBannersImg: LOAD_FILE,
      companySpreadHead: {
        en: 'Company profile',
        zh: '公司简介'
      },
      teamSpreadHead: {
        en: 'Team Introduction',
        zh: '团队介绍'
      },
      allSpreadHead: {
        en: 'www.loimore.com',
        zh: '六漠科技'
      }
    }
  },
  components: {
    spreadHead
  }
}
</script>

<style scoped>
  .protocol-banner {
    position: relative;
    width: 100%;
    height: 500px;
    overflow: hidden;
  }

  .protocol-banner img {
    width: 100%;
    height: 100%;
  }

  .protocol-slogan {
    position: absolute;
    display: block;
    top: 50%;
    right: 0;
    width: 100%;
    height: 168px;
    margin-top: -84px;
    text-align: center;
    color: #ffffff;
  }

  .slogan-one {
    height: 80px;
    line-height: 80px;
    margin-bottom: 60px;
    font-size: 80px;
    font-weight: 700;
  }

  .slogan-two {
    height: 28px;
    line-height: 28px;
    font-size: 28px;
  }

  .header {
    margin-top: 120px;
    margin-bottom: 80px;
  }

  .company-section {
    padding-bottom: 40px;
  }

  .img-box {
    width: 370px;
    height: 230px;
    margin-top: 15px;
    overflow: hidden;
  }

  .img-box img {
    width: 100%;
    height: 100%;
  }

  .content-box {
    width: 785px;
    margin-left: 70px;
  }

  .content-box p {
    line-height: 42px;
    font-size: 20px;
  }

  .team-content-box {
    width: 425px;
    height: 550px;
  }

  .team-title {
    display: inline-block;
    padding: 30px 0;
    border-bottom: 1px solid #2d86e7;
    font-size: 45px;
    font-weight: 700;
    color: #2d86e7;
  }

  .team-content {
    margin-top: 43px;
  }

  .team-content p {
    line-height: 30px;
    text-align: justify;
    font-size: 16px;
  }

  .team-img-box {
    position: relative;
    width: 730px;
    height: 550px;
    margin-left: 70px;
    overflow: hidden;
  }

  .team-img-box img {
    width: 100%;
    height: 100%;
  }

  .team-intro {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 260px;
    height: 550px;
    padding: 120px 60px;
    background-color: #2d86e7;
    background: rgba(45, 134, 231, .6);
    color: #ffffff;
  }

  .team-intro h3 {
    height: 28px;
    line-height: 28px;
    margin-bottom: 40px;
    font-size: 28px;
  }

  .team-intro p {
    line-height: 32px;
    font-size: 18px;
  }

  .all-section {
    padding-bottom: 50px;
  }

  .all-left {
    width: 733px;
    height: 686px;
    margin-right: 70px;
  }

  .all-left-t {
    margin-bottom: 39px;
  }

  .liumo-icon, .liumo-slogan {
    display: inline-block;
    overflow: hidden;
  }

  .liumo-icon {
    width: 112px;
    height: 112px;
  }

  .liumo-slogan {
    width: 479px;
    height: 112px;
    padding-top: 17px;
    padding-left: 20px;
  }

  .liumo-icon img, .liumo-slogan img {
    width: 100%;
    height: 100%;
  }

  .all-description {
    line-height: 42px;
    text-align: justify;
    font-size: 20px;
  }

  .all-download {
    margin-top: 100px;
  }

  .download-btn-wrapper {
    float: left;
    width: 330px;
    height: 152px;
    margin-right: 70px;
  }

  .and-btn, .ios-btn {
    width: 330px;
    height: 58px;
    overflow: hidden;
  }

  .and-btn {
    margin-bottom: 36px;
  }

  .download-qrcode {
    float: left;
    width: 152px;
    height: 152px;
    overflow: hidden;
  }

  .and-btn img, .ios-btn img, .download-qrcode img {
    width: 100%;
    height: 100%;
  }

  .all-right {
    width: 422px;
    height: 686px;
    overflow: hidden;
  }

  .all-right img {
    width: 100%;
    height: 100%;
  }

  .step-wrapper {
    width: 100%;
    height: 396px;
    background: url("./imgs/beijing-dibu.png") no-repeat;
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
      background: url("./imgs/xuxian.png") no-repeat;
  }

  .step-icon {
    width: 140px;
    height: 140px;
    margin: 0 auto 33px;
  }

  .step-icon img {
      width: 100%;
      height: auto;
  }

  .step-des {
    line-height: 34px;
    text-align: center;
    font-size: 20px;
    color: #ffffff;
  }
</style>
