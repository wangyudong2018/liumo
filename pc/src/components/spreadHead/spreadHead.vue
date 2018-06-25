<template>
  <div class="spread-head-wrapper">
    <p class="spread-head-en">{{spreadHead.en}}</p>
    <h2 class="spread-head-zh" :style="customStyle">{{spreadHead.zh}}</h2>
  </div>
</template>

<script>
export default {
  props: {
    spreadHead: {
      type: Object,
      required: true
    }
  },
  computed: {
    customStyle: function () {
      return {
        fontSize: this.spreadHead.fontSize,
        color: this.spreadHead.color
      }
    }
  }
}
</script>

<style scoped>
  .spread-head-wrapper {
    position: relative;
    width: 1205px;
    margin: 0 auto;
    text-align: center;
  }

  .spread-head-en {
    height: 44px;
    transform: scale(1.5, 1);
    font-size: 44px;
    font-weight: 700;
    color: #dadada;
  }

  .spread-head-zh {
    position: absolute;
    top: -10px;
    left: 50%;
    width: 1205px;
    height: 64px;
    line-height: 64px;
    margin-left: -602px;
    font-size: 54px;
    font-weight: 700;
    color: #000000;
  }
</style>
