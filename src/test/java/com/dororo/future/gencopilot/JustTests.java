package com.dororo.future.gencopilot;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JustTests {

    @Test
    public void test() {
        JSONArray arrayA = JSONUtil.readJSONArray(FileUtil.file("D:\\gust\\dev\\project\\github\\gen-copilot\\src\\main\\resources\\mapperJavaTypeConfig.json"), Charset.forName("UTF-8"));
        JSONArray arrayB = JSONUtil.readJSONArray(FileUtil.file("D:\\gust\\dev\\project\\github\\gen-copilot\\src\\main\\resources\\mapperJdbcTypeConfig.json"), Charset.forName("UTF-8"));

        List<Map> listA = JSONUtil.toList(arrayA, Map.class);
        List<Map> listB = JSONUtil.toList(arrayB, Map.class);

        Set<String> keySetA = listA.stream().map(o -> MapUtil.getStr(o, "columnType")).collect(Collectors.toSet());
        Set<String> keySetB = listB.stream().map(o -> MapUtil.getStr(o, "columnType")).collect(Collectors.toSet());
        keySetA.addAll(keySetB);

        Set<String> keySet = new HashSet<>();
        keySet.addAll(keySetA);
        keySet.addAll(keySetB);

        List<Map<Object, Object>> collect = keySet.stream().map(key -> {
            String typeA = listA.stream().filter(o -> key.equals(MapUtil.getStr(o, "columnType"))).map(o -> MapUtil.getStr(o, "javaType")).findFirst().orElse(null);
            String typeB = listB.stream().filter(o -> key.equals(MapUtil.getStr(o, "columnType"))).map(o -> MapUtil.getStr(o, "jdbcType")).findFirst().orElse(null);
            return MapUtil.builder().put("columnType", key).put("javaType", typeA).put("jdbcType", typeB).build();
        }).collect(Collectors.toList());


        // 按照KEY排序
        collect = collect.stream().sorted((o1, o2) -> {
            String key1 = MapUtil.getStr(o1, "columnType");
            String key2 = MapUtil.getStr(o2, "columnType");
            return key1.compareTo(key2);
        }).collect(Collectors.toList());


        System.out.println(JSONUtil.toJsonStr(collect));
    }

    @Test
    public void test02() {
        // Original timestamp
        extracted("2024-10-16T09:54:21.762923874+08:00");
        extracted("2024-10-16T09:54:21.716886911+08:00");
        extracted("2024-10-16T09:54:21.684770944+08:00");
    }

    private static void extracted(String isoTimestamp) {
        // Parse the ISO 8601 string into a DateTime object
        DateTime dateTime = DateUtil.parse(isoTimestamp);

        // Format the DateTime object into the desired format
        String formattedDate = dateTime.toString("yyyy-MM-dd HH:mm:ss");

        // Display the result
        System.out.println("Formatted Date: " + formattedDate);
    }
}