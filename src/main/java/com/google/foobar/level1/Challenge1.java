package com.google.foobar.level1;

import java.util.Arrays;

public final class Challenge1 {

    public static int solution(int[] x, int[] y) {
        Arrays.sort(x);
        Arrays.sort(y);

        for (int index = 0; index < Math.min(x.length, y.length); index++) {
            if (x[index] < y[index]) {
                return x[index];
            } else if (x[index] > y[index]) {
                return y[index];
            }
        }
        if (x.length > y.length) {
            return x[x.length - 1];
        } else {
            return y[y.length - 1];
        }
    }

}
