package com.google.foobar.level1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Challenge1Test {

    @Test
    void test1() {
        // given
        final var input1 = new int[]{14, 27, 1, 4, 2, 50, 3, 1};
        final var input2 = new int[]{2, 4, -4, 3, 1, 1, 14, 27, 50};

        // when
        final var output = Challenge1.solution(input1, input2);

        // then
        assertEquals(-4, output);
    }

    @Test
    void test2() {
        // given
        final var input1 = new int[]{13, 5, 6, 2, 5};
        final var input2 = new int[]{5, 2, 5, 13};

        // when
        final var output = Challenge1.solution(input1, input2);

        // then
        assertEquals(6, output);
    }

}