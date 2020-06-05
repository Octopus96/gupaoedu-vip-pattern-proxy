package com.gupaoedu.vip.pattern.proxy.dynamicproxy.octopusproxy.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Octoopus
 * @Date: 2020/5/30
 */
public class OctopusProxy {
    public static final String ln = "\r\n";

    public static Object newProxyInstance(OctopusClassLoader classLoader, Class<?>[] interfaces, OctopusInvocationHandler h) throws Exception {

        String src = generateSrc(interfaces);
        String filePath = OctopusProxy.class.getResource("").getPath();
        filePath = URLDecoder.decode(filePath,"utf-8");
        File file = new File(filePath + "$Proxy0.java");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(src);
        fileWriter.flush();
        fileWriter.close();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        manager.close();
        Class proxyclass = classLoader.findClass("$Proxy0");
        System.out.println(proxyclass);
        Constructor c = proxyclass.getConstructor(OctopusInvocationHandler.class);
        file.delete();
        return c.newInstance(h);
    }

    public static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append(OctopusProxy.class.getPackage() + ";" + ln);
        sb.append("import " + interfaces[0].getName() + ";" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public final class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        sb.append("OctopusInvocationHandler h;" + ln);
        sb.append("public $Proxy0(OctopusInvocationHandler h) { " + ln);
        sb.append("this.h = h;");
        sb.append("}" + ln);
        for (Method m : interfaces[0].getMethods()) {
            Class<?>[] params = m.getParameterTypes();

            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();

            for (int i = 0; i < params.length; i++) {
                Class clazz = params[i];
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName());
                paramNames.append(type + " " + paramName);
                paramValues.append(paramName);
                paramClasses.append(clazz.getName() + ".class");
                if (i < params.length - 1) {
                    paramNames.append(",");
                    paramClasses.append(",");
                    paramValues.append(",");
                }
            }

            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames + ") {" + ln);
                sb.append("try{" + ln);
                    sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\", new Class[]{" + paramClasses.toString() + "});" + ln);
                    if(m.getReturnType() != void.class){
                        sb.append("return ");
                        sb.append("(" + m.getReturnType().getName() + ")");
                        sb.append("(this.h.invoke(this,m,new Object[]{" + paramValues + "}));").append(ln);
                    }else{
                        sb.append("this.h.invoke(this,m,new Object[]{" + paramValues + "});").append(ln);
                    }
            sb.append("}catch(Error _ex) { }").append(ln);
            sb.append("catch(Throwable e){" + ln);
            sb.append("throw new UndeclaredThrowableException(e);" + ln);
            sb.append("}").append(ln);
            if(m.getReturnType() != void.class){
                sb.append("return null;");
            }
            sb.append("}");
        }
        sb.append("}" + ln);
        return sb.toString();
    }
    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src){
        char [] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}