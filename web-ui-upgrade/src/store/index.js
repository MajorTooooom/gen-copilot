import Vue from 'vue'
import Vuex from 'vuex'
// 
import loggedGenSysUser from './modules/loggedGenSysUser'
import getters from "./getters";
//

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        loggedGenSysUser,
    },
    getters
})

export default store