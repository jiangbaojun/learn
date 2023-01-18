package com.mrk.amq.common.util;

import com.alibaba.fastjson.JSON;
import com.mrk.amq.properties.DataChange;
import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.annotation.MyJmsListeners;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 消息注解工具
 * @author baojun.jiang
 * @date 2022-09-30 10:12
 */
public class MessageAnnotationUtil {

    /**
     * 解析bean中消息注解
     * @return java.util.Map<java.lang.reflect.Method,java.util.Set<com.mrk.amq.common.annotation.MyJmsListener>>
     * @date 2022/9/30 10:15
     */
    public static Map<Method, Set<MyJmsListener>> parse(Object bean) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        return MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<Set<MyJmsListener>>) method -> {
                    Set<MyJmsListener> listenerMethods = AnnotatedElementUtils.getMergedRepeatableAnnotations(
                            method, MyJmsListener.class, MyJmsListeners.class);
                    return (!listenerMethods.isEmpty() ? listenerMethods : null);
                });
    }

    /**
     * 回调监听方法
     * @param bean 实例
     * @param method 方法
     * @param msg 消息
     * @date 2022/9/30 15:50
     */
    public static void invokeListen(Object bean, Method method, String msg) throws BeansException {
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length==1){
                Class<?> type = parameterTypes[0];
                DataChange dataChange = JSON.parseObject(msg, DataChange.class);
                Object param;
                if(String.class.isAssignableFrom(type)){
                    param = JSON.toJSONString(dataChange.getData());
                }else{
                    param = JSON.parseObject(JSON.toJSONString(dataChange.getData()), type);
                }
                method.invoke(bean, param);
            }else{
                System.out.println("无法回调");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
