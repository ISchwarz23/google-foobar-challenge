package com.google.foobar.level4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Challenge1Test {

    @Test
    void test1() {
        // given
        final int[] entrances = new int[]{0};
        final int[] exits = new int[]{3};
        final int[][] path = new int[][]{
                {0, 7, 0, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 8},
                {9, 0, 0, 0}
        };

        // when
        final int output = Challenge1.solution(entrances, exits, path);

        // then
        assertEquals(6, output);
    }

    @Test
    void test2() {
        // given
        final int[] entrances = new int[]{0, 1};
        final int[] exits = new int[]{4, 5};
        final int[][] path = new int[][]{
                {0, 0, 4, 6, 0, 0},
                {0, 0, 5, 2, 0, 0},
                {0, 0, 0, 0, 4, 4},
                {0, 0, 0, 0, 6, 6},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        // when
        final int output = Challenge1.solution(entrances, exits, path);

        // then
        assertEquals(16, output);
    }

}