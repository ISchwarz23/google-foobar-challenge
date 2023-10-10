package com.google.foobar.level3.challenge1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Solution {

    public static int solution(int[][] map) {
        Collection<Map> possibleMaps = getManipulatedMaps(map);
        int bestResult = Integer.MAX_VALUE;
        for (Map m : possibleMaps) {
            int result = getShortestWayToExit(m, bestResult);
            if (result > 0 && result < bestResult) {
                bestResult = result;
            }
        }
        return bestResult;
    }

    private static Collection<Map> getManipulatedMaps(int[][] map) {
        final Map originalMap = new Map(map);

        Collection<Map> maps = new ArrayList<>();
        maps.add(originalMap);

        for (int y = 0; y < originalMap.height; y++) {
            for (int x = 0; x < originalMap.width; x++) {
                if (originalMap.isWall(x, y) && originalMap.getPossibleMoves(x, y).size() >= 2) {
                    int[][] newMapData = clone(originalMap.mapData);
                    newMapData[y][x] = 0;
                    maps.add(new Map(newMapData));
                }
            }
        }
        return maps;
    }

    private static int[][] clone(int[][] data) {
        int[][] newData = new int[data.length][];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i].clone();
        }
        return newData;
    }

    private static int getShortestWayToExit(Map m, int stepsToBeat) {
        Position startPosition = new Position(m.width - 1, m.height - 1);
        Position exitPosition = new Position(0, 0);
        Collection<Position> visitedPositions = new HashSet<>();

        final ArrayList<Step> steps = new ArrayList<>();

        Step currentStep = new Step(startPosition, 1);
        while (currentStep != null && currentStep.position.getDistanceTo(exitPosition) != 0 && currentStep.stepsTaken < stepsToBeat) {
            // get next moves
            Collection<Position> possibleMoves = m.getPossibleMoves(currentStep.position).stream()
                    .filter(pos -> !visitedPositions.contains(pos))
                    .collect(Collectors.toList());

            // add new visited positions
            visitedPositions.addAll(possibleMoves);

            // add new steps
            final int stepsTaken = currentStep.stepsTaken + 1;
            if (!possibleMoves.isEmpty()) {
                possibleMoves.forEach(pos -> steps.add(new Step(pos, stepsTaken)));
                steps.sort((step1, step2) -> {
                    int stepDifference = step1.stepsTaken - step2.stepsTaken;
                    if (stepDifference != 0) {
                        return stepDifference;
                    }
                    return step1.position.getDistanceTo(step2.position);
                });
            }
            if (steps.isEmpty()) {
                currentStep = null;
            } else {
                currentStep = steps.remove(0);
            }
        }

        if (currentStep == null) {
            return -1;
        } else {
            return currentStep.stepsTaken;
        }
    }

    private static class Step {

        private final Position position;
        private final int stepsTaken;

        private Step(Position position, int stepsTaken) {
            this.position = position;
            this.stepsTaken = stepsTaken;
        }

    }

    private static class Map {

        private final int[][] mapData;
        private final int width;
        private final int height;

        private Map(int[][] mapData) {
            this.mapData = mapData;
            this.height = mapData.length;
            this.width = mapData[0].length;
        }

        public Collection<Position> getPossibleMoves(Position pos) {
            return getPossibleMoves(pos.x, pos.y);
        }

        public Collection<Position> getPossibleMoves(int x, int y) {
            Collection<Position> moves = new ArrayList<>();
            Position top = new Position(x, y - 1);
            Position left = new Position(x - 1, y);
            Position down = new Position(x, y + 1);
            Position right = new Position(x + 1, y);
            if (canMoveTo(top)) {
                moves.add(top);
            }
            if (canMoveTo(left)) {
                moves.add(left);
            }
            if (canMoveTo(down)) {
                moves.add(down);
            }
            if (canMoveTo(right)) {
                moves.add(right);
            }
            return moves;
        }

        public boolean canMoveTo(Position pos) {
            return canMoveTo(pos.x, pos.y);
        }

        public boolean canMoveTo(int x, int y) {
            if (x < 0 || x >= width) {
                return false;
            }
            if (y < 0 || y >= height) {
                return false;
            }
            return !isWall(x, y);
        }

        public boolean isWall(int x, int y) {
            return mapData[y][x] == 1;
        }

    }

    private static class Position {

        private final int x;
        private final int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int getDistanceTo(Position other) {
            return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
