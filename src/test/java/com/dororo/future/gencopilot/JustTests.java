package com.dororo.future.gencopilot;

import cn.hutool.json.JSONUtil;
import com.dororo.future.gencopilot.util.FmUtils;
import org.junit.jupiter.api.Test;

public class JustTests {

    @Test
    public void test() {
        String string = FmUtils.stringToString("${a}", JSONUtil.createObj().putOpt("a", "b"));
        System.out.println(string);
    }
}
