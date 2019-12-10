 package com.longge.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.longge.common.configuration.OkHttpUtilsConfiguration;

/**
 * @author roger yang
 * @date 9/16/2019
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OkHttpUtilsConfiguration.class)
public @interface EnableOkHttpUtilsAutoConfiguration {

}
