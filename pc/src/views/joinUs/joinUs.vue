<template>
  <div class="join-us-wrapper">
    <div class="join-us-banner">
      <img src="./imgs/join_us_banner.png" alt="诚招英才">
      <div class="join-us-slogan">
        <h2 class="ju-slogan-title">诚征英才</h2>
        <p class="ju-slogan-text">加入六漠科技，谱写你的故事</p>
      </div>
    </div>
    <div class="join-us-content">
      <div class="layout-width layout-center join-us-box clearfix">
        <div class="content-left fl">
          <button
            v-for="(tab, index) in tabs"
            :key="tab.Alias"
            :class="['tab-btn',
              'tab-btn-0' + (index + 1),
              {'tab-active': currentTab === tab.Alias}]"
            @click="currentTab = tab.Alias"
          ><i class="iconfont" :class="index === 0 ? 'icon-xuzhi' : 'icon-zhiwei'"></i>{{tab.tabName}}</button>
        </div>
        <div class="content-right fl">
          <component :is="currentTabComponent"></component>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TabNotice from './tabNotice'
import TabStation from './tabStation'

export default {
  data () {
    return {
      tabs: [
        {
          Alias: 'notice',
          tabName: '招聘须知'
        },
        {
          Alias: 'station',
          tabName: '招聘岗位'
        }
      ],
      currentTab: 'notice'
    }
  },
  components: {
    TabNotice,
    TabStation
  },
  computed: {
    currentTabComponent () {
      return 'tab-' + this.currentTab.toLowerCase()
    }
  }
}
</script>

<style scoped>
  .join-us-wrapper {
    width: 100%;
  }

  .join-us-banner {
    position: relative;
    width: 100%;
    height: 500px;
    overflow: hidden;
  }

  .join-us-banner img {
    width: 100%;
    height: 500px;
  }

  .join-us-slogan {
    position: absolute;
    top: 50%;
    left: 0;
    width: 100%;
    height: 168px;
    margin-top: -110px;
    text-align: center;
    color: #ffffff;
  }

  .ju-slogan-title{
    height: 80px;
    line-height: 80px;
    margin-bottom: 60px;
    font-size: 80px;
    font-weight: 700;
  }

  .ju-slogan-text {
    height: 28px;
    line-height: 28px;
    font-size: 28px;
  }

  .join-us-box {
    padding: 60px 0;
  }

  .content-left {
    width: 280px;
    margin-right: 100px;
  }

  .tab-btn {
    width: 280px;
    height: 80px;
    line-height: 80px;
    outline: none;
    border: none;
    border-bottom: 1px solid #0a67bd;
    background-color: #ffffff;
    font-size: 28px;
    color: #0a67bd;
    cursor: pointer;
  }

  .tab-btn .iconfont {
    display: inline-block;
    width: 30px;
    height: 80px;
    line-height: 80px;
    vertical-align: top;
    margin-right: 30px;
    font-size: 28px;
  }

  .tab-active {
    background-color: #0a67bd;
    color: #ffffff;
  }

  .content-right {
    width: 845px;
  }
</style>
