package com.dororo.future.gencopilot.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DelTmpJob {

    /**
     * 每间隔N分钟执行一次：删除N分钟之前的临时目录和zip文件
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    // @Scheduled(cron = "1/5 * * * * ?")// test
    public void delTmpDir() {
        File tmpTop = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", "tmp");
        if (!(FileUtil.isDirectory(tmpTop) && FileUtil.exist(tmpTop))) {
            return;
        }
        for (File file : FileUtil.ls(FileUtil.getAbsolutePath(tmpTop))) {
            try {
                String mainName = FileUtil.mainName(file);
                if (!ReUtil.isMatch("^[0-9]{17}$", mainName)) {
                    continue;
                }
                DateTime dateTime = DateUtil.parse(mainName, "yyyyMMddHHmmssSSS");
                if (DateUtil.between(dateTime, DateUtil.date(), DateUnit.SECOND, false) > 5 * 60) {
                    FileUtil.del(file);
                    log.info("删除过期目录或zip:[{}]", file.getAbsolutePath());
                }
            } catch (Exception e) {
                log.error("Delete temporary directory error: {}", file.getAbsolutePath(), e);
            }
        }
    }
}
