package com.google.foobar.level4.challenge1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public final class Solution {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int[][] flowGraph = createGraphWithSingleEntryAndExitRoom(entrances, exits, path);
        return calculateMaximumFlow(flowGraph, 0, flowGraph.length - 1);
    }

    private static int calculateMaximumFlow(final int[][] flowGraph, final int entryRoomIndex, final int exitRoomIndex) {
        int[][] flowBetweenRooms = copy(flowGraph);

        int maxFlow = 0;
        Optional<List<Integer>> pathFromEntryToExit;
        while ((pathFromEntryToExit = findPath(flowBetweenRooms, entryRoomIndex, exitRoomIndex)).isPresent()) {
            List<Integer> path = pathFromEntryToExit.get();
            // find the maximum flow through the path
            int pathFlow = Integer.MAX_VALUE;
            for (int i = 0; i < path.size() - 1; i++) {
                final int currentRoomIndex = path.get(i);
                final int nextRoomIndex = path.get(i + 1);
                final int flowBetweenCurrentAndNextRoom = flowBetweenRooms[currentRoomIndex][nextRoomIndex];
                pathFlow = Math.min(pathFlow, flowBetweenCurrentAndNextRoom);
            }
            // add path flow to overall flow
            maxFlow += pathFlow;
            // update flow between rooms along the path
            for (int i = 0; i < path.size() - 1; i++) {
                final int currentRoomIndex = path.get(i);
                final int nextRoomIndex = path.get(i + 1);
                flowBetweenRooms[currentRoomIndex][nextRoomIndex] -= pathFlow;
            }
        }
        return maxFlow;
    }

    private static Optional<List<Integer>> findPath(final int[][] flowBetweenRooms, final int startRoomIndex, final int endRoomIndex) {
        return findPath(flowBetweenRooms, startRoomIndex, endRoomIndex, Collections.emptyList());
    }

    private static Optional<List<Integer>> findPath(final int[][] flowBetweenRooms, final int startRoomIndex, final int endRoomIndex, final List<Integer> alreadyVisitedNodes) {
        if (startRoomIndex == endRoomIndex) {
            return Optional.of(Collections.singletonList(startRoomIndex));
        }

        // find path using DFS
        for (int currentNodeIndex = 0; currentNodeIndex < flowBetweenRooms[startRoomIndex].length; currentNodeIndex++) {
            int currentNodeFlow = flowBetweenRooms[startRoomIndex][currentNodeIndex];
            if (currentNodeFlow > 0 && !alreadyVisitedNodes.contains(currentNodeIndex)) {
                final Optional<List<Integer>> path = findPath(flowBetweenRooms, currentNodeIndex, endRoomIndex, addElementToStartOfList(startRoomIndex, alreadyVisitedNodes))
                        .map(p -> addElementToStartOfList(startRoomIndex, p));
                if (path.isPresent()) {
                    return path;
                }
            }
        }
        return Optional.empty();
    }

    private static int[][] createGraphWithSingleEntryAndExitRoom(int[] entrances, int[] exits, int[][] path) {
        // create new graph with space for new entrance, new exit and given path
        int[][] graph = new int[path.length + 2][path[0].length + 2];
        // add single entry room, which links to given entrance rooms
        for (int entranceRoomIndex : entrances) {
            graph[0][entranceRoomIndex + 1] = Integer.MAX_VALUE;
        }
        // add given path to new graph
        for (int roomIndex = 0; roomIndex < path.length; roomIndex++) {
            System.arraycopy(path[roomIndex], 0, graph[roomIndex + 1], 1, path[roomIndex].length);
        }
        // add single exit room, which is reachable from all given exit rooms
        for (int exitRoomIndex : exits) {
            graph[exitRoomIndex + 1][graph[0].length - 1] = Integer.MAX_VALUE;
        }
        return graph;
    }

    private static int[][] copy(int[][] graph) {
        int[][] copy = new int[graph.length][graph[0].length];
        for (int i = 0; i < copy.length; i++) {
            System.arraycopy(graph[i], 0, copy[i], 0, copy[i].length);
        }
        return copy;
    }

    private static <T> List<T> addElementToStartOfList(T newElement, List<T> originalList) {
        ArrayList<T> extendedList = new ArrayList<>(originalList);
        extendedList.add(0, newElement);
        return extendedList;
    }

}
