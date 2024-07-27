package com.dororo.future.gencopilot.vo;

import cn.hutool.json.JSONObject;
import com.dororo.future.gencopilot.domain.TemplateCfg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UniteExecutiveOptionsResVo {
    private List<JSONObject> tableCfgOptions;
    private List<JSONObject> projectCfgOptions;
    private List<TemplateCfg> templateTableData;
}
