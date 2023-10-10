package com.google.foobar.level4;

import java.util.LinkedList;

public final class Challenge1 {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        // Problem: maximum flow in directed graph
        // Solution: Ford and Fulkerson

        int[][] graph = createGraphWithSingleEntryAndExitRoom(entrances, exits, path);
        return getMaximumFlow(graph, 0, graph.length - 1);
    }

    private static int getMaximumFlow(final int[][] graph, final int entryRoomIndex, final int exitRoomIndex) {
        int[][] resultGraph = copy(graph);

        // filled by BFS and to store path
        int[] parentRooms = new int[resultGraph.length];
        int max_flow = 0;
        while (runBfsSFCT(resultGraph, entryRoomIndex, exitRoomIndex, parentRooms)) {
            // find minimum residual capacity of the edges along the path filled by BFS,
            // or we can say find the maximum flow through the path found.
            int pathFlow = Integer.MAX_VALUE;
            for (int i = exitRoomIndex; i != entryRoomIndex; i = parentRooms[i]) {
                int parent = parentRooms[i];
                pathFlow = Math.min(pathFlow, resultGraph[parent][i]);
            }
            // update residual capacities of the edges and reverse edges along the path
            for (int i = exitRoomIndex; i != entryRoomIndex; i = parentRooms[i]) {
                int parent = parentRooms[i];
                resultGraph[parent][i] -= pathFlow;
                resultGraph[i][parent] += pathFlow;
            }
            // add path flow to overall flow
            max_flow += pathFlow;
        }
        return max_flow;
    }

    private static boolean runBfsSFCT(final int[][] graph, final int entryNodeIndex, final int exitNodeIndex, final int[] parentRooms) {
        // create a queue, enqueue source vertex...
        LinkedList<Integer> nodeQueue = new LinkedList<>();
        nodeQueue.add(entryNodeIndex);
        // ... and mark source vertex as visited
        boolean[] visited = new boolean[graph.length];
        visited[entryNodeIndex] = true;
        parentRooms[entryNodeIndex] = -1;

        // standard BFS loop
        while (!nodeQueue.isEmpty()) {
            int currentRoomIndex = nodeQueue.poll();
            for (int roomIndex = 0; roomIndex < graph.length; roomIndex++) {
                if (!visited[roomIndex] && graph[currentRoomIndex][roomIndex] > 0) {
                    nodeQueue.add(roomIndex);
                    parentRooms[roomIndex] = currentRoomIndex;
                    visited[roomIndex] = true;
                }
            }
        }
        // if sink reached in BFS starting from source, return true; else false
        return visited[exitNodeIndex];
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
