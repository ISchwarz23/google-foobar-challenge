package com.google.foobar.level3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class Challenge3Test {

    @Test
    void test1() {
        // given
        int[][] input = new int[][]{
                {0, 1, 0, 0, 0, 1},
                {4, 0, 0, 3, 2, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        // when
        final int[] output = Challenge3.solution(input);

        // then
        assertArrayEquals(new int[]{0, 3, 2, 9, 14}, output);
    }

    @Test
    void test2() {
        // given
        int[][] input = new int[][]{
                {0, 2, 1, 0, 0},
                {0, 0, 0, 3, 4},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        // when
        final int[] output = Challenge3.solution(input);

        // then
        assertArrayEquals(new int[]{7, 6, 8, 21}, output);
    }

}