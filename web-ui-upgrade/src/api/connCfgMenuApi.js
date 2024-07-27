import request from '@/utils/request'

export function addFormSubmitFn(data) {
    return request({
        url: '/connCfg/add',
        method: 'post',
        data,
    });
}

export function updateFormSubmitFn(data) {
    return request({
        url: '/connCfg/update',
        method: 'post',
        data,
    });

}

export function pageFn(data, query) {
    return request({
        url: '/connCfg/page',
        method: 'post',
        data,
        params: query,
    });
}

export function deleteRowsFn(data) {
    return request({
        url: '/connCfg/deleteRows',
        method: 'post',
        data,
    });
}

export function exportDictionaryFn(data) {
    return request({
        url: '/connCfg/exportDictionary',
        method: 'post',
        data,
    });
}

export const searchFormFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "cfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "url", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "username", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "password", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "createTime", "label": "创建时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}},
    {"prop": "updateTime", "label": "更新时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}}
];

export const searchFormInitJson = searchFormFields.reduce((acc, item) => {
    acc[item.prop] = ['isExtendTkMapper', 'isGenSwagger', 'isGenComment', 'isGenJavaxValidation'].includes(item.prop) ? true : null;
    return acc;
}, {});


export const tableFields = [
    {"prop": "id", "label": "ID", "componentPropMap": {"width": "50"}},
    {"prop": "cfgName", "label": "配置名称", "componentPropMap": {"width": "200"}},
    {"prop": "url", "label": "地址", "componentPropMap": {}},
    {"prop": "username", "label": "用户名", "componentPropMap": {"width": "100"}},
    {"prop": "password", "label": "密码", "componentPropMap": {"width": "100"}},
    {"prop": "createTime", "label": "创建时间", "componentPropMap": {"width": "200"}},
    {"prop": "updateTime", "label": "更新时间", "componentPropMap": {"width": "200"}}
];
export const headerCellStyle = {
    'left': [],
    'center': ['id', 'cfgName', 'url', 'username', 'password', 'createTime', 'updateTime'],
    'right': [],
};
export const addFormFields = [
    {"prop": "cfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "url", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "username", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "password", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
];
export const updateFormFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": true, "styleMap": {"width": "100%"}},
    {"prop": "cfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "url", "label": "地址", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "username", "label": "用户名", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "password", "label": "密码", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
];

