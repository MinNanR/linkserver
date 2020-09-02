<template>
  <div id="wrapper" class="toggled">
    <!-- Sidebar -->
    <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
      <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
          <a href="#">LinkServerManager</a>
        </li>
        <li v-for="(item, index) in navList" :key="index" :class="item.class">
          <a @click="switchPage(index)">
            <i :class="item.icon" style="margin-right: 1rem"></i>
            {{item.name}}
          </a>
        </li>
        <li>
          <a @click="headToIndex">
            <i class="glyphicon glyphicon-globe" style="margin-right: 1rem"></i>
            用户网站
          </a>
        </li>
      </ul>
    </nav>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
      <div class="container-fluid" style="padding-left:0;padding-right:0">
        <div
          style="background-color:black;height:65px;display:flex;justify-content: space-between;"
        >
          <div>
            <button
              type="button"
              class="hamburger is-open animated fadeInLeft"
              data-toggle="offcanvas"
            >
              <span class="hamb-top"></span>
              <span class="hamb-middle"></span>
              <span class="hamb-bottom"></span>
            </button>
          </div>
          <div style="float:right;color:white;display:flex;align-items:center">
            <h4>你好，{{userInfo.nickName}}</h4>
            <a style="margin-left:10px;" @click="logout()">
              <h4>
                <span class="iconfont icon-log-out"></span>退出登录
              </h4>
            </a>
          </div>
        </div>
        <div class="row">
          <router-view></router-view>
        </div>
      </div>
    </div>
    <!-- /#page-content-wrapper -->
  </div>
</template>

<script>
import "@/assets/css/manager.css";
import $ from "jquery";

export default {
  data() {
    return {
      currentPageIndex: 0,
      navList: [
        {
          name: "主页",
          icon: "glyphicon glyphicon-home",
          url: "/manager",
          class: "",
        },
        {
          name: "链接管理",
          icon: "glyphicon glyphicon-paperclip",
          url: "/manager/link",
          class: "",
        },
        {
          name: "工具管理",
          icon: "glyphicon glyphicon-folder-open",
          url: "/manager/tools",
          class: "",
        },
        {
          name: "使用说明",
          icon: "glyphicon glyphicon-wrench",
          url: "/manager/introduction",
          class: "",
        },
        {
          name: "用户管理",
          icon: "glyphicon glyphicon-user",
          url: "/manager/user",
          class: "",
        },
        {
          name: "日志",
          icon: "glyphicon glyphicon-file",
          url: "/manager/logs",
          class: "",
        },
      ],
      userInfo: {
        nickName: "",
      },
      infoClassExpand: {
        float: "right",
        "margin-right": "220px",
      },
      infoClassCollpased: {
        float: "right",
      },
    };
  },
  methods: {
    switchPage(i) {
      if (i === this.currentPageIndex) {
        return;
      }
      //改变样式
      this.navList.forEach((item, index) => {
        if (index === i) {
          item.class = "active";
        } else {
          item.class = "";
        }
      });
      this.currentPageIndex = i;
      this.$router.push(this.navList[i].url);
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("whiteList");
      this.$router.push("/login");
    },
    headToIndex(){
      this.$router.push('/');
    }
  },
  mounted() {
    this.request
      .post("/api/getUserInformation")
      .then((response) => {
        this.userInfo.nickName = response.data.nickName;
      })
      .catch((error) => {
        alert("获取用户信息异常");
        console.log(error);
      });

    var trigger = $(".hamburger"),
      overlay = $(".overlay"),
      isClosed = true;

    trigger.click(function () {
      hamburger_cross();
    });

    function hamburger_cross() {
      if (isClosed == true) {
        overlay.hide();
        trigger.removeClass("is-open");
        trigger.addClass("is-closed");
        isClosed = false;
      } else {
        overlay.show();
        trigger.removeClass("is-closed");
        trigger.addClass("is-open");
        isClosed = true;
      }
    }

    $('[data-toggle="offcanvas"]').click(function () {
      $("#wrapper").toggleClass("toggled");
    });

    setTimeout(() => {
      //根据当前路径高亮导航
      let currentPath = this.$route.path;
      this.navList.forEach((item) => {
        if (item.url == currentPath) {
          item.class = "active";
        } else {
          item.class = "";
        }
      });
    }, 100);
  },
  beforeRouteEnter(to, from, next) {
    next();
  },
};
</script>

<style>
.active {
  background-color: #7c5aae;
  color: black;
}

a:hover {
  cursor: pointer;
}
</style>