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
public enum YesNoEnum {
    NO(0, "NO"),
    YES(1, "YES"),
    ;
    public final Integer code;
    public final String name;

    public static List<Integer> codeList() {
        return Arrays.stream(YesNoEnum.values()).map(YesNoEnum::getCode).collect(Collectors.toList());
    }

    public static YesNoEnum getByCode(Integer code) {
        for (YesNoEnum anEnum : YesNoEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

    public static YesNoEnum getByName(String name) {
        for (YesNoEnum anEnum : YesNoEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        return null;
    }

    public static String tips() {
        String tip = Arrays.stream(YesNoEnum.values())
                .map(anEnum -> StrUtil.format("{}={}", anEnum.getCode(), anEnum.getName()))
                .collect(Collectors.joining(",", "[", "]"));
        return tip;
    }


    public static List<JSONObject> toOptions() {
        List<JSONObject> options = Arrays.stream(YesNoEnum.values())
                .map(anEnum -> JSONUtil.createObj().putOpt("value", anEnum.code).putOpt("label", anEnum.name))
                .collect(Collectors.toList());
        return options;
    }
}
