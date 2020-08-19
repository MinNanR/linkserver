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
      </ul>
    </nav>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
      <div class="container-fluid">
        <div class="row" style="background-color:black;height:65px">
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
import request from '@/utils/request.js'

export default {
  data() {
    return {
      currentPageIndex: 0,
      navList: [
        {
          name: "主页",
          icon: "glyphicon glyphicon-home",
          url: "/manager",
          class: "active",
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
  },
  mounted() {
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
  },
    beforeRouteEnter(to, from , next){
    console.log(to.path)
    request.post
    next()
  }
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