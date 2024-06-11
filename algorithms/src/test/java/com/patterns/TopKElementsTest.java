package com.patterns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The pattern matches the problem if:
 * - Unsorted list analysis:
 * - Identifying a specific subset
 *
 * The pattern DOES NOT match the problem if:
 * - Presorted input: the input is already sorted according to the criteria relevant to solving the problem
 * - Single extreme value: if only 1 extreme value (either max or min) is required, that can be solved in O(n) with
 * a simple linear scan
 */
public class TopKElementsTest {

    public void testSortCharactersByFrequency() {

    }

    @ParameterizedTest
    @MethodSource("getRopesData")
    public void testConnectTheRopeWithMinimalCost(int[] ropes, int minCost) {
        final PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int n : ropes) {
            minHeap.offer(n);
        }

        int result = -1;
        while (!minHeap.isEmpty()) {
            final Integer cheapestRope1 = minHeap.poll();
            if (minHeap.isEmpty()) {
                result = cheapestRope1;
                break;
            }
            final Integer cheapestRope2 = minHeap.poll();
            minHeap.offer(cheapestRope1 + cheapestRope2);
        }

        assertEquals(minCost, result);
    }

    private static Stream<Arguments> getRopesData() {
        return Stream.of(Arguments.of(new int[]{6, 4, 3, 2}, 15));
    }

}
