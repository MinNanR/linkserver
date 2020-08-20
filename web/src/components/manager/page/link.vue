<template>
  <div style="margin-left: 2%;margin-right:5%">
    <div>
      <table class="table table-striped" style="table-layout:fixed;word-wrap:break-word;">
        <thead>
          <tr>
            <th style="width:10%">节点名</th>
            <th style="width:50%">url</th>
            <th style="width:20%">更新时间</th>
            <th style="width:20%">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in linkList" :key="index">
            <th>{{item.name}}</th>
            <th>{{item.link}}</th>
            <th>{{item.updateTime}}</th>
            <th>
              <button class="btn btn-default btn-xs" title="修改" @click="openUpdateModal(index)">
                <span class="iconfont icon-edit"></span>
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
      data-target="#newLinkModal"
    >添加</button>

    <!-- 添加节点模态框 -->
    <div
      class="modal fade"
      id="newLinkModal"
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
            <h4 class="modal-title" id="exampleModalLabel">新增节点</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="name" class="control-label">节点名称:</label>
                <input type="text" class="form-control" id="name" v-model="newLink.name" />
              </div>
              <div class="form-group">
                <label for="link" class="control-label">链接:</label>
                <textarea
                  class="form-control"
                  id="link"
                  style="resize:none;height:100px"
                  v-model="newLink.link"
                ></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="handleAddConfirm">提交</button>
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
      id="deletLinkModal"
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
            <div class="modal-body" v-if="linkToDeleteIndex > -1">
              确认删除节点
              <b>{{linkList[linkToDeleteIndex].name}}</b>
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

    <!-- 修改节点模态框 -->
    <div
      class="modal fade"
      id="updateLinkModal"
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
            <h4 class="modal-title" id="exampleModalLabel">修改节点</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="name" class="control-label">节点名称:</label>
                <input type="text" class="form-control" id="name" v-model="linkToUpdate.name" />
              </div>
              <div class="form-group">
                <label for="link" class="control-label">链接:</label>
                <textarea
                  class="form-control"
                  id="link"
                  style="resize:none;height:100px"
                  v-model="linkToUpdate.link"
                ></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="handleUpdateConfirm">提交</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/fonts/iconfont.css";
import $ from "jquery";
import Schema from "async-validator";

export default {
  data() {
    return {
      linkList: [],
      newLink: {
        name: "",
        link: "",
      },
      rules: {
        name: {
          type: "string",
          required: true,
          message: "名字不能为空",
        },
        link: {
          type: "string",
          required: true,
          message: "链接地址不能为空",
        },
      },
      linkToDeleteIndex: -1,
      linkToUpdate: {
        id: 0,
        name: "",
        link: "",
      },
    };
  },
  methods: {
    getLinkList() {
      this.request.post("/api/getAllLinkList").then((response) => {
        this.linkList = response.data;
        this.linkToDeleteIndex = -1;
      });
    },
    handleModalClose() {
      this.newLink.name = "";
      this.newLink.link = "";
    },
    handleAddConfirm() {
      const validator = new Schema(this.rules);
      validator
        .validate(this.newLink)
        .then(() => {
          this.postAddLink();
        })
        .catch(({ errors, fields }) => {
          if (errors) {
            for (let key in fields) {
              alert(fields[key][0].message);
            }
          }
        });
    },
    postAddLink() {
      this.request
        .post("/manager/addLink", this.newLink)
        .then((response) => {
          $("#newLinkModal").modal("hide");
          alert(response.message);
          this.getLinkList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openDeleteModal(index) {
      this.linkToDeleteIndex = index;
      $("#deletLinkModal").modal("show");
    },
    handleDeleteConfirm() {
      let id = this.linkList[this.linkToDeleteIndex].id;
      this.request
        .post("/manager/deleteLink", { id: id })
        .then((response) => {
          $("#deletLinkModal").modal("hide");
          alert(response.message);
          this.getLinkList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openUpdateModal(index) {
      this.linkToUpdate.id = this.linkList[index].id;
      this.linkToUpdate.name = this.linkList[index].name;
      this.linkToUpdate.link = this.linkList[index].link;
      $("#updateLinkModal").modal("show");
    },
    handleUpdateConfirm() {
      const validator = new Schema(this.rules);
      validator
        .validate(this.linkToUpdate)
        .then(() => {
          this.postUpdateLink();
        })
        .catch(({ errors, fields }) => {
          if (errors) {
            for (let key in fields) {
              alert(fields[key][0].message);
            }
          }
        });
    },
    postUpdateLink() {
      this.request
        .post("/manager/updateLink", this.linkToUpdate)
        .then((response) => {
          $("#updateLinkModal").modal("hide");
          alert(response.message);
          this.getLinkList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getLinkList();
    $("#newLinkModal").on("hidden.bs.modal", () => {
      this.handleModalClose();
    });
  },

};
</script>

<style>
</style>