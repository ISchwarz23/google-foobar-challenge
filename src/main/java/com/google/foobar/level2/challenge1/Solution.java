package com.google.foobar.level2.challenge1;

import java.util.Arrays;

public class Solution {

    public static String[] solution(String[] l) {
        Arrays.sort(l, (v1, v2) -> {
            int[] v1Parts = Arrays.stream(v1.split("\\.")).mapToInt(Integer::parseInt).toArray();
            int[] v2Parts = Arrays.stream(v2.split("\\.")).mapToInt(Integer::parseInt).toArray();

            // compare major
            int majorResult = v1Parts[0] - v2Parts[0];
            if (majorResult != 0) {
                return majorResult;
            }

            // compare minor
            int v1Minor = v1Parts.length >= 2 ? v1Parts[1] : -1;
            int v2Minor = v2Parts.length >= 2 ? v2Parts[1] : -1;
            int minorResult = v1Minor - v2Minor;
            if (minorResult != 0) {
                return minorResult;
            }

            // compare revision
            int v1Revision = v1Parts.length >= 3 ? v1Parts[2] : -1;
            int v2Revision = v2Parts.length >= 3 ? v2Parts[2] : -1;
            return v1Revision - v2Revision;
        });
        return l;
    }

}
