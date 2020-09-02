<template>
  <div>
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button
            type="button"
            class="navbar-toggle collapsed"
            data-toggle="collapse"
            data-target="#navbar"
            aria-expanded="false"
          >
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">LinkkServer</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar">
          <ul class="nav navbar-nav" v-for="(item, index) in navList" :key="index">
            <li :class="item.class">
              <!-- <a @click="switchPage(index)">{{item.name}}</a> -->
              <a @click="switchPage(index)">{{item.name}}</a>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right"></ul>
        </div>
        <!-- /.navbar-collapse -->
      </div>
      <!-- /.container-fluid -->
    </nav>
    <div style="margin-bottom:20px;display:flex;justify-content:space-between">
      <h4>你好，{{userInfo.nickName}}</h4>
      <a @click="logout()"><h4><span class="iconfont icon-log-out"></span>退出登录</h4></a>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
import "@/assets/fonts/iconfont.css";

export default {
  data() {
    return {
      navList: [
        {
          name: "链接",
          class: "active",
          url: "/link",
        },
        {
          name: "工具",
          class: "",
          url: "/tools",
        },
        {
          name: "使用说明",
          class: "",
          url: "/introduction",
        },
      ],
      userInfo: {
        nickName: "",
      },
    };
  },
  methods: {
    switchPage(i) {
      //改变样式
      this.navList.forEach((item, index) => {
        if (index === i) {
          item.class = "active";
        } else {
          item.class = "";
        }
      });
      this.$router.push(this.navList[i].url);
    },
    logout(){
      localStorage.removeItem("token")
      localStorage.removeItem("whiteList")
      this.$router.push("/login")
    }
  },
  mounted() {
    if ($(window).width() < 768) {
      $("#navbar a").click(() => {
        $("#navbar").collapse("hide");
      });
      $(window).scroll(() => {
        $("#navbar").collapse("hide");
      });
    }

    setTimeout(() => {
      //根据当前路径高亮导航
      let currentPath = this.$route.path;
      console.log(currentPath);
      this.navList.forEach((item) => {
        if (item.url == currentPath) {
          item.class = "active";
        } else {
          item.class = "";
        }
      });
    }, 100);

    this.request
      .post("/api/getUserInformation")
      .then((response) => {
        this.userInfo.nickName = response.data.nickName;
      })
      .catch((error) => {
        alert("获取用户信息异常");
        console.log(error);
      });
  },
};
</script>

<style>
a:hover {
  cursor: pointer;
}

.navbar {
  margin-bottom: 0;
}
</style>