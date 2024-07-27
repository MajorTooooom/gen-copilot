package com.dororo.future.gencopilot.controller;

import com.dororo.future.gencopilot.dto.ProjectCfgAddDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgDeleteDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgPageDTO;
import com.dororo.future.gencopilot.dto.ProjectCfgUpdateDTO;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.service.ProjectCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projectCfg")
public class ProjectCfgController {
    @Autowired
    private ProjectCfgService projectCfgService;

    @PostMapping("/add")
    public R add(@RequestBody @Validated ProjectCfgAddDTO projectCfgAddDTO) {
        projectCfgService.add(projectCfgAddDTO);
        return R.success();
    }

    @PostMapping("/delete")
    public R add(@RequestBody @Validated ProjectCfgDeleteDTO deleteDTO) {
        projectCfgService.delete(deleteDTO);
        return R.success();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated ProjectCfgUpdateDTO projectCfgUpdateDTO) {
        projectCfgService.update(projectCfgUpdateDTO);
        return R.success();
    }

    @PostMapping("/page")
    public R page(@RequestBody @Validated ProjectCfgPageDTO projectCfgPageDTO) {
        return R.page(projectCfgService.page(projectCfgPageDTO));
    }

    @PostMapping("/projectCfgOptions")
    public R elSelectOptions() {
        return R.successData(projectCfgService.elSelectOptions());
    }


}
