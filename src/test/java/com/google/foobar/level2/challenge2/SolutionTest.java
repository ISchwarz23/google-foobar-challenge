package com.google.foobar.level2.challenge2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SolutionTest {

    @Test
    void test1() {
        // given
        final int[] input = new int[]{3, 1, 4, 1};

        // when
        final int output = Solution.solution(input);

        // then
        assertEquals(4311, output);
    }

    @Test
    void test2() {
        // given
        final int[] input = new int[]{3, 1, 4, 1, 5, 9};

        // when
        final int output = Solution.solution(input);

        // then
        assertEquals(94311, output);
    }

}