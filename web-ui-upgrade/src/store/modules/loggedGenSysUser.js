import {loginFn, signUpFn} from '@/api/loginApi'
import {getToken, setToken, removeToken} from '@/utils/authUtils'

const loggedGenSysUser = {
    state: {
        namespaced: false, //
        userId: '', username: '', isLoggedIn: false, roles: [], permissions: [],
    }, mutations: {
        SET_USERID: (state, userId) => {
            state.userId = userId
        }, SET_USERNAME: (state, username) => {
            state.username = username
        }, SET_ROLES: (state, roles) => {
            state.roles = roles
        }, SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
        }, SET_IS_LOGGED_IN: (state, isLoggedIn) => {
            state.isLoggedIn = isLoggedIn
        }, LOGOUT: (state) => {
            state.username = ''
            state.roles = []
            state.permissions = []
            state.isLoggedIn = false
            removeToken()
        },
    }, actions: {
        login({commit}, userInfo) {
            return new Promise((resolve, reject) => {
                loginFn(userInfo).then(res => {
                    console.log('#登陆成功#', res.data);
                    commit('SET_USERID', res.data.id)
                    commit('SET_USERNAME', res.data.username)
                    commit('SET_ROLES', res.data.roles)
                    commit('SET_PERMISSIONS', res.data.permissions)
                    commit('SET_IS_LOGGED_IN', res.data.isLoggedIn)
                    setToken(res.data.token)
                    resolve(res)
                }).catch(error => {
                    console.log(error)
                    reject(error); // 抛出错误
                });
            });
        }, signUp({commit}, userInfo) {
            signUpFn(userInfo).then(res => {
                // commit('SET_TOKEN', res.data.token)
                // commit('SET_USERNAME', res.data.username)
                // commit('SET_ROLES', res.data.roles)
                // commit('SET_PERMISSIONS', res.data.permissions)
                // commit('SET_IS_LOGGED_IN', res.data.isLoggedIn)
            }).catch(error => {
                console.log(error)
            })
        }, logout({commit}) {
            commit('LOGOUT')
        },
    }
}

export default loggedGenSysUser