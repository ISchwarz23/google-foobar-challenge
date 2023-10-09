package com.google.foobar.level2;

import org.junit.jupiter.api.Test;


class Challenge2Test {

    @Test
    void test1() {
        // given
        final var input = new int[]{3, 1, 4, 1};

        // when
        final var output = Challenge2.solution(input);

        // then
        assertEquals(4311, output);
    }

    @Test
    void test2() {
        // given
        final var input = new int[]{3, 1, 4, 1, 5, 9};

        // when
        final var output = Challenge2.solution(input);

        // then
        assertEquals(94311, output);
    }

}