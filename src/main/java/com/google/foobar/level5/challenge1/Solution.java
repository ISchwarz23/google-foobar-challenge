package com.google.foobar.level5.challenge1;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

public class Solution {

    public static String solution(int gridWidth, int gridHeight, int numberOfStates) {
        Set<Crystal> crystals = new HashSet<>();
        createCrystalIterable(gridWidth, gridHeight, numberOfStates).forEach(c -> {
            if (!crystals.contains(c)) {
                crystals.removeAll(getVerticalOrHorizontalPermutations(c));
                crystals.add(c);
            }
        });
        return Long.toUnsignedString(crystals.size());
    }

    private static Iterable<Crystal> createCrystalIterable(final int girdWidth, final int gridHeight, final int numberOfStates) {
        return () -> new Iterator<Crystal>() {

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
                return new Crystal(deepCopy(currentState));
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
    }

    private static Set<Crystal> getVerticalOrHorizontalPermutations(Crystal crystal) {
        return getVerticalOrHorizontalPermutations(crystal.states.length, crystal.states);
    }

    private static Set<Crystal> getVerticalOrHorizontalPermutations(int n, int[][] elements) {

        if (n == 1) {
            Set<Crystal> permutations = new HashSet<>();
            permutations.add(new Crystal(deepCopy(elements)));
            permutations.addAll(getHorizontalPermutations(elements[0].length, deepCopy(elements)));
            return permutations;
        } else {
            Set<Crystal> permutations = new HashSet<>(getVerticalOrHorizontalPermutations(n - 1, elements));
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swapRows(elements, i, n - 1);
                } else {
                    swapRows(elements, 0, n - 1);
                }
                permutations.addAll(getVerticalOrHorizontalPermutations(n - 1, elements));
            }
            return permutations;
        }
    }

    private static Set<Crystal> getHorizontalPermutations(int n, int[][] elements) {

        if (n == 1) {
            return Collections.singleton(new Crystal(deepCopy(elements)));
        } else {
            Set<Crystal> permutations = new HashSet<>(getHorizontalPermutations(n - 1, elements));
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swapColumns(elements, i, n - 1);
                } else {
                    swapColumns(elements, 0, n - 1);
                }
                permutations.addAll(getHorizontalPermutations(n - 1, elements));
            }
            return permutations;
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

    private static int[][] deepCopy(int[][] arr) {
        int[][] clone = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            clone[i] = arr[i].clone();
        }
        return clone;
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
            Crystal other = (Crystal) o;
            return Arrays.deepEquals(this.states, other.states);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(states);
        }
    }

}
