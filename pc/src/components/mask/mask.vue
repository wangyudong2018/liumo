<template>
  <div>
    <div class="overlay"></div>
    <slot></slot>
  </div>
</template>

<script>
import $ from 'jquery'
export default {
  mounted () {
    $(document).scroll(function () {
      return false
    })
  }
}
</script>

<style scoped>
  .overlay {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 100;
    background-color: #000000;
    background: rgba(0, 0, 0, .5);
  }
</style>
