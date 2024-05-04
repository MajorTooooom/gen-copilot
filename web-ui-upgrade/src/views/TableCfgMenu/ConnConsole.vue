<template>
  <div>
    <el-dialog :center="true" :custom-class="'dialog-custom-clazz'" :destroy-on-close="true" :visible.sync="show" title="请选择数据源以获取表格信息">
      <div v-loading="loading">
        <el-form ref="connConsoleForm" :model="connConsoleVo" label-width="80px" size="mini">
          <el-form-item label="数据源">
            <el-select v-model="connConsoleVo.connCfgId" clearable filterable placeholder="请选择数据源" style="min-width: 100%;">
              <el-option v-for="item in connCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="查询库表">
            <el-tooltip class="item" content="首次查询依然需要数据库握手耗时" effect="dark" placement="top" style="width: 150px">
              <el-button @click="getTablePoolOptions(false)">缓存查询（默认）</el-button>
            </el-tooltip>
            <el-tooltip class="item" content="直接查最新的库表信息并更新缓存" effect="dark" placement="top">
              <el-button plain type="success" @click="getTablePoolOptions(true)">刷新查询</el-button>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="库表">
            <el-cascader v-model="connConsoleVo.tableCfgChosen" :options="tableCfgOptions" clearable filterable placeholder="请选择库表" style="min-width: 100%;">
              <template slot-scope="{ node, data }">
                <span>{{ data.label }}</span>
                <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
              </template>
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getDbTableAndColumn">查询“表”信息以及“字段”信息</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {getConnCfgOptionsFn, getTablePoolOptionsFn, getDbTableAndColumnFn} from '@/api/connConsoleApi';

export default {
  name: "ConnConsole",
  components: {},
  data() {
    return {
      loading: false,
      show: false,
      connConsoleVo: {
        connCfgId: null,
        tableCfgChosen: [],
      },
      connCfgOptions: [],
      tableCfgOptions: [],
    }
  },
  methods: {
    getTablePoolOptions(isRefresh) {
      // 定位并提取数据源信息
      let connCfgOption = this.connCfgOptions.find(item => item.value === this.connConsoleVo.connCfgId);
      if (!(connCfgOption && connCfgOption.data)) {
        Message.error('请选择数据源');
        return false;
      }
      this.loading = true;
      getTablePoolOptionsFn(connCfgOption.data, {forced: isRefresh}).then(response => {
        this.tableCfgOptions = response.data;
      }).finally(() => {
        this.loading = false;
      });
    },
    getDbTableAndColumn() {
      let connCfgOption = this.connCfgOptions.find(item => item.value === this.connConsoleVo.connCfgId);
      if (!(connCfgOption && connCfgOption.data)) {
        Message.error('请选择数据源');
        return false;
      }
      if (!(this.connConsoleVo.tableCfgChosen && this.connConsoleVo.tableCfgChosen.length >= 2)) {
        Message.error('请选择库表');
        return false;
      }
      let payload = {
        url: connCfgOption.data.url,
        username: connCfgOption.data.username,
        password: connCfgOption.data.password,
        tableSchema: this.connConsoleVo.tableCfgChosen[0],
        tableName: this.connConsoleVo.tableCfgChosen[1],
      };
      getDbTableAndColumnFn(payload).then(response => {
        this.show = false;
        this.$emit('openColumnCfgAddConsole', response.data);
      });
    },
    getConnCfgOptions() {
      this.loading = true;
      getConnCfgOptionsFn().then(res => {
        this.connCfgOptions = res.data;
      }).finally(() => {
        this.loading = false;
      });
    },
  },// methods
  watch: {
    'connConsoleVo.connCfgId': {
      deep: true,
      handler: function (val, oldVal) {
        if (val) {
          this.getTablePoolOptions(false);
        }
      },
    },
  },
  mounted() {
  },
}
</script>

<style scoped>
/deep/ .dialog-custom-clazz {
  border-radius: 8px;
}
</style>