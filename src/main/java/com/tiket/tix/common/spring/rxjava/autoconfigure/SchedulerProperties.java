package com.tiket.tix.common.spring.rxjava.autoconfigure;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zakyalvan
 */
@Data
@Validated
@ConfigurationProperties(prefix = SchedulerProperties.PROPERTIES_PREFIX)
public class SchedulerProperties implements Serializable {
    public static final String PROPERTIES_PREFIX = "tiket.rxjava.support";

    @NestedConfigurationProperty
    private final Map<String, SchedulerSettings> schedulers = new HashMap<>();

    @Data
    public static class SchedulerSettings {
        @Min(1)
        @NotNull
        private Integer corePollSize = 100;

        @NotBlank
        private String threadNamePrefix = "CstmRxSchdlr";

        private boolean singletonBean = true;
    }
}
