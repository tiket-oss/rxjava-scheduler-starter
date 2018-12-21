package com.tiket.tix.common.spring.rxjava;

import com.tiket.tix.common.spring.rxjava.annotation.EnableRxJavaScheduler;
import io.reactivex.Scheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author zakyalvan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "tiket.rxjava.support.schedulers.first-sample.core-poll-size=100",
                "tiket.rxjava.support.schedulers.first-sample.thread-name-prefix=FirstSample",
                "tiket.rxjava.support.schedulers.second-sample.core-poll-size=100",
                "tiket.rxjava.support.schedulers.second-sample.thread-name-prefix=SecondSample"
        })
@RunWith(SpringRunner.class)
public class CustomSchedulerFactoryTests {
    @Autowired
    @Qualifier("first-sample")
    private Scheduler firstScheduler;

    @Autowired
    @Qualifier("second-sample")
    private Scheduler secondScheduler;

    @Autowired(required = false)
    @Qualifier("third-scheduler")
    private Scheduler thirdScheduler;

    @Test
    public void givenConfiguredScheduler_whenAutowiring_thenMustInjected() {
        assertThat(firstScheduler, notNullValue(Scheduler.class));
        assertThat(secondScheduler, notNullValue(Scheduler.class));
        assertThat(thirdScheduler, nullValue());
    }

    @EnableAutoConfiguration
    @EnableRxJavaScheduler
    @SpringBootConfiguration
    public static class AdditionalConfiguration {

    }
}
