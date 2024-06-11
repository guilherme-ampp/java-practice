package com.patterns;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * This pattern matches the problem if:
 * - Data Access: It requires repeated access to data during execution
 * - Pair-wise relation: It requires us to store the relationship between two sets of data in order to compute a result.
 */
public class HashingTest {

    @ParameterizedTest
    @MethodSource("getNextGreaterElementData")
    public void testNextGreaterElement(int[] nums1, int[] nums2, int[] expected) {
        final Stack<Integer> stack = new Stack<>();
        final HashMap<Integer, Integer> map = new HashMap<>();

        for (int current : nums2) {
             while (!stack.isEmpty() && current > stack.peek()) {
                 map.put(stack.pop(), current);
             }
             stack.push(current);
        }

        while (!stack.empty()) {
            map.put(stack.pop(), -1);
        }

        final int[] answer = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            answer[i] = map.get(nums1[i]);
        }

        assertArrayEquals(expected, answer);
    }

    private static Stream<Arguments> getNextGreaterElementData() {
        return Stream.of(
                Arguments.of(new int[]{5, 4, 7}, new int[]{4, 5, 7, 3}, new int[]{7, 5, -1}),
                Arguments.of(new int[]{9, 7, 6}, new int[]{5, 7, 6, 8, 9}, new int[]{-1, 8, 8})
        );
    }

}
