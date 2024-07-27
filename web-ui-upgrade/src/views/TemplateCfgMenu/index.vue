<template>
  <div v-loading="loading">
    <div style="text-align: right;">
      <el-button-group>
        <el-button size="mini" style="min-width: 250px;" type="danger" @click="beforeBatchDelete">删 除</el-button>
        <el-button size="mini" style="min-width: 250px;" type="warning" @click="uploadDialogVo.show=true">上 传</el-button>
        <el-button size="mini" style="min-width: 100px;" type="primary" @click="page">刷新列表</el-button>
      </el-button-group>
    </div>
    <el-dialog
        :close-on-click-modal="false"
        :custom-class="'upload-dialog-clazz'"
        :visible.sync="uploadDialogVo.show"
        title="上传模板文件"
        width="50%">
      <el-upload
          ref="uploadRef"
          :auto-upload="false"
          :before-upload="beforeUpload"
          :file-list="uploadFileList"
          :limit="200"
          :multiple="true"
          :on-change="uploadHandleChange"
          :on-remove="uploadHandleRemove"
          :show-file-list="false"
          action=""
          style="display: inline-block;margin-right: 10px;">
        <!-- 这里不放置任何内容，从而隐藏默认的上传按钮 -->
      </el-upload>
      <!--触发-->
      <el-button size="mini" style="width: 100%" type="warning" @click="selectUploadFile">选择文件{{ uploadFileTip }}</el-button>
      <div style="min-height: 100px; max-height: 350px; overflow-y: auto;overflow-x: hidden; ">
        <p v-for="tag in uploadFileList" :key="tag.name">
          <el-tag :type="'success'" closable @close="removeUpload(tag)">&nbsp;{{ tag.name }}&nbsp;</el-tag>
        </p>
      </div>
      <p><span>待上传文件：[{{ this.uploadFileList.length }}]个</span></p>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="uploadDialogVo.show = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="templateUploadConfirm">提 交</el-button>
      </span>
    </el-dialog>
    <!---->
    <el-table
        :border="true"
        :cell-style="getCellStyle"
        :data="tableData"
        :fit="true"
        :header-cell-style="getHeaderCellStyle"
        :row-style="getRowStyle"
        :stripe="true"
        max-height="700"
        size="mini"
        style="width: 100%;min-height:200px;"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="排序" width="55">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column prop="id" label="ID" width="50"></el-table-column>-->
      <el-table-column label="模板名称" prop="templateName"></el-table-column>
      <!--      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>-->
      <el-table-column label="文件最后修改时间" prop="updateTime" width="150"></el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="download(scope.row)">下载文件</el-button>
          <!--          <el-button type="text" size="mini" @click="updateContent(scope.row)">修改文件</el-button>-->
          <el-button size="mini" type="text" @click="beforeDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
      <template slot="empty">
        <el-empty :image-size="imageSize"></el-empty>
      </template>
    </el-table>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import DynamicForm from "@/components/DynamicForm.vue";
import {
  pageFn,
  rowStyleFn,
  cellStyleFn,
  templateUploadFn,
  headerCellStyleFn,
  verifyBeforeUploadFn,
  batchDeleteFn,
} from '@/api/templateCfgMenuApi';

