<template>
  <div v-loading="loading">
    <el-form ref="cfgForm" :inline="true" :model="cfgFormVo" label-width="110px" size="mini" style="text-align: right; margin-top: 10px;margin-bottom: -10px;">
      <el-form-item label="表&表字段配置">
        <el-select v-model="cfgFormVo.tableCfgId" clearable filterable placeholder="请选择表&表字段配置" style="min-width: 600px;margin-right: -40px;">
          <el-option v-for="item in tableCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渲染配置">
        <el-select v-model="cfgFormVo.projectCfgId" clearable filterable placeholder="请选择项目配置" style="min-width: 400px;">
          <el-option v-for="item in projectCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-button style="min-width: 200px;" type="primary" @click="conventionOverConfiguration">约定大于配置</el-button>
        <el-button type="warning" @click="resetPresuppose">重 置</el-button>
      </el-form-item>
    </el-form>
    <!--  template  -->
    <el-table
        :border="true"
        :cell-style="getCellStyle"
        :data="templateTableData"
        :fit="true"
        :header-cell-style="getHeaderCellStyle"
        :max-height="600"
        :row-style="getRowStyle"
        :stripe="true"
        size="mini"
        style="width: 100%;min-height:200px;"
        @selection-change="handleSelectionChange">
      <!--<el-table-column type="selection" width="55"></el-table-column>-->
      <el-table-column prop="id" label="ID" width="55"></el-table-column>
      <!--      <el-table-column prop="id" label="ID" width="50"></el-table-column>-->
      <el-table-column :show-overflow-tooltip="true" label="模板名称" prop="templateName" width="200"></el-table-column>
      <!--      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>-->
      <el-table-column label="文件最后修改时间" prop="updateTime" width="150"></el-table-column>

      <el-table-column :show-overflow-tooltip="true" label="目标目录绝对路径" prop="destDirAbsPath">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.destDirAbsPath" class="item" effect="dark" placement="right">
            <el-input v-model="scope.row.destDirAbsPath" placeholder="请输入目标目录绝对路径" size="mini"></el-input>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column :show-overflow-tooltip="true" label="目标相对路径包目录" prop="destPackage" width="250">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.destPackage" class="item" effect="dark" placement="left">
            <el-input v-model="scope.row.destPackage" placeholder="请输入目标相对路径包目录,eg:com.dororo.future" size="mini"></el-input>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column :show-overflow-tooltip="true" label="目标文件名" prop="expectName" width="300">
        <template slot-scope="scope">
          <el-input v-model="scope.row.expectName" placeholder="请输入目标文件名" size="mini"></el-input>
        </template>
      </el-table-column>

      <el-table-column :show-overflow-tooltip="true" label="是否使用" prop="expectName" width="70">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.use" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="结果" width="60">
        <template slot-scope="scope">
          <i v-if="scope.row.renderSuccess" class="fa fa-check" style="color: #00857d;"></i>
          <el-button v-if="scope.row.renderSuccess!=null&&!scope.row.renderSuccess" type="danger" circle size="mini" @click="openExDialog(scope.row)"><i class="fa fa-close"></i></el-button>
        </template>
      </el-table-column>
      <template slot="empty">
        <el-empty></el-empty>
      </template>
    </el-table>
    <div style="text-align: center;margin-top: 10px;">
      <el-button-group>
        <el-button :disabled="templateTableData.length==0" icon="el-icon-arrow-left" round size="mini" style="min-width: 30vw;" type="success" @click="renderTo(0)">渲 染 至 项 目 目 录</el-button>
        <el-button round size="mini" type="primary" @click="renderTo(1)">下 载<i class="el-icon-arrow-right el-icon--right"></i></el-button>
      </el-button-group>
    </div>
    <el-dialog
        title="异常信息"
        :visible.sync="exDialogVo.show"
        :custom-class="'ex-dialog-clazz'"
        width="30%">
      <el-input type="textarea" :autosize="{ minRows: 10, maxRows: 40}" placeholder="异常信息...." v-model="exDialogVo.textarea"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" type="warning" @click="exDialogVo.show = false">朕 已 阅</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import {conventionOverConfigurationFn, getCellStyleFn, getHeaderCellStyleFn, getRowStyleFn, renderFn, uniteExecutiveOptionsFn,} from '@/api/executiveCenterMenuApi';

export default {
  name: "ExecutiveCenterMenu",
  components: {},
  data() {
    return {
      loading: false,
      tableCfgOptions: [],
      projectCfgOptions: [],
      templateTableData: [],
      cfgFormVo: {
        tableCfgId: '',
        projectCfgId: '',
      },
      multipleSelection: [],
      exDialogVo: {
        show: false,
        textarea: '',
      },
    }
  },
  methods: {
    getUniteExecutiveOptions() {
      this.loading = true;
      uniteExecutiveOptionsFn().then(res => {
        this.tableCfgOptions = res.data.tableCfgOptions;
        this.projectCfgOptions = res.data.projectCfgOptions;
        // this.templateTableData = res.data.templateTableData;
        // 分别选择第一个
        this.cfgFormVo.tableCfgId = this.tableCfgOptions[0].value;
        this.cfgFormVo.projectCfgId = this.projectCfgOptions[0].value;
        this.conventionOverConfiguration();
      }).finally(() => {
        this.loading = false;
      });
    },
    getRowStyle: getRowStyleFn,
    getCellStyle: getCellStyleFn,
    getHeaderCellStyle: getHeaderCellStyleFn,
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 约定大于配置
    conventionOverConfiguration() {
      let payload = {
        tableCfgId: this.cfgFormVo.tableCfgId,
        projectCfgId: this.cfgFormVo.projectCfgId,
      };
      conventionOverConfigurationFn(payload).then(res => {
        this.templateTableData = res.data.list;
      });
    },
    resetPresuppose() {
      this.cfgFormVo.tableCfgId = '';
      this.cfgFormVo.projectCfgId = '';
      this.templateTableData = [];
    },
    // mode: 0-渲染至项目目录,1-渲染至临时目录并下载
    renderTo(mode) {
      let templateTableDataFilter = this.templateTableData.filter(item => item.use);
      if (templateTableDataFilter.length === 0) {
        this.$message.warning('至少选择一个模板');
        return;
      }
      let payload = {
        mode: mode,
        tableCfgId: this.cfgFormVo.tableCfgId,
        projectCfgId: this.cfgFormVo.projectCfgId,
        list: templateTableDataFilter,
      };
      this.loading = true;
      renderFn(payload).then(res => {
        console.log(res);
        // 根据id将渲染结果合并到表格绑定的数据中
        for (let i = 0; i < res.data.list.length; i++) {
          let resRow = res.data.list[i];
          this.templateTableData.forEach(item => {
            if (item.id === resRow.id) {
              item.renderSuccess = resRow.renderSuccess;
              item.renderException = resRow.renderException;
            }
          });
        }
        this.$msgbox({title: '渲染结果', message: `渲染成功[${res.data.successCount}]个,失败[${res.data.failCount}]个`, type: 'success', showCancelButton: false, confirmButtonText: '朕已阅'});
        if (mode === 1) {
          const url = `${process.env.VUE_APP_BASE_API}/executiveCenter/download/${res.data.tempDirName}`;
          window.open(url, '_blank');
        }
      }).finally(() => {
        this.loading = false;
      });
    },
    openExDialog(row) {
      this.exDialogVo.textarea = row.renderException + "";
      this.exDialogVo.show = true;
    },
  },//methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.getUniteExecutiveOptions();
  },
}
</script>

<style scoped>
/deep/ .ex-dialog-clazz {
  border-radius: 8px;
}
</style>