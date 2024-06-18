<template>
  <div>
    <div style="text-align: left;">
      <el-button-group>
        <el-button v-if="rowAddBtnVif" size="mini" type="primary" @click="superAdd">新 增</el-button>
        <el-button v-if="batchDeleteBtnVif" size="mini" type="danger" @click="deleteRows">批量 删除</el-button>
      </el-button-group>
    </div>
    <el-table
        :border="true"
        :cell-style="getCellStyle"
        :data="tableData"
        :fit="true"
        :header-cell-style="getHeaderCellStyle"
        :max-height="300"
        :row-style="getRowStyle"
        :stripe="true"
        size="mini"
        style="width: 100%;min-height:200px;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
          v-for="(field, index) in fields"
          :key="index"
          :label="field.label"
          :prop="field.prop"
          :show-overflow-tooltip="true"
          :style="field.styleMap"
          v-bind="field.componentPropMap">
        <template slot-scope="scope">
          <component :is="field.templateComponent" v-if="field.useTemplate" v-model="scope.row[field.prop]" v-bind="field.templateProps"></component>
          <span v-else>{{ scope.row[field.prop] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" :width="optColumnWidth">
        <template slot-scope="scope">
          <el-button v-if="rowEditBtnVif" size="mini" type="text" @click="openUpdate(scope.row)">编辑</el-button>
          <el-button v-if="rowDeleteBtnVif" size="mini" type="text" @click="deleteRow(scope.row)">删除</el-button>
          <el-button v-if="expansionMethod01Vif" size="mini" type="text" @click="expansionMethod01(scope.row)">{{ expansionMethod01Name }}</el-button>
        </template>
      </el-table-column>
      <template slot="empty">
        <el-empty :image-size="imageSize"></el-empty>
      </template>
    </el-table>
    <el-pagination
        v-show="paginationVsh"
        :current-page="pageVo.pageNum"
        :page-size="pageVo.pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="pageVo.total"
        layout="total, sizes, prev, pager, next, jumper"
        style="text-align: center;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
import {Message, MessageBox} from 'element-ui';

export default {
  name: "DynamicTable",
  components: {},
  props: {
    fields: {
      type: Array,
      required: true,
    },
    domainPrimaryKey: {
      type: String,
      required: true,
    },
    data: {
      type: Array,
      required: false,
    },
    headerCellStyle: {
      type: Object,
      required: false,
    },
    rowEditBtnVif: {
      type: Boolean,
      default: true,
    },
    rowDeleteBtnVif: {
      type: Boolean,
      default: true,
    },
    rowAddBtnVif: {
      type: Boolean,
      default: true,
    },
    batchDeleteBtnVif: {
      type: Boolean,
      default: true,
    },
    expansionMethod01Vif: {
      type: Boolean,
      default: false,
    },
    expansionMethod01Name: {
      type: String,
      default: '扩展方法01',
    },
    paginationVsh: {
      type: Boolean,
      default: true,
    },
    optColumnWidth: {
      type: String,
      default: '120',
    },
  },//props
  data() {
    return {
      imageSize: 60,
      tableData: this.data,
      multipleSelection: [],
      headerCellStyleVo: this.headerCellStyle,
      pageVo: {
        pageNum: 1,
        pageSize: 5,
        total: 0,
        orderBy: 'update_time',
        ascend: 'desc',
      },
    }
  },
  methods: {
    getRowStyle({row, rowIndex}) {
      return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
    },
    getCellStyle({row, column, rowIndex, columnIndex}) {
      if (columnIndex == 0) {
        return {textAlign: 'center'};
      }
      let styleVo = this.headerCellStyleVo;
      if (styleVo && styleVo['left'] && styleVo['left'].length > 0) {
        if (styleVo['left'].includes(column.property)) {
          return {textAlign: 'left'};
        }
      }
      if (styleVo && styleVo['center'] && styleVo['center'].length > 0) {
        if (styleVo['center'].includes(column.property)) {
          return {textAlign: 'center'};
        }
      }
      if (styleVo && styleVo['right'] && styleVo['right'].length > 0) {
        if (styleVo['right'].includes(column.property)) {
          return {textAlign: 'right'};
        }
      }
      return {textAlign: 'center'};
    },
    getHeaderCellStyle({row, column, rowIndex, columnIndex}) {
      return {backgroundColor: '#c9e7e5', textAlign: 'center', color: '#020202'};
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    openUpdate(row) {
      this.$emit('openUpdate', row);
    },
    handleSizeChange(val) {
      this.pageVo.pageSize = val;
      this.$emit('pageChange');
    },
    handleCurrentChange(val) {
      this.pageVo.pageNum = val;
      this.$emit('pageChange');
    },
    superAdd() {
      this.$emit('add');
    },
    deleteRow(row) {
      MessageBox.confirm('确实删除?', '提示', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'}).then(() => {
        this.$emit('deleteRow', [row[this.domainPrimaryKey]]);
      }).catch(() => {
        Message({type: 'info', message: '已取消删除'});
      });
    },
    deleteRows() {
      if (!(this.multipleSelection && this.multipleSelection.length > 0)) {
        this.$message({message: '请选择要删除的数据', type: 'warning'});
        return;
      }
      // 二次确认
      MessageBox.confirm('确实删除?', '提示', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'}).then(() => {
        this.$emit('deleteRows', this.multipleSelection.map(item => item[this.domainPrimaryKey]));
      }).catch(() => {
        Message({type: 'info', message: '已取消删除'});
      });
    },
    expansionMethod01(row) {
      this.$emit('expansionMethod01', row);
    },
  },//methods
  watch: {
    'data': {
      immediate: true,
      deep: true,
      handler: function (newVal, oldVal) {
        if (newVal) {
          this.tableData = newVal;
        }
      },
    },
  },//watch
  mounted() {
    console.log(this.headerCellStyleVo);
  },//mounted
}
</script>

<style scoped>
</style>