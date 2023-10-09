package com.google.foobar.level3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Challenge2Test {

    @Test
    void test1() {
        // when
        final int output = Challenge2.solution(17, 4);

        // then
        assertEquals(14, output);
    }

    @Test
    void test2() {
        // when
        final int output = Challenge2.solution(0, 3);

        // then
        assertEquals(2, output);
    }

}