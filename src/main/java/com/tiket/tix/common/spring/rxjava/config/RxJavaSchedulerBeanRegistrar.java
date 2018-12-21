package com.tiket.tix.common.spring.rxjava.config;

import com.tiket.tix.common.spring.rxjava.autoconfigure.SchedulerProperties;
import com.tiket.tix.common.spring.rxjava.factory.SchedulerFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.BindException;

/**
 * @author zakyalvan
 */
@Slf4j
public class RxJavaSchedulerBeanRegistrar implements ImportBeanDefinitionRegistrar, ApplicationContextAware, EnvironmentAware {
    private ApplicationContext applicationContext;

    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        if(!(environment instanceof ConfigurableEnvironment)) {
            log.error("Given environment object is not configurable environment");
            throw new BeanCreationException("Given environment object is not configurable environment");
        }
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanRegistry) {
        SchedulerProperties schedulerProperties = new SchedulerProperties();
        PropertiesConfigurationFactory<SchedulerProperties> configFactory = new PropertiesConfigurationFactory<>(schedulerProperties);
        //configFactory.setApplicationContext(applicationContext);
        configFactory.setTargetName(SchedulerProperties.PROPERTIES_PREFIX);

        MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        configFactory.setPropertySources(propertySources);

        try {
            configFactory.bindPropertiesToTarget();
        }
        catch (BindException e) {
            log.error("Can not bind scheduler properties");
            throw new BeanCreationException("Can not bind scheduler properties", e);
        }

        schedulerProperties.getSchedulers().forEach((beanName, schedulerSettings) -> {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SchedulerFactoryBean.class)
                    .addConstructorArgValue(schedulerSettings.getCorePollSize())
                    .addConstructorArgValue(schedulerSettings.getThreadNamePrefix())
                    .addConstructorArgValue(true)
                    .getBeanDefinition();
            beanRegistry.registerBeanDefinition(beanName, beanDefinition);
        });
    }
}
