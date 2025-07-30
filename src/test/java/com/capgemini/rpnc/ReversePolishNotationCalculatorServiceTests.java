package com.capgemini.rpnc;

import com.capgemini.rpnc.services.ReversePolishNotationCalculatorService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ReversePolishNotationCalculatorServiceTests {

	@Autowired
	ReversePolishNotationCalculatorService reversePolishNotationCalculatorService;

	@ParameterizedTest
	@MethodSource("equationsAndExpectedOutput")
	void testCalculate(String rpnEquation, String expectedOutput) {
		assertEquals(expectedOutput, reversePolishNotationCalculatorService.calculate(rpnEquation));
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
				Arguments.of("3 3 + 0 sin +", "6.0"),
				Arguments.of("3 3 - sin", "0.0"),
				Arguments.of("0 sin 0 cos avg 1 + 3 +", "4.5"),
				Arguments.of("0 cos 1 +", "2.0"),

				//Negative Scenarios
				Arguments.of("", " is not a valid equation"),
				Arguments.of(null, ""),
				Arguments.of("1", "1 is not a valid equation"),
				Arguments.of("1 1", "1 1 - Valid operation not found in equation"),
				Arguments.of("sin", "sin is not a valid equation"),
				Arguments.of("test", "test is not a valid equation"),
				Arguments.of("1 + 1", "1 + 1 - Not Reverse Polish Notation try backwards"),
				Arguments.of("1 1 test", "1 1 test - Valid operation not found in equation"),
				Arguments.of("1 & 1", "1 & 1 - Valid operation not found in equation"),
				Arguments.of("1 1 + 5 #", "1 1 + 5 # is not a valid equation"),
				Arguments.of("sin cos", "Not found any number in the equation"),
				Arguments.of("+ -", "Not found any number in the equation"),
				Arguments.of("3 3 3 + 0 0 sin", "3 3 3 + 0 0 sin - Not Reverse Polish Notation try backwards"),
				Arguments.of("3 3 + 0 0 sin", "3 3 + 0 0 sin - Not Reverse Polish Notation try backwards"),
				Arguments.of("3 3 + 0 sin", "3 3 + 0 sin - Not Reverse Polish Notation try backwards"),
				Arguments.of("3 3 + 0 sin", "3 3 + 0 sin - Not Reverse Polish Notation try backwards"),
				Arguments.of("0 0 sin + 3 3 3 +", "0 0 sin + 3 3 3 + - Not Reverse Polish Notation try backwards"),
				Arguments.of("0 sin + 3 3 3 +", "0 sin + 3 3 3 + - Not Reverse Polish Notation try backwards"),
				Arguments.of("0 sin + 3 3 3", "0 sin + 3 3 3 - Not Reverse Polish Notation try backwards"),
				Arguments.of("0 sin + 3 3 +", "0 sin + 3 3 + - Not Reverse Polish Notation try backwards"),
				Arguments.of("0 sin 3 3 +", "0 sin 3 3 + - Not Reverse Polish Notation try backwards")
		);
	}
}
