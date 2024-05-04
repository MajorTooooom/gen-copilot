import request from '@/utils/request'
import {Notification, MessageBox, Message, Loading} from 'element-ui';

export function pageFn() {
    return request({
        url: '/templateCfg/page',
        method: 'post'
    })
}

export function templateUploadFn(formData) {
    return request({
        url: '/templateCfg/upload',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
}

export function batchDeleteFn(data) {
    return request({
        url: '/templateCfg/batchDelete',
        method: 'post',
        data
    })

}

export function rowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

export function cellStyleFn({row, column, rowIndex, columnIndex}) {
    if (column.type === 'selection') {
        return {textAlign: 'center'};
    }
    if (['templateName'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    return {textAlign: 'center'};
}

export function headerCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#c9e7e5', textAlign: 'center', color: '#020202'};
}

// 校验上传文件
export function verifyBeforeUploadFn(uploadFileList) {
    if (!uploadFileList) {
        return false;
    }
    // 文件数量限制10
    if (uploadFileList.length > 200) {
        Message.error('数量限制200');
        return false;
    }
    // 单文件大小不能超过1MB,总文件大小不能超过3MB
    let totalSize = 0;
    for (let i = 0; i < uploadFileList.length; i++) {
        if (uploadFileList[i].size > 1 * 1024 * 1024) {
            this.$message.warning(`'单文件大小不能超过1MB：['${uploadFileList[i].name}](${uploadFileList[i].size} bytes)`)
            return false;
        } else {
            totalSize += uploadFileList[i].size;
        }
    }
    if (totalSize > 3 * 1024 * 1024) {
        this.$message.warning('总文件大小不能超过3MB')
        return false;
    }
    // all pass
    return true;
}