package com.example.loadtesting.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HealthChecker {

    private final RestTemplate restTemplate;
    private final String endpointUrl;
    private final Logger log = LoggerFactory.getLogger(HealthChecker.class);

    private WebClient webClient = WebClient.create("http://localhost:8999");

    public HealthChecker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.endpointUrl = "http://localhost:8999";
    }


    @CircuitBreaker(name = "checkHealth",fallbackMethod = "checkHealthFallback")
    public String checkHealth() {
        return restTemplate.getForObject(endpointUrl + "/health", String.class);
    }

    @CircuitBreaker(name = "checkHealth1", fallbackMethod = "checkHealth1Fallback")
    public Mono<String> checkHealth1() {
        return webClient.get().uri("/health")
                .retrieve()
                .bodyToMono(String.class);
    }

    public String checkHealthFallback(Exception e) {
        log.error("Serving from checkHealthFallback");
        return "checkHealthFallback";
    }

    public Mono<String> checkHealth1Fallback(Exception e) {
        log.error("Serving from checkHealth1Fallback");
        return Mono.just("checkHealth1Fallback");
    }
}

