package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * There are a total of `numCourses` courses you have to take. The courses are labeled from 0 to `numCourses - 1`.
 * You are also given a prerequisites array, where prerequisites[i] = [a[i], b[i]] indicates that you must take
 * course b[i] first if you want to take the course a[i]. For example, the pair
 * [1,0] indicates that to take course 1, you have to first take course 0.
 * <p>
 * Return TRUE if all the courses can be finished. Otherwise, return FALSE.
 */
public class CourseScheduleTest {

    @ParameterizedTest
    @MethodSource("courseScheduleData")
    public void testCourseSchedule(int numCourses, int[][] prerequisites, boolean expected) {
        assertEquals(expected, canFinish(numCourses, prerequisites));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return false;
        }
        if (prerequisites.length == 0) {
            return true;
        }
        // init the hash maps to keep track of the nodes degrees and their children
        final Map<Integer, List<Integer>> graph = new HashMap<>();
        final Map<Integer, AtomicInteger> inDegree = new HashMap<>();

        for (int course = 0; course < numCourses; course++) {
            graph.put(course, new ArrayList<>());
            inDegree.put(course, new AtomicInteger(0));
        }

        // build the graph from the prerequisites
        for (int[] edges : prerequisites) {
            int source = edges[0];
            int sink = edges[1];
            // for each edge we will have a parent and a child / or a source and a sink
            graph.get(source).add(sink);
            // the inDegree map will count how many incoming connections there are into the child/sink node
            // this is a `sink` node -- as opposed to the `source` node in this iteration
            inDegree.get(sink).incrementAndGet();
        }

        // find all sources - those with inDegrees = 0
        final LinkedList<Integer> sources = inDegree
                .entrySet().stream()
                .filter((e) -> e.getValue().get() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));

        // topological sort!
        final List<Integer> sortedList = new ArrayList<>();
        // keep running as we pop the `source` nodes and the `sink` nodes become sources
        while (!sources.isEmpty()) {
            // pop the source
            Integer source = sources.pop();
            // add it to the sorted list -- the source is a starting point
            sortedList.add(source);
            // for each child of this source ...
            for (int child : graph.get(source)) {
                // ... decrement its degree -- this is because we popped the source and therefore removed
                // one connection from this child -- hence, we are decrementing the degree
                if (inDegree.get(child).decrementAndGet() == 0) {
                    // the child becomes a new source as soon as we popped all of its connections!
                    sources.add(child);
                }
            }
        }

        // now, if we ended up here with fewer nodes in the sorted list than the number of nodes in the graph
        // it means there is not a possible topological order for the graph
        if (sortedList.size() != numCourses) {
            // there most likely is a cycle
            return false;
        }
        System.out.println(sortedList);
        return true;
    }

    private static Stream<Arguments> courseScheduleData() {
        return Stream.of(
                Arguments.of(2,
                        new int[][]{
                                new int[]{0, 1},
                                new int[]{1, 0}
                        }, false),
                Arguments.of(6,
                        new int[][]{
                                new int[]{1, 0},
                                new int[]{1, 2},
                                new int[]{3, 1},
                                new int[]{4, 1},
                                new int[]{1, 4},
                                new int[]{5, 1}
                        }, false),
                Arguments.of(8,
                        new int[][]{
                                new int[]{1, 0},
                                new int[]{2, 1},
                                new int[]{3, 2},
                                new int[]{4, 3},
                                new int[]{5, 4},
                                new int[]{6, 2},
                                new int[]{1, 6},
                                new int[]{7, 1}
                        }, false),
                Arguments.of(5,
                        new int[][]{
                                // [1, 0], [2, 1], [3, 1], [1, 4]
                                new int[]{1, 0},
                                new int[]{2, 1},
                                new int[]{3, 1},
                                new int[]{1, 4}
                        }, true)
        );
    }

}
