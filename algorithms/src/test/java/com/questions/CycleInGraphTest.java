package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CycleInGraphTest {

    @ParameterizedTest
    @MethodSource("cyclicGraphData")
    public void testCycleInGraph(int[][] edges, boolean expectedResult) {
        assertEquals(expectedResult, cycleInGraph(edges));
    }

    private boolean cycleInGraph(int[][] edges) {
        // edges[i] = vertex's outbound edges
        // a self-loop is considered a cycle
        final Set<Integer> visited = new HashSet<>();
        for (int vertex = 0; vertex < edges.length; vertex++) {
            if (dfsCheckCycle(vertex, edges, new HashSet<>(), visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfsCheckCycle(int vertex, int[][] edges, Set<Integer> visiting, Set<Integer> visited) {
        if (visited.contains(vertex)) {
            return false;
        }
        if (visiting.contains(vertex)) {
            return true;
        }
        // keep track of vertices visited down this specific path
        visiting.add(vertex);
        System.out.println("Visiting: " + visiting);
        for (int edge : edges[vertex]) {
            if (dfsCheckCycle(edge, edges, visiting, visited)) {
                return true;
            }
        }
        // remove from visiting as we unwind from going down a valid path
        visiting.remove(vertex);
        System.out.println("Visiting: " + visiting);
        // keep track of all vertices we visited down this valid path
        // this is so we don't repeat ourselves when going down a different path through the same nodes
        visited.add(vertex);
        return false;
    }

    private static Stream<Arguments> cyclicGraphData() {
        return Stream.of(
                Arguments.of(new int[][]{
                        new int[]{1, 3},
                        new int[]{2, 3, 4},
                        new int[]{0},
                        new int[]{},
                        new int[]{2, 5},
                        new int[]{}
                }, true),
                Arguments.of(new int[][]{
                        new int[]{1, 2},
                        new int[]{2},
                        new int[]{}
                }, false),
                Arguments.of(new int[][]{
                        new int[]{},
                        new int[]{0, 2},
                        new int[]{0, 3},
                        new int[]{0, 4},
                        new int[]{0, 5},
                        new int[]{0},
                }, false),
                Arguments.of(new int[][]{
                        new int[]{1},
                        new int[]{2},
                        new int[]{3},
                        new int[]{4},
                        new int[]{5},
                        new int[]{6},
                        new int[]{},
                }, false)
        );
    }
}
