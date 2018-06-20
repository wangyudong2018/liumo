<template>
  <div id="app">
    <my-header></my-header>
    <router-view/>
    <my-footer></my-footer>
  </div>
</template>

<script>
import myHeader from '@/components/header/Header'
import myFooter from '@/components/footer/Footer'
export default {
  name: 'App',
  components: {
    'my-header': myHeader,
    'my-footer': myFooter
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
