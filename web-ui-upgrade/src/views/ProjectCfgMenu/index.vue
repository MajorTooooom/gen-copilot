<template>
  <div v-loading="loading">
    <dynamic-form
        ref="searchForm"
        :fields="searchFormFields"
        :initialData="searchFormInitJson"
        :labelWidth="'80px'"
        :mode="'search'"
        :submitBtnLabel="'查询'"
        @formSubmit="searchFormSubmit">
    </dynamic-form>
    <dynamic-table
        ref="projectCfgTable"
        :data="tableData"
        :domainPrimaryKey="'id'"
        :fields="tableFields"
        :headerCellStyle="headerCellStyle"
        @add="openAdd"
        @deleteRow="deleteRows"
        @deleteRows="deleteRows"
        @openUpdate="openUpdate"
        @pageChange="page">
    </dynamic-table>
    <el-dialog :custom-class="'domain-dialog-clazz'" :destroy-on-close="true" :title="domainDialogVo.title" :top="'5vh'" :visible.sync="domainDialogVo.show" :width="'85%'">
      <dynamic-form
          v-if="domainDialogVo.mode==='add'"
          :key="projectCfgAddFormKey"
          ref="projectCfgAddForm"
          :fields="addFormFields"
          :initialData="addFormInitJson"
          :inline="false"
          :labelWidth="'200px'"
          :mode="'add'"
          :resetBtnVif="false"
          :submitBtnLabel="'新增'"
          @formSubmit="addFormSubmit">
      </dynamic-form>
      <dynamic-form
          v-if="domainDialogVo.mode==='update'"
          :key="projectCfgUpdateFormKey"
          ref="projectCfgUpdateForm"
          :fields="updateFormFields"
          :inline="false"
          :labelWidth="'200px'"
          :mode="'update'"
          :submitBtnLabel="'修改'"
          @formSubmit="updateFormSubmit">
      </dynamic-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="domainDialogVo.show = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {
  addFn, deleteFn, updateFn, pageFn, searchFormFields, tableFields, headerCellStyle, addFormFields, updateFormFields,
  searchFormInitJson,
  addFormSubmitFn,
  addFormInitJson,
} from '@/api/projectCfgMenuApi';
import DynamicForm from "@/components/DynamicForm.vue";
import DynamicTable from "@/components/DynamicTable.vue";

export default {
  name: "ProjectCfgMenu",
  components: {DynamicTable, DynamicForm},
  data() {
    return {
      loading: false,
      searchFormFields: searchFormFields,
      searchFormInitJson: searchFormInitJson,
      addFormInitJson: addFormInitJson,
      tableData: [],
      tableFields: tableFields,
      headerCellStyle: headerCellStyle,
      domainDialogVo: {
        title: '未指定操作类型',
        show: false,
        mode: 'none',
      },
      addFormFields: addFormFields,
      updateFormFields: updateFormFields,
      projectCfgAddFormKey: '' + new Date().getTime(),
      projectCfgUpdateFormKey: '' + new Date().getTime(),
    }
  },
  methods: {
    searchFormSubmit(formData) {
      this.page();
    },
    openAdd() {
      this.projectCfgAddFormKey = '' + new Date().getTime();
      this.domainDialogVo.title = '新增';
      this.domainDialogVo.mode = 'add';
      this.domainDialogVo.show = true;
    },
    openUpdate(row) {
      this.projectCfgUpdateFormKey = '' + new Date().getTime();
      this.domainDialogVo.title = '修改';
      this.domainDialogVo.mode = 'update';
      this.$nextTick(() => {
        this.$refs.projectCfgUpdateForm.formModel = row;
      });
      this.domainDialogVo.show = true;
    },
    page() {
      let model = this.$refs.searchForm.formModel || {};
      let page = this.$refs.projectCfgTable.pageVo;
      this.loading = true;
      pageFn(model, page).then(res => {
        this.tableData = res.data.list;
        this.$refs.projectCfgTable.pageVo.total = res.data.total;
      }).finally(() => {
        this.loading = false;
      });
    },
    deleteRows(ids) {
      deleteFn({ids: ids}).then(res => {
        this.page();
      });
    },
    addFormSubmit(formData) {
      this.loading = true;
      addFn(formData).then(res => {
        this.domainDialogVo.show = false;
        this.domainDialogVo.mode = 'none';
        this.page();
      }).finally(() => {
        this.loading = false;
      });
    },
    updateFormSubmit(formData) {
      this.loading = true;
      updateFn(formData).then(res => {
        this.domainDialogVo.show = false;
        this.domainDialogVo.mode = 'none';
        this.page();
      }).finally(() => {
        this.loading = false;
      });
    },
  },
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.page();
  },
}
</script>

<style scoped>
/deep/ .domain-dialog-clazz {
  width: 50%;
  border-radius: 10px;
}
</style>