export default {
  name: "TemplateCfgMenu",
  components: {DynamicForm},
  data() {
    return {
      loading: false,
      tableData: [],
      uploadFileList: [],
      uploadFileTip: '',
      msgPromise: Promise.resolve(),
      uploadDialogVo: {
        show: false,
      },
      multipleSelection: [],
      imageSize: 60,
    }
  },
  methods: {
    getRowStyle: rowStyleFn,
    getCellStyle: cellStyleFn,
    getHeaderCellStyle: headerCellStyleFn,
    page() {
      this.loading = true;
      pageFn().then(res => {
        this.tableData = res.data;
      }).finally(() => {
        this.loading = false;
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    download(row) {
      // 文件名处理路径编码
      const tName = encodeURIComponent(row.templateName);
      const userId = this.$store.getters.userId;
      const url = `${process.env.VUE_APP_BASE_API}/templateCfg/download/${userId}/${tName}`;
      window.open(url, '_blank');
    },
    updateContent(row) {
    },
    beforeDelete(row) {
      MessageBox.confirm('服务器文件将执行物理删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteAction([row.templateName]);
      }).catch(() => {
        // 取消
      });
    },
    beforeBatchDelete() {
      if (!(this.multipleSelection && this.multipleSelection.length > 0)) {
        Message({message: '请选择要删除的文件', type: 'warning', duration: 3000});
        return false;
      }
      MessageBox.confirm('服务器文件将执行物理删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let arr = [];
        for (let i = 0; i < this.multipleSelection.length; i++) {
          arr.push(this.multipleSelection[i].templateName);
        }
        this.deleteAction(arr);
      }).catch(() => {
        // 取消
      });
    },
    deleteAction(names) {
      // console.log('names:', names);
      batchDeleteFn({names: names}).then(res => {
        this.page();
      });
    },
    selectUploadFile() {
      // 使用this.$refs.uploadRef.$el来获取组件的根DOM元素
      const uploadInput = this.$refs.uploadRef.$el.querySelector('.el-upload__input');
      if (uploadInput) {
        uploadInput.click();
      } else {
        console.error('Cannot find the upload input');
      }
    },
    uploadHandleChange(file, fileList) {
      // 判断是否与此前已选择的文件重名
      if (this.uploadFileList.some(i => i.name === file.name)) {
        this.msgPromise = this.msgPromise.then(() => {
          Notification({title: '提示', message: `文件${file.name}重复选择,已忽略`, type: 'warning', position: 'bottom-right', duration: 3000});
        });
        fileList.pop();
        return false;
      }
      // 判断是否为ftl或vm文件
      if (!(file.name.toLowerCase().endsWith('.ftl') || file.name.toLowerCase().endsWith('.vm'))) {
        this.msgPromise = this.msgPromise.then(() => {
          Notification({title: '提示', message: `文件${file.name}不是ftl或vm文件,已忽略`, type: 'warning', position: 'bottom-right', duration: 3000});
        });
        fileList.pop();
        return false;
      }
      this.uploadFileList = this.uploadFileList.concat(file);
      // this.msgPromise = this.msgPromise.then(() => {Notification({title: '提示', message: `文件${file.name}已加入待上传列表`, type: 'info', position: 'bottom-right', duration: 1000});});
      // console.log(this.uploadFileList);
    },
    uploadHandleRemove(file, fileList) {
      this.uploadFileList = fileList;
    },
    beforeUpload() {
      // 阻止自动上传
      return false;
    },
    // 上传文件前的校验,大小、数量等
    templateUploadConfirm() {
      if (!verifyBeforeUploadFn(this.uploadFileList)) {
        return false;
      }
      // 二次确认
      MessageBox.confirm('服务器的同名文件会被覆盖，确定上传文件吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.templateUpload();
      }).catch(() => {
        // 取消
      });
    },
    templateUpload() {
      // 构建formData
      const formData = this.buildUploadFormData();
      this.loading = true;
      templateUploadFn(formData).then(res => {
      }).finally(() => {
        this.loading = false;
        this.uploadDialogVo.show = false;
        this.uploadFileList = [];
        this.page();
      });
    },
    buildUploadFormData() {
      // 创建一个空的FormData对象
      const formData = new FormData()
      // 你可以使用FormData.append来添加键/值对到表单里面;
      this.uploadFileList.forEach((file) => {
        formData.append('file', file.raw)
      })
      // (可选操作)添加自定义参数
      formData.append('prefix', 'test001');
      formData.append('suffix', 'test002');
      return formData;
    },
    removeUpload(tag) {
      this.uploadFileList = this.uploadFileList.filter(i => i.name !== tag.name);
      console.log(this.uploadFileList);
    },
    handleKeyDown(event) {
      // 监听+-键
      // if (event.key === 'Enter') {console.log('Enter键被按下');}
      if (event.key === '+') {
        console.log('按下了+键');
        this.uploadDialogVo.show = true;
      }
      if (event.key === '-') {
        console.log('按下了-键');
        this.beforeBatchDelete();
      }
    },
  },
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    window.addEventListener('keydown', this.handleKeyDown);
    this.page();
  },
  beforeDestroy() {
    window.removeEventListener('keydown', this.handleKeyDown);
  },
}
</script>

<style scoped>
/deep/ .upload-dialog-clazz {
  border-radius: 8px;
}
</style>