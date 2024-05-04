import request from '@/utils/request'

export function addFn(data) {
    return request({
        url: '/projectCfg/add',
        method: 'post',
        data
    });
}

export function deleteFn(data) {
    return request({
        url: '/projectCfg/delete',
        method: 'post',
        data
    });
}

export function updateFn(data) {
    return request({
        url: '/projectCfg/update',
        method: 'post',
        data
    });
}

export function pageFn(data, query) {
    return request({
        url: '/projectCfg/page',
        method: 'post',
        data,
        params: query
    });
}

export const searchFormFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    {"prop": "projectCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "200px"}},
    // {"prop": "sourceCodeAbsPath", "label": "源代码根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入源代码根目录绝对路径,eg:D:/projects/github/gen/src/main/java"}, "styleMap": {"width": "200px"}},
    // {"prop": "domainPackage", "label": "实体类包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入实体类包引用,eg:com.dororo.future.gencopilot.domain"}, "styleMap": {"width": "200px"}},
    // {"prop": "dtoPackage", "label": "DTO包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入DTO包引用,eg:com.dororo.future.gencopilot.dto"}, "styleMap": {"width": "200px"}},
    // {"prop": "mapperPackage", "label": "Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Mapper包引用,eg:com.dororo.future.gencopilot.mapper"}, "styleMap": {"width": "200px"}},
    // {"prop": "servicePackage", "label": "Service包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Service包引用,eg:com.dororo.future.gencopilot.service"}, "styleMap": {"width": "200px"}},
    // {"prop": "controllerPackage", "label": "Controller包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Controller包引用,eg:com.dororo.future.gencopilot.controller"}, "styleMap": {"width": "200px"}},
    // {"prop": "easyExcelListenerPackage", "label": "EasyExcelListener包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入EasyExcelListener包引用,eg:com.dororo.future.gencopilot.easyexcel"}, "styleMap": {"width": "200px"}},
    // {"prop": "resourceAbsPath", "label": "资源文件根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入资源文件根目录绝对路径,eg:D:/projects/github/gen/src/main/resources"}, "styleMap": {"width": "200px"}},
    // {"prop": "mapperXmlPackage", "label": "MapperXml包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入MapperXml包引用,eg:mapperpool.mapper或mapper"}, "styleMap": {"width": "200px"}},
    // {"prop": "isExtendTkMapper", "label": "是否使用通用Mapper", "component": "el-switch", "disabled": false, "componentPropMap": {}, "styleMap": {"width": "200px"}},
    // {"prop": "tkMapperPackage", "label": "通用Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入通用Mapper包引用,eg:com.zhien.common.core.dao"}, "styleMap": {"width": "200px"}},
    // {"prop": "isGenSwagger", "label": "生成swagger注解", "component": "el-switch", "disabled": false, "componentPropMap": {}, "styleMap": {"width": "200px"}},
    // {"prop": "isGenComment", "label": "生成javadoc注释", "component": "el-switch", "disabled": false, "componentPropMap": {}, "styleMap": {"width": "200px"}},
    // {"prop": "isGenJavaxValidation", "label": "生成JavaxValidation注解", "component": "el-switch", "disabled": false, "componentPropMap": {}, "styleMap": {"width": "200px"}},
    // {"prop": "createTime", "label": "创建时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}},
    // {"prop": "updateTime", "label": "更新时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "200px"}}
];
export const addFormFields = [
    // {"prop": "id", "label": "ID", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "projectCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "sourceCodeAbsPath", "label": "源代码根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入源代码根目录绝对路径,eg:D:/projects/github/gen/src/main/java"}, "styleMap": {"width": "100%"}},
    {"prop": "domainPackage", "label": "实体类包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入实体类包引用,eg:com.dororo.future.gencopilot.domain"}, "styleMap": {"width": "100%"}},
    {"prop": "dtoPackage", "label": "DTO包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入DTO包引用,eg:com.dororo.future.gencopilot.dto"}, "styleMap": {"width": "100%"}},
    {"prop": "voPackage", "label": "VO包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入VO包引用,eg:com.dororo.future.gencopilot.vo"}, "styleMap": {"width": "100%"}},
    {"prop": "mapperPackage", "label": "Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Mapper包引用,eg:com.dororo.future.gencopilot.mapper"}, "styleMap": {"width": "100%"}},
    {"prop": "servicePackage", "label": "Service包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Service包引用,eg:com.dororo.future.gencopilot.service"}, "styleMap": {"width": "100%"}},
    {"prop": "controllerPackage", "label": "Controller包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Controller包引用,eg:com.dororo.future.gencopilot.controller"}, "styleMap": {"width": "100%"}},
    {"prop": "easyExcelListenerPackage", "label": "EasyExcelListener包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入EasyExcelListener包引用,eg:com.dororo.future.gencopilot.easyexcel"}, "styleMap": {"width": "100%"}},
    {"prop": "resourceAbsPath", "label": "资源文件根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入资源文件根目录绝对路径,eg:D:/projects/github/gen/src/main/resources"}, "styleMap": {"width": "100%"}},
    {"prop": "mapperXmlPackage", "label": "MapperXml包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入MapperXml包引用,eg:mapperpool.mapper或mapper"}, "styleMap": {"width": "100%"}},
    {"prop": "isExtendTkMapper", "label": "是否使用通用Mapper", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "tkMapperPackage", "label": "通用Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入通用Mapper包引用,eg:com.zhien.common.core.dao"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenSwagger", "label": "生成swagger注解", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenComment", "label": "生成javadoc注释", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenJavaxValidation", "label": "生成JavaxValidation注解", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    // {"prop": "createTime", "label": "创建时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "100%"}},
    // {"prop": "updateTime", "label": "更新时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "100%"}}
];
export const tableFields = [
    {"prop": "id", "label": "ID", "componentPropMap": {"width": "50"}},
    {"prop": "projectCfgName", "label": "配置名称", "componentPropMap": {"width": "200"}},
    {"prop": "sourceCodeAbsPath", "label": "源代码根目录", "componentPropMap": {}},
    // {"prop": "domainPackage", "label": "实体类包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "dtoPackage", "label": "DTO包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "mapperPackage", "label": "Mapper包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "servicePackage", "label": "Service包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "controllerPackage", "label": "Controller包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "easyExcelListenerPackage", "label": "EasyExcelListener包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "resourceAbsPath", "label": "资源文件根目录", "componentPropMap": {"width": "200"}},
    // {"prop": "mapperXmlPackage", "label": "MapperXml包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "isExtendTkMapper", "label": "是否使用通用Mapper", "componentPropMap": {"width": "200"}},
    // {"prop": "tkMapperPackage", "label": "通用Mapper包引用", "componentPropMap": {"width": "200"}},
    // {"prop": "isGenSwagger", "label": "生成swagger注解", "componentPropMap": {"width": "200"}},
    // {"prop": "isGenComment", "label": "生成javadoc注释", "componentPropMap": {"width": "200"}},
    // {"prop": "isGenJavaxValidation", "label": "生成JavaxValidation注解", "componentPropMap": {"width": "200"}},
    {"prop": "createTime", "label": "创建时间", "componentPropMap": {"width": "200"}},
    {"prop": "updateTime", "label": "更新时间", "componentPropMap": {"width": "200"}}
];
export const headerCellStyle = {
    'left': ['sourceCodeAbsPath'],
    'center': ['id', 'projectCfgName', 'createTime', 'updateTime'],
    'right': [],
};

