package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.client;

import java.util.List;

/**
 * Created by Tom.
 */
public interface IPerson {

    List findLove(String condition, Integer num);

    String chooseOne(String name);
}
