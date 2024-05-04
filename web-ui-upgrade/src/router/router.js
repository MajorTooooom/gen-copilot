import Vue from 'vue';
import Router from 'vue-router';
import HomeMenu from '../views/HomeMenu';
import GenSysUserMenu from '../views/GenSysUserMenu';
import ConnCfgMenu from '../views/ConnCfgMenu';
import TableCfgMenu from '../views/TableCfgMenu';
import ProjectCfgMenu from '../views/ProjectCfgMenu';
import TemplateCfgMenu from '../views/TemplateCfgMenu';
import ExecutiveCenterMenu from '../views/ExecutiveCenterMenu';
import AboutMenu from '../views/AboutMenu';


Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {path: '/', name: 'HomeMenu', component: HomeMenu},
        {path: '/genSysUserMenu', name: 'GenSysUserMenu', component: GenSysUserMenu},
        {path: '/connCfgMenu', name: 'ConnCfgMenu', component: ConnCfgMenu},
        {path: '/tableCfgMenu', name: 'TableCfgMenu', component: TableCfgMenu},
        {path: '/projectCfgMenu', name: 'ProjectCfgMenu', component: ProjectCfgMenu},
        {path: '/templateCfgMenu', name: 'TemplateCfgMenu', component: TemplateCfgMenu},
        {path: '/executiveCenterMenu', name: 'ExecutiveCenterMenu', component: ExecutiveCenterMenu},
        {path: '/aboutMenu', name: 'AboutMenu', component: AboutMenu}
    ]
});
