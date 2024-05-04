import request from '@/utils/request';

export function loginFn(data) {
    return request({
        url: '/login/submit',
        method: 'post',
        data: data,
        headers: {
            enableAntiShake: true
        }
    })
}

export function signUpFn(data) {
    return request({
        url: '/login/signUp',
        method: 'post',
        data: data,
        headers: {
            enableAntiShake: true
        }
    })
}

export function tokenCheckFn(query) {
    return request({
        url: '/login/check',
        method: 'get',
        params: query,
        headers: {
            enableAntiShake: true
        }
    })
}