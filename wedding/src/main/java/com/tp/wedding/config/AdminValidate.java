package com.tp.wedding.config;

import com.tp.wedding.common.Role;

import java.lang.annotation.*;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminValidate {

    Role[] roles();

}
