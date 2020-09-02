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
      <div style="display:flex;flex-direction: row;align-items:center">
        <div style="vertical-align:middle">共{{totalCount}}条</div>
        <div style="margin-left: 50px">
          <nav aria-label="Page navigation">
            <ul class="pagination">
              <li :class="form.pageIndex == 1 ? 'disabled' : ''">
                <a aria-label="Previous" @click="previousPage()">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li
                v-for="(item, index) in pageList"
                :key="index"
                :class="item == form.pageIndex ? 'active' : ''"
              >
                <a @click="switchPage(item)">{{item}}</a>
              </li>
              <li :class="form.pageIndex === Math.floor(totalCount / form.pageSize) + 1 ? 'disabled' : ''">
                <a aria-label="Next" @click="nextPage()">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      logList: [],
      pageList: [],
      totalCount: 0,
      form: {
        pageSize: 20,
        pageIndex: 1,
      },
    };
  },
  methods: {
    getLogList() {
      this.request.post("manager/getLogList", {pageSize:10,pageIndex:1}).then((response) => {
        this.logList = response.data.list;
        this.totalCount = response.data.totalCount;
        this.pageList = [];
        for (let i = 1; i <= this.totalCount / this.form.pageSize + 1; i++) {
          this.pageList.push(i);
        }
      });
    },
    switchPage(targetPageIndex) {
      this.form.pageIndex = targetPageIndex;
      this.getlogList()
    },
    previousPage() {
      if (this.form.pageIndex == 1) {
        return;
      }
      this.switchPage(this.form.pageIndex - 1);
    },
    nextPage() {
      console.log(this.form.pageIndex, this.totalCount / this.form.pageSize + 1)
      if (this.form.pageIndex === Math.floor(this.totalCount / this.form.pageSize) + 1) {
        return;
      }
      this.switchPage(this.form.pageIndex + 1);
    },
  },
  mounted() {
    this.getLogList();
  },
};
</script>

<style>
</style>