<template>
  <div class="news-title-wrapper">
    <h2 class="news-title-zh">{{newsTitle.newsTitleZh}}</h2>
    <p class="split-line"></p>
    <h3 class="news-title-en">{{newsTitle.newsTitleEn}}</h3>
  </div>
</template>

<script>
export default {
  props: {
    newsTitle: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
  .news-title-wrapper {
    padding: 30px 60px;
    color: #000000;
  }

  .news-title-zh {
    height: 30px;
    line-height: 30px;
    margin-bottom: 14px;
    text-align: center;
    font-size: 30px;
  }

  .split-line {
    width: 100px;
    margin: 0 auto;
    border-top: 3px solid #2d86e7;
  }

  .news-title-en {
    height: 10px;
    line-height: 10px;
    margin-top: 8px;
    text-align: center;
    font-size: 10px;
  }
</style>
