<template>
  <div class="container">
    <navigation/>
    <router-view></router-view>
  </div>
</template>

<script>
import navigation from './nav'

export default {
  components:{
    navigation,
  }
};
</script>

<style>

</style>