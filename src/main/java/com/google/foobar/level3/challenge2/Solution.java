package com.google.foobar.level3.challenge2;

public final class Solution {

    public static int solution(int start, int length) {
        int result = 0;
        for (int lineIndex = 0; lineIndex < length; lineIndex++) {
            for (int index = 0; index < length - lineIndex; index++) {
                int id = start + lineIndex * length + index;
                result ^= id;
            }
        }
        return result;
    }

}
