<template>
  <div class="news-title-wrapper">
    <h2 class="news-title-zh" :style="{fontSize: newsTitle.zhFontSize + 'px'}">{{newsTitle.newsTitleZh}}</h2>
    <p class="split-line" :style="{width: newsTitle.splitWidth + 'px', borderBottomWidth: newsTitle.splitHeight + 'px'}"></p>
    <h3 class="news-title-en" :style="{fontSize: newsTitle.enFontSize + 'px'}">{{newsTitle.newsTitleEn}}</h3>
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
    text-align: center;
    color: #000000;
  }

  .news-title-zh {
    margin-bottom: 14px;
    font-size: 30px;
  }

  .split-line {
    width: 100px;
    margin: 0 auto;
    border-bottom-width: 3px;
    border-bottom-style: solid;
    border-bottom-color: #2d86e7;
  }

  .news-title-en {
    margin-top: 8px;
    font-size: 10px;
  }
</style>
