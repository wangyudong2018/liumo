<template>
  <div class="station-wrapper">
    <div class="select-wrapper clearfix">
      <div
        class="select-item"
        v-for="(selectItem, m) in selectConditions"
        :key="selectItem.conditionId"
      >
        <div class="item-label-box fl">
          <h2 class="item-label">{{selectItem.conditionName}}:</h2>
        </div>
        <ul class="item-conditions fl clearfix">
          <li
            class="item-condition fl"
            v-for="(condition, i) in selectItem.conditions"
            :class="{'item-active': i === selectedCondition[m]}"
            :key="condition.code"
            @click="sortConditions(m, i, condition.code)"
          >{{condition.name}}</li>
        </ul>
      </div>
    </div>
    <div class="station-box">
      <div class="station-header">
        <p class="label">职位类型</p>
        <p class="label">职位分类</p>
        <p class="label">工作地点</p>
        <p class="label">发布时间</p>
      </div>
      <div v-if="recruitList.length > 0">
        <div
          class="item-content-wrapper"
          v-for="(item, index) in recruitList"
          :key="index"
        >
          <div class="item-header" @click="transitionFn(index)">
            <p class="label">{{item.jobTitle}}</p>
            <p class="label">{{getCategoryName(item.category)}}</p>
            <p class="label">{{getCityName(item.workplace)}}</p>
            <p class="label">{{item.relDate}}</p>
            <i class="arrow" :class="{'active-arrow': index === previousIndex}"></i>
          </div>
          <div class="content-wrapper"
            :ref="'stationItem' + index"
            :style="{height: wrapperHeight[index] + 'px'}"
          >
            <div class="item-content">
              <div class="content-row">
                <p class="job-nature">工作性质：{{item.jobNature}}</p>
                <p class="salary-range">薪资范围：{{item.salaryRange}}</p>
                <p class="hiring">招聘人数：{{item.hiring}}</p>
              </div>
              <div class="content-row clearfix">
                <div class="station-label fl">工作职责：</div>
                <div class="station-des fl">
                  <p v-html="item.jobDuty"></p>
                </div>
              </div>
              <div class="content-row clearfix">
                <div class="station-label fl">任职资格：</div>
                <div class="station-des fl">
                  <p v-html="item.jobQuality"></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="no-data">
        抱歉，暂无数据！
      </div>
    </div>
    <div class="tab-page" v-show="count !== 0">
      <Page
        size="small"
        :total="count"
        :page-size="pageSize"
        @on-change="changePage"
      ></Page>
    </div>
  </div>
</template>

<script>
import api from '@/api/api'
export default {
  data () {
    return {
      count: 0, // 总记录数
      pageSize: 5, // 每页显示7条数据
      currentPage: 1, // 当前页码
      selectConditions: [
        {
          conditionId: '01',
          conditionName: '工作城市',
          conditions: [
            {
              code: '110000',
              name: '北京'
            },
            {
              code: '310000',
              name: '上海'
            }
          ]
        },
        {
          conditionId: '02',
          conditionName: '招聘类型',
          conditions: [
            {
              code: '01',
              name: '社会招聘'
            },
            {
              code: '02',
              name: '校园招聘'
            }
          ]
        },
        {
          conditionId: '03',
          conditionName: '工作类型',
          conditions: [
            {
              code: '',
              name: '全部'
            },
            {
              code: '01',
              name: '研发类'
            },
            {
              code: '02',
              name: '业务类'
            },
            {
              code: '03',
              name: '人力资源类'
            },
            {
              code: '04',
              name: '行政类'
            },
            {
              code: '05',
              name: '财务类'
            },
            {
              code: '06',
              name: '法务类'
            },
            {
              code: '07',
              name: '大数据类'
            }
          ]
        }
      ],
      recruitList: [], // 招聘记录列表
      wrapperHeight: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0], // 初始化每条招聘记录的初始高度
      previousIndex: 0, // 初始化上一条招聘信息的索引
      sortParams: { // 招聘信息过滤条件
        workplace: '110000', // 地区码
        recType: '01', // 招聘类型
        category: '', // 职位类型
        offset: '0', // 偏移量
        limit: '5' // 最大查询记录数
      },
      selectedCondition: [0, 0, 0] // 默认选中的过滤条件
    }
  },
  created () {
    // this.$http.get(api.recruitList(), {offset: '0', limit: '5'}, (res) => {
    //   this.recruitList = res.data.rows
    // })
  },
  mounted () {
    this.getStationList()
  },
  methods: {
    transitionFn (index) {
      let refDom = 'stationItem' + index // 获取当前招聘记录的容器元素
      let timer = setInterval(() => {
        if (this.$refs[refDom]) { // 判断当前的当前记录的容器元素是否挂在完毕
          let contentWrapper = this.$refs[refDom][0] // 获取当前招聘记录的容器元素
          this.$set(this.wrapperHeight, this.previousIndex, 0) // 将上一条展示的招聘记录隐藏
          this.previousIndex = index // 重置上一条招聘记录的索引为当前索引
          this.$set(this.wrapperHeight, index, contentWrapper.firstElementChild.clientHeight)
          clearInterval(timer)
        }
      }, 300)
    },
    sortConditions (m, i, code) {
      this.$set(this.selectedCondition, m, i)
      if (m === 0) {
        this.sortParams = Object.assign({}, this.sortParams, {workplace: code})
      } else if (m === 1) {
        this.sortParams = Object.assign({}, this.sortParams, {recType: code})
      } else if (m === 2) {
        this.sortParams = Object.assign({}, this.sortParams, {category: code})
      }
      this.getStationList()
    },
    getStationList () {
      let offset = (this.currentPage - 1) * this.pageSize // 计算偏移量
      this.sortParams = Object.assign({}, this.sortParams, {offset: offset})
      this.$http.get(api.recruitList(), this.sortParams, (res) => {
        this.recruitList = res.data.rows
        this.count = res.data.total
        if (res.data.rows && res.data.rows.length > 0) {
          this.transitionFn(0)
        }
      })
    },
    changePage (page) {
      this.currentPage = page
      this.getStationList()
    },
    getCityName (value) {
      if (!value) return ''
      let cityName = ''
      const cityDic = this.selectConditions[0].conditions
      for (let city of cityDic) {
        if (city.code === value) {
          cityName = city.name
          break
        }
      }
      return cityName
    },
    getCategoryName (value) {
      if (!value) return ''
      let categoryName = ''
      const categoryDic = this.selectConditions[2].conditions
      for (let category of categoryDic) {
        if (category.code === value) {
          categoryName = category.name
          break
        }
      }
      return categoryName
    }
  }
}
</script>

