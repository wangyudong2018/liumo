<template>
  <div class="specialty-item-box">
    <div class="specialty-item-img">
      <img :src="specialtyItemUrl">
    </div>
    <div class="specialty-item-title">
      <news-title :newsTitle="specialtyItem.newsTitle"></news-title>
    </div>
  </div>
</template>

<script>
import newsTitle from '../newsTitle/newsTitle'
export default {
  props: {
    specialtyItem: {
      type: Object,
      required: true
    }
  },
  components: {
    newsTitle
  },
  computed: {
    specialtyItemUrl: function () {
      return require('./imgs/specialty' + this.specialtyItem.specialtyId + '.png')
    }
  }
}
</script>

<style scoped>
  .specialty-item-box {
    float: left;
    width: 401px;
    padding: 0 25px 56px 26px;
  }

  .specialty-item-img {
    width: 350px;
    height: 250px;
    overflow: hidden;
  }

  .specialty-item-img img {
    width: 100%;
    height: auto;
  }
</style>
