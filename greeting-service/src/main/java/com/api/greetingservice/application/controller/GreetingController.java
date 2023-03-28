package com.api.greetingservice.application.controller;

import com.api.greetingservice.domain.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GreetingController {

    @GetMapping
    Greeting greeting(@RequestParam(value = "name", defaultValue = "") String nome);
}
