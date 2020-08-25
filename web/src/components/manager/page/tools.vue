<template>
  <div style="margin-left:50px;margin-top:50px;">
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
              <button class="btn btn-default btn-xs" title="下载">
                <span class="iconfont icon-download"></span>
              </button>
              <button
                class="btn btn-default btn-xs"
                style="margin-left:5px"
                title="删除"
                @click="openDeleteModal(index)"
              >
                <span class="iconfont icon-delete"></span>
              </button>
            </th>
          </tr>
        </tbody>
      </table>
    </div>

    <button
      type="button"
      class="btn btn-primary btn-lg"
      data-toggle="modal"
      data-target="#addFileModal"
    >添加</button>

    <!-- 添加文件modal -->
    <div
      class="modal fade"
      id="addFileModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="myModalLabel"
      data-backdrop="static"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="exampleModalLabel">添加文件</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="name" class="control-label">文件名</label>
                <input type="text" class="form-control" id="name" v-model="name" autocomplete="off" />
              </div>
              <div class="form-group">
                <label for="file">文件</label>
                <input type="file" id="file" ref="file" @change="handleFileChange" />
                <p class="help-block">请选择要上传的文件</p>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="handleSubmit">提交</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 删除节点确认模态框 -->
    <div
      class="modal fade"
      tabindex="-1"
      role="dialog"
      aria-labelledby="mySmallModalLabel"
      id="deleteToolsModal"
    >
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
              <h4 class="modal-title">确认删除</h4>
            </div>
            <div class="modal-body" v-if="toolsToDeleteIndex > -1">
              确认删除文件
              <b>{{toolsList[toolsToDeleteIndex].fileName}}</b>
              吗？
            </div>
            <div class="modal-footer">
              <button class="btn btn-default" @click="handleDeleteConfirm">确认</button>
              <button class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/fonts/iconfont.css";
import $ from "jquery";
import axios from "axios";

export default {
  data() {
    return {
      name: "",
      file: null,
      toolsList: [],
      toolsToDeleteIndex: -1,
    };
  },
  methods: {
    handleFileChange() {
      let fileDOM = this.$refs.file;
      this.file = fileDOM.files[0];
    },
    async handleSubmit() {
      let baseUrl = this.baseUrl;
      let formData = new FormData();
      formData.append("file", this.file);
      formData.append("name", this.name);
      await axios({
        url: `${baseUrl}/manager/addTools`,
        data: formData,
        method: "post",
        headers: {
          "Content-Type": "multipart/form-data",
          authorization: localStorage.getItem("token"),
        },
      })
        .then((response) => {
          console.log(response);
          $("#addFileModal").modal("hide");
          alert("上传成功");
        })
        .catch((error) => {
          console.log(error);
        });
      this.formData = null;
    },
    getToolsList() {
      this.request.post("api/getToolsList", {}).then((response) => {
        this.toolsList = response.data;
      });
    },
    openDeleteModal(index) {
      this.toolsToDeleteIndex = index;
      $("#deleteToolsModal").modal("show");
    },
    handleDeleteConfirm() {
      let id = this.toolsList[this.toolsToDeleteIndex].id;
      this.request
        .post("/manager/deleteTools", { id: id })
        .then((response) => {
          $("#deleteToolsModal").modal("hide");
          alert(response.message);
          this.getToolsList();
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
  mounted() {
    this.getToolsList();
    $("#addFileModal").on("hidden.bs.modal", () => {
      this.name = null;
      this.file = null;
      this.$refs.file.value = "";
    });
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