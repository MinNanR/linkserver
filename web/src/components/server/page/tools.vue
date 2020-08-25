<template>
  <div>
    <div>
      <table class="table table-striped" style="table-layout:fixed;word-wrap:break-word;">
        <thead>
          <tr>
            <th>文件名</th>
            <th>类型</th>
            <th>大小</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in toolsList" :key="index">
            <th>{{item.fileName}}</th>
            <th>{{item.extension}}</th>
            <th>{{item.size | resize}} MB</th>
            <th>{{item.updateTime}}</th>
            <th>
              <button class="btn btn-default btn-xs" title="下载" @click="downloadTools(item.id)">
                <span class="iconfont icon-download"></span>
              </button>
            </th>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import "@/assets/fonts/iconfont.css";

export default {
  data() {
    return {
      toolsList: [],
    };
  },
  methods: {
    getToolsList() {
      this.request.post("api/getToolsList", {}).then((response) => {
        this.toolsList = response.data;
      });
    },
    downloadTools(id) {
      this.request
        .post("api/downloadTools", { id: id })
        .then((response) => {
          let downloadUrl = response.data.downloadUrl;
          let fileName = response.data.fileName;
          console.log(downloadUrl, fileName)
          const a = document.createElement("a");
          a.href = downloadUrl;
          a.download = fileName;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getToolsList();
  },
  filters: {
    resize(value) {
      return (value / 1024 / 1024).toFixed(3);
    },
  },
};
</script>

<style>
</style>