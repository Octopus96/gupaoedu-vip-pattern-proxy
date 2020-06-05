package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.client;

import com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy.OctopusClassLoader;
import com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy.OctopusInvocationHandler;
import com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy.OctopusProxy;

import java.lang.reflect.Method;

/**
 * @Author: Octoopus
 * @Date: 2020/5/30
 */
public class OctopusMeipo implements OctopusInvocationHandler {

    private IPerson target;

    public IPerson getInstance(IPerson target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (IPerson) OctopusProxy.newProxyInstance(new OctopusClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(this.target,args);
        after();
        return result;
    }

    private void after() {
        System.out.println("双方同意，开始交往");
    }

    private void before() {
        System.out.println("我是媒婆，已经收集到你的需求，开始物色");
    }
}
