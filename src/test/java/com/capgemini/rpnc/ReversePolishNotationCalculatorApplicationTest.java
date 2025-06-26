package com.capgemini.rpnc;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReversePolishNotationCalculatorApplicationTest {

    private ReversePolishNotationCalculatorApplication app;
    private LogCaptor logCaptor;

    @BeforeEach
    void setUp() {
        app = new ReversePolishNotationCalculatorApplication();
        logCaptor = LogCaptor.forClass(ReversePolishNotationCalculatorApplication.class);
    }

    @AfterEach
    void tearDown() {
        logCaptor.clearLogs();
    }

    @Test
    void shouldLogInfoForValidFile() {
        app.run("input.txt"); // make sure this file exists in test resources

        List<String> logs = logCaptor.getInfoLogs();
        assertThat(logs).anyMatch(log -> log.contains("1.0 2.0 + = 3.0"));
    }

    @Test
    void shouldLogWarningWhenNoArgs() {
        app.run(); // no args

        List<String> warnLogs = logCaptor.getWarnLogs();
        assertThat(warnLogs).anyMatch(log -> log.contains("No input file specified"));
    }

    @Test
    void shouldLogErrorWhenInvalidFile() {
        app.run("invalid.txt");

        List<String> errorLogs = logCaptor.getErrorLogs();
        assertThat(errorLogs).anyMatch(log -> log.contains("File not found"));
    }
}
