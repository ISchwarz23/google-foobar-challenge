package com.google.foobar.level1.challenge1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SolutionTest {

    @Test
    void test1() {
        // given
        final int[] input1 = new int[]{14, 27, 1, 4, 2, 50, 3, 1};
        final int[] input2 = new int[]{2, 4, -4, 3, 1, 1, 14, 27, 50};

        // when
        final int output = Solution.solution(input1, input2);

        // then
        assertEquals(-4, output);
    }

    @Test
    void test2() {
        // given
        final int[] input1 = new int[]{13, 5, 6, 2, 5};
        final int[] input2 = new int[]{5, 2, 5, 13};

        // when
        final int output = Solution.solution(input1, input2);

        // then
        assertEquals(6, output);
    }

}