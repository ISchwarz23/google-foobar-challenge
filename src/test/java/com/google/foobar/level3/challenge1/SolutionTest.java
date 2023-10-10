package com.google.foobar.level3.challenge1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SolutionTest {

    @Test
    void test1() {
        // given
        final int[][] input = new int[][]{
                {0, 1, 1, 0},
                {0, 0, 0, 1},
                {1, 1, 0, 0},
                {1, 1, 1, 0}
        };

        // when
        final int output = Solution.solution(input);

        // then
        assertEquals(7, output);
    }

    @Test
    void test2() {
        // given
        final int[][] input = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };

        // when
        final int output = Solution.solution(input);

        // then
        assertEquals(11, output);
    }

    @Test
    void test3() {
        // given
        final int[][] input = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 1, 1},
                {0, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0}
        };

        // when
        final int output = Solution.solution(input);

        // then
        assertEquals(11, output);
    }

}