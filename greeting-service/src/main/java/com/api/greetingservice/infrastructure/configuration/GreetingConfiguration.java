package com.api.greetingservice.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("greeting-service")
@Getter
@Setter
public class GreetingConfiguration {

    private String greeting;
    private String defaultValue;
}
