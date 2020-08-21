<template>
  <div style="margin-left:50px;margin-top:50px;">
    <div>
      <table class="table table-striped" style="table-layout:fixed;word-wrap:break-word;">
        <thead>
          <tr>
            <th style="width: 100px">用户名</th>
            <th style="width: 100px">昵称</th>
            <th style="width: 100px">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in userList" :key="index">
            <th>{{item.username}}</th>
            <th>{{item.nickName}}</th>
            <th>
              <button class="btn btn-default btn-xs" title="修改" @click="openUpdateUserModal(index)">
                <span class="iconfont icon-edit"></span>
              </button>
              <button
                class="btn btn-default btn-xs"
                style="margin-left:5px"
                title="删除"
                @click="openDeleteUserModal(index)"
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
      @click="openVlidateModal('addUserModal')"
    >添加</button>

    <!-- 检查管理员身份模态框 -->
    <div
      class="modal fade"
      id="adminPasswordModal"
      role="dialog"
      aria-labelledby="myModalLabel"
      data-backdrop="static"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title">请输入管理员密码</h4>
          </div>
          <div class="modal-body">
            <input
              type="password"
              class="form-control"
              ref="validatePassword"
              v-model="passwordForValidate"
              @keyup.enter="handleValidate"
            />
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="handleValidate">提交</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加用户modal -->
    <div
      class="modal fade"
      id="addUserModal"
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
            <h4 class="modal-title" id="exampleModalLabel">添加用户</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label for="name" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-8">
                  <input
                    type="text"
                    class="form-control"
                    id="name"
                    v-model="newUser.username"
                    autocomplete="off"
                    ref="newUsername"
                  />
                </div>
              </div>
              <div class="form-group">
                <label for="password" class="col-sm-2 control-label">密码</label>
                <div class="col-sm-8">
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    v-model="newUser.password"
                  />
                </div>
              </div>
              <div class="form-group">
                <label for="nickName" class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" id="nickName" v-model="newUser.nickName" />
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="handleAddUserConfirm()">提交</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 更新用户信息modal -->
    <div
      class="modal fade"
      id="updateUserModal"
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
            <h4 class="modal-title" id="exampleModalLabel">修改用户信息</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label for="newUsername" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-8">
                  <input
                    type="text"
                    class="form-control"
                    id="newUsername"
                    v-model="userToUpdate.username"
                    autocomplete="off"
                    disabled
                  />
                </div>
              </div>
              <div class="form-group">
                <label for="newPassword" class="col-sm-2 control-label">密码</label>
                <div class="col-sm-8">
                  <input
                    type="password"
                    class="form-control"
                    id="newPassword"
                    v-model="userToUpdate.password"
                    placeholder="输入新密码，不填则不修改"
                  />
                </div>
              </div>
              <div class="form-group">
                <label for="newNickName" class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-8">
                  <input
                    type="text"
                    class="form-control"
                    id="newNickName"
                    v-model="userToUpdate.nickName"
                  />
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="postUpdateUser()">提交</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 删除用户确认框modal -->
    <div
      class="modal fade"
      tabindex="-1"
      role="dialog"
      aria-labelledby="mySmallModalLabel"
      id="deleteUserModal"
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
            <div class="modal-body" v-if="userToDeleteIndex > -1">
              确认删除用户
              <b>{{userList[userToDeleteIndex].username}}</b>
              吗？
            </div>
            <div class="modal-footer">
              <button class="btn btn-default" @click="postDeleteUser">确认</button>
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
import Schema from "async-validator";
import $ from "jquery";

