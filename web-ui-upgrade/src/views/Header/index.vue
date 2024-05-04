<template>
  <div v-loading="loading">
    <div class="avatar" style="font-size: 10px;" @click="userInfoDialogVo.show = true">
      <el-badge :value="99" class="item">
        <el-avatar :src="logoSrc"></el-avatar>
      </el-badge>
    </div>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import logoLeft from '@/assets/logo_left.png';

export default {
  name: "Header",
  components: {},
  data() {
    return {
      logoSrc: logoLeft,
      loading: false,
      userInfoDialogVo: {
        show: false,
      },
    }
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
.avatar {
  position: fixed;
  right: 25px;
  top: 9px;
}
</style>