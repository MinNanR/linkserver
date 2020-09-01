<template>
  <div style="margin-left:50px;margin-top:50px;">
    <div>
      <table class="table table-striped" style="table-layout:fixed;word-wrap:break-word;">
        <thead>
          <tr>
            <th>操作者用户名</th>
            <th>IP</th>
            <th>请求地址</th>
            <th>进行的操作</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in logList" :key="index">
            <th>{{item.username}}</th>
            <th>{{item.ip}}</th>
            <th>{{item.requestUri}}</th>
            <th>{{item.operation}}</th>
            <th>{{item.createTime}}</th>
            <th></th>
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
      logList: [],
    };
  },
  methods: {
    getlogList() {
      this.request.post("manager/getLogList", {pageSize:10,pageIndex:1}).then((response) => {
        this.logList = response.data.list;
      });
    },
  },
  mounted() {
    this.getlogList();
  },
};
</script>

<style>
</style>