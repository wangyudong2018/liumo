<template>
  <div class="notice-wrapper">
    <div class="notice-item"
      v-for="(notice, i) in noticeList"
      :key="i"
    >
      <h2 class="notice-title">{{notice.title}}</h2>
      <div class="notice-content">
        <p class="notice-text"
          v-for="(text, index) in notice.desList"
          :key="index"
        >
          {{(index + 1) + '. ' + text}}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      noticeList: [
        {
          title: '01/面向群体及岗位',
          desList: [
            '面向群体：社会相关职业从业人员，应届生相关专业学生；',
            '开放岗位：技术研发大类，客户服务岗位，欢迎有意向的同行业人员和应届生同学投递简历；'
          ]
        },
        {
          title: '01/简历投递渠道',
          desList: [
            'HR邮箱hr@yiyuntouzi.cn',
            '关注“六漠科技招聘”公众号'
          ]
        },
        {
          title: '01/关于测评',
          desList: [
            '测评形式：电话测评，简历投递后，通过电话基本问答后，方可决定是否进入面试阶段；',
            '测评要求：您需要保证个人信息真实有效，并确保电话问答由个人独立完成，如发现有任何作弊欺诈行为，我们将取消面试资格；'
          ]
        },
        {
          title: '04/关于面试',
          desList: [
            '技术研发面试流程：内推/审核---测评---项目经理面试---HR经理面试---分公司经理面试---发放录用意向书',
            '客户服务面试流程：内推/审核---测评---项目经理面试---HR经理面试---分公司经理面试---发放录用意向书'
          ]
        },
        {
          title: '05/关于offer',
          desList: [
            '我们会在第一时间向确认录用意向的同仁和同学发放《录用意向书》，另应届生招聘到校招结束为止；',
            '我们会非常欢迎乐观，发现世间小爱的朋友们加入我们这个大家庭，让我们共同创造价值、做有意义的事情；'
          ]
        }
      ]
    }
  }
}
</script>

<style scoped>
  .notice-title {
    height: 40px;
    line-height: 40px;
    font-size: 40px;
    font-weight: 700;
    color: #0a67bd;
  }

  .notice-content {
    padding: 40px 0 50px;
  }

  .notice-text {
    line-height: 38px;
    font-size: 18px;
    color: #000000;
  }
</style>
