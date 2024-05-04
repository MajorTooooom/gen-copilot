<template>
  <div>
    <div class="author-info-zone">
      <p @click="blog">
        <span :style="{'color':colorArr[0]}">© 2024 </span>
        <span :style="{'color':colorArr[1]}">dororo. </span>
        <span :style="{'color':colorArr[2]}">All </span>
        <span :style="{'color':colorArr[3]}">rights </span>
        <span :style="{'color':colorArr[4]}">reserved. </span>
      </p>
    </div>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';

export default {
  name: "AllRightsReserved",
  props: {
    colorArr: {
      type: Array,
      default: () => ['#4aa3fb', '#fc0000', '#EB547D', '#9F6AA7', '#5476B3']
    }
  },
  components: {},
  data() {
    return {}
  },
  methods: {
    blog() {
      window.open('https://dororosheep.cn/', '_blank');
    },
  },//end methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    // this.init();
  },
}
</script>

<style scoped>
.author-info-zone {
  font-family: 'Rubik Mono One', sans-serif;
  position: fixed;
  right: 10px;
  bottom: -3px;
}
</style>