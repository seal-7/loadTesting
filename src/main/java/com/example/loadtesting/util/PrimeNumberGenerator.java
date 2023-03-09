package com.example.loadtesting.util;

import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class PrimeNumberGenerator {
    RestTemplate restTemplate = new RestTemplate();
    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int getRandomPrimeNumber(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        while (!isPrime(randomNumber)) {
            randomNumber = random.nextInt((max - min) + 1) + min;
        }
        return randomNumber;
    }
}