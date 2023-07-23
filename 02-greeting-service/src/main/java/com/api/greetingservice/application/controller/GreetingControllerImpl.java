package com.api.greetingservice.application.controller;

import com.api.greetingservice.domain.model.Greeting;
import com.api.greetingservice.infrastructure.configuration.GreetingConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor
public class GreetingControllerImpl implements GreetingController {

    private final AtomicLong counter = new AtomicLong();
    private static final String TEMPLATE = "%s %s!";
    private final GreetingConfiguration configuration;

    @Override
    public Greeting greeting(String nome) {
        if (nome.isEmpty()) nome = configuration.getDefaultValue();

        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, configuration.getGreeting(), nome));
    }
}
