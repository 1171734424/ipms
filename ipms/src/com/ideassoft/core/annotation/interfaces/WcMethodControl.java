package com.ideassoft.core.annotation.interfaces;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WcMethodControl {
    //String rightType();
}
