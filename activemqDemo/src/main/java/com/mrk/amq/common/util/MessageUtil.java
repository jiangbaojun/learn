package com.mrk.amq.common.util;

import com.alibaba.fastjson.JSON;
import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.annotation.MyJmsListeners;
import com.mrk.amq.common.constant.MessageConstant;
import com.mrk.amq.properties.DataChange;
import com.mrk.amq.properties.FromModel;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 消息工具
 * @author baojun.jiang
 * @date 2022-09-30 10:12
 */
public class MessageUtil {

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
    public static void invokeListen(Object bean, Method method, String msg) throws BeansException, InvocationTargetException, IllegalAccessException {
        Object[] parameterValue = getParameterValue(method, msg);
        if(parameterValue==null){
            method.invoke(bean);
        }else{
            method.invoke(bean, parameterValue);
        }
    }

    /**
     * 获得参数。
     * 填充参数值，第一个默认为床底的参数，第二个以后的参数自动填充。
     * 目前只识别FromModel类型，其余默认空值
     * @return
     */
    private static Object[] getParameterValue(Method method,String msg) {
        int paramCount = method.getParameterCount();
        if(paramCount==0){
            return null;
        }
        DataChange dataChange = JSON.parseObject(msg, DataChange.class);
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramCount];
        for(int i=0;i<paramCount;i++){
            Class<?> type = paramTypes[i];
            if(i==0){
                //第一个参数是传递参数，特殊解析
                params[i] = getFirstParam(type, dataChange);
                continue;
            }
            if(FromModel.class.getName().equals(type.getName())){
                FromModel fromModel = new FromModel();
                fromModel.setStudyId(dataChange.getStudyId());
                fromModel.setSponsorId(dataChange.getSponsorId());
                fromModel.setSystemId(dataChange.getSystemId());
                params[i] = fromModel;
                continue;
            }
            if("boolean".equals(type.getName())){
                params[i] = false;
            }else if("char".equals(type.getName())){
                params[i] = '\u0000';
            }else if("byte".equals(type.getName())){
                params[i] = 0;
            }else if("short".equals(type.getName())){
                params[i] = 0;
            }else if("int".equals(type.getName())){
                params[i] = 0;
            }else if("long".equals(type.getName())){
                params[i] = 0L;
            }else if("float".equals(type.getName())){
                params[i] = 0.0f;
            }else if("double".equals(type.getName())){
                params[i] = 0.0d;
            }else{
                params[i] = null;
            }
        }
        return params;
    }

    /**
     * 解析传递参数，默认第一个参数
     * @return java.lang.Object
     * @date 2023/5/4 13:43
     */
    private static Object getFirstParam(Class<?> type, DataChange dataChange) {
        if(String.class.isAssignableFrom(type)){
            return JSON.toJSONString(dataChange.getData());
        }else{
            return JSON.parseObject(JSON.toJSONString(dataChange.getData()), type);
        }
    }

    /**
     * 队列统一前缀
     * @return java.lang.String
     * @date 2023/4/28 17:23
     */
    public static String getPrefix() {
        return MessageConstant.COMMON_PREFIX+".";
    }


}
