package com.tiket.tix.common.spring.rxjava.autoconfigure;

import com.tiket.tix.common.spring.rxjava.annotation.EnableRxJavaScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration} to create custom
 * RxJava scheduler.
 *
 * @author zakyalvan
 */
@Configuration
@ConditionalOnClass(RxJavaPlugins.class)
@AutoConfigureAfter(ValidationAutoConfiguration.class)
public class RxJavaSchedulerAutoConfiguration {
    @Configuration
    @EnableRxJavaScheduler
    public static class EnableCustomSchedulerConfiguration {

    }
}
