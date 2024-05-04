package com.dororo.future.gencopilot.controller;

import com.dororo.future.gencopilot.dto.ConnCfgAddDTO;
import com.dororo.future.gencopilot.dto.ConnCfgDeleteDTO;
import com.dororo.future.gencopilot.dto.ConnCfgPageDTO;
import com.dororo.future.gencopilot.dto.common.ConnCfgUpdateDTO;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.service.ConnCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connCfg")
public class ConnCfgController {
    @Autowired
    private ConnCfgService connCfgService;

    @PostMapping("/add")
    public R add(@RequestBody @Validated ConnCfgAddDTO connCfgAddDTO) {
        connCfgService.add(connCfgAddDTO);
        return R.success();
    }

    @PostMapping("/deleteRows")
    public R deleteRows(@RequestBody ConnCfgDeleteDTO connCfgDeleteDTO) {
        connCfgService.deleteRows(connCfgDeleteDTO);
        return R.success();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated ConnCfgUpdateDTO connCfgUpdateDTO) {
        connCfgService.update(connCfgUpdateDTO);
        return R.success();
    }

    @PostMapping("/page")
    public R page(@RequestBody @Validated ConnCfgPageDTO connCfgPageDTO) {
        return R.page(connCfgService.page(connCfgPageDTO));
    }

    @PostMapping("/connCfgOptions")
    public R elSelectOptions() {
        return R.successData(connCfgService.elSelectOptions());
    }

}
