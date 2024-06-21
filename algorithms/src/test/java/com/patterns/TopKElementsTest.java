package com.patterns;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The pattern matches the problem if:
 * - Unsorted list analysis:
 * - Identifying a specific subset
 * <p>
 * The pattern DOES NOT match the problem if:
 * - Presorted input: the input is already sorted according to the criteria relevant to solving the problem
 * - Single extreme value: if only 1 extreme value (either max or min) is required, that can be solved in O(n) with
 * a simple linear scan
 */
public class TopKElementsTest {

    @ParameterizedTest
    @MethodSource("getSortCharsData")
    public void testSortCharactersByFrequency(String input, String expected) {
        final Map<Character, AtomicInteger> charCounter = new HashMap<>();
        for (Character c : input.toCharArray()) {
            charCounter.computeIfAbsent(c, (k) -> new AtomicInteger(0)).incrementAndGet();
        }

        final PriorityQueue<Pair<Integer, Character>> maxHeap =
                new PriorityQueue<>(Comparator.comparingInt((p) -> ((Pair<Integer, Character>) p).getKey()).reversed());
        for (Map.Entry<Character, AtomicInteger> entry : charCounter.entrySet()) {
            maxHeap.offer(new Pair<>(entry.getValue().get(), entry.getKey()));
        }

        final StringBuilder sorted = new StringBuilder();
        while (!maxHeap.isEmpty()) {
            final Pair<Integer, Character> pair = maxHeap.poll();
            sorted.append(String.valueOf(pair.getValue()).repeat(pair.getKey()));
        }

        assertEquals(expected, sorted.toString());
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

    private static Stream<Arguments> getSortCharsData() {
        return Stream.of(Arguments.of("buubble", "bbbuule"));
    }

    private static Stream<Arguments> getRopesData() {
        return Stream.of(Arguments.of(new int[]{6, 4, 3, 2}, 15));
    }

    private static final class Pair<K, V> {
        final K key;
        final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