export default {
  data() {
    return {
      userList: [],
      newUser: {
        username: "",
        password: "",
        nickName: "",
      },
      userToUpdate: {
        id: 0,
        username: "",
        nickName: null,
        password: null,
      },
      userToDeleteIndex: -1,
      targetModal: "",
      passwordForValidate: "",
      rules: {
        username: {
          type: "string",
          required: true,
          message: "用户名不能为空",
        },
        password: {
          type: "string",
          required: true,
          message: "密码不能为空",
        },
      },
    };
  },
  methods: {
    getUserList() {
      this.request
        .post("manager/getUserList")
        .then((response) => {
          this.userList = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openVlidateModal(targetModal) {
      this.targetModal = targetModal;
      $("#adminPasswordModal").modal("show");
    },
    handleValidate() {
      const validator = new Schema({
        password: {
          type: "string",
          required: true,
          message: "密码不能为空",
        },
      });
      validator
        .validate({ password: this.passwordForValidate })
        .then(() => {
          this.request
            .post("manager/validateUser", {
              password: this.passwordForValidate,
            })
            .then((response) => {
              let result = Boolean(response.data);
              if (!result) {
                alert("密码错误");
              } else {
                $("#adminPasswordModal").modal("hide");
                $(`#${this.targetModal}`).modal("show");
              }
            });
        })
        .catch(({ errors, fields }) => {
          if (errors) {
            for (let key in fields) {
              alert(fields[key][0].message);
            }
          }
        });
    },
    openAddUserModal() {
      $("#addUserModal").modal("show");
    },
    handleAddUserConfirm() {
      const validator = new Schema(this.rules);
      validator
        .validate(this.newUser)
        .then(() => {
          this.postAddUser();
        })
        .catch(({ errors, fields }) => {
          if (errors) {
            for (let key in fields) {
              alert(fields[key][0].message);
            }
          }
        });
    },
    postAddUser() {
      this.request
        .post("/manager/addUser", this.newUser)
        .then((response) => {
          $("#addUserModal").modal("hide");
          alert(response.message);
          this.getUserList();
        })
        .catch((error) => {
          console.log(error);
          alert("添加失败");
        });
    },
    openUpdateUserModal(index) {
      this.userToUpdate.username = this.userList[index].username;
      this.userToUpdate.nickName = this.userList[index].nickName;
      this.userToUpdate.id = this.userList[index].id;
      this.openVlidateModal("updateUserModal");
    },
    postUpdateUser() {
      this.request
        .post("manager/updateUser", {
          id: this.userToUpdate.id,
          password: this.userToUpdate.password,
          nickName: this.userToUpdate.nickName,
        })
        .then((response) => {
          $("#updateUserModal").modal("hide");
          alert(response.message);
          this.getUserList();
        })
        .catch((error) => {
          console.log(error);
          alert("更新失败");
        });
    },
    openDeleteUserModal(index) {
      this.userToDeleteIndex = index;
      this.openVlidateModal("deleteUserModal");
    },
    postDeleteUser() {
      let id = this.userList[this.userToDeleteIndex].id;
      this.request
        .post("manager/deleteUser", { id: id })
        .then((response) => {
          this.userToDeleteIndex = -1;
          $("#deleteUserModal").modal("hide");
          alert(response.message);
          this.getUserList();
        })
        .catch((error) => {
          console.log(error);
          alert("更新失败");
        });
    },
  },
  mounted() {
    this.getUserList();
    $("#adminPasswordModal").on("shown.bs.modal", () => {
      this.$refs.validatePassword.focus();
    });
    $("#adminPasswordModal").on("hidden.bs.modal", () => {
      this.passwordForValidate = "";
    });
    $("#addUserModal").on("shown.bs.modal", () => {
      this.$refs.newUsername.focus();
    });
    $("#addUserModal").on("hidden.bs.modal", () => {
      this.newUser = {
        username: "",
        password: "",
        nickName: "",
      };
    });
  },
  watch: {
    "userToUpdate.password": function (newValue) {
      this.userToUpdate.password = newValue == "" ? null : newValue;
    },
    "userToUpdate.nickName": function (newValue) {
      this.userToUpdate.nickName = newValue == "" ? null : newValue;
    },
  },
};
</script>

<style>
</style>