package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @Author: Octoopus
 * @Date: 2020/5/30
 */
public class OctopusClassLoader extends ClassLoader {

    private File classPathFile;

    public OctopusClassLoader() throws Exception {
        String classpath = OctopusClassLoader.class.getResource("").getPath();
        classpath = URLDecoder.decode(classpath,"utf-8");
        this.classPathFile = new File(classpath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = OctopusClassLoader.class.getPackage().getName() + "." +name;
        if (classPathFile != null){
            File classFile = new File(classPathFile,name.replaceAll("\\.","/")+".class");
            if (classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try{
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte [] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1){
                        out.write(buff,0,len);
                    }
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
        return null;
}


}