<style scoped>
  .select-item {
    padding-bottom: 10px;
  }

  .item-label-box {
    width: 175px;
    padding: 0 6px 0 60px;
  }

  .item-label {
    height: 38px;
    line-height: 38px;
    font-size: 18px;
    font-weight: 700;
    color: #000000;
  }

  .item-conditions {
    width: 670px;
  }

  .item-condition {
    line-height: 38px;
    padding: 0 24px;
    background: url("./imgs/split.png") no-repeat;
    background-position: 100% 10px;
    font-size: 18px;
    cursor: pointer;
  }

  .item-active {
    color: #0a67bd;
  }

  .item-condition:last-child {
    background-image: none;
  }

  .station-header {
    margin-top: 20px;
    padding-left: 60px;
    background-color: #f2f2f2;
  }

  .no-data {
    height: 60px;
    line-height: 60px;
    border: 1px solid #f2f2f2;
    text-align: center;
    font-size: 28px;
    color: #666666;
  }

  .item-header {
    position: relative;
    padding-left: 60px;
    background-color: #ffffff;
    cursor: pointer;
  }

  .arrow {
    position: absolute;
    top: 0;
    right: 40px;
    height: 40px;
    width: 40px;
    background: url("./imgs/r_arrow.png") no-repeat;
    background-position: 13px 13px;
    background-size: 14px 14px;
    opacity: .5;
  }

  .active-arrow {
    background: url("./imgs/b_arrow.png") no-repeat;
    background-position: 13px 13px;
    background-size: 14px 14px;
  }

  .current-arrow {
    background: url("./imgs/r_arrow.png") no-repeat;
    background-position: 13px 13px;
    background-size: 14px 14px;
  }

  .station-header .label, .item-header .label {
    display: inline-block;
    width: 170px;
    height: 40px;
    line-height: 40px;
    font-size: 18px;
    color: #666666;
  }

  .station-header .label:first-child, .item-header .label:first-child {
    width: 220px;
  }

  .item-content {
    padding: 30px 60px;
    background-color: #f2f2f2;
  }

  .content-row {
    padding-bottom: 24px;
    font-size: 0;
  }

  .job-nature, .salary-range, .hiring {
    display: inline-block;
    width: 33%;
    height: 14px;
    line-height: 14px;
    font-size: 14px;
    color: #666666;
  }

 .job-nature {
   text-align: left;
 }

 .salary-range {
   width: 34%;
   text-align: center;
 }

 .hiring {
   text-align: right;
 }

  .station-label {
    width: 80px;
    line-height: 28px;
    font-size: 14px;
    color: #666666;
  }

  .station-des {
    width: 645px;
  }

  .station-des p {
    line-height: 28px;
    font-size: 14px;
    color: #666666;
  }

  .content-wrapper {
    height: 0;
    overflow: hidden;
  }

.tab-page {
  margin-top: 45px;
  text-align: right;
}
</style>
