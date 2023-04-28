package com.mrk.amq.common.annotation;

import com.mrk.amq.common.constant.MessageMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author baojun.jiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJmsListener {

    String id() default "";

    String destination();

    MessageMode mode() default MessageMode.POINT;

}
