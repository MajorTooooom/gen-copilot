<template>
  <div>
    <AllRightsReserved></AllRightsReserved>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import AllRightsReserved from '@/components/AllRightsReserved';

export default {
  name: "Footer",
  components: {AllRightsReserved},
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

<style lang="scss" scoped>
</style>