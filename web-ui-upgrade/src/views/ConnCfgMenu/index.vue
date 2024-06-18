<template>
  <div v-loading="loading">
    <dynamic-form
        ref="connCfgSearchForm"
        :fields="searchFormFields"
        :initialData="searchFormInitJson"
        :mode="'search'"
        :submitBtnLabel="'查询'"
        @formSubmit="searchFormSubmit">
    </dynamic-form>
    <dynamic-table
        ref="connCfgTable"
        :data="tableData"
        :domainPrimaryKey="'id'"
        :fields="tableFields"
        :headerCellStyle="headerCellStyleVo"
        :expansionMethod01Vif="true"
        :expansionMethod01Name="'导出字典'"
        :optColumnWidth="'200'"
        @add="openAdd"
        @deleteRow="deleteRows"
        @deleteRows="deleteRows"
        @openUpdate="openUpdate"
        @pageChange="page"
        @expansionMethod01="expansionMethod01">
    </dynamic-table>
    <el-dialog :custom-class="'domain-dialog-clazz'" :destroy-on-close="true" :title="domainDialogVo.title" :visible.sync="domainDialogVo.show">
      <dynamic-form
          v-if="domainDialogVo.mode==='add'"
          ref="connCfgAddForm"
          :fields="addFormFields"
          :initialData="searchFormInitJson"
          :inline="false"
          :mode="'add'"
          :submitBtnLabel="'新增'"
          @formSubmit="addFormSubmit">
      </dynamic-form>
      <dynamic-form
          v-if="domainDialogVo.mode==='update'"
          ref="connCfgUpdateForm"
          :fields="updateFormFields"
          :inline="false"
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
import DynamicForm from '@/components/DynamicForm';
import DynamicTable from '@/components/DynamicTable';
import {addFormFields, addFormSubmitFn, deleteRowsFn, headerCellStyle, pageFn, searchFormFields, searchFormInitJson, tableFields, updateFormFields, updateFormSubmitFn} from '@/api/connCfgMenuApi';

export default {
  name: "ConnCfgMenu",
  components: {DynamicForm, DynamicTable},
  data() {
    return {
      loading: false,
      searchFormFields: searchFormFields,
      searchFormInitJson: searchFormInitJson,
      tableFields: tableFields,
      headerCellStyleVo: headerCellStyle,
      tableData: [],
      domainDialogVo: {
        show: false,
        title: '未指定',
        mode: 'none',
      },
      addFormFields: addFormFields,
      updateFormFields: updateFormFields,
    }
  },
  methods: {
    page() {
      let model = this.$refs.connCfgSearchForm.formModel || {};
      let page = this.$refs.connCfgTable.pageVo;
      this.loading = true;
      pageFn(model, page).then(res => {
        // console.log('page res:', res);
        this.tableData = res.data.list;
        this.$refs.connCfgTable.pageVo.total = res.data.total;
      }).finally(() => {
        this.loading = false;
      });
    },
    searchFormSubmit(formData) {
      this.page();
    },
    openAdd() {
      this.domainDialogVo.title = '添加';
      this.domainDialogVo.mode = 'add';
      this.domainDialogVo.show = true;
    },
    addFormSubmit(formData) {
      this.loading = true;
      addFormSubmitFn(formData).then(res => {
        this.domainDialogVo.show = false;
        this.domainDialogVo.mode = 'none';
      }).finally(() => {
        this.loading = false;
      });
    },
    updateFormSubmit(formData) {
      updateFormSubmitFn(formData).then(res => {
        this.domainDialogVo.show = false;
        this.domainDialogVo.mode = 'none';
        this.page();
      });
    },
    openUpdate(row) {
      this.domainDialogVo.title = '修改';
      this.domainDialogVo.mode = 'update';
      this.$nextTick(() => {
        this.$refs.connCfgUpdateForm.justSetFormModel({...row});
      });
      this.domainDialogVo.show = true;
    },
    deleteRows(ids) {
      deleteRowsFn({ids: ids}).then(res => {
        this.page();
      });
    },
    expansionMethod01(row) {
      console.log('expansionMethod01:', row);
    },
  },// methods
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