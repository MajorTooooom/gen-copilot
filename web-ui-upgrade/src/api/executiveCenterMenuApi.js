import request from '@/utils/request'


export function uniteExecutiveOptionsFn() {
    return request({
        url: '/executiveCenter/uniteExecutiveOptions',
        method: 'post',
    })
}

export function conventionOverConfigurationFn(data) {
    return request({
        url: '/executiveCenter/conventionOverConfiguration',
        method: 'post',
        data
    })
}

export function renderFn(data) {
    return request({
        url: '/executiveCenter/render',
        method: 'post',
        data
    })
}

export function getHistoryCfgsFn() {
    return request({
        url: '/executiveCenter/getHistoryCfgs',
        method: 'get',
    })
}

export function useHistoryCfgFn(query) {
    return request({
        url: '/executiveCenter/useHistoryCfg',
        method: 'get',
        params: query
    })
}

export function getRowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

export function getCellStyleFn({row, column, rowIndex, columnIndex}) {
    if (column.type === 'selection') {
        return {textAlign: 'center'};
    }
    if (['templateName'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    return {textAlign: 'center'};
}

export function getHeaderCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#c9e7e5', textAlign: 'center', color: '#020202'};
}