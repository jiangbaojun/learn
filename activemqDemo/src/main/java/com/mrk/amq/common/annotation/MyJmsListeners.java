package com.mrk.amq.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJmsListeners {

	MyJmsListener[] value();

}