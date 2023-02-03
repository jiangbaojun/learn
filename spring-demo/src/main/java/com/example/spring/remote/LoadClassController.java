package com.example.spring.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RestController
public class LoadClassController {

    @Autowired
    private SpringUtil springUtil;

    /**
     * 模拟加载类
     * @return java.lang.Object
     * @date 2023/2/3 17:58
     */
    @GetMapping("/load")
    public Object reload() throws Exception {
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader(TestConstant.classLoaderPath);
        Class<?> clazz = classLoader.loadClass(TestConstant.classPath);
        springUtil.registerBean(clazz.getName(), clazz);
        //测试调用
        doTestLoad(clazz.getName());
        return "ok";
    }

    @GetMapping("/testLoad")
    public void testLoad() throws Exception {
        doTestLoad(TestConstant.classPath);
    }

    private void doTestLoad(String name) throws Exception {
        //测试调用
        Object o = springUtil.getBean(name);
        Class<?> aClass = o.getClass();
        Method method = aClass.getDeclaredMethod("test1");
        method.invoke(o);
    }

}
