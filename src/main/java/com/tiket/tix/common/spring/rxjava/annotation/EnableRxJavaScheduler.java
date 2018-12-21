package com.tiket.tix.common.spring.rxjava.annotation;

import com.tiket.tix.common.spring.rxjava.config.RxJavaSchedulerBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable RxJava custom scheduler. Annotate your configuration class with this to enable custom
 * RxJava scheduler bean based on configuration.
 *
 * @author zakyalvan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RxJavaSchedulerBeanRegistrar.class)
@Inherited
@Documented
public @interface EnableRxJavaScheduler {
}
