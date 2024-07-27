package com.dororo.future.gencopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请输入类描述
 *
 * @author Dororo
 * @date 2024-05-15 16:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutiveCfg {
    private Integer id;

    private Integer userId;

    private String cfgData;

    private String createTime;

    private String updateTime;
}