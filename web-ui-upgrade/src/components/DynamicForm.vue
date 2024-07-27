<template>
  <div>
    <el-form ref="dynamicForm" :inline="inline" :label-width="labelWidth" :model="formModel" size="mini" style="margin-top: 5px;">
      <el-form-item v-for="(field, index) in fields" :key="index" :label="field.label" :prop="field.prop">
        <component
            :is="field.component"
            v-model="formModel[field.prop]"
            :disabled="field.disabled"
            :style="field.styleMap"
            v-bind="field.componentPropMap"
        >
          <!-- 处理特定的组件属性 -->
          <el-option
              v-for="option in field.options"
              v-if="field.component === 'el-select'"
              :key="option.value"
              :label="option.label"
              :value="option.value"
          ></el-option>
        </component>
      </el-form-item>
      <el-form-item>
        <el-button-group>
          <el-button style="width: 150px;" type="primary" @click="handleSubmit">{{ submitBtnLabel }}</el-button>
          <el-button v-if="resetBtnVif&&mode!=='update'" type="warning" @click="resetForm">重置</el-button>
        </el-button-group>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: "DynamicForm",
  components: {},//components
  props: {
    mode: {
      type: String,
      default: 'none',
    },
    fields: {
      type: Array,
      required: true,
    },
    inline: {
      type: Boolean,
      default: true,
    },
    initialData: {
      type: Object,
      default: () => ({}),
    },
    labelWidth: {
      type: String,
      default: '100px',
    },
    submitBtnLabel: {
      type: String,
      default: '提交',
    },
    resetBtnVif: {
      type: Boolean,
      default: true,
    },
  },//props
  data() {
    return {
      formModel: {...this.initialData},
    }
  },//data
  methods: {
    resetForm() {
      Object.keys(this.formModel).forEach(key => {
        this.formModel[key] = null;
      });
      this.handleSubmit();
    },
    handleSubmit() {
      this.$emit('formSubmit', this.formModel);
    },
    justSetFormModel(formData) {
      this.formModel = formData;
    },
  },//methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },//watch
  mounted() {
    console.log('DynamicForm mounted', this.initialData);
  },//mounted
}
</script>

<style scoped>
.el-form-item .el-date-picker {
  width: 100%;
}
</style>