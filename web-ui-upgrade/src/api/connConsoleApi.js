import request from '@/utils/request'


export function getConnCfgOptionsFn() {
    return request({
        url: '/connCfg/connCfgOptions',
        method: 'post'
    })
}

export function getTablePoolOptionsFn(data, query) {
    return request({
        url: '/tableCfg/tablePoolOptions',
        method: 'post',
        data,
        params: query
    });
}

export function getDbTableAndColumnFn(data) {
    return request({
        url: '/tableCfg/dbTableAndColumn',
        method: 'post',
        data
    });
}