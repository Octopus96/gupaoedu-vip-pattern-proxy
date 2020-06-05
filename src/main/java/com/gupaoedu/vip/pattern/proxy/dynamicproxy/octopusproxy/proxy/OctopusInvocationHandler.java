package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy;

import java.lang.reflect.Method;

/**
 * @Author: Octoopus
 * @Date: 2020/5/30
 */
public interface OctopusInvocationHandler {
    public Object invoke(Object proxy, Method method,Object[] args)throws Throwable;
}
