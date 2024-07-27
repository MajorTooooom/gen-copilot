package com.dororo.future.gencopilot.vo;

import com.dororo.future.gencopilot.domain.TemplateCfg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenderResVo {
    private List<TemplateCfg> list;
    private Integer successCount;
    private Integer failCount;
    // 临时目录名
    private String tempDirName;
}
