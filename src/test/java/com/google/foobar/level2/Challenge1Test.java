package com.google.foobar.level2;

import org.junit.jupiter.api.Test;


class Challenge1Test {

    @Test
    void test1() {
        // given
        final var input = new String[]{"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"};

        // when
        final var output = Challenge1.solution(input);

        // then
        assertArrayEquals(new String[]{"0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"}, output);
    }

    @Test
    void test2() {
        // given
        final var input = new String[]{"1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"};

        // when
        final var output = Challenge1.solution(input);

        // then
        assertArrayEquals(new String[]{"1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"}, output);
    }

}