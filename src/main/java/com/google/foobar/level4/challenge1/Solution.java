package com.google.foobar.level4.challenge1;

import java.util.*;

public final class Solution {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        // Problem: maximum flow in directed graph
        // Solution: Ford and Fulkerson

        int[][] graph = createGraphWithSingleEntryAndExitRoom(entrances, exits, path);
        return getMaximumFlow(graph, 0, graph.length - 1);
    }

    private static int getMaximumFlow(final int[][] graph, final int entryRoomIndex, final int exitRoomIndex) {
        int[][] workingGraph = copy(graph);

        int maxFlow = 0;
        Optional<int[]> optionalPathToEnd;
        do {
            optionalPathToEnd = findPathToEnd(workingGraph, entryRoomIndex, exitRoomIndex);
            if (optionalPathToEnd.isPresent()) {
                int[] parentRooms = optionalPathToEnd.get();
                // find the maximum flow through the path
                int pathFlow = Integer.MAX_VALUE;
                for (int currentRoomIndex = exitRoomIndex; currentRoomIndex != entryRoomIndex; currentRoomIndex = parentRooms[currentRoomIndex]) {
                    int parentRoomIndex = parentRooms[currentRoomIndex];
                    pathFlow = Math.min(pathFlow, workingGraph[parentRoomIndex][currentRoomIndex]);
                }
                // add path flow to overall flow
                maxFlow += pathFlow;
                // update capacities of the edges along the path
                for (int currentRoomIndex = exitRoomIndex; currentRoomIndex != entryRoomIndex; currentRoomIndex = parentRooms[currentRoomIndex]) {
                    int parentRoomIndex = parentRooms[currentRoomIndex];
                    workingGraph[parentRoomIndex][currentRoomIndex] -= pathFlow;
                }
            }
        } while (optionalPathToEnd.isPresent());
        return maxFlow;
    }

    private static Optional<int[]> findPathToEnd(final int[][] graph, final int entryNodeIndex, final int exitNodeIndex) {
        int[] parentRooms = new int[graph.length];
        // create a queue, enqueue source room...
        LinkedList<Integer> nodeQueue = new LinkedList<>();
        nodeQueue.add(entryNodeIndex);
        // ... and mark source room as visited...
        boolean[] visitedRooms = new boolean[graph.length];
        visitedRooms[entryNodeIndex] = true;
        // ... and set parent of entry node to -1
        parentRooms[entryNodeIndex] = -1;

        // standard BFS loop
        while (!nodeQueue.isEmpty()) {
            int currentRoomIndex = nodeQueue.poll();
            for (int roomIndex = 0; roomIndex < graph.length; roomIndex++) {
                if (!visitedRooms[roomIndex] && graph[currentRoomIndex][roomIndex] > 0) {
                    nodeQueue.add(roomIndex);
                    parentRooms[roomIndex] = currentRoomIndex;
                    visitedRooms[roomIndex] = true;
                }
            }
        }
        // if exit reached in BFS starting from source, return true; else false
        if (visitedRooms[exitNodeIndex]) {
            return Optional.of(parentRooms);
        } else {
            return Optional.empty();
        }
    }

    private static int[][] createGraphWithSingleEntryAndExitRoom(int[] entrances, int[] exits, int[][] path) {
        // create new graph with space for new entrance, new exit and given path
        int[][] graph = new int[path.length + 2][path[0].length + 2];
        // add single entry room, which links to given entrance nodes
        for (int entranceRoomIndex : entrances) {
            graph[0][entranceRoomIndex + 1] = Integer.MAX_VALUE;
        }
        // add given path to new graph
        for (int roomIndex = 0; roomIndex < path.length; roomIndex++) {
            System.arraycopy(path[roomIndex], 0, graph[roomIndex + 1], 1, path[roomIndex].length);
        }
        // add single exit room, which is reachable from all given exit nodes
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

}
