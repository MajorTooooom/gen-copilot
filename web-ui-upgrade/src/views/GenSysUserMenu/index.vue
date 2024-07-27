<template>
  <div class="template-body">
    <h1><i class="fa fa-5x fa-user-o"></i></h1>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';

export default {
  name: "GenSysUserMenu",
  components: {},
  data() {
    return {}
  },
  methods: {},
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    // this.init();
  },
}
</script>

<style scoped>
.template-body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  font-size: 30px;
  color: #cb7c44;
  background: linear-gradient(to right, #f50771, #00857d);
//background: beige;
}
</style>