package com.google.foobar.level4.challenge2;


import java.util.ArrayList;
import java.util.List;

public final class Solution {

    public static int[][] solution(int numberOfBunnies, int numberOfRequiredBunnies) {
        // EXAMPLE
        // => numberOfBunnies = 5; numberOfRequiredBunnies = 3
        // => numberOfDuplicateKeys = 3
        //
        // [ 0, 1, 2, 3, 4, 5             ]
        // [ 0, 1, 2,          6, 7, 8    ]
        // [ 0,       3, 4,    6, 7,    9 ]
        // [    1,    3,    5, 6,    8, 9 ]
        // [       2,    4, 5,    7, 8, 9 ]

        // calculate number of duplicate keys
        int numberOfDuplicateKeys = numberOfBunnies - numberOfRequiredBunnies + 1;

        // create key combinations holder
        List<List<Integer>> keysPerBunny = new ArrayList<>();
        for (int bunnyIndex = 0; bunnyIndex < numberOfBunnies; bunnyIndex++) {
            keysPerBunny.add(new ArrayList<>());
        }

        // calculate pattern using binary numbers containing the calculated number of duplicate keys
        // therefore iterate from (2 ^ numberOfBunnies - 2 ^ (numberOfBunnies - numberOfDuplicateKeys) down to (2 ^ numberOfDuplicateKeys - 1)
        int lowerBound = (int) Math.pow(2, numberOfDuplicateKeys) - 1;
        int upperBound = (int) (Math.pow(2, numberOfBunnies) - Math.pow(2, numberOfBunnies - numberOfDuplicateKeys));

        int keyId = 0;
        for (int i = upperBound; i >= lowerBound; i--) {
            String binaryPattern = padLeft(Integer.toBinaryString(i), numberOfBunnies);

            // take all binary patterns that contain the required number of '1's
            if (binaryPattern.chars().filter(c -> c == '1').count() == numberOfDuplicateKeys) {
                for (int bunnyIndex = 0; bunnyIndex < numberOfBunnies; bunnyIndex++) {

                    // add the current key to the bunny on which index the pattern has a '1'
                    if (binaryPattern.charAt(bunnyIndex) == '1') {
                        keysPerBunny.get(bunnyIndex).add(keyId);
                    }
                }

                // increase key id every time a pattern has been found
                keyId++;
            }
        }

        // convert to 2d-array
        return keysPerBunny.stream()
                .map(keys -> keys.stream().mapToInt(k -> k).toArray())
                .toArray(int[][]::new);
    }

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

}
