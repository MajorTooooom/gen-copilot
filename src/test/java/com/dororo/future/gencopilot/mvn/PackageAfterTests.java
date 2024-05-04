package com.dororo.future.gencopilot.mvn;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class PackageAfterTests {
    /**
     * 打包后的工作,拷贝文件到docker-compose工作目录
     */
    public static void main(String[] args) {
        enterLog();
        copy();
        quitLog();
    }

    private static void copy() {
        // 当前项目的根目录
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        // 拷贝数据库文件
        AtomicReference<File> dbFile = new AtomicReference<>(FileUtil.file(currentDir, "attachments", "db", "gen-copilot.db"));
        if (!(FileUtil.exist(dbFile.get()) && FileUtil.isFile(dbFile.get()))) {
            // 在目录下找最新的zip文件解压缩
            Arrays.stream(FileUtil.ls(FileUtil.getAbsolutePath(FileUtil.getParent(dbFile.get(), 1)))).filter(f -> {
                boolean zip = StrUtil.isNotBlank(FileUtil.extName(f)) && StrUtil.equalsIgnoreCase("zip", FileUtil.extName(f));
                String mainName = FileUtil.mainName(f);
                boolean isDb = StrUtil.startWith(mainName, "gencopilot_");
                return zip && isDb;
            }).max((zip1, zip2) -> {
                String mainName1 = FileUtil.mainName(zip1);
                String mainName2 = FileUtil.mainName(zip2);
                String date1 = StrUtil.subAfter(mainName1, "_", false);
                String date2 = StrUtil.subAfter(mainName2, "_", false);
                DateTime dateTime1 = DateUtil.parse(date1, "yyyyMMdd_HHmmss");
                DateTime dateTime2 = DateUtil.parse(date2, "yyyyMMdd_HHmmss");
                return dateTime1.compareTo(dateTime2);
            }).ifPresent(zip -> {
                try {
                    new ZipFile(zip).extractAll(FileUtil.getAbsolutePath(FileUtil.getParent(zip, 1)));
                } catch (ZipException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Assert.isTrue(FileUtil.exist(dbFile.get()) && FileUtil.isFile(dbFile.get()), "数据库文件不存在");
        File dbDest = FileUtil.file(currentDir, "attachments", ".docker", "attachments", "db", "gen-copilot.db");
        FileUtil.copy(dbFile.get(), dbDest, true);
        // 拷贝模板文件
        File templateFrom = FileUtil.file(currentDir, "attachments", "template", "user6");
        File templateTo = FileUtil.file(currentDir, "attachments", ".docker", "attachments", "template", "user6");
        FileUtil.copyContent(templateFrom, templateTo, true);
        // 拷贝JAR包
        File jarFile = FileUtil.file(currentDir, "target", "gen-copilot.jar");
        Assert.isTrue(FileUtil.exist(jarFile) && FileUtil.isFile(jarFile), "JAR包不存在");
        File jarDist = FileUtil.file(currentDir, "attachments", ".docker", "build_image_springboot", "gen-copilot.jar");
        FileUtil.copy(jarFile, jarDist, true);
    }

    private static void enterLog() {
        System.out.println("====================================================================================================");
        System.out.println("开始执行打包后的工作");
        System.out.flush(); // 强制刷新缓冲
    }

    private static void quitLog() {
        System.out.println("打包后的工作执行完毕");
        System.out.println("====================================================================================================");
        System.out.flush(); // 强制刷新缓冲
    }
}
