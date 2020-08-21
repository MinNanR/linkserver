<template>
  <div class="container">
      <div v-html="introduction"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      introduction: "",
    };
  },
  methods: {
    getIntroduction() {
      this.request
        .post("api/getIntroduction")
        .then((response) => {
          this.introduction = response.data.introduction;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getIntroduction();
  },
};
</script>

<style>
</style>