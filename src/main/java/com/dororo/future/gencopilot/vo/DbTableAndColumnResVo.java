package com.dororo.future.gencopilot.vo;

import com.dororo.future.gencopilot.domain.ColumnCfg;
import com.dororo.future.gencopilot.domain.TableCfg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DbTableAndColumnResVo extends TableCfg {
    private List<ColumnCfg> columnCfgs;
}
