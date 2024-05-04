import loggedGenSysUser from "@/store/modules/loggedGenSysUser";

const getters = {
    token: state => state.loggedGenSysUser.token,
    isLoggedIn: state => state.loggedGenSysUser.isLoggedIn,
    userId: state => state.loggedGenSysUser.userId,
}
export default getters
