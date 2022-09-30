package com.mrk.amq.common;

import com.mrk.amq.common.annotation.MyJmsListener;

import java.lang.reflect.Method;

/**
 * @author baojun.jiang
 * @date 2022-09-30 14:24
 */
public abstract class AbstractMessageListenerProcessor {

    protected abstract void processMyJmsListener(MyJmsListener listener, Method method, Object bean);
}
