<template>
  <div v-loading="loading">
    <dynamic-form
        ref="tableCfgSearchForm"
        :fields="searchFormFields"
        :initialData="searchFormInitJson"
        :mode="'search'"
        :submitBtnLabel="'查询'"
        @formSubmit="searchFormSubmit">
    </dynamic-form>
    <dynamic-table
        ref="tableCfgTable"
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
    <conn-console ref="connConsole" @openColumnCfgAddConsole="openColumnCfgAddConsole"></conn-console>
    <column-cfg-console
        v-if="dealingAddOrUpdate === 'add'"
        :key="columnCfgAddConsoleKey"
        ref="columnCfgAddConsole"
        :columnCfgTableFields="columnCfgTableFields"
        :columnCfgTableInitData="columnCfgTableAddData"
        :mode="'add'"
        :tableCfgFields="tableCfgAddFields"
        :tableCfgInitData="tableCfgAddInitData"
        @tableCfgAddUpdateFormSubmit="tableCfgAddFormSubmit">
    </column-cfg-console>
    <column-cfg-console
        v-if="dealingAddOrUpdate === 'update'"
        :key="columnCfgUpdateConsoleKey"
        ref="columnCfgUpdateConsole"
        :columnCfgTableFields="columnCfgTableFields"
        :columnCfgTableInitData="columnCfgTableUpdateData"
        :mode="'update'"
        :tableCfgFields="tableCfgUpdateFields"
        :tableCfgInitData="tableCfgUpdateInitData"
        @tableCfgAddUpdateFormSubmit="tableCfgUpdateFormSubmit">
    </column-cfg-console>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import DynamicForm from "@/components/DynamicForm.vue";
import DynamicTable from "@/components/DynamicTable.vue";
import ConnConsole from "@/views/TableCfgMenu/ConnConsole.vue";
import ColumnCfgConsole from "@/views/TableCfgMenu/ColumnCfgConsole.vue";
import {
  searchFormFields,
  searchFormInitJson,
  tableFields,
  headerCellStyle,
  pageFn,
  tableCfgAddFields,
  tableCfgUpdateFields,
  columnCfgTableFields,
  tableCfgAddFormSubmitFn,
  tableCfgUpdateFormSubmitFn,
  getColumnCfgsFn,
  deleteRowsFn,
} from '@/api/tableCfgMenuApi';

export default {
  name: "TableCfgMenu",
  components: {DynamicTable, DynamicForm, ConnConsole, ColumnCfgConsole},
  data() {
    return {
      loading: false,
      searchFormFields: searchFormFields,
      searchFormInitJson: searchFormInitJson,
      tableData: [],
      tableFields: tableFields,
      headerCellStyle: headerCellStyle,
      dealingAddOrUpdate: 'none',
      tableCfgAddFields: tableCfgAddFields,
      tableCfgAddInitData: {},
      columnCfgTableFields: columnCfgTableFields,
      columnCfgTableAddData: [],
      tableCfgUpdateFields: tableCfgUpdateFields,
      tableCfgUpdateInitData: {},
      columnCfgTableUpdateData: [],
      columnCfgAddConsoleKey: '',
      columnCfgUpdateConsoleKey: '',
    }
  },
  methods: {
    searchFormSubmit() {
      this.page();
    },
    openAdd() {
      this.columnCfgAddConsoleKey = Date.now(); // 更新 key
      this.$refs.connConsole.show = true;
      this.$refs.connConsole.getConnCfgOptions();
    },
    openUpdate(row) {
      this.columnCfgUpdateConsoleKey = Date.now(); // 更新 key
      // 先把对应的columnCfgs数据查出来
      this.loading = true;
      getColumnCfgsFn({id: row.id}).then(res => {
        this.dealingAddOrUpdate = 'update';
        this.tableCfgUpdateInitData = row;
        this.columnCfgTableUpdateData = res.data;
        this.$nextTick(() => {
          this.$refs.columnCfgUpdateConsole.dialogVisible = true;
        });
      }).finally(() => {
        this.loading = false;
      });
    },
    page() {
      let model = this.$refs.tableCfgSearchForm.formModel || {};
      let page = this.$refs.tableCfgTable.pageVo;
      this.loading = true;
      pageFn(model, page).then(res => {
        console.log('res:', res);
        this.tableData = res.data.list;
        this.$refs.tableCfgTable.pageVo.total = res.data.total;
      }).finally(() => {
        this.loading = false;
      });
    },
    deleteRows(ids) {
      deleteRowsFn({ids: ids}).then(res => {
        this.page();
      });
    },
    openColumnCfgAddConsole(tableCfg) {
      console.log('######tableCfg:', tableCfg);
      this.dealingAddOrUpdate = 'add';
      this.tableCfgAddInitData = tableCfg;
      this.columnCfgTableAddData = tableCfg.columnCfgs || [];
      console.log('$$$$$$this.columnCfgTableAddData:', this.columnCfgTableAddData);
      this.$nextTick(() => {
        this.$refs.columnCfgAddConsole.dialogVisible = true;
      });
    },
    tableCfgAddFormSubmit(formData) {
      // console.log('来自ColumnCfgConsole:', formData);
      tableCfgAddFormSubmitFn(formData).then(res => {
        this.dealingAddOrUpdate = 'none';
        this.page();
      });
    },
    tableCfgUpdateFormSubmit(formData) {
      // console.log('来自ColumnCfgConsole:', formData);
      tableCfgUpdateFormSubmitFn(formData).then(res => {
        this.dealingAddOrUpdate = 'none';
        this.page();
      });
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

<style lang="scss" scoped>
</style>