package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.client;


import java.util.List;

/**
 * Created by Tom.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Zhangsan zhangsan = new Zhangsan();
        OctopusMeipo meipo = new OctopusMeipo();
        IPerson instance = meipo.getInstance(zhangsan);
        instance.findLove("我的条件：温柔可人", 5);
        zhangsan.chooseOne("小白");


//        IPerson zhaoliu = jdkMeipo.getInstance(new ZhaoLiu());
//        zhaoliu.findLove();
//        zhaoliu.buyInsure();


    }
}
