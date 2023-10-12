package com.google.foobar.level5.challenge1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Solution {

    public static String solution(int girdWidth, int gridHeight, int numberOfStates) {

        final List<Crystal> knownValues = new ArrayList<>();
        final long count = createCrystalStream(girdWidth, gridHeight, numberOfStates)
                .filter(c -> {
                    boolean known = knownValues.contains(c);
                    if (!known) {
                        knownValues.add(c);
                    }
                    return !known;
                })
                // .peek(System.out::println)
                .count();
        return String.valueOf(count);
    }

    private static Stream<Crystal> createCrystalStream(final int girdWidth, final int gridHeight, final int numberOfStates) {
        Iterator<Crystal> iterator = new Iterator<Crystal>() {

            private final int[][] currentState = new int[gridHeight][girdWidth];
            private final long maxStateCount = (long) Math.pow(numberOfStates, gridHeight * girdWidth);
            private int currentStateIndex = 0;
            private int stateCount = 0;

            @Override
            public boolean hasNext() {
                return stateCount < maxStateCount;
            }

            @Override
            public Crystal next() {
                // increase state by one
                setValueAt(currentStateIndex, oldValue -> ++oldValue);
                if (getValueAt(currentStateIndex) >= numberOfStates) {
                    // increase all states at and after current index that are going to flip
                    while (getValueAt(currentStateIndex) >= numberOfStates && ++currentStateIndex < gridHeight * girdWidth) {
                        setValueAt(currentStateIndex, oldValue -> ++oldValue);
                    }

                    // set all previous states to initial state '0'
                    while (currentStateIndex > 0) {
                        setValueAt(--currentStateIndex, 0);
                    }
                }
                // create new state
                stateCount++;
                return new Crystal(cloneArray(currentState));
            }

            private int getValueAt(int index) {
                return currentState[index / girdWidth][index % girdWidth];
            }

            private void setValueAt(int currentStateIndex, int newValue) {
                setValueAt(currentStateIndex, oldValue -> newValue);
            }

            private void setValueAt(int index, Function<Integer, Integer> valueManipulator) {
                currentState[index / girdWidth][index % girdWidth] = valueManipulator.apply(getValueAt(index));
            }

        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    }

    public static class Crystal {

        private final int[][] states;

        public Crystal(int[][] states) {
            this.states = states;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int[] row : states) {
                for (int element : row) {
                    sb.append(element);
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Crystal)) return false;
            Crystal crystal = (Crystal) o;
            return matchesAnyVerticalOrHorizontalPermutation(states.length, cloneArray(states), crystal.states);
        }

        @Override
        public int hashCode() {
            return generateHashUsingVerticalAndHorizontalPermutation(states.length, cloneArray(states));
        }
    }

    private static boolean matchesAnyVerticalOrHorizontalPermutation(int n, int[][] elements, int[][] goal) {

        if (n == 1) {
            return Arrays.deepEquals(elements, goal) || matchesUsingHorizontalPermutation(goal[0].length, cloneArray(elements), goal);
        } else {
            for (int i = 0; i < n - 1; i++) {
                if (matchesAnyVerticalOrHorizontalPermutation(n - 1, elements, goal)) {
                    return true;
                }
                if (n % 2 == 0) {
                    swapRows(elements, i, n - 1);
                } else {
                    swapRows(elements, 0, n - 1);
                }
            }
            return matchesAnyVerticalOrHorizontalPermutation(n - 1, elements, goal);
        }
    }

    private static boolean matchesUsingHorizontalPermutation(int n, int[][] elements, int[][] goal) {

        if (n == 1) {
            return Arrays.deepEquals(elements, goal);
        } else {
            for (int i = 0; i < n - 1; i++) {
                if (matchesUsingHorizontalPermutation(n - 1, elements, goal)) {
                    return true;
                }
                if (n % 2 == 0) {
                    swapColumns(elements, i, n - 1);
                } else {
                    swapColumns(elements, 0, n - 1);
                }
            }
            return matchesUsingHorizontalPermutation(n - 1, elements, goal);
        }
    }

    private static int generateHashUsingVerticalAndHorizontalPermutation(int n, int[][] elements) {

        if (n == 1) {
            return 31 * Arrays.deepHashCode(elements) + generateHashUsingHorizontalPermutation(elements[0].length, cloneArray(elements));
        } else {
            int hash = generateHashUsingVerticalAndHorizontalPermutation(n - 1, elements);
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swapRows(elements, i, n - 1);
                } else {
                    swapRows(elements, 0, n - 1);
                }
                hash = 31 * hash + generateHashUsingVerticalAndHorizontalPermutation(n - 1, elements);
            }
            return hash;
        }
    }

    private static int generateHashUsingHorizontalPermutation(int n, int[][] elements) {

        if (n == 1) {
            return Arrays.deepHashCode(elements);
        } else {
            int hash = generateHashUsingHorizontalPermutation(n - 1, elements);
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swapColumns(elements, i, n - 1);
                } else {
                    swapColumns(elements, 0, n - 1);
                }
                hash = 31 * hash + generateHashUsingHorizontalPermutation(n - 1, elements);
            }
            return hash;
        }
    }

    private static void swapRows(int[][] elements, int a, int b) {
        int[] tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }

    private static void swapColumns(int[][] elements, int a, int b) {
        for (int row = 0; row < elements.length; row++) {
            int tmp = elements[row][a];
            elements[row][a] = elements[row][b];
            elements[row][b] = tmp;
        }
    }

    private static int[][] cloneArray(int[][] arr) {
        int[][] clone = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            clone[i] = arr[i].clone();
        }
        return clone;
    }

}
