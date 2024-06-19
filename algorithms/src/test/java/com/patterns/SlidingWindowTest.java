package com.patterns;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The sliding window pattern is used to process sequential data, arrays, and strings, for example, to efficiently
 * solve subarray or substring problems. It involves maintaining a dynamic window that slides through the array or
 * string, adjusting its boundaries as needed to track relevant elements or characters. The window is used to slide
 * over the data in chunks corresponding to the window size, and this can be set according to the problem’s
 * requirements. It may be viewed as a variation of the two pointers pattern, with the pointers being used to set
 * the window bounds.
 */
public class SlidingWindowTest {

    @ParameterizedTest
    @MethodSource("maxSumSubArrayData")
    public void testMaximumSumSubArrayOfSizeK(int[] array, int subArraySize, int expectedResult) {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length < subArraySize) {
            return;
        }
        int maxSum = 0;
        int windowStart = 0;
        int windowEnd = 0;

        // open the window
        while (windowEnd < subArraySize) {
            maxSum += array[windowEnd];
            windowEnd++;
        }
        System.out.printf("MaxSum[%d]%n", maxSum);

        // move the window
        int currentSum = maxSum;
        while (windowEnd < array.length) {
            currentSum -= array[windowStart];
            windowStart++;
            currentSum += array[windowEnd];
            windowEnd++;
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
            System.out.printf("MaxSum[%d] CurrentSum[%d]%n", maxSum, currentSum);
        }

        assertEquals(expectedResult, maxSum);
    }


    @ParameterizedTest
    @MethodSource("longestNonRepeatingSubstringData")
    public void testLongestSubstringWithNoRepeatingCharacters(final String string, int expectedLength) {
        if (string == null || string.isEmpty()) {
            return;
        }
        int maxLength = 0;
        char[] chars = string.toCharArray();
        int windowStart = 0;
        Map<Character, Integer> charPositionsMap = new HashMap<>();

        // keep opening the window until we find a repeated character
        for (int windowEnd = 0; windowEnd < chars.length; windowEnd++) {
            Integer previousIndex = charPositionsMap.put(chars[windowEnd], windowEnd);
            if (previousIndex != null && previousIndex > windowStart) {
                // if we find a repeated character and its previous occurrence is within the current window
                // we then adjust the start of our window to the next position after the index of the repeated char
                windowStart = previousIndex + 1;
            }
            // then we calculate our current window length
            // that will be the differences of the two indexes + 1 to account for the char in the trailing position
            maxLength = windowEnd - windowStart + 1;
        }

        assertEquals(expectedLength, maxLength);
    }

    private static Stream<Arguments> maxSumSubArrayData() {
        return Stream.of(
                Arguments.of(new int[]{4, 2, -1, 9, 7, -3, 5}, 4, 18)
        );
    }

    private static Stream<Arguments> longestNonRepeatingSubstringData() {
        return Stream.of(
                Arguments.of("abcdbea", 5),
                Arguments.of("abcdefgh", 8),
                Arguments.of("abacdaefgh", 7)
        );
    }

}
