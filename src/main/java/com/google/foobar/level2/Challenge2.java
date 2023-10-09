package com.google.foobar.level2;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge2 {

    public static int solution(int[] l) {
        List<Integer> digits = Arrays.stream(l).boxed().collect(Collectors.toList());
        for (int numberOfDigits = digits.size(); numberOfDigits > 0; numberOfDigits--) {
            Optional<Integer> result = getBiggestNumberDividableByThree(digits, numberOfDigits);
            if (result.isPresent()) {
                return result.get();
            }
        }
        return 0;
    }

    private static Optional<Integer> getBiggestNumberDividableByThree(List<Integer> digits, int numberOfDigits) {
        return getPermutations(digits, numberOfDigits)
                .stream()
                .map(d -> d.stream().map(digit -> "" + digit).collect(Collectors.joining("")))
                .distinct()
                .map(Integer::valueOf)
                .sorted(Collections.reverseOrder())
                .filter(value -> value % 3 == 0)
                .findFirst();
    }

    public static Collection<List<Integer>> getPermutations(List<Integer> digits, int targetDigitNumber) {
        Collection<List<Integer>> allPermutations = new ArrayList<>();
        enumerate(digits, digits.size(), targetDigitNumber, allPermutations);
        return allPermutations;
    }

    private static void enumerate(List<Integer> a, int n, int k, Collection<List<Integer>> allPermutations) {
        if (k == 0) {
            List<Integer> singlePermutation = new ArrayList<>();
            for (int i = n; i < a.size(); i++) {
                singlePermutation.add(a.get(i));
            }
            allPermutations.add(singlePermutation);
            return;
        }

        for (int i = 0; i < n; i++) {
            swap(a, i, n - 1);
            enumerate(a, n - 1, k - 1, allPermutations);
            swap(a, i, n - 1);
        }
    }

    private static void swap(List<Integer> a, int i, int j) {
        Integer temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

}
