package com.works.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionAPI {

    String apiNameKorVer() default "";

    String description() default "";

    String response() default "";

    int apiCode() default 0;
}
