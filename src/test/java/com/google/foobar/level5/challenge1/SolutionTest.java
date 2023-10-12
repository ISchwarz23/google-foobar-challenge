package com.google.foobar.level5.challenge1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SolutionTest {

    @Test
    void test1() {
        // when
        final String output = Solution.solution(2, 2, 2);

        // then
        assertEquals("7", output);
    }

    @Test
    void test2() {
        // when
        final String output = Solution.solution(2, 3, 4);

        // then
        assertEquals("430", output);
    }

}