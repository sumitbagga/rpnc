package com.capgemini.rpnc;

import com.capgemini.rpnc.services.ReversePolishNotationCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SpringBootApplication
@Slf4j
public class ReversePolishNotationCalculatorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReversePolishNotationCalculatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args == null || args.length != 1) {
            log.warn("No input file specified. Skipping CLI execution.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(getResourceURI(args[0])));
            ReversePolishNotationCalculatorService service = new ReversePolishNotationCalculatorService();
            for (String line : lines) {
                log.info("{} = {}", line, service.calculate(line));
            }
        } catch (Exception e) {
            log.error("Error reading input: {}" , e.getMessage());
        }
    }

    public static URI getResourceURI(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try {
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for file: " + fileName, e);
        }
    }
}

