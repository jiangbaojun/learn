package com.example.spring.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ClassLoaderUtil {

    /**
     * 获得url类加载器，和getClassLoader2是等效的
     * @return java.lang.ClassLoader
     * @date 2023/2/3 18:00
     */
    public static ClassLoader getClassLoader(String url) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            URLClassLoader classLoader = new URLClassLoader(new URL[]{}, ClassLoader.getSystemClassLoader());
            method.invoke(classLoader, new URL(url));
            return classLoader;
        } catch (Exception e) {
            log.error("getClassLoader-error", e);
            return null;
        }
    }

    public static ClassLoader getClassLoader2(String url) {
        try {
            URL[] urls = new URL[]{new URL(url)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            return classLoader;
        } catch (Exception e) {
            log.error("getClassLoader-error", e);
            return null;
        }
    }


    @Test
    public void tt() throws Exception {
        //rule源码打包好的jar（不包含依赖）
        String jar = "D:\\work\\myProject\\ideaPro\\learn\\rules\\target\\rules-1.0.0.RELEASE.jar";
        //包含了rule模块依赖的jar包
        String lib = "D:\\work\\myProject\\ideaPro\\learn\\rules\\target\\libs";
        JarFile jarFile = new JarFile(new File(jar));
        Enumeration<JarEntry> entry = jarFile.entries();
        //Lib文件+项目Jar
        URL url = new URL("file:" + jar);
        File file_directory = new File(lib);
        URL[] urls = new URL[file_directory.listFiles().length + 1];
        int i = 0;
        urls[i++] = url;
        for (File file : file_directory.listFiles()) {
            urls[i++] = new URL("file:" + file.getAbsolutePath());
        }
        ClassLoader loader = new URLClassLoader(urls);
        Class<?> c = null;
        Object o = null;
        while (entry.hasMoreElements()) {
            JarEntry jarEntry = entry.nextElement();
            String name = jarEntry.getName();
            System.out.println(name);
            if (name != null && name.endsWith(".class")) {
                if (name.endsWith("Demo1.class")) {
                    c = loader.loadClass(name.substring(0, name.length() - 6).replaceAll("/","."));
                    o = c.newInstance();
                }
            }
        }
        if (o != null) {
            System.out.println(o);
            System.out.println(c);
            Method method = c.getDeclaredMethod("test1");
            method.invoke(o);
        }
    }

    /**
     * 模拟加载rule模块的代码
     */
    @Test
    public void t2() throws Exception {
        //要注意此种方式，源码打好的jar包，必须包含依赖。如果不包含依赖，详见上面的tt方法
        ClassLoader classLoader = getClassLoader2("file:D:\\work\\myProject\\ideaPro\\learn\\rules\\target\\rules-1.0.0.RELEASE-jar-with-dependencies.jar");
        Class<?> aClass = classLoader.loadClass("com.example.qlexpress.Demo1");
        //测试
        Object o = aClass.newInstance();
        Method method = aClass.getDeclaredMethod("test1");
        method.invoke(o);
    }

    /**
     * 当前工程的class有默认类加载器加载，测试无意义
     */
    @Test
    public void t1() throws Exception {
//        ClassLoader classLoader = getClassLoader2("file:D:\\work\\myProject\\ideaPro\\learn\\spring-demo\\target\\classes\\com\\example\\spring\\entity\\User.class");
//        ClassLoader classLoader = getClassLoader2("file:D:\\work\\myProject\\ideaPro\\learn\\spring-demo\\target\\spring-demo-1.0.0.RELEASE.jar");
        ClassLoader classLoader = getClassLoader2("file:"+this.getClass().getResource("/"));
        Class<?> aClass = classLoader.loadClass("com.example.spring.entity.User");
        //测试
        Object o = aClass.newInstance();
        Method method = aClass.getDeclaredMethod("outName");
        method.invoke(o);
    }
}