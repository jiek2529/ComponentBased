package com.jiek.jiek_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 声明作用域，放在哪个用
@Retention(RetentionPolicy.CLASS) // 声明生命周期
public @interface Route {
    String value();
}
