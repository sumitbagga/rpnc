package com.capgemini.rpnc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RpncApplicationTests {

	@Autowired
	RpncApplication rpncApplication;

	@ParameterizedTest
	@MethodSource("equationsAndExpectedOutput")
	void testCalculate(String rpnEquation, String expectedOutput) {
		String actualOutput = rpncApplication.calculate(rpnEquation);
		assertEquals(expectedOutput, actualOutput);
	}



	private static Stream<Arguments> equationsAndExpectedOutput() {
		return Stream.of(
				Arguments.of("1.0 2.0 +", "3.0"),
				Arguments.of("3 4 *", "12.0"),
				Arguments.of("6 3 * 2 - sqrt", "4.0"),
				Arguments.of("4 2 - 2 - 1000 *", "0.0"),
				Arguments.of("0 sin", "0.0"),
				Arguments.of("0 cos", "1.0"),
				Arguments.of("2 4 avg", "3.0"),
				Arguments.of("10 3 mod", "1.0"),
				Arguments.of("6 3 /", "2.0"),
				Arguments.of("2 3 + 5 *", "25.0"),
				Arguments.of("1 0 /", "Infinity"),
				//Negative Scenarios
				Arguments.of("", " - Not a valid equation"),
				Arguments.of(null, ""),
				Arguments.of("1", "1 - Not a valid equation"),
				Arguments.of("sin", "sin - Not a valid equation"),
				Arguments.of("test", "test - Not a valid equation"),
				Arguments.of("1 + 1", "1 + 1 - Not Reverse Polish Notation try backwards"),
				Arguments.of("1 1 test", "1 1 test - Valid operation not found in equation"),
				Arguments.of("1 & 1", "1 & 1 - Valid operation not found in equation"),
				Arguments.of("1 1 + 5 #", "1 1 + 5 # - Not a valid equation"),
				Arguments.of("sin cos", "sin cos - Not a valid equation"),
				Arguments.of("+ -", "+ - - Not a valid equation")

		);
	}
}
