<template>
  <div id="app">
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>

export default {
  name: "App",
  components: {},
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

@font-face {
  font-family: "iconfont";
  src: url('/assets/fonts/iconfont.eot'), /* IE6-IE8 */
  url('/assets/fonts/iconfont.woff') format('woff'), /* chrome, firefox */
  url('/assets/fonts/iconfont.woff2') format('woff2'), /* chrome, firefox */
  url('/assets/fonts/iconfont.ttf') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
  url('/assets/fonts/iconfont.svg#iconfont') format('svg'); /* iOS 4.1- */
}

.iconfont {
  font-family: "iconfont" !important;
  font-size: 16px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

</style>
