package com.zibengou.awful.backend.aop;

import com.zibengou.awful.backend.types.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {
    RoleEnum[] roles() default {};

    String match() default "";
}
