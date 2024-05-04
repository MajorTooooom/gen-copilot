<template>
  <div>
    <h1>ColumnCfgConsole</h1>
    <el-dialog
        :close-on-click-modal="false"
        :custom-class="'column-cfg-console'"
        :destroy-on-close="true"
        :visible.sync="dialogVisible"
        title="提示"
        width="90%">
      <div class="column-cfg-console-body">
        <span>这是一段信息mode={{ mode }}</span>
        <dynamic-form
            ref="tableCfgAddUpdateForm"
            :fields="tableCfgFields"
            :initialData="tableCfgInitData"
            :mode="mode"
            :resetBtnVif="false"
            :submitBtnLabel="mode === 'add' ? '新增' : '修改'"
            @formSubmit="tableCfgAddUpdateFormSubmit">
        </dynamic-form>
        <dynamic-table
            ref="columnCfgTable"
            :batchDeleteBtnVif="false"
            :data="columnCfgTableData"
            :domainPrimaryKey="'id'"
            :fields="columnCfgTableFields"
            :paginationVsh="false"
            :rowAddBtnVif="false"
            :rowDeleteBtnVif="false"
            :rowEditBtnVif="false">
        </dynamic-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import DynamicForm from "@/components/DynamicForm.vue";
import DynamicTable from "@/components/DynamicTable.vue";

export default {
  name: "ColumnCfgConsole",
  components: {DynamicForm, DynamicTable},
  props: {
    mode: {
      type: String,
      default: 'none',
    },
    tableCfgFields: {
      type: Array,
      required: true,
    },
    tableCfgInitData: {
      type: Object,
      default: () => ({}),
    },
    columnCfgTableFields: {
      type: Array,
      required: true,
    },
    columnCfgTableInitData: {
      type: Array,
      required: false,
    },
  },//props
  data() {
    return {
      dialogVisible: false,
      columnCfgTableData: this.columnCfgTableInitData,
    }
  },
  methods: {
    tableCfgAddUpdateFormSubmit(formData) {
      // console.log('tableCfgAddUpdateFormSubmit formData=', formData);
      formData.columnCfgs = this.$refs.columnCfgTable.tableData;
      this.$emit('tableCfgAddUpdateFormSubmit', formData);
    },
  },
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    // this.init();
  },
}
</script>

<style scoped>
/deep/ .column-cfg-console {
  height: 70vh;
  border-radius: 9px;
}
</style>