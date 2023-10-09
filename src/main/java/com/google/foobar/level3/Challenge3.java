package com.google.foobar.level3;

import java.util.Arrays;

public final class Challenge3 {

    public static int[] solution(int[][] m) {
        // remark: solved using "Markov Chain"

        // get intermediate states
        boolean[] isIntermediate = new boolean[m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] > 0) {
                    isIntermediate[i] = true;
                    break;
                }
            }
        }

        // add 100% probability for terminate steps to end on itself
        for (int i = 0; i < m.length; i++) {
            if (!isIntermediate[i]) {
                m[i][i] = 1;
            }
        }

        // get number of terminal states
        int numberOfTerminalStates = 0;
        for (int i = 0; i < m.length; i++) {
            if (!isIntermediate[i]) {
                numberOfTerminalStates++;
            }
        }

        // handle special case: first state is terminate
        if (!isIntermediate[0]) {
            int[] answer = new int[numberOfTerminalStates + 1];
            answer[0] = 1;
            answer[numberOfTerminalStates] = 1;
            for (int i = 1; i < numberOfTerminalStates; i++) {
                answer[i] = 0;
            }
            return answer;
        }

        // create P
        int[] denominators = Arrays.stream(m).mapToInt(row -> Arrays.stream(row).sum()).toArray();
        Matrix matrixP = Matrix.createZeroMatrix(m.length, m.length);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                matrixP.setValue(i, j, new Fraction(m[i][j], denominators[i]));
            }
        }

        // extract Q
        Matrix matrixQ = Matrix.createZeroMatrix(m.length - numberOfTerminalStates, m.length - numberOfTerminalStates);
        int iQ = 0;
        int jQ = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (isIntermediate[j] && isIntermediate[i]) {
                    matrixQ.setValue(iQ, jQ, matrixP.getValue(i, j));
                    jQ++;
                }
            }
            if (isIntermediate[i]) {
                iQ++;
            }
            jQ = 0;
        }

        // extract R
        Matrix matrixR = Matrix.createZeroMatrix(m.length - numberOfTerminalStates, numberOfTerminalStates);
        int iR = 0;
        int jR = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (!isIntermediate[j] && isIntermediate[i]) {
                    matrixR.setValue(iR, jR, matrixP.getValue(i, j));
                    jR++;
                }
            }
            if (isIntermediate[i]) {
                iR++;
            }
            jR = 0;
        }

        // calculate B
        Matrix identityMatrix = Matrix.createIdentityMatrix(matrixQ.n);
        Matrix matrixB = identityMatrix.subtract(matrixQ).inverse().dot(matrixR);

        // get answer
        Fraction[] firstRow = matrixB.data[0];
        int[] answer = new int[numberOfTerminalStates + 1];
        // find least common multiplier
        int lcm = 1;
        for (int i = 0; i < numberOfTerminalStates; i++) {
            if (firstRow[i].getNumerator() != 0) {
                lcm = findLeastCommonMultiplier(lcm, firstRow[i].getDenominator());
            }
        }
        // build up answers using found lcm
        for (int i = 0; i < numberOfTerminalStates; i++) {
            if (firstRow[i].getNumerator() != 0) {
                answer[i] = (firstRow[i].getNumerator() * (lcm / firstRow[i].getDenominator()));
            }
        }
        answer[numberOfTerminalStates] = lcm;
        return answer;
    }

    public static int findGreatestCommonDivisor(int a, int b) {
        if (b == 0) {
            return a;
        }
        int gcd = findGreatestCommonDivisor(b, a % b);
        return gcd < 0 ? gcd * -1 : gcd;
    }

    public static int findLeastCommonMultiplier(int a, int b) {
        int lcm = (a * b) / findGreatestCommonDivisor(a, b);
        return lcm < 0 ? lcm * -1 : lcm;
    }

    public static class Fraction {

        private int numerator;
        private int denominator;

        public Fraction(int numerator, int denominator) {
            if (denominator == 0) {
                throw new ArithmeticException("Division by zero!");
            }
            this.numerator = numerator;
            this.denominator = denominator;
            this.reduce();
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }

        private void reduce() {
            int gcd = findGreatestCommonDivisor(this.numerator, this.denominator);
            if (this.denominator > 0) {
                this.numerator = this.numerator / gcd;
                this.denominator = this.denominator / gcd;
            } else {
                this.numerator = this.numerator / (gcd * -1);
                this.denominator = this.numerator / (gcd * -1);
            }
        }

        private Fraction reciprocal() throws ArithmeticException {
            if (this.toString().equals("0")) {
                throw new ArithmeticException("Division by zero!");
            }
            return new Fraction(this.denominator, this.numerator);
        }

        public Fraction add(Fraction other) {
            return new Fraction(this.numerator * other.denominator + other.numerator * this.denominator, this.denominator * other.denominator);
        }

        public Fraction subtract(Fraction other) {
            return new Fraction(this.numerator * other.denominator - other.numerator * this.denominator, this.denominator * other.denominator);
        }

        public Fraction multiply(Fraction other) {
            return new Fraction(other.numerator * this.numerator, this.denominator * other.denominator);
        }

        public Fraction divide(Fraction other) {
            return this.multiply(other.reciprocal());
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }
    }

    public static class Matrix {

        private final int m;
        private final int n;
        private final Fraction[][] data;

        private Matrix(Fraction[][] data) {
            this.m = data.length;
            this.n = data[0].length;
            this.data = data;
        }

        public static Matrix createZeroMatrix(int m, int n) {
            Fraction[][] matrixFrac = new Fraction[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrixFrac[i][j] = new Fraction(0, 1);
                }
            }
            return new Matrix(matrixFrac);
        }

        public static Matrix createIdentityMatrix(int m) {
            Matrix result = Matrix.createZeroMatrix(m, m);
            for (int i = 0; i < m; i++) {
                result.setValue(i, i, new Fraction(1, 1));
            }
            return result;
        }

        public void setValue(int m, int n, Fraction value) {
            data[m][n] = value;
        }

        public Fraction getValue(int m, int n) {
            return data[m][n];
        }

        public Matrix subtract(Matrix anotherMatrix) {
            Matrix result = Matrix.createZeroMatrix(anotherMatrix.m, anotherMatrix.n);
            for (int i = 0; i < anotherMatrix.m; i++) {
                for (int j = 0; j < anotherMatrix.n; j++) {
                    result.data[i][j] = this.data[i][j].subtract(anotherMatrix.data[i][j]);
                }
            }
            return result;
        }

        public Matrix inverse() {

            Matrix matrixA = Matrix.createZeroMatrix(this.m, this.m);
            for (int i = 0; i < this.m; i++) {
                System.arraycopy(this.data[i], 0, matrixA.data[i], 0, this.m);
            }

            Matrix Id = Matrix.createIdentityMatrix(matrixA.m);
            Fraction temp;

            for (int k = 0; k < matrixA.m; k++) {
                temp = matrixA.data[k][k];

                for (int j = 0; j < matrixA.m; j++) {
                    matrixA.data[k][j] = matrixA.data[k][j].divide(temp);
                    Id.data[k][j] = Id.data[k][j].divide(temp);
                }

                for (int i = k + 1; i < matrixA.m; i++) {
                    temp = matrixA.data[i][k];

                    for (int j = 0; j < matrixA.m; j++) {
                        matrixA.data[i][j] = matrixA.data[i][j].subtract(matrixA.data[k][j].multiply(temp));
                        Id.data[i][j] = Id.data[i][j].subtract(Id.data[k][j].multiply(temp));
                    }
                }
            }

            for (int k = matrixA.m - 1; k > 0; k--) {
                for (int i = k - 1; i >= 0; i--) {
                    temp = matrixA.data[i][k];

                    for (int j = 0; j < matrixA.m; j++) {
                        matrixA.data[i][j] = matrixA.data[i][j].subtract(matrixA.data[k][j].multiply(temp));
                        Id.data[i][j] = Id.data[i][j].subtract(Id.data[k][j].multiply(temp));
                    }
                }
            }

            for (int i = 0; i < matrixA.m; i++) {
                System.arraycopy(Id.data[i], 0, matrixA.data[i], 0, matrixA.m);
            }
            return matrixA;
        }

        public Matrix dot(Matrix matrixB) {
            Matrix matrixC = Matrix.createZeroMatrix(this.m, matrixB.n);
            for (int i = 0; i < this.m; i++) {
                for (int j = 0; j < matrixB.n; j++) {
                    for (int k = 0; k < matrixB.m; k++) {
                        matrixC.data[i][j] = matrixC.data[i][j].add(this.data[i][k].multiply(matrixB.data[k][j]));
                    }
                }
            }
            return matrixC;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.m; i++) {
                for (int j = 0; j < this.n; j++) {
                    builder.append(this.getValue(i, j)).append(" ");
                }
                builder.append("\n");
            }
            return builder.toString();
        }

    }

}
