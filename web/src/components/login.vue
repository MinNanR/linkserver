<template>
  <div class="loginDiv" style="margin-top: 10rem">
    <div>
      <h3>请登陆</h3>
    </div>
    <form action="#">
      <div class="form-group">
        <label for="username" style="display:inline;">账户：</label>
        <input
          v-model="loginForm.username"
          id="username"
          type="text"
          class="form-control"
          style="display:inline;width:200px;"
          autocomplete="off"
        />
      </div>
      <div class="form-group">
        <label for="password" style="display:inline;">密码：</label>
        <input
          v-model="loginForm.password"
          id="password"
          type="password"
          class="form-control"
          style="display:inline;width:200px;"
          autocomplete="off"
          @keyup.enter="login"
        />
      </div>
      <button type="button" @click="login" class="btn btn-primary" style="margin-right: 20px">登录</button>
      <button type="button" class="btn btn-primary" onclick="toSignup()">注册</button>
    </form>
  </div>
</template>

<script>
export default {
    data(){
      return{
        loginForm:{
          username:'',
          password:''
        }
      }
    },
    methods:{
      login(){
        this.request.post('/login', this.loginForm)
        .then(response => {
          console.log(response)
          let token = response.jwtToken
          localStorage.setItem('token', `Bearer ${token}`)
          this.$router.push(response.redirectUrl)
        })
        .catch(error => {
          console.log(error)
        })
      }
    }

};
</script>

<style>
.loginDiv{
  text-align: center;
}
</style>