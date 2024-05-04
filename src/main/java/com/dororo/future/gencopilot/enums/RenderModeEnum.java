package com.dororo.future.gencopilot.enums;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum RenderModeEnum {
    TO_PROJECT(0, "直接渲染至项目目录"),
    DOWNLOAD(1, "渲染至临时目录然后打包下载"),
    ;
    public final Integer code;
    public final String name;

    public static List<Integer> codeList() {
        return Arrays.stream(RenderModeEnum.values()).map(RenderModeEnum::getCode).collect(Collectors.toList());
    }

    public static RenderModeEnum getByCode(Integer code) {
        for (RenderModeEnum anEnum : RenderModeEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

    public static RenderModeEnum getByName(String name) {
        for (RenderModeEnum anEnum : RenderModeEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        return null;
    }

    public static String tips() {
        String tip = Arrays.stream(RenderModeEnum.values())
                .map(anEnum -> StrUtil.format("{}={}", anEnum.getCode(), anEnum.getName()))
                .collect(Collectors.joining(",", "[", "]"));
        return tip;
    }


    public static List<JSONObject> toOptions() {
        List<JSONObject> options = Arrays.stream(RenderModeEnum.values())
                .map(anEnum -> JSONUtil.createObj().putOpt("value", anEnum.code).putOpt("label", anEnum.name))
                .collect(Collectors.toList());
        return options;
    }
}
