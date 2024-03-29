package com.br.bookservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Foo-bar endpoint")
@RestController
@RequestMapping(value = "/book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @Operation(summary = "return String foo-bar")
    @GetMapping(value = "/foo-bar")
    //  @Retry(name = "foo-bar",fallbackMethod = "fallbackMethod")
//    @CircuitBreaker(name = "default",fallbackMethod = "fallbackMethod")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String fooBar() {
        logger.info("Request to foo-bar is received!");
//        String restTemplate = new RestTemplate().getForEntity("http://localhost:8000/foo-bar", String.class).getBody();

        return "Foo-Bar";
        //  return restTemplate;
    }

    public String fallbackMethod(Exception ex) {
        return "FallbackMethod foo-bar";
    }
}
