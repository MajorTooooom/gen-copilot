import request from '@/utils/request'

export function tableCfgAddFormSubmitFn(data) {
    return request({
        url: '/tableCfg/add',
        method: 'post',
        data
    });
}

export function tableCfgUpdateFormSubmitFn(data) {
    return request({
        url: '/tableCfg/update',
        method: 'post',
        data
    });
}

export function pageFn(data, query) {
    return request({
        url: '/tableCfg/page',
        method: 'post',
        data,
        params: query
    });
}

export function getColumnCfgsFn(query) {
    return request({
        url: '/tableCfg/getColumnCfgsByTableCfgId',
        method: 'get',
        params: query
    });
}

export function deleteRowsFn(data) {
    return request({
        url: '/tableCfg/delete',
        method: 'post',
        data
    });
}

export const searchFormFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUrl", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUsername", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connPassword", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableSchema", "label": "库名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableName", "label": "表名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainName", "label": "实体类名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainZnName", "label": "实体类中文名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "createTime", "label": "创建时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}},
    {"prop": "updateTime", "label": "更新时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}}
];

export const tableCfgAddFields = [
    {"prop": "tableCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUrl", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUsername", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connPassword", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableSchema", "label": "库名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableName", "label": "表名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainName", "label": "实体类名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainZnName", "label": "实体类中文名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
];

export const tableCfgUpdateFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": true, "styleMap": {"width": "200px"}},
    {"prop": "tableCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUrl", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connUsername", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "connPassword", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableSchema", "label": "库名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "tableName", "label": "表名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainName", "label": "实体类名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "domainZnName", "label": "实体类中文名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
];

export const searchFormInitJson = searchFormFields.reduce((acc, item) => {
    acc[item.prop] = ['isExtendTkMapper', 'isGenSwagger', 'isGenComment', 'isGenJavaxValidation'].includes(item.prop) ? true : null;
    return acc;
}, {});

export const tableFields = [
    {"prop": "id", "label": "ID", "componentPropMap": {"width": "50"}},
    {"prop": "tableCfgName", "label": "配置名称", "componentPropMap": {"width": "200"}},
    {"prop": "connUrl", "label": "地址", "componentPropMap": {}},
    {"prop": "connUsername", "label": "用户名", "componentPropMap": {"width": "100"}},
    {"prop": "connPassword", "label": "密码", "componentPropMap": {"width": "100"}},
    {"prop": "tableSchema", "label": "库名", "componentPropMap": {"width": "100"}},
    {"prop": "tableName", "label": "表名", "componentPropMap": {"width": "100"}},
    {"prop": "domainName", "label": "实体类名", "componentPropMap": {"width": "100"}},
    {"prop": "domainZnName", "label": "实体类中文名", "componentPropMap": {"width": "100"}},
    {"prop": "createTime", "label": "创建时间", "componentPropMap": {"width": "150"}},
    {"prop": "updateTime", "label": "更新时间", "componentPropMap": {"width": "150"}}
];

export const headerCellStyle = {
    'left': [],
    'center': ['id', 'tableCfgName', 'connUrl', 'connUsername', 'connPassword', 'createTime', 'updateTime'],
    'right': [],
};

export const columnCfgTableFields = [
    {"prop": "id", "label": "ID", "componentPropMap": {"width": "50"}, "useTemplate": false},
    {"prop": "ordinalPosition", "label": "排序", "componentPropMap": {"width": "50"}, "useTemplate": false},
    {"prop": "columnType", "label": "列类型", "componentPropMap": {"width": "100"}, "useTemplate": false},
    {"prop": "columnName", "label": "列名", "componentPropMap": {"width": "200"}, "useTemplate": false},
    {"prop": "columnKey", "label": "主键", "componentPropMap": {"width": "100"}, "useTemplate": false},
    {"prop": "javaName", "label": "Java字段名", "componentPropMap": {"width": "200"}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入Java字段名', "size": "mini"}},
    {"prop": "javaType", "label": "Java字段类型", "componentPropMap": {"width": "150"}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入Java字段类型', "size": "mini"}},
    {"prop": "jdbcType", "label": "JDBC类型", "componentPropMap": {"width": "150"}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入JDBC类型', "size": "mini"}},
    {"prop": "columnComment", "label": "列注释", "componentPropMap": {}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入列注释', "size": "mini"}},
    {"prop": "columnSwaggerComment", "label": "Swagger注释", "componentPropMap": {"width": "150"}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入Swagger注释', "size": "mini"}},
    {"prop": "columnValidationComment", "label": "校验注释", "componentPropMap": {"width": "150"}, "useTemplate": true, "templateComponent": 'el-input', "templateProps": {"placeholder": '请输入校验注释', "size": "mini"}},
];