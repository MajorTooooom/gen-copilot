package com.dororo.future.gencopilot.controller;

import cn.hutool.core.util.StrUtil;
import com.dororo.future.gencopilot.dto.*;
import com.dororo.future.gencopilot.dto.common.R;
import com.dororo.future.gencopilot.service.TableCfgService;
import com.dororo.future.gencopilot.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tableCfg")
public class TableCfgController {
    @Autowired
    private TableCfgService tableCfgService;

    @PostMapping("/add")
    public R add(@RequestBody @Validated TableCfgAddDTO addDTO) {
        tableCfgService.add(addDTO);
        return R.success();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated TableCfgUpdateDTO updateDTO) {
        tableCfgService.update(updateDTO);
        return R.success();
    }

    @PostMapping("/page")
    public R page(@RequestBody @Validated TableCfgPageDTO tableCfgPageDTO) {
        return R.page(tableCfgService.page(tableCfgPageDTO));
    }

    /**
     * 某数据源下全部表和字段信息
     *
     * @param cfgTablePoolDTO 数据源信息
     * @param forced          是否从缓存中强制刷新,true:强制刷新查询,false:从缓存中获取
     */
    @PostMapping("/tablePoolOptions")
    public R tablePoolOptions(@RequestBody @Validated ConnCfgTablePoolDTO cfgTablePoolDTO, @RequestParam(name = "forced", required = false) Boolean forced) {
        forced = forced == null ? false : forced;
        // 计算缓存KEY
        String sameRoot = StrUtil.format("[{}][{}][{}]", cfgTablePoolDTO.getUrl(), cfgTablePoolDTO.getUsername(), cfgTablePoolDTO.getPassword());
        String cacheKey = IdUtils.getFixedLengthId(sameRoot);
        if (forced) {
            return tableCfgService.cachePutTablePoolOptions(cacheKey, cfgTablePoolDTO);
        } else {
            return tableCfgService.cacheableTablePoolOptions(cacheKey, cfgTablePoolDTO);
        }
    }

    @PostMapping("/dbTableAndColumn")
    public R getDbTableAndColumn(@RequestBody @Validated DbTableAndColumnReqDTO dbTableAndColumnReqDTO) {
        return R.successData(tableCfgService.getDbTableAndColumn(dbTableAndColumnReqDTO));
    }

    @GetMapping("/getColumnCfgsByTableCfgId")
    public R getColumnCfgsByTableCfgId(@RequestParam("id") Integer tableCfgId) {
        return R.successData(tableCfgService.getColumnCfgsByTableCfgId(tableCfgId));
    }

    @PostMapping("/delete")
    public R delete(@RequestBody @Validated TableCfgDeleteDTO deleteDTO) {
        tableCfgService.delete(deleteDTO);
        return R.success();
    }
}
