package com.example.loadtesting.controller;

import com.example.loadtesting.service.HealthChecker;
import com.example.loadtesting.util.PrimeNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class PrimeNumberController {

    @Autowired
    HealthChecker healthChecker;

    @GetMapping("/api/random-prime")
    public ResponseEntity<String> getRandomPrime() {
        int randomPrime = PrimeNumberGenerator.getRandomPrimeNumber(1,1000000);
        String resp = healthChecker.checkHealth();
        return new ResponseEntity<>(String.format("%d %s", randomPrime, resp), HttpStatus.OK);
    }

    @GetMapping("/nio/api/random-prime")
    public Mono<String> getRandomPrimeNio() {
        int randomPrime = PrimeNumberGenerator.getRandomPrimeNumber(1,1000000);
        return healthChecker.checkHealth1();
    }
}

