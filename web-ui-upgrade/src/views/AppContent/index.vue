<template>
  <div v-loading="loading">
    <div v-if="!expectEnterSpa">
      <Login
          @enterSpa="enterSpa"
          ref="loginRef">
      </Login>
    </div>
    <div v-if="expectEnterSpa">
      <el-container v-cloak class="top-container-clazz">
        <!--Aside-->
        <el-aside style="border: #cde2e0 1px solid;border-radius: 2px;" width="180px">
          <div style="height: 50px;text-align: center;">
            <span style="position: relative;top: 10px;right: 7px;">工 器</span>
          </div>
          <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" @select="handleSelect">
            <el-menu-item index="1"><i class="fa fa-2x fa-home" style="color: #284f11;"></i>&nbsp;&nbsp;首页</el-menu-item>
            <el-menu-item index="2"><i class="fa fa-2x fa-user" style="color: #447a6d;"></i>&nbsp;&nbsp;用户</el-menu-item>
            <el-menu-item index="3"><i class="fa fa-2x fa-database" style="color: #3c803d;"></i>&nbsp;&nbsp;数据源</el-menu-item>
            <el-menu-item index="4"><i class="fa fa-2x fa-table" style="color: #b6793c;"></i>&nbsp;&nbsp;表&表字段</el-menu-item>
            <el-menu-item index="5"><i class="fa fa-2x fa-git" style="color: #b60909;"></i>&nbsp;&nbsp;项目</el-menu-item>
            <el-menu-item index="6"><i class="fa fa-2x fa-file-text-o" style="color: #7217d9;"></i>&nbsp;&nbsp;模板</el-menu-item>
            <el-menu-item index="7"><i class="fa fa-2x fa-steam" style="color: #020202;"></i>&nbsp;&nbsp;执行中心</el-menu-item>
            <el-menu-item index="8"><i class="fa fa-2x fa-question-circle" style="color: #1b59ab;"></i>&nbsp;&nbsp;关于</el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-header style="height: 50px;">
            <!--Header-->
            <Header></Header>
          </el-header>
          <!--Main-->
          <el-main style="padding: 0px !important;">
            <!--<keep-alive><router-view/></keep-alive>-->
            <router-view/>
          </el-main>
          <el-footer style="height: 50px;">
            <Footer/>
          </el-footer>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import 'font-awesome/css/font-awesome.min.css';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import Login from '@/views/Login';
import Header from '@/views/Header';
import Footer from '@/views/Footer';


export default {
  name: "AppContent",
  components: {Login, Header, Footer},
  data() {
    return {
      loading: false,
      activeIndex: '1',
      expectEnterSpa: false,
    }
  },
  computed: {},
  methods: {
    handleSelect(index, indexPath) {
      let arr = [
        {index: '1', path: '/'},
        {index: '2', path: '/genSysUserMenu'},
        {index: '3', path: '/connCfgMenu'},
        {index: '4', path: '/tableCfgMenu'},
        {index: '5', path: '/projectCfgMenu'},
        {index: '6', path: '/templateCfgMenu'},
        {index: '7', path: '/executiveCenterMenu'},
        {index: '8', path: '/aboutMenu'},
      ];
      let path = arr.find(item => item.index === index).path;
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    },
    fullScreenOpen() {
      this.pageLoadingVo = this.$loading({
        lock: true,
        text: '加载中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    fullScreenClose() {
      try {
        this.pageLoadingVo.close();
      } catch (e) {
        console.error(e);
      }
    },
    handleKeyDown(event) {
      if (event.ctrlKey && event.key === 's') {
        event.preventDefault(); // 阻止默认的浏览器行为
        console.log('Ctrl+S被按下');
      }
      if (event.key === 'Enter') {
        console.log('Enter键被按下');
        this.login();
      }
    },
    enterSpa() {
      this.expectEnterSpa = true;
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.$bus.$on(CommonConsts.FULL_SCREEN_OPEN, this.fullScreenOpen);
    this.$bus.$on(CommonConsts.FULL_SCREEN_CLOSE, this.fullScreenClose);
  },
}
</script>

<style scoped>
[v-cloak] {
  display: none;
}

/* ===============================================[测试阶段颜色区分][start]======================================================== */
.el-header, .el-footer {
  background-color: beige;
  color: #333;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
}

.el-main {
  background-color: #FDFDFEFF;
  color: #333;
}

/* ===============================================[测试阶段颜色区分][end  ]======================================================== */


.top-container-clazz {
  overflow: hidden;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: 0px;
}

</style>