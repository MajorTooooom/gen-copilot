package com.dororo.future.gencopilot.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.dororo.future.gencopilot.dto.ConventionOverConfigurationReqDTO;
import com.dororo.future.gencopilot.dto.RenderReqDTO;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.enums.RenderModeEnum;
import com.dororo.future.gencopilot.service.ExecutiveCenterService;
import com.dororo.future.gencopilot.service.ExecutiveCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/executiveCenter")
public class ExecutiveCenterController {
    @Autowired
    private ExecutiveCenterService executiveCenterService;
    @Autowired
    private ExecutiveCfgService executiveCfgService;

    @PostMapping("/uniteExecutiveOptions")
    public R getUniteExecutiveOptions() {
        return R.successData(executiveCenterService.getUniteExecutiveOptions());
    }

    /**
     * 根据`表&表字段配置`以及`渲染配置/项目配置`,为所有模板设置推荐的路径或名称
     */
    @PostMapping("/conventionOverConfiguration")
    public R conventionOverConfiguration(@RequestBody @Validated ConventionOverConfigurationReqDTO reqDTO) {
        return R.successData(executiveCenterService.getConventionOverConfiguration(reqDTO));
    }

    @PostMapping("/render")
    public R render(@RequestBody @Validated RenderReqDTO reqDTO) {
        if (RenderModeEnum.TO_PROJECT.equals(RenderModeEnum.getByCode(reqDTO.getMode()))) {
            Boolean devIng = Convert.toBool(SpringUtil.getBean(Environment.class).getProperty("TEST_DEV_ING"), false);
            if (!devIng) {
                throw new RuntimeException("服务器环境不允许直接生成至项目目录,请使用下载功能");
            }
        }
        return R.successData(executiveCenterService.renderDispatch(reqDTO));
    }

    @GetMapping("/download/{name}")
    public void download(@PathVariable("name") String name, HttpServletResponse response) {
        executiveCenterService.download(name, response);
    }

    @GetMapping("/getHistoryCfgs")
    public R getHistoryCfgs() {
        return R.successData(executiveCfgService.getHistoryCfgs());
    }

    @GetMapping("/useHistoryCfg")
    public R useHistoryCfg(@RequestParam Integer id) {
        return R.successData(executiveCfgService.useHistoryCfg(id));
    }
}
