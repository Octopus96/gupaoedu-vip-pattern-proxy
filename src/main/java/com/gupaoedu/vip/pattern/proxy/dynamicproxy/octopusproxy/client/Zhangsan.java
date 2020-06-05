package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom.
 */
public class Zhangsan implements IPerson {

    @Override
    public List findLove(String condition, Integer num) {
        System.out.println(condition);
        System.out.println("候选人个数：" + num);
        List<String> candidate = new ArrayList<>();
        candidate.add("小白");
        candidate.add("小红");
        candidate.add("小黑");
        return candidate;
    }

    @Override
    public String chooseOne(String name) {
        return name;
    }

}
