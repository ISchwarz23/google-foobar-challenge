package com.google.foobar.level4.challenge2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class SolutionTest {

    @Test
    void test1() {
        // when
        final int[][] output = Solution.solution(2, 1);

        // then
        assertArrayEquals(new int[][]{
                {0},
                {0}
        }, output);
    }

    @Test
    void test2() {
        // when
        final int[][] output = Solution.solution(4, 4);

        // then
        assertArrayEquals(new int[][]{
                {0      },
                {  1    },
                {    2  },
                {      3}
        }, output);
    }

    @Test
    void test3() {
        // when
        final int[][] output = Solution.solution(5, 3);

        // then
        assertArrayEquals(new int[][]{
                {0, 1, 2, 3, 4, 5            },
                {0, 1, 2,          6, 7, 8   },
                {0,       3, 4,    6, 7,    9},
                {   1,    3,    5, 6,    8, 9},
                {      2,    4, 5,    7, 8, 9}
        }, output);
    }

    @Test
    void test4() {
        // when
        final int[][] output = Solution.solution(3, 1);

        // then
        assertArrayEquals(new int[][]{
                {0},
                {0},
                {0}
        }, output);
    }

    @Test
    void test5() {
        // when
        final int[][] output = Solution.solution(2, 2);

        // then
        assertArrayEquals(new int[][]{
                {0  },
                {  1}
        }, output);
    }

    @Test
    void test6() {
        // when
        final int[][] output = Solution.solution(3, 2);

        // then
        assertArrayEquals(new int[][]{
                {0, 1   },
                {0,    2},
                {   1, 2}
        }, output);
    }

    @Test
    void test7() {
        // when
        final int[][] output = Solution.solution(4, 2);

        // then
        assertArrayEquals(new int[][]{
                {0, 1, 2   },
                {0, 1,    3},
                {0,    2, 3},
                {   1, 2, 3}
        }, output);
    }

}