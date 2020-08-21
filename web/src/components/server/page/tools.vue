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
import axios from "axios";

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
      const baseUrl = sessionStorage.getItem("baseUrl");
      axios({
        url: `${baseUrl}/api/downloadTools`,
        params: {
          id: id,
        },
        headers:{
          authorization: localStorage.getItem("token"),
        },
        method: "get",
        responseType: "blob",
      }).then((response) => {
        let filename = response.headers["content-disposition"].split(";")[1].split("filename=")[1]
        const blob = response.data;
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onload = (e) => {
          const a = document.createElement("a");
          a.download = filename;
          a.href = e.target.result;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        };
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