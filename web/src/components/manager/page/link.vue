<template>
  <div style="margin-left: 2%;margin-right:5%">
    <div>
      <table class="table table-striped" style="table-layout:fixed;word-wrap:break-word;">
        <thead>
          <tr>
            <th style="width:10%">节点名</th>
            <th style="width:50%">url</th>
            <th style="wdith:20%">更新时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in linkList" :key="index">
            <th>{{item.name}}</th>
            <th>{{item.link}}</th>
            <th>{{item.createTime}}</th>
            <th>
              <span class="iconfont icon-edit"></span>
            </th>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      linkList: [],
    };
  },
  methods: {
    getLinkList() {
      this.request.post("/api/getAllLinkList").then((response) => {
        this.linkList = response;
      });
    },
  },
  mounted() {
    this.getLinkList();
  },
};
</script>

<style>
</style>