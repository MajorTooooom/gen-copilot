package com.dororo.future.gencopilot.controller;

import com.dororo.future.gencopilot.dto.TemplateCfgBatchDeleteReqDTO;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.service.TemplateCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/templateCfg")
public class TemplateCfgController {
    @Autowired
    private TemplateCfgService templateCfgService;

    @PostMapping("/page")
    public R page() {
        return R.successData(templateCfgService.page());
    }

    @PostMapping("/upload")
    public R upload(@RequestParam(value = "file", required = true) MultipartFile[] files,
                    @RequestParam("prefix") String prefix,
                    @RequestParam("suffix") String suffix,
                    HttpServletResponse response) {
        return R.success(templateCfgService.upload(files));
    }

    @GetMapping("/download/{userId}/{name}")
    public void download(@PathVariable("userId") String userId, @PathVariable("name") String name, HttpServletResponse response) {
        templateCfgService.download(userId, name, response);
    }

    @PostMapping("/batchDelete")
    public R batchDelete(@RequestBody @Validated TemplateCfgBatchDeleteReqDTO reqDTO) {
        templateCfgService.batchDelete(reqDTO);
        return R.success();
    }

}
