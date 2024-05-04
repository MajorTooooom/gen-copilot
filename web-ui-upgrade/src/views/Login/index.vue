<template>
  <div class="login-container" v-loading="loading">
    <el-form v-if="!legitimateToken" ref="loginForm" class="login-form" @submit.native.prevent="login" v-cloak>
      <h1 style="text-align: center; position: relative;left: -2px;">工 器</h1>
      <el-form-item label="" required>
        <el-input v-model="credentials.username" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item label="" required>
        <el-input v-model="credentials.password" autocomplete="off" placeholder="密码" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <div style="display: inline-block; width: 80%;">
          <el-button type="primary" @click="login"><i class="fa fa-user-o"></i>&nbsp;&nbsp;登陆</el-button>
        </div>
        <div style="display: inline-block;width: 20%;">
          <el-button type="info " @click="signUp">注册</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-image v-if="legitimateToken" style="width: 180px; height: 180px" :src="logoURl" v-cloak @click="enterSpa"></el-image>
    <all-rights-reserved :colorArr="colorArr"/>
  </div>
</template>

<script>
import AllRightsReserved from "@/components/AllRightsReserved";
import loggedGenSysUser from "@/store/modules/loggedGenSysUser";
import logo from '@/assets/logo.png';
import {getToken} from "@/utils/authUtils";
import {tokenCheckFn} from '@/api/loginApi';

export default {
  name: "Login",
  components: {AllRightsReserved},
  data() {
    return {
      loading: false,
      credentials: {
        username: '',
        password: ''
      },
      logoURl: logo,
      legitimateToken: false,
      colorArr: ['#020202', '#d99f2e', '#020202', '#020202', '#020202']
    }
  },
  methods: {
    tokenCheck() {
      let _token = getToken() || '';
      if (!_token) {
        return false;
      }
      this.loading = true;
      tokenCheckFn({token: _token}).then(res => {
        this.legitimateToken = res.data;
      }).finally(() => {
        this.loading = false;
      });
    },
    login() {
      this.$store.dispatch('login', this.credentials).then(() => {
        if (this.$route.path !== '/') {
          this.$router.push('/');
        }
        // this.legitimateToken = true;
        this.enterSpa();
      });
    },
    signUp() {
      this.$store.dispatch('signUp', this.credentials).then(() => {
        if (this.$route.path !== '/') {
          this.$router.push('/');
        }
      });
    },
    handleKeyDown(event) {
      if (event.key === 'Enter') {
        console.log('Enter键被按下');
        this.login();
      }
    },
    enterSpa() {
      this.$emit('enterSpa');
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    window.addEventListener('keydown', this.handleKeyDown);
    this.tokenCheck();
  },// mounted
  beforeDestroy() {
    window.removeEventListener('keydown', this.handleKeyDown);
  },// beforeDestroy
}
</script>

<style scoped>
[v-cloak] {
  display: none;
}

.login-container {
  overflow: hidden;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: 0px;
  font-family: 'Rubik Mono One', sans-serif;
  background: linear-gradient(to left, #00857d, #f50771);
  /* Flexbox for centering */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.login-form {
  padding: 20px;
  background: white;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  box-sizing: border-box;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  border: none;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #00857d;
}


</style>