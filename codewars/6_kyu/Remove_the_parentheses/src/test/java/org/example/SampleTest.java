package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    public void basicTests(String input, String expected) {
        assertEquals(expected, Kata.removeParentheses(input));
    }

    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                Arguments.of("example(unwanted thing)example", "exampleexample"),
                Arguments.of("example(unwanted thing)example", "exampleexample"),
                Arguments.of("example(unwanted thing)example", "exampleexample"),
                Arguments.of("example(unwanted thing)example", "exampleexample"),
                Arguments.of("example (unwanted thing) example", "example  example"),
                Arguments.of("a (bc d)e", "a e"),
                Arguments.of("a(b(c))", "a"),
                Arguments.of("hello example (words(more words) here) something", "hello example  something"),
                Arguments.of("(first group) (second group) (third group)", "  ")
        );
    }
}