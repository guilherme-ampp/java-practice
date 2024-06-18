package com.sort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Topological sorting is a technique used to organize a collection of items or tasks based on their dependencies.
 * <p>
 * The topological sort pattern is used to find valid orderings of elements that have dependencies on or priority
 * over each other. These elements can be represented as the nodes of a graph, so in technical terms, topological sort
 * is a way of ordering the nodes of a directed graph such that for every directed edge [a,b] from node a to node b,
 * a comes before b in the ordering.
 * <p>
 * Note: Topological sort is only applicable to directed acyclic graphs (DAGs), meaning there should be no cycles
 * present in the graph.
 * <p>
 * This pattern matches the problem if:
 * - Dependency relationships
 * - Ordering or sequencing accounting for some sort of dependency or priority
 * This pattern DOES NOT match the problem if:
 * - Presence of cycles -- topological sort cannot be applied if the graph has cycles
 * - Dynamic dependencies -- dependencies change dynamically during the executing of the algorithm
 */
public class TopologicalSortTest {

    @ParameterizedTest
    @MethodSource("topologicalSortData")
    public void testTopologicalSort(final List<String> nodes, final List<List<String>> edges, final boolean expectedResult) {
        assertEquals(!topologicalSort(nodes, edges).isEmpty(), expectedResult);
    }

    public List<String> topologicalSort(final List<String> nodes, final List<List<String>> edges) {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        if (edges == null || edges.isEmpty()) {
            return Collections.emptyList();
        }
        // init the graph and inDegree map
        final Map<String, AtomicInteger> inDegree = new HashMap<>();
        final Map<String, List<String>> graph = new HashMap<>();

        for (String node : nodes) {
            inDegree.put(node, new AtomicInteger(0));
            graph.put(node, new ArrayList<>());
        }

        // build the graph
        for (List<String> edge : edges) {
            final String parent = edge.get(0);
            final String child = edge.get(1);
            inDegree.get(child).incrementAndGet();
            graph.get(parent).add(child);
        }

        // find the sources
        final LinkedList<String> sources = inDegree.entrySet().stream()
                .filter((e) -> e.getValue().get() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));

        final List<String> sortedList = new ArrayList<>();
        while (!sources.isEmpty()) {
            final String source = sources.pop();
            sortedList.add(source);
            for (String child : graph.get(source)) {
                if (inDegree.get(child).decrementAndGet() == 0) {
                    sources.add(child);
                }
            }
        }

        if (sortedList.size() < nodes.size()) {
            return Collections.emptyList();  // cannot sort!
        }
        return sortedList;
    }

    private static Stream<Arguments> topologicalSortData() {
        return Stream.of(
                Arguments.of(List.of("0", "1"),
                        List.of(
                                List.of("0", "1"),
                                List.of("1", "0")
                        ), false),
                Arguments.of(List.of("0", "1", "2", "3", "4", "5"),
                        List.of(
                                List.of("1", "0"),
                                List.of("1", "2"),
                                List.of("3", "1"),
                                List.of("4", "1"),
                                List.of("1", "4"),
                                List.of("5", "1")
                        ), false),
                Arguments.of(List.of("0", "1", "2", "3", "4", "5", "6", "7"),
                        List.of(
                                List.of("1", "0"),
                                List.of("2", "1"),
                                List.of("3", "2"),
                                List.of("4", "3"),
                                List.of("5", "4"),
                                List.of("6", "2"),
                                List.of("1", "6"),
                                List.of("7", "1")
                        ), false),
                Arguments.of(List.of("0", "1", "2", "3", "4"),
                        List.of(
                                List.of("1", "0"),
                                List.of("2", "1"),
                                List.of("3", "1"),
                                List.of("1", "4")
                        ), true)
        );
    }

}
