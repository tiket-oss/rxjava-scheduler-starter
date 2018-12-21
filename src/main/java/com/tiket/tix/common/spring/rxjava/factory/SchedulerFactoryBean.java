package com.tiket.tix.common.spring.rxjava.factory;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * {@link FactoryBean} for custom RxJava's scheduler.
 *
 * @author zakyalvan
 */
public class SchedulerFactoryBean implements FactoryBean<Scheduler>, DisposableBean {
    private final Integer corePollSize;
    private final String threadNamePrefix;
    private final boolean singletonBean;

    private ScheduledExecutorService executorService;

    public SchedulerFactoryBean(Integer corePollSize, String threadNamePrefix, boolean singletonBean) {
        this.corePollSize = corePollSize;
        this.threadNamePrefix = threadNamePrefix;
        this.singletonBean = singletonBean;
    }

    @Override
    public Scheduler getObject() throws Exception {
        executorService = Executors.newScheduledThreadPool(corePollSize, new RxThreadFactory(threadNamePrefix));
        return Schedulers.from(executorService);
    }

    @Override
    public Class<?> getObjectType() {
        return Scheduler.class;
    }

    @Override
    public boolean isSingleton() {
        return singletonBean;
    }

    @Override
    public void destroy() throws Exception {
        if(executorService != null) {
            executorService.shutdown();
        }
    }
}