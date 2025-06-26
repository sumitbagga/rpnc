package com.capgemini.rpnc;

import com.capgemini.rpnc.services.ReversePolishNotationCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            Path filePath = resolveFilePath(args[0]);
            List<String> lines = Files.readAllLines(filePath);
            ReversePolishNotationCalculatorService service = new ReversePolishNotationCalculatorService();
            for (String line : lines) {
                log.info("{} = {}", line, service.calculate(line));
            }
        } catch (Exception e) {
            log.error("Error reading input: {}" , e.getMessage());
        }
    }

    private Path resolveFilePath(String fileName) {
        Path localPath = Paths.get(fileName);
        if (Files.exists(localPath)) {
            return localPath;
        }

        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource != null) {
            try {
                return Paths.get(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid URI for file: " + fileName, e);
            }
        }

        throw new IllegalArgumentException("File not found: " + fileName);
    }
}