export const searchFormInitJson = searchFormFields.reduce((acc, item) => {
    // acc[item.prop] = ['isExtendTkMapper', 'isGenSwagger', 'isGenComment', 'isGenJavaxValidation'].includes(item.prop) ? true : null;
    acc[item.prop] = null;
    return acc;
}, {});

export const addFormInitJson = searchFormFields.reduce((acc, item) => {
    acc[item.prop] = ['isExtendTkMapper', 'isGenSwagger', 'isGenComment', 'isGenJavaxValidation'].includes(item.prop) ? true : null;
    return acc;
}, {});


export const updateFormFields = [
    {"prop": "id", "label": "ID", "component": "el-input", "disabled": true, "styleMap": {"width": "100%"}},
    {"prop": "projectCfgName", "label": "配置名称", "component": "el-input", "disabled": false, "styleMap": {"width": "100%"}},
    {"prop": "sourceCodeAbsPath", "label": "源代码根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入源代码根目录绝对路径,eg:D:/projects/github/gen/src/main/java"}, "styleMap": {"width": "100%"}},
    {"prop": "domainPackage", "label": "实体类包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入实体类包引用,eg:com.dororo.future.gencopilot.domain"}, "styleMap": {"width": "100%"}},
    {"prop": "dtoPackage", "label": "DTO包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入DTO包引用,eg:com.dororo.future.gencopilot.dto"}, "styleMap": {"width": "100%"}},
    {"prop": "voPackage", "label": "VO包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入VO包引用,eg:com.dororo.future.gencopilot.vo"}, "styleMap": {"width": "100%"}},
    {"prop": "mapperPackage", "label": "Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Mapper包引用,eg:com.dororo.future.gencopilot.mapper"}, "styleMap": {"width": "100%"}},
    {"prop": "servicePackage", "label": "Service包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Service包引用,eg:com.dororo.future.gencopilot.service"}, "styleMap": {"width": "100%"}},
    {"prop": "controllerPackage", "label": "Controller包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入Controller包引用,eg:com.dororo.future.gencopilot.controller"}, "styleMap": {"width": "100%"}},
    {"prop": "easyExcelListenerPackage", "label": "EasyExcelListener包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入EasyExcelListener包引用,eg:com.dororo.future.gencopilot.easyexcel"}, "styleMap": {"width": "100%"}},
    {"prop": "resourceAbsPath", "label": "资源文件根目录", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入资源文件根目录绝对路径,eg:D:/projects/github/gen/src/main/resources"}, "styleMap": {"width": "100%"}},
    {"prop": "mapperXmlPackage", "label": "MapperXml包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入MapperXml包引用,eg:mapperpool.mapper或mapper"}, "styleMap": {"width": "100%"}},
    {"prop": "isExtendTkMapper", "label": "是否使用通用Mapper", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "tkMapperPackage", "label": "通用Mapper包引用", "component": "el-input", "disabled": false, "componentPropMap": {"placeholder": "请输入通用Mapper包引用,eg:com.zhien.common.core.dao"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenSwagger", "label": "生成swagger注解", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenComment", "label": "生成javadoc注释", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    {"prop": "isGenJavaxValidation", "label": "生成JavaxValidation注解", "component": "el-switch", "disabled": false, "componentPropMap": {"active-value": "true", "inactive-value": "false"}, "styleMap": {"width": "100%"}},
    // {"prop": "createTime", "label": "创建时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "100%"}},
    // {"prop": "updateTime", "label": "更新时间", "component": "el-date-picker", "disabled": false, "componentPropMap": {"type": "datetime"}, "styleMap": {"width": "100%"}}
];