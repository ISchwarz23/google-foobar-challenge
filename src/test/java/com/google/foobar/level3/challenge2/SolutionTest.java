package com.google.foobar.level3.challenge2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SolutionTest {

    @Test
    void test1() {
        // when
        final int output = Solution.solution(17, 4);

        // then
        assertEquals(14, output);
    }

    @Test
    void test2() {
        // when
        final int output = Solution.solution(0, 3);

        // then
        assertEquals(2, output);
    }

}